package com.rongdu.loans.test;

import java.io.File;

import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;

public class FileServerClientTest {

	public static void main(String[] args) throws Exception {
		//公共参数
		UploadParams  params = new UploadParams();
		String userId = "123456";
		String applyId = "1111111";
		//注意获取用户端的IP地址
		String clientIp = "127.0.0.1";
		//上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
		String source = "1";
		params.setUserId(userId);
		params.setApplyId(applyId);
		params.setIp(clientIp);
		params.setSource(source);	
		
//		//上传图片文件示例
		String filePath = "D:/测试图片.png";
		File file = new File(filePath);
		//注意获取用户端上传源文件的文件名
		String origName = file.getName();
		params.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		params.setOrigName(origName);
		FileServerClient client = new FileServerClient();
		String respString = client.uploadImage(file,params);
		
		//上传视频文件示例
//		String filePath = "D:/temp/测试.mp4";
//		File file = new File(filePath);
//		//注意获取用户端上传源文件的文件名
//		String origName = file.getName();
//		params.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
//		params.setOrigName(origName);
//		FileServerClient client = new FileServerClient();
//		String respString = client.uploadVideo(file,params);
		
		//上传base64图片示例	
//		String filePath = "C:/Users/MARK/Desktop/股东信息.jpg";
//		File file = new File(filePath);
//		params.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
//		InputStream input = new FileInputStream(file);
//		byte[] bytes = IOUtils.toByteArray(input);
//		String base64Image = Encodes.encodeBase64(bytes);	
//		FileServerClient client = new FileServerClient();
//		String respString = client.uploadBase64Image(base64Image,params);
		
		//上传base视频示例
//		String filePath = "D:/temp/测试.mp4";
//		File file = new File(filePath);
//		InputStream input = new FileInputStream(file);
//		params.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
//		byte[] bytes = IOUtils.toByteArray(input);
//		String base64Video = Encodes.encodeBase64(bytes);	
//		FileServerClient client = new FileServerClient();
//		String respString = client.uploadBase64Video(base64Video,params);
		
	}

}
