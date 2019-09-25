package com.rongdu.loans.pay.xianfeng.vo;

import java.io.Serializable;

/**
 * @author liuzhuang
 * 
 */
public class XfAgreementPayQueryVO implements Serializable {

	private static final long serialVersionUID = -5103606434948118357L;

	/**
	 * 接口名称
	 */
	private String service = "REQ_PROTOCOL_QUERY_BY_ID";
	/**
	 * 签名算法
	 */
	private String secId = "RSA";
	/**
	 * 接口版本
	 */
	private String version = "4.0.0";
	/**
	 * 序列号
	 */
	private String reqSn;
	/**
	 * 商户号
	 */
	private String merchantId;

	/**
	 * 商户订单号
	 */
	private String merchantNo;

	/**
	 * 订单签名数据
	 */
	private String sign;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReqSn() {
		return reqSn;
	}

	public void setReqSn(String reqSn) {
		this.reqSn = reqSn;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

}
