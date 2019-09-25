package com.rongdu.loans.zhicheng.vo;

import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.zhicheng.message.EchoQueryApiResponseParams;

/**
 * 查询借款、风险和逾期信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class CreditInfoVO extends CreditApiVo{

	private static final long serialVersionUID = -4695418047175301678L;
	/**
	 * 应答代码
	 */
	private String errorCode;
	/**
	 * 应答消息
	 */
	private String message;
	/**
	 * 风险数据
	 */
	private EchoQueryApiResponseParams params;
	
	@Override
	public boolean isSuccess() {
		if ("0000".equals(getErrorCode())) {
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
		return getErrorCode();
	}

	@Override
	public void setCode(String code) {
		this.code =code;
	}

	@Override
	public String getMsg() {
		return getMessage();
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EchoQueryApiResponseParams getParams() {
		return params;
	}

	public void setParams(EchoQueryApiResponseParams params) {
		this.params = params;
	}


}