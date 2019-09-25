package com.rongdu.loans.loan.vo.sll;

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
public class ContractGetVO implements Serializable {
    private static final long serialVersionUID = 8345560425612903524L;

    @JsonProperty("contract_url")
    private String contractUrl;
}
