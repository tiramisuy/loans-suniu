package com.rongdu.loans.loan.option.dwd.report;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class ServiceDetails implements Serializable {

    private static final long serialVersionUID = 4265488067483540044L;
    @JsonProperty("interact_mth")
    private String interactMth;
    @JsonProperty("interact_cnt")
    private int interactCnt;
}