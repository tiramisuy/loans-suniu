package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 查询芝麻信用授权结果-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResultOP implements Serializable{

	private static final long serialVersionUID = -6946683934140372269L;
	
	/**
	 *  用户ID
	 */
	private  String	userId;
	/**
	 *  applyId
	 */
	private  String	applyId;
	/**
	 * 用户端的IP地址
	 * 必填
	 */
	private String ip;	
	/**
	 * 上传文件来源于哪个终端 来源于哪个终端（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	 * 必填
	 */
	 @NotBlank(message="进件来源不能为空")
	 @Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
	//必须传递openId或者（idNo，name）其中之一
	/**
	 *  身份证号
	 */
	private  String	idNo;
	/**
	 *  姓名
	 */
	private  String	name;
	/**
	 *  芝麻信用openId
	 */
	private  String	openId;
	
	public AuthorizeResultOP(){
	}
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
}
