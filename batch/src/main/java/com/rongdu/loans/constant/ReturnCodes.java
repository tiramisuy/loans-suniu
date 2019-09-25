package com.rongdu.loans.constant;

import javax.servlet.http.HttpServletResponse;

public enum ReturnCodes {
	
	//用户登录模块代码
	login_success("0", "登录成功"),
	login_enabled(ModuleInfo.Login.code+"01", "终端设备有效"),
	login_unregistered(ModuleInfo.Login.code+"02", "终端设备未注册"),
	login_disabled(ModuleInfo.Login.code+"03", "终端设备注册未通过"),
	login_waite(ModuleInfo.Login.code+"04", "终端设备注册信息待审核，请耐心等候"),
	login_unknown_account("90001", "密码错误，请重新输入"),
	login_incorrect_credentials("90001", "用户名或密码错误，请重新输入"),
	login_no_org("021", "用户不存在，请重新输入"),
	login_auth_failure(ModuleInfo.Login.code+"08", "认证失败"),
	login_not_update(ModuleInfo.Login.code+"09","请升级后重新登录"),
	
	//注销登录模块代码
	logout_success("0", "已成功注销"),	
	
	//通用代码	
	server_exception("1", "服务器异常"),
	invalid_praram("2", "请求参数无效"),
	conn_loan_timeout("3", "连接消费信贷系统超时"),
	unknown_host_exception("4", "不能识别交易系统域名或IP，请检查配置"),
	connect_exception("5", "无法连接消费信贷系统，请检查网络配置"),
	connect_db_exception("5", "无法连接数据库，请检查网路配置"),
	socket_timeout_exception("6", "消费信贷系统Socket响应超时"),
	socket_exception("7", "消费信贷系统socket异常"),
	io_exception("8", "服务器文件读写异常"),
	ftp_io_exception("9", "无法连接并登录FTP服务器!"),
	file_not_found_exception("10", "没有找到文件"),
	unauthenticated(String.valueOf(HttpServletResponse.SC_FORBIDDEN), "会话超时或者尚未认证，请重新登录"),
	unauthorized(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "尚未授权，禁止访问"),
	success("0", "处理成功");
	
	public String code;
	public String msg;

	ReturnCodes(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
