package com.rongdu.loans.koudai.op.deposit;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-05 17:20:18
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class KDRepaymentsub implements Serializable {

    private static final long serialVersionUID = -6512001491169808485L;
    private int period;
    @JsonProperty("trueRepaymentTime")
    private int trueRepaymentTime;
    @JsonProperty("truePrincipal")
    private int truePrincipal;
    @JsonProperty("trueInterest")
    private int trueInterest;
    @JsonProperty("remissionAmount")
    private int remissionAmount;
    @JsonProperty("repayStatus")
    private int repayStatus;
    @JsonProperty("repaymentType")
    private int repaymentType;
    private String bankSerialno;

}