package com.rongdu.loans.linkface.vo;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 
* @Description:  商汤 人脸识别 数据
* 接口：/identity/selfie_idnumber_verification
* @author: 饶文彪
* @date 2018年7月2日 下午1:34:11
 */
public class IdnumberVerificationVO extends CreditApiVo implements Serializable {
	private static final long serialVersionUID = -1;

	private String request_id; // 本次请求的id。
	private String status; // 状态。正常为 OK，其他值表示失败。详见错误码
	private Float confidence; // 置信度。值为
								// 0~1，值越大表示两张照片属于同一个人的可能性越大。无法得到公安后台预留水印照时该值为
								// null
	private IdnumberVerificationIdentity identity; // 公安接口调用结果。
	private String reason;
	// private Object selfie; // 请求参数中使用file、url方式会返回图片的id

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getConfidence() {
		return confidence;
	}

	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}

	public IdnumberVerificationIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(IdnumberVerificationIdentity identity) {
		this.identity = identity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		success = status != null && status.equals("OK");
		return success;
	}

	@Override
	public void setSuccess(boolean success) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code = status;
	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMsg() {
		if(identity != null){
			msg = identity.getReason();
		}else if(null != reason){
			msg = reason;
		}
		return msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
	}

}
