package com.rongdu.loans.credit100.vo;

import com.rongdu.loans.credit100.common.ApiRespCode;
import com.rongdu.loans.credit100.common.RespCodeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * 特殊名单核查-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class SpecialListcVO extends HashMap<String,String>{

	/**
	 * 应答代码
	 */
	private String code;
	/**
	 * 应答消息
	 */
	private String msg;
	/**
	 * 特殊名单核查产品输出标识
	 * 1(输出成功),0(未匹配上无输出),98(用户输入信息不足),99(系统异常)
	 */
	private String flag_specialList_c;
	/**
	 * 百融交易订单号
	 */
	private String swift_number;


	public boolean isSuccess() {
		return ApiRespCode.SUCCESS.getCode().equals(getCode());
	}

	public String getCode() {
		if(StringUtils.isBlank(code)){
			code = get("code");
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return RespCodeUtils.getApiMsg(code);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFlag_specialList_c() {
		if(StringUtils.isBlank(flag_specialList_c)){
			code = get("flag_specialList_c");
		}
		return flag_specialList_c;
	}

	public void setFlag_specialList_c(String flag_specialList_c) {
		this.flag_specialList_c = flag_specialList_c;
	}

	public String getSwift_number() {
		return swift_number;
	}

	public void setSwift_number(String swift_number) {
		this.swift_number = swift_number;
	}

}
