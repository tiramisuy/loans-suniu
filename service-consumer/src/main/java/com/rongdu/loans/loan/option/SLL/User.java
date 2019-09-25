package com.rongdu.loans.loan.option.SLL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -797045818443175563L;
    @JsonProperty("user_source")
    private String userSource;
    @JsonProperty("id_card")
    private String idCard;
    private String addr;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("phone_remain")
    private int phoneRemain;
    private String phone;
    @JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("star_level")
    private String starLevel;
    private int authentication;
    @JsonProperty("phone_status")
    private String phoneStatus;
    @JsonProperty("package_name")
    private String packageName;


}