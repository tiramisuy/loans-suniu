package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-06 17:42:39
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class AcceptVO implements Serializable {

    private static final long serialVersionUID = -2875049199574874619L;
    @JsonProperty("is_reloan")
    private int isReloan;
    @JsonProperty("approval_amount")
    private float approvalAmount;
    @JsonProperty("approval_term")
    private List<Integer> approvalTerm;
    @JsonProperty("term_unit")
    private int termUnit;


}