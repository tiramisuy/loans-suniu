package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class WorkOfficeInfo implements Serializable {

    private static final long serialVersionUID = -5638431973138207503L;
    @JsonProperty("company_name")
    private String companyName;
    private String areas;
    private String address;
    private String type;
    @JsonProperty("work_age")
    private String workAge;
    @JsonProperty("pay_type")
    private String payType;
    private String revenue;
    @JsonProperty("tel_area")
    private String telArea;
    @JsonProperty("tel_no")
    private String telNo;
    private String tel;


}