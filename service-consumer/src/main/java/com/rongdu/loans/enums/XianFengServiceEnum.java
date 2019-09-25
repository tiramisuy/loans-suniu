package com.rongdu.loans.enums;

/**
 * code y0602
 * 
 * @Title: XianFengServiceEnum.java
 * @Package com.rongdu.loans.pay.utils
 * @Description: 先锋代扣接口枚举
 * @author: yuanxianchu
 * @date 2018年6月2日
 * @version V1.0
 */
public enum XianFengServiceEnum {

	REQ_WITHOIDING("01", "REQ_WITHOIDING", "4.0.0", "RSA", "单笔代扣"), 
	REQ_WITHOIDING_QUERY("02", "REQ_WITHOIDING_QUERY", "4.0.0", "RSA", "单笔代扣订单查询"),
	REQ_WITHDRAW("03","REQ_WITHDRAW","4.0.0","RSA","单笔代发"),
	REQ_WITHDRAW_QUERY_BY_ID("04","REQ_WITHDRAW_QUERY_BY_ID","4.0.0","RSA","单笔代发订单查询");

	/**
	 * 接口代码
	 */
	private String serviceId;
	/**
	 * 接口名
	 */
	private String serviceName;
	/**
	 * 接口版本
	 */
	private String version;
	/**
	 * 签名算法
	 */
	private String secId;
	/**
	 * 接口描述
	 */
	private String desc;

	XianFengServiceEnum(String serviceId, String serviceName, String version, String secId, String desc) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.version = version;
		this.secId = secId;
		this.desc = desc;
	}

	/**
	 * @Description: 根据接口ID获取枚举
	 */
	public static XianFengServiceEnum get(String serviceName) {
		for (XianFengServiceEnum p : XianFengServiceEnum.values()) {
			if (p.getServiceName().equals(serviceName)) {
				return p;
			}
		}
		return null;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
