/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.service.BaiqishiService;
import com.rongdu.loans.baiqishi.vo.*;
import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.basic.vo.AreaVO;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.credit.baiqishi.service.ZhimaService;
import com.rongdu.loans.credit.baiqishi.vo.*;
import com.rongdu.loans.credit.common.RedisPrefix;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.credit100.common.ApiRespCode;
import com.rongdu.loans.credit100.service.Credit100Service;
import com.rongdu.loans.credit100.vo.ApplyLoanMonOP;
import com.rongdu.loans.credit100.vo.ApplyLoandOP;
import com.rongdu.loans.credit100.vo.ApplyLoandVO;
import com.rongdu.loans.credit100.vo.SpecialListCOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.kdcredit.service.KDService;
import com.rongdu.loans.kdcredit.vo.KDBlackListOP;
import com.rongdu.loans.kdcredit.vo.KDBlackListVO;
import com.rongdu.loans.linkface.service.LinkfaceService;
import com.rongdu.loans.linkface.vo.IdnumberVerificationOP;
import com.rongdu.loans.linkface.vo.IdnumberVerificationVO;
import com.rongdu.loans.loan.option.dwd.ChargeInfo;
import com.rongdu.loans.loan.option.dwd.DWDAdditionInfo;
import com.rongdu.loans.loan.option.dwd.DWDBaseInfo;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rong360Model.OrderBaseInfo;
import com.rongdu.loans.loan.option.rongTJreportv1.RongTJResp;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.RiskContants;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.risk.service.RiskRuleService;
import com.rongdu.loans.tencent.service.TencentAntiFraudService;
import com.rongdu.loans.tencent.vo.AntiFraudOP;
import com.rongdu.loans.tencent.vo.AntiFraudVO;
import com.rongdu.loans.thirdpartycredit1.service.ThirdPartyCredit1Service;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListOP;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListVO;
import com.rongdu.loans.tlblackrisk.op.TongLianBlackOP;
import com.rongdu.loans.tlblackrisk.service.TongLianBlackService;
import com.rongdu.loans.tlblackrisk.vo.TongLianBlackVO;
import com.rongdu.loans.tongdun.service.TongdunService;
import com.rongdu.loans.tongdun.vo.FraudApiOP;
import com.rongdu.loans.tongdun.vo.FraudApiVO;
import com.rongdu.loans.tongdun.vo.RuleDetailOP;
import com.rongdu.loans.tongdun.vo.RuleDetailVO;
import com.rongdu.loans.xinyan.service.XinyanService;
import com.rongdu.loans.xinyan.vo.*;
import com.rongdu.loans.zhicheng.entity.EchoDecisionReport;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreen;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreenRiskResult;
import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;
import com.rongdu.loans.zhicheng.message.LoanRecord;
import com.rongdu.loans.zhicheng.service.*;
import com.rongdu.loans.zhicheng.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 现金贷自动审批所需的公共数据服务
 * 
 * @author sunda
 * @version 2017-08-14
 */
@Service("creditDataInvokeService")
public class CreditDataInvokeServiceImpl extends BaseService implements CreditDataInvokeService {
	@Autowired
	public LoanApplyService loanApplyService;
	@Autowired
	public CustUserService userService;
	@Autowired
	public Credit100Service credit100Service;
	@Autowired
	public ZhichengService zhichengService;
	@Autowired
	public TongdunService tongdunService;
	@Autowired
	public BaiqishiService baiqishiService;
	@Autowired
	public RiskRuleService riskRuleService;
	@Autowired
	public ZhimaService zhimaService;
	@Autowired
	public TencentAntiFraudService tencentAntiFraudService;
	@Autowired
	public KDService kdService;
	@Autowired
	public OverdueService overdueService;
	@Autowired
	public ThirdPartyCredit1Service thirdPartyCredit1Service;
	@Autowired
	public XinyanService xinyanService;
	@Autowired
	private LinkfaceService linkfaceService;
	@Autowired
	private RongService rongService;
	@Autowired
	private JDQService jdqService;
	@Autowired
	private DWDService dwdService;
	@Autowired
	private SLLService sllService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;
	@Autowired
	private EchoLoanRecordService echoLoanRecordService;
	@Autowired
	private EchoDecisionReportService echoDecisionReportService;
	@Autowired
	private EchoFraudScreenService echoFraudScreenService;
	@Autowired
	private EchoFraudScreenRiskResultService echoFraudScreenRiskResultService;
	@Autowired
	private TongLianBlackService tongLianBlackService;

	// 自动审批时加载的数据默认保存1小时
	public int HOUR_1 = 1 * 60 * 60;
	public int DAY_1 = 24 * 60 * 60;
	public int DAY_7 = 7 * 24 * 60 * 60;

	/**
	 * 获得某个特定的风控规则
	 *
	 * @param ruleCode
	 * @param modelId
	 * @return
	 */
	public RiskRule getRiskRule(String ruleCode,Integer modelId) {
		return riskRuleService.getRiskRule(ruleCode,modelId);
	}

	/**
	 * 根据規則集查询一组风控规则Map
	 * 
	 * @param ruleSetId
	 *            不能为空
	 * @return
	 */
	public Map<String, RiskRule> getRiskRuleMap(String ruleSetId,Integer modelId) {
		return riskRuleService.getRiskRuleMap(ruleSetId, modelId);
	}

	/**
	 * 初始化贷款自动审核的数据：用户信息，贷款申请信息，用户基本资料
	 */
	public AutoApproveContext createAutoApproveContext(String applyId) {
		String cacheKey = RedisPrefix.AUTO_APPROVE_CONTEXT + applyId;
		AutoApproveContext context = (AutoApproveContext) JedisUtils.getObject(cacheKey);
		if (context == null || context.getApplyInfo() == null || context.getUser() == null
				|| context.getUserInfo() == null) {
			context = new AutoApproveContext(applyId);
			// 贷款申请信息
			LoanApplySimpleVO applyInfo = null;
			// 用户基本信息
			CustUserVO user = null;
			// 用户扩展信息
			UserInfoVO userInfo = null;
			if (context.getApplyInfo() == null) {
				applyInfo = getLoanApplyById(applyId);
				context.setApplyInfo(applyInfo);
				if (applyInfo != null) {
					context.setUserId(applyInfo.getCustId());
					context.setUserName(applyInfo.getCustName());
				}
			}

			if (user == null && StringUtils.isNotBlank(context.getUserId())) {
				user = getUserById(context.getUserId());
				context.setUser(user);
			}

			if (userInfo == null && StringUtils.isNotBlank(context.getUserId())) {
				userInfo = getUserInfo(context.getUserId(), applyId, true);
				// 移除空的联系人
				if (userInfo != null && userInfo.getContactList() != null) {
					List<CustContactVO> contactList = userInfo.getContactList();
					for (int i = contactList.size() - 1; i >= 0; i--) {
						CustContactVO item = contactList.get(i);
						if (StringUtils.isBlank(item.getMobile()) || StringUtils.contains(item.getMobile(), "null")) {
							contactList.remove(item);
						}
					}
				}
				context.setUserInfo(userInfo);
			}
			JedisUtils.setObject(cacheKey, context, HOUR_1);
			Assert.notNull(applyInfo, "贷款申请信息为空，请检查申请编号是否正确");
		}
		return context;
	}

	/**
	 * 查询贷款申请信息
	 * 
	 * @param applyId
	 * @return
	 */
	public LoanApplySimpleVO getLoanApplyById(String applyId) {
		String cacheKey = RedisPrefix.LOAN_APPLY + applyId;
		LoanApplySimpleVO vo = (LoanApplySimpleVO) JedisUtils.getObject(cacheKey);
		if (vo == null || StringUtils.isBlank(vo.getApplyId())) {
			vo = loanApplyService.getLoanApplyById(applyId);
			if (vo != null) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public CustUserVO getUserById(String userId) {
		String cacheKey = RedisPrefix.USER + userId;
		CustUserVO vo = (CustUserVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			vo = userService.getCustUserById(userId);
			if (vo != null) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 查询用户基本资料
	 * 
	 * @param userId
	 * @param applyId
	 * @param b
	 * @return
	 */
	public UserInfoVO getUserInfo(String userId, String applyId, boolean b) {
		String cacheKey = RedisPrefix.USER_INFO + userId;
		UserInfoVO vo = (UserInfoVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			vo = userService.getUserInfo(userId, applyId, b);
			if (vo != null) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 查询百融特殊名单
	 * 
	 * @param context
	 * @return
	 */
	public Map<String,String> getCredit100SpecialList(AutoApproveContext context) {
		String cacheKey = RedisPrefix.CREDIT100_SPECIAL_LIST + context.getApplyId();
		Map<String,String> vo = (Map<String,String>) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			SpecialListCOP op = new SpecialListCOP();

			op.setIdNo(context.getUser().getIdNo());
			op.setMobile(context.getUser().getMobile());
			op.setName(context.getUser().getRealName());
			op.setUserId(userId);
			op.setApplyId(applyId);
			List<String> contacts = getContactList(context);
			op.setContacts(contacts);

			// op.setIdNo("140502198811102244");
			// op.setMobile("18000000000");
			// op.setName("聚宝钱包");

			vo = credit100Service.specialListc(op);
			if (vo != null && ApiRespCode.SUCCESS.getCode().equals(vo.get("code"))) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 获取紧急联系人列表
	 * 
	 * @param context
	 * @return
	 */
	private List<String> getContactList(AutoApproveContext context) {
		// 紧急联系人
		List<String> contacts = new ArrayList<>();
		List<CustContactVO> list = context.getUserInfo().getContactList();
		// 支持最多输入3个手机号
		int count = 0;
		for (CustContactVO item : list) {
			contacts.add(item.getName());
			count++;
			if (count >= 3) {
				break;
			}
		}
		return contacts;
	}

	/**
	 * 查询百融当日多次申请
	 * 
	 * @param context
	 * @return
	 */
	public ApplyLoandVO getCredit100ApplyLoand(AutoApproveContext context) {
		String cacheKey = RedisPrefix.CREDIT100_APPLY_LOAN_DAY + context.getApplyId();
		ApplyLoandVO vo = (ApplyLoandVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			ApplyLoandOP op = new ApplyLoandOP();
			op.setIdNo(context.getUser().getIdNo());
			op.setMobile(context.getUser().getMobile());
			op.setName(context.getUser().getRealName());
			op.setUserId(userId);
			op.setApplyId(applyId);

			// op.setIdNo("140502198811102244");
			// op.setMobile("18000000000");
			// op.setName("聚宝钱包");

			vo = credit100Service.applyLoand(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 查询百融当日多次申请
	 * 
	 * @param context
	 * @return
	 */
	public Map<String,String> getCredit100ApplyLoanMon(AutoApproveContext context) {
		String cacheKey = RedisPrefix.CREDIT100_APPLY_LOAN_MONTH + context.getApplyId();
		Map<String,String> vo = (Map<String,String>) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			ApplyLoanMonOP op = new ApplyLoanMonOP();

			op.setIdNo(context.getUser().getIdNo());
			op.setMobile(context.getUser().getMobile());
			op.setName(context.getUser().getRealName());
			op.setUserId(userId);
			op.setApplyId(applyId);
			List<String> contacts = getContactList(context);
			op.setContacts(contacts);

			// op.setIdNo("140502198811102244");
			// op.setMobile("18000000000");
			// op.setName("聚宝钱包");

			vo = credit100Service.applyLoanMon(op);
			if (vo != null && ApiRespCode.SUCCESS.getCode().equals(vo.get("code"))) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	// /**
	// * 百融-自定义汇总的多头申请规则
	// * @return
	// */
	// public Map<String, String> getCredit100RiskConfigMap(String ruleSetId) {
	// Map<String,RiskRule> ruleMap = riskRuleService.getRiskRuleMap(ruleSetId);
	// RiskRule riskRule = null;
	// Map<String,String> map = new LinkedHashMap<>();
	// for (Map.Entry<String,RiskRule> entry:ruleMap.entrySet()){
	// riskRule = (RiskRule)entry.getValue();
	// map.put(riskRule.getRuleCode(),riskRule.getThreshold());
	// }
	// return map;
	// }

	/**
	 * 宜信致诚-查询借款、风险和逾期信息
	 * 
	 * @param context
	 * @return
	 */
	@Transactional
	public CreditInfoVO getZhichengCreditInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHICHENG_CREDIT_INFO + context.getApplyId();
		CreditInfoVO vo = (CreditInfoVO) JedisUtils.getObject(cacheKey);
		String userId = context.getUserId();
		String applyId = context.getApplyId();
		if (vo == null) {
			CreditInfoOP op = new CreditInfoOP();
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setUserId(userId);
			op.setApplyId(applyId);
			vo = zhichengService.queryCreditInfoOther(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
				if (null != vo && null != vo.getParams() && null != vo.getParams().getData() && null != vo.getParams().getData().getLoanRecords()){
					List<LoanRecord> loanRecords = vo.getParams().getData().getLoanRecords();
					List<EchoLoanRecord> echoLoanRecords = new ArrayList<>();
					EchoLoanRecord echoLoanRecord = null;
					for (LoanRecord loanRecord : loanRecords) {
						echoLoanRecord = new EchoLoanRecord();
						BeanMapper.copy(loanRecord,echoLoanRecord);
						echoLoanRecord.setApplyId(applyId);
						echoLoanRecord.setUserId(userId);
						echoLoanRecords.add(echoLoanRecord);
					}
					// 保存记录
					echoLoanRecordService.saveLoanRecordList(echoLoanRecords);
					// 更新订单表
					loanApplyService.updateZhiChengLoanRecord(context.getApplyId(), loanRecords.size());
				}
			}
		}
		return vo;
	}

	/**
	 * 宜信致诚-福网-风险名单
	 * 
	 * @param context
	 * @return
	 */
	public RiskListVO getZhichengRiskList(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHICHENG_RISK_LIST + context.getApplyId();
		RiskListVO vo = (RiskListVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			RiskListOP op = new RiskListOP();
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setUserId(userId);
			op.setApplyId(applyId);
			// op.setIdNo("320305198905040963");
			// op.setName("朱培培");

			vo = zhichengService.queryMixedRiskList(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 同盾-执行反欺诈决策引擎
	 * 
	 * @return
	 */
	public FraudApiVO doTongdunAntifraud(AutoApproveContext context) {
		String cacheKey = RedisPrefix.TONGDUN_ANTIFRAUD + context.getApplyId();
		FraudApiVO vo = (FraudApiVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			FraudApiOP op = new FraudApiOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setSource(context.getApplyInfo().getSource());
			op.setIp(context.getApplyInfo().getIp());
			if (context.getApplyInfo().getExtInfo() != null) {
				// 获取设备指纹
				Map<String, String> extInfo = (HashMap) JsonMapper.fromJsonString(context.getApplyInfo().getExtInfo(),
						HashMap.class);
				String blackBox = (String) extInfo.get("tdBlackBox");
				Assert.notNull(blackBox, "同盾设备指纹ID不能为空");
				op.setBlackBox(blackBox);
			}

			// 获取联系人
			List<CustContactVO> list = context.getUserInfo().getContactList();
			List<Map<String, String>> contacts = new ArrayList<>();
			Map<String, String> contact = null;
			for (CustContactVO item : list) {
				contact = new HashMap<>();
				contact.put("name", item.getName());
				contact.put("mobile", item.getMobile());
				contacts.add(contact);
			}
			op.setContacts(contacts);

			// op.setIdNo("451110190501015626");
			// op.setName("王五");
			// op.setMobile("18600000000");
			// op.setSource("2");

			vo = tongdunService.antifraud(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 同盾-命中规则详情查询
	 * 
	 * @return
	 */
	public RuleDetailVO getTongdunRuleDetail(AutoApproveContext context) {
		String cacheKey = RedisPrefix.TONGDUN_RULE_DETAIL + context.getApplyId();
		RuleDetailVO vo = (RuleDetailVO) JedisUtils.getObject(cacheKey);
		String sequenceId = (String) context.get("tongdunSequenceId");
		if (vo == null && StringUtils.isNotBlank(sequenceId)) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			RuleDetailOP op = new RuleDetailOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setSequenceId(sequenceId);

			vo = tongdunService.getRuleDetail(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 执行白骑士-反欺诈决策引擎
	 * 
	 * @return
	 */
	public DecisionVO doBaishiqiDecision(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_DECISION + context.getApplyId();
		DecisionVO vo = (DecisionVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			DecisionOP op = new DecisionOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setCertNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());

			if (context.getUserInfo() != null && context.getUserInfo().getLoanSuccCount()  != null && context.getUserInfo().getLoanSuccCount()  > 0){
				// 说明为复贷
				op.setLoanSuccCount("1");
			}
			// 渠道不同, 白骑士调用app不同
			op.setChannelId(context.getApplyInfo().getChannelId());
			// 1=ios,2=android,3=h3,4=api
			boolean isSetTokenKey = false;
			if ("1".equals(context.getApplyInfo().getSource())) {
				op.setPlatform("ios");
				isSetTokenKey = true;
			}
			if ("2".equals(context.getApplyInfo().getSource())) {
				op.setPlatform("android");
				isSetTokenKey = true;
			}
			// op.setOccurTime(context.getApplyInfo().getApplyTime());
			op.setIp(context.getApplyInfo().getIp());

			String eventType = context.get(RiskContants.KEY_BAIQISHI_EVENT_TYPE).toString();
			op.setEventType(eventType);

			if (isSetTokenKey && context.getApplyInfo().getExtInfo() != null) {
				// 获取设备指纹
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Map<String, String> extInfo = (HashMap) JsonMapper.fromJsonString(context.getApplyInfo().getExtInfo(),
						HashMap.class);
				String tokenKey = (String) extInfo.get("bqsTokenKey");
				Assert.notNull(tokenKey, "白骑士设备指纹ID不能为空");
				op.setTokenKey(tokenKey);
			}

			// 获取联系人
			List<CustContactVO> list = context.getUserInfo().getContactList();
			if (list != null && list.size() >= 2) {
				op.setContactsName(list.get(0).getName());
				op.setContactsMobile(list.get(0).getMobile());
				op.setContactsNameSec(list.get(1).getName());
				op.setContactsMobileSec(list.get(1).getMobile());
			} else {
				// throw new BizException("缺少足够的紧急联系人");
				logger.warn("缺少足够的紧急联系人,applyId={}", applyId);
			}
			// 自定义风控规则字段
			Map<String, String> extParams = getBaishiqiExtParams(context);

			vo = baiqishiService.doDecision(op, extParams);

			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 执行腾讯-反欺诈决策引擎
	 * 
	 * @param context
	 * @return
	 */
	public AntiFraudVO doTencentAntifraud(AutoApproveContext context) {
		String cacheKey = RedisPrefix.TENCENT_ANTIFRAUD + context.getApplyId();
		AntiFraudVO vo = (AntiFraudVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();
			AntiFraudOP op = new AntiFraudOP();
			op.setIdNumber(context.getUser().getIdNo());
			op.setPhoneNumber("0086-" + context.getUser().getMobile());
			op.setName(context.getUser().getRealName());
			vo = tencentAntiFraudService.antiFraud(userId, applyId, op);
			if (vo != null) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 白骑士-资信云报告数据
	 */
	public ReportDataVO getBaishiqiReportData(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_REPORT + context.getApplyId();
		ReportDataVO vo = (ReportDataVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			ReportExtOP opExt = new ReportExtOP();
			opExt.setUserId(userId);
			opExt.setApplyId(applyId);
			opExt.setCertNo(context.getUser().getIdNo());
			opExt.setName(context.getUser().getRealName());
			opExt.setMobile(context.getUser().getMobile());
			List<ReportExtContact> contacts = convertToExtContactList(context.getUserInfo().getContactList());
			opExt.setContacts(contacts);
			// 给资信云上报联系人信息
			ReportExtVO voExt = baiqishiService.reportExt(opExt);
			if (voExt != null && voExt.isSuccess()) {
				ReportDataOP op = new ReportDataOP();
				op.setUserId(userId);
				op.setApplyId(applyId);
				op.setCertNo(context.getUser().getIdNo());
				op.setName(context.getUser().getRealName());
				op.setMobile(context.getUser().getMobile());
				vo = baiqishiService.getReportData(op);
				if (vo != null && vo.isSuccess()) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		// Assert.notNull(vo,"资信云报告数据为空");
		// Assert.isTrue(vo.isSuccess(),String.format("查询资信云报告数据失败：%s，%s",vo.getCode(),vo.getMsg()));
		// Assert.notNull(vo.getData().getMnoCommonlyConnectMobiles(),"资信云报告常用联系电话(近6个月)为空");

		if (vo != null && vo.getData() != null) {
			if (vo.getData().getMnoCommonlyConnectMobiles() == null) {
				String msg = "常用联系电话(近6个月)为空";
				vo.setMsg(msg);
			}
		}
		return vo;
	}

	/**
	 * 获取资信云报告,数据源来自文件
	 * 
	 * @return
	 */
	public ReportDataVO getBaishiqiReportDataFromFile(String applyId) {
		String cacheKey = RedisPrefix.BAIQISHI_REPORT_FROM_FILE + applyId;
		ReportDataVO vo = (ReportDataVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			FileInfoVO fileInfoVO = userService.getLastReportByApplyId(applyId);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "白骑士", "从文件获取白骑士报告", fileInfoVO.getUrl());
				logger.info("{}-{}-请求报文：{}", "白骑士", "从文件获取白骑士报告", applyId);
				vo = (ReportDataVO) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						ReportDataVO.class);
				if (vo != null && vo.isSuccess()) {
					logger.info("{}-{}-应答结果：{}", "白骑士", "从文件获取白骑士报告", vo.isSuccess());
					JedisUtils.setObject(cacheKey, vo, DAY_1);
				} else {
					logger.info("{}-{}-应答结果：{}", "白骑士", "从文件获取白骑士报告", false);
				}
			}
		}
		return vo;
	}

	/**
	 * 白骑士-上报联系人
	 */
	public int reportContactToBaiqishi(String userId) {
		CustUserVO user = getUserById(userId);
		UserInfoVO userInfo = getUserInfo(userId, null, false);

		ReportExtOP opExt = new ReportExtOP();
		opExt.setUserId(userId);
		opExt.setApplyId("");
		opExt.setCertNo(user.getIdNo());
		opExt.setName(user.getRealName());
		opExt.setMobile(user.getMobile());
		List<ReportExtContact> contacts = convertToExtContactList(userInfo.getContactList());
		opExt.setContacts(contacts);
		// 给资信云上报联系人信息
		ReportExtVO voExt = baiqishiService.reportExt(opExt);
		if (voExt != null && voExt.isSuccess()) {
			return 1;
		}
		return 0;
	}

	/**
	 * 白骑士-移动网络运营商的原始数据
	 */
	public MnoContactVO getBaiqishiMnoContact(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_MNO_CONTACT + context.getApplyId();
		MnoContactVO vo = (MnoContactVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			MnoContactOP op = new MnoContactOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setCertNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());

			vo = baiqishiService.getMnoContact(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}

		}
		return vo;
	}

	/**
	 * 将紧急联系封装成白骑士所需的格式 聚宝钱包-与本人关系字典： 1父母，2配偶，3朋友，4同事 白骑士-与本人关系字典： 1 父母，2 子女，3
	 * 夫妻，4 恋人，5 同事，6 同学，7 朋友，8 其他
	 * 
	 * @param contactList
	 * @return
	 */
	private List<ReportExtContact> convertToExtContactList(List<CustContactVO> contactList) {
		List<ReportExtContact> list = new ArrayList<>();
		if (contactList != null) {
			ReportExtContact contact = null;
			for (CustContactVO vo : contactList) {
				if (StringUtils.isNotBlank(vo.getMobile())) {
					contact = new ReportExtContact();
					contact.setMobile(vo.getMobile());
					contact.setName(vo.getName());
					int relationship = vo.getRelationship().intValue();
					switch (relationship) {
					case 1:
						contact.setRelation("1");
						break;
					case 2:
						contact.setRelation("3");
						break;
					case 3:
						contact.setRelation("7");
						break;
					case 4:
						contact.setRelation("5");
						break;
					default:
						contact.setRelation("8");
					}
					list.add(contact);
				}

			}
		}
		return list;
	}

	/**
	 * 根据地区编号获取地区信息
	 */
	public AreaVO getArea(String areaId) {
		// Map<String,AreaVO> map = areaService.getAllAreaMap();
		// AreaVO vo = map.get(areaId);
		// return vo;
		return null;
	}

	/**
	 * 查询设备信息
	 * 
	 * @return
	 */
	public DeviceInfoVO getBaiqishiDeviceInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_DEVICE + context.getApplyId();
		DeviceInfoVO vo = (DeviceInfoVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			DeviceInfoOP op = new DeviceInfoOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setSource(context.getApplyInfo().getSource());
			if (context.getApplyInfo().getExtInfo() != null) {
				// 获取设备指纹
				Map<String, String> extInfo = (HashMap) JsonMapper.fromJsonString(context.getApplyInfo().getExtInfo(),
						HashMap.class);
				String tokenKey = (String) extInfo.get("bqsTokenKey");
				Assert.notNull(tokenKey, "白骑士设备指纹ID不能为空");
				op.setTokenKey(tokenKey);
			}
			vo = baiqishiService.getDeviceInfo(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		// Assert.notNull(vo.getResultData(),"手机设备信息为空，无法校验此规则");
		return vo;
	}

	/**
	 * 获取手机通讯录信息
	 * 
	 * @return
	 */
	public DeviceContactVO getBaiqishiContactInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_CONTACT_INFO + context.getApplyId();
		DeviceContactVO vo = (DeviceContactVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			DeviceContactOP op = new DeviceContactOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setSource(context.getApplyInfo().getSource());
			if (context.getApplyInfo().getExtInfo() != null) {
				// 获取设备指纹
				Map<String, String> extInfo = (HashMap) JsonMapper.fromJsonString(context.getApplyInfo().getExtInfo(),
						HashMap.class);
				String tokenKey = (String) extInfo.get("bqsTokenKey");
				Assert.notNull(tokenKey, "白骑士设备指纹ID不能为空");
				op.setTokenKey(tokenKey);
			}
			vo = baiqishiService.getDeviceContact(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		// Assert.notNull(vo.getContactsInfo(),"手机设备通讯录为空");
		return vo;
	}

	/**
	 * 获取手机通讯录信息,数据源来自文件
	 * 
	 * @return
	 */
	public DeviceContactVO getBaiqishiContactInfoFromFile(String userId, String applyId) {
		String cacheKey = RedisPrefix.BAIQISHI_CONTACT_INFO_FROM_FILE + applyId;
		DeviceContactVO vo = (DeviceContactVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			int size = 0;
			FileInfoVO applyFileInfoVO = userService.getLastContactInfoByApplyId(applyId);
			if (applyFileInfoVO != null && StringUtils.isNotBlank(applyFileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "白骑士", "从文件获取订单最新手机通讯录", applyFileInfoVO.getUrl());
				logger.info("{}-{}-请求报文：{},{}", "白骑士", "从文件获取订单最新手机通讯录", userId, applyId);
				vo = (DeviceContactVO) RestTemplateUtils.getInstance().getForObject(applyFileInfoVO.getUrl(),
						DeviceContactVO.class);
				size = (vo == null || !vo.isSuccess() || vo.getContactsInfo() == null) ? 0 : vo.getContactsInfo()
						.size();
				logger.info("{}-{}-应答结果：{}", "白骑士", "从文件获取订单最新手机通讯录", size);
				if (size > 0) {
					AutoApproveUtils.removeEmptyContact(vo);
					JedisUtils.setObject(cacheKey, vo, DAY_1);
				}
			}
			if (size == 0) {
				List<FileInfoVO> fileInfoList = userService.getAllContactFileinfo(userId);
				if (fileInfoList != null) {
					for (FileInfoVO fileInfoVO : fileInfoList) {
						if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
							logger.info("{}-{}-请求地址：{}", "白骑士", "从文件获取用户最新手机通讯录", fileInfoVO.getUrl());
							logger.info("{}-{}-请求报文：{},{}", "白骑士", "从文件获取用户最新手机通讯录", userId, applyId);
							vo = (DeviceContactVO) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
									DeviceContactVO.class);
							size = (vo == null || !vo.isSuccess() || vo.getContactsInfo() == null) ? 0 : vo
									.getContactsInfo().size();
							logger.info("{}-{}-应答结果：{}", "白骑士", "从文件获取用户最新手机通讯录", size);
							if (size > 0) {
								AutoApproveUtils.removeEmptyContact(vo);
								JedisUtils.setObject(cacheKey, vo, DAY_1);
								break;
							}
						}
					}
				}
			}
		}
		return vo;
	}

	/**
	 * 查询芝麻信用授权结果
	 * 
	 * @return
	 */
	public AuthorizeResultVO getZmAuthorizeResult(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHIMA_AUTHORIZE_RESULT + context.getApplyId();
		AuthorizeResultVO vo = (AuthorizeResultVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			AuthorizeResultOP op = new AuthorizeResultOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setSource(context.getApplyInfo().getSource());
			op.setName(context.getUser().getRealName());
			op.setIdNo(context.getUser().getIdNo());
			vo = zhimaService.getAuthorizeResult(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 查询芝麻信用分
	 * 
	 * @return
	 */
	public ZmScoreVO getZmScore(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHIMA_SCORE + context.getApplyId();
		ZmScoreVO vo = (ZmScoreVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();
			AuthorizeResultVO authorizeResultVO = getZmAuthorizeResult(context);
			if (authorizeResultVO != null && authorizeResultVO.isSuccess()
					&& StringUtils.isNotBlank(authorizeResultVO.getOpenId())) {
				ZmScoreOP op = new ZmScoreOP();
				op.setUserId(userId);
				op.setApplyId(applyId);
				op.setSource(context.getApplyInfo().getSource());
				op.setOpenId(authorizeResultVO.getOpenId());

				vo = zhimaService.getZmScore(op);
				if (vo != null && vo.isSuccess()) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		if (vo == null) {
			String msg = "未能查询到芝麻信用分";
			vo = new ZmScoreVO();
			vo.setMsg(msg);
		} else {
			String msg = String.format("查询到芝麻信用分失败：错误代码：%s，错误描述：%s", vo.getCode(), vo.getMsg());
			if (!vo.isSuccess()) {
				vo.setMsg(msg);
			}
		}
		return vo;
	}

	/**
	 * 查询芝麻信用行业关注名单
	 * 
	 * @return
	 */
	public ZmWatchListVO getZmWatchList(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHIMA_WATCH_LIST + context.getApplyId();
		ZmWatchListVO vo = (ZmWatchListVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();
			AuthorizeResultVO authorizeResultVO = getZmAuthorizeResult(context);
			if (authorizeResultVO != null && authorizeResultVO.isSuccess()
					&& StringUtils.isNotBlank(authorizeResultVO.getOpenId())) {
				ZmWatchListOP op = new ZmWatchListOP();
				op.setUserId(userId);
				op.setApplyId(applyId);
				op.setOpenId(authorizeResultVO.getOpenId());

				vo = zhimaService.getZmWatchList(op);
				if (vo != null && vo.isSuccess()) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		if (vo == null) {
			String msg = "未能查询到芝麻信用行业关注名单";
			vo = new ZmWatchListVO();
			vo.setMsg(msg);
		} else {
			String msg = String.format("查询芝麻信用行业关注名单失败：错误代码：%s，错误描述：%s", vo.getCode(), vo.getMsg());
			if (!vo.isSuccess()) {
				vo.setMsg(msg);
			}
		}
		return vo;
	}

	/**
	 * 向白骑士规则引擎上送的自定义字段
	 * 
	 * @return
	 */
	public Map<String, String> getBaishiqiExtParams(AutoApproveContext context) {
		String cacheKey = RedisPrefix.BAIQISHI_EXT_PARAMS;
		Map<String, String> map = (Map) context.get(cacheKey);
		if (map == null) {
			map = new LinkedHashMap<>();
			List<RiskRule> list = riskRuleService.getAllRiskRuleList();
			for (RiskRule item : list) {
				if (StringUtils.isNotBlank(item.getFieldName())) {
					// 默认都上传0
					map.put(item.getFieldName(), String.valueOf(0));
				}
			}
			context.put(cacheKey, map);
		}
		return map;
	}

	/**
	 * 口袋查询黑名单
	 * 
	 * @param context
	 * @return
	 */
	public KDBlackListVO getKDBlacklist(AutoApproveContext context) {
		String cacheKey = RedisPrefix.KDCREDIT_BLACKLIST + context.getApplyId();
		KDBlackListVO vo = (KDBlackListVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			KDBlackListOP op = new KDBlackListOP();
			op.setId_card(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setUserId(userId);
			op.setApplyId(applyId);

			vo = kdService.searchOne(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 本平台查询历史最长逾期天数
	 */
	public int getMaxOverdueDays(AutoApproveContext context) {
		return overdueService.getMaxOverdueDays(context.getUserId());
	}

	/**
	 * 本平台查询历史最长逾期天数
	 */
	public int getCountOverdueDays(AutoApproveContext context) {
		return overdueService.getCountOverdueDays(context.getUserId());
	}

	/**
	 * 三方征信1查询黑名单
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public ThirdPartyCredit1BlackListVO getThirdPartyCredit1Blacklist(AutoApproveContext context) {
		String cacheKey = RedisPrefix.THIRDPARTYCREDIT1_BLACKLIST + context.getApplyId();
		ThirdPartyCredit1BlackListVO vo = (ThirdPartyCredit1BlackListVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			ThirdPartyCredit1BlackListOP op = new ThirdPartyCredit1BlackListOP();
			op.setIdCard(context.getUser().getIdNo());
			op.setUserId(userId);
			op.setApplyId(applyId);

			vo = thirdPartyCredit1Service.blacklist(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 三方征信2查询黑名单
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public ThirdPartyCredit1BlackListVO getThirdPartyCredit2Blacklist(AutoApproveContext context) {
		String cacheKey = RedisPrefix.THIRDPARTYCREDIT2_BLACKLIST + context.getApplyId();
		ThirdPartyCredit1BlackListVO vo = (ThirdPartyCredit1BlackListVO) JedisUtils.getObject(cacheKey);
		vo = null;
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			ThirdPartyCredit1BlackListOP op = new ThirdPartyCredit1BlackListOP();
			op.setIdCard(context.getUser().getIdNo());
			op.setUserId(userId);
			op.setApplyId(applyId);
			vo = thirdPartyCredit1Service.blacklist2(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 新颜-负面拉黑
	 */
	public BlackVO getXinyanBlack(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XINYAN_BLACK + context.getApplyId();
		BlackVO vo = (BlackVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			BlackOP op = new BlackOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setCardNo(context.getUser().getCardNo());
			vo = xinyanService.black(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
				// uploadFile(context, vo,
				// FileBizCode.XINYAN_BLACK.getBizCode(), "txt");
			}
		}
		return vo;
	}

	/**
	 * 新颜-共债档案
	 */
	public TotaldebtVO getXinyanTotalDebt(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XINYAN_TOTALDEBT + context.getApplyId();
		TotaldebtVO vo = (TotaldebtVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			TotaldebtOP op = new TotaldebtOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setCardNo(context.getUser().getCardNo());
			vo = xinyanService.totaldebt(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 新颜-逾期档案
	 */
	public OverdueVO getXinyanOverdueVO(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XINYAN_OVERDUE + context.getApplyId();
		OverdueVO vo = (OverdueVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			OverdueOP op = new OverdueOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setCardNo(context.getUser().getCardNo());
			vo = xinyanService.overdue(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	/**
	 * 新颜-负面拉黑-聚宝钱包
	 */
	public JbqbBlackVO getXinyanBlackJbqb(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XINYAN_BLACK_JBQB + context.getApplyId();
		JbqbBlackVO vo = (JbqbBlackVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			JbqbBlackOP op = new JbqbBlackOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			vo = xinyanService.blackJbqb(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
				// uploadFile(context, vo,
				// FileBizCode.XINYAN_BLACK.getBizCode(), "txt");
			}
		}
		return vo;
	}

	/**
	 * 新颜-全景雷达
	 */
	public RadarVO getXinyanRadar(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XINYAN_RADAR + context.getApplyId();
		RadarVO vo = (RadarVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String userId = context.getUserId();
			String applyId = context.getApplyId();

			RadarOP op = new RadarOP();
			op.setUserId(userId);
			op.setApplyId(applyId);
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			vo = xinyanService.radar(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
				// uploadFile(context, vo,
				// FileBizCode.XINYAN_BLACK.getBizCode(), "txt");
			}
		}
		return vo;
	}

	/**
	 * 魔蝎-信用卡邮箱报告
	 */
	public List<EmailReportVO> getMoxieEmailReport(String userId) {
		String cacheKey = RedisPrefix.MOXIE_EMAIL_REPORT + userId;
		List<EmailReportVO> list = (List<EmailReportVO>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			FileInfoVO fileInfoVO = userService.getLastEmailReportByUserId(userId);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "魔蝎", "从文件获取最新信用卡邮箱报告", fileInfoVO.getUrl());
				logger.info("{}-{}-请求报文：{}", "魔蝎", "从文件获取最新信用卡邮箱报告", userId);
				list = (List<EmailReportVO>) RestTemplateUtils.getInstance().getListForObject(fileInfoVO.getUrl(),
						EmailReportVO.class);
				logger.info("{}-{}-应答结果：{}", "魔蝎", "从文件获取最新信用卡邮箱报告", list.size());
				if (list != null && !list.isEmpty())
					JedisUtils.setObject(cacheKey, list, HOUR_1);
			}
		}
		return list;
	}

	/**
	 * 魔蝎-网银报告
	 */
	public BankReportVO getMoxieBankReport(String userId) {
		String cacheKey = RedisPrefix.MOXIE_BANK_REPORT + userId;
		BankReportVO vo = (BankReportVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			FileInfoVO fileInfoVO = userService.getLastBankReportByUserId(userId);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "魔蝎", "从文件获取最新网银报告", fileInfoVO.getUrl());
				logger.info("{}-{}-请求报文：{}", "魔蝎", "从文件获取最新网银报告", userId);
				vo = (BankReportVO) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						BankReportVO.class);
				logger.info("{}-{}-应答结果：{}", "魔蝎", "从文件获取最新网银报告", vo);
				if (vo != null && vo.isSuccess()) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	// private void uploadFile(AutoApproveContext context, Object vo, String
	// fileBizCode, String fileExt) {
	// // 公共参数
	// UploadParams params = new UploadParams();
	// // 注意获取用户端的IP地址
	// String clientIp = "127.0.0.1";
	// // 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
	// String source = context.getApplyInfo().getSource();
	// params.setUserId(context.getUserId());
	// params.setApplyId(context.getApplyId());
	// params.setIp(clientIp);
	// params.setSource(source);
	// params.setBizCode(fileBizCode);
	// String fileBodyText = JsonMapper.toJsonString(vo);
	// fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
	// }

	@Override
	public XianJinBaiKaCommonOP getXianJinBaiKaBase(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XIANJINCARD_BASE + context.getUserId();
		XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			FileInfoVO fileInfoVO = userService.getLastXianJinCardBaseByUserId(context.getUserId());
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户基础信息", fileInfoVO.getUrl());
				vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						XianJinBaiKaCommonOP.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public XianJinBaiKaCommonOP getXianJinBaiKaAdditional(AutoApproveContext context) {
		String cacheKey = RedisPrefix.XIANJINCARD_ADDITIONAL + context.getUserId();
		XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			FileInfoVO fileInfoVO = userService.getLastXianJinCardAdditionalByUserId(context.getUserId());
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户附加信息", fileInfoVO.getUrl());
				vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						XianJinBaiKaCommonOP.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public OrderBaseInfo getRongBase(AutoApproveContext context) {
		String cacheKey = RedisPrefix.RONG360_BASE + context.getApplyId();
		OrderBaseInfo vo = (OrderBaseInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastRongBaseByOrderSn(orderNo);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户基础信息", fileInfoVO.getUrl());
				vo = (OrderBaseInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						OrderBaseInfo.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public OrderAppendInfo getRongAdditional(AutoApproveContext context) {
		String cacheKey = RedisPrefix.RONG360_ADDITIONAL + context.getApplyId();
		OrderAppendInfo vo = (OrderAppendInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastRongAdditionalByOrderSn(orderNo);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户附加信息", fileInfoVO.getUrl());
				vo = (OrderAppendInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
						OrderAppendInfo.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public IdnumberVerificationVO getLinkfaceIdnumberVerification(AutoApproveContext context) {
		String cacheKey = RedisPrefix.LINKFACE_IDNUMBER_VERIFICATION + context.getApplyId();
		IdnumberVerificationVO vo = (IdnumberVerificationVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {

			IdnumberVerificationOP op = new IdnumberVerificationOP();
			op.setUserId(context.getUser().getId());
			op.setApplyId(context.getApplyId());
			op.setId_number(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());

			// 人脸识别图片
			FileInfoVO fileInfo = null;
			List<FileInfoVO> list = userService.getFileinfo(op.getUserId());
			for (FileInfoVO fileInfoVO : list) {
				if (FileBizCode.FACE_VERIFY.getBizCode().equals(fileInfoVO.getBizCode())) {
					fileInfo = fileInfoVO;
					break;
				}
			}

			if (null == fileInfo) {
				logger.error("人脸识别图片为空");
				return null;
			}

			op.setSelfie_url(fileInfo.getUrl());
			vo = linkfaceService.idnumberVerification(op);
			if (vo != null && vo.isSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}

	@Override
	public TianjiReportDetailResp getRongTJReportDetail(AutoApproveContext context) {
		String cacheKey = RedisPrefix.RONGTJ_REPORT_DETAIL + context.getApplyId();
		TianjiReportDetailResp vo = (TianjiReportDetailResp) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			try {
				String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(context.getApplyId());
				FileInfoVO fileInfoVO = userService.getLastRongTJReportDetailByOrderSn(orderNo);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户运营商报告信息", fileInfoVO.getUrl());
					vo = (TianjiReportDetailResp) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
							TianjiReportDetailResp.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, HOUR_1);
					}
				}
			} catch (Exception e) {
				logger.error("从文件获取用户运营商报告信息异常！！！applyId={},userId={}", context.getApplyId(), context.getUserId(), e);
			}
		}
		return vo;
	}

	@Override
	public void crawlRongCarrierReport(String orderNo, String userId, String applyId) {

		logger.debug("----------开始执行【融360预审批-生成融天机运营商报告】orderNo={}----------", orderNo);
		String searchId = null;
		try {
			RongTJResp resp = rongService.crawlGenerateReport(orderNo, "mobile", null, "1.0");
			searchId = resp.getTianjiApiTaojinyunreportGeneratereportResponse().getSearchId();
			if (StringUtils.isBlank(searchId)) {
				logger.warn("融天机运营商报告生成异常，查询ID为空！！！orderNo={}", orderNo);
				return;
			}
			String cacheKey = "rong_tj_report_" + searchId;
			Map<String, String> map = new HashMap<>();
			map.put("searchId", searchId);
			map.put("orderNo", orderNo);
			map.put("applyId", applyId);
			map.put("userId", userId);
			JedisUtils.setMap(cacheKey, map, 60 * 60);
			logger.debug("----------成功执行【融360预审批-生成融天机运营商报告】----------orderNo={},searchId={}", orderNo, searchId);
		} catch (Exception e) {
			logger.warn("融天机运营商报告生成异常！！！orderNo={},searchId={}", orderNo, searchId, e);
		}
	}

	/**
	 * 根据手机号查询未完结订单
	 * 
	 * @param mobile
	 * @return
	 */
	public int countUnFinishApplyByMobile(String mobile) {
		return loanApplyService.countUnFinishApplyByMobile(mobile);
	}

	@Override
	public IntoOrder getjdqBase(AutoApproveContext context) {
		String cacheKey = RedisPrefix.JDQ_BASE + context.getApplyId();
		IntoOrder vo = (IntoOrder) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = jdqService.getOrderNo(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastJDQBaseByOrderSn(orderNo,FileBizCode.JDQ_BASE_DATA.getBizCode());
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户基础信息", fileInfoVO.getUrl());
				vo = (IntoOrder) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), IntoOrder.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public IntoOrder getjdqBaseAdd(AutoApproveContext context) {
		String cacheKey = RedisPrefix.JDQ_BASE_ADD + context.getApplyId();
		IntoOrder vo = (IntoOrder) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = jdqService.getOrderNo(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastJDQBaseByOrderSn(orderNo,FileBizCode.JDQ_BASE_DATA_ADD.getBizCode());
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户基础补充信息", fileInfoVO.getUrl());
				vo = (IntoOrder) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), IntoOrder.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public JDQReport getjdqReport(AutoApproveContext context) {
		String cacheKey = RedisPrefix.JDQ_REPORT + context.getApplyId();
		JDQReport vo = (JDQReport) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = jdqService.getOrderNo(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastJDQReportByOrderSn(orderNo);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户报告信息", fileInfoVO.getUrl());
				vo = (JDQReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), JDQReport.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public DWDReport getdwdReport(AutoApproveContext context) {
		String cacheKey = RedisPrefix.DWD_REPORT + context.getApplyId();
		DWDReport vo = (DWDReport) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = dwdService.getOrderNo(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastDWDReportByOrderSn(orderNo);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "大王贷", "从文件获取用户报告信息", fileInfoVO.getUrl());
				vo = (DWDReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), DWDReport.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public ChargeInfo getdwdChargeInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.DWD_CHARGEINFO + context.getApplyId();
		ChargeInfo vo = (ChargeInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			try {
				String orderNo = dwdService.getOrderNo(context.getApplyId());
				FileInfoVO fileInfoVO = userService.getLastDWDChargeInfoByOrderSn(orderNo);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					logger.info("{}-{}-请求地址：{}", "【大王贷】", "从文件获取用户运营商信息", fileInfoVO.getUrl());
					vo = (ChargeInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), ChargeInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, HOUR_1);
					}
				}
			} catch (Exception e) {
				logger.error("{}-{}-异常！！！applyId={},userId={}", "【大王贷】","从文件获取用户运营商信息",context.getApplyId(),
						context.getUserId(),
						e);
			}
		}
		return vo;
	}


	@Override
	public JDQReport getsllReport(AutoApproveContext context) {
		String cacheKey = RedisPrefix.SLL_REPORT + context.getUserId();
		JDQReport vo = (JDQReport) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			String orderNo = sllService.getOrderNo(context.getApplyId());
			FileInfoVO fileInfoVO = userService.getLastSLLReportByOrderSn(orderNo);
			if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
				logger.info("{}-{}-请求地址：{}", "SLL", "从文件获取用户报告信息", fileInfoVO.getUrl());
				vo = (JDQReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), JDQReport.class);
				if (vo != null) {
					JedisUtils.setObject(cacheKey, vo, HOUR_1);
				}
			}
		}
		return vo;
	}

	@Override
	public int countContract(AutoApproveContext context, String type, int time) {
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < contactList.size(); i++) {
			list.add(contactList.get(i).getMobile());
		}
		//查询条件
		Map map = new HashMap();
		map.put("userId", context.getUserId());

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if ("hour".equals(type)) {
			c.add(Calendar.HOUR, -time);
		} else if ("month".equals(type)) {
			c.add(Calendar.MONTH, -time);
		}
		map.put("list", list);
		map.put("createTime", c.getTime());

		return userService.countContractNum(map);
	}

	@Override
	public DWDAdditionInfo getdwdAdditionInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.DWD_BASE_ADD + context.getApplyId();
		DWDAdditionInfo vo = (DWDAdditionInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			try {
				String orderNo = dwdService.getOrderNo(context.getApplyId());
				FileInfoVO fileInfoVO = userService.getLastDWDAdditionalByOrderSn(orderNo);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					logger.info("{}-{}-请求地址：{}", "【大王贷】", "从文件获取用户补充信息", fileInfoVO.getUrl());
					vo = (DWDAdditionInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), DWDAdditionInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, HOUR_1);
					}
				}
			} catch (Exception e) {
				logger.error("{}-{}-异常！！！applyId={},userId={}", "【大王贷】","从文件获取用户补充信息",context.getApplyId(),
						context.getUserId(),
						e);
			}
		}
		return vo;
	}

	@Override
	public DWDBaseInfo getdwdBaseInfo(AutoApproveContext context) {
		String cacheKey = RedisPrefix.DWD_BASE + context.getApplyId();
		DWDBaseInfo vo = (DWDBaseInfo) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			try {
				String orderNo = dwdService.getOrderNo(context.getApplyId());
				FileInfoVO fileInfoVO = userService.getLastDWDBaseByOrderSn(orderNo);
				if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
					logger.info("{}-{}-请求地址：{}", "【大王贷】", "从文件获取用户基本信息", fileInfoVO.getUrl());
					vo = (DWDBaseInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), DWDBaseInfo.class);
					if (vo != null) {
						JedisUtils.setObject(cacheKey, vo, HOUR_1);
					}
				}
			} catch (Exception e) {
				logger.error("{}-{}-异常！！！applyId={},userId={}", "【大王贷】","从文件获取用户基本信息",context.getApplyId(),
						context.getUserId(),
						e);
			}
		}
		return vo;
	}

	@Override
	@Transactional
	public com.rongdu.loans.zhicheng.vo.DecisionVO getZhiChengDecision(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHICHENG_DECISION_INFO + context.getApplyId();
		com.rongdu.loans.zhicheng.vo.DecisionVO  vo = (com.rongdu.loans.zhicheng.vo.DecisionVO) JedisUtils.getObject(cacheKey);
		String userId = context.getUserId();
		String applyId = context.getApplyId();
		if (vo == null) {
			CreditInfoOP op = new CreditInfoOP();
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setUserId(userId);
			op.setApplyId(applyId);
			vo = zhichengService.queryDecisionOther(op);
			if (vo != null && vo.getSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}

		if (vo != null && vo.getData() != null){
			EchoDecisionReport entity = new EchoDecisionReport();
			entity.setUserId(userId);
			entity.setApplyId(applyId);
			entity.setCompositeScore(vo.getData().getCompositeScore());
			entity.setDecisionSuggest(vo.getData().getDecisionSuggest());
			echoDecisionReportService.saveDecisionReport(entity);
			// 分数更新到订单表
			loanApplyService.updateCompositeScore(context.getApplyId(), vo.getData().getCompositeScore());
		}
		return vo;
	}

	@Override
	@Transactional
	public FraudScreenVO getZhiChengFraudScreen(AutoApproveContext context) {
		String cacheKey = RedisPrefix.ZHICHENG_FRAUDSCREEN_INFO + context.getApplyId();
		FraudScreenVO  vo = (FraudScreenVO) JedisUtils.getObject(cacheKey);
		String userId = context.getUserId();
		String applyId = context.getApplyId();
		if (vo == null) {
			CreditInfoOP op = new CreditInfoOP();
			op.setIdNo(context.getUser().getIdNo());
			op.setName(context.getUser().getRealName());
			op.setMobile(context.getUser().getMobile());
			op.setUserId(userId);
			op.setApplyId(applyId);
			vo = zhichengService.queryFraudScreenOther(op);
			if (vo != null && vo.getSuccess()) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}

		if (vo != null && vo.getData() != null){
			EchoFraudScreen echoFraudScreen = new EchoFraudScreen();
			echoFraudScreen.setUserId(userId);
			echoFraudScreen.setApplyId(applyId);
			echoFraudScreen.setFlowId(vo.getFlowId());
			echoFraudScreen.setFraudLevel(vo.getData().getFraudLevel());
			echoFraudScreen.setFraudScore(vo.getData().getFraudScore());
			echoFraudScreen.setSocialNetwork(JsonMapper.toJsonString(vo.getData().getSocialNetwork()));
			echoFraudScreenService.saveFraudScreen(echoFraudScreen);
			// 存储附加风控信息
			List<RiskResult> riskResult = vo.getData().getRiskResult();
			if (null != riskResult){
				List<EchoFraudScreenRiskResult> entityList = new ArrayList<>();
				EchoFraudScreenRiskResult entity = null;
				for (RiskResult result : riskResult) {
					entity = new EchoFraudScreenRiskResult();
					entity.setApplyId(applyId);
					entity.setUserId(userId);
					entity.setFlowId(vo.getFlowId());
					entity.setDataType(result.getDataType());
					entity.setRiskDetail(result.getRiskDetail());
					entity.setRiskItemType(result.getRiskItemType());
					entity.setRiskItemValue(result.getRiskItemValue());
					entity.setRiskTime(result.getRiskTime());
					entityList.add(entity);
				}
				echoFraudScreenRiskResultService.saveFraudScreenRiskResultList(entityList);
			}
		}
		return vo;
	}

	@Override
	public TongLianBlackVO getTongLianBlackDetail(AutoApproveContext context) {
		String cacheKey = RedisPrefix.TONGLIAN_BLACK_INFO + context.getApplyId();
		TongLianBlackVO  vo = (TongLianBlackVO) JedisUtils.getObject(cacheKey);
		if (vo == null) {
			TongLianBlackOP op = new TongLianBlackOP();
			op.setCustOrderId(context.getApplyId());
			op.setIdCard(context.getUser().getIdNo());
			vo = tongLianBlackService.getBlackDetail(op);
			if (vo != null && ("000000".equals(vo.getCode()) || "000010".equals(vo.getCode()))) {
				JedisUtils.setObject(cacheKey, vo, HOUR_1);
			}
		}
		return vo;
	}
}