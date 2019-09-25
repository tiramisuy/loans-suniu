package com.rongdu.loans.qhzx.vo;


import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.qhzx.common.QhzxConfig;

/**
 * 前海征信-好信欺诈度提示-请求参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxAntiFraudDooMSC8075 {

	/**
	 * 证件号码
	 */
	private String idNo = "";
	/**
	 * 证件类型：
	 * 0-身份证；
	 *  1-户口簿； 
	 *  2-护照； 
	 *  3-军官证； 
	 *  4-士兵证； 
	 *  5-港澳居民来往内地通行证；
	 *  6-台湾同胞来往内地通行证；
	 * 7-临时身份证； 
	 * 8-外国人居留证； 
	 * 9-警官证； 
	 * X-其他证件。
	 */
	private String idType = "0";
	/**
	 * 主体名称
	 */
	private String name = "";
	/**
	 * 查询原因
	 * 01--贷款审批
	 * 02--贷中管理
	 * 03--贷后管理
	 * 04--本人查询
	 * 05--异议查询
	 * 99--其他
	 */
	private String reasonNo  = "";
	//”是否核查不良信息“、“手机号码”和“IP地址”三者必填一项
	/**
	 * 是否核查不良信息
	 */
	private String chkBadInfo  = "";
	/**
	 * 手机号码
	 */
	private String mobileNo = "";
	/**
	 * IP地址
	 */
	private String ip  = "";
	/**
	 * 业务描述
	 */
	private String busiDesc = "";
	/**
	 * 信息主体授权码
	 */
	private String entityAuthCode = QhzxConfig.auth_code;
	/**
	 * 信息主体授权时间 yyyy-MM-dd
	 */
	private String entityAuthDate = DateUtils.getDate();
	/**
	 * 序列号 子批次号，本批次内唯一
	 */
	private String seqNo = "";

	public QhzxAntiFraudDooMSC8075() {

	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReasonNo() {
		return reasonNo;
	}

	public void setReasonNo(String reasonNo) {
		this.reasonNo = reasonNo;
	}

	public String getChkBadInfo() {
		return chkBadInfo;
	}

	public void setChkBadInfo(String chkBadInfo) {
		this.chkBadInfo = chkBadInfo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBusiDesc() {
		return busiDesc;
	}

	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}

	public String getEntityAuthCode() {
		return entityAuthCode;
	}

	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}

	public String getEntityAuthDate() {
		return entityAuthDate;
	}

	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

}
