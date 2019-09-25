package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class FaceVerifyVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 436711400611431178L;
	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 调用结果
	 */
	private Integer doResult;
	/**
	 * 人脸识别照片URL
	 */
	private String imageUrl;
	/**
	 * 人脸识别视频URL
	 */
	private String videoUrl;
	
	public String getApplyId() {
		return applyId;
	}
	public Integer getDoResult() {
		return doResult;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setDoResult(Integer doResult) {
		this.doResult = doResult;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
