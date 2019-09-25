package com.rongdu.loans.loan.option.rongTJreportv1;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: RongTJGeneratereportOP.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Data
public class RongTJGeneratereportOP implements Serializable {

	private static final long serialVersionUID = 6388981049838657268L;
	
	/**
	 * mobile	运营商报告
	 * xgscore	风控模型
	 * xgscoreplus	风控模型plus
	 */
	private String type;
	
	/**
	 * 淘金云订单id(融360三方ID)
	 */
	private String orderNo;
	
	/**
	 * 服务器异步通知页面路径
	 */
	private String notifyUrl;
	
	/**
	 * 报告版本，运营商支持1.0,2.0
	 * 风控模型支持: 1.2,2.1版本
	 */
	private String version;

}
