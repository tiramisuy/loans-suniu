package com.rongdu.loans.api.vo;

public enum BankCode {
	//中国工商银行
	ICBC_BANK("ICBC","0102"), 
	//中国农业银行
	ABC_BANK("ABC","0103"),
	//中国银行
	BOC_BANK("BOC","0104"),
	//中国建设银行
	CCB_BANK("CCB","0105"),
	//中国交通银行
	BCM_BANK("BCOM","0301"),
	//中信银行
	ZXS_BANK("CITIC","0302"),
	//中国光大银行
	CEB_BANK("CEB","0303"),
	//华夏银行
	HXB_BANK("HXB","0304"),
	//民生银行
	CMBC_BANK("CMBC","0305"),
	//平安银行
	PAB_BANK("PAB","0307"),
	//招商银行
	CMB_BANK("CMB","0308"),
	//兴业银行
	CIB_BANK("CIB","0309"),
	//浦东发展银行
	SPDB_BANK("SPDB","0310"),
	//邮政储蓄银行
	PSBC_BANK("PSBC","0403"),
	//广发银行
	GDB_BANK("GDB","0306");
	
	
	private String bName;
	private String bcode;
	
	BankCode(String bName, String bcode){
		this.bName = bName;
		this.bcode = bcode;
	}
	
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	
}
