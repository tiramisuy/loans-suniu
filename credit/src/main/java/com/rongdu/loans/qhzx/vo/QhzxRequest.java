package com.rongdu.loans.qhzx.vo;

/**
 * 前海征信-请求对象
 * 
 * @author sunda
 *
 * @version 2017-06-20
 */
public class QhzxRequest{
	
	/**
	 * 机构代码
	 */
	private QhzxRequestHeader header = new QhzxRequestHeader();;
	/**
	 * 业务请求数据
	 */
	private Object busiData = new QhzxRequestBusiData();
	/**
	 * 安全校验信息
	 */
	private QhzxRequestSecurityInfo securityInfo = new QhzxRequestSecurityInfo();
	
	public QhzxRequest() {
	}
	
	public QhzxRequestHeader getHeader() {
		return header;
	}
	public void setHeader(QhzxRequestHeader header) {
		this.header = header;
	}
	public Object getBusiData() {
		return busiData;
	}
	public void setBusiData(Object busiData) {
		this.busiData = busiData;
	}
	public QhzxRequestSecurityInfo getSecurityInfo() {
		return securityInfo;
	}
	public void setSecurityInfo(QhzxRequestSecurityInfo securityInfo) {
		this.securityInfo = securityInfo;
	}
	
}
