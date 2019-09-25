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
public class ContactsOverdue implements Serializable{

    /**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/ 
	private static final long serialVersionUID = -5341136585462618346L;
	@JsonProperty("hit_cnt")
    private int hitCnt;
    private int seconds;
    private int cnt;
    private int type;

}