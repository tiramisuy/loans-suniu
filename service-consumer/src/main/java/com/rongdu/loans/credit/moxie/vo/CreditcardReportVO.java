package com.rongdu.loans.credit.moxie.vo;

import java.io.Serializable;

public class CreditcardReportVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;// 1=信用卡邮箱，2网银
	private Object reportData;// 报告数据

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getReportData() {
		return reportData;
	}

	public void setReportData(Object reportData) {
		this.reportData = reportData;
	}

}
