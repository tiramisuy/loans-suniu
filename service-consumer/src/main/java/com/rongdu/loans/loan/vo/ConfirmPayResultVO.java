package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 确认支付结果
 * @author likang
 *
 */
public class ConfirmPayResultVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5686855931325084248L;

	/**
	 * 处理结果（0-处理失败， 1-处理成功）
	 */
	private Integer result;
	
	/**
     * 提前还款标识（1-提前还清, 2-非提前还清）
     */
    private String prePayFlag;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getPrePayFlag() {
		return prePayFlag;
	}

	public void setPrePayFlag(String prePayFlag) {
		this.prePayFlag = prePayFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
