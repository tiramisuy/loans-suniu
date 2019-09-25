package com.rongdu.loans.linkface.vo;

import java.io.Serializable;

/**
 * 
* @Description:  商汤 人脸识别 Identity数据
* @author: 饶文彪
* @date 2018年7月2日 下午1:34:11
 */
public class IdnumberVerificationIdentity implements Serializable {
	private static final long serialVersionUID = -1;

	private Boolean validity; // 身份证和姓名经过公安接口验证是否匹配。匹配为 true，不匹配为 false
	private String photo_id; // 公安后台预留照片的 ID。公安后台无该身份信息对应的照片时该值为 null
	private String reason; // 公安接口出错的原因。正常为Gongan status OK 。其他错误类型参考reason字段表

	public Boolean getValidity() {
		return validity;
	}

	public void setValidity(Boolean validity) {
		this.validity = validity;
	}

	public String getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(String photo_id) {
		this.photo_id = photo_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
