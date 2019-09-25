package com.rongdu.loans.entity;

import javax.servlet.http.HttpServletResponse;

public enum ResponseCodes {
	
	//用户登录模块代码
	login_success(0, "登录成功"),
	login_enabled(1001, "终端设备有效"),
	login_unregistered(1002, "终端设备未注册"),
	login_disabled(1003, "禁用该用户登录"),
	login_unknown_account(1004, "用户不存在"),
	login_incorrect_credentials(1005, "密码不正确"),
	login_no_org(1006, "机构不存在"),
	login_auth_failure(1007, "认证失败"),
	login_repeat_user(1008, "该登录用户重复，拒绝登录"),
	verify_error(1009, "验证码错误"),
	
	//注销登录模块代码
	logout_success(0, "已成功注销"),	
	
	//通用代码	
	server_exception(1, "服务器异常"),
	invalid_praram(2, "请求参数无效"),
	conn_loan_timeout(3, "连接消费信贷系统超时"),
	unknown_host_exception(4, "不能识别交易系统域名或IP，请检查配置"),
	connect_exception(5, "无法连接交易系统，请联系系统管理员"),
	socket_timeout_exception(6, "交易系统Socket响应超时"),
	socket_exception(7, "交易系统socket异常"),
	io_exception(8, "服务器文件读写异常"),
	ftp_exception(9, "无法传输文件到FTP服务器!"),
	file_not_found_exception(10, "没有找到文件"),
	request_type_not_matched(11, "请求类型不正确"),
	unauthenticated(HttpServletResponse.SC_FORBIDDEN, "会话超时或者尚未认证，请重新登录"),
	unauthorized(HttpServletResponse.SC_UNAUTHORIZED, "尚未授权，禁止访问"),
	bind_data_exception(11, "创建对象失败，无法绑定请求参数"),
	db_exception(12, "数据库连接异常"),
	empty_message(13, "外围系统返回报文为空"),
	jaxb_exception(14, "XML映射异常"),
	suffix_not_exists(15,"要下载的文件后缀不存在"),
	login_type_not_exists_exception(16,"该登录类型不存在"),

	
	c_3315(3115, "该活动已经结束，不能参与！"),
	
	success(0, "处理成功");
	
	public int code;
	public String msg;

	ResponseCodes(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
