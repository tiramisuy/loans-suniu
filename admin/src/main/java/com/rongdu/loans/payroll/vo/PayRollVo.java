package com.rongdu.loans.payroll.vo;

import lombok.Data;

import java.util.Date;

/**
 * 放款薪资
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class PayRollVo {
    /**
     * 编号
     */
    private String id;
    /**
     * 放款卡号
     */
    private String accountNo;
    /**
     * 持卡人姓名
     */
    private String accountName;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 电话
     */
    private String mobile;

    /**
     * 放款金额
     */
    private Integer amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String status;
}
