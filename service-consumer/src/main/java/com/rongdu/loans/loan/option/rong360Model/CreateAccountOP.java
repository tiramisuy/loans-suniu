package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/2/17
 * @since 1.0.0
 */
@Data
public class CreateAccountOP implements Serializable {
    private static final long serialVersionUID = -1246105228964366926L;

    /**
     * 订单编号
     */
    @JsonProperty("order_no")
    private String orderNo;

    /**
     * 跳转回调地址
     */
    @JsonProperty("open_return_url")
    private String openReturnUrl;
}
