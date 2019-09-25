package com.rongdu.loans.enums;

/**
 * 放款提现渠道
 * 
 * @author liuzhuag
 * 
 */
public enum WithdrawalSourceEnum {

	WITHDRAWAL_ONLINE(0, "投复利线上"), 
	WITHDRAWAL_OFFLINE(1, "线下"), 
	WITHDRAWAL_KOUDAI(2, "口袋"), 
	WITHDRAWAL_KOUDAI_CG(3,"口袋存管"), 
	WITHDRAWAL_LESHI(4, "乐视"),
	WITHDRAWAL_HJS(5, "汉金所"),
	WITHDRAWAL_TONGRONG(6, "通融"),
	WITHDRAWAL_TONGLIAN(7, "通联");

	private Integer value;
	private String desc;

	WithdrawalSourceEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static WithdrawalSourceEnum getByValue(int value) {
		for (WithdrawalSourceEnum p : WithdrawalSourceEnum.values()) {
			if (p.getValue().intValue() == value) {
				return p;
			}
		}
		return null;
	}
}
