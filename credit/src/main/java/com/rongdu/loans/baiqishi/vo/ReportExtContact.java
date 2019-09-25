package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 白骑士-给资信云上报额外参数-紧急联系人-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ReportExtContact implements Serializable {

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 *关系：
	 1  父母
	 2  子女
	 3  夫妻
	 4  恋人
	 5  同事
	 6  同学
	 7  朋友
	 8  其他
	 */
	private String relation;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

}
