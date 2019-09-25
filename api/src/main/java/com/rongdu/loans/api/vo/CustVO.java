package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class CustVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6249615186010916084L;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 帐号（手机号等）
	 */
	private String account;

	/**
	 * 姓名
	 */
	private String trueName;
	
	/**
	 * 令牌id
	 */
	private String tokenId;
	
	/**
	 * appKey
	 */
	private String appKey;

	/**
	 * 证件类型
	 */
	private String idType;
	
	/**
	 * 证件号
	 */
	private String idNo;
	
	/**
	 * 图像url
	 */
	private String avatarUrl;
	
	/**
	 * 性别
	 */
	private Integer sex;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public Integer getSex() {
		return sex;
	}

	public void setAvatarUrl(String avatarUrl) {
		if(null == avatarUrl) {
			avatarUrl = "";
		}
		this.avatarUrl = avatarUrl;
	}

	public void setSex(Integer sex) {
		if(null == sex) {
			sex = 1;
		}
		this.sex = sex;
	}

	public String getIdType() {
		return idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdType(String idType) {
		if(null == idType) {
			idType = "1";
		}
		this.idType = idType;
	}

	public void setIdNo(String idNo) {
		if(null == idNo) {
			idNo = "";
		}
		this.idNo = idNo;
	}
}
