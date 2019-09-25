package com.rongdu.loans.credit100.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 特殊名单核查-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class SpecialListcReqData implements Serializable{

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
	 * 策略代码
	 */
	public String strategy_id;
	/**
	 * 紧急联系人
	 */
	public List<String> linkman_cell;


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

	public List<String> getLinkman_cell() {
		return linkman_cell;
	}

	public void setLinkman_cell(List<String> linkman_cell) {
		this.linkman_cell = linkman_cell;
	}

	public String getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(String strategy_id) {
		this.strategy_id = strategy_id;
	}
}