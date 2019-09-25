package com.rongdu.loans.loan.option.SLL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Bill implements Serializable {

    private static final long serialVersionUID = 1529502687449819816L;
    private String month;
    @JsonProperty("call_pay")
    private int callPay;
    @JsonProperty("package_fee")
    private int packageFee;
    @JsonProperty("msg_fee")
    private int msgFee;
    @JsonProperty("tel_fee")
    private int telFee;
    @JsonProperty("net_fee")
    private int netFee;
    @JsonProperty("addtional_fee")
    private int addtionalFee;
    @JsonProperty("preferential_fee")
    private int preferentialFee;
    @JsonProperty("other_fee")
    private int otherFee;
    private int score;

}