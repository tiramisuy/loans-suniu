package com.rongdu.loans.tongdun.common;

/**
 * 调用完成状态码
 * Created by MARK on 2017/8/20.
 */
public enum  FraudApiRespCode {

    SUCCESS("SUCCESS","操作成功","操作成功"),
    C_000("000","认证失败","因系统认证引起的其他未知错误"),
    C_001("001","用户认证失败","合作方标识和合作方密钥认证失败"),
    C_002("002","用户授权过期","合作方标识和合作方密钥授权过期"),
    C_003("003","用户密码错误","合作方用户密码错误"),
    C_100("100","非法参数","因参数引起的未知错误"),
    C_101("101","参数不能为空","例如：未传入必填参数"),
    C_102("102","参数类型不正确","例如：Integer类型传入了String类型值"),
    C_103("103","参数超过最大长度","个别参数有长度限制"),
    C_104("104","参数格式不正确","例如：字符串时间参数格式不正确"),
    C_105("105","时间参数范围不正确","例如：起始时间大于截止时间"),
    C_106("106","枚举值不存在","例如：字符串枚举类型传入了一个不存在的枚举值"),
    C_200("200","请求已完成",""),
    C_301("301","服务未购买","未取得访问资源的授权"),
    C_302("302","服务已被禁用","即使获取授权也不能访问"),
    C_303("303","流量不足","计费流量已用尽，需要付款后再试"),
    C_304("304","服务已过期","取得的授权已经过期"),
    C_401("401","请求超时","合作方用户请求超时"),
    C_404("404","找不到资源","没有找到对应的资源（例如：找不到报告）"),
    C_405("405","不支持当前服务类型",""),
    C_500("500","服务器内部错误",""),
    C_551("551","因安全策略暂时禁止访问","服务因安全策略暂时禁止访问");

    //响应代码
    private String code;
    //响应消息
    private String msg;
    //处理方式
    private String solution;


    FraudApiRespCode(String code, String msg, String solution) {
        this.code = code;
        this.msg = msg;
        this.solution = solution;
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
    public String getSolution() {
        return solution;
    }
    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
