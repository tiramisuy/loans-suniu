package com.rongdu.loans.payroll.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 放款薪资
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class PayRollOp {
    /**
     * 放款卡号
     */
    @NotBlank(message = "卡号不可为空")
    private String accountNo;
    /**
     * 持卡人姓名
     */
    @NotBlank(message = "持卡人姓名不可为空")
    private String accountName;
    /**
     * 银行编码
     */
    @NotBlank(message = "银行编码不可为空")
    private String bankCode;
    /**
     * 电话
     */
    @NotBlank(message = "电话不可为空")
    private String mobile;

    /**
     * 放款金额
     */
    @NotNull(message = "金额不可为空")
    private Double amount;

}
