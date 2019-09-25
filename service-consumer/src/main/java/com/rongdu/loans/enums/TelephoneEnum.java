package com.rongdu.loans.enums;

/**
 * 电话枚举类
 */
public enum TelephoneEnum {

	/**
	 * 聚宝钱包客服电话
	 */
	JUQIANBAOPHONE(1, "JQBPHONE", "4001622772");



	private Integer id;
	private String code;
	private String desc;

	TelephoneEnum(Integer id, String code, String desc) {
		this.id = id;
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据id匹配描述
	 * 
	 * @param id
	 * @return
	 */
	public static String getDesc(Integer id) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getId().equals(id)) {
				return temp.getDesc();
			}
		}
		return null;
	}

	/**
	 * 根据id匹配渠道代码
	 * 
	 * @param id
	 * @return
	 */
	public static String getCode(Integer id) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getId().equals(id)) {
				return temp.getCode();
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 根据描述获取id
	 * 
	 * @param desc
	 * @return
	 */
	public static Integer getIdByDesc(String desc) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getDesc().equals(desc)) {
				return temp.getId();
			}
		}
		return null;
	}

	/**
	 * 根据渠道代码获取id
	 * 
	 * @param code
	 * @return
	 */
	public static Integer getIdByCode(String code) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getId();
			}
		}
		return null;
	}

	/**
	 * 根据渠道代码获取描述
	 * 
	 * @param code
	 * @return
	 */
	public static String getDescByCode(String code) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getDesc();
			}
		}
		return null;
	}

	/**
	 * 根据渠道代码获取id
	 * 
	 * @param code
	 * @return
	 */
	public static String getCodeByDesc(String desc) {
		for (TelephoneEnum temp : TelephoneEnum.values()) {
			if (temp.getDesc().equals(desc)) {
				return temp.getCode();
			}
		}
		return null;
	}
}
