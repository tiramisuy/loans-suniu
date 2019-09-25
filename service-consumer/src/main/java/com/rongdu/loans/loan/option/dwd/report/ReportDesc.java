package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/27
 * @since 1.0.0
 */
@Data
public class ReportDesc implements Serializable {
    private static final long serialVersionUID = -8686335830681977783L;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("rpt_id")
    private String rptId;
    private String version;
    private String token;
}
