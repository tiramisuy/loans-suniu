package com.rongdu.loans.baiqishi.message;

import com.rongdu.loans.baiqishi.vo.CallRecord;
import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.SmsRecord;

import java.util.List;

/**
 * 查询通讯信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ContactInfoResultData{

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
