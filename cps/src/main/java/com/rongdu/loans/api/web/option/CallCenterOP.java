package com.rongdu.loans.api.web.option;

import java.io.Serializable;
/**
 * 
* @Description: 呼叫中心 
* @author: RaoWenbiao
* @date 2019年1月16日
 */
public class CallCenterOP implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 坐席id
	 */
	private String id;
	/**
	 * 坐席工号
	 */
	private String operator_id;
	
	/**
	 * 电话
	 */
	private String called;
	
	private String cust;

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	public String getCust() {
		return cust;
	}

	public void setCust(String cust) {
		this.cust = cust;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	
	
	
}
