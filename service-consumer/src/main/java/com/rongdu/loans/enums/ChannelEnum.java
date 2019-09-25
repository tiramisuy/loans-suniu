package com.rongdu.loans.enums;

/**
 * 渠道枚举类
 */
public enum ChannelEnum {

	/**
	 * 聚宝钱包
	 */
	JUQIANBAO(1, "JQB", "聚宝钱包",""),

	/**
	 * 诚成贷
	 */
	CHENGDAI(2, "CCDQB", "诚诚贷",""),

	/**
	 * 聚宝钱包线下贷
	 */
	XXD(3, "XXD", "聚宝钱包线下贷",""),

	/**
	 * 投复利
	 */
	TOUFULI(4, "TFLAPP", "投复利借贷APP",""),

	/**
	 * 旅游分期
	 */
	LYFQAPP(5, "LYFQAPP", "旅游分期APP",""),

	/**
	 * 借点钱渠道
	 */
	JIEDIANQIAN(6, "JDQAPI", "借点钱","贷款大师"),

	/**
	 * 借点钱渠道
	 */
	LoanWallet(7, "LWAPI", "贷款钱包","贷款钱包"),

	/**
	 * 借点钱渠道
	 */
	DAWANGDAI(8, "DWDAPI", "大王贷","融泽财富"),

	JIEDIANQIAN2(9, "JDQAPI2", "借点钱2","贷款大师"),

	CYQB(10, "CYQBAPI", "Android橙意钱包","橙意钱包"),

	CYQBIOS(11, "CYQBIOSAPI", "IOS橙意钱包","橙意钱包"),

	_51JDQ(12,"51JDQAPI","51借点钱","51借点钱"),

	YBQB(13,"YBQBAPI","亿宝钱包","亿宝钱包");

	private Integer id;
	private String code;
	private String desc;

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	private String app;

	ChannelEnum(Integer id, String code, String desc,String app) {
		this.id = id;
		this.code = code;
		this.desc = desc;
		this.app = app;
	}

	/**
	 * 根据id匹配描述
	 * 
	 * @param id
	 * @return
	 */
	public static String getDesc(Integer id) {
		for (ChannelEnum temp : ChannelEnum.values()) {
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
		for (ChannelEnum temp : ChannelEnum.values()) {
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
		for (ChannelEnum temp : ChannelEnum.values()) {
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
		for (ChannelEnum temp : ChannelEnum.values()) {
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
		for (ChannelEnum temp : ChannelEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getDesc();
			}
		}
		return null;
	}

	/**
	 * 根据渠道代码获取APP
	 *
	 * @param code
	 * @return
	 */
	public static String getAppByCode(String code) {
		for (ChannelEnum temp : ChannelEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getApp();
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
		for (ChannelEnum temp : ChannelEnum.values()) {
			if (temp.getDesc().equals(desc)) {
				return temp.getCode();
			}
		}
		return null;
	}
}
