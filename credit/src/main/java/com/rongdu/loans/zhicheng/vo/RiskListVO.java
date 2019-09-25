package com.rongdu.loans.zhicheng.vo;

import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.zhicheng.message.RiskListRequestData;

/**
 * 宜信致诚风险名单-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class RiskListVO extends CreditApiVo{

	private static final long serialVersionUID = -4695418047175301678L;
	/**
	 * 应答代码
	 */
	private String errorcode;
	/**
	 * 应答消息
	 */
	private String message;
	/**
	 * 风险名单数据
	 */
	private RiskListRequestData data;
	
	@Override
	public boolean isSuccess() {
		if ("0000".equals(getErrorcode())) {
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
		return getErrorcode();
	}

	@Override
	public void setCode(String code) {
		this.code =code;
	}

	@Override
	public String getMsg() {
		return getErrorcode();
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RiskListRequestData getData() {
		return data;
	}

	public void setData(RiskListRequestData data) {
		this.data = data;
	}

}