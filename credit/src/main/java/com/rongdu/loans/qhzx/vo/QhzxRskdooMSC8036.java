package com.rongdu.loans.qhzx.vo;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.qhzx.common.QhzxConfig;

/**
 * 前海征信-好信风险度提示（专业版）-请求参数
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxRskdooMSC8036 {

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
	 * IP集
	 * 多个IP以逗号分开
	 */
	private String ips = "";
	/**
	 * 卡号集
	 * 多个卡号以逗号分开
	 */
	private String cardNos = "";
	/**
	 * 手机号码集
	 * 多个手机号码以逗号分开
	 */
	private String moblieNos = "";
	/**
	 * 查询原因
	 * 01--贷款审批
	 * 02--贷中管理
	 * 03—贷后管理
	 * 04--本人查询
	 * 05--异议查询
	 * 99--其他
	 */
	private String reasonCode = "";
	/**
	 * 主体名称
	 */
	private String name = "";
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

	public QhzxRskdooMSC8036() {
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

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String getCardNos() {
		return cardNos;
	}

	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
	}

	public String getMoblieNos() {
		return moblieNos;
	}

	public void setMoblieNos(String moblieNos) {
		this.moblieNos = moblieNos;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
