package com.rongdu.loans.constant;

public interface LoginStatus {
	public static final String SUCCESS = "00000";
	public static final String FAILURE = "90001";
	public static final String ENABLED = "011";
	public static final String UNREGISTERED = "012";
	public static final String DISABLED = "013";
	public static final String NO_USER = "014";
	public static final String ERROR_PWD = "015";
	public static final String LOGOUT = "016";
	public static final String NOT_LOGIN = "017";
	public static final String NO_UDID = "018";
	
	public static final String FAILURE_MSG = "登录失败";
	public static final String SUCCESS_MSG = "登录成功";
	public static final String ENABLED_MSG = "设备有效";
	public static final String UNREGISTERED_MSG = "设备未注册";
	public static final String DISABLED_MSG = "设备已列入黑名单";
	public static final String NO_USER_MSG = "用户不存在";
	public static final String ERROR_PWD_MSG = "密码错误";
	public static final String LOGOUT_MSG = "已成功注销";
	public static final String NOT_LOGIN_MSG = "尚未授权";
	public static final String NO_UDID_MSG = "设备唯一标示不存在";
}
