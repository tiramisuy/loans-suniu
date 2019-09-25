package com.rongdu.loans.loan.option.rongTJreportv1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.common.utils.StringUtils;

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
	
	public void setTalkCnt(int talkCnt) {
		this.talkCnt = talkCnt;
	}
	
	public boolean setTalkSeconds(int talkSeconds) {
		if (this.talkSeconds < talkSeconds) {
			this.talkSeconds = talkSeconds;
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(RongContactCheck o) {
		int i = o.getTalkSeconds() - this.getTalkSeconds();
		return i;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == this.getClass()) {
			RongContactCheck rCheck = (RongContactCheck) obj;
			if (!StringUtils.isBlank(rCheck.getPhone())) {
				return rCheck.getPhone().equals(this.getPhone());
			}
		}
		return false;
	}
	
    @Override
    public int hashCode() {
    	if (!StringUtils.isBlank(phone)) {
    		return phone.hashCode();
		}
    	return super.hashCode();
    }
}
