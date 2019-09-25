package com.rongdu.loans.pay.tonglian.vo;

/**
 * 通联独立放款应答码
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/12
 */
public enum TonglianIndependentRspEnum {
    TR_00("00", "成功", "成功"),
    TR_01("01", "报文格式错误", "失败"),
    TR_02("02", "签名验证失败", "失败"),
    TR_03("03", "验证要素缺失", "失败"),
    TR_04("04", "重复交易", "失败"),
    TR_05("05", "商户状态不正确", "失败"),
    TR_06("06", "商户无此交易权限", "失败"),
    TR_07("07", "交易金额超限", "失败"),
    TR_08("08", "风险交易", "失败"),
    TR_09("09", "无此交易权限", "失败"),
    TR_10("10", "未查询到交易信息", "失败"),
    TR_11("11", "验证原交易信息失败", "失败"),
    TR_12("12", "商户不支持该发卡行交易", "失败"),
    TR_13("13", "余额不足", "失败"),
    TR_14("14", "交易失败,详情请咨询银联或发卡行", "失败"),
    TR_15("15", "验证要素(姓名、密码、有效期、CVN2、手机号等)不正确,交易失败", "失败"),
    TR_16("16", "密码输入次数超限", "失败"),
    TR_17("17", "卡状态不正确", "失败"),
    TR_18("18", "交易处理中，请发起查询交易", "未知"),
    TR_19("19", "交易状态未明，请查询对账结果", "未知"),
    TR_20("20", "交易失败", "失败"),
    TR_99("99", "交易处理异常", "未知");

    private String code;
    private String status;
    private String desc;

    TonglianIndependentRspEnum(String code, String status, String desc) {
        this.code = code;
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
