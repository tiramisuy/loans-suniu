package com.rongdu.loans.enums;

import lombok.Getter;

/**
 * @author zcb
 * 产品类
 */
@Getter
public enum LoanProductEnum {
    XJD("XJD", "聚宝钱包","",""),
    CCD("CCD", "诚诚普惠","",""),
    ZJD("ZJD", "租金贷","",""),
    TYD("TYD", "同业贷","",""),
    TFL("TFL", "复利小贷","",""),
    LYFQ("LYFQ", "旅游分期","",""),
    XJDFQ("XJDFQ", "现金贷分期","",""),

    JDQ("JDQXJD", "金牛分期","0","2000元28天分4期"),
    JNFQ("JNFQ", "金牛分期","0","2500元28天分4期"),
    JN("JN", "金牛钱包","0","1500元8天单期"),
    JN2("JN2", "金牛钱包","0","2000元8天分4期");


    private String id;
    private String name;
    private String type;// 产品类型 0：现金贷
    private String desc;// 产品描述

    LoanProductEnum(String id, String name, String type, String desc) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.desc = desc;
    }

    public static LoanProductEnum get(String id) {
        for (LoanProductEnum p : LoanProductEnum.values()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }


    /**
     * 根据id匹配描述
     *
     * @param id
     * @return
     */
    public static String getDesc(String id) {
        for (LoanProductEnum temp : LoanProductEnum.values()) {
            if (temp.getId().equals(id)) {
                return temp.getName();
            }
        }
        return null;
    }

}
