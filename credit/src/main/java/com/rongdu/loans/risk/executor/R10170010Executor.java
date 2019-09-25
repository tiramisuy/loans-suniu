package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.xinyan.entity.RadarDetail;
import com.rongdu.loans.xinyan.service.RadarDetailService;
import com.rongdu.loans.xinyan.vo.RadarResultDetailVO;
import com.rongdu.loans.xinyan.vo.RadarVO;

/**
 * 新颜-全景雷达聚宝袋得分
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10170010Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10170010);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		if(true) {
			return;
		}
		// 全景雷达定制单期的，多期不跑
		LoanApplySimpleVO applyVO = context.getApplyInfo();
		if (applyVO.getTerm() > 1)
			return;
		// 加载风险分析数据
		RadarVO vo = null;
		try {
			vo = getDataInvokeService().getXinyanRadar(context);
		} catch (Exception e) {
			logger.error("新颜全景雷达查询异常", e);
		}
		if (vo == null)
			return;
		// 命中的规则
		HitRule hitRule = check(vo, context);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), hitNum, evidence);
	}

	/**
	 * 聚宝袋得分
	 * 
	 * @param RadarVO
	 * @return
	 */
	private HitRule check(RadarVO vo, AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null && vo.isSuccess()) {
			if ("0".equals(vo.getData().getCode())) {
				RadarResultDetailVO d = vo.getData().getResult_detail();
				int score = Integer.parseInt(d.getTotal_score());
				String msg = String.format("聚宝袋分：%s", score);
				setHitNum(1);
				hitRule.setRemark(msg);

				RadarDetailService radarDetailService = SpringContextHolder.getBean(RadarDetailService.class);
				RadarDetail detail = new RadarDetail();
				detail.preInsert();
				detail.setApplyId(context.getApplyId());
				detail.setUserId(context.getUserId());
				detail.setUserName(context.getUserName());
				detail.setMobile(context.getUser().getMobile());
				detail.setScore(score);
				detail.setRemark(JsonMapper.toJsonString(d));
				radarDetailService.save(detail);
			}
		}
		return hitRule;
	}
}
