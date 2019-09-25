package com.rongdu.loans.pay.service;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.pay.common.HttpUtils;

import java.util.Map;

/**
 * 征信合作厂商的API接口的服务基类
 * @author sunda
 * @version 2017-07-10
 */
public class PartnerApiService extends BaseService{
	
	/**
	 * 每个Service单独持有一个Http连接
	 */
	protected HttpUtils httpUtils = new HttpUtils();


	/**
	 * 发送post请求,将返回的Json字符串转化为VO对象
	 * @param url
	 * @param jsonParams
	 * @param clazz
	 * @param partnerId
	 * @param partnerName
	 * @param bizCode
	 * @param bizName
	 * @return
	 */
	protected Object postForJson(String url, String jsonParams,
			Class<?> clazz, String partnerId, String partnerName, String bizCode, String bizName) {
		logger.debug("{}-{}-请求地址：{}",partnerName,bizName,url);
		logger.debug("{}-{}-请求报文：{}",partnerName,bizName,jsonParams);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.postForJson(url, jsonParams);
		logger.debug("{}-{}-应答结果：{}",partnerName,bizName,responseString);
		//将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo= (CreditApiVo)response;
		boolean success = vo.isSuccess();
		String code = vo.getCode();
		String msg = vo.getMsg();
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		saveApiInvokeLog(partnerId,partnerName,bizCode,bizName,timeCost,success,code,msg);
		logger.debug("{}-{}-耗时：{}ms",partnerName,bizName,timeCost);
		return response;
	}
	
	/**
	 * 发送post请求,将返回的Json字符串转化为VO对象
	 * @param url
	 * @param params
	 * @param clazz
	 * @param partnerId
	 * @param partnerName
	 * @param bizId
	 * @param bizName
	 * @return
	 */
	protected Object postForJson(String url, Map<String, String> params,
			Class<?> clazz, String partnerId, String partnerName, String bizCode, String bizName) {
		logger.debug("{}-{}-请求地址：{}",partnerName,bizName,url);
		logger.debug("{}-{}-请求报文：{}",partnerName,bizName,params);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.postForJson(url, params);
		logger.debug("{}-{}-应答结果：{}",partnerName,bizName,responseString);
		//将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo= (CreditApiVo)response;
		boolean success = vo.isSuccess();
		String code = vo.getCode();
		String msg = vo.getMsg();
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		saveApiInvokeLog(partnerId,partnerName,bizCode,bizName,timeCost,success,code,msg);
		logger.debug("{}-{}-耗时：{}ms",partnerName,bizName,timeCost);
		return response;
	}
	
	/**
	 * 发送get请求,将返回的Json字符串转化为VO对象
	 * @param url
	 * @param params
	 * @param clazz
	 * @param partnerId
	 * @param partnerName
	 * @param bizId
	 * @param bizName
	 * @return
	 */
	protected Object getForObject(String url, Map<String, String> params,
			Class<?> clazz, String partnerId, String partnerName, String bizCode, String bizName) {
		logger.debug("{}-{}-请求地址：{}",partnerName,bizName,url);
		logger.debug("{}-{}-请求报文：{}",partnerName,bizName,params);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.getForJson(url, params);
		logger.debug("{}-{}-应答结果：{}",partnerName,bizName,responseString);
		//将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo= (CreditApiVo)response;
		boolean success = vo.isSuccess();
		String code = vo.getCode();
		String msg = vo.getMsg();
		long end = System.currentTimeMillis();
		long timeCost = end-start;
		saveApiInvokeLog(partnerId,partnerName,bizCode,bizName,timeCost,success,code,msg);
		logger.debug("{}-{}-耗时：{}ms",partnerName,bizName,timeCost);
		return response;
	}

	protected void saveApiInvokeLog(String partnerId, String partnerName,
			String bizCode, String bizName,long timeCost,boolean success, String code,
			String msg) {
		logger.debug("{}-{}-处理结果：{}，应答代码：{}，应答消息：{}",partnerName,bizName,success,code,msg);
		//写入队列
		
	}
	
	
}
