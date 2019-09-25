package com.rongdu.loans.pay.common;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.StringUtils;

import java.util.HashMap;


public class RespInfo extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 1563774469990007868L;
	private String code;
	private String msg;
	
	public RespInfo(){
		super.put("code", ErrInfo.SUCCESS.getCode());
		super.put("msg", ErrInfo.SUCCESS.getMsg());
	}
	
	public RespInfo(ErrInfo e){
		super.put("code", e.getCode());
		super.put("msg", e.getMsg());
	}
	
	public RespInfo(String code,String msg){
		super.put("code", code);
		super.put("msg", msg);
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			this.code = code;
			super.put("code", code);		
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		super.put("msg", msg);
	}
}
