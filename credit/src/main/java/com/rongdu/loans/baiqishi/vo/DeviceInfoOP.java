package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询设备信息-请求报文
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class DeviceInfoOP implements Serializable {

	private static final long serialVersionUID = 1687027729736356626L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 来源于哪个终端（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	 */
	private String source;
	/**
	 * 白骑士设备ID
	 */
	private String tokenKey;

	public DeviceInfoOP() {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

}
