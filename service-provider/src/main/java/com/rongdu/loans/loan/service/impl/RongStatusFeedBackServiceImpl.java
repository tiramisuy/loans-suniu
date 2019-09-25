package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.RequestDefaultHandler;
import com.rongdu.loans.common.Rong360Config;
import com.rongdu.loans.common.ThirdApiDTO;
import com.rongdu.loans.common.rong360.Base64Utils;
import com.rongdu.loans.common.rong360.CommonUtil;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.common.rong360.RSAUtils;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.Rong360ServiceEnums;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.rong360.RongBindcardfeedbackOP;
import com.rongdu.loans.loan.option.rong360Model.*;
import com.rongdu.loans.loan.option.xjbk.LoanRepayPlanVO;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version V1.0
 * @Title: Rong360FeedBackService.java
 * @Package com.rongdu.loans.loan.aspect
 * @author: yuanxianchu
 * @date 2018年7月3日
 */
@Slf4j
@Service("rongFeedBackService")
public class RongStatusFeedBackServiceImpl extends RequestDefaultHandler implements RongStatusFeedBackService {
    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RongService rongService;

    @Override
    public RongApproveFeedBackOP pullApproveStatusFeedBack(String orderNo) {
        String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
        log.debug("----------开始拉取【融360-审批状态】applyId={},orderNo={}----------", applyId, orderNo);
        RongApproveFeedBackOP rongApproveFeedBackOP = new RongApproveFeedBackOP();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            // 还未生成订单
            rongApproveFeedBackOP.setOrderNo(orderNo);
            rongApproveFeedBackOP.setConclusion("1");// 审批处理中
            return rongApproveFeedBackOP;
        }
        Integer status = loanApply.getStatus();
        if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
            rongApproveFeedBackOP.setOrderNo(orderNo);
            rongApproveFeedBackOP.setConclusion("40");// 审批不通过
            // rongApproveFeedBackOP.setRemark("lalala测试");
            rongApproveFeedBackOP.setRefuseTime(String.valueOf((loanApply.getApproveTime() == null ?
                    loanApply.getUpdateTime() : loanApply.getApproveTime()).getTime() / 1000));
            rongApproveFeedBackOP.setRemark(loanApply.getRemark() == null ? "系统取消" : loanApply.getRemark());
        } else if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)
                || status.intValue() > ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().intValue()) {
            rongApproveFeedBackOP.setOrderNo(orderNo);
            rongApproveFeedBackOP.setConclusion("10");// 审批通过
            rongApproveFeedBackOP.setAmountType("0");// 0=固定金额
            rongApproveFeedBackOP.setTermUnit("1");// 1=单期产品（按天计息）
            rongApproveFeedBackOP.setTermType("0");// 0=固定期限
            rongApproveFeedBackOP.setApprovalTime(String.valueOf(loanApply.getApproveTime().getTime() / 1000));
            rongApproveFeedBackOP.setApprovalAmount(loanApply.getApproveAmt());// 审批金额
            rongApproveFeedBackOP.setApprovalTerm(loanApply.getApproveTerm());// 审批期限
            // 总还款额 审批金额+利息
            rongApproveFeedBackOP.setPayAmount(loanApply.getApproveAmt().add(loanApply.getInterest()));
            // 总到账金额 审批金额-服务费
            rongApproveFeedBackOP.setReceiveAmount(loanApply.getApproveAmt().subtract(loanApply.getServFee()));
            rongApproveFeedBackOP.setRemark("本金" + loanApply.getApproveAmt() + "元，利息" + loanApply.getInterest() + "元");
        } else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
            rongApproveFeedBackOP.setOrderNo(orderNo);
            rongApproveFeedBackOP.setConclusion("1");// 审批处理中
        }
        log.debug("----------结束拉取【融360-审批状态】applyId={},orderNo={}----------{}", applyId, orderNo,
                JSONObject.toJSONString(rongApproveFeedBackOP));
        return rongApproveFeedBackOP;
    }

    @Override
    public RongOrderFeedBackOP pullOrderStatusFeedBack(String orderNo) {
        String loanApplyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
        log.debug("----------开始拉取【融360-订单状态】applyId={},orderNo={}----------", loanApplyId, orderNo);
        LoanApply loanApply = loanApplyManager.getLoanApplyById(loanApplyId);
        RongOrderFeedBackOP rongOrderFeedBackOP = new RongOrderFeedBackOP();
        if (loanApply == null) {
            // 还未生成订单
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setUpdateTime((int) (new Date().getTime() / 1000));
            return rongOrderFeedBackOP;
        }
        Integer status = loanApply.getStatus();
        if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)
        || ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue().equals(status)) {
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(100);// 审核通过
            /*if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(loanApply.getPayChannel())
                    && JedisUtils.exists(Global.RONG_CREATE_ACCOUNT + loanApplyId)) {
                // 口袋存管需要开户
                rongOrderFeedBackOP.setOrderStatus(120);// 待开户
            }*/
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } /*else if (ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue().equals(status)) {
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(151);// 待放款
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        }*/ else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)) {
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(110);// 审核不通过
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().equals(status)) {
            // 放款成功
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(170);// 放款成功
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL.getValue().equals(status)) {
            // 待提现
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(171);// 待提现
            if (!JedisUtils.exists(Global.RONG_CREATE_ACCOUNT + loanApplyId)){
                // 用户尚未审批确认，不推送“待提现”状态
                rongOrderFeedBackOP.setOrderStatus(100);
            }
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
            // 贷款取消
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(161);// 贷款取消
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue().equals(status)) {
            // 贷款逾期
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(180);// 贷款逾期
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.REPAY.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().equals(status)) {
            // 贷款结清
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(200);// 贷款结清
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(1);// 审批处理中
            rongOrderFeedBackOP.setUpdateTime((int) (loanApply.getUpdateTime().getTime() / 1000));
        } else {
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(0);// 未知状态
            rongOrderFeedBackOP.setUpdateTime((int) (new Date().getTime() / 1000));
        }
        log.debug("----------结束拉取【融360-订单状态】applyId={},orderNo={}----------{}", loanApplyId, orderNo,
                JSONObject.toJSONString(rongOrderFeedBackOP));
        return rongOrderFeedBackOP;
    }

    @Override
    public RongPushRepaymentOP pullRepaymentPlan(String orderNo) {
        String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
        log.debug("----------开始拉取【融360-还款计划】applyId={},orderNo={}----------", applyId, orderNo);
        LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);
        CalculateInfo calculateInfo = rongService.getOrderInfo(loanApplySimpleVO);
        float overDuefee = calculateInfo.getOverdueFee().floatValue();
        BigDecimal interest = loanApplySimpleVO.getInterest();

        BigDecimal approveInterest = calculateInfo.getLoanApplyInterest();
        BigDecimal ovdueInterest = interest.subtract(approveInterest);
        float principal = calculateInfo.getPrincipal().floatValue();

        // 还款计划推送数据组合
        // 第一步：基础数据组合
        RongPushRepaymentOP rongPushRepaymentOP = new RongPushRepaymentOP();
        rongPushRepaymentOP.setOrderNo(orderNo);// 三方订单号
        rongPushRepaymentOP.setBankCard(loanApplySimpleVO.getBankCard());// 银行卡号
        rongPushRepaymentOP.setOpenBank(loanApplySimpleVO.getBankCode());// 银行名称

        RepayDetailListOP op = new RepayDetailListOP();
        op.setContNo(repayPlan.getContNo());
        op.setIsDelaySettlement(2);
        //List<RepayDetailListVO> rpayDetailListVOList = repayPlanItemManager.repayDetailList(null, op);
        List<RepayPlanItem> planItems = repayPlanItemManager.getByApplyIdForApp(applyId);
        // 第二步：还款计划数据组合
        List<RongRepaymentPlan> repayPlanList = new ArrayList<>();
        for (RepayPlanItem planItem : planItems) {
            RongRepaymentPlan repaymentPlant = new RongRepaymentPlan();
            repaymentPlant.setAmount(planItem.getTotalAmount());// 还款总金额
            repaymentPlant.setBillStatus(planItem.getStatus(), planItem.getRepayDate());// 账单状态
            // 1未到期；2已还款；3逾期
            repaymentPlant.setCanRepayTime((int) (planItem.getStartDate().getTime() / 1000));// 可以还款时间
            repaymentPlant.setDueTime((int) (planItem.getRepayDate().getTime() / 1000));// 到期时间
            repaymentPlant.setIsAbleDefer(0);// 是否可以展期
            repaymentPlant.setPaidAmount(planItem.getActualRepayAmt());// 已还金额
            repaymentPlant.setPayType(5);// 支持的还款方式类型 5=1+4同时支持主动还款和银行代扣
            repaymentPlant.setPeriodNo(planItem.getThisTerm());// 期数
            repaymentPlant.setRemark("本金" + principal + "元，利息" + approveInterest + "元，逾期利息" + ovdueInterest.floatValue()
                    + "元，逾期费" + overDuefee + "元");
            repaymentPlant.setSuccessTime(planItem.getActualRepayTime() == null ? 0
                    : (int) (planItem.getActualRepayTime().getTime() / 1000));
            repayPlanList.add(repaymentPlant);
        }
        rongPushRepaymentOP.setRongRepaymentPlan(repayPlanList);
        log.debug("----------结束拉取【融360-还款计划】applyId={},oredrNo={}----------{}", applyId, orderNo,
				JSONObject.toJSONString(rongPushRepaymentOP));
        return rongPushRepaymentOP;
    }

    private RongRepayFeedBackOP pullRepayStatusFeedBack(String orderNo, String repayPlanItemId, boolean result,
                                                        int type, Integer repayType) {
        log.debug("----------开始拉取【融360-还款/展期状态】orderNo={},repayPlanItemId={}----------", orderNo, repayPlanItemId);
        RongRepayFeedBackOP rongRepayFeedBackOP = new RongRepayFeedBackOP();
        // 查询最近的交易日志
        RepayLogVO repayLogVO = repayLogService.findByRepayPlanItemId(repayPlanItemId);
        RepayPlanItem repayPlanItem = repayPlanItemManager.get(repayPlanItemId);
        rongRepayFeedBackOP.setOrderNo(orderNo);
        rongRepayFeedBackOP.setPeriodNos(repayPlanItem.getThisTerm().toString());
        rongRepayFeedBackOP.setRepayPlace(String.valueOf(repayType));
        rongRepayFeedBackOP.setRepayStatus("2");// 2：还款失败（展期失败)
        if (repayLogVO == null) {
            rongRepayFeedBackOP.setRemark(ErrInfo.ERROR.getMsg());
        } else {
            rongRepayFeedBackOP.setRemark(repayLogVO.getRemark());
        }
        if (result) {
            if (type == 1) {
                rongRepayFeedBackOP.setRemark("展期金额" + repayPlanItem.getActualRepayAmt());
            } else if (type == 2) {
                rongRepayFeedBackOP.setRemark("还款金额" + repayPlanItem.getActualRepayAmt());
            }
            rongRepayFeedBackOP.setRepayStatus("1");// 1：还款成功（展期成功）
            rongRepayFeedBackOP.setSuccessTime(String.valueOf(repayPlanItem.getUpdateTime().getTime() / 1000));
        }
        log.debug("----------结束拉取【融360-还款/展期状态】orderNo={},repayPlanItemId={}----------{}", orderNo, repayPlanItemId,
                JSONObject.toJSONString(rongRepayFeedBackOP));
        return rongRepayFeedBackOP;
    }

    @Override
    public void rongBindCardStatusFeedBack(BindCardOP bindCardOP, Rong360Resp rong360Resp) throws Exception {
        log.debug("----------开始执行【融360-绑卡结果推送】请求orderNo={}----------", bindCardOP.getOrderNo());
        RongBindcardfeedbackOP rongBindcardfeedbackOP = new RongBindcardfeedbackOP();
        rongBindcardfeedbackOP.setOrderNo(bindCardOP.getOrderNo());
        rongBindcardfeedbackOP.setBindCardType(bindCardOP.getBankcardtype());
        if ("200".equals(rong360Resp.getCode())) {
            rongBindcardfeedbackOP.setBindStatus(1);
        } else {
            rongBindcardfeedbackOP.setBindStatus(2);
            rongBindcardfeedbackOP.setReason(rong360Resp.getMsg());
        }
        Rong360FeedBackResp resp = this.requestHandler(rongBindcardfeedbackOP, Rong360FeedBackResp.class,
                Rong360ServiceEnums.ORDER_BINDCARDFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
        log.debug("----------结束执行【融360-绑卡结果推送】请求orderNo={}，绑卡结果推送响应结果={}----------", bindCardOP.getOrderNo(),
                JSONObject.toJSONString(resp));
    }

    @Override
    public boolean rongApproveStatusFeedBack(String applyId) {
        Rong360FeedBackResp approveResp = null;//推送审批结论 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-审批结论反馈】请求applyId={},orderNo={}----------", applyId, orderNo);
            RongApproveFeedBackOP rongApproveFeedBackOP = this.pullApproveStatusFeedBack(orderNo);
            if (StringUtils.isBlank(rongApproveFeedBackOP.getConclusion()) || "1".equals(rongApproveFeedBackOP.getConclusion())) {
                log.debug("----------撤销执行【融360-审批结论反馈】请求applyId={},orderNo={}----------", applyId, orderNo);
                return flag;
            }
            // 推送审批结论
            approveResp = this.requestHandler(rongApproveFeedBackOP, Rong360FeedBackResp.class,
                    Rong360ServiceEnums.ORDER_APPROVEFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
            if (approveResp == null || !"200".equals(approveResp.getError())) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-审批结论反馈】异常applyId={},orderNo={},推送审批结论响应结果={}----------", applyId, orderNo,
                    JSONObject.toJSONString(approveResp), e);
        }
        log.debug("----------结束执行【融360-审批结论反馈】applyId={},orderNo={},推送审批结论响应结果={}----------", applyId, orderNo,
                JSONObject.toJSONString(approveResp));
        return flag;
    }

    @Override
    public void rongContractStatusFeedBack(String applyId) throws Exception {
        log.debug("----------开始执行【融360-合同状态推送】请求applyId={}----------", applyId);
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
        RongContractStatusOP rongContractStatusOP = new RongContractStatusOP();
        rongContractStatusOP.setOrderNo(orderNo);
        rongContractStatusOP.setContractStatus(1);

        Rong360FeedBackResp resp = this.requestHandler(rongContractStatusOP, Rong360FeedBackResp.class,
                Rong360ServiceEnums.ORDER_CONTRACTSTATUS.getMethod(), Rong360Config.rong360_gateway);
        log.debug("----------结束执行【融360-合同状态推送】请求applyId={}，合同状态推送响应结果={}----------", applyId,
                JSONObject.toJSONString(resp));
    }

    @Override
    public boolean rongLendStatusFeedBack(String applyId) {
        Rong360FeedBackResp orderResp = null;//推送订单状态 响应结果
        Rong360FeedBackResp paymentResp = null;//推送还款计划 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-放款-订单状态，还款计划推送】请求applyId={},orderNo={}----------", applyId, orderNo);
            RongOrderFeedBackOP rongOrderFeedBackOP = this.pullOrderStatusFeedBack(orderNo);
            if (rongOrderFeedBackOP.getOrderStatus() != null && (rongOrderFeedBackOP.getOrderStatus().intValue() == 170
                    || rongOrderFeedBackOP.getOrderStatus().intValue() > 171)) {
                // 放款成功同时推送还款计划
                paymentResp = this.rongPushRepayPlan(orderNo);
                if (paymentResp == null || !"200".equals(paymentResp.getError())) {
                    flag = false;
                }
            }
            // 推送订单状态
            orderResp = this.requestHandler(rongOrderFeedBackOP, Rong360FeedBackResp.class,
                    Rong360ServiceEnums.ORDER_ORDERFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
            if (orderResp == null ||
                    !("200".equals(orderResp.getError()) ||"1022".equals(orderResp.getError()) ||"2022".equals(orderResp.getError()))) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-放款-订单状态，还款计划推送】异常applyId={},orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(paymentResp), e);
        }
        log.debug("----------结束异步执行【融360-放款-订单状态，还款计划推送】flag={},applyId={},orderNo={},推送订单状态响应结果={}," +
                        "推送还款计划响应结果={}----------",
                flag, applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(paymentResp));

        return flag;
    }

    @Override
    public Rong360FeedBackResp rongPushOrderStatus(String orderNo) throws Exception {
        // 订单状态数据组合
        RongOrderFeedBackOP rongOrderFeedBackOP = this.pullOrderStatusFeedBack(orderNo);
        Rong360FeedBackResp orderResp = this.requestHandler(rongOrderFeedBackOP, Rong360FeedBackResp.class,
                Rong360ServiceEnums.ORDER_ORDERFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
        return orderResp;
    }

    private Rong360FeedBackResp rongPushRepayPlan(String orderNo) throws Exception {
        // 还款计划推送数据组合
        RongPushRepaymentOP rongPushRepaymentOP = this.pullRepaymentPlan(orderNo);
        Rong360FeedBackResp paymentResp = this.requestHandler(rongPushRepaymentOP, Rong360FeedBackResp.class,
                Rong360ServiceEnums.ORDER_PUSHREPAYMENT.getMethod(), Rong360Config.rong360_gateway);
        return paymentResp;
    }

    @Override
    public boolean rongCancelStatusFeedBack(String applyId) throws Exception {
        Rong360FeedBackResp orderResp = null;//推送订单状态 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-贷款取消-订单状态推送】请求applyId={},orderNo={}----------", applyId, orderNo);
            orderResp = this.rongPushOrderStatus(orderNo);
            if (orderResp == null ||
                    !("200".equals(orderResp.getError()) ||"1022".equals(orderResp.getError()) ||"2022".equals(orderResp.getError()))) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-贷款取消-订单状态推送】异常applyId={},orderNo={},推送订单状态响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(orderResp), e);
        }
        log.debug("----------结束执行【融360-贷款取消-订单状态推送】请求applyId={},orderNo={},推送订单状态响应结果={}----------",
                applyId, orderNo, JSONObject.toJSONString(orderResp));
        return flag;
    }

    @Override
    public boolean rongDelayStatusFeedBack(String repayPlanItemId, String applyId, boolean result) {
        Rong360FeedBackResp delayResp = null;//推送还款或展期结果 响应结果
        Rong360FeedBackResp paymentResp = null;//推送还款计划 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-贷款延期-还款或展期结果，还款计划推送】请求----------");
            // 推送还款或展期结果
            RongRepayFeedBackOP rongRepayFeedBackOP = pullRepayStatusFeedBack(orderNo, repayPlanItemId, result, 1, 3);
            delayResp = this.requestHandler(rongRepayFeedBackOP, Rong360FeedBackResp.class,
                    Rong360ServiceEnums.ORDER_REPAYFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
            if (delayResp == null || !"200".equals(delayResp.getError())) {
                flag = false;
            }
            if (result) {
                // 延期成功后，推送还款计划
                paymentResp = this.rongPushRepayPlan(orderNo);
                if (paymentResp == null || !"200".equals(paymentResp.getError())) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-贷款延期-还款或展期结果，还款计划推送】异常applyId={},orderNo={},推送还款或展期结果响应结果={}," +
							"推送还款计划响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(delayResp), JSONObject.toJSONString(paymentResp), e);
        }
        log.debug("----------结束异步执行【融360-贷款延期-还款或展期结果，还款计划推送】applyId={},orderNo={},推送还款或展期结果响应结果={}," +
						"推送还款计划响应结果={}----------",
                applyId, orderNo, JSONObject.toJSONString(delayResp), JSONObject.toJSONString(paymentResp));
        return flag;
    }

    @Override
    public boolean rongOverdueStatusFeedBack(String orderNo) {
        Rong360FeedBackResp orderResp = null;//推送订单状态 响应结果
        Rong360FeedBackResp paymentResp = null;//推送还款计划 响应结果

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-贷款逾期-订单状态，还款计划推送】请求orderNo={}----------", orderNo);
            // 贷款逾期，推送还款计划
            paymentResp = this.rongPushRepayPlan(orderNo);
            if (paymentResp == null || !"200".equals(paymentResp.getError())) {
                flag = false;
            }
            // 推送订单状态
            orderResp = this.rongPushOrderStatus(orderNo);
            if (orderResp == null ||
                    !("200".equals(orderResp.getError()) ||"1022".equals(orderResp.getError()) ||"2022".equals(orderResp.getError()))) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-贷款逾期-订单状态，还款计划推送】异常orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
                    orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(paymentResp), e);
        }
        log.debug("----------结束异步执行【融360-贷款逾期-订单状态，还款计划推送】orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
                orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(paymentResp));
        return flag;
    }

    @Override
    public boolean rongSettlementStatusFeedBack(String repayPlanItemId, String applyId, boolean result,
                                             Integer repayType) {
        Rong360FeedBackResp orderResp = null;//推送订单状态 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        log.debug("----------开始执行【融360-贷款结清-订单状态，还款或展期结果，还款计划反馈】请求orderNo={}----------", orderNo);
        // 第一步：执行 还款-还款或展期结果反馈
        flag = this.rongRepayStatusFeedBack(repayPlanItemId, applyId, result, repayType);

        // 第二部：还款成功时 执行 贷款结清-订单状态反馈
        if (!result) {
            log.debug("----------结束执行【融360-贷款结清-订单状态，还款或展期结果，还款计划反馈】请求-还款失败orderNo={}----------", orderNo);
            return flag;
        }
        try {
            // 推送订单状态
            orderResp = this.rongPushOrderStatus(orderNo);
            if (orderResp == null ||
                    !("200".equals(orderResp.getError()) ||"1022".equals(orderResp.getError()) ||"2022".equals(orderResp.getError()))) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-贷款结清-订单状态，还款或展期结果，还款计划反馈】异常orderNo={},推送订单状态响应结果={}----------",
                    orderNo, JSONObject.toJSONString(orderResp), e);
        }
        log.debug("----------结束异步执行【融360-贷款结清-订单状态，还款或展期结果，还款计划推送】orderNo={},推送订单状态响应结果={}----------",
                orderNo, JSONObject.toJSONString(orderResp));
        return flag;
    }

    @Override
    public boolean rongRepayStatusFeedBack(String repayPlanItemId, String applyId, boolean result, Integer repayType) {
        Rong360FeedBackResp delayResp = null;//推送还款或展期结果 响应结果
        Rong360FeedBackResp paymentResp = null;//推送还款计划 响应结果
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始执行【融360-还款-还款或展期结果，还款计划推送】请求orderNo={},repayPlanItemId={}----------", orderNo,
                    repayPlanItemId);
            // 推送还款或展期结果
            RongRepayFeedBackOP rongRepayFeedBackOP = pullRepayStatusFeedBack(orderNo, repayPlanItemId, result, 2,
                    repayType);
            delayResp = this.requestHandler(rongRepayFeedBackOP, Rong360FeedBackResp.class,
                    Rong360ServiceEnums.ORDER_REPAYFEEDBACK.getMethod(), Rong360Config.rong360_gateway);
            if (delayResp == null || !("200".equals(delayResp.getError()) || "1023".equals(delayResp.getError()))) {
                flag = false;
            }
            // 还款成功同时推送还款计划
            if (result) {
                paymentResp = this.rongPushRepayPlan(orderNo);
                if (paymentResp == null || !"200".equals(paymentResp.getError())) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【融360-还款-还款或展期结果，还款计划推送】异常applyId={},orderNo={},推送还款或展期结果响应结果={},推送还款计划响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(delayResp), JSONObject.toJSONString(paymentResp), e);
        }
        log.debug("----------结束异步执行【融360-还款-还款或展期结果，还款计划推送】applyId={},orderNo={},推送还款或展期结果响应结果={},推送还款计划响应结果={}----------",
                applyId, orderNo, JSONObject.toJSONString(delayResp), JSONObject.toJSONString(paymentResp));
        return flag;
    }

    //@Override
    public <T> T requestHandler(Object requestOP, Class<T> responseVO, String serviceName, String url) throws Exception {
        //String reqData = JSONObject.toJSONString(requestOP);
        String reqData = JsonMapper.toJsonString(requestOP);
        log.debug("{}-请求业务数据：{}", serviceName, reqData);
        Map<String, String> params = this.createPostParam(serviceName, reqData);
        log.debug("{}-请求报文：{}", serviceName, params);

        // 请求结果响应
        String result = HttpClientUtils.postForPair(url, params);
        //log.debug("{}-请求结果响应：{}", serviceName, result);
        if (serviceName.equals(Rong360ServiceEnums.TJY_IMAGE_API_FETCH.getMethod())) {
            //融360获取图片内容接口不做json转换
            return (T) result;
        }
        if (result == null || result.length() == 0) {
            throw new Exception("Request api " + serviceName + " returns null");
        }
        // Object resp = (T) JsonMapper.fromJsonString(result, responseVO);
        T resp = JSONObject.parseObject(result, responseVO);
        if (resp == null) {
            throw new Exception("Request api " + serviceName + " got a non-json result");
        }
        return resp;
    }

    //@Override
    public Map<String, String> createPostParam(String serviceName, String reqData) throws Exception {
        Map<String, String> params = new HashMap<>();

        String privateKey = Rong360Config.rong360_private_key;
        String appId = Rong360Config.rong360_app_id;
        Rong360ServiceEnums rong360ServiceEnums = Rong360ServiceEnums.get(serviceName);

        // 拼装网关参数
        params.put("biz_data", reqData);
        params.put("method", serviceName);
        params.put("sign_type", rong360ServiceEnums.getSignType());
        params.put("version", rong360ServiceEnums.getVersion());
        params.put("format", "json");
        params.put("app_id", appId);
        params.put("timestamp", String.valueOf(new Date().getTime() / 1000));

        //sign处理 RSA加密
        String paramsStr = CommonUtil.getSortParams(params);
        byte[] bytes = RSAUtils.generateSHA1withRSASigature(paramsStr, privateKey, "UTF-8");
        String sign = Base64Utils.encode(bytes);
        params.put("sign", sign);

        return params;
    }

    @Override
    public Map<String, String> createPostParam(ThirdApiDTO thirdApiDTO) throws Exception {
        return null;
    }
}
