package com.rongdu.loans.api.web.option;
/**
 * 
* @Description:  确认借款OP
* @author: RaoWenbiao
* @date 2018年12月5日
 */
public class KDComplianceBorrowPageOP {

	private String orderId;//订单ID
	private String status;//状态
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
