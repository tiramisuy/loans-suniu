package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;

/**
 * 用户访问授权-请求报文
 * @author sunda
 * @version 2017-07-10
 */
/**
 * 用户访问授权-业务参数
 * @author sunda
 * @version 2017-07-10
 */
public class ExtParam implements Serializable{
	
	private static final long serialVersionUID = -7271155646594153079L;
	/**
	 *  身份标识类型
	 *  必填：是
	 *  0 ：按照 
	 *  2 ：按照身份证+ 姓名进行授权
	 */
	private  String	identityType;
	/**
	 *  身份证号
	 *  必填：否
	 *  根据  identityType ，选择传递
	 */
	private  String	certNo;
	/**
	 *  姓名
	 *  必填：否
	 *  根据  identityType ，选择传递
	 */
	private  String	name;
	/**
	 *  授权页面类别
	 *  必填：是
	 *  apppc:pc
	 *  app ：手机
	 */
	private  String	channel;
	/**
	 *  授权成功返回路径
	 *  必填：是
	 */
	private  String	callbackUrl;
	/**
	 *  用户openId ，根据  identityType ，选择传递
	 *  必填：是
	 */
	private  String	openId;
	
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}	
	
}

