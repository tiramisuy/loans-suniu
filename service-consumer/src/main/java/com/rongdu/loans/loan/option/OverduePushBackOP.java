package com.rongdu.loans.loan.option;

import lombok.Data;

import java.io.Serializable;

@Data
public class OverduePushBackOP implements Serializable{

	private static final long serialVersionUID = 7134782501420164575L;
	
	
	public Integer termType;
	
	private String startTime;
	
	private String endTime;

	private String channelId;
	/**
	 * 是否为复贷
	 */
	private String loanSucess;

}
