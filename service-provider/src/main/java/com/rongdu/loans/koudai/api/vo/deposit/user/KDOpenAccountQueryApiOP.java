package com.rongdu.loans.koudai.api.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountQueryApiOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String version = "2.0";// 版本号 目前2.0
	private String txCode = "queryAccountOpenDetail"; // 业务类型码
														// accountOpenEncryptPage
	private String platNo = "aKiQLTvB";// 平台标识 例aKiQLTvB,由口袋资产系统颁发
	private String txTime = String.valueOf(System.currentTimeMillis()); // 发起请求时间戳
																		// 例:1511409091
	private String pack;// 业务参数 业务数据密文，具体字段如下

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

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

}
