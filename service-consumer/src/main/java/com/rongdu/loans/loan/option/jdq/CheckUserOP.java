package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**  
* @Title: CheckUserOP.java  
* @Package com.rongdu.loans.loan.option.jdq
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月19日  
* @version V1.0  
*/
@Data
public class CheckUserOP implements Serializable{

	private static final long serialVersionUID = 6287472127811903988L;
	
	/**
	 * 通过身份证号（遇到字母转为大写）MD5加密后的字符串，可选
	 */
	@JsonProperty("id_number_md5")
	private String idNumberMd5;
	
	/**
	 * 用户身份证号（遇到字母转为大写），后五位掩码，如3408811979010*****
	 */
	@JsonProperty("id_number")
	private String idNumber;
	
	/**
	 * 用户手机号，后4位掩码，如1391234****
	 */
	private String phone;
	
	/**
	 * 用户姓名
	 */
	@JsonProperty("user_name")
	private String userName;

}
