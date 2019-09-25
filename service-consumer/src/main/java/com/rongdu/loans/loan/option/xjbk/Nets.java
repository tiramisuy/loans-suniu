package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Nets implements Serializable{

    private static final long serialVersionUID = 4138480686722603613L;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("cell_phone")
    private String cellPhone;

}