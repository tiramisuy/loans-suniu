package com.rongdu.loans.enums;

/**
 * 还款方式
 */
public enum RepayMethodEnum {

	INTEREST(1, "等额本息"),
	PRINCIPAL(2, "按月等额本金"),
//	ONE_TIME(3, "一次性还本付息"),
	ONE_TIME(3, "到期还本付息"),
	EXPIRE(4, "先息后本"),
	INTEREST_DAY(5, "按天等额本息"),
	PRINCIPAL_INTEREST(6, "等本等息"),
	PRINCIPAL_INTEREST_DAY(7, "按天等本等息");

	private Integer value;
	private String desc;

	RepayMethodEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static String getDesc(Integer id) {
		for (RepayMethodEnum repayMethodEnum : RepayMethodEnum.values()) {
			if (repayMethodEnum.getValue().equals(id)) {
				return repayMethodEnum.getDesc();
			}
		}
		return null;
	}

	public static RepayMethodEnum get(Integer id) {
		for (RepayMethodEnum repayMethodEnum : RepayMethodEnum.values()) {
			if (repayMethodEnum.getValue().equals(id)) {
				return repayMethodEnum;
			}
		}
		return null;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
