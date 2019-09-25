package com.rongdu.loans.common;

import java.util.HashMap;

import com.rongdu.common.exception.ErrInfo;

/**
 * api应答结果:
 *  code-应答状态
 *  msg-应答消息
 *  data-应答消息内容
 * @author sunda
 *
 */
public class ApiResult extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 6059789383786071296L;
	
	private String code;
	private String msg;
	private Object data;
	
	public ApiResult(){
		this.code = ErrInfo.SUCCESS.getCode();
		this.msg = ErrInfo.SUCCESS.getMsg();
		super.put("code", this.code);
		super.put("msg", this.msg);
	}
	
	public ApiResult(ErrInfo e){
		this.code = e.getCode();
		this.msg = e.getMsg();
		super.put("code", this.code);
		super.put("msg", this.msg);
	}
	
	public ApiResult(String code,String msg){
		this.code = code;
		this.msg =msg;
		super.put("code", this.code);
		super.put("msg", this.msg);
	}
	
	public ApiResult(String code,String msg,Object data){
		this.code = code;
		this.msg =msg;
		this.data =data;
		super.put("code", this.code);
		super.put("msg", this.msg);
		super.put("data", this.data);
	}
	
	public ApiResult set(ErrInfo info){
		this.code = info.getCode();
		this.msg =info.getMsg();
		super.put("code", this.code);
		super.put("msg", this.msg);
		return this;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
		super.put("code", this.code);
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		super.put("msg", this.msg);
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
		super.put("data", this.data);
	}

}
