package com.rongdu.loans.loan.option.dwd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 14:12:48
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class DWDInfo implements Serializable {

    private static final long serialVersionUID = 5023820546676053344L;
    @JsonProperty("source_id")
    private String sourceId;
    private String method;
    private String sign;
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("des_key")
    private String desKey;
    @JsonProperty("biz_data")
    private String bizData;
    @JsonProperty("biz_enc")
    private String bizEnc;

}