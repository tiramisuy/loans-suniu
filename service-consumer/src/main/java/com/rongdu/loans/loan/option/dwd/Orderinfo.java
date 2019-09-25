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
public class Orderinfo implements Serializable {

    private static final long serialVersionUID = 861061960477688884L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("is_reloan")
    private int isReloan;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("application_amount")
    private String applicationAmount;
    @JsonProperty("order_time")
    private String orderTime;
    private String bank;

}