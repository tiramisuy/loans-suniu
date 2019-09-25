package com.rongdu.loans.api.web.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
/**
 * 发送聚宝钱包短信验证码接口
 * @author likang
 */
public class SendFnMCOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 9077984620022702691L;

	@NotBlank(message="用户名不能为空")
	private String account;
	
	/**
	 * 短息类型
	 * [fnReg] 注册; [fnForget] 忘记密码;
	 */
	@NotBlank(message="短息验证码类型为空")
	@Pattern(regexp="fnReg|fnForget",message="短息验证码类型有误")
	private String msgType;
	
	/**
	 * 渠道代码
	 */
	@NotBlank(message="渠道码不能为空")
	@Pattern(regexp="LVKA|BEIKE|JIEDAITONG|JUQIANBAO",message="渠道码有误")
	private String channel;
	
	/**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	private String source;

	public String getAccount() {
		return account;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getChannel() {
		return channel;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
