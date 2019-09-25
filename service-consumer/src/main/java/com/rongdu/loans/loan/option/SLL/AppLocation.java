package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-10 13:56:59
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class AppLocation implements Serializable {

    private static final long serialVersionUID = 1452168237547364839L;
    private String lat;
    private String lon;
    private String address;
    @JsonProperty("district_code")
    private String districtCode;

}