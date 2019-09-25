package com.rongdu.loans.enums;

/**
 * @author zcb 产品类
 */
public enum UserTypeEnum {
	personal("1", "个人"), enterprise("2", "企业");

	private String id;
	private String name;

	UserTypeEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static String getName(String id) {
		for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
			if (userTypeEnum.getId().equals(id)) {
				return userTypeEnum.getName();
			}
		}
		return null;
	}

	public static UserTypeEnum get(String id) {
		for (UserTypeEnum p : UserTypeEnum.values()) {
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
