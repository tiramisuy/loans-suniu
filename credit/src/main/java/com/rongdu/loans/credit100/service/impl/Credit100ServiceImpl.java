/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit100.service.impl;

import com.bfd.facade.MerchantServer;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit100.common.ApiRespCode;
import com.rongdu.loans.credit100.common.Credit100Config;
import com.rongdu.loans.credit100.common.RespCodeUtils;
import com.rongdu.loans.credit100.manager.SpecialListCManager;
import com.rongdu.loans.credit100.message.ApplyLoanMonReqData;
import com.rongdu.loans.credit100.message.ApplyLoandReqData;
import com.rongdu.loans.credit100.message.Credit100Request;
import com.rongdu.loans.credit100.message.SpecialListcReqData;
import com.rongdu.loans.credit100.service.Credit100Service;
import com.rongdu.loans.credit100.vo.ApplyLoanMonOP;
import com.rongdu.loans.credit100.vo.ApplyLoandOP;
import com.rongdu.loans.credit100.vo.ApplyLoandVO;
import com.rongdu.loans.credit100.vo.SpecialListCOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 百融-特殊名单核查-业务逻辑实现类
 * 
 * @author sunda
 * @version 2017-08-14
 */
@Service("credit100Service")
public class Credit100ServiceImpl extends Credit100BaseService implements Credit100Service {

	/**
	 * 百融-特殊名单核查-实体管理接口
	 */
	@Autowired
	private SpecialListCManager specialListCManager;

	// 商户客户端
	private MerchantServer ms = new MerchantServer();

	/**
	 * 特殊名单核查
	 */
	public Map<String,String> specialListc(SpecialListCOP op) {

		// 配置参数
		String partnerId = Credit100Config.partner_id;
		String partnerName = Credit100Config.partner_name;
		String bizCode = Credit100Config.speciallistc_biz_code;
		String bizName = Credit100Config.speciallistc_biz_name;
		String url = "";

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Credit100Request<SpecialListcReqData> request = new Credit100Request(new SpecialListcReqData());
		// 请求参数
		String id = op.getIdNo();
		String cell = op.getMobile();
		String name = op.getName();

		request.getReqData().setId(id);
		request.getReqData().getCell().add(cell);
		request.getReqData().setName(name);
		//request.getReqData().setMeal(bizCode);
		request.getReqData().setStrategy_id(bizCode);
		request.setTokenid(getTokenid());

		long start = System.currentTimeMillis();
		String requestString = JsonMapper.toJsonString(request);
		logger.debug("{}-{}-请求报文：{}", partnerName, bizName, requestString);
		String responseString = "";
		try {
			responseString = ms.getApiData(requestString, Credit100Config.api_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("{}-{}-应答结果：{}", partnerName, bizName, responseString);

		Map<String,String> result  = (Map<String, String>) JsonMapper.fromJsonString(responseString, Map.class);
		String code = result.get("code");
		String succCode = ApiRespCode.SUCCESS.getCode();
		logger.debug("{}-{}-调用结果：{}，{}，{}", partnerName, bizName, code, RespCodeUtils.getApiMsg(code),
				RespCodeUtils.getApiSolution(code));
		if (StringUtils.isNotBlank(code)&&succCode.equals(code)) {

		}
		String c100007 = ApiRespCode.C_100007.getCode();
		// tokenid有效期是1个小时，如果1个小时没有用，就会过期。建议通过过期返回的错误码100007判断是否需要重新登录。
		int retryTimes = getRetryTimes(op.getApplyId());
		if (c100007.equals(code) && retryTimes < 2) {
			addRetryTimes(op.getApplyId());
			clearTokenidCache();
			specialListc(op);
		}

		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(ApiRespCode.SUCCESS.getCode().equals(code));
		log.setCode(code);
		log.setMsg(RespCodeUtils.getApiMsg(code));
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return result;
	}

	/**
	 * 多次申请核查月度版
	 */
	public Map<String,String> applyLoanMon(ApplyLoanMonOP op) {

		// 配置参数
		String partnerId = Credit100Config.partner_id;
		String partnerName = Credit100Config.partner_name;
		String bizCode = Credit100Config.applyloanmon_biz_code;
		String bizName = Credit100Config.applyloanmon_biz_name;
		String url = "";

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Credit100Request<ApplyLoanMonReqData> request = new Credit100Request(new ApplyLoanMonReqData());
		// 请求参数
		String id = op.getIdNo();
		String cell = op.getMobile();
		String name = op.getName();

		request.getReqData().setId(id);
		request.getReqData().getCell().add(cell);
		request.getReqData().setName(name);
		//request.getReqData().setMeal(bizCode);
		request.getReqData().setStrategy_id(bizCode);
		request.setTokenid(getTokenid());

		long start = System.currentTimeMillis();
		String requestString = JsonMapper.toJsonString(request);
		logger.debug("{}-{}-请求报文：{}", partnerName, bizName, requestString);
		String responseString = "";
		try {
			responseString = ms.getApiData(requestString, Credit100Config.api_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("{}-{}-应答结果：{}", partnerName, bizName, responseString);

		Map<String,String> result  = (Map<String, String>) JsonMapper.fromJsonString(responseString, Map.class);
		String code = result.get("code");
		String succCode = ApiRespCode.SUCCESS.getCode();
		logger.debug("{}-{}-调用结果：{}，{}，{}", partnerName, bizName, code, RespCodeUtils.getApiMsg(code),
				RespCodeUtils.getApiSolution(code));
		if (StringUtils.isNotBlank(code)&&succCode.equals(code)) {

		}
		String c100007 = ApiRespCode.C_100007.getCode();
		// tokenid有效期是1个小时，如果1个小时没有用，就会过期。建议通过过期返回的错误码100007判断是否需要重新登录。
		int retryTimes = getRetryTimes(op.getApplyId());
		if (c100007.equals(code) && retryTimes < 2) {
			addRetryTimes(op.getApplyId());
			clearTokenidCache();
			applyLoanMon(op);
		}

		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(ApiRespCode.SUCCESS.getCode().equals(code));
		log.setCode(code);
		log.setMsg(RespCodeUtils.getApiMsg(code));
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return result;
	}

	/**
	 * 当日多次申请
	 */
	public ApplyLoandVO applyLoand(ApplyLoandOP op) {

		// 配置参数
		String partnerId = Credit100Config.partner_id;
		String partnerName = Credit100Config.partner_name;
		String bizCode = Credit100Config.applyloand_biz_code;
		String bizName = Credit100Config.applyloand_biz_name;
		String url = "";

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		Credit100Request<ApplyLoandReqData> request = new Credit100Request(new ApplyLoandReqData());
		// 请求参数
		String id = op.getIdNo();
		String cell = op.getMobile();
		String name = op.getName();

		request.getReqData().setId(id);
		request.getReqData().getCell().add(cell);
		request.getReqData().setName(name);
		request.getReqData().setMeal(bizCode);
		request.setTokenid(getTokenid());

		long start = System.currentTimeMillis();
		String requestString = JsonMapper.toJsonString(request);
		logger.debug("{}-{}-请求报文：{}", partnerName, bizName, requestString);
		String responseString = "";
		try {
			responseString = ms.getApiData(requestString, Credit100Config.api_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("{}-{}-应答结果：{}", partnerName, bizName, responseString);

		ApplyLoandVO result = (ApplyLoandVO) JsonMapper.fromJsonString(responseString, ApplyLoandVO.class);
		String code = result.getCode();
		code = result.get("code");
		logger.debug("{}-{}-调用结果：{}，{}，{}", partnerName, bizName, code, result.getMsg(),
				RespCodeUtils.getApiSolution(code));
		if (result.isSuccess()) {

		}
		String c100007 = ApiRespCode.C_100007.getCode();
		// tokenid有效期是1个小时，如果1个小时没有用，就会过期。建议通过过期返回的错误码100007判断是否需要重新登录。
		int retryTimes = getRetryTimes(op.getApplyId());
		if (c100007.equals(code) && retryTimes < 2) {
			addRetryTimes(op.getApplyId());
			clearTokenidCache();
			applyLoand(op);
		}

		long end = System.currentTimeMillis();
		long costTime = end - start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(result.isSuccess());
		log.setCode(result.getCode());
		log.setMsg(result.getMsg());
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return result;
	}

}