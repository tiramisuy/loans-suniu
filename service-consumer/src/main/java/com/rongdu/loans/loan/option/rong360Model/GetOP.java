package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/7/9.
 */
@Data
public class GetOP implements Serializable {

    private static final long serialVersionUID = -8376093053890116012L;
    @JsonProperty("order_no")
    private String orderNo;
}
