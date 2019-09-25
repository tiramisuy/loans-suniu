package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询芝麻信用分-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmScoreOP implements Serializable{

	private static final long serialVersionUID = -7325974613223330375L;
	/**
	 *  用户ID
	 */
	private  String	userId;
	/**
	 *  applyId
	 */
	private  String	applyId;
	/**
	 *  用户App端的IP地址
	 */
	private  String	ip;
	/**
	 *  来源于哪个终端（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	 */
	private  String	source;
	/**
	 *  用户openId
	 */
	private  String	openId;
	
	public ZmScoreOP(){
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

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
	
}
