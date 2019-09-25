package com.rongdu.loans.tongrong.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.tongrong.api.op.TRPayApiOP;
import com.rongdu.loans.tongrong.api.service.TRPayApiService;
import com.rongdu.loans.tongrong.op.TRPayOP;
import com.rongdu.loans.tongrong.vo.TRPayVO;

@Service("tRPayApiService")
public class TRPayApiServiceImpl implements TRPayApiService {
	public static final Logger logger = LoggerFactory.getLogger(TRPayApiServiceImpl.class);
	
	@Override
	public TRPayVO pay(TRPayOP op) {
		String url = Global.getConfig("tongrong.pay.url");
		String cid = Global.getConfig("tongrong.pay.account");
		String ckey = Global.getConfig("tongrong.pay.password");
		TRPayApiOP apiOP = BeanMapper.map(op, TRPayApiOP.class);
		apiOP.setCid(cid);
		apiOP.setCkey(ckey);
		String params = JsonMapper.toJsonString(apiOP);
		
		logger.info("{}-{}-请求地址：{}", "通融", "放款申请", url);
		logger.info("{}-{}-请求报文：{}", "通融", "放款申请参数", JsonMapper.toJsonString(apiOP));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "通融", "放款申请", responseString);
		TRPayVO vo = (TRPayVO) JsonMapper.fromJsonString(responseString, TRPayVO.class);
		return vo;
	}

}
