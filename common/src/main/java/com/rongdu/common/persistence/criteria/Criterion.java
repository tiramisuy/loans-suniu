/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.persistence.criteria;

import java.io.Serializable;
import java.util.List;

/**
 * SQL WHERE过滤条件(单个)
 * @author sunda
 * @version 2017-06-30
 */
public class Criterion implements Serializable {
	private static final long serialVersionUID = 6783696506216411802L;
	/**
	 * 列名
	 */
	private String column;
	/**
	 * 过滤条件
	 */
	private MatchType matchType;
	/**
	 * 需要匹配的值
	 */
	private Object value;
	/**
	 * 需要匹配的第二个值
	 */
    private Object secondValue;
    
	/**
	 * 过滤条件-取值
	 */
	private String match;
    
    /**
     * 传单个值，如：String,int,List...有可能是数组链表
     */
    private boolean singleValue;
    /**
     * 传两个个值，用于between条件
     */
    private boolean betweenValue;
    /**
     * 传单个值时，该传值对象是否为数组链表，如：批量删除时，传入id数据
     */
    private boolean listValue;
	
	public Criterion(){
	}
	
	public Criterion(String column,MatchType matchType,Object value){
		this.column = column;
		this.matchType = matchType;
		this.match = matchType.getValue();
		this.value = value;
		if (value!=null) {
			if (value instanceof List<?>||value.getClass().isArray()) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
			if (value instanceof String) {
				this.value = "'"+value.toString()+ "'";
			} 
		}else {
			this.singleValue = true;
		}
	}
	
	public Criterion(String column,MatchType matchType,Object value,Object secondValue){
		this.column = column;
		this.matchType = matchType;
		this.match = matchType.getValue();
		this.value = value;
		this.secondValue = secondValue;
		this.betweenValue = true;
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(Object secondValue) {
		this.secondValue = secondValue;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public void setListValue(boolean listValue) {
		this.listValue = listValue;
	}
	
	/**
	 * 等于
	 * e.g：Cretiron.eq("status",1)，生成SQL：status=1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion eq(String column,Object value){
		return new Criterion(column, MatchType.EQ, value);
	}
	
	/**
	 * 不等于
	 * e.g：Cretiron.ne("status",1)，生成SQL：status!=1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion ne(String column,Object value){
		return new Criterion(column, MatchType.NE, value);
	}
	
	/**
	 * 模糊匹配
	 * e.g：Cretiron.like("name","%张三%")，生成SQL：name LIKE %张三%
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion like(String column,Object value){
		return new Criterion(column, MatchType.LIKE, value);
	}
	
	/**
	 * 小于
	 *  e.g：Cretiron.lt("status",1)，生成SQL：status<1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion lt(String column,Object value){
		return new Criterion(column, MatchType.LT, value);
	}
	
	/**
	 * 大于
	 *  e.g：Cretiron.gt("status",1)，生成SQL：status>1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion gt(String column,Object value){
		return new Criterion(column, MatchType.GT, value);
	}
	
	/**
	 * 小于等于
	 * e.g：Cretiron.le("status",1)，生成SQL：status<=1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion le(String column,Object value){
		return new Criterion(column, MatchType.LE, value);
	}
	
	/**
	 * 大于等于
	 * e.g：Cretiron.ge("status",1)，生成SQL：status>=1
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion ge(String column,Object value){
		return new Criterion(column, MatchType.GE, value);
	}
	
	/**
	 * 在...其中,支持List或者数组
	 * e.g：
	 * List<String> ids = Arrays.asList(new String[]{"1","2","3"});
	 * Cretiron.in("status",ids)，生成SQL：status IN ("1","2","3")
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion in(String column,Object value){
		return new Criterion(column, MatchType.IN, value);
	}
	
	/**
	 * 介于...中间
	 * e.g：Cretiron.in("status",1,5)，生成SQL：status BETWEEN 1 AND 5
	 * @param column
	 * @param value
	 * @return
	 */
	public static Criterion between(String column,Object value,Object secondValue){
		return new Criterion(column, MatchType.BETWEEN, value, secondValue);
	}
	
}