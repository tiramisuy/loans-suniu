package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class EchoQueryApiRequest implements Serializable{

	private static final long serialVersionUID = 7209682953717889561L;
	/**
	 * 业务类型编号，此处取"101"
	 */
	private String tx = "101";
	/**
	 * 具体请求参数
	 */
	private CreditInfoRequestParam data;
	
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public CreditInfoRequestParam getData() {
		return data;
	}
	public void setData(CreditInfoRequestParam data) {
		this.data = data;
	}
	
	
}