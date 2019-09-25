package com.jubao.cps.impl;

import com.alibaba.fastjson.JSONObject;
import com.jubao.cps.service.RequestDefaultHandler;
import com.jubao.cps.utils.CpsUtil;
import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.CostUtils;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StandardDesUtil;
import com.rongdu.loans.common.sll.SLLUtil;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.SLLServiceEnums;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.option.SLL.CardBindOP;
import com.rongdu.loans.loan.option.SLL.SLLFeedbackResp;
import com.rongdu.loans.loan.option.SLL.SLLResp;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.loan.vo.sll.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("sllStatusFeedBackService")
public class SLLStatusFeedBackServiceImpl extends RequestDefaultHandler implements SLLStatusFeedBackService {

    @Autowired
    private SLLService sllService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private BorrowInfoService borrowInfoService;

    @Override
    public OrderStatusPullVO pullOrderStatus(String orderNo) {
        log.debug("----------开始拉取【奇虎360-订单状态】orderNo={}----------", orderNo);
        String applyId = sllService.getApplyId(orderNo);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        OrderStatusPullVO orderStatusPullVO = new OrderStatusPullVO();
        if (loanApply == null) {
            // 还未生成订单
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(0);
            return orderStatusPullVO;
        }
        Integer status = loanApply.getStatus();
        String key = null;// 确认借款标识
        String key2 = null;// 待开户标识
        String key3 = null;// 开户成功标识
        if (loanApply.getPayChannel() != null && WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(loanApply.getPayChannel())) {
            // 汉金所存管
            key = JedisUtils.get(Global.HJS_OPEN1 + applyId);
            key2 = JedisUtils.get(Global.HJS_OPEN2 + applyId);
            key3 = JedisUtils.get(Global.HJS_OPEN3 + applyId);
        } else if (loanApply.getPayChannel() != null && WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(loanApply.getPayChannel())) {
            // 口袋存管
            key = JedisUtils.get(Global.KD_OPEN1 + applyId);
            key2 = JedisUtils.get(Global.KD_OPEN2 + applyId);
            key3 = JedisUtils.get(Global.KD_OPEN3 + applyId);
        }
        if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status) || ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue().equals(status)) {
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(100);// 审核通过
            Criteria criteria = new Criteria();
            criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
            criteria.and(Criterion.eq("apply_id", applyId));
            BorrowerInfoVO borrowInfo = borrowInfoService.getByCriteria(criteria);
            if (key2 != null && key3 == null) {
                orderStatusPullVO.setOrderNo(orderNo);
                orderStatusPullVO.setOrderStatus(165);// 待开户
                orderStatusPullVO.setUpdateTime(String.valueOf(new Date().getTime() / 1000));
            }
            if (borrowInfo != null || (key != null && key2 == null)) {
                orderStatusPullVO.setOrderStatus(160);// 160贷款确认
                BigDecimal servFee = loanApply.getServFee();
                orderStatusPullVO.setLoanAmount(loanApply.getApproveAmt());//用户确认的借款金额
                orderStatusPullVO.setLoanTerm(loanApply.getApproveTerm());//用户确认的借款期限
                orderStatusPullVO.setReceiveAmount(CostUtils.calToAccountAmt(loanApply.getApproveAmt(), servFee));
                //用户卡中收到款的金额
                orderStatusPullVO.setServiceFee(servFee);//放款时预扣除手续费
                orderStatusPullVO.setPayAmount(CostUtils.calRealRepayAmt(loanApply.getApproveAmt(),
                        loanApply.getInterest()));//用户的总还款额
                if (loanApply.getPayChannel() != null && WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(loanApply.getPayChannel())
                && key != null) {
                    JedisUtils.set(Global.HJS_OPEN2 + applyId, "lock", Global.THREE_DAY_CACHESECONDS);
                }else if (loanApply.getPayChannel() != null && WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(loanApply.getPayChannel())
                && key != null) {
                    JedisUtils.set(Global.KD_OPEN2 + applyId, "lock", Global.THREE_DAY_CACHESECONDS);
                }
            }
            if (key3 != null) {
                orderStatusPullVO.setOrderNo(orderNo);
                orderStatusPullVO.setOrderStatus(166);// 开户成功
                orderStatusPullVO.setUpdateTime(String.valueOf(new Date().getTime() / 1000));
            }
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)) {
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(110);// 审核不通过
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().equals(status)) {
            // 放款成功
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(170);// 放款成功
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
            // 贷款取消
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(161);// 贷款取消
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
            if (key3 != null) {
                // 已去存管开过户
                orderStatusPullVO.setOrderStatus(171);// 放款退标
            }
        } else if (ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY.getValue().equals(status)) {
            // 贷款逾期
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(180);// 贷款逾期
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.REPAY.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.OVERDUE_REPAY.getValue().equals(status)) {
            // 贷款结清
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(200);// 贷款结清
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(1);// 审批处理中
            orderStatusPullVO.setUpdateTime(String.valueOf(loanApply.getUpdateTime().getTime() / 1000));
        } else if (ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL.getValue().equals(status)) {
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(170);// 待提现
            orderStatusPullVO.setWithDrawals("1");
            orderStatusPullVO.setUpdateTime(String.valueOf(new Date().getTime() / 1000));
        } else {
            orderStatusPullVO.setOrderNo(orderNo);
            orderStatusPullVO.setOrderStatus(0);// 未知状态
            orderStatusPullVO.setUpdateTime(String.valueOf(new Date().getTime() / 1000));
        }
        log.debug("----------结束拉取【奇虎360-订单状态】orderNo={}----------{}", orderNo, orderStatusPullVO);
        return orderStatusPullVO;
    }

    @Override
    public ConclusionPullVO pullConclusion(String orderNo) {
        log.debug("----------开始拉取【奇虎360-审批状态】orderNo={}----------", orderNo);
        String applyId = sllService.getApplyId(orderNo);
        //LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        ConclusionPullVO conclusionPullVO = new ConclusionPullVO();
        if (loanApply == null) {
            // 还未生成订单
            conclusionPullVO.setOrderNo(orderNo);
            conclusionPullVO.setConclusion(1);// 审批处理中
            return conclusionPullVO;
        }
        Integer status = loanApply.getStatus();
        if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
            conclusionPullVO.setOrderNo(orderNo);
            conclusionPullVO.setConclusion(40);// 审批不通过
            conclusionPullVO.setRefuseTime(String.valueOf((loanApply.getApproveTime() == null ?
                    loanApply.getUpdateTime() : loanApply.getApproveTime()).getTime() / 1000));
            conclusionPullVO.setRemark(loanApply.getRemark() == null ? "系统取消" : loanApply.getRemark());
        } else if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)
                || status.intValue() > ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().intValue()) {
            conclusionPullVO.setOrderNo(orderNo);
            conclusionPullVO.setConclusion(10);// 审批通过
            conclusionPullVO.setAmountType(0);// 0=固定金额
            conclusionPullVO.setTermUnit(loanApply.getTerm(), loanApply.getRepayFreq());// 单期/多期产品，1=单期产品
            conclusionPullVO.setTermType(0);// 0=固定期限
            conclusionPullVO.setApprovalTime(String.valueOf(loanApply.getApproveTime().getTime() / 1000));
            conclusionPullVO.setApprovalAmount(loanApply.getApproveAmt().intValue());// 审批金额
            conclusionPullVO.setApprovalTerm(loanApply.getApproveTerm());// 审批期限
            conclusionPullVO.setServiceFee(loanApply.getServFee());// 服务费

        } else if (ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue().equals(status)
                || ApplyStatusLifeCycleEnum.MANUAL_RECHECK.getValue().equals(status)) {
            conclusionPullVO.setOrderNo(orderNo);
            conclusionPullVO.setConclusion(1);// 审批处理中
        }
        log.debug("----------结束拉取【奇虎360-审批状态】orderNo={}----------{}", orderNo,
                JSONObject.toJSONString(conclusionPullVO));
        return conclusionPullVO;
    }

    @Override
    public RepaymentPlanPullVO pullRepaymentPlan(String orderNo) {
        log.debug("----------开始拉取【奇虎360-还款计划】orderNo={}----------", orderNo);
        String applyId = sllService.getApplyId(orderNo);
        //LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        ApplyListVO loanApply = loanApplyService.getBaseLoanApplyById(applyId);
        CustUserVO custUser = custUserService.getCustUserById(loanApply.getUserId());
        //LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        //LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);

        // 还款计划推送数据组合
        // 第一步：基础数据组合
        RepaymentPlanPullVO repaymentPlanPullVO = new RepaymentPlanPullVO();
        repaymentPlanPullVO.setOrderNo(orderNo);// 三方订单号
        repaymentPlanPullVO.setBankCard(custUser.getCardNo());// 银行卡号
        repaymentPlanPullVO.setOpenBank(custUser.getBankCode());// 银行名称

        //RepayDetailListOP op = new RepayDetailListOP();
        //op.setContNo(repayPlan.getContNo());
        //op.setIsDelaySettlement(2);
        //List<RepayDetailListVO> rpayDetailListVOList = repayPlanItemManager.repayDetailList(null, op);
        //List<RepayPlanItem> planItems = repayPlanItemManager.getByApplyIdForApp(applyId);
        List<RepayDetailListVO> planItems = repayPlanItemService.getByApplyIdExecludeDelay(applyId);
        // 第二步：还款计划数据组合
        List<RepaymentPlanDetail> list = new ArrayList<>();
        for (RepayDetailListVO planItem : planItems) {
            RepaymentPlanDetail detail = new RepaymentPlanDetail();
            detail.setPeriodNo(String.valueOf(planItem.getThisTerm()));//期数
            detail.setBillStatus(planItem.getStatus(), planItem.getRepayDate());// 账单状态 1未到期；2已还款；3逾期
            detail.setDueTime(String.valueOf(planItem.getRepayDate().getTime() / 1000));// 到期时间
            detail.setCanRepayTime(String.valueOf(planItem.getCreateTime().getTime() / 1000));// 可以还款时间
            detail.setPayType(5);//5=主动还款+银行代扣
            detail.setPaidAmount(planItem.getActualRepayAmt() == null ? BigDecimal.ZERO :
                    planItem.getActualRepayAmt());// 已还金额
            detail.setAmount(planItem.getTotalAmount().subtract(detail.getPaidAmount()));// 剩余待还金额
            detail.setOverdueFee(planItem.getOverdueFee() == null ? BigDecimal.ZERO : planItem.getOverdueFee());//逾期费用
            Integer overdue = null;
            if (planItem.getStatus().equals(1)) {
                overdue = DateUtils.daysBetween(planItem.getRepayDate(), planItem.getActualRepayTime());
                overdue = overdue > 0 ? overdue : 0;
            } else {
                overdue = DateUtils.daysBetween(planItem.getRepayDate(), new Date());
                overdue = overdue > 0 ? overdue : 0;
            }
            detail.setOverdueDay(overdue);//账单已经逾期的天数
            detail.setSuccessTime(planItem.getActualRepayTime() == null ? ""
                    : String.valueOf(planItem.getActualRepayTime().getTime() / 1000));
            detail.setRemark("");
            if (detail.getBillStatus() == 2) {
                detail.setRemark("含本金" + planItem.getPrincipal() + "元，利息&手续费" + planItem.getInterest() + "元，逾期" + planItem.getOverdueFee()
                        + "元");
            }
            list.add(detail);
        }
        repaymentPlanPullVO.setRepaymentPlan(list);
        log.debug("----------结束拉取【奇虎360-还款计划】oredrNo={}----------{}", orderNo,
                JSONObject.toJSONString(repaymentPlanPullVO));
        return repaymentPlanPullVO;
    }

    @Override
    public List<OrderRepayplanDetailVO> pullPaymentPlanDetail(String orderNo, List<String> termList) {
        String applyId = sllService.getApplyId(orderNo);
        log.debug("----------开始拉取【奇虎360-还款详情】orderNo={},applyId={},termList={}----------", orderNo, applyId, termList);
        //OrderRepayplanDetailVO vo = new OrderRepayplanDetailVO();

        //RepayPlanItem repayPlanItem = repayPlanItemManager.getById(repayPlanItemId);
        List<RepayPlanDetailVO> repayPlanDetailVOS = repayPlanItemService.getByTerms(applyId, termList);
        List<OrderRepayplanDetailVO> list = new ArrayList<>();
        for (RepayPlanDetailVO repayPlanDetailVO : repayPlanDetailVOS) {
            OrderRepayplanDetailVO vo = new OrderRepayplanDetailVO();
            vo.setPeriodNos(repayPlanDetailVO.getThisTerm().toString());
            vo.setOverdueAmount(repayPlanDetailVO.getOverdueFee());
            BigDecimal paidAmount = repayPlanDetailVO.getActualRepayAmt() == null ? BigDecimal.ZERO :
                    repayPlanDetailVO.getActualRepayAmt();
            vo.setAmount(repayPlanDetailVO.getTotalAmount().subtract(paidAmount));
            list.add(vo);
        }
        log.debug("----------结束拉取【奇虎360-还款详情】orderNo={},applyId={},termList={}----------{}", orderNo, applyId, termList,
                JSONObject.toJSONString(list));
        return list;
    }

    public RepayFeedbackVO pullRepayStatus(String orderNo, String repayPlanItemId) {
        log.debug("----------开始拉取【奇虎360-还款状态】orderNo={},repayPlanItemId={}----------", orderNo, repayPlanItemId);
        RepayFeedbackVO vo = new RepayFeedbackVO();

        RepayDetailListVO repayPlanItem = repayPlanItemService.getByRepayPlanItemId(repayPlanItemId);
        if (repayPlanItem == null) {
            log.error("【奇虎360-查询还款状态接口】异常-用户还款计划不存在repayPlanItemId={},orderNo={}", repayPlanItemId, orderNo);
            return null;
        }
        RepayLogVO repayLogVO = repayLogService.findByRepayPlanItemId(repayPlanItemId);
        if (repayLogVO == null) {
            log.warn("【奇虎360-查询还款状态接口】异常-用户尚无还款记录repayPlanItemId={},orderNo={}", repayPlanItemId, orderNo);
            return null;
        }

        vo.setOrderNo(orderNo);
        vo.setPeriodNos(String.valueOf(repayPlanItem.getThisTerm()));
        vo.setRepayAmount(repayPlanItem.getTotalAmount());
        if ("SUCCESS".equals(repayLogVO.getStatus())) {
            vo.setRepayStatus(1);//1=还款成功
        } else if (CpsUtil.isUnsolved(repayLogVO.getStatus())) {
            vo.setRepayStatus(0);//0=还款中
        } else {
            vo.setRepayStatus(2);//2=还款失败
        }
        if ("AUTH_PAY".equals(repayLogVO.getTxType())) {
            vo.setRepayPlace(1);//1=主动还款
        } else {
            vo.setRepayPlace(2);//2=自动代扣
        }
        vo.setSuccess_time(String.valueOf(repayLogVO.getTxTime() == null ? "" :
                repayLogVO.getTxTime().getTime() / 1000));
        log.debug("----------结束拉取【奇虎360-还款状态】orderNo={},repayPlanItemId={}----------{}", orderNo, repayPlanItemId,
                JSONObject.toJSONString(vo));
        return vo;
    }


    @Override
    public SLLFeedbackResp bindcardfeedback(CardBindOP cardBindOP, SLLResp sllResp) throws Exception {
        CardBindVO vo = new CardBindVO();
        String applyId = sllService.getApplyId(cardBindOP.getOrderNo());
        ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
        log.debug("----------开始异步执行【奇虎360-绑卡结果推送】applyId={},orderNo={}----------", applyId, cardBindOP.getOrderNo());
        vo.setOrderNo(cardBindOP.getOrderNo());
        //vo.setBankCard(bankVerifyOP.getBankCard());
        if (sllResp != null && SLLResp.SUCCESS.equals(sllResp.getCode())) {
            vo.setBindStatus(1);
        } else {
            vo.setBindStatus(2);
            vo.setReason(sllResp.getMsg());
        }
        String appId = SLLUtil.appId;
        if ("SLLAPIJHH".equals(applyAllotVO.getChannelId())) {
            appId = SLLUtil.jhhappId;
        }
        SLLFeedbackResp resp = this.requestHandler(vo, SLLFeedbackResp.class,
                SLLServiceEnums.SLL_BINDCARDFEEDBACK.getMethod(),
                SLLUtil.gateWay, appId);
        log.debug("----------结束异步执行【奇虎360-绑卡结果推送】applyId={},orderNo={},响应结果={}----------", applyId,
                cardBindOP.getOrderNo(), JSONObject.toJSONString(resp));
        return resp;
    }

    @Override
    public boolean approveFeedBack(String applyId) {
        SLLFeedbackResp approveResp = null;//推送审批结论 响应结果
        SLLFeedbackResp orderResp = null;//推送订单状态 响应结果
        String orderNo = sllService.getOrderNo(applyId);
        ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始异步执行【奇虎360-审批结论，订单状态推送】applyId={},orderNo={}----------", applyId, orderNo);
            ConclusionPullVO conclusionPullVO = this.pullConclusion(orderNo);
            if (1 == conclusionPullVO.getConclusion()) {
                log.debug("----------撤销异步执行【奇虎360-审批结论，订单状态推送】applyId={},orderNo={}----------", applyId, orderNo);
                return flag;
            }
            String appId = SLLUtil.appId;
            if ("SLLAPIJHH".equals(applyAllotVO.getChannelId())) {
                appId = SLLUtil.jhhappId;
            }
            // 推送审批结论
            approveResp = this.requestHandler(conclusionPullVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_APPROVEFEEDBACK.getMethod(),
                    SLLUtil.gateWay, appId);
            if (approveResp == null || approveResp.getError() == null || 200 != approveResp.getError() && 1 != approveResp.getError()) {
                flag = false;
            }
            // 推送订单状态
            OrderStatusPullVO orderStatusPullVO = this.pullOrderStatus(orderNo);
            orderResp = this.requestHandler(orderStatusPullVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_ORDERFEEDBACK.getMethod(),
                    SLLUtil.gateWay, appId);
            if (orderResp == null || orderResp.getError() == null || 200 != orderResp.getError() && 1 != orderResp.getError()) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【奇虎360-审批结论，订单状态推送】异常applyId={},orderNo={},推送审批结论响应结果={},推送订单状态响应结果={}----------",
                    applyId, orderNo,
                    JSONObject.toJSONString(approveResp), JSONObject.toJSONString(orderResp), e);
        }
        log.debug("----------结束异步执行【奇虎360-审批结论，订单状态推送】applyId={},orderNo={},推送审批结论响应结果={},推送订单状态响应结果={}----------",
                applyId, orderNo,
                JSONObject.toJSONString(approveResp), JSONObject.toJSONString(orderResp));
        return flag;
    }

    @Override
    public boolean lendFeedBack(String applyId) {
        SLLFeedbackResp orderResp = null;// 推送订单状态 响应结果
        SLLFeedbackResp planResp = null;// 推送还款计划 响应结果
        String orderNo = sllService.getOrderNo(applyId);
        ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始异步执行【奇虎360-订单状态，还款计划推送】applyId={},orderNo={}----------", applyId, orderNo);
            String appId = SLLUtil.appId;
            if ("SLLAPIJHH".equals(applyAllotVO.getChannelId())) {
                appId = SLLUtil.jhhappId;
            }
            // 推送订单状态
            OrderStatusPullVO orderStatusPullVO = this.pullOrderStatus(orderNo);
            orderResp = this.requestHandler(orderStatusPullVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_ORDERFEEDBACK.getMethod(),
                    SLLUtil.gateWay, appId);
            if (orderResp == null || orderResp.getError() == null || 200 != orderResp.getError() && 1 != orderResp.getError()) {
                flag = false;
            }
            if (orderStatusPullVO.getOrderStatus() >= 170 && !"1".equals(orderStatusPullVO.getWithDrawals())){
                // 放款成功同时推送还款计划
                RepaymentPlanPullVO repaymentPlanPullVO = this.pullRepaymentPlan(orderNo);
                planResp = this.requestHandler(repaymentPlanPullVO, SLLFeedbackResp.class,
                        SLLServiceEnums.SLL_PUSHREPAYMENT.getMethod(), SLLUtil.gateWay, appId);
                if (planResp == null || planResp.getError() == null || 200 != planResp.getError() && 1 != planResp.getError()) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【奇虎360-订单状态，还款计划推送】异常applyId={},orderNo={},推送订单状态响应结果={},推送还款计划响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(planResp), e);
        }
        log.debug("----------结束异步执行【奇虎360-订单状态，还款计划推送】flag={},applyId={},orderNo={},推送订单状态响应结果={}," +
                        "推送还款计划响应结果={}----------",
                flag, applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(planResp));
        return flag;
    }

    @Override
    public boolean overdueFeedBack(String applyId) {
        return lendFeedBack(applyId);
    }

    @Override
    public boolean settlementFeedBack(String applyId, String repayPlanItemId) {
        SLLFeedbackResp orderResp = null;// 推送订单状态 响应结果
        SLLFeedbackResp payStatusResp = null;// 推送还款状态 响应结果
        SLLFeedbackResp planResp = null;// 推送还款计划 响应结果
        String orderNo = sllService.getOrderNo(applyId);
        ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);

        boolean flag = true;
        try {
            log.debug("----------开始异步执行【奇虎360-订单状态，还款状态，还款计划推送】applyId={},orderNo={}----------", applyId, orderNo);
            String appId = SLLUtil.appId;
            if ("SLLAPIJHH".equals(applyAllotVO.getChannelId())) {
                appId = SLLUtil.jhhappId;
            }
            // 推送订单状态
            OrderStatusPullVO orderStatusPullVO = this.pullOrderStatus(orderNo);
            if (orderStatusPullVO.getOrderStatus() != 170){
                // 奇虎重复推170异常？
                orderResp = this.requestHandler(orderStatusPullVO, SLLFeedbackResp.class,
                        SLLServiceEnums.SLL_ORDERFEEDBACK.getMethod(),
                        SLLUtil.gateWay, appId);
                if (orderResp == null || orderResp.getError() == null || 200 != orderResp.getError() && 1 != orderResp.getError()) {
                    flag = false;
                }
            }
            // 推送还款状态
            RepayFeedbackVO repayFeedbackVO = this.pullRepayStatus(orderNo, repayPlanItemId);
            payStatusResp = this.requestHandler(repayFeedbackVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_REPAYFEEDBACK.getMethod(), SLLUtil.gateWay, appId);
            if (payStatusResp == null || payStatusResp.getError() == null || 200 != payStatusResp.getError() && 1 != payStatusResp.getError()) {
                flag = false;
            }
            // 推送还款计划
            RepaymentPlanPullVO repaymentPlanPullVO = this.pullRepaymentPlan(orderNo);
            planResp = this.requestHandler(repaymentPlanPullVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_PUSHREPAYMENT.getMethod(), SLLUtil.gateWay, appId);
            if (planResp == null || planResp.getError() == null || 200 != planResp.getError() && 1 != planResp.getError()) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error(
                    "----------【奇虎360-订单状态，还款状态，还款计划推送】异常applyId={},orderNo={},推送订单状态响应结果={},推送还款状态响应结果={}," +
                            "推送还款计划响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(payStatusResp),
                    JSONObject.toJSONString(planResp), e);
        }
        log.debug(
                "----------结束异步执行【奇虎360-订单状态，还款状态，还款计划推送】applyId={},orderNo={},推送订单状态响应结果={},推送还款状态响应结果={}," +
                        "推送还款计划响应结果={}----------",
                applyId, orderNo, JSONObject.toJSONString(orderResp), JSONObject.toJSONString(payStatusResp),
                JSONObject.toJSONString(planResp));
        return flag;
    }

    @Override
    public boolean orderfeedback(String orderNo, boolean flag) throws Exception {
        // 推送订单状态
        String applyId = sllService.getApplyId(orderNo);
        log.debug("----------开始异步执行【奇虎360-订单状态推送】applyId={},orderNo={}----------", applyId, orderNo);
        SLLFeedbackResp orderResp = null;
        try {
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            String appId = SLLUtil.appId;
            if ("SLLAPIJHH".equals(applyAllotVO.getChannelId())) {
                appId = SLLUtil.jhhappId;
            }
            OrderStatusPullVO orderStatusPullVO = this.pullOrderStatus(orderNo);
            orderResp = this.requestHandler(orderStatusPullVO, SLLFeedbackResp.class,
                    SLLServiceEnums.SLL_ORDERFEEDBACK.getMethod(),
                    SLLUtil.gateWay, appId);
            if (orderResp == null || orderResp.getError() == null || 200 != orderResp.getError() && 1 != orderResp.getError()) {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            log.error("----------【奇虎360-订单状态推送】异常applyId={},orderNo={},推送订单状态响应结果={}----------",
                    applyId, orderNo, JSONObject.toJSONString(orderResp),e);
        }
        log.debug("----------结束异步执行【奇虎360-订单状态推送】applyId={},orderNo={},推送订单状态响应结果={}----------",
                applyId, orderNo, JSONObject.toJSONString(orderResp));
        return flag;
    }


    @Override
    public Map<String, String> createPostParam(String serviceName, String reqData, String appid) throws Exception {
        Map<String, String> params = new HashMap<>();

        String privateKey = SLLUtil.partnerPrivateKey;
        SLLServiceEnums sllServiceEnums = SLLServiceEnums.get(serviceName);

        // 拼装网关参数
        params.put("biz_data", reqData);
        params.put("method", serviceName);
        params.put("sign_type", sllServiceEnums.getSignType());
        params.put("version", sllServiceEnums.getVersion());
        params.put("format", "json");
        params.put("app_id", appid);
        params.put("timestamp", String.valueOf(new Date().getTime() / 1000));

        String paramStr = StandardDesUtil.getSignParamStr(JSONObject.toJSONString(params));
        String sign = StandardDesUtil.sign(paramStr, privateKey);
        /*String paramsStr = CommonUtil.getSortParams(params);
        byte[] bytes = RSAUtils.generateSHA1withRSASigature(paramsStr, privateKey, "UTF-8");
        String sign = Base64Utils.encode(bytes);*/
        params.put("sign", sign);

        return params;
    }

}
