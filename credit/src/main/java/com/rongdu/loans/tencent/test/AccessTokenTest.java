package com.rongdu.loans.tencent.test;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.credit.http.HttpUtils;
import com.rongdu.loans.tencent.common.TencentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenTest {
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(AccessTokenTest.class);
	
	public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //公共参数
		params.put("app_id", TencentConfig.appid);
		params.put("secret",TencentConfig.secret);
		params.put("grant_type", "client_credential");
		params.put("version", "1.0.0");   
		
		String url = TencentConfig.access_token_url;
		url = HttpUtils.makeQueryString(url, params);
		
		logger.debug("{}-{}-请求地址：{}",TencentConfig.partner_name,TencentConfig.access_token_biz_name,url);
		logger.debug("{}-{}-请求报文：{}",TencentConfig.partner_name,TencentConfig.access_token_biz_name,params);
		long start = System.currentTimeMillis();
		HttpUtils httpUtils = new HttpUtils();
		String responseString = httpUtils.getForJson(url, params);
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		logger.debug("{}-{}-耗时：{}ms",TencentConfig.partner_name,TencentConfig.access_token_biz_name,timeCost);
		logger.debug("{}-{}-应答结果：{}",TencentConfig.partner_name,TencentConfig.access_token_biz_name,responseString);
		Map<String,Object> response = (Map<String,Object>)JsonMapper.fromJsonString(responseString, HashMap.class);
		if (response!=null&&"0".equals(response.get("code"))) {
			String tencentAccessToken = (String) response.get("access_token");
//			JedisUtils.set("tencentAccessToken", tencentAccessToken, 6600);
		}

	}
	
}
