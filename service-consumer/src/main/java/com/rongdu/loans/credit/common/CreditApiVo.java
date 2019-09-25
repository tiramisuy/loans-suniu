package com.rongdu.loans.credit.common;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

/**
 * 征信合作厂商的API接口的应答消息
 * @author sunda
 * @version 2017-07-10
 */
public abstract class CreditApiVo implements Serializable{
	
	/**
	 * 是否成功
	 */
	protected boolean success;
	/**
	 * 应答代码
	 */
	protected String code = ErrInfo.ERROR.getCode();
	/**
	 * 应答消息
	 */
	protected String msg = ErrInfo.ERROR.getMsg();

	
	public CreditApiVo(){
	}
	
	/**
	 * 每个厂商接口响应成功的标识都不一样，都子类重写
	 * @return
	 */
	public abstract boolean isSuccess();

	public abstract void setSuccess(boolean success);

	public abstract String getCode();

	public abstract void setCode(String code);

	public abstract String getMsg();

	public abstract void setMsg(String msg) ;
	
}
