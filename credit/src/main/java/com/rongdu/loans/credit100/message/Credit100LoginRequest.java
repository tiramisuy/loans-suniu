package com.rongdu.loans.credit100.message;


/**
 * 百融金服-请求参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class Credit100LoginRequest {

	/**
	 * 登陆唯一标示
	 */
	private String tokenid = "";
	/**
	 * 此次请求的产品代号，如不配置将返回商户购买的所有产品。
	 */
	private String meal = "";
	/**
	 *身份证号
	 */
	private String id  = "";
	/**
	 *手机号（打包调用支持最多5个手机号，建议第一个填常用手机号）
	 */
	private String cell  = "";
	/**
	 *姓名
	 */
	private String name  = "";
	

	public Credit100LoginRequest() {

	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
