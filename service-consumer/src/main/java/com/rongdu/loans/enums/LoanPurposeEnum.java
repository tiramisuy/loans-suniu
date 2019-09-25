package com.rongdu.loans.enums;

/**
 * @author zcb
 *	借款用途类
 */
public enum LoanPurposeEnum {
	P1("1", "短期周转"), 
	P2("2", "个人消费"),
	P3("3", "旅游分期"),
	P4("4", "教育培训"),
	P5("5", "装修借款"),
	P6("6", "投资创业"),
	P7("7", "购房借款"),
	P8("8", "购车借款"),
	P9("9", "婚礼筹备"),
	P10("10", "购物分期"),
	P11("11", "其他借款");

	private String id;
	private String name;

	LoanPurposeEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static LoanPurposeEnum get(String id) {
		for (LoanPurposeEnum p : LoanPurposeEnum.values()) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	public static String getDesc(String id) {
		for (LoanPurposeEnum loanPurposeEnum : LoanPurposeEnum.values()) {
			if (loanPurposeEnum.getId().equals(id)) {
				return loanPurposeEnum.getName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
