package com.rongdu.loans.credit.baiqishi.vo;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 查询资信云报告页面URL-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ReportPageVO  extends CreditApiVo{

	private static final long serialVersionUID = -6909977101992364369L;

	/**
	 * 资信云报告页面URL
	 */
	private String reportPageUrl;
	
	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReportPageUrl() {
		return reportPageUrl;
	}

	public void setReportPageUrl(String reportPageUrl) {
		this.reportPageUrl = reportPageUrl;
	}

}
