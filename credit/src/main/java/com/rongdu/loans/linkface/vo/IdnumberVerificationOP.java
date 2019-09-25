package com.rongdu.loans.linkface.vo;

import java.io.File;
import java.io.Serializable;

/**
 * 
* @Description:  商汤 人脸识别 接口参数
* @author: 饶文彪
* @date 2018年7月2日 下午1:36:18
 */
public class IdnumberVerificationOP implements Serializable {
	private static final long serialVersionUID = -1;

	// api_id private String 是 API 账户。
	// api_secret private String 是 API 密钥。

	private String userId;
	private String applyId;

	private String id_number; // 身份证号。用以查询公安部后台预留照片
	private String name; // 与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
	// private File selfie_file; // 见下方注释 需上传的图片文件。上传本地图片可选取此参数
	private String selfie_url; // 见下方注释 图片网络地址。采用抓取网络图片方式可选取此参数
	// private String selfie_image_id; // 见下方注释 图片的id。在云端上传过的图片可选取此参数
	boolean selfie_auto_rotate; // 开启图片自动旋转功能。开通：true，不开通：false。默认不开通

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelfie_url() {
		return selfie_url;
	}

	public void setSelfie_url(String selfie_url) {
		this.selfie_url = selfie_url;
	}

	public boolean isSelfie_auto_rotate() {
		return selfie_auto_rotate;
	}

	public void setSelfie_auto_rotate(boolean selfie_auto_rotate) {
		this.selfie_auto_rotate = selfie_auto_rotate;
	}

}
