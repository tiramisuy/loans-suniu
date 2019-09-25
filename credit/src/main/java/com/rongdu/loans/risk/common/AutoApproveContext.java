package com.rongdu.loans.risk.common;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 自动审核的公用/全局数据
 * 说明：此类封装贷款审批所需的基本数据，如需其他数据，放置在Map中
 *
 * @author sunda
 * @version 2017-08-14
 */
public class AutoApproveContext extends HashMap<String, Object> {

    private static final long serialVersionUID = 6059789383786071296L;
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 贷款申请编号
     */
    private String applyId;
    /**
     * 用户基本信息
     */
    private CustUserVO user;
    /**
     * 用户扩展信息
     */
    private UserInfoVO userInfo;
    /**
     * 贷款申请信息
     */
    private LoanApplySimpleVO applyInfo;

    /**
     * 欺诈分
     */
    private int score = 0;
    /**
     * 风险决策：ACCEPT-通过，REVIEW-人工审批，REJECT-拒绝
     */
    private String decision = RiskDecision.REVIEW;

    /**
     * 耗时
     */
    private int costTime = 0;

    /**
     * 命中的规则
     */
    private List<HitRule> hitRules = new ArrayList<HitRule>();

    /**
     * 模型Id
     */
    private int modelId;

    public AutoApproveContext(String applyId) {
        this.applyId = applyId;
    }

    /**
     * 初始化贷款自动审核的数据：用户信息，贷款申请信息，用户基本资料
     */
    public void initContext() {
        CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
        if (applyInfo == null) {
            applyInfo = creditDataInvokeService.getLoanApplyById(getApplyId());
            if (applyInfo != null) {
                userId = applyInfo.getCustId();
                userName = applyInfo.getCustName();
            }
        }
        logger.info("正在加载贷款申请信息：{}", JsonMapper.toJsonString(applyInfo));
        if (user == null && StringUtils.isNotBlank(userId)) {
            user = creditDataInvokeService.getUserById(userId);
        }
        if (userInfo == null && StringUtils.isNotBlank(userId)) {
            userInfo = creditDataInvokeService.getUserInfo(userId, applyId, true);
            if (userInfo != null && userInfo.getContactList() != null) {
                List<CustContactVO> contactList = userInfo.getContactList();
                for (int i = contactList.size() - 1; i >= 0; i--) {
                    CustContactVO item = contactList.get(i);
                    if (StringUtils.isBlank(item.getMobile()) || StringUtils.contains(item.getMobile(), "null")) {
                        contactList.remove(item);
                    }
                }
            }
        }

    }

//	/**
//	 * 记录命中的风控
//	 * @param ruleId
//	 * @param value
//	 * @param remark
//	 */
//	public HitRule addHitRule(String ruleId, String value, String remark) {
//		RiskRuleService riskRuleService = SpringContextHolder.getBean("riskRuleService");
//		RiskRule rule =  riskRuleService.getRiskRule(ruleId);
//		HitRule hitRule = null;
//		if (rule!=null){
//			hitRule = addHitRule(rule,value ,remark);
//		}
//		return hitRule;
//	}

//	/**
//	 * 记录命中的风控
//	 * @param source
//	 * @param subSource
//	 * @param riskCodeList
//     * @return
//     */
//	public List<HitRule> addHitRule(String source, String subSource, List<String> riskCodeList) {
//		RiskRuleService riskRuleService = SpringContextHolder.getBean("riskRuleService");
//		Map<String,RiskRule> ruleMap =  (Map)riskRuleService.getRiskRuleMap(source,subSource);
//		RiskRule rule =  null;
//		HitRule hitRule = null;
//		String value = "";
//		String remark = "";
//		List<HitRule> hitRuleList = new ArrayList<>();
//		for (String riskCode:riskCodeList){
//			rule = ruleMap.get(riskCode);
//			if (rule==null){
//				logger.info("自动审批-该规则尚未配置到规则库中：{},{},{}",source,subSource,riskCode);
//			}else{
//				value = rule.getRuleCode();
//				remark = rule.getRuleName();
//				hitRule = addHitRule(rule,value ,remark);
//				if (hitRule!=null){
//					hitRuleList.add(hitRule);
//				}
//			}
//		}
//		return hitRuleList;
//	}

//	/**
//	 * 记录命中的风控
//	 * @param rule
//	 * @param value
//	 * @param remark
//     * @return
//     */
//	public HitRule addHitRule(RiskRule rule,String value, String remark) {
//		HitRule hitRule = BeanMapper.map(rule, HitRule.class);
//		hitRule.setUserId(userId);
//		hitRule.setApplyId(applyId);
//		hitRule.setId(null);
//		hitRule.setName(getUserName());
//		hitRule.setRuleId(rule.getId());
//		hitRule.setValue(value);
//		hitRule.setRemark(remark);
//		hitRules.add(hitRule);
//		HitRuleService hitRuleService = SpringContextHolder.getBean("hitRuleService");
//		hitRuleService.insert(hitRule);
//		return hitRule;
//	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public CustUserVO getUser() {
        return user;
    }

    public void setUser(CustUserVO user) {
        this.user = user;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public LoanApplySimpleVO getApplyInfo() {
        return applyInfo;
    }

    public void setApplyInfo(LoanApplySimpleVO applyInfo) {
        this.applyInfo = applyInfo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public List<HitRule> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<HitRule> hitRules) {
        this.hitRules = hitRules;
    }

    public void addHitRule(HitRule hitRule) {
        hitRules.add(hitRule);
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
}
