package com.rongdu.loans.loan.option.rongTJreport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: ContactCheck.java  
* @Package com.rongdu.loans.loan.option.rongTJreport  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月18日  
* @version V1.0  
*/
@Data
public class RongContactCheck implements Serializable,Comparable<RongContactCheck> {

	private static final long serialVersionUID = -8173593978216332678L;

	private String phone;
	private String name;
	@JsonProperty("talk_cnt")
	private int talkCnt;
	@JsonProperty("talk_seconds")
	private int talkSeconds;
	private int index;
	
	@Override
	public int compareTo(RongContactCheck o) {
		int i = o.getTalkSeconds() - this.getTalkSeconds();
		return i;
	}
}
