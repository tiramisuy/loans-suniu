package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.credit.common.CreditApiVo;


/**
 * 白骑士-给资信云上报额外参数-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ReportExtVO extends CreditApiVo{

	/**
	 * 调用结果码
	 */
	private String resultCode;
	/**
	 * 应答消息
	 */
	private String resultDesc;

	public ReportExtVO(){
	}
	
	@Override
	public boolean isSuccess() {
		if ("CCOM1000".equals(getResultCode())) {
			return true;
		}
		return false;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return getResultCode();
	}

	@Override
	public void setCode(String code) {
		
	}

	@Override
	public String getMsg() {
		return getResultDesc();
	}

	@Override
	public void setMsg(String msg) {
		
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

}
