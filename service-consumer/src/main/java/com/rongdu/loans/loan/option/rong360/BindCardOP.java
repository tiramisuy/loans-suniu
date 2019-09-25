package com.rongdu.loans.loan.option.rong360;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Auto-generated: 2018-06-29 16:34:3
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@lombok.Data
public class BindCardOP implements Serializable {

    private static final long serialVersionUID = 7207474993522063257L;
    @JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("bank_card")
    private String bankCard;
    @JsonProperty("open_bank")
    private String openBank;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("id_number")
    private String idNumber;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("bank_address")
    private String bankAddress;
    @JsonProperty("return_url")
    private String returnUrl;
    @JsonProperty("bind_card_src")
    private int bindCardSrc;
    @JsonProperty("bankCardType")
    private int bankcardtype;
    @JsonProperty("verify_code")
    private String verifyCode;
}