package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.baiqishi.message.*;
import com.rongdu.loans.credit.baiqishi.service.ZhimaService;
import com.rongdu.loans.credit.baiqishi.vo.*;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 芝麻信用-服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("zhimaService")
public class ZhimaServiceImpl extends PartnerApiService implements ZhimaService{
	
	/**
	 * 芝麻信用授权
	 * @return
	 */
	public AuthorizeVO authorize(AuthorizeOP op) {
		
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.zhima_authorize_biz_code;
		String bizName = BaiqishiConfig.zhima_authorize_biz_name;
		String url = BaiqishiConfig.zhima_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());
		
		ZhimaRequest request = new ZhimaRequest();
		request.setProductId(bizCode);
		String channel = "app";
		String callbackUrl = BaiqishiConfig.zhima_callback_url;	
		//业务参数
		String idNo = op.getIdNo();
		String name = op.getName();
		request.getExtParam().setIdentityType("2");
		request.getExtParam().setCertNo(idNo);
		request.getExtParam().setName(name);
		request.getExtParam().setChannel(channel);
		request.getExtParam().setCallbackUrl(callbackUrl);		
		String jsonParamsBody  = JsonMapper.toJsonString(request);		
		//发送请求
		AuthorizeResponse response = (AuthorizeResponse) postForJson(url,jsonParamsBody,AuthorizeResponse.class,log);
		AuthorizeVO vo = new AuthorizeVO();
		//应答成功
		if (response!=null) {
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setFlowNo(response.getFlowNo());
			vo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				vo.setAuthInfoUrl(response.getResultData().getAuthInfoUrl());	
			}
		}		
		return vo;
	}
	
	/**
	 * 查询芝麻信用授权结果
	 * @return
	 */
	public AuthorizeResultVO getAuthorizeResult(AuthorizeResultOP op) {
		
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.zhima_authorize_result_biz_code;
		String bizName = BaiqishiConfig.zhima_authorize_result_biz_name;
		String url = BaiqishiConfig.zhima_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());
		
		ZhimaRequest request = new ZhimaRequest();
		request.setProductId(bizCode);
		//业务参数
		String openId = op.getOpenId();
		if (StringUtils.isNotBlank(openId)) {
			request.getExtParam().setIdentityType("0");
			request.getExtParam().setOpenId(openId);
		}else {
			String idNo = op.getIdNo();
			String name = op.getName();
			request.getExtParam().setIdentityType("2");
			request.getExtParam().setCertNo(idNo);
			request.getExtParam().setName(name);	
		}
		
		String jsonParamsBody  = JsonMapper.toJsonString(request);	
		//发送请求
		AuthorizeResultResponse response = (AuthorizeResultResponse) postForJson(url, jsonParamsBody ,AuthorizeResultResponse.class,log);
		//应答成功
		AuthorizeResultVO vo = new AuthorizeResultVO();
		//应答成功
		if (response!=null) {
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setFlowNo(response.getFlowNo());
			vo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				vo.setOpenId(response.getResultData().getOpenId());
				vo.setAuthorized(response.getResultData().getAuthorized());
			}
		}		
		return vo;
	}
	
	/**
	 * 查询芝麻信用分
	 * @return
	 */
	public ZmScoreVO getZmScore(ZmScoreOP op) {	
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.zhima_zmscore_biz_code;
		String bizName = BaiqishiConfig.zhima_zmscore_biz_name;
		String url = BaiqishiConfig.zhima_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());
		
		ZhimaRequest request = new ZhimaRequest();
		request.setProductId(bizCode);	
		//业务参数
		String openId = op.getOpenId();
		ZmScoreVO vo = new ZmScoreVO();
		if (StringUtils.isNotBlank(openId)){
			request.getExtParam().setOpenId(openId);
			String jsonParamsBody  = JsonMapper.toJsonString(request);
			//发送请求
			ZmScoreResponse response = (ZmScoreResponse) postForJson(url, jsonParamsBody ,ZmScoreResponse.class,log);
			//应答成功
			if (response!=null) {
				vo.setCode(response.getCode());
				vo.setMsg(response.getMsg());
				vo.setFlowNo(response.getFlowNo());
				vo.setSuccess(response.isSuccess());
				if (response.isSuccess()) {
					vo.setZmScore(response.getResultData().getZmScore());
					vo.setBizNo(response.getResultData().getBizNo());
				}
			}
		}else{
			vo.setCode(ErrInfo.ERROR.getCode());
			vo.setMsg("芝麻信用不存在或者尚未授权");
		}
		return vo;
	}
	
	/**
	 * 查询芝麻信用行业关注名单
	 * @return
	 */
	public ZmWatchListVO getZmWatchList(ZmWatchListOP op) {
		
		//配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.zhima_watchlist_biz_code;
		String bizName = BaiqishiConfig.zhima_watchlist_biz_name;
		String url = BaiqishiConfig.zhima_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());
		
		ZhimaRequest request = new ZhimaRequest();
		request.setProductId(bizCode);
		//业务参数
		String openId = op.getOpenId();
		request.getExtParam().setOpenId(openId);		
		String jsonParamsBody  = JsonMapper.toJsonString(request);	
		//发送请求
		ZmWatchListResponse response = (ZmWatchListResponse) postForJson(url, jsonParamsBody ,ZmWatchListResponse.class,log);
		//应答成功
		ZmWatchListVO vo = new ZmWatchListVO();
		//应答成功
		if (response!=null) {
			vo.setCode(response.getCode());
			vo.setMsg(response.getMsg());
			vo.setFlowNo(response.getFlowNo());
			vo.setSuccess(response.isSuccess());
			if (response.isSuccess()) {
				vo.setMatched(response.getResultData().isMatched());
				vo.setBizNo(response.getResultData().getBizNo());
				vo.setDetails(response.getResultData().getDetails());
			}
		}	
		return vo;
	}
	
}
