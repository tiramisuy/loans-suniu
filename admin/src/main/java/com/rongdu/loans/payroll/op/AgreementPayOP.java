package com.rongdu.loans.payroll.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Data
public class AgreementPayOP {
    @NotBlank(message = "协议号不可为空")
    private String bindId;
    @NotBlank(message = "姓名不可为空")
    private String realName;
    @NotBlank(message = "代扣金额不可为空")
    private String amount;
    private String userId;

}
