package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SLLReq implements Serializable {


    private static final long serialVersionUID = 5838681971411954780L;
    private String sign;
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("biz_enc")
    private String bizEnc;
    @JsonProperty("des_key")
    private String desKey;
    @JsonProperty("biz_data")
    private String bizData;

}
