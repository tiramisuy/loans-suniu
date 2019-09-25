package com.rongdu.loans.credit100.common;

/**
 * 百融金服-API-响应代码
 */
public enum ApiRespCode {
	
	SUCCESS("00","操作成功","用户请求的产品中至少有一个匹配成功，flag中至少有一个为1"),
	C_100001("100001","程序错误","明确失败，联系客服"),
	C_100002("100002","匹配结果为空","查询key值未命中数据库"),
	C_100003("100003","必选key值缺失或不合法","请补充key值（id/cell/name）,或检查必传key值是否合法"),
	C_100004("100004","商户不存在或用户名错误","请联系客服查询正确用户名"),
	C_100005("100005","登陆密码不正确	","请联系客服找回密码"),
	C_100006("100006","请求参数格式错误","明确失败，联系客服"),
	C_100007("100007","Tokenid过期","请重新登录"),
	C_100008("100008","客户端api调用码不能为空","	Apicode 错误"),
	C_100009("100009","IP地址错误","请联系客服添加IP白名单"),
	C_100010("100010","超出当天访问次数","测试客户查询限制为50条/天"),
	C_100011("100011","账户停用","明确失败"),
	C_100012("100012","请求套餐为空","取消传输自定义套餐或确认非空值重新传"),
	C_1000015("1000015","请求参数其他错误","联系客服，反馈出错场景"),
	C_1000016("1000016","捕获请求json异常，无法解析的错误","修改请求json或联系客服");
	
	//响应代码
	private String code;
	//响应消息
	private String msg;
	//处理方式
	private String solution;
	
	private ApiRespCode(String code, String msg, String solution) {
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
	
}