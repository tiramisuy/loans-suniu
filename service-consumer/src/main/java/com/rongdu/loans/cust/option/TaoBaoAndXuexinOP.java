package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class TaoBaoAndXuexinOP implements Serializable {

	/**
     * 序列号
     */
	private static final long serialVersionUID = -7763214644270837040L;
    
    /**
     * 认证成功
     */
    public static final String DO_OPERATOR_SUCC = "1";
    /**
     * 认证失败
     */
    public static final String DO_OPERATOR_FAIL = "2";
    
    /**
	 * 进件来源（1-ios, 2-android, 3-h5, 4-api）
	 */ 
	@Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String source;
	
	/**
	 * 认证结果（1-成功, 2-失败）
	 */ 
	@NotBlank(message="认证结果为空")
	@Pattern(regexp="1|2",message="认证结果类型有误")
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
	 * 淘宝账号
	 */
	private String alipayId;
	
	/**
	 * 学信网账号
	 */
	private String xuexinId;
	
	public String getSource() {
		return source;
	}
	public String getUserId() {
		return userId;
	}
	public String getApplyId() {
		return applyId;
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
	public String getDoOperatorResult() {
		return doOperatorResult;
	}
	public void setDoOperatorResult(String doOperatorResult) {
		this.doOperatorResult = doOperatorResult;
	}
	public String getXuexinId() {
		return xuexinId;
	}
	public void setXuexinId(String xuexinId) {
		this.xuexinId = xuexinId;
	}
	public String getAlipayId() {
		return alipayId;
	}
	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}
	
}
