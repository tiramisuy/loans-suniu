package com.rongdu.loans.qhzx.vo;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.qhzx.common.QhzxConfig;

/**
 * 前海征信-请求对象-请求头
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxRequestHeader {
	
	/**
	 * 机构代码
	 */
	private String orgCode = QhzxConfig.org_code;
	/**
	 * 渠道、系统ID
	 */
	private String chnlId = QhzxConfig.chnl_id;
	/**
	 * 交易流水号
	 * 该流水号只能使用一次，同一机构必须确保唯一
	 */
	private String transNo = "JQB"+System.nanoTime();
	/**
	 * 交易时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String transDate = DateUtils.getDateTime();
	/**
	 * 授权代码
	 */
	private String authCode = QhzxConfig.auth_code;
	/**
	 *授权时间
	 */
	private String authDate = DateUtils.getDate();
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getChnlId() {
		return chnlId;
	}
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	
}
