package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 申请编号VO
 * @author likang
 *
 */
public class ApplyIdVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 申请编号是否新生成
	 */
	private boolean isNewApplyId = false;
	
	/**
	 * 申请编号
	 */
	private String applyId;

	public boolean isNewApplyId() {
		return isNewApplyId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setNewApplyId(boolean isNewApplyId) {
		this.isNewApplyId = isNewApplyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
}
