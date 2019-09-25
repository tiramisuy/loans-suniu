package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lee on 2018/6/29.
 */
@lombok.Data
public class BindStatus implements Serializable {
    private static final long serialVersionUID = 5626014179910420038L;
    @JsonProperty("bind_status")
    private String bindStatus;
}
