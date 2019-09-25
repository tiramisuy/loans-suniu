package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

public class KDDepositResultVO implements Serializable {
	protected static final long serialVersionUID = 1L;
	
	/**
	 * 状态码 0成功，其他失败

	 */
	protected Integer retCode;
	/**
	 * 状态码解释
	 */
	protected String retMsg;
	/**
	 * 当前调用的唯一码
	 */
	protected String retTraceId;
	
	/**
	 * 接口版本号
	 */
	protected String version;
	/**
	 * 业务类型码
	 */
	protected String txCode;
	
	/**
	 * 返回数据
	 */
	protected Object retData;
	
	public Integer getRetCode() {
		return retCode;
	}
	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getRetTraceId() {
		return retTraceId;
	}
	public void setRetTraceId(String retTraceId) {
		this.retTraceId = retTraceId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public Object getRetData() {
		return retData;
	}
	public void setRetData(Object retData) {
		this.retData = retData;
	}
	
	
}
