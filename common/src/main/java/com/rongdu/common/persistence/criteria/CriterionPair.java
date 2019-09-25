/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.persistence.criteria;

import java.io.Serializable;

/**
 * SQL WHERE过滤条件(单个)，与逻辑关系(and,or,not)配对
 * @author sunda
 * @version 2017-06-30
 */
public class CriterionPair implements Serializable {

	private static final long serialVersionUID = -326326755704409623L;
	/**
	 * 逻辑关系
	 */
	private LogicType logicType;
	/**
	 * 过滤条件
	 */
	private Criterion criterion;
	
	/**
	 * 逻辑关系-对应LogicType的取值
	 */
	private String logic;
	
	public CriterionPair(){
	}
	
	public CriterionPair(LogicType logicType,Criterion criterion){
		this.logicType = logicType;
		this.logic = logicType.getValue();
		this.criterion = criterion;
	}

	public LogicType getLogicType() {
		return logicType;
	}

	public void setLogicType(LogicType logicType) {
		this.logicType = logicType;
	}

	public Criterion getcriterion() {
		return criterion;
	}

	public void setcriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}
	
}
