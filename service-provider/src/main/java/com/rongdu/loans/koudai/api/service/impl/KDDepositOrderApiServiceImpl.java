package com.rongdu.loans.koudai.api.service.impl;

import com.google.common.collect.Lists;
import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.UnicodeUtil;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.koudai.api.service.KDDepositOrderApiService;
import com.rongdu.loans.koudai.api.vo.deposit.*;
import com.rongdu.loans.koudai.common.DesHelper;
import com.rongdu.loans.koudai.common.KdaiSignUtils;
import com.rongdu.loans.koudai.op.deposit.*;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("kDDepositOrderApiService")
public class KDDepositOrderApiServiceImpl implements KDDepositOrderApiService {
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	String platNo = Global.getConfig("kd.deposit.platNo");
	String signKey = Global.getConfig("kd.deposit.signKey");
	String desKey = Global.getConfig("kd.deposit.desKey");
	String url = Global.getConfig("kd.deposit.url");
	String uploadurl = Global.getConfig("kd.upload.url");

	@Override
	public KDCreateOrderLendPayResultVO createOrderLendPay(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			logger.error("申请id不能为空");
			throw new IllegalArgumentException("申请id不能为空");
		}

		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		CustUser custUser = custUserManager.getById(loanApply.getUserId());

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositCreateOrderLendPayOP pack = new KDDepositCreateOrderLendPayOP();

		KDUserBaseOP userBase = new KDUserBaseOP();
		userBase.setIdNumber(custUser.getIdNo());

		pack.setUserBase(userBase);

		KDOrderBaseOP kdOrderBaseOP = new KDOrderBaseOP();
		kdOrderBaseOP.setOutTradeNo(applyId);
		// 贷款金额，单位：分
		kdOrderBaseOP.setMoneyAmount(loanApply.getApplyAmt().multiply(BigDecimal.valueOf(100)).intValue());
		// 贷款方式(0:按天,1:按月,2:按年) M-月、Q-季、Y-年、D-天
		kdOrderBaseOP.setLoanMethod(loanApply.getRepayFreq().equals("D") ? 0
				: loanApply.getRepayFreq().equals("M") ? 1
						: loanApply.getRepayFreq().equals("Q") ? 1 : loanApply.getRepayFreq().equals("Y") ? 2 : -1);
		kdOrderBaseOP
				.setLoanTerm(loanApply.getRepayFreq().equals("Q") ? (3 * loanApply.getTerm()) : loanApply.getTerm());
		kdOrderBaseOP.setLoanInterests(loanApply.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
		kdOrderBaseOP.setApr(loanApply.getActualRate().multiply(BigDecimal.valueOf(100)).floatValue());
		kdOrderBaseOP.setOrderTime(currentTime);
		kdOrderBaseOP.setCounterFee(loanApply.getServFee().multiply(BigDecimal.valueOf(100)).intValue());

		pack.setOrderBase(kdOrderBaseOP);

		KDRepaymentBaseOP kdRepaymentBaseOP = new KDRepaymentBaseOP();

		kdRepaymentBaseOP.setRepaymentType(2);

		LoanRepayPlan loanRepayPlan = loanRepayPlanManager.getByApplyId(applyId);
		kdRepaymentBaseOP
				.setRepaymentAmount(loanRepayPlan.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP.setRepaymentTime(Long.valueOf(loanRepayPlan.getLoanEndDate().getTime() / 1000).intValue());
		kdRepaymentBaseOP.setPeriod(kdOrderBaseOP.getLoanTerm());
		kdRepaymentBaseOP
				.setRepaymentPrincipal(loanRepayPlan.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
		kdRepaymentBaseOP
				.setRepaymentInterest(loanRepayPlan.getInterest().multiply(BigDecimal.valueOf(100)).intValue());

		pack.setRepaymentBase(kdRepaymentBaseOP);

		List<KDPeriodBaseOP> kdPeriodBaseOPList = new ArrayList<>();

		/** 获取所有明细 */
		List<RepayPlanItem> repayPlanItemList = repayPlanItemManager.getByApplyId(applyId);
		KDPeriodBaseOP preBaseOP = null;
		for (RepayPlanItem repayPlanItem : repayPlanItemList) {
			preBaseOP = new KDPeriodBaseOP();
			preBaseOP.setPeriod(repayPlanItem.getThisTerm());
			preBaseOP
					.setPlanRepaymentMoney(repayPlanItem.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP.setPlanRepaymentTime(Long.valueOf(repayPlanItem.getRepayDate().getTime() / 1000).intValue());
			preBaseOP.setPlanRepaymentPrincipal(
					repayPlanItem.getPrincipal().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP
					.setPlanRepaymentInterest(repayPlanItem.getInterest().multiply(BigDecimal.valueOf(100)).intValue());
			preBaseOP.setApr(kdOrderBaseOP.getApr());

			kdPeriodBaseOPList.add(preBaseOP);
		}

		pack.setPeriodBase(kdPeriodBaseOPList);

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("createOrderLendPay");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "推单", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "推单", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "推单", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "推单", responseString);
		KDCreateOrderLendPayResultVO resultVO = (KDCreateOrderLendPayResultVO) JsonMapper.fromJsonString(responseString,
				KDCreateOrderLendPayResultVO.class);

		return resultVO;
	}

	@Override
	public KDWithdrawResultVO withdraw(KDDepositWithdrawOP pack) {
		// TODO Auto-generated method stub

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("withdraw");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "提现", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "提现", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "提现", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "提现", responseString);
		KDWithdrawResultVO resultVO = (KDWithdrawResultVO) JsonMapper.fromJsonString(responseString,
				KDWithdrawResultVO.class);

		return resultVO;
	}

	@Override
	public KDDepositResultVO orderCancel(String applyId) {

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositWithdrawOP pack = new KDDepositWithdrawOP();

		pack.setOutTradeNo(applyId);

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("orderCancel");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "取消订单", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "取消订单", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "取消订单", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "取消订单", responseString);
		KDDepositResultVO resultVO = (KDDepositResultVO) JsonMapper.fromJsonString(responseString,
				KDDepositResultVO.class);

		return resultVO;
	}

	@Override
	public KDComprehensiveResultVO comprehensive(KDDepositComprehensiveOP pack) {

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("comprehensive");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		String encodeParam = encodeParam(pack);
		String desPack = DesHelper.desEncrypt(desKey, encodeParam);// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "综合页接口", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "综合页接口", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "综合页接口", JsonMapper.toJsonString(pack));
		logger.info("{}-{}-请求报文编码后data：{}", "口袋", "综合页接口", encodeParam);
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-订单号：{}-应答结果：{}", "口袋", "综合页接口", pack.getOrderBase().getOutTradeNo(), responseString);
		KDComprehensiveResultVO resultVO = (KDComprehensiveResultVO) JsonMapper.fromJsonString(responseString,
				KDComprehensiveResultVO.class);

		return resultVO;
	}

	@Override
	public KDDepositResultVO pushAssetRepaymentPeriod(KDPushAssetRepaymentPeriodOP pack) {
		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("pushAssetRepaymentPeriod");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		// String encodeParam = encodeParam(pack);
		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "创建债权还款计划", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "创建债权还款计划", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "创建债权还款计划", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "创建债权还款计划", responseString);
		KDDepositResultVO resultVO = (KDDepositResultVO) JsonMapper.fromJsonString(responseString,
				KDDepositResultVO.class);

		return resultVO;
	}

	/**
	 * @return String json
	 * @throws @Title:
	 *             encodeParam
	 * @Description: 对bean值进行urlencode编码
	 */
	private String encodeParam(Object param) {
		param = JsonMapper.toJsonString(param);// 去掉null值
		JSONObject packJson = JSONObject.fromObject(param);// JSONObject.fromObject(param);
		encodeJsonParam(packJson);

		return packJson.toString().replace("\\\\u", "\\u");
	}

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             encodeParam
	 * @Description:
	 */
	private void encodeJsonParam(Object json) {
		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) json;

			for (Object key : jsonObject.keySet()) {
				if (jsonObject.get(key) == null) {
					continue;
				} else if (jsonObject.get(key) instanceof String) {
					if (jsonObject.get(key).toString().trim().length() > 0) {// 过滤空字符串
						jsonObject.put(key, UnicodeUtil.cnToUnicode(jsonObject.get(key).toString()));
					}
				} else if (jsonObject.get(key) instanceof JSON) {
					encodeJsonParam(jsonObject.get(key));
				}
			}

		} else if (json instanceof JSONArray) {
			for (Object iteam : (JSONArray) json) {
				encodeJsonParam(iteam);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getContract(String applyId) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");
		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("queryContract");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		Map<String, Object> pack = new HashMap<>();
		pack.put("outTradeNo", applyId);
		pack.put("regenerate", 1);
		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "查询合同", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "查询合同", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "查询合同", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "查询合同", responseString);
		Map<String, Object> result = (Map<String, Object>) JsonMapper.fromJsonString(responseString, Map.class);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryStatus(String applyId) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");
		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("queryOrderWithdrawStatus");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		Map<String, Object> pack = new HashMap<>();
		pack.put("outTradeNo", applyId);
		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "查询放款状态", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "查询放款状态", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "查询放款状态", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "口袋", "查询放款状态", applyId, responseString);
		Map<String, Object> result = (Map<String, Object>) JsonMapper.fromJsonString(responseString, Map.class);
		return result;
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void pushData() {
		logger.info("====口袋债权推送开始执行====");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
				"select * from loan_apply  a where a.pay_channel='3' and a.apply_status='1' and a.update_time>=DATE_SUB(curdate(),INTERVAL 2 DAY) and a.update_time<=DATE_SUB(curdate(),INTERVAL 1 DAY) ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(stringBuffer.toString());
		for (Map<String, Object> map : list) {
			String status = pushRepalyData(String.valueOf(map.get("id")));
			logger.info("====口袋债权推送===={},{}", map.get("id"), status);
		}
		logger.info("====口袋债权推送结束执行====");
	}

	public String pushRepalyData(String orderNo) {
		List<RepayPlanItem> repayPlanItemList = repayPlanItemManager.getByApplyId(orderNo);

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();
		List<KDRepaymentsub> kdRepaymentsubList = new ArrayList<>();
		for (RepayPlanItem repayPlanItem : repayPlanItemList) {
			if (repayPlanItem.getActualRepayTime() != null) {
				KDRepaymentsub kdRepaymentsub = new KDRepaymentsub();

				Integer period = repayPlanItem.getThisTerm();
				Integer trueRepaymentTime = (int) (repayPlanItem.getActualRepayTime().getTime() / 1000);
				Integer truePrincipal = (repayPlanItem.getPayedPrincipal().intValue() - 300) * 100;
				Integer trueInterest = truePrincipal * 18 / 100 / 360 * 14;
				Integer remissionAmount = repayPlanItem.getDeduction().intValue() * 100;
				Integer overdueFee = repayPlanItem.getOverdueFee().intValue();
				Integer repayStatus = 1;
				if (overdueFee > 0) {
					repayStatus = 5;
				}
				kdRepaymentsub.setPeriod(period);
				kdRepaymentsub.setTrueRepaymentTime(trueRepaymentTime);
				kdRepaymentsub.setTruePrincipal(truePrincipal);
				kdRepaymentsub.setTrueInterest(trueInterest);
				kdRepaymentsub.setRemissionAmount(remissionAmount);
				kdRepaymentsub.setRepayStatus(repayStatus);
				kdRepaymentsub.setRepaymentType(1);
				kdRepaymentsubList.add(kdRepaymentsub);
			}
		}

		KDSubscribes kdSubscribes = new KDSubscribes();
		kdSubscribes.setOutTradeNo(orderNo);
		kdSubscribes.setRepaymentSub(kdRepaymentsubList);
		List<KDSubscribes> list = Lists.newArrayList();
		list.add(kdSubscribes);
		String jsonString1 = com.alibaba.fastjson.JSONObject.toJSONString(list);

		Map<String, String> params = new HashedMap();
		params.put("platNo", "kdlc_jbqbdep");
		params.put("subscribes", jsonString1);
		params.put("txTime", String.valueOf(currentTime));
		String sign = KdaiSignUtils.createSignURL(params, signKey);
		params.put("sign", sign);
		String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(params);
		jsonString = jsonString.replace("\\", "").replace("\"[", "[").replace("]\"", "]");
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(uploadurl, jsonString);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseString);
		String retCode = String.valueOf(jsonObject.get("retCode"));
		return retCode;
	}

	/**
	 * @return KDComprehensiveResultVO 返回类型
	 * @throws @Title:
	 *             queryComprehensive
	 * @Description: 综合页查询
	 */
	@Override
	public KDQueryComprehensiveVO queryComprehensive(String applyId) {

		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("queryComprehensive");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		Map<String, Object> pack = new HashMap<>();
		pack.put("outTradeNo", applyId);

		String encodeParam = encodeParam(pack);
		String desPack = DesHelper.desEncrypt(desKey, encodeParam);// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "综合页查询接口", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "综合页查询接口", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "综合页查询接口", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-{}-应答结果：{}", "口袋", "综合页查询接口", applyId, responseString);
		KDQueryComprehensiveVO resultVO = (KDQueryComprehensiveVO) JsonMapper.fromJsonString(responseString,
				KDQueryComprehensiveVO.class);

		return resultVO;
	}

	@Override
	public Map<String, Object> queryAccount(String idNo) {
		String platNo = Global.getConfig("kd.deposit.platNo");
		String signKey = Global.getConfig("kd.deposit.signKey");
		String desKey = Global.getConfig("kd.deposit.desKey");
		String url = Global.getConfig("kd.deposit.url");
		int currentTime = new Long(System.currentTimeMillis() / 1000).intValue();

		KDDepositOP depositOP = new KDDepositOP();
		depositOP.setVersion("2.0");
		depositOP.setTxCode("queryAccountOpenDetail");
		depositOP.setPlatNo(platNo);
		depositOP.setTxTime(currentTime);

		Map<String, Object> pack = new HashMap<>();
		pack.put("idNumber", idNo);
		String desPack = DesHelper.desEncrypt(desKey, JsonMapper.toJsonString(pack));// 加密json
		depositOP.setPack(desPack);

		Map<String, String> params = BeanMapper.describeStringValue(depositOP);

		String sign = KdaiSignUtils.createSign(params, signKey);
		params.put("sign", sign);

		String reqJson = JsonMapper.toJsonString(params);

		logger.info("{}-{}-请求地址：{}", "口袋", "查询口袋开户信息", url);
		logger.info("{}-{}-请求报文：{}", "口袋", "查询口袋开户信息", reqJson);
		logger.info("{}-{}-请求报文data：{}", "口袋", "查询口袋开户信息", JsonMapper.toJsonString(pack));
		String responseString = RestTemplateUtils.getInstance().postForJsonRaw(url, params);
		logger.info("{}-{}-应答结果：{}", "口袋", "查询口袋开户信息", responseString);
		Map<String, Object> result = (Map<String, Object>) JsonMapper.fromJsonString(responseString, Map.class);
		return result;
	}

}
