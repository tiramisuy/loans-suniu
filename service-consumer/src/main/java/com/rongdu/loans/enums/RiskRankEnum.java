package com.rongdu.loans.enums;

/**
 * @author liuzhuang
 */
public enum RiskRankEnum {
	P("P", "审批通过",1),
	A("A", "高",2), 
	B("B", "高",3),
	C("C", "中",4),
	D("D", "低",5);
	
	private String id;
	private String name;
	private int sort;

	RiskRankEnum(String id, String name,int sort) {
		this.id = id;
		this.name = name;
		this.sort=sort;
	}

	public static RiskRankEnum get(String id) {
		for (RiskRankEnum p : RiskRankEnum.values()) {
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
