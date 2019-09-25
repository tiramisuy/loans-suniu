package com.rongdu.loans.enums;

public enum BankCodeEnum {
	// 中国工商银行
	ICBC_BANK("ICBC", "中国工商银行", "0102"),
	// 中国农业银行
	ABC_BANK("ABC", "中国农业银行","0103"),
	// 中国银行
	BOC_BANK("BOC", "中国银行","0104"),
	// 中国建设银行
	CCB_BANK("CCB", "中国建设银行","0105"),
	// 中国交通银行
	BCM_BANK("BCOM", "中国交通银行","0301"),
	// 中信银行
	ZXS_BANK("CITIC", "中信银行","0302"),
	// 中国光大银行
	CEB_BANK("CEB", "中国光大银行","0303"),
	// 华夏银行
	//HXB_BANK("HXB", "华夏银行",""),
	// 民生银行
	CMBC_BANK("CMBC", "民生银行","0305"),
	// 平安银行
	PAB_BANK("PAB", "平安银行","0307"),
	// 招商银行
	CMB_BANK("CMB", "招商银行","0308"),
	// 兴业银行
	CIB_BANK("CIB", "兴业银行","0309"),
	// 浦东发展银行
	SPDB_BANK("SPDB", "浦东发展银行","0310"),
	// 邮政储蓄银行
	PSBC_BANK("PSBC", "邮政储蓄银行","0403"),
	// 广发银行
	GDB_BANK("GDB", "广发银行","0306"),
	// 上海银行
	SHB_BANK("SHB", "上海银行","04012900");

	private String bCode;
	private String bName;
	private String bNo;

	BankCodeEnum(String bCode, String bName, String bNo) {
		this.bCode = bCode;
		this.bName = bName;
		this.bNo = bNo;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbCode() {
		return bCode;
	}

	public void setbCode(String bCode) {
		this.bCode = bCode;
	}

	public static String getName(String code) {
		for (BankCodeEnum temp : BankCodeEnum.values()) {
			if (temp.getbCode().equals(code)) {
				return temp.getbName();
			}
		}
		return null;
	}

	public static BankCodeEnum getEnumByCode(String code) {
		for (BankCodeEnum temp : BankCodeEnum.values()) {
			if (temp.getbCode().equals(code)) {
				return temp;
			}
		}
		return null;
	}

	public static BankCodeEnum getEnumByNo(String bNo) {
		for (BankCodeEnum temp : BankCodeEnum.values()) {
			if (temp.getbNo().equals(bNo)) {
				return temp;
			}
		}
		return null;
	}

	public static String getCode(String name) {
		for (BankCodeEnum temp : BankCodeEnum.values()) {
			if (temp.getbName().equals(name)) {
				return temp.getbCode();
			}
		}
		return null;
	}

	public String getbNo() {
		return bNo;
	}

	public void setbNo(String bNo) {
		this.bNo = bNo;
	}
}
