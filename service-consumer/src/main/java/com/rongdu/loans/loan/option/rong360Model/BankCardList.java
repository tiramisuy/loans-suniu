package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-06 10:32:28
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class BankCardList implements Serializable{

    private static final long serialVersionUID = -3790737735044669405L;
    @JsonProperty("open_bank")
    private String openBank;
    @JsonProperty("bank_card")
    private String bankCard;
    @JsonProperty("card_type")
    private int cardType;
    @JsonProperty("is_repay_card")
    private int isRepayCard;
}