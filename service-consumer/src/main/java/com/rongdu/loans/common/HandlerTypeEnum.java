package com.rongdu.loans.common;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
public enum HandlerTypeEnum {
    /**
     * 目前用于发工资
     */
    SYSTEM_HANDLER("system_handler", "系统内部调用渠道"),
    SANS_HANDLER("sans_handler", "外部调用渠道");

    private String code;
    private String desc;

    private HandlerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
