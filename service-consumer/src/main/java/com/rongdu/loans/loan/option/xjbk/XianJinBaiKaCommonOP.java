
package com.rongdu.loans.loan.option.xjbk;


import com.rongdu.loans.loan.option.xjbk2.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class XianJinBaiKaCommonOP implements Serializable {
    private static final long serialVersionUID = -3356162293293277307L;
    // 用户过滤
    private String user_name; // 用户姓名
    private String user_phone; // 用户手机号
    private String user_idcard; // 用户身份证号码
    private String md5; // md5(手机号+身份证)

    // 订单推送
    private UserInfo user_info; // 用户基本信息
    private OrderInfo order_info; // 订单基本信息
    private UserAdditional user_additional; // 用户补充信息
    private UserVerify user_verify; // 用户认证信息

    // 绑卡
    private String order_sn;// 借款订单唯一编号
    private String bank_code; // 绑卡银行编码
    private String card_number; // 银行卡号
    private String card_phone; // 银行预留手机号

    // 获取订单状态
    private Integer act_type;// 操作类型

    // 借款试算
    private BigDecimal loan_amount;// 审批金额 单位（分）
    private Integer loan_term;// 审批期限
    private Integer term_type;// 1:按天; 2：按月; 3：按年

    // 订单还款
    private Integer repay_type;// 0：仅还当期，1，分期业务全额还款

    private String auth_type;
    private String confirm_result;

    private String verify_code;
}
