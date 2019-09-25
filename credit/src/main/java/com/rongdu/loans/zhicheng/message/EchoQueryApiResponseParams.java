package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;
/**
 * 查询借款、风险和逾期信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class EchoQueryApiResponseParams implements Serializable{
	
	private static final long serialVersionUID = 8928681367126114658L;
	/**
	 * 交易代码
	 */
	private String tx;
	/**
	 * 应答数据
	 */
	private EchoQueryApiResponseData data;
	
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public EchoQueryApiResponseData getData() {
		return data;
	}
	public void setData(EchoQueryApiResponseData data) {
		this.data = data;
	}

}