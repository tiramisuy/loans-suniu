package com.rongdu.loans.enums;
/**  
* @Title: Rong360ServiceEnums.java  
* @Package com.rongdu.loans.enums  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月3日  
* @version V1.0  
*/
public enum Rong360ServiceEnums {
	
	ORDER_BINDCARDFEEDBACK("is.api.v3.order.bindcardfeedback","RSA","1.0","融360绑卡反馈接口"),
	ORDER_APPROVEFEEDBACK("is.api.v3.order.approvefeedback","RSA","1.0","融360审批结果反馈接口"),
	ORDER_ORDERFEEDBACK("is.api.v3.order.orderfeedback","RSA","1.0","融360订单状态反馈接口"),
	ORDER_PUSHREPAYMENT("is.api.v3.order.pushrepayment","RSA","1.0","融360还款计划推送接口"),
	ORDER_CONTRACTSTATUS("is.api.v3.order.contractstatus","RSA","1.0","融360合同状态反馈接口"),
	ORDER_REPAYFEEDBACK("is.api.v3.order.repayfeedback","RSA","1.0","融360还款或展期结果反馈接口"),
	
	TAOJINYUNREPORT_GENERATEREPORT("tianji.api.taojinyunreport.generatereport","RSA","1.0","天机生成报告接口"),
	TAOJINYUNREPORT_REPORTDETAIL("tianji.api.taojinyunreport.reportdetail","RSA","1.0","天机互联网报告信息详情接口"),
	TAOJINYUNREPORT_SCORE("tianji.api.taojinyunreport.score","RSA","1.0","天机风控模型详情接口"),
	TAOJINYUNREPORT_SCOREPLUS("tianji.api.taojinyunreport.scoreplus","RSA","1.0","天机风控模型plus详情接口"),
	
	TJY_IMAGE_API_FETCH("tjy.image.api.fetch","RSA","1.0","融360获取图片内容接口");
	
	/**
	 * 接口名
	 */
	private String method;
	
	/**
	 * 加密方法
	 */
	private String signType;
	
	/**
	 * 接口版本
	 */
	private String version;
	
	/**
	 * 接口描述
	 */
	private String desc;
	
	Rong360ServiceEnums(String method,String signType,String version,String desc) {
		this.method = method;
		this.signType = signType;
		this.version = version;
		this.desc = desc;
	}
	
	/**
	 * @Description: 根据接口名获取枚举
	 */
	public static Rong360ServiceEnums get(String method) {
		for (Rong360ServiceEnums p : Rong360ServiceEnums.values()) {
			if (p.getMethod().equals(method)) {
				return p;
			}
		}
		return null;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
