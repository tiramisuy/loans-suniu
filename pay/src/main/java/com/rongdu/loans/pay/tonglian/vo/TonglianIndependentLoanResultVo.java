package com.rongdu.loans.pay.tonglian.vo;

import lombok.Data;

/**
 * 通联独立放款vo
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/12
 */
@Data
public class TonglianIndependentLoanResultVo {
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
    /** 交易金额 */
    private String amount;
    /** 交易币种 */
    private String currency;
    /** 账号 */
    private String card_no;
    /** 响应码 */
    private String resp_code;
    /** 响应码描述 */
    private String resp_msg;
    /** 清算日期 */
    private String stlm_date;
    /** 商户保留域 */
    private String mer_addmsg;
    /** 保留域 */
    private String add_msg;

}
