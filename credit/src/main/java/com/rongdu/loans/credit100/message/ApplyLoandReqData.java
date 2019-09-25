package com.rongdu.loans.credit100.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 当日多次申请-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ApplyLoandReqData implements Serializable{

	/**
	 * 身份证号码
	 */
	public String id;
	/**
	 * 身份证号码
	 */
	public List<String> cell = new ArrayList<>();
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 业务代码
	 */
	public String meal;
	/**
	 * 申请时间
	 */
	public String user_time;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getCell() {
		return cell;
	}

	public void setCell(List<String> cell) {
		this.cell = cell;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getUser_time() {
		return user_time;
	}

	public void setUser_time(String user_time) {
		this.user_time = user_time;
	}
}