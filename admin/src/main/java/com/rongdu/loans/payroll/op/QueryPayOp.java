package com.rongdu.loans.payroll.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/16
 */
@Data
public class QueryPayOp {

    /**
     * 请求no
     */
    @NotBlank(message = "请求订单号不可为空")
    private String reqNo;

    /**
     * 订单发送时间
     */
    @NotBlank(message = "订单发送时间不可为空")
    private String txnDate;

}
