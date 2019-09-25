package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈查询复贷和黑名单信息〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class QuickLoanOP implements Serializable {
    private static final long serialVersionUID = 1270565415853342907L;

    private String md5;

    @JsonProperty("id_card")
    private String idCard;

    @JsonProperty("user_mobile")
    private String userMobile;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("product_id")
    private String productId;
}
