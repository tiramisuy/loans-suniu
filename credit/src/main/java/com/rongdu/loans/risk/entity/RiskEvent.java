/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 风险事件：指风险在什么时候发生，如：注册、登录、绑卡、贷款、提现等Entity
 * @author sunda
 * @version 2017-08-14
 */
public class RiskEvent extends BaseEntity<RiskEvent> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *风险事件
	  */
	private String name;		
	
	public RiskEvent() {
		super();
	}

	public RiskEvent(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}