package com.rongdu.loans.api.vo;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 发送短息请求
 * @author likang
 *
 */
public class SendMessageOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5198322823655319012L;
	
	@NotBlank(message="用户名不能为空")
	private String account;
	
	/**
	 * 短息类型
	 * [1] 注册; [2] 忘记密码;[5] 登录验证码
	 * 
	 */
	@NotBlank(message="短息验证码类型为空")
	@Pattern(regexp="1|2|5",message="短息验证码类型有误")
	private String msgType;
	
	/**
	 * 来源于哪个终端（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private String source;
	
	private String channel;
	
	/**
	 * 图片验证码
	 */
	private String imageCode;
	
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
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
