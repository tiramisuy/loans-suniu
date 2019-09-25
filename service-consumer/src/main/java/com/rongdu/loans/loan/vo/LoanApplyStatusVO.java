package com.rongdu.loans.loan.vo;

import java.io.Serializable;

public class LoanApplyStatusVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3608861361307542106L;
    
    private String applyId;  // 申请编号
	private Integer stage;		// 贷款处理阶段
	private Integer status;   // 贷款阶段状态
	
	public String getApplyId() {
		return applyId;
	}
	public Integer getStage() {
		return stage;
	}
	public Integer getStatus() {
		return status;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
