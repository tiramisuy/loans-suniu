package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.baiqishi.message.ContactInfoResponse;
import com.rongdu.loans.baiqishi.service.BaiqishiService;
import com.rongdu.loans.baiqishi.vo.*;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 白骑士反欺诈风险决策引擎-服务实现类
 * 
 * @author sunda
 * @version 2017-07-20
 */
@Service("baiqishiService")
public class BaiqishiServiceImpl extends PartnerApiService implements BaiqishiService {

	/**
	 * 白骑士反欺诈云风险决策
	 */
	public DecisionVO doDecision(DecisionOP op, Map<String, String> extParams) {

		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.decision_biz_code;
		String bizName = BaiqishiConfig.decision_biz_name;
		String url = BaiqishiConfig.decision_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		if (StringUtils.isNotBlank(op.getLoanSuccCount())){
			params.put("appId", "app06");
		} else {
			if (ChannelEnum.JIEDIANQIAN2.getCode().equals(op.getChannelId()) || ChannelEnum.JIEDIANQIAN.getCode().equals(op.getChannelId())){
				params.put("appId", "app11");
			} else if(ChannelEnum.DAWANGDAI.getCode().equals(op.getChannelId()) ||
					ChannelEnum._51JDQ.getCode().equals(op.getChannelId()) ||
					ChannelEnum.YBQB.getCode().equals(op.getChannelId()) ||
					ChannelEnum.CYQB.getCode().equals(op.getChannelId()) ||
					ChannelEnum.CYQBIOS.getCode().equals(op.getChannelId())) {
				params.put("appId", BaiqishiConfig.appId);
			} else {
				// 默认appid
				params.put("appId", "app11");
			}
		}
		params.put("eventType", op.getEventType());

		Map<String, String> bizParams = BeanMapper.describe(op);
		bizParams = removeMapElement(bizParams, "userId", "applyId", "class","loanSuccCount","channelId");
		// 规则引擎的必要参数
		params.putAll(bizParams);
		// 自定义风控规则的字段及数值
		params.putAll(extParams);
		// 请求字符串
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		DecisionVO vo = (DecisionVO) postForJson(url, jsonParamsBody, DecisionVO.class, log);

		return vo;
	}

	/**
	 * 白骑士-资信云报告数据
	 */
	public ReportDataVO getReportData(ReportDataOP op) {
		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.getreport_biz_code;
		String bizName = BaiqishiConfig.getreport_biz_name;
		String url = BaiqishiConfig.getreport_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		Map<String, String> bizParams = BeanMapper.describe(op);
		bizParams = removeMapElement(bizParams, "userId", "applyId", "class");

		params.putAll(bizParams);
		// 请求字符串
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		ReportDataVO vo = (ReportDataVO) postForJson(url, jsonParamsBody, ReportDataVO.class, log);
		return vo;
	}

	/**
	 * 白骑士-给资信云上报额外参数
	 */
	public ReportExtVO reportExt(ReportExtOP op) {
		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.reportext_biz_code;
		String bizName = BaiqishiConfig.reportext_biz_name;
		String url = BaiqishiConfig.reportext_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("contacts", op.getContacts());
		// params.put("verifyKey", BaiqishiConfig.verifyKey);

		Map<String, String> bizParams = BeanMapper.describe(op);
		bizParams = removeMapElement(bizParams, "userId", "applyId", "class", "contacts");

		params.putAll(bizParams);
		// 请求字符串
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		ReportExtVO vo = (ReportExtVO) postForJson(url, jsonParamsBody, ReportExtVO.class, log);
		return vo;
	}

	/**
	 * 查询设备信息
	 * 
	 * @return
	 */
	public DeviceInfoVO getDeviceInfo(DeviceInfoOP op) {

		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.device_info_biz_code;
		String bizName = BaiqishiConfig.device_info_biz_name;
		String url = BaiqishiConfig.device_info_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		if ("1".equals(op.getSource())) {
			params.put("platform", "ios");
		}
		if ("2".equals(op.getSource())) {
			params.put("platform", "android");
		}
		params.put("tokenKey", op.getTokenKey());
		// 发送请求
		DeviceInfoVO vo = (DeviceInfoVO) getForJson(url, params, DeviceInfoVO.class, log);

		return vo;
	}

	/**
	 * 获取手机通讯录信息
	 * 
	 * @return
	 */
	public DeviceContactVO getDeviceContact(DeviceContactOP op) {

		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.contact_info_biz_code;
		String bizName = BaiqishiConfig.contact_info_biz_name;
		String url = BaiqishiConfig.contact_info_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		if ("1".equals(op.getSource())) {
			params.put("platform", "ios");
		}
		if ("2".equals(op.getSource())) {
			params.put("platform", "android");
		}
		params.put("tokenKey", op.getTokenKey());
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		ContactInfoResponse response = (ContactInfoResponse) postForJson(url, jsonParamsBody,
				ContactInfoResponse.class, log);
		DeviceContactVO vo = null;
		// 应答成功
		if (response != null) {
			vo = BeanMapper.map(response, DeviceContactVO.class);
			if (response.isSuccess()) {
				vo.setContactsInfo(response.getResultData().getContactsInfo());
				vo.setCallRecordInfo(response.getResultData().getCallRecordInfo());
				vo.setSmsRecordInfo(response.getResultData().getSmsRecordInfo());
			}
			AutoApproveUtils.removeEmptyContact(vo);
		}
		return vo;
	}

	/**
	 * 获取运营商通讯信息
	 * 
	 * @return
	 */
	public MnoContactVO getMnoContact(MnoContactOP op) {
		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.getoriginal_biz_code;
		String bizName = BaiqishiConfig.getoriginal_biz_name;
		String url = BaiqishiConfig.getoriginal_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		Map<String, String> bizParams = BeanMapper.describe(op);
		bizParams = removeMapElement(bizParams, "userId", "applyId", "class");

		params.putAll(bizParams);
		// 请求字符串
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		MnoContactVO vo = (MnoContactVO) postForJson(url, jsonParamsBody, MnoContactVO.class, log);
		return vo;
	}

	/**
	 * 白骑士-上传查询催收指标用户
	 */
	public CuishouDataVO uploadCuishou(CuishouDataOP op) {
		// 配置参数
		String partnerId = BaiqishiConfig.partner_id;
		String partnerName = BaiqishiConfig.partner_name;
		String bizCode = BaiqishiConfig.uploadcuishou_biz_code;
		String bizName = BaiqishiConfig.uploadcuishou_biz_name;
		String url = BaiqishiConfig.uploadcuishou_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		log.setUserId(op.getUserId());
		log.setApplyId(op.getApplyId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerId", BaiqishiConfig.partnerId);
		params.put("verifyKey", BaiqishiConfig.verifyKey);
		Map<String, String> bizParams = BeanMapper.describe(op);
		bizParams = removeMapElement(bizParams, "userId", "applyId", "class");

		params.putAll(bizParams);
		// 请求字符串
		String jsonParamsBody = JsonMapper.toJsonString(params);
		// 发送请求
		CuishouDataVO vo = (CuishouDataVO) postForJson(url, jsonParamsBody, CuishouDataVO.class, log);
		return vo;
	}

}
