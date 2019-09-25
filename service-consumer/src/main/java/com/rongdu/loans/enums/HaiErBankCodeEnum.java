package com.rongdu.loans.enums;
/**
 * 
* @Description:  海尔支付银行编码
* @author: RaoWenbiao
* @date 2018年12月20日
 */
public enum HaiErBankCodeEnum {
	// 中国工商银行
	ICBC_BANK("ICBC", "中国工商银行"),
	// 中国农业银行
	ABC_BANK("ABC", "中国农业银行"),
	// 中国银行
	BOC_BANK("BOC", "中国银行"),
	// 中国建设银行
	CCB_BANK("CCB", "中国建设银行"),
	// 中国交通银行
	BCM_BANK("COMM", "中国交通银行"),
	// 中信银行
	ZXS_BANK("CITIC", "中信银行"),
	// 中国光大银行
	CEB_BANK("CEB", "中国光大银行"),
	// 华夏银行
	HXB_BANK("HXB", "华夏银行"),
	// 民生银行
	CMBC_BANK("CMBC", "民生银行"),
	// 平安银行
	PAB_BANK("SZPAB", "平安银行"),
	// 招商银行
	CMB_BANK("CMB", "招商银行"),
	// 兴业银行
	CIB_BANK("CIB", "兴业银行"),
	// 浦东发展银行
	SPDB_BANK("SPDB", "浦东发展银行"),
	// 邮政储蓄银行
	PSBC_BANK("PSBC", "邮政储蓄银行"),
	// 广发银行
	GDB_BANK("GDB", "广发银行"),
	// 上海银行
	SHB_BANK("BOS", "上海银行");

	private String bCode;
	private String bName;

	HaiErBankCodeEnum(String bCode, String bName) {
		this.bCode = bCode;
		this.bName = bName;
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
		for (HaiErBankCodeEnum temp : HaiErBankCodeEnum.values()) {
			if (temp.getbCode().equals(code)) {
				return temp.getbName();
			}
		}
		return null;
	}
	
	public static String getCode(String name) {
		for (HaiErBankCodeEnum temp : HaiErBankCodeEnum.values()) {
			if (temp.getbName().equals(name)) {
				return temp.getbCode();
			}
		}
		return null;
	}
}
