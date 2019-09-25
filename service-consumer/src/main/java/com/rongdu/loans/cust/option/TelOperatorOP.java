package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author zcb
 * @date 2018年11月29日
 */
public class TelOperatorOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -2655773952417701372L;
    
    /**
     * 调运营商认证成功
     */
    public static final String DO_OPERATOR_SUCC = "1";
    /**
     * 调运营商认证失败
     */
    public static final String DO_OPERATOR_FAIL = "1";
    
    /**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@NotBlank(message="消息来源不能为空")
	@Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
	
	/**
	 * 运营商认证结果（1-成功, 2-失败）
	 */ 
	@NotBlank(message="运营商认证结果为空")
	@Pattern(regexp="1|2",message="运营商认证结果类型有误")
	private String doOperatorResult;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 运营商认证方式 1：白骑士 2：天机
	 */
	private Integer operatorType;
	
	public String getSource() {
		return source;
	}
	public String getUserId() {
		return userId;
	}
	public String getApplyId() {
		return applyId;
	}
	public String getIp() {
		return ip;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDoOperatorResult() {
		return doOperatorResult;
	}
	public void setDoOperatorResult(String doOperatorResult) {
		this.doOperatorResult = doOperatorResult;
	}
}
