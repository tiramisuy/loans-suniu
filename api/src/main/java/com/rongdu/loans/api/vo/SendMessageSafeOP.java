package com.rongdu.loans.api.vo;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 发送短息请求
 * @author likang
 *
 */
public class SendMessageSafeOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5198322823655319012L;
	
	@NotBlank(message="手机号不能为空")
	private String account;
	
	/**
	 * 短息类型
	 * [3] 恒丰银行电子账户;
	 * [5] 通联绑卡;
	 *
	 * 
	 */
	@NotBlank(message="短息验证码类型为空")
	@Pattern(regexp="3|4|5",message="短息验证码类型有误")
	private String msgType;
	
	/**
	 * 来源于哪个终端（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private String source;
	
	/**
	 * 渠道
	 */
	private String channel;
	
	/**
	 * 银行卡号
	 */
	private String cardNo;
	
	/**
	 * 姓名
	 */
	private String userName;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 银行代码
	 */
	private String bankCode;
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
