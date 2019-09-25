package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.excel.annotation.ExcelField;

public class SmsCodeVo extends BaseEntity<SmsCodeVo> implements Serializable{

	private static final long serialVersionUID = 9139686286513236305L;
	
	private String mobile;
	private String smsCode;
	private Date sendTime;
	private String contNo;
	private String userName;
	private Date repayDate;
	private String totalAmount;
	private String channelName;
	private String cardNo;

	@ExcelField(title="客户手机号", type=1, align=2, sort=7)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ExcelField(title="短信内容", type=1, align=2, sort=6)
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	@ExcelField(title="发送时间", type=1, align=2, sort=3)
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@ExcelField(title="合同编号", type=1, align=2, sort=2)
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	@ExcelField(title="客户姓名", type=1, align=2, sort=3)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@ExcelField(title="还款时间", type=1, align=2, sort=4)
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	@ExcelField(title="还款金额", type=1, align=2, sort=5)
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	@ExcelField(title="归属门店", type=1, align=2, sort=1)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelField(title="银行卡号", type=1, align=2, sort=8)
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
