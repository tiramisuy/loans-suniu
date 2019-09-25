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
public class Applydetail implements Serializable {

    private static final long serialVersionUID = -7496585711689270690L;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_education")
    private String userEducation;
    @JsonProperty("is_on_type")
    private String isOnType;
    @JsonProperty("work_period")
    private String workPeriod;
    @JsonProperty("user_income_by_card")
    private String userIncomeByCard;
    @JsonProperty("user_social_security")
    private String userSocialSecurity;
    private String famadr;
    private String validstartdate;
    private String validenddate;
    private String agency;
    private String nation;

}