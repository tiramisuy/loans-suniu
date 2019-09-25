package com.rongdu.loans.constant;

public enum ModuleInfo {
	
	Login("01","Login","用户登录"),
	Logout("02","Logout","注销登录");
	
	public String code;
	public String name;
	public String displayName;

	ModuleInfo(String code, String name,String displayName) {
		this.code = code;
		this.name = name;
		this.displayName =displayName;
	}
	
}
