package com.rongdu.loans.enums;

/**
 * 来源枚举类
 * @author likang
 * (1-ios，2-android，3-H5，4-api，5-网站，6-system)
 */
public enum SourceEnum {
	/**
	 * ios
	 */
	IOS(1,"ios","1"),
	/**
	 * android
	 */
	ANDROID(2,"android","2"),
	/**
	 * H5
	 */
	H5(3,"H5","3"),
	/**
	 * api
	 */
	API(4,"api","4"),
	/**
	 * 网站
	 */
	NET(5,"网站","5"),
	/**
	 * system
	 */
	SYSTEM(6,"system","6");
	
    private Integer value;
    private String desc;
    private String code;

    SourceEnum(Integer value, String desc, String code) {
        this.value = value;
        this.desc = desc;
        this.code = code;
    }

    /**
     * 根据id匹配描述
     * @param id
     * @return
     */
    public static String getDesc(Integer id) {
        for (SourceEnum sourceEnum : SourceEnum.values()) {
            if (sourceEnum.getValue().equals(id)) {
                return sourceEnum.getDesc();
            }
        }
        return null;
    }
    
    /**
     * 根据code匹配描述
     * @param id
     * @return
     */
    public static String getDesc(String code) {
        for (SourceEnum sourceEnum : SourceEnum.values()) {
            if (sourceEnum.getCode().equals(code)) {
                return sourceEnum.getDesc();
            }
        }
        return null;
    }



    public String getDesc() {
        return desc;
    }
    
	public String getCode() {
		return code;
	}

	public Integer getValue() {
		return value;
	}
    
    /**
     * 根据描述获取id
     * @param desc
     * @return
     */
    public static Integer getValue(String desc) {
        for (SourceEnum sourceEnum : SourceEnum.values()) {
            if (sourceEnum.getDesc().equals(desc)) {
                return sourceEnum.getValue();
            }
        }
        return null;
    }
}
