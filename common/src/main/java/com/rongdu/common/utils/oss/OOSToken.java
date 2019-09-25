package com.rongdu.common.utils.oss;

import java.io.Serializable;

public class OOSToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5120603431007100312L;
	private String AccessKeyId;
	private String AccessKeySecret;
	private String SecurityToken;
	private String Expiration;

	public String getAccessKeyId() {
		return AccessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		AccessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return AccessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		AccessKeySecret = accessKeySecret;
	}

	public String getSecurityToken() {
		return SecurityToken;
	}

	public void setSecurityToken(String securityToken) {
		SecurityToken = securityToken;
	}

	public String getExpiration() {
		return Expiration;
	}

	public void setExpiration(String expiration) {
		Expiration = expiration;
	}

}
