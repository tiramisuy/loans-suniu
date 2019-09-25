package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询审批结论〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class ConclusionPullVO implements Serializable {
    private static final long serialVersionUID = -8575023821410456831L;

    @JsonProperty("order_no")
    private String orderNo;

    private Integer conclusion;

    @JsonProperty("approval_time")
    private String approvalTime;

    @JsonProperty("term_unit")
    private Integer termUnit;

    @JsonProperty("amount_type")
    private Integer amountType;

    @JsonProperty("term_type")
    private Integer termType;

    @JsonProperty("approval_amount")
    private Integer approvalAmount;

    @JsonProperty("service_fee")
    private BigDecimal serviceFee;

    @JsonProperty("approval_term")
    private Integer approvalTerm;

    @JsonProperty("refuse_time")
    private String refuseTime;

    private String remark;

    public void setTermUnit(Integer term, String repayFreq){
        if (term > 1){
            if ("D".equals(repayFreq))
                this.setTermUnit(4);//4=多期产品（审批期限单位为天）
            if ("M".equals(repayFreq))
                this.setTermUnit(2);//2=多期产品（审批期限单位为月）
        }else {
            this.setTermUnit(1);//单期产品
        }
    }
}
