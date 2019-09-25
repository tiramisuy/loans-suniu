package com.rongdu.loans.loan.option.rongTJreport;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: RongTJReportDetail.java  
* @Package com.rongdu.loans.loan.option.rong360Model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月17日  
* @version V1.0  
*/
@Data
public class RongTJReportDetailOP implements Serializable {

	private static final long serialVersionUID = 5445533558868970921L;
	
	/**
	 * 融天机生成报告接口返回生成的search_id
	 */
	private String searchId;
	
	/**
	 * 报告的类型，可以为file（html文件下载链接）
	 * 或者html（json+html源码+html文件下载链接）
	 * 不传则只返回json
	 */
	private String reportType;

}
