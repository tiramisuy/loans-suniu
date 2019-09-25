package com.rongdu.loans.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.json.JSONObject;
import com.rongdu.common.mapper.JsonMapper;

public class TestHttp {
	public static void main(String[] args) {
		//String url = "http://192.168.21.100:18080/anon/zhima/callback?error_message=%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F&open_id=268812577406819781144423847&success=true&error_code=SUCCESS";
		String url = "http://192.168.1.248:9080/api/anon/cust/saveApplyInfo";
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
		//headers.add("userId", "974e53f09def487ca7cb289836c3d0e0");
		//headers.add("tokenId", "7dd3e3ebf29745a49cd828d3f2cc9216");
		
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.set("userId", "c4b7a6191c1949cc917403c3dc7ab780");
		params.set("applyAmt", "10000");
		params.set("applyTerm", "30");
		params.set("source", "2");
		params.set("productId", "CCD");
		params.set("productType", "0");
		params.set("bqsTokenKey", "bqs");
		params.set("tdBlackBox", "td");
		
		HttpEntity<String> entity = new HttpEntity<String>(JsonMapper.toJsonString(params),headers);
		String str = template.postForObject(url, entity, String.class);
		System.out.println(str);
	}
}

