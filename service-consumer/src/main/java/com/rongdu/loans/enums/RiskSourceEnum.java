package com.rongdu.loans.enums;

/**
 * @author liuzhuang
 */
public enum RiskSourceEnum {
	JUQIANBAO("juqianbao", "聚宝钱包"),
	JUCAI("juqianbao", "聚宝钱包"),
	JINIU("jiniu", "金牛分期"),
	BAIQISHI("baiqishi", "白骑士"),
	ZHIMA("zhima", "芝麻"),
	ZHICHENG("zhicheng", "宜信阿福"),
	CREDIT100("credit100", "百融"),
	TENCENT("tencent", "腾讯"),
	TONGDUN("tongdun", "同盾"),
	KDCREDIT("kdcredit", "口袋"),
	THIRDPARTYCREDIT1("thirdpartycredit1", "三方征信1"),
	XINYAN("xinyan", "新颜"),
	MOXIE("moxie", "魔蝎"),
	RONG360("rong360","融360");

	private String id;
	private String name;

	RiskSourceEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static RiskSourceEnum get(String id) {
		for (RiskSourceEnum p : RiskSourceEnum.values()) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
