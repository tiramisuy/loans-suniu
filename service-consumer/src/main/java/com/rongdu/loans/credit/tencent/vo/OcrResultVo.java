package com.rongdu.loans.credit.tencent.vo;

import java.util.Map;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 腾讯身份证OCR识别的结果
 * @author sunda
 * @version 2017-07-10
 */
public class OcrResultVo extends CreditApiVo{
	
	private static final long serialVersionUID = -6472859698806247755L;
	
	/**
	 * 交易时间
	 */
	private String transactionTime;
	/**
	 * 腾讯业务流水号
	 */
	private String bizSeqNo;
	/**
	 * 聚宝钱包业务流水号
	 */
	private String orderNo;
	/**
	 * “0”说明人像面识别成功
	 */
	private String frontCode;
	/**
	 * “0”说明国徽面识别成功
	 */
	private String backCode;
	/**
	 * frontCode 为 0 返回:证件姓名
	 */
	private String name;
	/**
	 * frontCode 为 0 返回:性别
	 */
	private String sex;
	/**
	 * frontCode 为 0 返回:民族
	 */
	private String nation;
	/**
	 * frontCode 为 0 返回:出生日期
	 */
	private String birth;
	/**
	 * frontCode 为 0 返回:地址
	 */
	private String address;
	/**
	 * frontCode 为 0 返回:身份证号
	 */
	private String idcard;
	/**
	 * backCode 为 0 返回:证件的有效期
	 */
	private String validDate;
	/**
	 *backCode 为 0 返回:发证机关
	 */
	private String authority;
	/**
	 *人像面照片，转换后为 JPG 格式
	 */
	private String frontPhoto;
	/**
	 * 国徽面照片，转换后为 JPG 格式
	 */
	private String backPhoto;
	/**
	 * 做 OCR 的操作时间
	 */
	private String operateTime;
	
	/**
	 * 映射Json中的result字段
	 */
	private Map<String, String> result;

	
	public OcrResultVo(){
	}

	public boolean isSuccess() {
		if ("0".equals(code)) {
			success = true;
		}
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFrontCode() {
		return frontCode;
	}

	public void setFrontCode(String frontCode) {
		this.frontCode = frontCode;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getFrontPhoto() {
		return frontPhoto;
	}

	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}

	public String getBackPhoto() {
		return backPhoto;
	}

	public void setBackPhoto(String backPhoto) {
		this.backPhoto = backPhoto;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}
	
}
