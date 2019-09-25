package com.rongdu.loans.loan.option.SLL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送用户绑定银行卡〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class CardBindOP implements Serializable {
    private static final long serialVersionUID = -7418728876718683027L;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("bank_card")
    private String bankCard;

    @JsonProperty("open_bank")
    private String openBank;

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("id_number")
    private String idNumber;

    @JsonProperty("user_mobile")
    private String userMobile;

    @JsonProperty("bank_address")
    private String bankAddress;

    @JsonProperty("verify_code")
    private String verifyCode;

    @JsonProperty("bind_card_src")
    private String bindCardSrc;
}
