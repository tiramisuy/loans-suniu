package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 保存贷款申请结果对象
 */
public class ApplySaveResultVO implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4648375082115830398L;
    
    /**
     * 执行结果 1-成功; 0-失败
     */
    private Integer saveResult;
    
    /**
     * 申请到账金额
     */
    private String toAccountAmt;

	public Integer getSaveResult() {
		return saveResult;
	}

	public String getToAccountAmt() {
		return toAccountAmt;
	}

	public void setSaveResult(Integer saveResult) {
		this.saveResult = saveResult;
	}

	public void setToAccountAmt(String toAccountAmt) {
		this.toAccountAmt = toAccountAmt;
	}
}
