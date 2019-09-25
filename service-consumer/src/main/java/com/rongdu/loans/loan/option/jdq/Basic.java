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
public class Basic implements Serializable {

    private static final long serialVersionUID = -7531473231567275056L;
    @JsonProperty("cell_phone")
    private String cellPhone;
    private String idcard;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("reg_long")
    private int regLong;
    @JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("update_time")
    private String updateTime;


}