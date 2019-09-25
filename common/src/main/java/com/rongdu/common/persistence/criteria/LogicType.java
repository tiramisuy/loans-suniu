/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.persistence.criteria;

/**
 * SQL WHERE过滤条件的逻辑关系
 * @author sunda
 * @version 2017-06-30
 */
public enum LogicType {
	
	AND(" AND "), OR(" OR "), NOT(" NOT "), NONE(" ");
	
	private String value;
	
	LogicType(){		
	}
	
	LogicType(String value){
		this.value = value;
	}
	
	public String value(){
		return getValue();
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
