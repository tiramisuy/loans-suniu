
package com.rongdu.loans.loan.service;


import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.xjbk.EnSureAgreement;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaRepaymentPlan;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaVO;

public interface XianJinBaiKaService {

	boolean savePushUserBaseInfo(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    boolean savePushUserAdditionalInfo(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    TaskResult saveUserAndApplyInfo();

    XianJinBaiKaCommonOP getPushBaseData(String orderSn);

    XianJinBaiKaCommonOP getPushAdditionalData(String orderSn);

    TaskResult approveFeedback();

    TaskResult lendingFeedback();

    TaskResult xianJinCardCallback();

    TaskResult repayStatusFeedbackToRedis();

    TaskResult repayPlanFeedback();

    TaskResult repayStatusFeedback();

    XianJinBaiKaRepaymentPlan getRepayplan(String order_sn);

    XianJinBaiKaVO applyRepay(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO isUserAccept(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO loanCalculate(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO applyBindCard(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO confirmLoan(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO getOrderStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO getContracts(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);
    
    XianJinBaiKaVO authStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaCommonOP getXianJinBaiKaBase(String userId);

    XianJinBaiKaCommonOP getXianJinBaiKaAdditional(String userId);

    void  updateUserInfo(String orderSn, String applyId);

    XianJinBaiKaVO isUserAcceptFQ(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaVO loanCalculateFQ(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest);

    XianJinBaiKaRepaymentPlan getRepayplanFQ(String order_sn);
}
