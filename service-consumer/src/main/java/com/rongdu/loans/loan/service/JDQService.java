package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.jdq.CalculateOP;
import com.rongdu.loans.loan.option.jdq.CardInfoOP;
import com.rongdu.loans.loan.option.jdq.CheckUserOP;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.JDQResp;
import com.rongdu.loans.loan.option.jdq.RepaymentOP;
import com.rongdu.loans.loan.option.jdq.WithdrawOP;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.vo.jdq.CardInfoVO;
import com.rongdu.loans.loan.vo.jdq.CheckUserVO;
import com.rongdu.loans.loan.vo.jdq.JDQCalculateInfoVO;

/**
 * Created by lee on 2018/10/11.
 */
public interface JDQService {

    CheckUserVO checkUser(CheckUserOP checkUserOP);

    JDQResp intoOrder(String partnerDecode, String type, String channelCode);

    boolean saveIntoOrder(IntoOrder intoOrder, String type);

    TaskResult saveUserAndApplyInfo();

    IntoOrder getPushBaseData(String orderSn);

    boolean isExistApplyId(String applyId);

    boolean isExistApplyId(String applyId, String status);

    int insertTripartiteOrder(String applyId, String orderSn, String status);
    
    JDQCalculateInfoVO calculate(CalculateOP calculateOP);
    
    JDQResp repayment(RepaymentOP repaymentOP);
    
    JDQResp bindCard(String partnerDecode);
    
    void withdraw(WithdrawOP withdrawOP);
    
    CardInfoVO cardInfo(CardInfoOP cardInfoOP);
    
    String getOrderNo(String applyId);

    String getApplyId(String orderNo);
    
    List<String> findThirdIdsByApplyIds(List<String> applyIds);
    
    TaskResult orderStatusFeedbackToRedis();
    
    JDQReport getReportData(String orderNo);

    IntoOrder getPushBaseTwoData(String orderSn);
}
