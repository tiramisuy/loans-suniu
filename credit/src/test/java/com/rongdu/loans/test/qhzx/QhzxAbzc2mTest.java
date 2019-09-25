package com.rongdu.loans.test.qhzx;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.qhzx.common.QhzxConfig;
import com.rongdu.loans.qhzx.demo.DataSecurityUtil;
import com.rongdu.loans.qhzx.vo.QhzxRequest;
import com.rongdu.loans.qhzx.vo.QhzxRequestBusiData;
import com.rongdu.loans.qhzx.vo.QhzxResponse;
import com.rongdu.loans.qhzx.vo.QhzxResponseBusiData;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QhzxAbzc2mTest {
	
	private static String url = QhzxConfig.domain+"/do/"+QhzxConfig.net_type+"/query/"+QhzxConfig.ubzc2m_biz_code+"/"+QhzxConfig.api_ver+"/"+QhzxConfig.ubzc2m_message_code;
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(QhzxAbzc2mTest.class);
	
	public static void main(String[] args) throws Exception {
		QhzxRequest request = new QhzxRequest();
		
		String name = "卢院平";
		String idNo = "430104196606273061";
		String mobileNo = "18126098065";
		String accountNo = "6225880138988887";
		String entityAuthDate = DateUtils.getDate();
		String seqNo = StringUtils.leftPad(String.valueOf(RandomUtils.nextLong(0, 10000000)), 8, '0');
		String entityAuthCode = Digests.md5(idNo+entityAuthDate+seqNo);
		Map<String,String> param = new HashMap<String,String>();
		param.put("idType", "0");
		param.put("idNo", idNo);
		param.put("name", name);
		param.put("mobileNo",mobileNo);
		param.put("accountNo", accountNo);
		param.put("reasonCode", "01");
		param.put("entityAuthCode", entityAuthCode);
		param.put("entityAuthDate", entityAuthDate);
		param.put("seqNo", seqNo);
		
		QhzxRequestBusiData busiDataRequest =   (QhzxRequestBusiData)request.getBusiData();
		busiDataRequest.getRecords().add(param);
		String busiDataString = JsonMapper.toJsonString(request.getBusiData());
		logger.debug("{}-{}-请求报文(明文)：{}",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,busiDataString);
        
        String busiDataRequestEncrypt = DataSecurityUtil.encrypt(busiDataString.getBytes(),QhzxConfig.secret);
		request.setBusiData(busiDataRequestEncrypt);
		String signatureValue = DataSecurityUtil.signData(busiDataRequestEncrypt);
		request.getSecurityInfo().setSignatureValue(signatureValue);
		String userPassword = DataSecurityUtil.digest(QhzxConfig.user_password.getBytes());
		request.getSecurityInfo().setUserPassword(userPassword);
		
		String requestString = JsonMapper.toJsonString(request);
		logger.debug("{}-{}-请求地址：{}",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,url);
		logger.debug("{}-{}-请求报文：{}",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,requestString);
		long start = System.currentTimeMillis();
		String responseString = postForJson(url, requestString);
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		logger.debug("{}-{}-耗时：{}ms",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,timeCost);
		logger.debug("{}-{}-应答结果：{}",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,responseString);
		QhzxResponse response = (QhzxResponse)JsonMapper.fromJsonString(responseString, QhzxResponse.class);
		String busiDataResponseEncrypt = (String)response.getBusiData();
		String busiDataResponseString = DataSecurityUtil.decrypt(busiDataResponseEncrypt,QhzxConfig.secret);
		logger.debug("{}-{}-应答结果(明文)：{}",QhzxConfig.partner_name,QhzxConfig.ubzc2m_biz_name,busiDataResponseString);
		QhzxResponseBusiData busiDataResponse = (QhzxResponseBusiData)JsonMapper.fromJsonString(busiDataResponseString, QhzxResponseBusiData.class);

	}
	
		/**
		 * 发送POST请求
		 * @param url
		 * @param reqString
		 * @return
		 */
		public static String postForJson(String url,String jsonString) {
			RestTemplate client = new RestTemplate();
			List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
			StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
					
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=utf-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			headers.setContentLength(jsonString.getBytes().length);
			
			mcList.add(mc);
			client.setMessageConverters(mcList);
			HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
			String respString = client.postForObject(url, entity, String.class);
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
