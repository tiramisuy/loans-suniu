package com.rongdu.loans.pay.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


/**
 * 通联
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/8
 */
@Data
public class TlWithholdQueryOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 543689232460661348L;
    /**
     * 要查询的交易流水
     * 也就是原请求交易中的REQ_SN的值
     */
    private String reqSn;

    /**
     * 订单发送时间
     */
    private String txnDate;

}
