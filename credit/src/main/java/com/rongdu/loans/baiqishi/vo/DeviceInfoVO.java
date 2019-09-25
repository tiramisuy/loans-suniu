package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.credit.common.CreditApiVo;

import java.util.Map;

/**
 * 查询设备信息-应答报文
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class DeviceInfoVO  extends CreditApiVo{

	private static final long serialVersionUID = -6909977101992364369L;
	/**
	 * 调用结果码，BQS000为成功，其他为失败
	 */
	private String resultCode;
	/**
	 * 应答消息
	 */
	private String resultDesc;
	/**
	 * 设备信息
	 */
	private Map<String, Object> resultData;
	
	@Override
	public boolean isSuccess() {
		if ("BQS000".equals(getResultCode())) {
			return true;
		}
		return false;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return getResultCode();
	}

	@Override
	public void setCode(String code) {
		
	}

	@Override
	public String getMsg() {
		return getResultDesc();
	}

	@Override
	public void setMsg(String msg) {
		
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Map<String, Object> getResultData() {
		return resultData;
	}

	public void setResultData(Map<String, Object> resultData) {
		this.resultData = resultData;
	}

}