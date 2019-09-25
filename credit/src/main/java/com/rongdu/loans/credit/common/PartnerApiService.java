package com.rongdu.loans.credit.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.credit.entity.ApiInvokeLog;
import com.rongdu.loans.credit.http.HttpUtils;
import com.rongdu.loans.mq.MessageProductor;

/**
 * 征信合作厂商的API接口的服务基类
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class PartnerApiService extends BaseService {

	/**
	 * 每个Service单独持有一个Http连接
	 */
	protected HttpUtils httpUtils = new HttpUtils();
	protected DozerBeanMapper beanMapper = new DozerBeanMapper();

	/**
	 * 发送post请求,将返回的Json字符串转化为VO对象
	 * 
	 * @param url
	 * @param jsonParams
	 * @param clazz
	 * @param log
	 * @return
	 */
	protected Object postForJson(String url, String jsonParams, Class<?> clazz, LogParam log) {
		logger.debug("{}-{}-请求地址：{}", log.getPartnerName(), log.getBizName(), url);
		logger.debug("{}-{}-请求报文：{}", log.getPartnerName(), log.getBizName(), jsonParams);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.postForJson(url, jsonParams);
		if (responseString != null && responseString.length() > 1000) {
			logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
					StringUtils.substring(responseString, 0, 1000));
		} else {
			logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
		}
		// 将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo = (CreditApiVo) response;
		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(vo.isSuccess());
		log.setCode(vo.getCode());
		log.setMsg(vo.getMsg());
		log.setUrl(url);
		log.setReqContent(jsonParams);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return response;
	}

	/**
	 * 发送post请求,将返回的Json字符串转化为VO对象
	 * 
	 * @param url
	 * @param params
	 * @param clazz
	 * @param log
	 * @return
	 */
	protected Object postForJson(String url, Map<String, String> params, Class<?> clazz, LogParam log) {
		String requestString = JsonMapper.toJsonString(params);
		logger.debug("{}-{}-请求地址：{}", log.getPartnerName(), log.getBizName(), url);
		logger.debug("{}-{}-请求报文：{}", log.getPartnerName(), log.getBizName(), requestString);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.postForJson(url, params);
		if (responseString != null && responseString.length() > 1000) {
			logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
					StringUtils.substring(responseString, 0, 1000));
		} else {
			logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
		}
		// 将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo = (CreditApiVo) response;
		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(vo.isSuccess());
		log.setCode(vo.getCode());
		log.setMsg(vo.getMsg());
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return response;
	}

	/**
	 * 发送get请求,将返回的Json字符串转化为VO对象
	 * 
	 * @param url
	 * @param params
	 * @param clazz
	 * @param log
	 * @return
	 */
	protected Object getForJson(String url, Map<String, String> params, Class<?> clazz, LogParam log) {
		String requestString = JsonMapper.toJsonString(params);
		logger.debug("{}-{}-请求地址：{}", log.getPartnerName(), log.getBizName(), url);
		logger.debug("{}-{}-请求报文：{}", log.getPartnerName(), log.getBizName(), requestString);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.getForJson(url, params);
		if (responseString != null && responseString.length() > 1000) {
			logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
					StringUtils.substring(responseString, 0, 1000));
		} else {
			logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
		}
		// 将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo = (CreditApiVo) response;
		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(vo.isSuccess());
		log.setCode(vo.getCode());
		log.setMsg(vo.getMsg());
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param clazz
	 * @param log
	 * @return
	 */
	protected Object getForJson(String url, Map<String, String> params, Map<String, String> headers, Class<?> clazz,
			LogParam log) {
		String requestString = JsonMapper.toJsonString(params);
		logger.debug("{}-{}-请求地址：{}", log.getPartnerName(), log.getBizName(), url);
		logger.debug("{}-{}-请求报文：{}", log.getPartnerName(), log.getBizName(), requestString);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.getForJson(url, params, headers);
		if (responseString != null && responseString.length() > 1000) {
			logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
					StringUtils.substring(responseString, 0, 1000));
		} else {
			logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
		}
		// 将应答的Json映射成Java对象
		Object response = JsonMapper.fromJsonString(responseString, clazz);
		CreditApiVo vo = (CreditApiVo) response;
		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(vo.isSuccess());
		log.setCode(vo.getCode());
		log.setMsg(vo.getMsg());
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param clazz
	 * @param log
	 * @return
	 */
	protected Object getListForJson(String url, Map<String, String> params, Map<String, String> headers,
			Class<?> clazz, LogParam log) {
		String requestString = JsonMapper.toJsonString(params);
		logger.debug("{}-{}-请求地址：{}", log.getPartnerName(), log.getBizName(), url);
		logger.debug("{}-{}-请求报文：{}", log.getPartnerName(), log.getBizName(), requestString);
		long start = System.currentTimeMillis();
		String responseString = httpUtils.getForJson(url, params, headers);
		if (responseString != null && responseString.length() > 1000) {
			logger.debug("{}-{}-应答结果：{}...", log.getPartnerName(), log.getBizName(),
					StringUtils.substring(responseString, 0, 1000));
		} else {
			logger.debug("{}-{}-应答结果：{}", log.getPartnerName(), log.getBizName(), responseString);
		}
		Object response = null;
		// 将应答的Json映射成Java对象
		if (responseString.startsWith("[")) {
			JavaType javaType = JsonMapper.getInstance().createCollectionType(ArrayList.class, clazz);
			response = JsonMapper.getInstance().fromJson(responseString, javaType);
			log.setSuccess(true);
			log.setCode("");
			log.setMsg("成功");
		} else {
			response = JsonMapper.fromJsonString(responseString, clazz);
			CreditApiVo vo = (CreditApiVo) response;
			log.setSuccess(vo.isSuccess());
			log.setCode(vo.getCode());
			log.setMsg(vo.getMsg());
		}
		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return response;
	}

	/**
	 * 通过
	 * 
	 * @param param
	 */
	protected void saveApiInvokeLog(LogParam param) {
		logger.debug("{}-{}-处理结果：{}，应答代码：{}，应答消息：{}，耗时：{}ms", param.getPartnerName(), param.getBizName(),
				param.isSuccess(), param.getCode(), param.getMsg(), param.getCostTime());
		// 写入队列
		ApiInvokeLog log = new ApiInvokeLog();
		beanMapper.map(param, log);
		log.setId(IdGen.uuid());
		log.setIsNewRecord(true);
		log.setMonth(Integer.parseInt(DateUtils.getDate("yyyyMMdd")));
		log.setStatus(param.getCode());
		log.setRemark(param.getMsg());
		Date invokeTime = log.getInvokeTime();
		if (invokeTime == null) {
			invokeTime = new Date();
			log.setInvokeTime(invokeTime);
		}
		String str = log.getSyncRespContent();
		if (StringUtils.isNotBlank(str) && str.length() > 1000) {
			str = str.replaceAll("\"([^\"]{500,})\"", "\"\"");
			if (str.length() > 10000) {
				str = null;
			}
			log.setSyncRespContent(str);
		}
		MessageProductor productor = SpringContextHolder.getBean("messageProductor");
		productor.sendDataToQueue(log);

	}

	/**
	 * 移除Map中指定的元素
	 * 
	 * @param bizParams
	 * @return
	 */
	public Map<String, String> removeMapElement(Map<String, String> bizParams, String... removeKey) {
		if (bizParams != null) {
			Iterator iterator = bizParams.keySet().iterator();
			List<String> keys = Arrays.asList(removeKey);
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (keys.contains(key)) {
					iterator.remove();
					bizParams.remove(key);
				}
			}
		}
		return bizParams;
	}

}
