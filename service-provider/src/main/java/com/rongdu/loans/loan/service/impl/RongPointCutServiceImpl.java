package com.rongdu.loans.loan.service.impl;

import java.util.List;

import com.rongdu.loans.loan.option.SLL.CardBindOP;
import com.rongdu.loans.loan.option.SLL.SLLResp;
import com.rongdu.loans.loan.vo.OverdueVO;
import org.springframework.stereotype.Service;

import com.rongdu.loans.loan.service.RongPointCutService;

/**  
* @Title: RongPointCutServiceImpl.java  
* @Package com.rongdu.loans.loan.service.impl  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月23日  
* @version V1.0  
*/
@Service("rongPointCutService")
public class RongPointCutServiceImpl implements RongPointCutService {

	@Override
	public void repayProcessFailPoint(String repayPlanItemId) {
		
	}

	@Override
	public void overduePoint(List<String> applyIdList) {
		
	}

	@Override
	public void overduePointForAnRong(List<OverdueVO> overdueVOS) {

	}

	@Override
	public void settlementPoint(String repayPlanItemId, boolean result) {
		
	}

	@Override
	public void settlementManPayPoint(String repayPlanItemId, boolean result) {
		
	}

	@Override
	public void delayPoint(String repayPlanItemId) {
		
	}

	@Override
	public void creatAccount(String userId, String msg, boolean result) {
		
	}

	@Override
	public void lendPoint(String applyId, int flag) {

	}

	@Override
	public void sllCardBindConfirm(CardBindOP cardBindOP, SLLResp sllResp) {

	}

	@Override
	public void enSureAgreement(String applyId) {

	}

	@Override
	public void test() throws InterruptedException {
		System.out.println("lallalalalalal");
		Thread.currentThread().sleep(10000l);
		System.out.println("hahahhahahha");
	}

	@Override
	public int cancelApply(String applyId, String operatorName) {
		return 1;
	}

	@Override
	public void sllLendPoint(String applyId, int flag) {

	}

}
