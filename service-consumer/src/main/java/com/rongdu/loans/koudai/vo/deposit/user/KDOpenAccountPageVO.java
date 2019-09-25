package com.rongdu.loans.koudai.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountPageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String retCode;// 状态码 integer 0成功，其他失败
	private String retMsg;// 状态码解释 string received,rejected
	private String retTraceId;// 当前调用的唯一码 string 例:aIjUeLwKMBgfRQcgTaS
	private String version;// 接口版本号 string 目前是2.0
	private String txCode;// 业务类型码 string queryAccountOpenDetail
	private KDOpenAccountPageDataVO retData;// 返回数据 json

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

	public KDOpenAccountPageDataVO getRetData() {
		return retData;
	}

	public void setRetData(KDOpenAccountPageDataVO retData) {
		this.retData = retData;
	}

}
