package com.rongdu.loans.tongrong.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.tongrong.op.TongrongPayLogOP;
import com.rongdu.loans.tongrong.vo.TRPayVO;

public interface TRPayService {

	public TRPayVO pay(String applyId);
	
	public Page findList(Page page, TongrongPayLogOP op);
	
	/**
	 * 通融 手动放款
	 * @param payLogId
	 * @return
	 */
	public AdminWebResult adminPay(String payLogId);
	
	/**
	 * 
	* @Title: adminCancel
	* @Description: 通融 管理员取消订单
	* @return AdminWebResult    返回类型
	* @throws
	 */
	public AdminWebResult adminCancel(String payLogId);
	
}
