/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.service.StoreService;
import com.rongdu.loans.common.LoginUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.compute.helper.RepayPlanHelper;
import com.rongdu.loans.cust.entity.CustCoupon;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.entity.Message;
import com.rongdu.loans.cust.manager.CustCouponManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.manager.MessageManager;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustCouponService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.OverdueRepayNoticeVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.loan.vo.WithholdRepayPlanQueryVO;
import com.rongdu.loans.pay.exception.OrderProcessingException;
import com.rongdu.loans.pay.exception.WithholdUpdateException;
import com.rongdu.loans.pay.op.*;
import com.rongdu.loans.pay.service.*;
import com.rongdu.loans.pay.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 代扣服务
 *
 * @author zhangxiaolong
 * @version 2017-07-22
 */
@Service("withholdService")
public class WithholdServiceImpl extends BaseService implements WithholdService {

    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private LoanRepayPlanManager loanRepayPlanManager;
    @Autowired
    private BaofooWithholdService baofooWithholdService;
    @Autowired
    private TltAgreementPayService tltAgreementPayService;
    @Autowired
    private CustUserManager custUserManager;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private ContractManager contractManager;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private BorrowInfoManager borrowInfoManager;
    @Autowired
    private OverdueManager overdueManager;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private XianFengWithholdService xianFengWithholdService;
    @Autowired
    private XianFengAgreementPayService xianFengAgreementPayService;
    @Autowired
    private SettlementService settlementService;
    @Autowired
    private RongPointCutService rongPointCutService;
    @Autowired
    private BorrowInfoService borrowInfoService;
    @Autowired
    private CustCouponService custCouponService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private KjtpayService kjtpayService;
    @Autowired
    private CustCouponManager custCouponManager;
    @Autowired
    private ConfigService configService;

    @Autowired
    private BindCardService bindCardService;

    /**
     * 批量代扣任务 1.查询还款计划到期的数据 2.发起代扣 3.明确代扣结果，落库 4.修改业务数据状态 5.记录操作日志
     */
    @Override
    public TaskResult withholdBatch() {
        logger.info("开始执行宝付代扣任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;

        int[] termTypes = new int[]{1, 2};// 1=单期，2=多期
        for (int termType : termTypes) {
            /** 查询当日到期的还款计划明细 */
            List<RepayPlanItem> itemList = repayPlanItemManager.curdateRepayList(termType);
            if (CollectionUtils.isEmpty(itemList)) {
                // long endTime = System.currentTimeMillis();
                // logger.info("执行代扣任务结束，暂无代扣数据。执行耗时{}", endTime - starTime);
                // return new TaskResult(success, fail);
                continue;
            }
            writeInitLog(itemList);
            /** 查询用户信息 */
            List<CustUser> userList = findCustUserList(itemList);
            /** 查询贷款申请单信息 */
            // List<LoanApply> applyList = findApplyList(itemList);
            /** 查询合同信息 */
            // List<Contract> contractList = findContractList(itemList);
            /** 查询还款计划 */
            // List<LoanRepayPlan> repayPlanList = findRepayPlanList(itemList);

            for (RepayPlanItem item : itemList) {
                try {
                    Thread.sleep(100);

                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
                    int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                    if (currTerm != item.getThisTerm().intValue()) {
                        logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", item.getId(), currTerm,
                                item.getThisTerm());
                        continue;
                    }
                    // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
                    Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
                    if (payCount != 0) {
                        logger.error("批量代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    if (repayPlanItemManager.processing(item.getId()) == 0) {
                        logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    CustUser user = getUser(item, userList);
                    WithholdOP param = getWithholdOP(item, user);
                    /** 宝付代扣 */
                    WithholdResultVO vo = null;
                    try {
                        /** 宝付代扣 */
                        vo = baofooWithholdService.withhold(param, PayTypeEnum.SETTLEMENT.getId());
                    } catch (Exception e) {
                        // 宝付接口调用失败
                        logger.error("代扣失败，param = " + JsonMapper.getInstance().toJson(param), e);
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }

                    if (!vo.getSuccess()) {
                        rongPointCutService.settlementPoint(item.getId(), false);// 用作Rong360订单还款时，切面通知的切入点标记
                        logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(), vo.getMsg());
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }

                    // 更新订单
                    WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
                    updateVO.setSuccAmt(vo.getSuccAmt());
                    updateVO.setOrigTradeDate(vo.getTradeDate());
                    updateOrderInfo(item.getId(), updateVO);
                    rongPointCutService.settlementPoint(item.getId(), true);// 用作Rong360订单还款时，切面通知的切入点标记
                    success++;
                } catch (Exception e) {
                    logger.error("代扣失败，还款明细id = " + item.getId(), e);
                    fail++;
                }
            }
            itemList.clear();
            itemList = null;
        }
        long endTime = System.currentTimeMillis();
        logger.info("执行宝付代扣任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 批量代扣任务 1.查询还款计划未到期的数据 2.发起代扣 3.明确代扣结果，落库 4.修改业务数据状态 5.记录操作日志
     */
    @Override
    public TaskResult withholdBatchAfterCurdate() {
        logger.info("开始执行宝付代扣任务-代扣未到期。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;

        int[] termTypes = new int[]{1, 2};// 1=单期，2=多期
        // ytodo 0317 配置代扣数量
        String withholdDate = configService.getValue("withhold_date");
        if (DateUtils.daysBetween(DateUtils.parse(withholdDate, "yyyy-MM-dd"), new Date()) >= 0) {
            logger.info("代扣日期错误");
            return new TaskResult(success, fail);
        }
        int limit = Integer.valueOf(configService.getValue("withhold_limit"));
        for (int termType : termTypes) {
            /** 查询当日未到期的还款计划明细 */
            // ytodo 0317 查询当日未到期的还款计划明细
            List<RepayPlanItem> itemList = repayPlanItemManager.afterCurdateRepayList(termType, withholdDate, limit);
            if (CollectionUtils.isEmpty(itemList)) {
                // long endTime = System.currentTimeMillis();
                // logger.info("执行代扣任务结束，暂无代扣数据。执行耗时{}", endTime - starTime);
                // return new TaskResult(success, fail);
                continue;
            }
            writeInitLog(itemList);
            /** 查询用户信息 */
            List<CustUser> userList = findCustUserList(itemList);
            /** 查询贷款申请单信息 */
            // List<LoanApply> applyList = findApplyList(itemList);
            /** 查询合同信息 */
            // List<Contract> contractList = findContractList(itemList);
            /** 查询还款计划 */
            // List<LoanRepayPlan> repayPlanList = findRepayPlanList(itemList);

            for (RepayPlanItem item : itemList) {
                try {
                    Thread.sleep(100);

                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
                    int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                    if (currTerm != item.getThisTerm().intValue()) {
                        logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", item.getId(), currTerm,
                                item.getThisTerm());
                        continue;
                    }
                    // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
                    Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
                    if (payCount != 0) {
                        logger.error("批量代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    if (repayPlanItemManager.processing(item.getId()) == 0) {
                        logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    CustUser user = getUser(item, userList);
                    // ytodo 0317 设置代扣金额
                    WithholdOP param = getWithholdOPTemp(item, repayPlan, user);
                    /** 宝付代扣 */
                    WithholdResultVO vo = null;
                    try {
                        /** 宝付代扣 */
                        vo = baofooWithholdService.withhold(param, PayTypeEnum.SETTLEMENT.getId());
                    } catch (Exception e) {
                        // 宝付接口调用失败
                        logger.error("代扣失败，param = " + JsonMapper.getInstance().toJson(param), e);
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }

                    if (!vo.getSuccess()) {
                        rongPointCutService.settlementPoint(item.getId(), false);// 用作Rong360订单还款时，切面通知的切入点标记
                        logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(), vo.getMsg());
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }

                    // 更新订单
                    WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
                    updateVO.setSuccAmt(vo.getSuccAmt());
                    updateVO.setOrigTradeDate(vo.getTradeDate());
                    updateOrderInfo(item.getId(), updateVO);
                    rongPointCutService.settlementPoint(item.getId(), true);// 用作Rong360订单还款时，切面通知的切入点标记
                    success++;
                } catch (Exception e) {
                    logger.error("代扣失败，还款明细id = " + item.getId(), e);
                    fail++;
                }
            }
            itemList.clear();
            itemList = null;
        }
        long endTime = System.currentTimeMillis();
        logger.info("执行宝付代扣任务结束-代扣未到期,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 海尔支付 代扣定时任务 批量代扣任务 1.查询还款计划到期的数据 2.发起代扣 3.明确代扣结果，落库 4.修改业务数据状态 5.记录操作日志
     */
    @Override
    public TaskResult kjtpayTradeBankWitholding() {
        logger.info("开始执行海尔代扣任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        int[] termTypes = new int[]{1, 2};// 1=单期，2=多期
        for (int termType : termTypes) {
            /** 查询当日到期的还款计划明细 */
            List<RepayPlanItem> itemList = repayPlanItemManager.curdateRepayList(termType);
            if (CollectionUtils.isEmpty(itemList)) {
                // long endTime = System.currentTimeMillis();
                // logger.info("执行代扣任务结束，暂无代扣数据。执行耗时{}", endTime - starTime);
                // return new TaskResult(success, fail);
                continue;
            }
            writeInitLog(itemList);
            /** 查询用户信息 */
            List<CustUser> userList = findCustUserList(itemList);
            /** 查询贷款申请单信息 */
            // List<LoanApply> applyList = findApplyList(itemList);
            /** 查询合同信息 */
            // List<Contract> contractList = findContractList(itemList);
            /** 查询还款计划 */
            // List<LoanRepayPlan> repayPlanList = findRepayPlanList(itemList);

            for (RepayPlanItem item : itemList) {
                try {
                    Thread.sleep(100);

                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
                    int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                    if (currTerm != item.getThisTerm().intValue()) {
                        logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", item.getId(), currTerm,
                                item.getThisTerm());
                        continue;
                    }
                    // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
                    Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
                    if (payCount != 0) {
                        logger.error("批量代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    if (repayPlanItemManager.processing(item.getId()) == 0) {
                        logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    CustUser user = getUser(item, userList);
                    WithholdOP param = getWithholdOP(item, user);
                    /** 海尔代扣 */
                    WithholdResultVO vo = null;
                    try {
                        vo = kjtpayService.tradeBankWitholding(param);
                    } catch (Exception e) {
                        // 海尔接口调用失败
                        logger.error("代扣失败，param = " + JsonMapper.getInstance().toJson(param), e);
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }

                    if (!vo.getSuccess() && !"P".equals(vo.getCode())) {// P：处理中
                        rongPointCutService.settlementPoint(item.getId(), false);// 用作Rong360订单还款时，切面通知的切入点标记
                        logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(), vo.getMsg());
                        fail++;
                        repayPlanItemManager.unfinish(item.getId());
                        // 发送短信
                        String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(),
                                item.getTotalAmount());
                        sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
                        continue;
                    }
                    success++;
                } catch (Exception e) {
                    logger.error("代扣失败，还款明细id = " + item.getId(), e);
                    fail++;
                }
            }
            itemList.clear();
            itemList = null;
        }

        long endTime = System.currentTimeMillis();
        logger.info("执行海尔代扣任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 先锋 代扣定时任务 批量代扣任务 1.查询还款计划到期的数据 2.发起代扣 3.明确代扣结果，落库 4.修改业务数据状态 5.记录操作日志
     */
    @Override
    public TaskResult xfWithholdTask() {
        logger.info("开始执行先锋代扣任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        int[] termTypes = new int[]{1, 2};// 1=单期，2=多期
        for (int termType : termTypes) {
            /** 查询当日到期的还款计划明细 */
            List<RepayPlanItem> itemList = repayPlanItemManager.curdateRepayList(termType);
            if (CollectionUtils.isEmpty(itemList)) {
                continue;
            }
            writeInitLog(itemList);
            for (RepayPlanItem item : itemList) {
                LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
                int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                if (currTerm != item.getThisTerm().intValue()) {
                    logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", item.getId(), currTerm,
                            item.getThisTerm());
                    continue;
                }
                // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
                Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
                if (payCount != 0) {
                    logger.error("批量代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
                    continue;
                }
                if (repayPlanItemManager.processing(item.getId()) == 0) {
                    logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
                    continue;
                }
                XfAgreementPayResultVO vo = null;
                try {
                    Thread.sleep(100);
                    BigDecimal actualRepayAmt2 = item.getActualRepayAmt() == null ? BigDecimal.ZERO
                            : item.getActualRepayAmt();
                    String txAmt = item.getTotalAmount().subtract(actualRepayAmt2).toString();

                    XfAgreementPayOP op = new XfAgreementPayOP();
                    op.setContractId(item.getContNo());
                    op.setApplyId(item.getApplyId());
                    op.setUserId(item.getUserId());
                    op.setAmount(txAmt);
                    op.setRepayPlanItemId(item.getId());
                    op.setIp("127.0.0.1");
                    op.setSource("99");// 后台
                    op.setPayType(PayTypeEnum.SETTLEMENT.getId());
                    vo = xianFengAgreementPayService.agreementPay(op);
                } catch (Exception e) {
                    logger.error("代扣失败，info = " + JsonMapper.getInstance().toJson(item), e);
                    fail++;
                    repayPlanItemManager.unfinish(item.getId());
                    continue;
                }
                repayPlanItemManager.unfinish(item.getId());
                if ("F".equals(vo.getStatus())) {
                    logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getResCode(),
                            vo.getResMessage());
                    fail++;
                    continue;
                }
                success++;
            }
            itemList.clear();
            itemList = null;
        }

        long endTime = System.currentTimeMillis();
        logger.info("执行先锋代扣任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }


    /**
     * 通联 代扣定时任务 批量代扣任务 1.查询还款计划到期的数据 2.发起代扣 3.明确代扣结果，落库 4.修改业务数据状态 5.记录操作日志
     */
    @Override
    public TaskResult tlWithholdTask() {
        logger.info("开始执行通联代扣任务。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        int[] termTypes = new int[]{1, 2};// 1=单期，2=多期
        for (int termType : termTypes) {
            /** 查询当日到期的还款计划明细 */
            List<RepayPlanItem> itemList = repayPlanItemManager.curdateRepayList(termType);
            if (CollectionUtils.isEmpty(itemList)) {
                continue;
            }
            writeInitLog(itemList);
            for (RepayPlanItem item : itemList) {
                String applyId = item.getApplyId();
                String lockKey = Global.JBD_PAY_LOCK + applyId;
                String requestId = String.valueOf(System.nanoTime());// 请求标识
                TlAgreementPayResultVO vo = null;

                try {
                    boolean lock = JedisUtils.setLock(lockKey, requestId, 2 * 60);
                    if (!lock) {
                        logger.warn("协议直接支付接口调用中，applyId= {}", applyId);
                        continue;
                    }

                    LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
                    int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
                    if (currTerm != item.getThisTerm().intValue()) {
                        logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", item.getId(), currTerm,
                                item.getThisTerm());
                        continue;
                    }
                    // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
                    Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
                    if (payCount != 0) {
                        logger.error("批量代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
                        continue;
                    }
                    Thread.sleep(100);

                    CustUserVO custUser = LoginUtils.getCustUserInfo(item.getUserId());
                    if (null == custUser) {
                        // 从数据库获取
                        custUser = custUserService.getCustUserById(item.getUserId());
                    }
                    CustUser user = BeanMapper.map(custUser, CustUser.class);
                    WithholdOP param = getWithholdOP(item, user);
                    vo = tltAgreementPayService.withhold(param, PayTypeEnum.SETTLEMENT.getId());
                } catch (Exception e) {
                    logger.error("代扣失败，info = " + JsonMapper.getInstance().toJson(item), e);
                    fail++;
                    continue;
                } finally {
                    // 解除orderId并发锁
                    JedisUtils.releaseLock(lockKey, requestId);
                }
                if ("F".equals(vo.getStatus())) {
                    logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getResCode(),
                            vo.getResMessage());
                    fail++;
                    continue;
                } else if ("I".equals(vo.getStatus())){
                    // 代扣成功，更新订单信息
            /*        WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
                    updateVO.setSuccAmt(vo.getAmount());
                    updateVO.setOrigTradeDate(vo.getTradeTime());
                    this.updateOrderInfo(item.getId(), updateVO);*/
                    success++;
                }
            }
            itemList.clear();
            itemList = null;
        }

        long endTime = System.currentTimeMillis();
        logger.info("执行通联代扣任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * code y0524 逾期手动代扣
     *
     * @param itemId
     * @return
     */
    @Transactional
    public Boolean overdueWithhold(String itemId) {
        /*
         * RepayPlanItem item = repayPlanItemManager.getById(itemId); if (item
         * == null ||
         * ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus()) ||
         * DateUtils.daysBetween(item.getRepayDate(), new Date()) < 0) {
         * logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId); return false; }
         * LoanRepayPlan repayPlan =
         * loanRepayPlanManager.getByApplyId(item.getApplyId()); int currTerm =
         * (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1; if
         * (currTerm != item.getThisTerm().intValue()) { logger.error(
         * "还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId,
         * currTerm, item.getThisTerm()); return false; } //
         * 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付 Long payCount =
         * repayLogService.countPayingByRepayPlanItemId(item.getId()); if
         * (payCount != 0) { logger.error("代扣数据异常，订单正在处理中：{}",
         * JsonMapper.getInstance().toJson(item)); return false; } if
         * (repayPlanItemManager.processing(item.getId()) == 0) {
         * logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
         * return false; } CustUser user =
         * custUserManager.getById(item.getUserId()); WithholdOP param =
         * getWithholdOP(item, user);
         *//** 宝付代扣 */
        /*
         * WithholdResultVO vo = baofooWithholdService.withhold(param); if
         * (!vo.getSuccess()) { logger.error(
         * "代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(),
         * vo.getMsg()); repayPlanItemManager.unfinish(item.getId()); // 发送短信
         * String content = String.format(ShortMsgTemplate.OVERDUE_REPAY_FAIL,
         * item.getUserName(), item.getTotalAmount()); sendSMS(content,
         * user.getMobile(), user.getId(), user.getChannel()); return false; }
         */
//        WithholdResultVO vo = withhold(itemId, null);
        WithholdResultVO vo = withhold(itemId, null, PayTypesEnum.BAOFU);
        if (!vo.getSuccess()) {
            rongPointCutService.settlementPoint(itemId, false);// 用作Rong360订单还款时，切面通知的切入点标记
            return false;
        }
        // 更新订单
        WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
        updateVO.setSuccAmt(vo.getSuccAmt());
        updateVO.setOrigTradeDate(vo.getTradeDate());
        updateOrderInfo(itemId, updateVO);
        rongPointCutService.settlementPoint(itemId, true);// 用作Rong360订单还款时，切面通知的切入点标记
        return true;
    }

    /**
     * 逾期代扣，返回代扣信息
     *
     * @param itemId
     * @param withholdType 代付类型 baofu->宝付   通联->tonglian
     * @return
     */
    @Transactional
    public WithholdResultVO adminOverdueWithhold(@NotNull(message = "参数不能为空") String itemId, PayTypesEnum payTypeEnum) {
        WithholdResultVO vo = withhold(itemId, null, payTypeEnum);
        if (!vo.getSuccess()) {
            rongPointCutService.settlementPoint(itemId, false);// 用作Rong360订单还款时，切面通知的切入点标记
            return vo;
        }
/*        // 更新订单
        WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
        updateVO.setSuccAmt(vo.getSuccAmt());
        updateVO.setOrigTradeDate(vo.getTradeDate());
        updateOrderInfo(itemId, updateVO);*/
 //       rongPointCutService.settlementPoint(itemId, true);// 用作Rong360订单还款时，切面通知的切入点标记
        return vo;
    }

    /**
     * code y0524 延期代扣
     */
    @Override
    @Transactional
    public WithholdResultVO delayDealWithhold(String itemId, String delayAmt, PayTypesEnum payTypesEnum) {
        // 代扣操作
        WithholdResultVO vo = withhold(itemId, delayAmt, payTypesEnum);
        if (!vo.getSuccess()) {
            return vo;
        }
        try {
            // 延期操作
            contractService.delayDeal(itemId, 2, DateUtils.formatDateTime(vo.getTradeDate()), null, null);
        } catch (Exception e) {
            logger.error("代扣成功，延期异常：repayPlanItemId = " + itemId, e);
            throw new WithholdUpdateException("F002", "代扣成功，延期异常，请尝试手动延期");
        }
        return vo;
    }

    @Transactional
    WithholdResultVO withhold(String itemId, String delayAmt, PayTypesEnum payTypeEnum) {
        boolean isDelay = StringUtils.isNotBlank(delayAmt);// 判断为还款还是延期
        WithholdResultVO vo = new WithholdResultVO();
        RepayPlanItem item = repayPlanItemManager.getById(itemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item
                .getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId);
            return vo;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId, currTerm, item.getThisTerm());
            return vo;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            vo.setUnsolved(true);
            return vo;
        }
        if (repayPlanItemManager.processing(item.getId()) == 0) {
            logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
            return vo;
        }
        CustUser user = custUserManager.getById(item.getUserId());
        WithholdOP param = getWithholdOP(item, user);
        param.setBindId(user.getBindId() == null ? user.getProtocolNo() : user.getBindId());
        // 延期金额不为空则为延期代扣，否则是还款代扣
        Integer payType = PayTypeEnum.SETTLEMENT.getId();
        if (isDelay) {
/*            param.setTxnAmt(new BigDecimal(delayAmt).multiply(BigDecimal.valueOf(100)).toString());
            payType = PayTypeEnum.DELAY.getId();

            if (PayTypesEnum.BAOFU.getCode().equals(payTypeEnum.getCode())) {
                *//** 宝付代扣 *//*
                vo = baofooWithholdService.withhold(param, payType);
            } else {
                *//** 通联代扣 *//*
                TlAgreementPayResultVO resultVO = tltAgreementPayService.withhold(param, payType);
                vo = new WithholdResultVO();
                vo.setSuccess(resultVO.getSuccess());
                vo.setTransNo(resultVO.getTradeNo());
                vo.setTradeDate(resultVO.getTradeTime());
                vo.setCode(resultVO.getResCode());
                vo.setMsg(resultVO.getResMessage());
                vo.setSuccAmt(resultVO.getAmount());
                vo.setStatus(resultVO.getStatus());
            }

            if (!vo.getSuccess() && isRespUnsolved(vo.getCode())) {
                vo.setUnsolved(true);
                repayPlanItemManager.unfinish(item.getId());
            } else if (!vo.getSuccess() && !isRespUnsolved(vo.getCode())) {
                vo.setUnsolved(false);
                repayPlanItemManager.unfinish(item.getId());
            }*/
        } else {
            // 系统代扣
            if (PayTypesEnum.TONGLIAN.getCode().equals(payTypeEnum.getCode())) {
                /** 通联 */
                TlAgreementPayResultVO resultVO = tltAgreementPayService.withhold(param, payType);
                vo = new WithholdResultVO();
                vo.setSuccess(resultVO.getSuccess());
                vo.setTransNo(resultVO.getTradeNo());
                vo.setTradeDate(resultVO.getTradeTime());
                vo.setCode(resultVO.getResCode());
                vo.setMsg(resultVO.getResMessage());
                vo.setSuccAmt(resultVO.getAmount());
                vo.setStatus(resultVO.getStatus());
            }
            if ("F".equals(vo.getStatus())) {
                logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(), vo.getMsg());
                repayPlanItemManager.unfinish(item.getId());
            }
        }
        return vo;
    }

    private boolean isRespUnsolved(String status) {
        if (StringUtils.isBlank(status)) {
            return false;
        }
        String[] statusArr = {"BF00100", "BF00112", "BF00113", "BF00114", "BF00115", "BF00144", "BF00202"};
        for (String str : statusArr) {
            if (status.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新订单相关信息
     */
    @Transactional
    public boolean updateOrderInfo(String repayPlanItemId, WithholdQueryResultVO withholdQueryResultVO) {
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        Contract contract = contractManager.getByApplyId(item.getApplyId());
        LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());

        WithholdResultVO vo = new WithholdResultVO();
        vo.setTradeDate(withholdQueryResultVO.getOrigTradeDate());
        vo.setSuccAmt(withholdQueryResultVO.getSuccAmt());
        vo.setCouponId(withholdQueryResultVO.getCouponId());
        vo.setPayType(withholdQueryResultVO.getPayType());

        CustUser user = custUserManager.getById(item.getUserId());

        ApplyStatusLifeCycleEnum statusEnum = ApplyStatusLifeCycleEnum.REPAY;

        /** 修改逾期还款记录状态 */
        // ytodo
        int rz = updateOverdue(item.getId(), MoneyUtils.yuan2fen(item.getTotalAmount().toString()));
        // int rz = updateOverdue(item.getId(), vo.getSuccAmt());
        if (rz == 0) {
            logger.error("更新逾期还款表为空或失败,repayPlanItemId={}", item.getId());
        } else {
            statusEnum = ApplyStatusLifeCycleEnum.OVERDUE_REPAY;
        }

        /** 修改还款计划明细 */
        updateRepayPlanItem(item, vo);
        /** 修改还款计划 */
        updateRepayPlan(repayPlan, item, apply, contract, vo);
        /** 最后一期时修改结清状态 */
        if (item.getThisTerm() == item.getTotalTerm()) {
            /** 修改合同状态 */
            updateContract(contract);
            /** 修改贷款申请单状态 */
            // y:定时任务代扣还款
            updateApply(apply, statusEnum);
        }
        /** 保存代扣日志 */
        saveWithholdLog(apply, item, statusEnum, "系统代扣");

        BigDecimal succAmt = new BigDecimal(vo.getSuccAmt()).divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
                BigDecimal.ROUND_HALF_UP);
        /** 保存移动端通知 */
        // saveMessage(item.getUserId(), succAmt);
        /** 发送短信 */
        //String content = String.format(ShortMsgTemplate.REPAY_SUCCESS, succAmt);
        //sendSMS(content, user.getMobile(), user.getId(), apply.getChannelId());
        return true;
    }

    /**
     * 后台手动代扣结算查询
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    public WithholdRepayPlanQueryVO processAdminWithholdQuery(String repayPlanItemId, String actualRepayTime,
                                                              Integer type) {
        WithholdRepayPlanQueryVO vo = new WithholdRepayPlanQueryVO();
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());
        // 逾期天数
        int overdueDays = DateUtils.daysBetween(item.getRepayDate(), DateUtils.parse(actualRepayTime, "yyyy-MM-dd"));
        if (LoanProductEnum.CCD.getId().equals(apply.getProductId())
                || LoanProductEnum.ZJD.getId().equals(apply.getProductId())
                || LoanProductEnum.TYD.getId().equals(apply.getProductId())
                || LoanProductEnum.TFL.getId().equals(apply.getProductId())
                || LoanProductEnum.LYFQ.getId().equals(apply.getProductId())) {
            int hour = DateUtils.getHour(actualRepayTime);
            if (overdueDays >= 0 && hour >= 18)
                overdueDays++;
        }
        //应还本金
        BigDecimal principal = item.getPrincipal();
        //应还利息
        BigDecimal interest = BigDecimal.ZERO;
        //逾期管理费
        BigDecimal overdueFee = BigDecimal.ZERO;
        //逾期罚息
        BigDecimal penalty = BigDecimal.ZERO;
        //提前还款手续费
        BigDecimal prepayFee = BigDecimal.ZERO;
        //中介服务手续费
        BigDecimal servFee = item.getServFee();
        //减免费用
        BigDecimal deduction = item.getDeduction();
        //应还本息（应还本金+应还利息+逾期管理费+提前还款手续费+罚息-减免费用）
        BigDecimal totalAmount = BigDecimal.ZERO;
        int repayUnit = apply.getRepayUnit() == null ? 0 : apply.getRepayUnit().intValue();
        if (overdueDays > 0) {
            // 当前利息
            interest = RepayPlanHelper.getCurrInterest(apply.getRepayMethod(), apply.getApproveAmt(),
                    apply.getApproveTerm(), item.getTotalTerm(), item.getThisTerm(), apply.getActualRate(), overdueDays,
                    repayUnit, apply.getPayChannel(), apply.getServFee());
            // 逾期管理费
            // overdueFee = CostUtils.calOverFee(apply.getOverdueFee(),
            // overdueDays, apply.getApproveAmt());
            // if (overdueFee.compareTo(principal.multiply(new BigDecimal(0.5)))
            // >= 0) {
            // overdueFee = principal.multiply(new BigDecimal(0.5));
            // }
            // 逾期管理费 只收3天
            overdueFee = CostUtils.calOverFee(apply.getOverdueFee(), overdueDays > 3 ? 3 : overdueDays,
                    apply.getApproveAmt());
            // 罚息
            penalty = CostUtils.calPenalty(apply.getOverdueRate(), apply.getApproveAmt(), overdueDays);
            // 应还本息 = 应还本金+应还利息+逾期管理费+罚息+提前还款手续费-减免费用
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        } else {
            /*interest = RepayPlanHelper.getCurrInterest(apply.getRepayMethod(), apply.getApproveAmt(),
                    apply.getApproveTerm(), item.getTotalTerm(), item.getThisTerm(), apply.getActualRate(), 0,
                    repayUnit, apply.getPayChannel(), apply.getServFee());*/
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        }
        if (type == 3 || type == 4) {
            List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(item.getApplyId());
            for (RepayPlanItem detail : itemList) {
                if (ApplyStatusEnum.FINISHED.getValue().equals(detail.getStatus()))
                    continue;
                if (detail.getThisTerm() > item.getThisTerm()) {
                    principal = principal.add(detail.getPrincipal());
                    interest = interest.add(detail.getInterest());
                    servFee = servFee.add(detail.getServFee());
                    overdueFee = overdueFee.add(detail.getOverdueFee());
                    penalty = penalty.add(detail.getPenalty());
                    deduction = deduction.add(detail.getDeduction());
                }
            }
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        }
        BigDecimal currActualRepayAmt = item.getActualRepayAmt() == null ? BigDecimal.ZERO : item.getActualRepayAmt();
        vo.setTotalAmount(totalAmount);
        vo.setCurrActualRepayAmt(currActualRepayAmt);
        vo.setCurrRepayAmt(totalAmount.subtract(currActualRepayAmt));
        vo.setPrincipal(principal);
        vo.setInterest(interest);
        vo.setOverdueFee(overdueFee);
        vo.setPenalty(penalty);
        vo.setDeduction(deduction);

        vo.setType(type);
        vo.setRepayPlanItemId(repayPlanItemId);
        vo.setOverdueDays(overdueDays);
        return vo;
    }


    /**
     * 后台手动代扣结算查询
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    public WithholdRepayPlanQueryVO processAdminWithholdQueryBySuniu(String repayPlanItemId, String actualRepayTime,
                                                                     Integer type) {
        WithholdRepayPlanQueryVO vo = new WithholdRepayPlanQueryVO();
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());
        // 逾期天数
        int overdueDays = DateUtils.daysBetween(item.getRepayDate(), DateUtils.parse(actualRepayTime, "yyyy-MM-dd"));
        if (LoanProductEnum.CCD.getId().equals(apply.getProductId())
                || LoanProductEnum.ZJD.getId().equals(apply.getProductId())
                || LoanProductEnum.TYD.getId().equals(apply.getProductId())
                || LoanProductEnum.TFL.getId().equals(apply.getProductId())
                || LoanProductEnum.LYFQ.getId().equals(apply.getProductId())) {
            int hour = DateUtils.getHour(actualRepayTime);
            if (overdueDays >= 0 && hour >= 18)
                overdueDays++;
        }
        //应还本金
        BigDecimal principal = item.getPrincipal();
        //应还利息
        BigDecimal interest = item.getInterest();
        //逾期管理费
        BigDecimal overdueFee = BigDecimal.ZERO;
        //逾期罚息
        BigDecimal penalty = BigDecimal.ZERO;
        //提前还款手续费
        BigDecimal prepayFee = BigDecimal.ZERO;
        //中介服务手续费
        BigDecimal servFee = item.getServFee();
        //减免费用
        BigDecimal deduction = item.getDeduction();
        //应还本息（应还本金+应还利息+逾期管理费+提前还款手续费+罚息-减免费用）
        BigDecimal totalAmount = BigDecimal.ZERO;
        int repayUnit = apply.getRepayUnit() == null ? 0 : apply.getRepayUnit().intValue();
        if (overdueDays > 0) {

            // 逾期管理费 只收10天
            overdueFee = CostUtils.calOverFee(apply.getOverdueFee(), overdueDays > 10 ? 10 : overdueDays,
                    apply.getApproveAmt());
//            // 罚息
//            penalty = CostUtils.calPenalty(apply.getOverdueRate(), apply.getApproveAmt(), overdueDays);
            // 应还本息 = 应还本金+应还利息+逾期管理费+罚息+提前还款手续费-减免费用
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        } else {
            /*interest = RepayPlanHelper.getCurrInterest(apply.getRepayMethod(), apply.getApproveAmt(),
                    apply.getApproveTerm(), item.getTotalTerm(), item.getThisTerm(), apply.getActualRate(), 0,
                    repayUnit, apply.getPayChannel(), apply.getServFee());*/
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        }
        if (type == 3 || type == 4) {
            List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(item.getApplyId());
            for (RepayPlanItem detail : itemList) {
                if (ApplyStatusEnum.FINISHED.getValue().equals(detail.getStatus()))
                    continue;
                if (detail.getThisTerm() > item.getThisTerm()) {
                    principal = principal.add(detail.getPrincipal());
                    interest = interest.add(detail.getInterest());
                    servFee = servFee.add(detail.getServFee());
                    overdueFee = overdueFee.add(detail.getOverdueFee());
                    penalty = penalty.add(detail.getPenalty());
                    deduction = deduction.add(detail.getDeduction());
                }
            }
            totalAmount = CostUtils.calTotalAmount(principal, interest, overdueFee, penalty, prepayFee, servFee,
                    deduction);
        }
        BigDecimal currActualRepayAmt = item.getActualRepayAmt() == null ? BigDecimal.ZERO : item.getActualRepayAmt();
        vo.setTotalAmount(totalAmount);
        vo.setCurrActualRepayAmt(currActualRepayAmt);
        vo.setCurrRepayAmt(totalAmount.subtract(currActualRepayAmt));
        vo.setPrincipal(principal);
        vo.setInterest(interest);
        vo.setOverdueFee(overdueFee);
        vo.setPenalty(penalty);
        vo.setDeduction(deduction);

        vo.setType(type);
        vo.setRepayPlanItemId(repayPlanItemId);
        vo.setOverdueDays(overdueDays);
        return vo;
    }


    /**
     * 后台手动代扣结算
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param prepayFee       提前还款手续费
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    @Transactional
    public boolean processAdminWithhold(String repayPlanItemId, BigDecimal actualRepayAmt, String actualRepayTime,
                                        String prepayFee, Integer type, String deductionAmt, String repayType, String repayTypeName) {
        // y:后台手动还款
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        if (item == null) {
            logger.error("还款计划明细ID不存在, repayPlanItemId = " + repayPlanItemId);
            throw new RuntimeException("还款计划明细ID不存在, repayPlanItemId = " + repayPlanItemId);
        }
        if (item.getStatus() == 1) {
            logger.error("还款计划明细的状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
            throw new RuntimeException(
                    "还款计划明细状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItemId, currTerm,
                    item.getThisTerm());
            throw new RuntimeException("还款期数错误, itemId = " + repayPlanItemId + " ,currTerm =" + currTerm
                    + " ,thisTerm =" + item.getThisTerm());
        }
        // code y0615 统计已经提交支付，正在处理中的订单，如果有，就不能手动还款
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("还款数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            throw new OrderProcessingException("99", "订单正在处理中，请稍后再试");
        }
        LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());

        // 取消借款发送通知到p2p
        if (type == 4 && !borrowInfoService.cancelLoanNotify(item.getApplyId())) {
            throw new OrderProcessingException("99", "请求平台取消借款接口失败，请联系平台技术人员");
        }

        // 当前实际已还
        BigDecimal currActualRepayAmt = item.getActualRepayAmt() == null ? new BigDecimal(0) : item.getActualRepayAmt();
        currActualRepayAmt = currActualRepayAmt.add(actualRepayAmt);
        boolean isSuccess = false;
        if (type == 1 || type == 3 || type == 4) {
            WithholdRepayPlanQueryVO vo = processAdminWithholdQuery(repayPlanItemId, actualRepayTime, type);

            /** 修改还款明细 **/
            BigDecimal zero = BigDecimal.ZERO;
            List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(item.getApplyId());
            for (RepayPlanItem d : itemList) {
                if (ApplyStatusEnum.FINISHED.getValue().equals(d.getStatus()))
                    continue;
                if (d.getThisTerm() == item.getThisTerm()) {
                    RepayPlanHelper.setOverRepayPlanItem(d,
                            vo.getTotalAmount().add(new BigDecimal(prepayFee)).subtract(new BigDecimal(deductionAmt)),
                            vo.getPrincipal(), vo.getInterest(), vo.getOverdueFee(), vo.getPenalty(),
                            new BigDecimal(prepayFee), vo.getDeduction().add(new BigDecimal(deductionAmt)),
                            currActualRepayAmt, actualRepayTime, repayType);
                    // 聚宝钱包一次性还款结清，实还金额<应还金额时做减免
                    if (type == 1 && LoanProductEnum.XJD.getId().equals(apply.getProductId())
                            && currActualRepayAmt.compareTo(d.getTotalAmount()) < 0) {
                        BigDecimal subAmt = d.getTotalAmount().subtract(currActualRepayAmt);
                        d.setDeduction(d.getDeduction().add(subAmt));
                        d.setTotalAmount(d.getTotalAmount().subtract(subAmt));
                    }
                    repayPlanItemManager.update(d);
                }
                if ((type == 3 || type == 4) && d.getThisTerm() > item.getThisTerm()) {
                    RepayPlanHelper.setOverRepayPlanItem(d, zero, zero, zero, zero, zero, zero, zero, zero,
                            actualRepayTime, repayType);
                    repayPlanItemManager.update(d);
                    // 提前结清时修改逾期
                    int overdueDays = DateUtils.daysBetween(d.getRepayDate(),
                            DateUtils.parse(actualRepayTime, "yyyy-MM-dd"));
                    if (overdueDays > 0) {
                        /** 修改逾期还款记录状态 */
                        updateOverdue(d.getId(), String.valueOf(zero), actualRepayTime, overdueDays);
                    } else {
                        overdueManager.deleteTruely(d.getId());
                    }
                }
            }
            /** 汇总还款计划明细，更新还款计划 */
            repayPlan = RepayPlanHelper.summaryLoanRepayPlan(repayPlan, itemList);
            if (type == 4) { // 取消借款是更新还款计划总表借款状态为1
                repayPlan.setLoanStatus(1);
            }
            loanRepayPlanManager.update(repayPlan);

            ApplyStatusLifeCycleEnum statusEnum = ApplyStatusLifeCycleEnum.REPAY;
            if (vo.getOverdueDays() > 0) {
                statusEnum = ApplyStatusLifeCycleEnum.OVERDUE_REPAY;
                /** 修改逾期还款记录状态 */
                updateOverdue(item.getId(), String.valueOf(currActualRepayAmt), actualRepayTime, vo.getOverdueDays());
            } else {
                overdueManager.deleteTruely(item.getId());
            }
            // 结清后修改合同和订单
            if (repayPlan.getStatus().equals(Global.REPAY_PLAN_STATUS_OVER)) {
                Contract contract = contractManager.getByApplyId(item.getApplyId());
                /** 修改合同状态 */
                updateContract(contract);
                /** 修改贷款申请单状态 */
                updateApply(apply, statusEnum);
            }
            isSuccess = true;
        } else if (type == 2) {
            Date repayTime = DateUtils.parse(actualRepayTime, "yyyy-MM-dd HH:mm:ss");
            String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
            item.setActualRepayAmt(currActualRepayAmt);
            item.setActualRepayDate(repayDate);
            item.setActualRepayTime(repayTime);
            int rz = repayPlanItemManager.update(item);
            if (rz > 0)
                isSuccess = true;
        }
        // 取消借款时，收回购物券
        if (type == 4) {
            custCouponService.cancelCoupon(item.getApplyId());
        }
        saveAdminRepayLog(isSuccess, item.getUserId(), item.getApplyId(), item.getContNo(), item.getId(),
                actualRepayTime, MoneyUtils.yuan2fen(String.valueOf(actualRepayAmt)), repayType, repayTypeName);

        rongPointCutService.settlementManPayPoint(repayPlanItemId, true);// 用作Rong360订单还款时，切面通知的切入点标记
        return isSuccess;
    }


    /**
     * 速牛后台手动代扣结算
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param prepayFee       提前还款手续费
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    @Transactional
    public boolean processAdminWithholdBySuniu(String repayPlanItemId, BigDecimal actualRepayAmt, String actualRepayTime,
                                               String prepayFee, Integer type, String deductionAmt, String repayType, String repayTypeName) {
        // y:后台手动还款
        RepayPlanItem item = repayPlanItemManager.get(repayPlanItemId);
        if (item == null) {
            logger.error("还款计划明细ID不存在, repayPlanItemId = " + repayPlanItemId);
            throw new RuntimeException("还款计划明细ID不存在, repayPlanItemId = " + repayPlanItemId);
        }
        if (item.getStatus() == 1) {
            logger.error("还款计划明细的状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
            throw new RuntimeException(
                    "还款计划明细状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItemId, currTerm,
                    item.getThisTerm());
            throw new RuntimeException("还款期数错误, itemId = " + repayPlanItemId + " ,currTerm =" + currTerm
                    + " ,thisTerm =" + item.getThisTerm());
        }
        // code y0615 统计已经提交支付，正在处理中的订单，如果有，就不能手动还款
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("还款数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            throw new OrderProcessingException("99", "订单正在处理中，请稍后再试");
        }
        LoanApply apply = loanApplyManager.getLoanApplyById(item.getApplyId());

        // 取消借款发送通知到p2p
        if (type == 4 && !borrowInfoService.cancelLoanNotify(item.getApplyId())) {
            throw new OrderProcessingException("99", "请求平台取消借款接口失败，请联系平台技术人员");
        }

        // 当前实际已还
        BigDecimal currActualRepayAmt = item.getActualRepayAmt() == null ? new BigDecimal(0) : item.getActualRepayAmt();
        currActualRepayAmt = currActualRepayAmt.add(actualRepayAmt);
        boolean isSuccess = false;
        if (type == 1 || type == 3 || type == 4) {
            WithholdRepayPlanQueryVO vo = processAdminWithholdQueryBySuniu(repayPlanItemId, actualRepayTime, type);

            /** 修改还款明细 **/
            BigDecimal zero = BigDecimal.ZERO;
            List<RepayPlanItem> itemList = repayPlanItemManager.getByApplyId(item.getApplyId());
            for (RepayPlanItem d : itemList) {
                if (ApplyStatusEnum.FINISHED.getValue().equals(d.getStatus()))
                    continue;
                if (d.getThisTerm() == item.getThisTerm()) {
                    RepayPlanHelper.setOverRepayPlanItem(d,
                            vo.getTotalAmount().add(new BigDecimal(prepayFee)).subtract(new BigDecimal(deductionAmt)),
                            vo.getPrincipal(), vo.getInterest(), vo.getOverdueFee(), vo.getPenalty(),
                            new BigDecimal(prepayFee), vo.getDeduction().add(new BigDecimal(deductionAmt)),
                            currActualRepayAmt, actualRepayTime, repayType);
                    // 聚宝钱包一次性还款结清，实还金额<应还金额时做减免
                    if (type == 1 && LoanProductEnum.XJD.getId().equals(apply.getProductId())
                            && currActualRepayAmt.compareTo(d.getTotalAmount()) < 0) {
                        BigDecimal subAmt = d.getTotalAmount().subtract(currActualRepayAmt);
                        d.setDeduction(d.getDeduction().add(subAmt));
                        d.setTotalAmount(d.getTotalAmount().subtract(subAmt));
                    }
                    repayPlanItemManager.update(d);
                }
                if ((type == 3 || type == 4) && d.getThisTerm() > item.getThisTerm()) {
                    RepayPlanHelper.setOverRepayPlanItem(d, zero, zero, zero, zero, zero, zero, zero, zero,
                            actualRepayTime, repayType);
                    repayPlanItemManager.update(d);
                    // 提前结清时修改逾期
                    int overdueDays = DateUtils.daysBetween(d.getRepayDate(),
                            DateUtils.parse(actualRepayTime, "yyyy-MM-dd"));
                    if (overdueDays > 0) {
                        /** 修改逾期还款记录状态 */
                        updateOverdue(d.getId(), String.valueOf(zero), actualRepayTime, overdueDays);
                    } else {
                        overdueManager.deleteTruely(d.getId());
                    }
                }
            }
            /** 汇总还款计划明细，更新还款计划 */
            repayPlan = RepayPlanHelper.summaryLoanRepayPlan(repayPlan, itemList);
            if (type == 4) { // 取消借款是更新还款计划总表借款状态为1
                repayPlan.setLoanStatus(1);
            }
            loanRepayPlanManager.update(repayPlan);

            ApplyStatusLifeCycleEnum statusEnum = ApplyStatusLifeCycleEnum.REPAY;
            if (vo.getOverdueDays() > 0) {
                statusEnum = ApplyStatusLifeCycleEnum.OVERDUE_REPAY;
                /** 修改逾期还款记录状态 */
                updateOverdue(item.getId(), String.valueOf(currActualRepayAmt), actualRepayTime, vo.getOverdueDays());
            } else {
                overdueManager.deleteTruely(item.getId());
            }
            // 结清后修改合同和订单
            if (repayPlan.getStatus().equals(Global.REPAY_PLAN_STATUS_OVER)) {
                Contract contract = contractManager.getByApplyId(item.getApplyId());
                /** 修改合同状态 */
                updateContract(contract);
                /** 修改贷款申请单状态 */
                updateApply(apply, statusEnum);
            }
            isSuccess = true;
        } else if (type == 2) {
            Date repayTime = DateUtils.parse(actualRepayTime, "yyyy-MM-dd HH:mm:ss");
            String repayDate = DateUtils.formatDate(repayTime, "yyyy-MM-dd");
            item.setActualRepayAmt(currActualRepayAmt);
            item.setActualRepayDate(repayDate);
            item.setActualRepayTime(repayTime);
            int rz = repayPlanItemManager.update(item);
            if (rz > 0)
                isSuccess = true;
        }
        // 取消借款时，收回购物券
        if (type == 4) {
            custCouponService.cancelCoupon(item.getApplyId());
        }
        saveAdminRepayLog(isSuccess, item.getUserId(), item.getApplyId(), item.getContNo(), item.getId(),
                actualRepayTime, MoneyUtils.yuan2fen(String.valueOf(actualRepayAmt)), repayType, repayTypeName);

        rongPointCutService.settlementManPayPoint(repayPlanItemId, true);// 用作Rong360订单还款时，切面通知的切入点标记
        return isSuccess;
    }

    /**
     * p2p回调代扣结算
     * 还款计划明细Id
     */
    @Transactional
    public boolean processP2pWithhold(String outsidSerialNo, BigDecimal repayAmt, Integer repayTerm, String repayDate) {
        BorrowInfo borrwoInfo = borrowInfoManager.getByOutsideSerialNo(outsidSerialNo);
        if (borrwoInfo == null) {
            logger.error("标的不存在, outsidSerialNo = " + outsidSerialNo);
            throw new RuntimeException("标的不存在, outsidSerialNo = " + outsidSerialNo);
        }
        RepayPlanItem item = repayPlanItemManager.getUnoverItemByTerm(borrwoInfo.getApplyId(), repayTerm);
        if (item == null) {
            logger.error("还款计划明细, applyId = " + borrwoInfo.getApplyId() + ",repayTerm=" + repayTerm);
            throw new RuntimeException("还款计划明细, applyId = " + borrwoInfo.getApplyId() + ",repayTerm=" + repayTerm);
        }
        String repayPlanItemId = item.getId();
        if (item.getStatus() == 1) {
            logger.error("还款计划明细的状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
            throw new RuntimeException(
                    "还款计划明细状态不正确, repayPlanItemId = " + repayPlanItemId + " ,status =" + item.getStatus());
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItemId, currTerm,
                    item.getThisTerm());
            throw new RuntimeException("还款期数错误, itemId = " + repayPlanItemId + " ,currTerm =" + currTerm
                    + " ,thisTerm =" + item.getThisTerm());
        }
        // repayAmt 还款金额,单位分
        String repayAmtStr = MoneyUtils.yuan2fen(String.valueOf(repayAmt));

        WithholdQueryResultVO retVo = new WithholdQueryResultVO();
        retVo.setSuccAmt(repayAmtStr);
        retVo.setOrigTradeDate(repayDate);
        boolean result = updateOrderInfo(repayPlanItemId, retVo);
        rongPointCutService.settlementPoint(repayPlanItemId, true);// 用作Rong360订单还款时，切面通知的切入点标记
        return result;

    }

    /**
     * 提前一天还款通知
     */
    @Transactional
    public TaskResult repayNotice() {
        logger.info("开始执行还款通知任务。");
        long starTime = System.currentTimeMillis();
        try {
            /** 查询第二天到期的还款计划明细 */
            List<RepayPlanItemDetail> itemList = repayPlanItemManager.noticeRepayList();
            if (CollectionUtils.isEmpty(itemList)) {
                long endTime = System.currentTimeMillis();
                logger.info("执行还款通知任务结束，暂无数据。执行耗时{}", endTime - starTime);
                return new TaskResult(0, 0);
            }
            List<Message> list = new ArrayList<>();
            for (RepayPlanItemDetail item : itemList) {
                // 组装通知
                Message message = getMessageDetail(item);
                list.add(message);
                // 发送短信
                String date = DateUtils.formatDate(item.getRepayDate(), DateUtils.FORMAT_SHORT);
                String content = String.format(ShortMsgTemplate.REPAY_NOTICE, date, item.getTotalAmount());
                try {
                    SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
                    sendShortMsgOP.setMessage(content);
                    sendShortMsgOP.setMobile(item.getMobile());
                    sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
                    sendShortMsgOP.setUserId(item.gettUserId());
                    sendShortMsgOP.setChannelId(item.getChannelId());
                    sendShortMsgOP.setMsgType(MsgTypeEnum.REPAY_NOTICE.getValue());
                    sendShortMsgOP.setProductId(item.getProductId());
                    sendShortMsgOP.setRemark(item.getId());
                    shortMsgService.sendMsg(sendShortMsgOP);
                } catch (Exception e) {
                    logger.error("发送还款通知短信失败，userId=" + item.gettUserId() + ", mobile=" + item.getMobile(), e);
                }

            }
            // 保存通知
            messageManager.insertBatch(list);
        } catch (Exception e) {
            logger.error("还款通知失败", e);
            return new TaskResult(0, 1);
        }

        long endTime = System.currentTimeMillis();
        logger.info("执行还款通知任务结束,执行耗时{}", endTime - starTime);
        return new TaskResult(1, 0);
    }

    /**
     * 更新还款明细
     *
     * @param source
     * @return
     */
    private int updateRepayPlanItem(RepayPlanItem source, WithholdResultVO vo) {
        RepayPlanItem repayPlanItem = new RepayPlanItem();
        repayPlanItem.setId(source.getId());
        repayPlanItem.setPayedPrincipal(source.getUnpayPrincipal());
        repayPlanItem.setUnpayPrincipal(BigDecimal.ZERO);
        repayPlanItem.setPayedInterest(source.getUnpayInterest());
        repayPlanItem.setUnpayInterest(BigDecimal.ZERO);
        if (vo.getTradeDate() == null || "".equals(vo.getTradeDate())) {
            vo.setTradeDate(DateUtils.getDate(DateUtils.FORMAT_INT_DATE));
        }
        Date tradeDate = DateUtils.parseDate(vo.getTradeDate());
        repayPlanItem.setActualRepayTime(tradeDate);
        if (repayPlanItem.getActualRepayTime() == null) {
            repayPlanItem.setActualRepayTime(DateUtils.parse(DateUtils.getDateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        repayPlanItem.setActualRepayDate(DateUtils.getDate());
        BigDecimal succAmt = new BigDecimal(vo.getSuccAmt()).divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
                BigDecimal.ROUND_HALF_UP);
        // 本次支付成功金额 + 已支付金额
        repayPlanItem.setActualRepayAmt(
                succAmt.add(source.getActualRepayAmt() == null ? BigDecimal.ZERO : source.getActualRepayAmt()));
        // repayPlanItem.setActualRepayAmt(succAmt);
        repayPlanItem.setStatus(ApplyStatusEnum.FINISHED.getValue());
        repayPlanItem.setRepayType(vo.getPayType());
        repayPlanItem.preUpdate();
        // ytodo 0317
        repayPlanItem.setDeduction(source.getDeduction());
        repayPlanItem.setTotalAmount(source.getTotalAmount());

        if (StringUtils.isNotBlank(vo.getCouponId())) {
            // 设置减免金额
            String[] couponIds = vo.getCouponId().split(",");
            BigDecimal deductionAmt = new BigDecimal(custCouponManager.sumCouponAmtByIds(Arrays.asList(couponIds)));
            repayPlanItem.setDeduction(CostUtils.add(source.getDeduction(), deductionAmt));
            repayPlanItem.setTotalAmount(CostUtils.sub(source.getTotalAmount(), deductionAmt));
            repayPlanItem.setCouponId(vo.getCouponId());// 购物券ID
        }

        // ytodo 0317 特别设置减免金额
        BigDecimal actualRepayAmt = source.getActualRepayAmt() == null ? BigDecimal.ZERO : source.getActualRepayAmt();
        BigDecimal deductionAmt = source.getTotalAmount().subtract(actualRepayAmt).subtract(succAmt);
        if (deductionAmt.compareTo(BigDecimal.ZERO) > 0) {
            repayPlanItem.setDeduction(CostUtils.add(repayPlanItem.getDeduction(), deductionAmt));
            repayPlanItem.setTotalAmount(CostUtils.sub(repayPlanItem.getTotalAmount(), deductionAmt));
        }
        return repayPlanItemManager.updatePayResult(repayPlanItem);
    }

    /**
     * 更新还款计划
     */
    private int updateRepayPlan(LoanRepayPlan source, RepayPlanItem item, LoanApply loanApply, Contract contract,
                                WithholdResultVO vo) {
        LoanRepayPlan repayPlan = new LoanRepayPlan();
        repayPlan.setId(source.getId());
        repayPlan.setApplyId(source.getApplyId());
        repayPlan.setPayedTerm(source.getPayedTerm() + 1);
        repayPlan.setUnpayTerm(source.getUnpayTerm() - 1);
        repayPlan.setPayedPrincipal(source.getPayedPrincipal().add(item.getPrincipal()));
        repayPlan.setUnpayPrincipal(source.getUnpayPrincipal().subtract(item.getPrincipal()));
        repayPlan.setPayedInterest(source.getPayedInterest().add(item.getInterest()));
        repayPlan.setUnpayInterest(source.getUnpayInterest().subtract(item.getInterest()));
        Date tradeDate = DateUtils.parseDate(vo.getTradeDate());
        repayPlan.setCurRealRepayDate(tradeDate);
        // 最后一期时更新为完结
        if (item.getThisTerm() >= item.getTotalTerm()) {
            repayPlan.setStatus(ApplyStatusEnum.FINISHED.getValue());
        } else {
            repayPlan.setCurrentTerm(source.getCurrentTerm() + 1);
            repayPlan
                    .setNextRepayDate(RepayPlanHelper.getRepayDateStr(loanApply, contract, repayPlan.getCurrentTerm()));
        }
        if (StringUtils.isNotBlank(vo.getCouponId())) {
            // 设置减免金额
            String[] couponIds = vo.getCouponId().split(",");
            BigDecimal deductionAmt = new BigDecimal(custCouponManager.sumCouponAmtByIds(Arrays.asList(couponIds)));
            repayPlan.setDeduction(CostUtils.add(source.getDeduction(), deductionAmt));
            repayPlan.setTotalAmount(CostUtils.sub(source.getTotalAmount(), deductionAmt));
            // 更新购物券使用状态
            CustCoupon custCoupon = new CustCoupon();
            custCoupon.setStatus(1);
            custCouponManager.updateCouponStatus(Arrays.asList(couponIds), custCoupon);
        }
        repayPlan.preUpdate();
        return loanRepayPlanManager.updatePayResult(repayPlan);
    }

    /**
     * 更新合同
     *
     * @param source
     * @return
     */
    private int updateContract(Contract source) {
        Contract contract = new Contract();
        contract.setApplyId(source.getApplyId());
        return contractManager.OverContract(contract);
    }

    /**
     * 更新贷款申请单
     *
     * @param source
     * @return
     */
    private int updateApply(LoanApply source, ApplyStatusLifeCycleEnum value) {
        LoanApply loanApply = new LoanApply();
        loanApply.setId(source.getId());
        loanApply.setStage(value.getStage());
        loanApply.setStatus(value.getValue());
        loanApply.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
        loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        loanApply.setUpdateTime(new Date());
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    /**
     * 保存操作记录
     *
     * @param source
     */
    private void saveWithholdLog(LoanApply source, RepayPlanItem item, ApplyStatusLifeCycleEnum value, String result) {
        logger.info("代扣日志：{},{},{},{},{},{}", source.getId(), item.getId(), source.getStage(), source.getStatus(),
                value.getStage(), value.getValue());
        /*
         * try { OperationLog operationLog = new OperationLog();
         * operationLog.preInsert(); operationLog.setUserId(source.getUserId());
         * operationLog.setPreviousStage(source.getStage());
         * operationLog.setPreviousStatus(source.getStatus());
         * operationLog.setApplyId(source.getId());
         * operationLog.setStatus(value.getValue());
         * operationLog.setStage(value.getStage());
         * operationLog.setSource(Global.SOURCE_SYSTEM);
         * operationLog.setTime(new Date());
         * operationLog.setOperatorId(Global.DEFAULT_OPERATOR_ID);
         * operationLog.setOperatorName(Global.DEFAULT_OPERATOR_NAME);
         * operationLog.setRemark(result);
         * operationLogManager.saveOperationLog(operationLog); } catch
         * (Exception e) { logger.error("保存代扣操作日志失败，applyId = " +
         * source.getId(), e); }
         */
    }

    /**
     * 根据id查询用户列表
     *
     * @param itemList
     * @return
     */
    private List<LoanRepayPlan> findRepayPlanList(List<RepayPlanItem> itemList) {
        List<String> idList = new ArrayList<>();
        for (RepayPlanItem item : itemList) {
            idList.add(item.getApplyId());
        }
        return loanRepayPlanManager.getByApplyIdList(idList);
    }

    /**
     * 根据id查询用户列表
     *
     * @param itemList
     * @return
     */
    private List<CustUser> findCustUserList(List<RepayPlanItem> itemList) {
        List<String> idList = new ArrayList<>();
        for (RepayPlanItem item : itemList) {
            idList.add(item.getUserId());
        }
        return custUserManager.findByIdList(idList);
    }

    /**
     * 根据id查询用户列表
     *
     * @param itemList
     * @return
     */
    private List<LoanApply> findApplyList(List<RepayPlanItem> itemList) {
        List<String> idList = new ArrayList<>();
        for (RepayPlanItem item : itemList) {
            idList.add(item.getApplyId());
        }
        return loanApplyManager.findByIdList(idList);
    }

    /**
     * 根据id查询合同列表
     *
     * @param itemList
     * @return
     */
    private List<Contract> findContractList(List<RepayPlanItem> itemList) {
        List<String> idList = new ArrayList<>();
        for (RepayPlanItem item : itemList) {
            idList.add(item.getContNo());
        }
        return contractManager.findByIdList(idList);
    }

    /**
     * 获取申请单
     *
     * @param item
     * @param applyList
     * @return
     */
    private LoanApply getApply(RepayPlanItem item, List<LoanApply> applyList) {
        for (LoanApply apply : applyList) {
            if (StringUtils.equals(item.getApplyId(), apply.getId())) {
                return apply;
            }
        }
        return null;
    }

    /**
     * 获取合同
     *
     * @param item
     * @param contractList
     * @return
     */
    private Contract getContract(RepayPlanItem item, List<Contract> contractList) {
        for (Contract contract : contractList) {
            if (StringUtils.equals(item.getContNo(), contract.getId())) {
                return contract;
            }
        }
        return null;
    }

    /**
     * 获取还款计划
     *
     * @param item
     * @param repayPlanList
     * @return
     */
    private LoanRepayPlan getRepayPlan(RepayPlanItem item, List<LoanRepayPlan> repayPlanList) {
        for (LoanRepayPlan repayPlan : repayPlanList) {
            if (StringUtils.equals(item.getApplyId(), repayPlan.getApplyId())) {
                return repayPlan;
            }
        }
        return null;
    }

    /**
     * 获取用户
     *
     * @param item
     * @param userList
     * @return
     */
    private CustUser getUser(RepayPlanItem item, List<CustUser> userList) {
        for (CustUser user : userList) {
            if (StringUtils.equals(item.getUserId(), user.getId())) {
                return user;
            }
        }
        return null;
    }

    /**
     * 组装参数
     *
     * @param item
     * @param custUser
     * @return
     */
    private WithholdOP getWithholdOP(RepayPlanItem item, CustUser custUser) {
        WithholdOP param = new WithholdOP();
        param.setUserId(item.getUserId());
        param.setRealName(item.getUserName());
        param.setIdNo(custUser.getIdNo());
        if (StringUtils.isNotBlank(custUser.getBankMobile())) {
            param.setMobile(custUser.getBankMobile());
        } else {
            param.setMobile(custUser.getMobile());
        }
        param.setBankCode(custUser.getBankCode());
        param.setCardNo(custUser.getCardNo());
        BigDecimal actualRepayAmt = item.getActualRepayAmt() == null ? BigDecimal.ZERO : item.getActualRepayAmt();
        int value = item.getTotalAmount().subtract(actualRepayAmt).multiply(BigDecimal.valueOf(100)).intValue();
        param.setTxnAmt(String.valueOf(value));
        param.setApplyId(item.getApplyId());
        param.setContNo(item.getContNo());
        param.setRepayPlanItemId(item.getId());
        param.setBindId(custUser.getBindId());
        return param;
    }

    private WithholdOP getWithholdOPTemp(RepayPlanItem item, LoanRepayPlan repayPlan, CustUser custUser) {
        LoanApply loanApply = loanApplyManager.getLoanApplyById(item.getApplyId());
        WithholdOP param = new WithholdOP();
        param.setUserId(item.getUserId());
        param.setRealName(item.getUserName());
        param.setIdNo(custUser.getIdNo());
        if (StringUtils.isNotBlank(custUser.getBankMobile())) {
            param.setMobile(custUser.getBankMobile());
        } else {
            param.setMobile(custUser.getMobile());
        }
        param.setBankCode(custUser.getBankCode());
        param.setCardNo(custUser.getCardNo());
        BigDecimal actualRepayAmt = item.getActualRepayAmt() == null ? BigDecimal.ZERO : item.getActualRepayAmt();
        // 扣除金额=本金*（1-服务费率）-实际已还+利息
        BigDecimal principalRate = new BigDecimal(1).subtract(loanApply.getServFeeRate());
        BigDecimal principal = item.getPrincipal().multiply(principalRate).setScale(0, BigDecimal.ROUND_HALF_UP);
        // 计算利息
        int days = DateUtils.daysBetween(item.getStartDate(), new Date()) + 1;
        if (days < 0) {
            days = 0;
        }
        loanApply.setApproveAmt(loanApply.getApproveAmt().multiply(principalRate));
        BigDecimal interest = RepayPlanHelper.getTermInterestTemp(loanApply, repayPlan, days);
        if (interest.compareTo(BigDecimal.ZERO) < 0) {
            interest = BigDecimal.ZERO;
        }

        BigDecimal txAmt = principal.subtract(actualRepayAmt).add(interest).multiply(BigDecimal.valueOf(100));
        param.setTxnAmt(String.valueOf(txAmt));
        param.setApplyId(item.getApplyId());
        param.setContNo(item.getContNo());
        param.setRepayPlanItemId(item.getId());
        return param;
    }

    private void writeInitLog(List<RepayPlanItem> itemList) {
        StringBuffer log = new StringBuffer("本次需要处理的还款明细包括：");
        for (RepayPlanItem item : itemList) {
            log.append("【").append(item.getId()).append("】");
        }
        logger.info(log.toString());
    }

    /**
     * 组装移动端通知
     *
     * @param item
     * @return
     */
    private Message getMessage(RepayPlanItem item, String name, String productId) {
        String date = DateUtils.formatDate(item.getRepayDate(), DateUtils.FORMAT_SHORT);
        String content = String.format(ShortMsgTemplate.REPAY_NOTICE, date, item.getTotalAmount());
        Message message = new Message();
        message.preInsert();
        message.setUserId(item.getUserId());
        message.setTitle(ShortMsgTemplate.REPAY_NOTICE_TITLE);
        message.setContent(content);
        message.setType(Global.CUST_MESSAGE_TYPE_SYS);
        message.setNotifyTime(new Date());
        message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
        message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
        message.setStatus(1);
        message.setDel(0);
        return message;
    }

    /**
     * 组装移动端通知--For 门店
     *
     * @param item
     * @return
     */
    private Message getMessageDetail(RepayPlanItemDetail item) {
        String date = DateUtils.formatDate(item.getRepayDate(), DateUtils.FORMAT_SHORT);
        String content = String.format(ShortMsgTemplate.REPAY_NOTICE, date, item.getTotalAmount());
        Message message = new Message();
        message.preInsert();
        message.setUserId(item.getUserId());
        message.setTitle(ShortMsgTemplate.REPAY_NOTICE_TITLE);
        message.setContent(content);
        message.setType(Global.CUST_MESSAGE_TYPE_SYS);
        message.setNotifyTime(new Date());
        message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
        message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
        message.setStatus(1);
        message.setDel(0);
        return message;
    }

    /**
     * 保存移动端通知
     */
    private void saveMessage(String userId, BigDecimal totalAmount) {
        try {
            String content = String.format(ShortMsgTemplate.REPAY_SUCCESS, totalAmount);
            Message message = new Message();
            message.preInsert();
            message.setUserId(userId);
            message.setTitle(ShortMsgTemplate.REPAY_SUCCESS_TITLE);
            message.setContent(content);
            message.setType(Global.CUST_MESSAGE_TYPE_SYS);
            message.setNotifyTime(new Date());
            message.setNotifyType(Global.CUST_MESSAGE_NOTIFY_TYPE_0);
            message.setViewStatus(Global.CUST_MESSAGE_VIEW_STATUS_0);
            message.setStatus(1);
            message.setDel(0);
            messageManager.insert(message);
        } catch (Exception e) {
            logger.error("发送APP还款成功通知失败,userId = " + userId + ", totalAmount = " + totalAmount, e);
        }
    }

    /**
     * 发送短信
     *
     * @param message
     * @param mobile
     * @param userid
     */
    private void sendSMS(String message, String mobile, String userid, String channelId) {
        try {
            SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
            sendShortMsgOP.setMessage(message);
            sendShortMsgOP.setMobile(mobile);
            sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
            sendShortMsgOP.setUserId(userid);
            sendShortMsgOP.setChannelId(channelId);
            shortMsgService.sendMsg(sendShortMsgOP);
        } catch (Exception e) {
            logger.error("发送还款通知短信失败，userId=" + userid + ", mobile=" + mobile, e);
        }
    }

    /**
     * 拼装理财端通知入参
     *
     * @param vo
     * @param item
     * @return
     */
    private OverdueRepayNoticeVO getOverdueRepayNoticeVO(WithholdResultVO vo, RepayPlanItem item) {
        BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(item.getApplyId());
        OverdueRepayNoticeVO dto = new OverdueRepayNoticeVO();
        dto.setAssetId(borrowInfo.getOutsideSerialNo());
        dto.setAccountId(borrowInfo.getAccountId());
        String tradeDate = DateUtils.formatDateTime(DateUtils.parse(vo.getTradeDate()));
        dto.setRepayDate(tradeDate);
        dto.setOverdueInterest(item.getPenalty().toString());
        dto.setOverdueFee(item.getOverdueFee().toString());
        dto.setReduceFee(item.getDeduction().toString());
        dto.setInterest(item.getInterest().toString());
        dto.setPrincipal(item.getPrincipal().toString());
        return dto;
    }

    private int updateOverdue(String id, String successAmt) {
        Overdue overdue = overdueManager.get(id);
        if (overdue != null) {
            BigDecimal succAmt = new BigDecimal(successAmt).divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
                    BigDecimal.ROUND_HALF_UP);
            overdue.setActualRepayAmt(succAmt);
            overdue.setActualRepayDate(DateUtils.getDate());
            overdue.setActualRepayTime(new Date());
            overdue.setOverdueEndDate(new Date());
            overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
            return overdueManager.update(overdue);
        }
        return 0;
    }

    private int updateOverdue(String id, String actualRepayAmt, String actualRepayDate, int overdueDays) {
        Overdue overdue = overdueManager.get(id);
        if (overdue != null) {
            Date repayTime = DateUtils.parse(actualRepayDate, "yyyy-MM-dd HH:mm:ss");
            overdue.setActualRepayAmt(new BigDecimal(actualRepayAmt));
            overdue.setActualRepayDate(DateUtils.formatDate(repayTime, "yyyy-MM-dd"));
            overdue.setActualRepayTime(repayTime);
            overdue.setOverdueEndDate(repayTime);
            overdue.setStatus(ApplyStatusEnum.FINISHED.getValue());
            overdue.setOverdueDays(overdueDays);
            return overdueManager.update(overdue);
        }
        return 0;
    }

    /**
     * 保存后台线下还款记录
     *
     * @return
     */
    private int saveAdminRepayLog(boolean isSuccess, String userId, String applyId, String contNo,
                                  String repayPlanItemId, String tradeDate, String tradeAmt, String repayType, String repayTypeName) {

        CustUserVO user = custUserService.getCustUserById(userId);

        Date now = new Date();
        RepayLogVO repayLog = new RepayLogVO();
        repayLog.setId(IdGen.uuid());
        repayLog.setNewRecord(true);
        repayLog.setApplyId(applyId);
        repayLog.setContractId(contNo);
        repayLog.setRepayPlanItemId(repayPlanItemId);
        repayLog.setUserId(user.getId());
        repayLog.setUserName(user.getRealName());
        repayLog.setIdNo(user.getIdNo());
        repayLog.setMobile(user.getMobile());
        repayLog.setTxType("MANPAY");
        Date dTradeDate = DateUtils.parse(tradeDate, "yyyy-MM-dd HH:mm:ss");
        repayLog.setTxDate(Long.parseLong(DateUtils.formatDate(dTradeDate, "yyyyMMdd")));
        if (StringUtils.isNotBlank(tradeDate)) {
            repayLog.setTxTime(dTradeDate);
        }
        BigDecimal txAmt = new BigDecimal(tradeAmt).divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE,
                BigDecimal.ROUND_HALF_UP);
        repayLog.setTxAmt(txAmt);
        repayLog.setTxFee(calWithholdFee(txAmt));
        repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
        repayLog.setChlOrderNo("");
        repayLog.setChlName(repayTypeName);
        repayLog.setChlCode(repayType);
        repayLog.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());
        repayLog.setBankCode(user.getBankCode());
        repayLog.setCardNo(user.getCardNo());
        repayLog.setBankName("");
        repayLog.setGoodsName("聚宝贷还款");
        repayLog.setGoodsNum(1);
        repayLog.setPayType(PayTypeEnum.SETTLEMENT.getId());
        repayLog.setSuccAmt(txAmt);
        repayLog.setSuccTime(now);
        if (isSuccess) {
            repayLog.setStatus("S");
            repayLog.setRemark("后台手动结清");
        } else {
            repayLog.setStatus("FAIL");
            repayLog.setRemark("交易失败");
        }
        int rz = repayLogService.save(repayLog);
        return rz;
    }

    /**
     * 计算每笔代扣交易费用 10万及以下，2.00元/笔 10万以上，5.00/笔
     *
     * @param fee 单位元
     * @return
     */
    private BigDecimal calWithholdFee(BigDecimal fee) {
        if (fee.compareTo(new BigDecimal(100000)) > 0) {
            return BigDecimal.valueOf(5);
        }
        return BigDecimal.valueOf(2);
    }

    public static void main(String[] args) {
        System.out.println(
                DateUtils.isBefore(DateUtils.parseYYMMdd("2018-03-10 16:35:35"), DateUtils.parseYYMMdd("2018-03-11")));
    }

    @Override
    public XfAgreementPayResultVO xfWithholdByApplyId(String applyId, String amount, Integer payType) {
        // 先锋业务数据
        /*
         * XfWithholdOP xfWithholdOP = new XfWithholdOP(); XfWithholdResultVO vo
         * = new XfWithholdResultVO(); try { LoanApply loanApply =
         * loanApplyManager.getLoanApplyById(applyId); if (loanApply == null) {
         * logger.error("贷款申请单不存在，applyId = {}", applyId);
         * vo.setCode(ErrInfo.ERROR.getCode()); vo.setMsg("贷款申请单不存在"); return
         * vo; } CustUser user = custUserManager.getById(loanApply.getUserId());
         * xfWithholdOP.setAmount(amount);
         * xfWithholdOP.setAccountNo(user.getCardNo());
         * xfWithholdOP.setAccountName(user.getRealName());
         * xfWithholdOP.setBankId(user.getBankCode());
         * xfWithholdOP.setCertificateNo(user.getIdNo());
         * xfWithholdOP.setProductName("视频会员");
         *
         * xfWithholdOP.setUserId(user.getId());
         * xfWithholdOP.setApplyId(applyId);
         * xfWithholdOP.setContractId(loanApply.getContNo()); vo =
         * xianFengWithholdService.xfWithhold(xfWithholdOP, payType); } catch
         * (Exception e) { logger.error("先锋单笔代扣异常", e); }
         */
        logger.debug("========先锋单笔【购物金】代扣类交易===========");
        XfAgreementPayOP op = new XfAgreementPayOP();
        XfAgreementPayResultVO vo = new XfAgreementPayResultVO();
        try {
            LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
            if (loanApply == null) {
                logger.error("贷款申请单不存在，applyId = {}", applyId);
                vo.setResCode(ErrInfo.ERROR.getCode());
                vo.setResMessage("贷款申请单不存在");
                return vo;
            }
            CustUser user = custUserManager.getById(loanApply.getUserId());
            op.setContractId(loanApply.getContNo());
            op.setApplyId(applyId);
            op.setUserId(user.getId());
            op.setAmount(amount);

            op.setPayType(payType);
            vo = xianFengAgreementPayService.agreementPay(op);
            // op.setRepayPlanItemId(param.getRepayPlanItemId());
            // op.setIp(ip);
            // op.setSource(param.getSource());
        } catch (Exception e) {
            logger.error("先锋单笔代扣异常", e);
        }
        return vo;
    }

    @Override
    public XfAgreementPayResultVO xfWithholdQuery(String merchantNo) {
        /*
         * XfWithholdResultVO vo = xianFengWithholdService
         * .xfWithholdQuery(merchantNo);
         */
        logger.debug("========先锋单笔【购物金】订单查询类交易===========");
        XfAgreementPayResultVO vo = xianFengAgreementPayService.agreementPayQuery(merchantNo);
        return vo;
    }

    private WithholdResultVO withholdXianJinBaiKa(String itemId, String delayAmt) {
        boolean isDelay = StringUtils.isNotBlank(delayAmt);// 判断为还款还是延期
        WithholdResultVO vo = new WithholdResultVO();
        RepayPlanItem item = repayPlanItemManager.getById(itemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId);
            return vo;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId, currTerm, item.getThisTerm());
            return vo;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            vo.setUnsolved(true);
            return vo;
        }
        if (repayPlanItemManager.processing(item.getId()) == 0) {
            logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
            return vo;
        }
        CustUser user = custUserManager.getById(item.getUserId());
        WithholdOP param = getWithholdOP(item, user);
        // 延期金额不为空则为延期代扣，否则是还款代扣
        Integer payType = PayTypeEnum.SETTLEMENT.getId();
        if (isDelay) {
            param.setTxnAmt(new BigDecimal(delayAmt).multiply(BigDecimal.valueOf(100)).toString());
            payType = PayTypeEnum.DELAY.getId();
            /** 宝付代扣 */
            vo = baofooWithholdService.withhold(param, payType);
            if (!vo.getSuccess() && isRespUnsolved(vo.getCode())) {
                vo.setUnsolved(true);
                repayPlanItemManager.unfinish(item.getId());
            } else if (!vo.getSuccess() && !isRespUnsolved(vo.getCode())) {
                vo.setUnsolved(false);
                repayPlanItemManager.unfinish(item.getId());
            }
        } else {
            /** 宝付代扣 */
            vo = baofooWithholdService.withhold(param, payType);
            if (!vo.getSuccess()) {
                logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), vo.getCode(), vo.getMsg());
                repayPlanItemManager.unfinish(item.getId());
                // 发送短信
                String content = String.format(ShortMsgTemplate.REPAY_FAIL, item.getUserName(), item.getTotalAmount());
                sendSMS(content, user.getMobile(), user.getId(), user.getChannel());
            }
        }
        return vo;
    }

    @Transactional
    @Override
    public WithholdResultVO withholdXianJinBaiKa(String itemId) {
        WithholdResultVO vo = withholdXianJinBaiKa(itemId, null);
        if (vo.getSuccess()) {
            WithholdQueryResultVO updateVO = new WithholdQueryResultVO();
            updateVO.setSuccAmt(vo.getSuccAmt());
            updateVO.setOrigTradeDate(vo.getTradeDate());
            updateOrderInfo(itemId, updateVO);
        }
        return vo;
    }

    @Transactional
    @Override
    public ConfirmAuthPayVO agreementPay(RePayOP rePayOP) {
        ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
        String itemId = rePayOP.getRepayPlanItemId();
        RepayPlanItem item = repayPlanItemManager.getById(itemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId);
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款明细不存在或状态不正确");
            return confirmAuthPayVO;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            confirmAuthPayVO.setStatus("I");
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("订单正在处理中，请稍后再试");
            return confirmAuthPayVO;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId, currTerm, item.getThisTerm());
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款期数错误");
            return confirmAuthPayVO;
        }
        if (repayPlanItemManager.processing(item.getId()) == 0) {
            logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("未知异常");
            return confirmAuthPayVO;
        }

        // 先锋支付
        XfAgreementPayOP op = new XfAgreementPayOP();
        op.setContractId(item.getContNo());
        op.setApplyId(item.getApplyId());
        op.setUserId(item.getUserId());
        op.setAmount(rePayOP.getTxAmt());
        op.setRepayPlanItemId(itemId);
        // op.setIp(ip);
        op.setSource(rePayOP.getSource());
        op.setPayType(rePayOP.getPayType());

        String repay_method = configService.getValue("repay_method");// 1=宝付代扣,2=先锋支付
        XfAgreementPayResultVO xfAgreementPayResultVO = null;
        if ("1".equals(repay_method)) {
            xfAgreementPayResultVO = new XfAgreementPayResultVO();
            xfAgreementPayResultVO.setResCode("00041");
        } else {
            xfAgreementPayResultVO = xianFengAgreementPayService.agreementPay(op);
        }
        // 先锋支付失败，接着宝付代扣
        // 00041=暂不支持该银行
        if ("00041".equals(xfAgreementPayResultVO.getResCode())) {
            repayPlanItemManager.unfinish(rePayOP.getRepayPlanItemId());
            WithholdResultVO withholdResultVO = adminOverdueWithhold(itemId, PayTypesEnum.BAOFU);
            if (!withholdResultVO.getSuccess()) {
                confirmAuthPayVO.setSuccess(false);
                confirmAuthPayVO.setCode("FAIL");
                confirmAuthPayVO.setMsg(withholdResultVO.getMsg());
                return confirmAuthPayVO;
            } else {
                confirmAuthPayVO.setSuccess(true);
                confirmAuthPayVO.setCode("SUCCESS");
                confirmAuthPayVO.setMsg("支付成功");
                return confirmAuthPayVO;
            }
        }

        // 保存支付结果
        confirmAuthPayVO.setSuccess(xfAgreementPayResultVO.isSuccess());
        confirmAuthPayVO.setCode(xfAgreementPayResultVO.getResCode());
        confirmAuthPayVO.setMsg(xfAgreementPayResultVO.getResMessage());
        confirmAuthPayVO.setOrderNo(xfAgreementPayResultVO.getMerchantNo());
        confirmAuthPayVO.setSuccAmt(xfAgreementPayResultVO.getAmountYuan());
        confirmAuthPayVO.setSuccTime(xfAgreementPayResultVO.getTradeTime());
        confirmAuthPayVO.setStatus(xfAgreementPayResultVO.getStatus());
        rePayOP.setPayStatus(confirmAuthPayVO.getCode());
        rePayOP.setOrderInfo(confirmAuthPayVO.getMsg());
        rePayOP.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
        rePayOP.setPaySuccTime(confirmAuthPayVO.getSuccTime());
        rePayOP.setChlCode(Global.XIANFENG_CHANNEL_CODE);
        repayPlanItemManager.unfinish(rePayOP.getRepayPlanItemId());
        if (!confirmAuthPayVO.isSuccess()) {
            logger.error("协议支付，{}，applyId= {}", confirmAuthPayVO.getMsg(), rePayOP.getApplyId());
            return confirmAuthPayVO;
        }

        if (PayTypeEnum.SETTLEMENT.getId().equals(rePayOP.getPayType())) {
            rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
            settlementService.settlement(rePayOP);
        }
        return confirmAuthPayVO;
    }

    @Transactional
    @Override
    public ConfirmAuthPayVO agreementPayTest(RePayOP rePayOP){
        ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
        String itemId = rePayOP.getRepayPlanItemId();
        RepayPlanItem item = repayPlanItemManager.getById(itemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId);
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款明细不存在或状态不正确");
            return confirmAuthPayVO;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            confirmAuthPayVO.setStatus("I");
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("订单正在处理中，请稍后再试");
            return confirmAuthPayVO;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId, currTerm, item.getThisTerm());
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款期数错误");
            return confirmAuthPayVO;
        }

        String repay_method = configService.getValue("repay_method");// 1=宝付代扣,2=先锋支付,3=通联支付
        AuthPayOP authPayOP = new AuthPayOP();
        authPayOP.setContractId(item.getContNo());// 合同号
        authPayOP.setApplyId(item.getApplyId());// 订单号
        authPayOP.setUserId(item.getUserId());
        authPayOP.setAmount(rePayOP.getTxAmt());// 金额（元）
        authPayOP.setRepayPlanItemId(itemId);// 还款计划明细id
        authPayOP.setIp(rePayOP.getIp());
        authPayOP.setSource(rePayOP.getSource());// 进件来源
        authPayOP.setPayType(rePayOP.getPayType());// 支付分类
        authPayOP.setBindId(rePayOP.getBindId());// 绑定签约号
        authPayOP.setRealName(rePayOP.getFullName());
        authPayOP.setTxType(rePayOP.getTxType());
        if ("1".equals(repay_method)){
            // 宝付支付
        } else if ("2".equals(repay_method)) {
            // 先锋支付
            rePayOP.setChlCode(PayChannelEnum.XIANFENG.getChannelCode());
            confirmAuthPayVO = xianFengAgreementPayService.pay(authPayOP);
        } else if ("3".equals(repay_method)) {
            // 通联支付
            rePayOP.setChlCode(PayChannelEnum.TONGLIAN.getChannelCode());
            confirmAuthPayVO = tltAgreementPayService.pay(authPayOP);
        }
        rePayOP.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
        rePayOP.setPaySuccTime(confirmAuthPayVO.getSuccTime());

        //repayPlanItemManager.unfinish(rePayOP.getRepayPlanItemId());
        if (!confirmAuthPayVO.isSuccess()) {
            logger.error("协议支付，{}，applyId= {}", confirmAuthPayVO.getMsg(), rePayOP.getApplyId());
            return confirmAuthPayVO;
        }

        if (PayTypeEnum.SETTLEMENT.getId().equals(rePayOP.getPayType())) {
            rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
            settlementService.settlement(rePayOP);
        }

        return confirmAuthPayVO;
    }


    @Transactional
    @Override
    public ConfirmAuthPayVO agreementTonglianPay(RePayOP rePayOP) {
        ConfirmAuthPayVO confirmAuthPayVO = new ConfirmAuthPayVO();
        String itemId = rePayOP.getRepayPlanItemId();
        RepayPlanItem item = repayPlanItemManager.getById(itemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("代扣失败,还款明细不存在或状态不正确，id = {}", itemId);
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款明细不存在或状态不正确");
            return confirmAuthPayVO;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            confirmAuthPayVO.setStatus("I");
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("订单正在处理中，请稍后再试");
            return confirmAuthPayVO;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", itemId, currTerm, item.getThisTerm());
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("还款期数错误");
            return confirmAuthPayVO;
        }
        if (repayPlanItemManager.processing(item.getId()) == 0) {
            logger.error("代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
            confirmAuthPayVO.setCode("FAIL");
            confirmAuthPayVO.setMsg("未知异常");
            return confirmAuthPayVO;
        }

        // 先锋支付
        XfAgreementPayOP op = new XfAgreementPayOP();
        op.setContractId(item.getContNo());
        op.setApplyId(item.getApplyId());
        op.setUserId(item.getUserId());
        op.setAmount(rePayOP.getTxAmt());
        op.setRepayPlanItemId(itemId);
        // op.setIp(ip);
        op.setSource(rePayOP.getSource());
        op.setPayType(rePayOP.getPayType());

        String repay_method = configService.getValue("repay_method");// 1=宝付代扣,2=先锋支付,3=通联支付
        XfAgreementPayResultVO xfAgreementPayResultVO = null;
        if ("1".equals(repay_method) || "3".equals(repay_method)) {
            xfAgreementPayResultVO = new XfAgreementPayResultVO();
            xfAgreementPayResultVO.setResCode("00041");
        } else if ("2".equals(repay_method)) {
            xfAgreementPayResultVO = xianFengAgreementPayService.agreementPay(op);
        }

        // 先锋支付失败，接着宝付代扣
        // 不用宝付用，通联
        // 00041=暂不支持该银行
        if ("00041".equals(xfAgreementPayResultVO.getResCode())) {
            repayPlanItemManager.unfinish(rePayOP.getRepayPlanItemId());
//            WithholdResultVO withholdResultVO = adminOverdueWithhold(itemId, "baofu");
            WithholdResultVO withholdResultVO = adminOverdueWithhold(itemId, PayTypesEnum.TONGLIAN);
            if (!withholdResultVO.getSuccess()) {
                confirmAuthPayVO.setSuccess(false);
                confirmAuthPayVO.setCode("FAIL");
                confirmAuthPayVO.setStatus(withholdResultVO.getStatus());
                confirmAuthPayVO.setMsg(withholdResultVO.getMsg());
                return confirmAuthPayVO;
            } else {
                confirmAuthPayVO.setStatus(withholdResultVO.getStatus());
                confirmAuthPayVO.setSuccess(true);
                confirmAuthPayVO.setCode("SUCCESS");
                confirmAuthPayVO.setMsg("支付成功");
                return confirmAuthPayVO;
            }
        }

        // 保存支付结果
        confirmAuthPayVO.setSuccess(xfAgreementPayResultVO.isSuccess());
        confirmAuthPayVO.setCode(xfAgreementPayResultVO.getResCode());
        confirmAuthPayVO.setMsg(xfAgreementPayResultVO.getResMessage());
        confirmAuthPayVO.setOrderNo(xfAgreementPayResultVO.getMerchantNo());
        confirmAuthPayVO.setSuccAmt(xfAgreementPayResultVO.getAmountYuan());
        confirmAuthPayVO.setSuccTime(xfAgreementPayResultVO.getTradeTime());
        confirmAuthPayVO.setStatus(xfAgreementPayResultVO.getStatus());
        rePayOP.setPayStatus(confirmAuthPayVO.getCode());
        rePayOP.setOrderInfo(confirmAuthPayVO.getMsg());
        rePayOP.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
        rePayOP.setPaySuccTime(confirmAuthPayVO.getSuccTime());
        rePayOP.setChlCode(Global.XIANFENG_CHANNEL_CODE);
        repayPlanItemManager.unfinish(rePayOP.getRepayPlanItemId());
        if (!confirmAuthPayVO.isSuccess()) {
            logger.error("协议支付，{}，applyId= {}", confirmAuthPayVO.getMsg(), rePayOP.getApplyId());
            return confirmAuthPayVO;
        }

        if (PayTypeEnum.SETTLEMENT.getId().equals(rePayOP.getPayType())) {
            rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);
            settlementService.settlement(rePayOP);
        }
        return confirmAuthPayVO;
    }


    @Override
    public WithholdResultVO partWithhold(String repayPlanItemId, String amount, Integer payType) {
        WithholdResultVO withholdResultVO = new WithholdResultVO();

        // 数据状态检测
        RepayPlanItem item = repayPlanItemManager.getById(repayPlanItemId);
        if (item == null || ApplyStatusEnum.FINISHED.getValue().equals(item.getStatus())) {
            logger.error("部分代扣失败,还款明细不存在或状态不正确，id = {}", repayPlanItemId);
            withholdResultVO.setCode("FAIL");
            withholdResultVO.setMsg("还款明细不存在或状态不正确");
            return withholdResultVO;
        }
        // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        Long payCount = repayLogService.countPayingByRepayPlanItemId(item.getId());
        if (payCount != 0) {
            logger.error("部分代扣数据异常，订单正在处理中：{}", JsonMapper.getInstance().toJson(item));
            withholdResultVO.setUnsolved(true);
            withholdResultVO.setCode("FAIL");
            withholdResultVO.setMsg("订单正在处理中，请稍后再试");
            return withholdResultVO;
        }
        LoanRepayPlan repayPlan = loanRepayPlanManager.getByApplyId(item.getApplyId());
        int currTerm = (item.getTotalTerm() > 1) ? repayPlan.getCurrentTerm() : 1;
        if (currTerm != item.getThisTerm().intValue()) {
            logger.error("部分代扣还款期数错误, itemId : {} , currTerm : {} , thisTerm : {} ", repayPlanItemId, currTerm,
                    item.getThisTerm());
            withholdResultVO.setCode("FAIL");
            withholdResultVO.setMsg("还款期数错误");
            return withholdResultVO;
        }
        if (repayPlanItemManager.processing(item.getId()) == 0) {
            logger.error("部分代扣数据异常：{}", JsonMapper.getInstance().toJson(item));
            withholdResultVO.setCode("FAIL");
            withholdResultVO.setMsg("未知异常");
            return withholdResultVO;
        }

        CustUser user = custUserManager.getById(item.getUserId());
        WithholdOP param = getWithholdOP(item, user);
        param.setTxnAmt(MoneyUtils.yuan2fen(amount));// 部分代扣金额

        // 代扣
        try {

            // withholdResultVO = baofooWithholdService.withhold(param, payType);
            // 通联代扣操作
            TlAgreementPayResultVO withhold = tltAgreementPayService.withhold(param, payType);
            withholdResultVO.setSuccess(withhold.getSuccess());
            withholdResultVO.setTransNo(withhold.getTradeNo());
            withholdResultVO.setTradeDate(withhold.getTradeTime());
            withholdResultVO.setCode(withhold.getResCode());
            withholdResultVO.setMsg(withhold.getResMessage());
            withholdResultVO.setSuccAmt(withhold.getAmount());
            withholdResultVO.setStatus(withhold.getStatus());

        } finally {
            repayPlanItemManager.unfinish(item.getId());
        }
        if ("I".equals(withholdResultVO.getStatus())) {
            // 代扣处理中
            withholdResultVO.setUnsolved(true);
            return withholdResultVO;
        }
        if ("F".equals(withholdResultVO.getStatus())) {
            // 代扣失败
            logger.error("代扣失败，还款明细id = {}, code = {}, msg = {}", item.getId(), withholdResultVO.getCode(),
                    withholdResultVO.getMsg());
            return withholdResultVO;
        }

        return withholdResultVO;
    }

}