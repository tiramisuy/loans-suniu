package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 设备通讯录联系含有敏感词
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030017Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030017);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		DeviceContactVO vo = creditDataInvokeService.getBaiqishiContactInfo(context);
		List<Contact> list = vo.getContactsInfo();
		//命中的规则
		HitRule hitRule = checkContactsInfo(list);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
	}

	/**
	 * 设备通讯录联系含有敏感词
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
		String[] array = {"骗贷","伪造","催收","催钱","黑户","贷款中介","口子","网贷中介","小贷中介"};
		List list1 = Arrays.asList(array);
		String name = "";
		int count = 0;
		List<String> extInfo = new ArrayList<>();
		for (Contact item:list){
			name = item.getName();
			if (containsAny(name,list1)){
				extInfo.add(name);
				count++;
			}
		}
		if (count>=1){
			setHitNum(1);
		}
		hitRule.setValue(String.valueOf(count));
		String msg = String.format("匹配数量：%s，详情如下：%s",count, JsonMapper.toJsonString(extInfo));
		hitRule.setRemark(msg);
		return  hitRule;
	}

	private boolean containsAny(String name, List<String> list) {
		boolean match = false;
		for (String item:list){
			if (StringUtils.contains(name,item)){
				return true;
			}
		}
		return match;
	}


}
