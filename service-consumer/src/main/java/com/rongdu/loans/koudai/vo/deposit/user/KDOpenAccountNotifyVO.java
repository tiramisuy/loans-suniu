package com.rongdu.loans.koudai.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountNotifyVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String retCode;// 状态码 integer 0成功，其他失败
	private String retMsg;// 状态码解释 string received,rejected
	private String txCode;// 业务类型码 string queryAccountOpenDetail
	private KDOpenAccountNotifyDataVO retData;// 返回数据 json

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

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public KDOpenAccountNotifyDataVO getRetData() {
		return retData;
	}

	public void setRetData(KDOpenAccountNotifyDataVO retData) {
		this.retData = retData;
	}

}
