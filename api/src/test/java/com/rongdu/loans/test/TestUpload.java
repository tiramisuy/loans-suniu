package com.rongdu.loans.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" })
public class TestUpload {

	@Test
	public void testUpload() {
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

		// //上传图片文件示例
		String filePath = "D:/测试图片.png";
		File file = new File(filePath);
		// 注意获取用户端上传源文件的文件名
		String origName = file.getName();
		params.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		params.setOrigName(origName);
		FileServerClient client = new FileServerClient();
		String respString = client.uploadImage(file, params);
		System.out.println("upload-resp ：" + respString);
	}
}
