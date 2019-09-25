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
public class SingleOverdue implements Serializable{

	private static final long serialVersionUID = -5924846262546993446L;
	
	@JsonProperty("hit_cnt")
    private int hitCnt;
    private int no;

}