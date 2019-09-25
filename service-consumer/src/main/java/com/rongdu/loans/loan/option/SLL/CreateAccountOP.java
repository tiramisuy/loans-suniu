package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2019/2/22.
 */
@Data
public class CreateAccountOP implements Serializable {
    private static final long serialVersionUID = -1316792583576023244L;
    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("return_url")
    private String returnUrl;
}
