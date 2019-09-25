package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-12 10:27:22
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 3441023591142773189L;


    @JsonProperty("user_name")
    private String userName;

    private String phone;

    @JsonProperty("id_card")
    private String idCard;

    private String gender;

    private Integer role;

    @JsonProperty("id_card_address")
    private String idCardAddress;

    private String nation;

    @JsonProperty("id_positive")
    private String idPositive;

    @JsonProperty("id_negative")
    private String idNegative;

    private String face;

    @JsonProperty("id_signing_authority")
    private String idSigningAuthority;

    @JsonProperty("id_expiry_date")
    private String idExpiryDate;

    @JsonProperty("id_start_date")
    private String idStartDate;

    @JsonProperty("id_positive_valid_state")
    private String idPositiveValidState;

    @JsonProperty("idNegativeValidState")
    private String id_negative_valid_state;

    private String marry;

    private String educate;

    @JsonProperty("living_address")
    private String livingAddress;


    @JsonProperty("company_address")
    private String companyAddress;

    @JsonProperty("company_name")
    private String companyName;

}