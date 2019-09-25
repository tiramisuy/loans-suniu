package com.rongdu.loans.hanjs.api.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.hanjs.api.service.HanJSApiUserService;
import com.rongdu.loans.hanjs.op.*;
import com.rongdu.loans.hanjs.util.MD5Util;
import com.rongdu.loans.hanjs.vo.HanJSPushHeadVO;
import com.rongdu.loans.hanjs.vo.HanJSPushResultVO;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("hanJSApiUserService")
public class HanJSApiUserServiceImpl implements HanJSApiUserService {

	private static final String channelId = "jbqb";
	private static final String key = Global.getConfig("hanjs.common.key");
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public HanJSResultVO openAccount(HanJSUserOP op) {

		String url = Global.getConfig("hanjs.openAccount.url");
		String datetime = DateUtils.getDate("yyyyMMddHHmmss");

		// 获取sign
		op.setChannelId(channelId);
		op.setDatetime(datetime);
		op.setSign_success_url(Global.getConfig("hanjs.sign.success.url") + "?mobile=" + op.getMobile());
		op.setSign_fail_url(Global.getConfig("hanjs.sign.fail.url") + "?mobile=" + op.getMobile());
		Map<String, Object> map = BeanMapper.map(op, Map.class);
		map.put("key", key);
		String sign = MD5Util.getMd5Sign(map);

		// 配置请求参数
		HanJSOpenAccountOP param = new HanJSOpenAccountOP();
		BeanMapper.copy(op, param);
		param.setSign(sign);
		Map<String, String> params = BeanMapper.map(param, Map.class);

		logger.info("{}-{}-请求地址：{}", "汉金所", "开户接口", url);
		logger.info("{}-{}-请求报文data：{}", "汉金所", "开户接口", JsonMapper.toJsonString(param));
		String responseString = RestTemplateUtils.getInstance().postForJson(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "汉金所", "开户接口", op.getMobile(), responseString);

		HanJSResultVO result = new HanJSResultVO();
		if (null != responseString) {
			if (StringUtils.startsWith(responseString, "<!DOCTYPE")) {
				result.setCode(Global.BANKDEPOSIT_RESULT_APP);
				result.setData(responseString);
			} else {
				Map<String, String> response = (Map<String, String>) JsonMapper.fromJsonString(responseString,
						Map.class);
				if (null != response && StringUtils.isNotBlank(response.get("code"))) {
					result.setCode("FAIL");
					result.setMessage(response.get("message"));
				}
			}
			return result;
		} else {
			result.setCode("FAIL");
			result.setMessage("开户失败");
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HanJSResultVO withdraw(HanJSWithdrawOP op) {
		String url = Global.getConfig("hanjs.withdraw.url");
		String datetime = DateUtils.getDate("yyyyMMddHHmmss");

		// 获取秘钥
		op.setChannelId(channelId);
		op.setDatetime(datetime);
		op.setRetUrl(Global.getConfig("hanjs.ret.url") + "?mobile=" + op.getMobile());
		op.setPassResetRetUrl(Global.getConfig("hanjs.passResetRet.url"));
		Map<String, Object> map = BeanMapper.map(op, Map.class);
		map.put("key", key);
		String sign = MD5Util.getMd5Sign(map);

		// 配置请求参数
		HanJSApiWithdrawOP param = new HanJSApiWithdrawOP();
		BeanMapper.copy(op, param);
		param.setSign(sign);
		Map<String, String> params = BeanMapper.map(param, Map.class);

		logger.info("{}-{}-请求地址：{}", "汉金所", "提现接口", url);
		logger.info("{}-{}-请求报文data：{}", "汉金所", "提现接口", JsonMapper.toJsonString(param));
		String responseString = RestTemplateUtils.getInstance().postForJson(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "汉金所", "提现接口", op.getOrderId(), responseString);

		HanJSResultVO result = new HanJSResultVO();
		if (null != responseString) {
			if (StringUtils.startsWith(responseString, "<!DOCTYPE")) {
				result.setCode(Global.BANKDEPOSIT_RESULT_APP);
				result.setData(responseString);
			} else {
				Map<String, String> response = (Map<String, String>) JsonMapper.fromJsonString(responseString,
						Map.class);
				if (null != response && StringUtils.isNotBlank(response.get("code"))) {
					result.setCode("FAIL");
					result.setMessage(response.get("message"));
				}
			}
			return result;
		} else {
			result.setCode("FAIL");
			result.setMessage("提现失败");
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HanJSResultVO pushBid(HanJSOrderOP op) {
		String url = Global.getConfig("hanjs.pushBid.url");
		String datetime = DateUtils.getDate("yyyyMMddHHmmss");

		// 获取sign
		op.setChannelId(channelId);
		op.setDatetime(datetime);
		Map<String, Object> map = BeanMapper.map(op, Map.class);
		map.put("key", key);
		String sign = MD5Util.getMd5Sign(map);

		// 配置参数
		HanJSApiOrderOP param = new HanJSApiOrderOP();
		BeanMapper.copy(op, param);
		param.setSign(sign);
		Map<String, String> params = BeanMapper.map(param, Map.class);

		// 发起请求
		logger.info("{}-{}-请求地址：{}", "汉金所", "推标接口", url);
		logger.info("{}-{}-请求报文data：{}", "汉金所", "推标接口", JsonMapper.toJsonString(param));
		String responseString = RestTemplateUtils.getInstance().postForJson(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "汉金所", "推标接口", op.getOrderId(), responseString);

		HanJSResultVO result = new HanJSResultVO();
		if (null != responseString) {
			HanJSPushResultVO vo = (HanJSPushResultVO) JsonMapper.fromJsonString(responseString,
					HanJSPushResultVO.class);
			HanJSPushHeadVO head = vo.getHead();
			if (null != head && "0000".equals(head.getCode()) && "success".equals(head.getRetCode())) {
				result.setCode(Global.BANKDEPOSIT_RESULT_APP);
				result.setMessage(head.getMsg());
			} else {
				result.setCode("FAIL");
				result.setMessage("推标失败");
			}
		} else {
			result.setCode("FAIL");
			result.setMessage("推标失败");
		}
		return result;
	}

	public HanJSResultVO queryOrderState(QueryOrderStateOP op) {

		String url = Global.getConfig("hanjs.queryOrderState.url");
		String datetime = DateUtils.getDate("yyyyMMddHHmmss");

		op.setChannelId(channelId);
		op.setDatetime(datetime);
		Map<String, Object> map = BeanMapper.map(op, Map.class);
		map.put("key", key);
		String sign = MD5Util.getMd5Sign(map);
		map.put("sign", sign);

		String jsonStr = JsonMapper.toJsonString(map);
		Map<String, String> params = (Map<String, String>) JsonMapper.fromJsonString(jsonStr, Map.class);

		logger.info("{}-{}-请求地址：{}", "汉金所", "查询订单状态", url);
		logger.info("{}-{}-请求报文data：{}", "汉金所", "查询订单状态", jsonStr);
		String responseString = RestTemplateUtils.getInstance().postForJson(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "汉金所", "查询订单状态", op.getOrderId(), responseString);

		HanJSResultVO result = (HanJSResultVO) JsonMapper.fromJsonString(responseString, HanJSResultVO.class);
		return result;
	}
}
