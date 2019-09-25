package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.SLL.CardBindOP;
import com.rongdu.loans.loan.option.SLL.SLLFeedbackResp;
import com.rongdu.loans.loan.option.SLL.SLLResp;
import com.rongdu.loans.loan.vo.sll.ConclusionPullVO;
import com.rongdu.loans.loan.vo.sll.OrderRepayplanDetailVO;
import com.rongdu.loans.loan.vo.sll.OrderStatusPullVO;
import com.rongdu.loans.loan.vo.sll.RepaymentPlanPullVO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
public interface SLLStatusFeedBackService {

    /**
     * 拉取订单状态
     */
    OrderStatusPullVO pullOrderStatus(String orderNo);

    /**
     * 拉取审批结论
     */
    ConclusionPullVO pullConclusion(String orderNo);

    /**
     * 拉取还款计划
     */
    RepaymentPlanPullVO pullRepaymentPlan(String orderNo);

    /**
     * 拉取还款状态
     */
    List<OrderRepayplanDetailVO> pullPaymentPlanDetail(String orderNo, List<String> termList);

    /**
     * 绑卡验证反馈
     */
    SLLFeedbackResp bindcardfeedback(CardBindOP cardBindOP, SLLResp sllResp) throws Exception;

    /**
     * 审批结论反馈
     */
    boolean approveFeedBack(String applyId);

    /**
     * 放款反馈
     */
    boolean lendFeedBack(String applyId);

    /**
     * 逾期反馈
     */
    boolean overdueFeedBack(String applyId);

    /**
     * 还款反馈
     */
    boolean settlementFeedBack(String applyId,String repayPlanItemId);

    boolean orderfeedback(String orderNo, boolean flag) throws Exception;

}
