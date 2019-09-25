package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/7/4.
 */
@Data
public class NeedVerify implements Serializable {
    private static final long serialVersionUID = -2865329135629862917L;
    @JsonProperty("is_need_verify")
    private String isNeedVerify;
}
