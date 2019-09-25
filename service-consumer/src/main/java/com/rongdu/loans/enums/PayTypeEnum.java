package com.rongdu.loans.enums;

/**
 * @author 支付类型
 */
public enum PayTypeEnum {
	SETTLEMENT(1, "还款"), 
	DELAY(2, "延期"),
	URGENT(3, "加急"),
	SHOPPING(4, "购物金"),
	TRIP(5, "旅游券"),
	SHOPPINGMALL(6, "商城购物"),
	BORROWHELP(7,"助贷服务"),
	PARTWITHHOLD(8,"部分代扣"),
	HT_ADMIN(9,"手动扣款");

	private Integer id;
	private String name;

	PayTypeEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static PayTypeEnum get(Integer id) {
		for (PayTypeEnum p : PayTypeEnum.values()) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
