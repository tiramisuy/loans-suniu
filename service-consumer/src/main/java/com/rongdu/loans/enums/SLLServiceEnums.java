package com.rongdu.loans.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/12
 * @since 1.0.0
 */
public enum SLLServiceEnums {

    SLL_APPROVEFEEDBACK("order.approvefeedback","RSA","1.0","奇虎360推送审批结论接口"),
    SLL_ORDERFEEDBACK("order.orderfeedback","RSA","1.0","奇虎360推送订单状态接口"),
    SLL_BINDCARDFEEDBACK("order.bindcardfeedback","RSA","1.0","奇虎360推送绑卡结果接口"),
    SLL_PUSHREPAYMENT("order.pushrepayment","RSA","1.0","奇虎360推送还款计划接口"),
    SLL_REPAYFEEDBACK("order.repayfeedback","RSA","1.0","奇虎360推送还款状态接口"),
    SLL_FINDFILE("order.pullcreditmaterial", "RSA", "1.0", "奇虎360查询用户信审信息接口");

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

    SLLServiceEnums(String method, String signType, String version, String desc) {
        this.method = method;
        this.signType = signType;
        this.version = version;
        this.desc = desc;
    }

    /**
     * @Description: 根据接口名获取枚举
     */
    public static SLLServiceEnums get(String method) {
        for (SLLServiceEnums p : SLLServiceEnums.values()) {
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
