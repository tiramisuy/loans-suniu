package com.rongdu.loans.payroll.vo;

import lombok.Data;

/**
 * 绑卡Vo
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class PayRollBindCardVo {

    /**
     * id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 真实姓名
     */
    private String accountName;
    /**
     * 卡号
     */
    private String accountNo;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 银行预留电话
     */
    private String mobile;

    /**
     * 签约绑卡协议号
     */
    private String bindNo;

    /**
     * 预绑卡流水号
     */
    private String reqNo;

    /**
     * 是否开通协议,0-否 1-是
     */
    private String isBind;
}
