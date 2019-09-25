package com.rongdu.loans.credit.tencent.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 腾讯接口的访问令牌 
 * @author sunda
 * @version 2017-07-10
 */
public class AccessTokenVo extends CreditApiVo{
	
	private static final long serialVersionUID = -6472859698806247755L;
	
	/**
	 * 交易时间
	 */
	private String transactionTime;
	
	/**
	 * 腾讯业务流水号
	 */
	private String bizSeqNo;
	
	/**
	 *  access token 必须缓存在磁盘，并定时刷新,
	 *  建议每 1 小时 50 分钟请新的access token,原 access token 2 小时(7200S)失效，期间两个 token 都能使用
	 *  同一个合作伙伴，10 分钟内 access token 只能成功请求一次
	 */
	@JsonProperty("access_token")
	private String accessToken = "";
	/**
	 * expire_time 为 access_token 失效的绝对时间，
	 * 由于各服务器时间差异，不能使用作为有效期的判定依据，只展示使用
	 */
	@JsonProperty("expire_time")
	private String expireTime;
	/**
	 * expire_in 为 access_token 的最大生存时间，单位秒，
	 * 合作伙伴在判定 有效期时以此为准
	 */
	@JsonProperty("expire_in")
	private int expireIn;
	
	public AccessTokenVo(){
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
	
}
