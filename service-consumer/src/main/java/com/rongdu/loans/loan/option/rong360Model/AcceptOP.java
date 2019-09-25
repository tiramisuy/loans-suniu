package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-06 16:54:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class AcceptOP implements Serializable{

    private static final long serialVersionUID = -6846823158183871671L;
    private String md5;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("product_id")
    private int productId;


}