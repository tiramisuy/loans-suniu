package com.rongdu.loans.loan.option.rongTJreport;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-17 19:0:50
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class UserPortrait implements Serializable{

	private static final long serialVersionUID = 6309254114467291351L;
	
	@JsonProperty("silent_days")
    private SilentDays silentDays;
    @JsonProperty("contacts_distribution")
    private ContactsDistribution contactsDistribution;
    @JsonProperty("both_call_cnt")
    private int bothCallCnt;
    @JsonProperty("night_activities")
    private NightActivities nightActivities;

}