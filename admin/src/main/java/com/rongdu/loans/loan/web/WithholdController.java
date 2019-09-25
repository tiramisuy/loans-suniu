package com.rongdu.loans.loan.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.enums.PayTypesEnum;
import com.rongdu.loans.loan.vo.WithholdRepayPlanQueryVO;
import com.rongdu.loans.pay.exception.OrderProcessingException;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.sys.entity.Log;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.LogService;
import com.rongdu.loans.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/withhold")
public class WithholdController extends BaseController {

    @Autowired
    private WithholdService withholdService;
    @Autowired
    private LogService logService;

    /**
     * 代扣
     */
    @ResponseBody
    @RequestMapping(value = "withhold")
    public WebResult withhold(@RequestParam(value = "itemId") String itemId,
                              @RequestParam(value = "applyId") String applyId, @RequestParam(value = "contNo") String contNo,
                              @RequestParam(value = "userId") String userId, @RequestParam(value = "thisTerm") Integer thisTerm,
                              @RequestParam(value = "totalAmount") String totalAmount,
                              @RequestParam(value = "actualRepayAmt") String actualRepayAmt,
                              @RequestParam(value = "channel") String channel) {
        User user = UserUtils.getUser();
        logger.info("系统代扣--->{}--->{}--->{}", user.getId(), user.getName(), itemId);
        // 插入日志
        Log entity = new Log();
        entity.setTitle("还款明细-手动代扣");
        entity.setCreateBy(user);
        entity.setParams("repayPlanItemId=" + itemId + "&channel=" + channel);
        logService.save(entity);

        WebResult lockResult = isWithholdLimit(itemId, 5);
        if (lockResult != null) {
            return lockResult;
        }
        String lockKey = Global.JBD_PAY_LOCK + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        try {
            // 根据orderId防并发加锁
            boolean lock = JedisUtils.setLock(lockKey, requestId, 2 * 60);
            if (!lock) {
                logger.warn("{}-协议直接支付接口调用中，applyId= {}",channel, applyId);
                return new WebResult("2", "交易处理中，请稍后查询", null);
            }
            if ("tonglian".equals(channel)) {
                WithholdResultVO vo = withholdService.adminOverdueWithhold(itemId, PayTypesEnum.TONGLIAN);
                if ("F".equals(vo.getStatus())) {
                    return new WebResult("2", vo.getMsg(), null);
                }
            }
            return new WebResult("1", "代扣请求提交成功", null);
        } catch (Exception e) {
            logger.error("系统异常", e);
            return new WebResult("99", "系统异常");
        }finally {
            // 解除orderId并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
    }

    /**
     * 手动结算查询还款计划
     */
    @ResponseBody
    @RequestMapping(value = "processAdminWithholdQuery")
    public WebResult processAdminWithholdQuery(@RequestParam(value = "repayPlanItemId") String repayPlanItemId,
                                               @RequestParam(value = "actualRepayTime") String actualRepayTime,
                                               @RequestParam(value = "type") Integer type) {
        User user = UserUtils.getUser();
        logger.info("手动结算查询还款计划--->{}--->{},操作类型--->{},当期还款计划ID--->{}", user.getId(), user.getName(), type,
                repayPlanItemId);
        try {
            WithholdRepayPlanQueryVO vo = null;
            Boolean isRepay = true;// UserUtils.haveRole("sdcz");
            if (isRepay) {
                synchronized (WithholdController.class) {
                    if (StringUtils.isBlank(actualRepayTime)) {
                        actualRepayTime = DateUtils.getDateTime();
                    }
                    vo = withholdService.processAdminWithholdQueryBySuniu(repayPlanItemId, actualRepayTime, type);
                }
            }
            return new WebResult("1", "提交成功", vo);
        } catch (Exception e) {
            logger.error("手动结算查询异常：repayPlanItemId = " + repayPlanItemId + ",type=" + type, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 手动结算
     */
    @ResponseBody
    @RequestMapping(value = "processAdminWithhold")
    public WebResult processAdminWithhold(@RequestParam(value = "repayPlanItemId") String repayPlanItemId,
                                          @RequestParam(value = "actualRepayAmt") BigDecimal actualRepayAmt,
                                          @RequestParam(value = "actualRepayTime") String actualRepayTime,
                                          @RequestParam(value = "prepayFee") String prepayFee, @RequestParam(value = "type") Integer type,
                                          @RequestParam(value = "deductionAmt") String deductionAmt,
                                          @RequestParam(value = "repayType") String repayType,
                                          @RequestParam(value = "repayTypeName") String repayTypeName) {
        User user = UserUtils.getUser();
        logger.info("还款明细-手动结算--->{}--->{}--->{}", user.getId(), user.getName(), repayPlanItemId);

        String lockKey = "processAdminWithhold_" + repayPlanItemId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        boolean lock = JedisUtils.setLock(lockKey, requestId, 60);
        if (!lock) {
            logger.warn("processAdminWithhold接口调用中，repayPlanItemId= {}", repayPlanItemId);
            return new WebResult("3", "操作频繁，请60秒后再试！", null);
        }
        try {
            Boolean isRepay = UserUtils.haveRole("sdcz");
            if (isRepay) {
                synchronized (WithholdController.class) {
                    if (StringUtils.isBlank(prepayFee)) {
                        prepayFee = "0";
                    }
                    if (StringUtils.isBlank(deductionAmt)) {
                        deductionAmt = "0";
                    }
                    withholdService.processAdminWithholdBySuniu(repayPlanItemId, actualRepayAmt, actualRepayTime, prepayFee,
                            type, deductionAmt, repayType, repayTypeName);
                }
            } else {
                return new WebResult("1", "没有权限", null);
            }
            // 插入日志
            Log entity = new Log();
            switch (type) {
                case 1:
                    entity.setTitle("还款明细-手动结算-一次性还款付息");
                    break;
                case 2:
                    entity.setTitle("还款明细-手动结算-部分还款");
                    break;
                case 3:
                    entity.setTitle("还款明细-手动结算-提前结清");
                    break;
                case 4:
                    entity.setTitle("还款明细-手动结算-取消借款");
                    break;
                default:
                    break;
            }
            entity.setCreateBy(user);
            entity.setParams("repayPlanItemId=" + repayPlanItemId);
            logService.save(entity);
            return new WebResult("1", "提交成功", null);
        } catch (OrderProcessingException e) {
            return new WebResult(e.getCode(), e.getMsg());
        } catch (Exception e) {
            logger.error("手动结算异常：repayPlanItemId = " + repayPlanItemId + ",type=" + type, e);
            return new WebResult("99", "系统异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/partWithhold", method = RequestMethod.POST)
    public WebResult partWithhold(@RequestParam String repayPlanItemId, @RequestParam String actualRepayAmt,
                                  @RequestParam Integer type) {
        User user = UserUtils.getUser();
        logger.info("部分代扣--->{}--->{}--->{}", user.getId(), user.getName(), repayPlanItemId);
        WebResult lockResult = isWithholdLimit(repayPlanItemId, 5);
        if (lockResult != null) {
            return lockResult;
        }
        try {
            // 执行部分代扣操作
            WithholdResultVO vo = withholdService.partWithhold(repayPlanItemId, actualRepayAmt, type);
            if ("I".equals(vo.getStatus())) {
                return new WebResult("1", "提交成功", null);
            } else {
                return new WebResult("2", vo.getMsg(), null);
            }
        } catch (Exception e) {
            logger.error("部分代扣接口调用异常，repayPlanItemId= {}", repayPlanItemId, e);
            return new WebResult("2", "未知异常", null);
        }
    }

    private WebResult isWithholdLimit(String itemId, int seconds) {
        User user = UserUtils.getUser();
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        String userLockKey = "admin_withhold_user_lock_" + user.getId();
        boolean userLock = JedisUtils.setLock(userLockKey, requestId, seconds);
        if (!userLock) {
            logger.warn("代扣--->操作频繁，请{}秒后再试，itemId= {},name={}", seconds, itemId, user.getName());
            return new WebResult("2", "操作频繁，请" + seconds + "秒后再试！", null);
        }

        String itemLockKey = "admin_withhold_item_lock_" + itemId;
        boolean itemLock = JedisUtils.setLock(itemLockKey, requestId, 60);
        if (!itemLock) {
            logger.warn("代扣--->代扣处理中，请勿重复操作，itemId= {},name={}", itemId, user.getName());
            return new WebResult("2", "代扣处理中，请勿重复操作！", null);
        }
        if(!user.getLoginName().equals("xiexuan")){
            String numLockKey = "admin_withhold_item_num_lock_" + user.getLoginName() + itemId;
            String s = (String) JedisUtils.getObject(numLockKey);
            int num = s == null ? 0 : Integer.valueOf(s);
            if (num >= 5) {
                logger.warn("代扣--->操作频繁，今日操作已达上限5次，itemId= {},name={}", itemId, user.getName());
                return new WebResult("2", "操作频繁，今日操作已达上限5次,请明日操作！", null);
            } else {
                num++;
                JedisUtils.setObject(numLockKey,String.valueOf(num),DateUtils.getSecondsOfTwoDate(new Date(),DateUtils.getDayEnd(new Date())));
            }
        }
        return null;
    }

}
