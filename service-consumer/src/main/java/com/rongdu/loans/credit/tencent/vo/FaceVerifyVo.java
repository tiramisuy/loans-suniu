package com.rongdu.loans.credit.tencent.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 腾讯人脸验证的结果
 * @author sunda
 * @version 2017-07-10
 */
public class FaceVerifyVo extends CreditApiVo{
	
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
	 * 聚宝钱包业务订单号
	 */
	private String orderNo;
	/**
	 * 腾讯分配的AppId
	 */
	@JsonProperty("app_id")
	private String appId;
	/**
	 * 证件类型
	 */
	private String idType;
	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 刷脸时的照片，base64 位编码
	 */
	private String photo;
	/**
	 * 刷脸时的视频，base64 编码
	 */
	private String video;
	/**
	 * 映射Json中的result字段
	 */
	private Map<String, String> result;
	
	public FaceVerifyVo(){
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
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
