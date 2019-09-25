package com.rongdu.loans.credit100.common;

/**
 * 百融金服-用户登录-响应代码
 */
public enum LoginRespCode {
	
	SUCCESS("00","登录成功","用户登录成功"),
	C_100001("100001","程序错误","明确失败，联系客服"),
	C_100004("100004","商户不存在或用户名错误","请联系客服查询正确用户名"),
	C_100005("100005","登陆密码不正确","请联系客服找回密码"),
	C_100006("100006","请求参数格式错误","明确失败，联系客服"),
	C_100011("100011","账户停用","明确失败，联系客服");	
	
	//响应代码
	private String code;
	//响应消息
	private String msg;
	//处理方式
	private String solution;
	
	
	private LoginRespCode(String code, String msg, String solution) {
		this.code = code;
		this.msg = msg;
		this.solution = solution;
	}	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
