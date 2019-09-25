package com.rongdu.loans.payroll.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class BindCardOp {

    /**
     * 绑卡id
     */
    @NotBlank(message = "绑卡id不可为空")
    private String payRollBindCardID;
    /**
     * 绑卡验证码
     */
    @NotBlank(message = "绑卡验证码不可为空")
    private String preMsgCode;

    /**
     * 绑卡前置流水号
     */
    private String reqNo;
}

