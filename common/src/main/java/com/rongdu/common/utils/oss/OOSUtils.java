package com.rongdu.common.utils.oss;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;

public class OOSUtils {
	public static Logger logger = LoggerFactory.getLogger(OOSUtils.class);
	public static final String OOS_TOKEN_CACHE_KEY = "OOS_TOKEN_CACHE_KEY_";
	public static final String BORROW_FILE_CACHE_KEY = "BORROW_FILE_CACHE_KEY_";
	public static final String STS_TOKEN_URL = Global.getConfig("STS.token.url");
	public static final String endpoint = "oss-cn-shenzhen.aliyuncs.com";
	public static final String bucketName = "tflborrowimage";
	public static final String XJDFQBucketName = "toufuli";

	public static OOSToken getToken(String uid, String applyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Uid", uid);

		String cacheKey = OOS_TOKEN_CACHE_KEY + applyId;
		OOSToken token = (OOSToken) JedisUtils.getObject(cacheKey);
		if (token == null) {
			logger.info("图片上传阿里云OSS请求，url = {}, param = {}", STS_TOKEN_URL, map.toString());
			GetSTSTokenResult result = (GetSTSTokenResult) RestTemplateUtils.getInstance().postForObject(STS_TOKEN_URL,
					map, GetSTSTokenResult.class);
			logger.info("图片上传阿里云OSS响应:{}", JsonMapper.toJsonString(result));
			if (result != null && result.getStatus() != null && "OK".equals(result.getStatus())) {
				token = new OOSToken();
				token.setAccessKeyId(result.getData().get("AccessKeyId"));
				token.setAccessKeySecret(result.getData().get("AccessKeySecret"));
				token.setSecurityToken(result.getData().get("SecurityToken"));
				token.setExpiration(result.getData().get("Expiration"));
				JedisUtils.setObject(cacheKey, token, 60 * 30);
			}
		}
		return token;
	}

	public static boolean uploadBorrowFile(String uid, String applyId, InputStream is, String key) {
		boolean flag = false;
		OSSClient client = null;
		PutObjectResult rs = null;
		String cacheKey = BORROW_FILE_CACHE_KEY + applyId;
		try {
			OOSToken token = getToken(uid, applyId);
			if (token != null) {
				client = new OSSClient(endpoint, token.getAccessKeyId(), token.getAccessKeySecret(),
						token.getSecurityToken());
				rs = client.putObject(bucketName, key, is);
				if (rs != null && StringUtils.isNotBlank(rs.getETag())) {
					flag = true;
					JedisUtils.set(cacheKey, key, 24*60*60);
					logger.info("图片上传结果:{}", rs.getETag());
				}
			}
		} finally {
			IOUtils.closeQuietly(is);
			if (client != null)
				client.shutdown();
			if (rs != null && rs.getResponse() != null) {
				IOUtils.closeQuietly(rs.getResponse().getContent());
			}
		}
		return flag;
	}

	public static boolean uploadCardFile(String uid, String applyId, InputStream is, String key) {
		boolean flag = false;
		OSSClient client = null;
		PutObjectResult rs = null;
		try {
			OOSToken token = getToken(uid, applyId);
			if (token != null) {
				client = new OSSClient(endpoint, token.getAccessKeyId(), token.getAccessKeySecret(),
						token.getSecurityToken());
				rs = client.putObject(XJDFQBucketName, key, is);
				if (rs != null && StringUtils.isNotBlank(rs.getETag())) {
					flag = true;
					logger.info("图片上传结果:{}", rs.getETag());
				}
			}
		} finally {
			IOUtils.closeQuietly(is);
			if (client != null)
				client.shutdown();
			if (rs != null && rs.getResponse() != null) {
				IOUtils.closeQuietly(rs.getResponse().getContent());
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		getToken("34441", "1");
	}

}
