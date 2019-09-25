package com.rongdu.loans.enums;

/**
 * @author liuzhuang
 */
public enum KDBankEnum {
	CODE_1("1","ICBC", "工商银行"), 
	CODE_2("2","ABC", "农业银行"),
	CODE_3("3","CEB", "光大银行"),
	CODE_4("4","PSBC", "邮政储蓄银行"),
	CODE_5("5","CIB", "兴业银行"),
//	CODE_6("6", "深圳发展银行"),
	CODE_7("7","CCB", "建设银行"),
	CODE_8("8","CMB", "招商银行"),
	CODE_9("9", "BOC","中国银行"),
	CODE_10("10","SPDB", "浦发银行"),
	CODE_11("11","PAB", "平安银行"),
	CODE_12("12","HXB", "华夏银行"),
	CODE_13("13","CITIC", "中信银行"),
	CODE_14("14","BCOM", "交通银行"),
	CODE_15("15","CMBC", "民生银行"),
	CODE_16("16","GDB", "广发银行"),
	CODE_17("17","BOB", "北京银行"),
	CODE_18("18","bosc", "上海银行"),
	CODE_19("19","srcb", "上海农商银行");
//	CODE_49("49", "江西银行");
	
	private String id;
	private String code;
	private String name;

	KDBankEnum(String id,String code, String name) {
		this.id=id;
		this.code = code;
		this.name = name;
	}

	public static String getId(String code) {
		for (KDBankEnum p : KDBankEnum.values()) {
			if (code.equals(p.getCode())) {
				return p.getId();
			}
		}
		return null;
	}
	public static String getName(String code) {
		for (KDBankEnum p : KDBankEnum.values()) {
			if (code.equals(p.getCode())) {
				return p.getName();
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




}
