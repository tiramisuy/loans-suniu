package com.rongdu.loans.koudai.api.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.koudai.api.service.KDPayApiService;
import com.rongdu.loans.koudai.api.vo.pay.KDPayApiOP;
import com.rongdu.loans.koudai.api.vo.pay.KDPayQueryApiOP;
import com.rongdu.loans.koudai.common.KDHttpUtils;
import com.rongdu.loans.koudai.common.KDPaySignUtils;
import com.rongdu.loans.koudai.op.pay.KDPayOP;
import com.rongdu.loans.koudai.op.pay.KDPayQueryOP;
import com.rongdu.loans.koudai.vo.pay.KDPayQueryVO;
import com.rongdu.loans.koudai.vo.pay.KDPayVO;

@Service("kdPayApiService")
public class KDPayApiServiceImpl implements KDPayApiService {
	public static final Logger logger = LoggerFactory.getLogger(KDPayApiServiceImpl.class);

	@Override
	public KDPayVO pay(KDPayOP op) {
		String url = Global.getConfig("kd.pay.submitUrl");
		String projectName = Global.getConfig("kd.pay.projectName");
		String pwd = Global.getConfig("kd.pay.password");
		KDPayApiOP apiOP = BeanMapper.map(op, KDPayApiOP.class);
		apiOP.setProject_name(projectName);
		apiOP.setPwd(pwd);
		apiOP.setFee("0");

		Map<String, String> params = BeanMapper.describe(apiOP);
		// 加签
		String sign = KDPaySignUtils.createSign(params, Global.getConfig("kd.pay.signKey"));
		params.put("sign", sign);
		logger.info("加签后字符串：" + sign);

		logger.info("{}-{}-请求地址：{}", "口袋", "放款申请", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "放款申请", JsonMapper.toJsonString(apiOP));
		String responseString = KDHttpUtils.sendPostRequestBody(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "放款申请", responseString);
		KDPayVO vo = (KDPayVO) JsonMapper.fromJsonString(responseString, KDPayVO.class);
		return vo;
	}

	@Override
	public KDPayQueryVO query(KDPayQueryOP op) {
		String url = Global.getConfig("kd.pay.queryUrl");
		String projectName = Global.getConfig("kd.pay.projectName");
		KDPayQueryApiOP apiOP = BeanMapper.map(op, KDPayQueryApiOP.class);
		apiOP.setProject_name(projectName);

		Map<String, String> params = BeanMapper.describe(apiOP);
		// 加签
		String sign = KDPaySignUtils.createSign(params, Global.getConfig("kd.pay.signKey"));
		params.put("sign", sign);
		logger.info("加签后字符串：" + sign);

		logger.info("{}-{}-请求地址：{}", "口袋", "放款查询", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "放款查询", JsonMapper.toJsonString(apiOP));
		String responseString = KDHttpUtils.sendPostRequestBody(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "放款查询", responseString);
		KDPayQueryVO vo = (KDPayQueryVO) JsonMapper.fromJsonString(responseString, KDPayQueryVO.class);
		return vo;
	}
}
