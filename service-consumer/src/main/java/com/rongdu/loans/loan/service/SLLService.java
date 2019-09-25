package com.rongdu.loans.loan.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.SLL.*;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.vo.sll.*;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: SLLService.java
 * @Package com.rongdu.loans.loan.service
 * @author: yuanxianchu
 * @date 2018年10月29日
 */
public interface SLLService {


    boolean saveBaseInfo(BaseData intoOrder);

    boolean saveAdditionInfo(AddData intoOrder);

    String getOrderNo(String applyId);

    String getApplyId(String orderNo);

    List<String> findThirdIdsByApplyIds(List<String> applyIds);

    /**
     * 查询复贷和黑名单信息
     */
    SLLResp orderQuickLoan(QuickLoanOP quickLoanOP);

    /**
     * 推送用户绑定银行
     */
    SLLResp cardBind(CardBindOP cardBindOP);

    /**
     * 推送用户验证银行卡
     */
    SLLResp cardBindConfirm(CardBindOP cardBindOP);

    /**
     * 查询审批结论
     */
    ConclusionPullVO conclusionPull(String orderNo);

    /**
     * 推送用户确认收款信息
     */
    SLLResp conclusionConfirm(ConclusionConfirmOP conclusionConfirmOP);

    /**
     * 试算接口
     */
    OrderTrialVO orderTrial(OrderTrialOP orderTrialOP);

    /**
     * 查询还款计划
     */
    RepaymentPlanPullVO repaymentPlanPull(String orderNo);

    /**
     * 查询还款详情
     */
    List<OrderRepayplanDetailVO> repayplanDetail(RepayplanDetailOP repayplanDetailOP);

    /**
     * 推送用户还款信息
     */
    SLLResp orderRepay(OrderRepayOP orderRepayOP);

    /**
     * 查询订单状态
     */
    OrderStatusPullVO orderStatusPull(String orderNo);


    TaskResult saveUserAndApplyInfo();

    BaseData getPushBaseData(String orderSn);

    AddData getPushAdditionalData(String orderSn);

    boolean isExistApplyId(String applyId);

    int insertTripartiteOrder(String applyId, String orderSn);

    JDQReport getReportData(String orderNo);

    TaskResult approveFeedbackOfRedis();

    TaskResult lendFeedbackOfRedis();

    TaskResult settlementFeedbackOfRedis();

    TaskResult orderStatusFeedbackOfRedis();

    String resetImage(String orderNo);


    String createAccount(String url, Map<String, String> params, Map<String, String> headerMap, String payChannel);

    String withdraw(String url, Map<String, String> params, Map<String, String> headerMap, String payChannel);
}
