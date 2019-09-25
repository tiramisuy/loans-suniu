package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-14 10:55:49
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Nets implements Serializable{

    private static final long serialVersionUID = -2485631730234279270L;
    @JsonProperty("cell_phone")
    private String cellPhone;
    @JsonProperty("net_type")
    private String netType;
    private String place;
    @JsonProperty("start_time")
    private String startTime;
    private String subflow;
    private int subtotal;
    @JsonProperty("update_time")
    private String updateTime;

}