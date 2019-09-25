package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-06 13:42:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ContractOP implements Serializable{

    private static final long serialVersionUID = 2987370519650052713L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("contract_return_url")
    private String contractReturnUrl;
    @JsonProperty("contract_page")
    private String contractPage;
    @JsonProperty("contract_pos")
    private String contractPos;
}