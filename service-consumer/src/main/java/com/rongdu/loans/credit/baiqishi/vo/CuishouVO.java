package com.rongdu.loans.credit.baiqishi.vo;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 上传查询催收指标用户-应答报文
 * @author liuzhuang
 * @version 2017-12-28
 */
public class CuishouVO  extends CreditApiVo{

	private static final long serialVersionUID = -6909977101992364369L;

	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
