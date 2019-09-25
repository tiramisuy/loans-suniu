package com.rongdu.loans.loan.option.dwd;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 14:59:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class DWDBaseInfo implements Serializable{

    private static final long serialVersionUID = -205810473011376445L;
    @JsonProperty("orderInfo")
    private Orderinfo orderinfo;
    @JsonProperty("applyDetail")
    private Applydetail applydetail;

    private String channelCode;

}