package com.rongdu.loans.enums;

/**
 * @version V1.0
 * @Title: DWDServiceEnums.java
 * @Package com.rongdu.loans.enums
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yuanxianchu
 * @date 2018年10月30日
 */
public enum DWDServiceEnums {
	
	DWD_APPROVEFEEDBACK("api.audit.result","RSA","1.0","大王贷推送审批结论接口"),
	DWD_ORDERFEEDBACK("api.order.status","RSA","1.0","大王贷推送订单状态接口"),
	DWD_BINDCARDFEEDBACK("api.bank.result","RSA","1.0","大王贷推送绑卡结果接口"),
	DWD_PUSHREPAYMENT("api.payment.plan","RSA","1.0","大王贷推送还款计划接口"),
	DWD_REPAYFEEDBACK("api.payment.status","RSA","1.0","大王贷推送还款状态接口"),
    DWD_FINDFILE("api.resource.findfile", "RSA", "1.0", "大王贷查询文件接口"),
    DWD_CHARGE("api.charge.data", "RSA", "1.0", "大王贷查询运营商接口");

    /**
     * 接口名
     */
    private String method;

    /**
     * 加密方法
     */
    private String signType;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 接口描述
     */
    private String desc;

    DWDServiceEnums(String method, String signType, String version, String desc) {
        this.method = method;
        this.signType = signType;
        this.version = version;
        this.desc = desc;
    }

    /**
     * @Description: 根据接口名获取枚举
     */
    public static DWDServiceEnums get(String method) {
        for (DWDServiceEnums p : DWDServiceEnums.values()) {
            if (p.getMethod().equals(method)) {
                return p;
            }
        }
        return null;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
