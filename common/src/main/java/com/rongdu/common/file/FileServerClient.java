package com.rongdu.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.*;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 影像资料服务器-客户端
 * 
 * @author sunda
 * @version 2017-06-27
 */
public class FileServerClient {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(FileServerClient.class);
	/**
	 * 图片上传地址
	 */
	private static String UPLOAD_IMAGE_URL = Global.getConfig("file.upload.image.url");

	/**
	 * 视频上传地址
	 */
	private static String UPLOAD_VIDEO_URL = Global.getConfig("file.upload.video.url");

	/**
	 * 文档上传地址
	 */
	private static String UPLOAD_DOCUMENT_URL = Global.getConfig("file.upload.document.url");

	private RestTemplate restTemplate = null;

	public FileServerClient() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * 向文件服务器提交图片文件
	 * 
	 * @param file
	 * @param params
	 * @return
	 */
	public String uploadImage(File file, UploadParams params) {
		FileSystemResource resource = new FileSystemResource(file);
		MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
		// 待上传的文件
		multiMap.add("file", resource);
		// 文件类型
		multiMap.add("fileType", FileType.IMG.getType());
		// 将文件上传参数封装到MultiValueMap
		multiMap = toMultiValueMap(params, multiMap);
		// 响应结果
		String respString = null;
		try {
			logger.debug("影像资料服务器客户端-正在上传图片：{}，{}", params.getBizCode(),
					file.getAbsolutePath());
			respString = restTemplate.postForObject(UPLOAD_IMAGE_URL, multiMap,
					String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("影像资料服务器客户端-响应内容：{}", respString);
		return respString;
	}

	/**
	 * 将文件上传参数封装到MultiValueMap
	 * 
	 * @param params
	 * @param multiMap
	 * @return
	 */
	private MultiValueMap<String, Object> toMultiValueMap(UploadParams params,
			MultiValueMap<String, Object> multiMap) {
		if (StringUtils.isNotBlank(params.getOrigName())) {
			// 可能含有中文，对其进行URL转码
			params.setOrigName(Encodes.urlEncode(params.getOrigName()));
		}
		multiMap.set("userId", params.getUserId());
		multiMap.set("applyId", params.getApplyId());
		multiMap.set("bizCode", params.getBizCode());
		multiMap.set("origName", params.getOrigName());
		multiMap.set("ip", params.getIp());
		multiMap.set("source", params.getSource());
		multiMap.set("remark", params.getRemark());
		return multiMap;
	}

	/**
	 * 向文件服务器提交Base64格式的图片
	 * 
	 * @param base64Image
	 * @param params
	 * @return
	 */
	public String uploadBase64Image(String base64Image, UploadParams params) {
		logger.debug("影像资料服务器客户端-正在上传base64图片：{}，{}", params.getBizCode(),
				params.getIp());
		String tmpdir = System.getProperty("java.io.tmpdir");
		String filePath = new StringBuilder().append(tmpdir).append("/")
				.append(IdGen.uuid()).append(".jpg").toString();
		File file = new File(filePath);
		String respString = null;
		OutputStream output = null;
		try {
			String origName = file.getName();
			params.setOrigName(origName);
			output = new FileOutputStream(file);
			byte[] bytes = Encodes.decodeBase64(base64Image);
			IOUtils.write(bytes, output);
			respString = uploadImage(file, params);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			FileUtils.deleteFile(file.getAbsolutePath());
		}
		return respString;
	}

	/**
	 * 向文件服务器提交Base64格式的图片
	 * 
	 * @param base64Video
	 * @param params
	 * @return
	 */
	public String uploadBase64Video(String base64Video, UploadParams params) {
		logger.debug("影像资料服务器客户端-正在上传base64视频：{}，{}", params.getBizCode(),
				params.getIp());
		String tmpdir = System.getProperty("java.io.tmpdir");
		String filePath = new StringBuilder().append(tmpdir).append("/")
				.append(IdGen.uuid()).append(".mp4").toString();
		File file = new File(filePath);
		String respString = null;
		OutputStream output = null;
		try {
			String origName = file.getName();
			params.setOrigName(origName);
			output = new FileOutputStream(file);
			byte[] bytes = Encodes.decodeBase64(base64Video);
			IOUtils.write(bytes, output);
			respString = uploadVideo(file, params);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			FileUtils.deleteFile(file.getAbsolutePath());
		}
		return respString;
	}

	/**
	 * 向文件服务器上传视频
	 * 
	 * @param file
	 * @param params
	 * @return
	 */
	public String uploadVideo(File file, UploadParams params) {
		FileSystemResource resource = new FileSystemResource(file);
		MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
		// 待上传的文件
		multiMap.add("file", resource);
		// 文件类型
		multiMap.add("fileType", FileType.VIDEO.getType());
		// 将文件上传参数封装到MultiValueMap
		multiMap = toMultiValueMap(params, multiMap);
		// 响应结果
		String respString = null;
		try {
			logger.debug("影像资料服务器客户端-正在上传视频：{}，{}", params.getBizCode(),
					file.getAbsolutePath());
			respString = restTemplate.postForObject(UPLOAD_VIDEO_URL, multiMap,
					String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("影像资料服务器客户端-响应内容：{}", respString);
		return respString;
	}

	/**
	 * 向文件服务器上传视频
	 * 
	 * @param file
	 * @param params
	 * @return
	 */
	public String uploadDocument(File file, UploadParams params) {
		FileSystemResource resource = new FileSystemResource(file);
		MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
		// 待上传的文件
		multiMap.add("file", resource);
		// 文件类型
		multiMap.add("fileType", FileType.DOC.getType());
		// 将文件上传参数封装到MultiValueMap
		multiMap = toMultiValueMap(params, multiMap);
		// 响应结果
		String respString = null;
		try {
			logger.debug("影像资料服务器客户端-正在上传文档：{}，{}", params.getBizCode(),
					file.getAbsolutePath());
			respString = restTemplate.postForObject(UPLOAD_DOCUMENT_URL,
					multiMap, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("影像资料服务器客户端-响应内容：{}", respString);
		return respString;
	}

	/**
	 * 向文件服务器上传文档
	 * 
	 * @param fileBodyText
	 * @param fileExt
	 * @param params
	 * @return
	 */
	public String uploadDocumentString(String fileBodyText, String fileExt,
			UploadParams params) {
		logger.debug("影像资料服务器客户端-正在上传文档：{}，{}", params.getBizCode(),
				params.getIp());
		String tmpdir = System.getProperty("java.io.tmpdir");
		String filePath = new StringBuilder().append(tmpdir).append("/")
				.append(IdGen.uuid()).append(".").append(fileExt).toString();
		File file = new File(filePath);
		String respString = null;
		OutputStream output = null;
		try {
			String origName = file.getName();
			params.setOrigName(origName);
			output = new FileOutputStream(file);
			byte[] bytes = fileBodyText.getBytes(CharsetUtils.DEFAULT_CHARSET);
			IOUtils.write(bytes, output);
			respString = uploadDocument(file, params);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			FileUtils.deleteFile(file.getAbsolutePath());
		}
		return respString;
	}

	public static void main(String[] args) throws Exception {
		// 公共参数
		UploadParams params = new UploadParams();
		String userId = "123456";
		String applyId = "1111111";
		// 注意获取用户端的IP地址
		String clientIp = "127.0.0.1";
		// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
		String source = "1";
		params.setUserId(userId);
		params.setApplyId(applyId);
		params.setIp(clientIp);
		params.setSource(source);

		// 上传图片文件示例
		// String filePath = "C:/Users/MARK/Desktop/股东信息.jpg";
		// File file = new File(filePath);
		// //注意获取用户端上传源文件的文件名
		// String origName = file.getName();
		// params.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		// params.setOrigName(origName);
		// FileServerClient client = new FileServerClient();
		// String respString = client.uploadImage(file,params);

		// 上传视频文件示例
		// String filePath = "D:/temp/测试.mp4";
		// File file = new File(filePath);
		// //注意获取用户端上传源文件的文件名
		// String origName = file.getName();
		// params.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		// params.setOrigName(origName);
		// FileServerClient client = new FileServerClient();
		// String respString = client.uploadVideo(file,params);

		// 上传base64图片示例
		// String filePath = "C:/Users/MARK/Desktop/股东信息.jpg";
		// File file = new File(filePath);
		// params.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		// InputStream input = new FileInputStream(file);
		// byte[] bytes = IOUtils.toByteArray(input);
		// String base64Image = Encodes.encodeBase64(bytes);
		// FileServerClient client = new FileServerClient();
		// String respString = client.uploadBase64Image(base64Image,params);

		// 上传base视频示例
		// String filePath = "D:/temp/测试.mp4";
		// File file = new File(filePath);
		// InputStream input = new FileInputStream(file);
		// params.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		// byte[] bytes = IOUtils.toByteArray(input);
		// String base64Video = Encodes.encodeBase64(bytes);
		// FileServerClient client = new FileServerClient();
		// String respString = client.uploadBase64Video(base64Video,params);

	}

}
