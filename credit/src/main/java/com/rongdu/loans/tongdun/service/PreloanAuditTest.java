package com.rongdu.loans.tongdun.service;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.http.HttpUtils;
import com.rongdu.loans.qhzx.common.QhzxConfig;
import com.rongdu.loans.tongdun.common.TongdunConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreloanAuditTest {
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(PreloanAuditTest.class);
	
	public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //公共参数
		// 此处值填写您的合作方标识
		params.put("partner_code", TongdunConfig.partner_code);
		// 此处填写对应app密钥
		params.put("partner_key",TongdunConfig.partner_key);
		// 此处填写策略集上的事件标识
		params.put("app_name", TongdunConfig.android_app_name);
//		//此处填写移动端sdk采集到的信息black_box
//		params.put("black_box", "your_black_box_to_send_to_api");
//		//以下填写其他要传的参数，比如系统字段，扩展字段
//		params.put("account_login", "your_login_name");
//		//终端IP地址
//		params.put("ip_address", "your_login_ip");
		
//		String name = "王五";
//		String idNo = "451110190501015626";
//		String mobile = "18600000000";
		String name = "皮晴晴";
		String idNo = "370404199006301915";
		String mobile = "13333333333";
		// 业务参数
		params.put("name", name);
		params.put("id_number", idNo);
		params.put("mobile", mobile);        
		
		String url = "https://apitest.tongdun.cn/preloan/apply/v5";
		url = HttpUtils.makeQueryString(url, params);
		
		logger.debug("{}-{}-请求地址：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,url);
		logger.debug("{}-{}-请求报文：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,params);
		long start = System.currentTimeMillis();
		HttpUtils httpUtils = new HttpUtils();
		String responseString = httpUtils.postForJson(url, params);
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		logger.debug("{}-{}-耗时：{}ms",QhzxConfig.partner_name,QhzxConfig.address_biz_name,timeCost);
		logger.debug("{}-{}-应答结果：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,responseString);
		Map<String,Object> response = (Map<String,Object>)JsonMapper.fromJsonString(responseString, HashMap.class);
		String reportId = (String) response.get("report_id");
		if (StringUtils.isNotBlank(reportId)) {
			queryReportDetai(reportId);
		}

	}
	
	private static void queryReportDetai(String reportId) {
		Map<String, String> params = new HashMap<String, String>();
		//公共参数
		// 此处值填写您的合作方标识
		params.put("partner_code", TongdunConfig.partner_code);
		// 此处填写app密钥
		params.put("partner_key",TongdunConfig.partner_key);
		// 此处填写app名称
		params.put("app_name", TongdunConfig.android_app_name);
		params.put("report_id", reportId);
		
		// 业务参数
		params.put("report_id", reportId);

		String  url = "https://apitest.tongdun.cn/preloan/report/v8";
		
		url = HttpUtils.makeQueryString(url, params);
		logger.debug("{}-{}-请求地址：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,url);
		logger.debug("{}-{}-请求报文：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,params);
		long start = System.currentTimeMillis();
		HttpUtils httpUtils = new HttpUtils();
		String responseString = httpUtils.getForJson(url, params);
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		logger.debug("{}-{}-耗时：{}ms",QhzxConfig.partner_name,QhzxConfig.address_biz_name,timeCost);
		logger.debug("{}-{}-应答结果：{}",QhzxConfig.partner_name,QhzxConfig.address_biz_name,responseString);
		Map<String,Object> response = (Map<String,Object>)JsonMapper.fromJsonString(responseString, HashMap.class);
		
	}

		/**
		 * 发送POST请求
		 * @param url
		 * @param reqString
		 * @return
		 */
		public static String postForJson(String url,Map<String, String> params) {
			RestTemplate client = new RestTemplate();
			List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
//			StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
					
//			HttpHeaders headers = new HttpHeaders();
//			MediaType type = MediaType.parseMediaType("application/json; charset=utf-8");
//			headers.setContentType(type);
//			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			
//			mcList.add(mc);
//			client.setMessageConverters(mcList);
//			HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(params, headers);
			String respString = client.postForObject(url, params, String.class);
			return respString;
		}
		
		
	/**
	 * 发送POST请求
	 * @param url
	 * @param reqString
	 * @return
	 */
	public static String post(String url,String requestString) {
		RestTemplate client = new RestTemplate();
		List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
		StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
		mcList.add(mc);
		client.setMessageConverters(mcList);
		HttpEntity<String> entity = new HttpEntity<String>(requestString);
		String respString = client.postForObject(url, entity, String.class);
		return respString;
	}
	

}
