package com.rongdu.loans.zhicheng.message;

import com.rongdu.loans.zhicheng.vo.RiskListOP;

import java.io.Serializable;

/**
 * 宜信致诚风险名单-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class RiskListRequest implements Serializable{

	private static final long serialVersionUID = 7209682953717889561L;
	/**
	 * 查询原因
	 */
	private String queryReason = "10";
	/**
	 * 收费验证环节
	 * 1-身份信息认证，2-手机号实名验证，
	 * 3-银行卡三要素验证，4-银行卡四要素认证
	 */
	private String pricedAuthentification;
	/**
	 * 具体请求参数
	 */
	private RiskListOP  data;
	
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
	public String getPricedAuthentification() {
		return pricedAuthentification;
	}
	public void setPricedAuthentification(String pricedAuthentification) {
		this.pricedAuthentification = pricedAuthentification;
	}
	public RiskListOP getData() {
		return data;
	}
	public void setData(RiskListOP data) {
		this.data = data;
	}
	
}