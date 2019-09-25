package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-08 10:9:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ServiceDetails implements Serializable {

    private static final long serialVersionUID = 457102055480556978L;
    @JsonProperty("interact_cnt")
    private int interactCnt;
    @JsonProperty("interact_mth")
    private String interactMth;


}