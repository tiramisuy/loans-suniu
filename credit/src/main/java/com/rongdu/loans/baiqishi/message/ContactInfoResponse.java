package com.rongdu.loans.baiqishi.message;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 查询通讯信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ContactInfoResponse  extends CreditApiVo{

	private static final long serialVersionUID = -6909977101992364369L;
	/**
	 * 调用结果码，BQS000为成功，其他为失败
	 */
	private String resultCode;
	/**
	 * 应答消息
	 */
	private String resultDesc;
	/**
	 * 通讯录/通话记录/短信记录
	 */
	private ContactInfoResultData resultData;

	
	@Override
	public boolean isSuccess() {
		if ("BQS000".equals(getResultCode())) {
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

	public ContactInfoResultData getResultData() {
		return resultData;
	}

	public void setResultData(ContactInfoResultData resultData) {
		this.resultData = resultData;
	}

}
