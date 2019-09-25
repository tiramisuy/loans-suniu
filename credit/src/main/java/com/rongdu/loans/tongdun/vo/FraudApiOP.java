package com.rongdu.loans.tongdun.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 同盾反欺诈决策引擎服务-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class FraudApiOP implements Serializable{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 身份证号
	 */
	private String idNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 紧急联系人
	 */
	private List<Map<String,String>> contacts;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 进件来源
	 */
	private String source;
	/**
	 * 设备指纹唯一标识
	 */
	private String blackBox;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Map<String,String>> getContacts() {
		return contacts;
	}

	public void setContacts(List<Map<String,String>> contacts) {
		this.contacts = contacts;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 是否为Android App进件
	 * @return
     */
	public boolean isAndroid() {
		return "2".equals(source);
	}

	/**
	 * 是否为iOS  App进件
	 * @return
	 */
	public boolean isIOS() {
		return "1".equals(source);
	}

	public String getBlackBox() {
		return blackBox;
	}

	public void setBlackBox(String blackBox) {
		this.blackBox = blackBox;
	}
}
