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
public class Calls implements Serializable {

    private static final long serialVersionUID = 7548611860233189366L;
    @JsonProperty("call_type")
    private String callType;
    @JsonProperty("cell_phone")
    private String cellPhone;
    @JsonProperty("init_type")
    private String initType;
    @JsonProperty("other_cell_phone")
    private String otherCellPhone;
    private String place;
    @JsonProperty("start_time")
    private String startTime;
    private int subtotal;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("use_time")
    private int useTime;

}