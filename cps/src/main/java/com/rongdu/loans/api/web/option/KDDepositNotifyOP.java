package com.rongdu.loans.api.web.option;

import java.io.Serializable;
/**
 * 
* @Description:  异步回调 公共参数
* @author: RaoWenbiao
* @date 2018年11月28日
 */
public class KDDepositNotifyOP implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 状态码 
	 */
	private String retCode;
	/**
	 * 状态码解释
	 */
	private String retMsg;
	/**
	 * 当前调用的唯一码
	 */
	private String retTraceId;
	/**
	 * 返回数据 json
	 */
	private Object retData;
	/**
	 * 接口版本号
	 */
	private String version;
	/**
	 * 业务类型码
	 */
	private String txCode;

	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
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
	public Object getRetData() {
		return retData;
	}
	public void setRetData(Object retData) {
		this.retData = retData;
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
	
	
	
}
