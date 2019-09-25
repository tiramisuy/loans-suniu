package com.rongdu.loans.cust.vo;

import java.io.Serializable;
/*
 * 渠道实体类
 */
public class ChannelVO implements Serializable{
	
	private String cid;
	private String cName;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	
}
