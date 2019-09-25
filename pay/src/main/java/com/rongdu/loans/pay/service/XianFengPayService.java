package com.rongdu.loans.pay.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.XianFengServiceEnum;
import com.rongdu.loans.pay.common.HttpUtils;
import com.rongdu.loans.pay.exception.XfNewInstanceException;
import com.rongdu.loans.pay.utils.XianFengConfig;
import com.ucf.sdk.UcfForOnline;
import com.ucf.sdk.util.AESCoder;
import com.ucf.sdk.util.UnRepeatCodeGenerator;

import lombok.extern.slf4j.Slf4j;

/**  
* @Title: XianFengPayService.java  
* @Package com.rongdu.loans.pay.service  
* @Description: 先锋支付 
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
@Slf4j
@Service
public class XianFengPayService {
	
	HttpUtils httpUtils = new HttpUtils();
	
	/**
	* @Title: xfPayPostForObject  
	* @Description: 先锋支付-公用请求提取 (POST)
	* @param @param serviceName 先锋接口名
	* @param @param params	接口请求（差异）参数
	* @return T    返回类型  
	 */
	 public <T> T xfPayPostForObject(String serviceName, Map<String, String> params, Class<T> clazz){
		long start = System.currentTimeMillis();
		XianFengServiceEnum xianFengServiceEnum = XianFengServiceEnum.get(serviceName);
		String version = xianFengServiceEnum.getVersion();//接口版本
		String secId = xianFengServiceEnum.getSecId();//签名算法
		String bizName = xianFengServiceEnum.getDesc();
		String partnerName = Global.XIANFENG_CHANNEL_NAME;
		
		String merchantId = XianFengConfig.merchantId;//商户号
		String key = XianFengConfig.merRSAKey;//密钥
		String actionUrl = XianFengConfig.gateway;//接入地址
		String sign = null;
		
		String responseStr = null;
//		XfWithdrawResultVO result = new XfWithdrawResultVO();
		T result = null;
		try {
			result = clazz.newInstance();
			//序列号（本次请求的唯一标识，防止重复提交）
			String reqSn =UnRepeatCodeGenerator.createUnRepeatCode(merchantId, serviceName, new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()));
			if (StringUtils.isNotBlank(params.get("data"))) {
				String dataStr = params.get("data");
				log.debug("{}-{}-业务数据：{}",serviceName,bizName,dataStr);
				dataStr = AESCoder.encrypt(dataStr, key);
				params.put("data",dataStr);
			}
			log.debug("-------------------------------------------------");
			
			params.put("service",serviceName);
			params.put("secId",secId);
			params.put("version",version);
			params.put("merchantId",merchantId);
			params.put("reqSn",reqSn);
			log.debug("{}-{}-{}-请求报文：{}",partnerName,serviceName,bizName,params);
			sign = UcfForOnline.createSign(key, "sign", params, secId);
			params.put("sign", sign);
			log.debug("-------------------------------------------------");
			
			responseStr = httpUtils.postForJson(actionUrl, params);
			responseStr = AESCoder.decrypt(responseStr, key);
			//判断解密是否正确。如果为空则公钥不正确
			if(responseStr.isEmpty()){
				log.debug("{}-{}-{}-请求结果为空，检查解密公钥是否正确！}",partnerName,serviceName,bizName);
			}
			log.debug("{}-{}-{}-应答结果：{}",partnerName,serviceName,bizName,responseStr);
			log.debug("-------------------------------------------------");
			result = JSONObject.parseObject(responseStr, clazz);
			
			log.debug("result="+result);
		} catch (InstantiationException e) {
			throw new XfNewInstanceException(clazz.getName(),e);
		} catch (IllegalAccessException e) {
			throw new XfNewInstanceException(clazz.getName(),e);
		}
		catch (Exception e) {
			log.error("{}-{}-{}-先锋接口请求异常",partnerName,serviceName,bizName,e);
		}
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		log.debug("{}-{}-{}-耗时：{}ms",partnerName,serviceName,bizName,timeCost);
		return result;
	}
	
}
