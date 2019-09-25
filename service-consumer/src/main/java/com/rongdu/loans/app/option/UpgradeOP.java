package com.rongdu.loans.app.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * app升级请求参数(示例)，服务端非业务逻辑参数通过hibernate-validator校验
 * 
 * @author sunda OPTION
 *
 */
public class UpgradeOP implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7289002844349557969L;

	/**
	 * 手机系统类型: [1]-ios; [2]-android
	 */
	@NotBlank(message="App类型为空")
	@Pattern(regexp="1|2|3|4|5|6|7|22",message="App类型有误")
	private String type;
	
	@NotBlank(message="当前版本号不能为空")
	private String appVerson;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAppVerson() {
		return appVerson;
	}
	public void setAppVerson(String appVerson) {
		this.appVerson = appVerson;
	}
	
}
