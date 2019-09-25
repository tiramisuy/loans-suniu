package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/5/31.
 */
@Data
public class ApplyBindCardVO implements Serializable{
    private static final long serialVersionUID = -6351260163802671061L;
    @JsonProperty("bind_status")
    private String bindStatus;
    private String remark;
}
