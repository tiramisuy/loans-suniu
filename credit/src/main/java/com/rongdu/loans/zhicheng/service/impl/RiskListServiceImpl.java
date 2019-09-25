package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.zhicheng.common.ZhichengcreditConfig;
import com.rongdu.loans.zhicheng.message.RiskListRequest;
import com.rongdu.loans.zhicheng.vo.RiskListOP;
import com.rongdu.loans.zhicheng.vo.RiskListVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *宜信致诚风险名单 -服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("riskListService")
public class RiskListServiceImpl extends PartnerApiService{
	
	/**
	 * 查询风险名单
	 * @return
	 */
	public RiskListVO queryMixedRiskList(RiskListOP op) {
		//配置参数
		String partnerId = ZhichengcreditConfig.partner_id;
		String partnerName = ZhichengcreditConfig.partner_name;
		String bizCode = ZhichengcreditConfig.mixed_risk_list_biz_code;
		String bizName = ZhichengcreditConfig.mixed_risk_list_biz_name;
		String url = ZhichengcreditConfig.mixed_risk_list_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		Map<String,String>  params = new HashMap<String,String>();
		String input = ZhichengcreditConfig.userId+ZhichengcreditConfig.password;
		String sign = Digests.md5(input);
		params.put("userid",ZhichengcreditConfig.userId);
		params.put("sign", sign);
		RiskListRequest request = new RiskListRequest();
		request.setData(op);
		//请求字符串
		String jsonParams  = JsonMapper.toJsonString(request);	
		params.put("params", jsonParams);
		//发送请求
		RiskListVO vo = (RiskListVO) getForJson(url,params,RiskListVO.class,log);
		return vo;
	}
	
}
