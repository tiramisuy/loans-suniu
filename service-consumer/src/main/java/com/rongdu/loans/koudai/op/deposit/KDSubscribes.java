package com.rongdu.loans.koudai.op.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-12-05 17:20:18
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class KDSubscribes implements Serializable {

    private static final long serialVersionUID = -6342087114371945033L;
    @JsonProperty("outTradeNo")
    private String outTradeNo;
    @JsonProperty("repaymentSub")
    private List<KDRepaymentsub> repaymentSub;


}