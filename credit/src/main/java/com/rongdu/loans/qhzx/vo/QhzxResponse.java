package com.rongdu.loans.qhzx.vo;

/**
 * 前海征信-请求对象
 * 
 * @author sunda
 *
 * @version 2017-06-20
 */
public class QhzxResponse {
	
	/**
	 * 机构代码
	 */
	private QhzxResponseHeader header;
	/**
	 * 业务请求数据
	 */
	private Object busiData;
	/**
	 * 安全校验信息
	 */
	private QhzxResponseSecurityInfo securityInfo;
	
	public QhzxResponseHeader getHeader() {
		return header;
	}
	public void setHeader(QhzxResponseHeader header) {
		this.header = header;
	}
	public Object getBusiData() {
		return busiData;
	}
	public void setBusiData(Object busiData) {
		this.busiData = busiData;
	}
	public QhzxResponseSecurityInfo getSecurityInfo() {
		return securityInfo;
	}
	public void setSecurityInfo(QhzxResponseSecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
	}
	
}
