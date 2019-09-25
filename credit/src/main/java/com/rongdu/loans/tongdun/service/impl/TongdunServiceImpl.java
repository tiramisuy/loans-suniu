package com.rongdu.loans.tongdun.service.impl;

import cn.fraudmetrix.riskservice.RuleDetailClient;
import cn.fraudmetrix.riskservice.RuleDetailResult;
import cn.fraudmetrix.riskservice.object.Environment;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.tongdun.common.FraudApiRespCodeUtils;
import com.rongdu.loans.tongdun.common.TongdunConfig;
import com.rongdu.loans.tongdun.service.TongdunService;
import com.rongdu.loans.tongdun.vo.FraudApiOP;
import com.rongdu.loans.tongdun.vo.FraudApiVO;
import com.rongdu.loans.tongdun.vo.RuleDetailOP;
import com.rongdu.loans.tongdun.vo.RuleDetailVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 同盾-反欺诈决策引擎服务
 * @author sunda
 * @version 2017-07-20
 */
@Service("tongdunService")
public class TongdunServiceImpl extends PartnerApiService implements TongdunService {

	/**
	 * 反欺诈决策引擎
	 * @return
	 */
	public FraudApiVO antifraud(FraudApiOP op){
		//配置参数
		String partnerId = TongdunConfig.partner_id;
		String partnerName = TongdunConfig.partner_name;
		String bizCode = TongdunConfig.antifraud_biz_code;
		String bizName = TongdunConfig.antifraud_biz_name;
		String url = TongdunConfig.antifraud_url;
		String fee = TongdunConfig.antifraud_fee;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setFee(fee);

		Map<String, String> params = new HashMap<String, String>();
		//公共参数
		// 此处值填写您的合作方标识
		params.put("partner_code", TongdunConfig.partner_code);
		// 此处填写对应app密钥
		if (op.isAndroid()){
			params.put("secret_key", TongdunConfig.android_secret_key);
			// 此处填写策略集上的事件标识
			params.put("event_id", TongdunConfig.android_event_id);
		}
		if (op.isIOS()){
			params.put("secret_key", TongdunConfig.ios_secret_key);
			// 此处填写策略集上的事件标识
			params.put("event_id", TongdunConfig.ios_event_id);
		}
//		//此处填写移动端sdk采集到的信息black_box
		params.put("black_box", op.getBlackBox());
//		//以下填写其他要传的参数，比如系统字段，扩展字段
//		params.put("account_login", "your_login_name");
//		//终端IP地址
		params.put("ip_address", op.getIp());

		String name = op.getName();
		String idNo = op.getIdNo();
		String mobile = op.getMobile();
		// 业务参数
		params.put("account_name", name);
		params.put("id_number", idNo);
		params.put("account_mobile", mobile);
		//发送请求
		FraudApiVO vo = (FraudApiVO) postForJson(url,params,FraudApiVO.class,log);
		return vo;
	}


	/**
	 * 命中规则详情查询
	 * @return
	 */
	public RuleDetailVO getRuleDetail(RuleDetailOP op){

		//配置参数
		String partnerId = TongdunConfig.partner_id;
		String partnerName = TongdunConfig.partner_name;
		String bizCode = TongdunConfig.rule_detail_biz_code;
		String bizName = TongdunConfig.rule_detail_biz_name;
		String url = TongdunConfig.rule_detail_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		long start = System.currentTimeMillis();
		String requestString = JsonMapper.toJsonString(op);
		logger.debug("{}-{}-请求报文：{}",partnerName,bizName,requestString);
		String responseString= "";
		RuleDetailResult result = null;
		try {
			// 填写参数
			String partnerCode = TongdunConfig.partner_code;
			String partnerKey = TongdunConfig.partner_key;
			// Environment.PRODUCT表示调用生产环境, 测试环境请修改为Environment.SANDBOX
			Environment env = null;
			if ("SANDBOX".equals(TongdunConfig.env)){
				env = Environment.SANDBOX;
			}
			if ("PRODUCT".equals(TongdunConfig.env)){
				env = Environment.PRODUCT;
			}
			// 调用接口
			RuleDetailClient client = RuleDetailClient.getInstance(partnerCode, env);
			result = client.execute(partnerKey, op.getSequenceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("{}-{}-应答结果：{}",partnerName,bizName,requestString);

		RuleDetailVO vo = BeanMapper.map(result,RuleDetailVO.class);
		String code = vo.getCode();
		logger.debug("{}-{}-调用结果：{}，{}，{}",partnerName,bizName,code, vo.getMsg(), FraudApiRespCodeUtils.getApiSolution(code));
		if (vo.isSuccess()){

		}

		long end = System.currentTimeMillis();
		long costTime = end-start;
		log.setCostTime(costTime);
		log.setInvokeTime(new java.sql.Date(start));
		log.setSuccess(vo.isSuccess());
		log.setCode(vo.getCode());
		log.setMsg(vo.getMsg());
		log.setUrl(url);
		log.setReqContent(requestString);
		log.setSyncRespContent(responseString);
		saveApiInvokeLog(log);
		return vo;
	}


	
}
