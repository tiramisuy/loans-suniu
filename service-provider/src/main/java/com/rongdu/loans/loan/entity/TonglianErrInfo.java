package com.rongdu.loans.loan.entity;

/**
 * 通联支付-代付结果描述
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/4
 */
public enum TonglianErrInfo {

    //自定义返回码，保持统一
    I("I","处理中"),
    S("S","成功"),
    F("F","失败"),
    REPAY("REPAY","重新付款"),

    //外部返回码及描述信息
    E0("0","转账中"),
    E1("1","转账成功"),
    E_1("-1","转账失败"),
    E2("2","转账退款"),
    E0000("0000","交易处理成功（交易已受理）"),
    E4000("4000","交易处理成功"),
    E2000("2000","系统处理数据中"),
    E2001("2001","等待商户审核"),
    E2003("2003","等待受理"),
    E2005("2005","等待符核"),
    E2007("2007","提交银行处理中"),
    E2008("2008","实时交易超时"),
    E1108("1108","批次号重复"),
    E1000("1000","报文内容错或处理错");



    private String code;
    private String msg;

    TonglianErrInfo(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
