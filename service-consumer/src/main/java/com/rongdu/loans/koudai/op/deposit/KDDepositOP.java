package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;

public class KDDepositOP implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//版本号
	private String version;
	//业务类型码
	private String txCode;
	//平台标识
	private String platNo;
	//发起请求时间戳	
	private Integer txTime;
	//业务参数
	private String pack;
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
	public String getPlatNo() {
		return platNo;
	}
	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}
	public Integer getTxTime() {
		return txTime;
	}
	public void setTxTime(Integer txTime) {
		this.txTime = txTime;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	
	
}
