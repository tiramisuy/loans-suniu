package com.rongdu.loans.loan.option.rong360Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-07-06 10:32:28
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class BindBankCardVO implements Serializable{

	private static final long serialVersionUID = -197559223940747012L;
	
	@JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("bank_card_list")
    private List<BankCardList> bankCardList;
}