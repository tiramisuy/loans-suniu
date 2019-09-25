package com.rongdu.loans.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.common.rong360.Base64Utils;
import com.rongdu.loans.common.rong360.CommonUtil;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.common.rong360.RSAUtils;
import com.rongdu.loans.enums.Rong360ServiceEnums;

import lombok.extern.slf4j.Slf4j;

/**  
* @Title: RongClient.java  
* @Package com.rongdu.loans.loan.aspect  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月4日  
* @version V1.0  
*/
@Slf4j
public class RongClient {
	
	@SuppressWarnings("unchecked")
	public static <T> T rongPost(String method,Map<String,String> params,Class<T> clazz) throws Exception {
//		Rong360FeedBackResp resp = new Rong360FeedBackResp();
		T resp = null;
		resp = clazz.newInstance();
		
		String privateKey = Rong360Config.rong360_private_key;
		String appId = Rong360Config.rong360_app_id;
		String url = Rong360Config.rong360_gateway;
		String format = "json";
		
		Rong360ServiceEnums rong360ServiceEnums = Rong360ServiceEnums.get(method);
		log.debug("{}-请求业务数据：{}",method,params);
		if(params==null){
            params = new HashMap<String,String>();
        }
		//请求参数组合
		params.put("method", rong360ServiceEnums.getMethod());
		params.put("sign_type", rong360ServiceEnums.getSignType());
		params.put("version", rong360ServiceEnums.getVersion());
		params.put("app_id", appId);
		params.put("format", format);
		params.put("timestamp", String.valueOf(new Date().getTime()/1000));
		
		//sign处理 RSA加密
        String paramsStr = CommonUtil.getSortParams(params);
        byte[] bytes = RSAUtils.generateSHA1withRSASigature(paramsStr, privateKey, "UTF-8");
        String sign = Base64Utils.encode(bytes);
        params.put("sign", sign);
        log.debug("{}-请求报文：{}",method,params);
        //请求结果响应
        String result = HttpClientUtils.postForPair(url, params);
//        log.debug("{}-请求结果：{}",method,result);
        if (method.equals(Rong360ServiceEnums.TJY_IMAGE_API_FETCH.getMethod())) {
        	//融360获取图片内容接口不做json转换
        	return (T) result;
		}
        if (result == null || result.length() == 0) {
			throw new Exception("Request rong360 api " + method + " returns null");
		}
        resp = (T) JsonMapper.fromJsonString(result, clazz);
//        resp = JSONObject.parseObject(result, Rong360FeedBackResp.class);
        if (resp == null) {
			throw new Exception("Request rong360 api " + method + " got a non-json result");
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T rongTianJiPost(String method, Map<String,String> params, Class<T> clazz) throws Exception {
		T resp = null;
		resp = clazz.newInstance();
		
		String privateKey = Rong360Config.rong_tianji_private_key;
		String appId = Rong360Config.rong_tianji_app_id;
		String url = Rong360Config.rong_tianji_gateway;
		String logid = String.valueOf(new Date().getTime());
		url = url + "?logid=" + logid;
		String format = "json";
		
		Rong360ServiceEnums rong360ServiceEnums = Rong360ServiceEnums.get(method);
		log.debug("{}-请求业务数据：{}",method,params);
		if(params==null){
            params = new HashMap<String,String>();
        }
		//请求参数组合
		params.put("method", rong360ServiceEnums.getMethod());
		params.put("sign_type", rong360ServiceEnums.getSignType());
		params.put("version", rong360ServiceEnums.getVersion());
		params.put("app_id", appId);
		params.put("format", format);
		params.put("timestamp", String.valueOf(new Date().getTime()/1000));
		
		//sign处理 RSA加密
        String paramsStr = CommonUtil.getSortParams(params);
        byte[] bytes = RSAUtils.generateSHA1withRSASigature(paramsStr, privateKey, "UTF-8");
        String sign = Base64Utils.encode(bytes);
        params.put("sign", sign);
        log.debug("{}-请求报文：{}",method,params);
        //请求结果响应
        String result = HttpClientUtils.postForPair(url, params);
//        log.debug("{}-请求结果：{}",method,result);
        if (result == null || result.length() == 0) {
			throw new Exception("Request tianji api " + method + " returns null");
		}
        resp = (T) JsonMapper.fromJsonString(result, clazz);
//        resp = JSONObject.parseObject(result, clazz);
        if (resp == null) {
			throw new Exception("Request tianji api " + method + " got a non-json result");
		}
		return resp;
	}
	
	/**
     * 检验参数是否合法
     */
    private void checkParams(String appId, String privateKey) {
    	if (!StringUtils.isNumeric(appId) || (appId.length() != 7 && !appId.equals("123456"))) {
    		throw new IllegalArgumentException("appid格式不合法！");
    	}
    	if (!isValidPrivateKey(privateKey)) {
    		throw new IllegalArgumentException("私钥格式不合法！");
    	}
    }

    /**
     * 是否是合法的私钥
     * @return boolean 是否是合法的私钥
     */
	private boolean isValidPrivateKey(String privateKey) {
		String[] strs = StringUtils.split(privateKey, "\n");
		for (int index = 0; index < 13; index++) {
			if (index >= strs.length || strs[index].length() != 64) {
				return false;
			}
		}
		if (strs.length < 14 || strs[13].length() != 16) {
			return false;
		}
		return true;
	}
}
