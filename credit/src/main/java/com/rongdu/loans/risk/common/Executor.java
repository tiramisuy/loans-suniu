package com.rongdu.loans.risk.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.entity.AutoApproveLog;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.risk.service.AutoApproveLogService;
import com.rongdu.loans.risk.service.BlacklistService;
import com.rongdu.loans.risk.service.CreditDataInvokeService;


/**
 * 风控规则执行器
 *
 * @author sunda
 * @version 2017-08-14
 */
public abstract class Executor {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 当前规则或者规则集的ID
     */
    public String ruleId;
    /**
     * 当前规则或者规则集的名称
     */
    private String ruleName;
    /**
     * 每个执行器对应1个规则或者规则集
     */
    private RiskRule riskRule;
    /**
     * 指向下一个执行器
     */
    private Executor nextExecutor;
    /**
     * 执行此规则耗时（毫秒）
     */
    private int costTime = 0;
    /**
     * 命中规则的数量(一个规则集可能包含多个具体规则)
     */
    private int hitNum = 0;

    public Executor() {
    }


    /**
     * 执行规则之前，进行数据准备工作
     */
    public void preExecute(AutoApproveContext context) {
        String ruleId = getRuleId();
        //执行具体规则执行器前，先初始化规则
        if (StringUtils.isNotBlank(ruleId)) {
            CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
            RiskRule currentRiskRule = creditDataInvokeService.getRiskRule(ruleId, context.getModelId());
            setRuleName(currentRiskRule.getRuleName());
            setRiskRule(currentRiskRule);
        }
        //执行执行器链条时，无对应规则
    }

    /**
     * 执行规则，具体如何执行，由每个执行者自行实现
     */
    public abstract void doExecute(AutoApproveContext context);

    /**
     * 执行规则
     */
    public void execute(AutoApproveContext context) {
        long start = System.currentTimeMillis();
        init();
//		try {
//			preExecute(context);
//			doExecute(context);
//			afterExecute(context);
//		}catch (Exception e){
//			HitRule hitRule = createHitRule(getRiskRule());
//			String message = e.getMessage();
//			if (StringUtils.isNotBlank(message)){
//				message = String.format("规则校验失败，错误信息：%s",message);
//			}else{
//				message = "规则校验失败";
//			}
//			hitRule.setRemark(message);
//			addHitRule(context,hitRule);
//			e.printStackTrace();
//		}
        preExecute(context);
        doExecute(context);
        afterExecute(context);
        long end = System.currentTimeMillis();
        setCostTime(new Long(end - start).intValue());
    }

    /**
     * 执行规则之后，保存结果和日志
     */
    public void afterExecute(AutoApproveContext context) {
        AutoApproveLog log = new AutoApproveLog();
        log.setApplyId(context.getApplyId());
        log.setUserId(context.getUserId());
        log.setCostTime(getCostTime());
        log.setName(context.getUserName());
        log.setHitNum(hitNum);
        RiskRule rule = getRiskRule();
        if (rule != null) {
            log.setRuleId(rule.getId());
            log.setRuleName(rule.getRuleName());
        }

    }

    /**
     * 在子类中配置规则编号
     */
    public abstract void init();

    /**
     * 自动审批数据服务
     *
     * @return
     */
    public CreditDataInvokeService getDataInvokeService() {
        CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
        return creditDataInvokeService;
    }

    /**
     * 自动审批日志服务
     *
     * @return
     */
    public AutoApproveLogService getLogService() {
        AutoApproveLogService logService = SpringContextHolder.getBean("autoApproveLogService");
        return logService;
    }

    /**
     * 是否拒绝贷款
     *
     * @param riskRank
     */
    public boolean isReject(String riskRank) {
        if (RiskRank.A.equals(riskRank) || RiskRank.B.equals(riskRank)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否直接通过自动审批
     *
     * @param riskRank
     */
    public boolean isAccept(String riskRank) {
        if (RiskRank.P.equals(riskRank)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否提交到黑名单
     *
     * @param riskRank
     */
    public boolean isToBlacklist(String riskRank) {
        if (RiskRank.A.equals(riskRank)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 记录命中的风控规则
     *
     * @param context
     * @param hitRule
     * @return
     */
    public HitRule addHitRule(AutoApproveContext context, HitRule hitRule) {
        hitRule.setUserId(context.getUserId());
        hitRule.setApplyId(context.getApplyId());
        hitRule.setName(context.getUserName());
        context.addHitRule(hitRule);
        //根据命中规则，决定后续执行策略
        consumeHitRule(hitRule, context);
//		HitRuleService hitRuleService = SpringContextHolder.getBean("hitRuleService");
//		hitRuleService.insert(hitRule);
        return hitRule;
    }

    /**
     * 记录命中的风控规则
     *
     * @param context
     * @param hitRuleList
     * @return
     */
    public List<HitRule> addHitRules(AutoApproveContext context, List<HitRule> hitRuleList) {
        if (hitRuleList != null && !hitRuleList.isEmpty()) {
            for (HitRule hitRule : hitRuleList) {
                hitRule.setParentRuleId(getRuleId());
                hitRule.setName(context.getUserName());
                addHitRule(context, hitRule);
            }
        }
        return hitRuleList;
    }

    /**
     * 根据风控规则创建命中规则信息
     *
     * @param riskRule
     * @return
     */
    public HitRule createHitRule(RiskRule riskRule) {
        HitRule hitRule = BeanMapper.map(riskRule, HitRule.class);
        hitRule.setId(null);
        hitRule.setRuleId(riskRule.getId());
        return hitRule;
    }

    /**
     * 根据命中规则，决定后续执行策略
     *
     * @param hitRule
     */
    public void consumeHitRule(HitRule hitRule, AutoApproveContext context) {
        if (hitRule != null && StringUtils.isNotBlank(hitRule.getRiskRank())) {
            String riskRank = hitRule.getRiskRank();
            if (isAccept(riskRank)) {
                finish();
            } else if (isReject(riskRank)) {
                finish();
            }
            if (isToBlacklist(riskRank)) {
                toBlacklist(hitRule, context);
            }
        }
    }

    /**
     * 是否为自有规则
     *
     * @param hitRule
     * @return
     */
    public static boolean isInnerRule(HitRule hitRule) {
        boolean match = false;
        if (StringUtils.equals(RuleIds.R1003, hitRule.getParentRuleId())) {
            match = true;
        }
        return match;
    }


    /**
     * 如果命中A类规则，将用户放入黑名单
     *
     * @param userId
     */
    public void toBlacklist(HitRule hitRule, AutoApproveContext context) {
        BlacklistService blacklistService = SpringContextHolder.getBean("blacklistService");
        long count = blacklistService.findBlacklistCount(context.getApplyInfo().getUserId());
        if (count > 0) {
            logger.info("用户已在黑名单：userId={}", context.getApplyInfo().getUserId());
        } else {
            logger.info("将用户放入黑名单：userId={}", context.getApplyInfo().getUserId());
            blacklistService.saveBlacklist(hitRule, context);
        }
    }

    /**
     * 终止执行后续任务
     */
    public void finish() {
        this.setNextExecutor(null);
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public RiskRule getRiskRule() {
        return riskRule;
    }

    public void setRiskRule(RiskRule riskRule) {
        this.riskRule = riskRule;
    }

    public Executor getNextExecutor() {
        return nextExecutor;
    }

    public void setNextExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public int getHitNum() {
        return hitNum;
    }

    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    public int addHitNum(int hitNum) {
        this.hitNum = this.hitNum + hitNum;
        return this.hitNum;
    }

    public boolean isHit() {
        return hitNum > 0;
    }

}