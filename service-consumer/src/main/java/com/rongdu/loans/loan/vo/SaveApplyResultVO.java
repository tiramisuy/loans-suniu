package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 贷款申请结果返回对象
 */
public class SaveApplyResultVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -747374203777928406L;
        
    /**
     * 保存结果
     */
    private boolean isSuccess;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 构造方法
     */
    public SaveApplyResultVO() {
    	isSuccess = false;
    }


    public boolean isSuccess() {
		return isSuccess;
	}


	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
