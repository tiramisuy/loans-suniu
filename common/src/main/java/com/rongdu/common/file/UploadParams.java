package com.rongdu.common.file;

import lombok.Data;

/**
 * 影像资料服务器上传参数
 * @author sunda
 * @version 2017-07-12
 */
@Data
public class UploadParams {
	
	/**
	 * 用户ID
	 * 必填
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 * 选填
	 */
	private String applyId;
	/**
	 * 业务代码
	 * 必填
	 * com.rongdu.common.file.FileBizCode
	 */
	private String bizCode;	
	/**
	 * 文件原名
	 * 必填
	 */
	private String origName;
	
	/**
	 * 用户端的IP地址
	 * 必填
	 */
	private String ip;	
	/**
	 * 上传文件来源于哪个终端 来源于哪个终端（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	 * 必填
	 */
	private String source;

	private String remark;

}
