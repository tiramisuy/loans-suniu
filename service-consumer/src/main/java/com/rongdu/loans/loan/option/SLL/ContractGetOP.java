package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询借款合同〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class ContractGetOP implements Serializable {
    private static final long serialVersionUID = 7756944696446822478L;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("contract_return_url")
    private String contractReturnUrl;

    private String page;
}
