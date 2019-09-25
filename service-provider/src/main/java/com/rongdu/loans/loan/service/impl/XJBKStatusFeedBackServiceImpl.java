package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.XJ360FQUtil;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.option.xjbk.ApproveFeedbackOP;
import com.rongdu.loans.loan.option.xjbk.AuthFeedBackOP;
import com.rongdu.loans.loan.option.xjbk.LendingFeedback;
import com.rongdu.loans.loan.option.xjbk.RepayStatusFeedback;
import com.rongdu.loans.loan.option.xjbk.ThirdResponse;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.XJBKPushFeedBackService;
import com.rongdu.loans.loan.service.XJBKStatusFeedBackService;

import lombok.extern.slf4j.Slf4j;

/**  
* @Title: XJBKStatusFeedBackServiceImpl.java  
* @Package com.rongdu.loans.loan.service.impl  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年8月20日  
* @version V1.0  
*/
@Slf4j
@Service("xjbkStatusFeedBackService")
public class XJBKStatusFeedBackServiceImpl implements XJBKStatusFeedBackService {
	
	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private XJBKPushFeedBackService xjbkPushFeedBackService;
	@Autowired
	private LoanApplyManager loanApplyManager;
	
	@Override
	public void xjbkApproveFeedBack(String applyId) {
		log.info("----------开始执行【现金白卡-审批结果回调】请求----------");
		String orderNo = applyTripartiteService.getThirdIdByApplyId(applyId);
		ApproveFeedbackOP approveFeedbackOP = pullApproveFeedBack(orderNo);
		if (StringUtils.isBlank(approveFeedbackOP.getApproveStatus())) {
			log.info("----------撤销执行【现金白卡-审批结果回调】请求----------");
			return;
		}
		
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
            String call = "Partner.Order.approveFeedback";
            String response = XJ360FQUtil.XianJinBaiKaRequest(approveFeedbackOP, call);
            thirdResponse = JSON.parse(response, ThirdResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
		log.debug("工单：{}，现金白卡-审批结果回调结果：{}", orderNo, thirdResponse);
		log.info("----------结束执行【现金白卡-审批结果回调】----------");
	}
	
	private ApproveFeedbackOP pullApproveFeedBack(String orderNo) {
		String applyId = applyTripartiteService.getApplyIdByThirdId(orderNo);
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		ApproveFeedbackOP approveFeedbackOP = new ApproveFeedbackOP();
        approveFeedbackOP.setOrderSn(orderNo);
        approveFeedbackOP.setApproveTerm(String.valueOf(loanApply.getApproveTerm()));
        approveFeedbackOP.setApproveAmount(loanApply.getApplyAmt().multiply(BigDecimal.valueOf(100)));
        Integer status = loanApply.getStatus();
        if (ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(status)) {
            approveFeedbackOP.setApproveStatus("200");
            approveFeedbackOP.setApproveRemark("ok");
		} else if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(status)
				|| ApplyStatusLifeCycleEnum.CANCAL.getValue().equals(status)) {
			approveFeedbackOP.setApproveStatus("403");
			approveFeedbackOP.setApproveRemark("评分不足");
		}
        approveFeedbackOP.setCanLoanTime(DateUtils.formatDate(
                DateUtils.addDay(loanApply.getApplyTime(), 30), "yyyy-MM-dd HH:mm:ss"));
        approveFeedbackOP.setUpdatedAt(String.valueOf(new Date().getTime()));
        approveFeedbackOP.setTermType("1");
        return approveFeedbackOP;
	}

	@Override
	public void xjbkLendingFeedBack(String applyId, boolean result){
		log.info("----------开始执行【现金白卡-放款结果回调】请求----------");
		String orderNo = applyTripartiteService.getThirdIdByApplyId(applyId);
		LendingFeedback lendingFeedback = new LendingFeedback();
		if (result) {
			lendingFeedback = pullLendingFeedback(orderNo, Global.XJBK_SUCCESS);
			//放款成功同时推送还款计划
			xjbkPushFeedBackService.pushRepayPlan(orderNo);
		} else {
			lendingFeedback = pullLendingFeedback(orderNo, Global.XJBK_FAIL);
		}
		
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
            String call = "Partner.Order.lendingFeedback";
            String response = XJ360FQUtil.XianJinBaiKaRequest(lendingFeedback, call);
            thirdResponse = JSON.parse(response, ThirdResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
		log.debug("工单：{}，现金白卡-放款结果回调结果：{}", orderNo, thirdResponse);
		log.info("----------结束执行【现金白卡-放款结果回调】----------");
	}
	
	@Override
	public void xjbkRepayStatusFeedBack(String repayPlanItemId, String applyId, boolean result){
		log.info("----------开始执行【现金白卡-还款结果回调】请求----------");
		String orderNo = applyTripartiteService.getThirdIdByApplyId(applyId);
		RepayStatusFeedback repayStatusFeedback = new RepayStatusFeedback();
		if (result) {
			repayStatusFeedback = pullRepayStatusFeedback(orderNo, Global.XJBK_SUCCESS, "ok");
			//还款成功同时推送还款计划
			xjbkPushFeedBackService.pushRepayPlan(orderNo);
		} else {
			repayStatusFeedback = pullRepayStatusFeedback(orderNo, Global.XJBK_FAIL, "");
		}
		
		ThirdResponse thirdResponse = new ThirdResponse();
        try {
            String call = "Partner.Order.repayStatusFeedback";
            String response = XJ360FQUtil.XianJinBaiKaRequest(repayStatusFeedback, call);
            thirdResponse = JSON.parse(response, ThirdResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("工单：{} ，现金白卡-还款结果回调结果：{}", orderNo, thirdResponse);
		log.info("----------结束执行【现金白卡-还款结果回调】----------");
	}
	
	private LendingFeedback pullLendingFeedback(String orderSn, String status) {
		LendingFeedback lendingFeedback = new LendingFeedback();
		lendingFeedback.setOrderSn(orderSn);
		lendingFeedback.setUpdatedAt(String.valueOf(new Date().getTime()));
		if (status.equals(Global.XJBK_SUCCESS)) {
			lendingFeedback.setFailReason("ok");
			lendingFeedback.setLendingStatus("200");
		}
		if (status.equals(Global.XJBK_FAIL)) {
			lendingFeedback.setFailReason(Global.XJBK_FAIL);
			lendingFeedback.setLendingStatus("401");
		}
		return lendingFeedback;
	}
	
	private RepayStatusFeedback pullRepayStatusFeedback(String orderSn, String status, String msg) {
		RepayStatusFeedback repayStatusFeedback = new RepayStatusFeedback();
		repayStatusFeedback.setOrderSn(orderSn);
		repayStatusFeedback.setUpdatedAt(String.valueOf(new Date().getTime()));
		if (status.equals(Global.XJBK_SUCCESS)) {
			repayStatusFeedback.setFailReason(msg);
			repayStatusFeedback.setRepayResult("200");
		}
		if (status.equals(Global.XJBK_FAIL)) {
			repayStatusFeedback.setFailReason(msg);
			repayStatusFeedback.setRepayResult("505");
		}
		return repayStatusFeedback;
	}
	
	@Override
	public void xjbkAuthFeedback(String applyId, boolean result) {
		log.info("----------开始执行【现金白卡-H5认证结果回调】请求----------");
		AuthFeedBackOP authFeedBackOP = new AuthFeedBackOP();
		String orderNo = applyTripartiteService.getThirdIdByApplyId(applyId);
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		authFeedBackOP.setUserIdcard(loanApply.getIdNo());
		authFeedBackOP.setUserName(loanApply.getUserName());
		authFeedBackOP.setUserPhone(loanApply.getMobile());
		authFeedBackOP.setAuthType("8");
		authFeedBackOP.setAuthStatus("200");
		authFeedBackOP.setSuccessTime(String.valueOf(System.currentTimeMillis() / 1000));
		if (!result) {
			authFeedBackOP.setAuthStatus("401");
		}
		
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			String call = "Partner.Order.authFeedback";
			String response = XJ360FQUtil.XianJinBaiKaRequest(authFeedBackOP, call);
            thirdResponse = JSON.parse(response, ThirdResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("工单：{} ，现金白卡-H5认证结果回调结果：{}", orderNo, thirdResponse);
		log.info("----------结束执行【现金白卡-H5认证结果回调】----------");
	}
	
	@Override
	public void xjbkOverdueStatusFeedBack(String orderNo) {
		log.info("----------开始执行【现金白卡-订单逾期】推送----------");
		//订单逾期，推送还款计划
		xjbkPushFeedBackService.pushRepayPlan(orderNo);
		log.info("----------结束执行【现金白卡-订单逾期】----------");
	}
}
