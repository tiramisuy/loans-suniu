package com.rongdu.loans.risk.executor;

import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 设备通讯录联系人号码重复比例较大
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030005Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030005);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		DeviceContactVO vo = getDataInvokeService().getBaiqishiContactInfo(context);
		List<Contact>  contactList = vo.getContactsInfo();

		//命中的规则
		HitRule hitRule = checkContactsInfo(contactList);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),hitNum,evidence);

	}

	/**
	 * 设备通讯录联系人重复号码比例＞20%
	 * @param list
	 * @return
     */
	private HitRule checkContactsInfo(List<Contact> list) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (list==null){
			setHitNum(1);
			hitRule.setRemark("设备通讯录为空");
			return hitRule;
		}
		Set<String> mobiles = new HashSet<>();
		for (Contact item:list){
			mobiles.add(item.getMobile());
		}
		//不重复的号码数量
		int count = mobiles.size();
		//总共电话号码数量
		int total = list.size();
		double threshold = 0.20D;
		double rate = 1-(double)count/total;
		if (rate>threshold){
			setHitNum(1);
		}
		String msg = String.format("电话号码总数：%s，不重复的号码数量：%s，重复比例：%s",total,count,rate);
		hitRule.setRemark(msg);
		return  hitRule;
	}

}
