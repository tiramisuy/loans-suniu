package com.rongdu.loans.koudai.api.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.koudai.api.service.KDDepositUserApiService;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDOpenAccountPageApiDataOP;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDOpenAccountPageApiOP;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDOpenAccountQueryApiDataOP;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDOpenAccountQueryApiOP;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDPwdResetApiDataOP;
import com.rongdu.loans.koudai.api.vo.deposit.user.KDPwdResetApiOP;
import com.rongdu.loans.koudai.common.DesHelper;
import com.rongdu.loans.koudai.common.KdaiSignUtils;
import com.rongdu.loans.koudai.common.MyCharUtils;
import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountOP;
import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountQueryOP;
import com.rongdu.loans.koudai.op.deposit.user.KDPwdResetOP;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountPageVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountQueryResultVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDPwdResetVO;

@Service("kdDepositUserApiService")
public class KDDepositUserApiServiceImpl implements KDDepositUserApiService {
	public static final Logger logger = LoggerFactory.getLogger(KDDepositUserApiServiceImpl.class);

	// {"retCode":0,"retData":{"accountId":"6212461620000000101","bindCard":"6236682870008974276","isBindCard":1,"mobile":"13277091298","bankId":7,"bankName":"建设银行","idNo":"421003199210134023"},"retMsg":"操作成功","retTraceId":"19c4785e15ae10c2fa782b51fb490ed2","version":"2.0","txCode":"queryAccountOpenDetail","sign":"f0a977a53f836ce3a24d0cb8282ed79f"}
	public KDOpenAccountQueryResultVO queryAccountOpenDetail(KDOpenAccountQueryOP op) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");

		KDOpenAccountQueryApiOP apiOP = new KDOpenAccountQueryApiOP();
		apiOP.setPlatNo(platNo);

		KDOpenAccountQueryApiDataOP dataOP = BeanMapper.map(op, KDOpenAccountQueryApiDataOP.class);
		String pack = JsonMapper.toJsonString(dataOP);
		String desPack = DesHelper.desEncrypt(desKey, pack);
		apiOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describe(apiOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		logger.info("{}-{}-请求地址：{}", "口袋", "开户信息查询", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "开户信息查询", JsonMapper.toJsonString(params));
		logger.info("{}-{}-请求报文data：{}", "口袋", "开户信息查询", JsonMapper.toJsonString(dataOP));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "开户信息查询", responseString);
		KDOpenAccountQueryResultVO vo = (KDOpenAccountQueryResultVO) JsonMapper.fromJsonString(responseString,
				KDOpenAccountQueryResultVO.class);
		return vo;
	}

	// {"retCode":0,"retData":{"url":"http://test.asset.koudailc.com/asset-page/to/account-open-new?key=19787dee9fe0a2d1639885d27867f7f8"},"retMsg":"操作成功","retTraceId":"640a9728a7ba9afa08d7cf9e9014e533","version":"2.0","txCode":"accountOpenEncryptPage","sign":"494927b8afc9703c669167a82c38a2fc"}
	@Override
	public KDOpenAccountPageVO accountOpenEncryptPage(KDOpenAccountOP op) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");
		String accountOpenRetURL = Global.getConfig("kd.deposit.accountOpenRetURL");
		String accountOpenCallbackURL = Global.getConfig("kd.deposit.accountOpenCallbackURL");

		KDOpenAccountPageApiOP apiOP = new KDOpenAccountPageApiOP();
		apiOP.setPlatNo(platNo);

		KDOpenAccountPageApiDataOP dataOP = BeanMapper.map(op, KDOpenAccountPageApiDataOP.class);
		dataOP.setRetUrl(accountOpenRetURL);
		dataOP.setNotifyUrl(accountOpenCallbackURL);
		String pack = JsonMapper.toJsonString(dataOP);
		pack = MyCharUtils.chinese2Unicode(pack);
		String desPack = DesHelper.desEncrypt(desKey, pack);
		apiOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describe(apiOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		logger.info("{}-{}-请求地址：{}", "口袋", "存管开户", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "存管开户", JsonMapper.toJsonString(params));
		logger.info("{}-{}-请求报文data：{}", "口袋", "存管开户", JsonMapper.toJsonString(dataOP));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "存管开户", responseString);
		KDOpenAccountPageVO vo = (KDOpenAccountPageVO) JsonMapper.fromJsonString(responseString,
				KDOpenAccountPageVO.class);
		return vo;
	}
//{"retCode":0,"retData":{"url":"http://test.asset.koudailc.com/asset-page/to/account-open-new?key=e41e3a767a4507ce3754a832eaebe3df"},"retMsg":"操作成功","retTraceId":"7a6ad1dd7e43e7ca2e2933d11b4741b1","version":"2.0","txCode":"passwordResetPage","sign":"0ab732e5839b4ed66c5a03d0339c569a"}
	@Override
	public KDPwdResetVO passwordResetPage(KDPwdResetOP op) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");
		String pwdResetRetURL = Global.getConfig("kd.deposit.pwdResetRetURL");

		KDPwdResetApiOP apiOP = new KDPwdResetApiOP();
		apiOP.setPlatNo(platNo);

		KDPwdResetApiDataOP dataOP = BeanMapper.map(op, KDPwdResetApiDataOP.class);
		dataOP.setRetUrl(pwdResetRetURL);
		String pack = JsonMapper.toJsonString(dataOP);
		pack = MyCharUtils.chinese2Unicode(pack);
		String desPack = DesHelper.desEncrypt(desKey, pack);
		apiOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describe(apiOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		logger.info("{}-{}-请求地址：{}", "口袋", "存管重置密码", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "存管重置密码", JsonMapper.toJsonString(params));
		logger.info("{}-{}-请求报文data：{}", "口袋", "存管重置密码", JsonMapper.toJsonString(dataOP));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "存管重置密码", responseString);
		KDPwdResetVO vo = (KDPwdResetVO) JsonMapper.fromJsonString(responseString, KDPwdResetVO.class);
		return vo;
	}
}
