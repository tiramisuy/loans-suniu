package com.rongdu.loans.loan.option;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class ContentTableOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386813584730L;

	/**
	 * 合同编号
	 */
	@NotNull(message = "合同编号不能为空")
	private String contNo;
	/**
	 * 贷款申请订单ID
	 */
	@NotNull(message = "贷款申请订单号不能为空")
	private String applyId;
	/**
	 * 产品ID
	 */
	@NotNull(message = "产品编号不能为空")
	private String productId;

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
