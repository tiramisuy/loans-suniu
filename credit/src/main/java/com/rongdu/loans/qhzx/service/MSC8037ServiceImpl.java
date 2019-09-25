package com.rongdu.loans.qhzx.service;

import com.rongdu.common.service.BaseService;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 前海征信-好信常贷客（专业版）
 * @author sunda
 * @version 2017-06-20
 */
@Service
public class MSC8037ServiceImpl extends BaseService{
	
	public static final int SALT_SIZE = 8;
	
	
	
	public void post(){
		
		logger.debug(" 前海征信-好信常贷客（专业版）：");
	}
	
	/**
	 * 发送POST请求
	 * @param url
	 * @param reqString
	 * @return
	 */
	public String post(String url,String reqString) {
		RestTemplate client = new RestTemplate();
		List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
		StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
		mcList.add(mc);
		client.setMessageConverters(mcList);
		HttpEntity<String> entity = new HttpEntity<String>(reqString);
		String respString = client.postForObject(url, entity, String.class);
		return respString;
	}
	

	
}
