package com.rongdu.common.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.StringUtils;

/**
 * 
* @Description:OSS存储工具  
* @author: 饶文彪
* @date 2018年6月25日 上午10:27:14
 */
public class OSSUtils {
	public static Logger logger = LoggerFactory.getLogger(OSSUtils.class);

	public static String ENDPOINT = "";// 外网地址
	public static String SERVER = "";// 资源访问域名
	public static String SERVERINNER = "";//内网资源访问路径
	private static String ACCESSKEYID = "";
	private static String ACCESSKEYSECRET = "";

	private static String BUCKETNAME = "";

	static {
		try {
			ENDPOINT = Global.getConfig("oss.endpoint");
			SERVER = Global.getConfig("oss.server");
			SERVERINNER = Global.getConfig("oss.server.inner");
			ACCESSKEYID = Global.getConfig("oss.accesskeyId");
			ACCESSKEYSECRET = Global.getConfig("oss.accesskeySeret");
			BUCKETNAME = Global.getConfig("oss.bucketName");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 
	* @Title: uploadFile
	* @Description: 上传文件到oss
	* @param is 
	* @param key
	* @return    设定文件
	* @return String    OSS资源URL
	* @throws
	 */
	public static String uploadFile(InputStream is, String key) {
		String rtnUrl = null;
		OSSClient client = null;
		PutObjectResult rs = null;
		try {
			
			ClientConfiguration configuration = new ClientConfiguration();
			configuration.setConnectionTimeout(5*1000);// 建立连接的超时时间（单位：毫秒）。
			configuration.setMaxErrorRetry(0);//当可重试的请求失败后最大的重试次数。（默认值为3）
			
			client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET,configuration);
			rs = client.putObject(BUCKETNAME, key, is);
			if (rs != null && StringUtils.isNotBlank(rs.getETag())) {
				rtnUrl = "http://" + SERVER + "/" + key;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(is);
			if (client != null)
				client.shutdown();
			if (rs != null && rs.getResponse() != null) {
				IOUtils.closeQuietly(rs.getResponse().getContent());
			}
		}
		return rtnUrl;
	}

	public static void main(String[] args) {
		// 上传字符串。
		String content = "test2";
		System.out.println(uploadFile(new ByteArrayInputStream(content.getBytes()), "test.txt"));
	}

}
