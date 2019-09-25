package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.credit.common.CreditApiVo;

import java.util.List;

/**
 * 查询通讯信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class DeviceContactVO extends CreditApiVo{

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
	 * 通讯录
	 */
	private List<Contact> contactsInfo;
	/**
	 * 通话记录
	 */
	private List<CallRecord> callRecordInfo;
	/**
	 * 短信记录
	 */
	private List<SmsRecord> smsRecordInfo;
	
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

	public List<Contact> getContactsInfo() {
		return contactsInfo;
	}

	public void setContactsInfo(List<Contact> contactsInfo) {
		this.contactsInfo = contactsInfo;
	}

	public List<CallRecord> getCallRecordInfo() {
		return callRecordInfo;
	}

	public void setCallRecordInfo(List<CallRecord> callRecordInfo) {
		this.callRecordInfo = callRecordInfo;
	}

	public List<SmsRecord> getSmsRecordInfo() {
		return smsRecordInfo;
	}

	public void setSmsRecordInfo(List<SmsRecord> smsRecordInfo) {
		this.smsRecordInfo = smsRecordInfo;
	}


}
