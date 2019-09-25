package com.rongdu.loans.pay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通联独立放款查询结果
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/12
 */
@Data
public class TonglianQueryResultVo implements Serializable {
    /** 版本号 */
    private String version;
    /** 签名方法 */
    private String sign_method;
    /** 签名 */
    private String sign;
    /** 交易类型  */
    private String trans_code;
    /** 商户代码 */
    private String mer_code;
    /** 商户订单号 */
    private String order_no;
    /** 订单发送时间 */
    private String txn_date;
    /** 响应码 */
    private String resp_code;
    /** 响应码描述 */
    private String resp_msg;
    /** 原交易应答码 */
    private String org_resp_code;
    /** 原交易应答信息 */
    private String org_resp_msg;

    @Override
    public String toString() {
        return "TonglianQueryResultVo{" +
                "version='" + version + '\'' +
                ", sign_method='" + sign_method + '\'' +
                ", sign='" + sign + '\'' +
                ", trans_code='" + trans_code + '\'' +
                ", mer_code='" + mer_code + '\'' +
                ", order_no='" + order_no + '\'' +
                ", txn_date='" + txn_date + '\'' +
                ", resp_code='" + resp_code + '\'' +
                ", resp_msg='" + resp_msg + '\'' +
                ", org_resp_code='" + org_resp_code + '\'' +
                ", org_resp_msg='" + org_resp_msg + '\'' +
                '}';
    }
}
