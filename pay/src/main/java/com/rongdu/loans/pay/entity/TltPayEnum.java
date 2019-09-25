package com.rongdu.loans.pay.entity;

/**
 * 通联支付
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/3
 */
public enum TltPayEnum {

    TX_310001("310001", "快捷签约短信发起"),
    TX_310002("310002", "协议支付签约"),
    TX_310003("310003", "快捷协议解约"),
    TX_310011("310011", "协议支付"),
    TX_310010("310010", "协议支付并签约"),
    TX_200004("200004", "交易查询"),
    TX_100011("100011", "单笔实时代收(代扣)"),
    TX_100014("100014", "单笔实时代付(出金/提现)");

    private String code;
    private String desc;

    private TltPayEnum(String code, String desc) {
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
