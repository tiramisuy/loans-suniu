package com.rongdu.loans.loan.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.entity.Goods;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.UserMd5;
import com.rongdu.loans.loan.manager.ApplyTripartiteRong360Manager;
import com.rongdu.loans.loan.manager.GoodsManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.manager.UserMd5Manager;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.rong360Model.ApproveOP;
import com.rongdu.loans.loan.option.rong360Model.GoodsInfo;
import com.rongdu.loans.loan.option.rong360Model.Rong360Resp;
import com.rongdu.loans.loan.service.ApplyTripartiteRong360Service;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.RongStatusFeedBackService;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @version V1.0
 * @Title: UpdateRepayPlanItemAspect.java
 * @Package com.rongdu.loans.loan.aspect
 * @Description: 融360订单逾期，延期，还款，异步反馈结果切面 （针对融360订单，还款计划变化的结果反馈）
 * @author: yuanxianchu
 * @date 2018年7月4日
 */
@Slf4j
@Aspect
@Component
public class UpdateRepayPlanItemAspect {

    @Autowired
    private RongStatusFeedBackService rongStatusFeedBackService;
    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private ApplyTripartiteRong360Manager applyTripartiteRong360Manager;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private GoodsManager goodsManager;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private UserMd5Manager userMd5Manager;

    /**
     * @Title: overduePointcut
     * @Description: 贷款逾期-订单状态反馈切点
     */
    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.overduePoint(..))")
    void overduePointcut() {
    }

    @Async
    @AfterReturning(value = "overduePointcut()&&args(applyIdList)")
    void overdueFeedBack(List<String> applyIdList) throws Exception {
        List<String> rongOrderNos = applyTripartiteRong360Manager.findThirdIdsByApplyIds(applyIdList);
        if (rongOrderNos == null || rongOrderNos.isEmpty()) {
            log.debug("【融360】三方订单表不存在此订单-{}", applyIdList);
            return;
        }
        Thread.sleep(2000);
        for (String orderNo : rongOrderNos) {
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
        	log.debug("----------进入【融360-逾期】后置通知：异步执行结果反馈orderNo={}----------",orderNo);
            boolean flag = rongStatusFeedBackService.rongOverdueStatusFeedBack(orderNo);
            if (!flag) {
                this.toRedis(applyId, Global.RONG_PAY_FEEDBACK);
            }
            log.debug("----------离开【融360-逾期】后置通知orderNo={}----------",orderNo);
        }
    }

    /**
     * 在融360平台中暂不支持 展期操作
     *
     * @Title: manualDelayFeedBackPointcut
     * @Description: 贷款延期-还款或展期结果反馈切点 （延期成功） 后台手动延期（手动延期和代扣延期）以及app主动延期
     */
    /*@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.delayPoint(..))")
    void delayFeedBackPointcut() {
    }*/

    //@Async
    //@AfterReturning(value = "delayFeedBackPointcut()&&args(repayPlanItemId)")
    public void delayFeedBack1(String repayPlanItemId) throws Exception {
        delayFeedBack(repayPlanItemId);
    }

    /**
     * 在融360平台中暂不支持 展期操作
     *
     * @Title: manualDelayFeedBackPointcut
     * @Description: 贷款延期-还款或展期结果反馈切点 （延期成功） app主动延期
     */
    /*
     * @Pointcut(value =
	 * "execution(* com.rongdu.loans.loan.service.ContractService.processManualDelay(..))"
	 * ) void manualDelayFeedBackPointcut() { }
	 * 
	 * @Async
	 * 
	 * @AfterReturning(value =
	 * "manualDelayFeedBackPointcut()&&args(repayPlanItemId)") public void
	 * delayFeedBack2(String repayPlanItemId) throws Exception {
	 * delayFeedBack(repayPlanItemId); }
	 */
    private void delayFeedBack(String repayPlanItemId) throws Exception {
        Thread.sleep(3000);
        String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
        if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
            log.debug("三方订单表不存在此订单-{}", applyId);
            return;
        }
        log.debug("----------进入【延期】后置通知：异步执行结果反馈----------");
        log.debug("入参repayPlanItemId:{}", repayPlanItemId);
        rongStatusFeedBackService.rongDelayStatusFeedBack(repayPlanItemId, applyId, true);
        log.debug("----------离开【延期】后置通知----------");
    }

    /**
     * 在融360平台中暂不支持 展期操作
     *
     * @Title: manualDelayFeedBackPointcut
     * @Description: 贷款延期-还款或展期结果反馈切点 （处理中“变为”延期失败）
     */
    /*@Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.repayProcessFailPoint(..))")
    void repayProcessFailPointcut() {
    }*/

    // @AfterReturning(value =
    // "repayProcessFailPointcut()&&args(repayPlanItemId)")
    public void repayProcessFailFeedBack(String repayPlanItemId) throws Exception {
        String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
        if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
            log.debug("三方订单表不存在此订单-{}", applyId);
            return;
        }
        log.debug("----------进入【延期失败】后置通知：异步执行结果反馈----------");
        log.debug("入参repayPlanItemId:{}", repayPlanItemId);
        rongStatusFeedBackService.rongDelayStatusFeedBack(repayPlanItemId, applyId, false);
        log.debug("----------离开【延期失败】后置通知----------");
    }

	/*
     * @AfterThrowing(value="delayFeedBackPointcut()&&args(repayPlanItemId)",
	 * throwing="e") public void delayFailFeedBack(String
	 * repayPlanItemId,Throwable e) throws Exception{
	 * log.debug("----------进入【延期】后置通知：异步执行结果反馈----------");
	 * log.debug("入参repayPlanItemId:{}",repayPlanItemId);
	 * rongStatusFeedBackService.rongDelayStatusFeedBack(repayPlanItemId,true);
	 * log.debug("----------离开【延期】后置通知----------"); }
	 */

    /**
     * @Title: settlementFeedBackPointcut
     * @Description: 贷款结清-订单状态反馈切点 app还款（还款方式 - 主动还款"manual"）
     */
    @Pointcut(value = "execution(* com.rongdu.loans.loan.manager.SettlementManager.Settlement(..))")
    void settlementFeedBackPointcut1() {
    }

    @Async
    @AfterReturning(value = "settlementFeedBackPointcut1()&&args(rePayOP)", returning = "returnValue")
    public void settlementFeedBack1(RePayOP rePayOP, ConfirmPayResultVO returnValue) throws Exception {
        boolean result = false;
        if (returnValue.getResult().intValue() == 1) {
            result = true;
        }
        settlementFeedBack(rePayOP.getRepayPlanItemId(), result, 1);
    }

    /**
     * 逾期手动/系统代扣（还款方式 - 自动扣款"auto"）
     */
    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.settlementPoint(..))")
    void settlementFeedBackPointcut2() {
    }

    @Async
    @AfterReturning(value = "settlementFeedBackPointcut2()&&args(repayPlanItemId,result)")
    public void settlementFeedBack2(String repayPlanItemId, boolean result) throws Exception {
        settlementFeedBack(repayPlanItemId, result, 2);
    }

    /**
     * 后台手动还款（还款方式 - 手动还款"manpay"）
     */
    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.settlementManPayPoint(..))")
    void settlementFeedBackPointcut3() {
    }

    @Async
    @AfterReturning(value = "settlementFeedBackPointcut3()&&args(repayPlanItemId,result)")
    public void settlementFeedBack3(String repayPlanItemId, boolean result) throws Exception {
        settlementFeedBack(repayPlanItemId, true, 1);
    }

    /**
     * 融360还款（还款方式 - 自动扣款"auto"） ps：特殊处理为主动还款
     */
    @Pointcut(value = "execution(* com.rongdu.loans.pay.service.WithholdService.agreementPay(..))")
    void settlementFeedBackPointcut4() {
    }

    @Async
    @AfterReturning(value = "settlementFeedBackPointcut4()&&args(rePayOP)", returning = "returnValue")
    public void settlementFeedBack4(RePayOP rePayOP, ConfirmAuthPayVO returnValue) throws Exception {
        settlementFeedBack(rePayOP.getRepayPlanItemId(), returnValue.isSuccess(), 1);
    }

    private void settlementFeedBack(String repayPlanItemId, boolean result, Integer repayType) throws Exception {
        String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
        if (!applyTripartiteRong360Service.isExistApplyId(applyId)) {
            log.debug("【融360】三方订单表不存在此订单-{}", applyId);
            return;
        }
        Thread.sleep(2000);
        log.debug("----------进入【融360-还款】后置通知：异步执行结果反馈,applyId={},repayPlanItemId={}----------", applyId, repayPlanItemId);
        log.debug("入参repayPlanItemId:{}", repayPlanItemId);
        RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
        boolean flag = true;
        if (repayPlanItem.getThisTerm() == repayPlanItem.getTotalTerm()) {
            flag = rongStatusFeedBackService.rongSettlementStatusFeedBack(repayPlanItemId, applyId, result,
                    repayType);
        } else {
            flag = rongStatusFeedBackService.rongRepayStatusFeedBack(repayPlanItemId, applyId, result, repayType);
        }
        if (!flag) {
            this.toRedisWithResult(repayPlanItemId, Global.RONG_SETTLEMENT_FEEDBACK, result, repayType);
        }
        log.debug("----------离开【融360-还款】后置通知,applyId={},repayPlanItemId={}----------", applyId, repayPlanItemId);
    }

    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongService.confirmLoan(..))")
    void confirmLoan() {
    }

    @Async
    @AfterReturning(value = "confirmLoan()&&args(approveOP)", returning = "rong360Resp")
    public void confirmLoan(ApproveOP approveOP, Rong360Resp rong360Resp) throws Exception {
        log.info("融360开始放款:{}", approveOP.getOrderNo());
        try {
            if (rong360Resp.getCode().equals("200")) {
                String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(approveOP.getOrderNo());
                LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
                Integer status = loanApplySimpleVO.getProcessStatus();
                if (!status.equals(XjdLifeCycle.LC_CASH_4) && !status.equals(XjdLifeCycle.LC_CHANNEL_0)) {
//                    boolean flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
//                contractService.processKoudaiLendPay(applyId, new Date());
                    List<GoodsInfo> goodsInfoList = approveOP.getGoodsInfo();
                    List<Goods> goodsList = new ArrayList<>();
                    for (GoodsInfo goodsInfo : goodsInfoList) {
                        Goods goods = BeanMapper.map(goodsInfo, Goods.class);
                        goodsList.add(goods);
                    }
                    goodsManager.insertBatch(goodsList);
                    log.info("融360放款成功:{}", approveOP.getOrderNo());
                }
            }
        } catch (Exception e) {
            log.info("融360放款异常:{}", approveOP.getOrderNo());
            e.printStackTrace();
        }
    }

    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.LoanApplyService.saveLoanApply(..))")
    void saveLoanApply() {
    }

    @Async
    @AfterReturning(value = "saveLoanApply()&&args(loanApplyOP)")
    public void saveLoanApply(LoanApplyOP loanApplyOP) throws Exception {
        log.info("融360保存用户MD5:{}", loanApplyOP.getUserId());
        try {
            CustUserVO custUserVO = custUserService.getCustUserById(loanApplyOP.getUserId());
            String mobile = custUserVO.getMobile();
            String idNo = custUserVO.getIdNo();
            String mobileAndIdNo = mobile + idNo;
            String md5 = MD5Util.string2MD5(mobileAndIdNo).toUpperCase();
            String userId = loanApplyOP.getUserId();
            UserMd5 userMd5 = new UserMd5();
            userMd5.setMobile(mobile);
            userMd5.setIdNo(idNo);
            userMd5.setMd5(md5);
            userMd5.setUserNo(userId);
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("md5", md5));
            UserMd5 userMd51 = userMd5Manager.getByCriteria(criteria);
            if (userMd51 == null) {
                userMd5Manager.insert(userMd5);
            }
        } catch (Exception e) {
            log.info("融360保存用户MD5异常:{}", loanApplyOP.getUserId());
            e.printStackTrace();
        }
    }

    private void toRedis(String applyId,String cacheKey) {
        Map<String, String> map = new HashMap<>();
        map.put(applyId, String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut(cacheKey, map);
    }

    private void toRedisWithResult(String applyId, String cacheKey, boolean result, Integer repayType) {
        String value =
                String.valueOf(result) + "_" + String.valueOf(repayType) + "_" + String.valueOf(System.currentTimeMillis());
        Map<String, String> map = new HashMap<>();
        map.put(applyId, value);
        JedisUtils.mapPut(cacheKey, map);
    }

}
