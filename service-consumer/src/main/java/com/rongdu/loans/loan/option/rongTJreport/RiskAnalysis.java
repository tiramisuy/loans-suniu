package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class RiskAnalysis implements Serializable{

	private static final long serialVersionUID = 8693575282609217844L;
	
	@JsonProperty("contacts_overdue")
    private List<ContactsOverdue> contactsOverdue;
    @JsonProperty("single_overdue")
    private List<SingleOverdue> singleOverdue;

}