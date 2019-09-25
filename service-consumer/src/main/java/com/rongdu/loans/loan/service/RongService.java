
package com.rongdu.loans.loan.service;


import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.rong360Model.*;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJReportDetailResp;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJResp;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;

import java.util.Map;

public interface RongService {

    boolean saveUserAdditionalInfo(OrderAppendInfo orderAppendInfo);

    boolean saveUserBaseInfo(OrderBaseInfo orderBaseInfo);

    Rong360Resp bindCard(BindCardOP bindCardOP);

    Rong360Resp confirmBindCard(BindCardOP bindCardOP);

    TaskResult saveUserAndApplyInfo();

    OrderBaseInfo getPushBaseData(String orderSn);

    OrderAppendInfo getPushAdditionalData(String orderSn);

    Rong360Resp calculation(Calculation calculation);

    Rong360Resp confirmLoan(ApproveOP approveOP);

    Rong360Resp getRepaymentDetails(RepaymentDetailsOP repaymentDetailsOP);

    Rong360Resp applyRepay(ApplyRepayOP applyRepayOP);

    Rong360Resp delayDetails(DelayDetailsOP delayDetailsOP);

    CalculateInfo getOrderInfo(LoanApplySimpleVO loanApplySimpleVO);

    Rong360Resp applyDelay(ApplyDelayOP applyRepayOP);

    Rong360Resp getBindBankCard(GetCardOP getCardOP);

    Rong360Resp getContract(ContractOP contractOP);

    Rong360Resp isUserAccept(AcceptOP acceptOP);

    Rong360Resp getApproval(GetOP getOP);

    Rong360Resp getRepaymentPlan(GetOP getOP);

    Rong360Resp getOrderStatus(GetOP getOP);
    
    /**
    * @Title: saveRongTJReportDetail  
    * @Description: 保存融360运营商报告
     */
    boolean saveRongTJReportDetail(TianjiReportDetailResp tianjiReportDetail);
    
    /**
    * @Title: crawlGenerateReport  
    * @Description: 融天机生成报告接口 
    * @param @param orderNo 淘金云订单id
    * @param @param type 
    * @param @param notifyUrl 服务器异步通知页面路径
    * @param @param version 报告版本
     */
    RongTJResp crawlGenerateReport(String orderNo, String type, String notifyUrl, String version) throws Exception;
    
    /**
    * @Title: crawlReportDetail  
    * @Description: 融天机互联网报告信息详情接口（运营商报告）
    * @param @param searchId 
    * @param @param reportType
     */
    RongTJReportDetailResp crawlReportDetail(String searchId, String reportType) throws Exception;
    
    /**
    * @Title: crawlScore  
    * @Description: 融天机风控模型plus详情接口
    * @param @param searchId
     */
    RongTJReportDetailResp crawlScore(String searchId) throws Exception;
    
    /**
     * @Title: crawlScore  
     * @Description: 融天机风控模型详情接口
     * @param @param searchId
      */
    RongTJReportDetailResp crawlScoreplus(String searchId) throws Exception;
    
    /**
    * @Title: getRongBase  
    * @Description: 从文件系统获取融360用户基础信息 
    * @param @param userId
     */
    OrderBaseInfo getRongBase(String userId);
    
    /**
    * @Title: getRongAdditional  
    * @Description: 从文件系统获取融360用户附加信息
    * @param @param userId
     */
    OrderAppendInfo getRongAdditional(String userId);
    
    /**
    * @Title: getRongTJReportDetail  
    * @Description: 从文件系统获取融360运营商报告 
    * @param @param userId
     */
    TianjiReportDetailResp getRongTJReportDetail(String userId);
    
    String rongImageFetch(String orderNo,String token) throws Exception;
    
    boolean rongReportDetail(String orderNo,String searchId,String userId,String applyId,String state);

    void resetImage(String orderSn);

    TaskResult repayStatusFeedbackToRedis();

    TaskResult rongApproveStatusFeedBack();

    TaskResult orderStatusFeedbackOfRedis();

    TaskResult settlementFeedBackOfRedis();

    void handPush(String start, String end, int size);

    void handPush1(String applyId);

    void HandleData();

    String handRefuse(String orderNo);

    String handCancle(String orderNo);

    CreateAccountVO createAccount(String serviceUrl, Map<String, String> params, Map<String, String> headerMap);

    ConfirmWithdrawVO confirmWithdraw(String serviceUrl, Map<String, String> params, Map<String, String> headerMap);
}
