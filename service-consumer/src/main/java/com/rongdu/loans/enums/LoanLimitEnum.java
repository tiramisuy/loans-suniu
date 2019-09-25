package com.rongdu.loans.enums;

/**
 * @author zcb
 * 借款额度限制
 */
public enum LoanLimitEnum {
	MIN("1", "200000"),
	MAX("2", "1000000");

	private String id;
	private String value;

	LoanLimitEnum(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public static LoanLimitEnum get(String id) {
		for (LoanLimitEnum p : LoanLimitEnum.values()) {
			if (p.getId().equals(id)) {
				return p;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
