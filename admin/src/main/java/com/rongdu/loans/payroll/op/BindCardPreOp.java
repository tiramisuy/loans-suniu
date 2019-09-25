package com.rongdu.loans.payroll.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 绑卡op
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class BindCardPreOp {

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不可为空")
    private String accountName;
    /**
     * 卡号
     */
    @NotBlank(message = "卡号不可为空")
    private String accountNo;
    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不可为空")
    private String idCard;
    /**
     * 银行预留电话
     */
    @NotBlank(message = "电话不可为空")
    private String mobile;

}
