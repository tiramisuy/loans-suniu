package com.rongdu.loans.sys.web;

import java.io.Serializable;

import com.rongdu.loans.api.vo.FileVO;

public class FileResult implements Serializable{

	/**
	 * 序列号    
	 */
	private static final long serialVersionUID = 6009740966313867191L;

	private String code;
	private String msg;
	private FileVO data;
	
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public FileVO getData() {
		return data;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setData(FileVO data) {
		this.data = data;
	}
}
