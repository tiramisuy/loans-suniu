package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 紧急联系人都不在申请人工作所在地
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030015Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030015);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		String workArea = AutoApproveUtils.getWorkArea(context.getUserInfo());
		List<ReportEmergencyContact> dataList = vo.getData().getEmergencyContacts();

		//命中的规则
		HitRule hitRule = checkEmergencyContacts(workArea,dataList);
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
	 * 申请人填写的常用联系人手机号码归属地没有一位在申请人手机号码所在地
	 * @param list
	 * @return
     */
	private HitRule checkEmergencyContacts(String workArea,List<ReportEmergencyContact> list) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		int threshold = 1;
		String connectCity = null;
		Map<String,String> extInfo = new LinkedHashMap<>();
		if (list!=null&&workArea!=null){
			workArea = AutoApproveUtils.filterArea(workArea);
			for (ReportEmergencyContact item:list){
				connectCity = item.getBelongTo();
				connectCity = AutoApproveUtils.filterArea(connectCity);
				extInfo.put(item.getMobile(),item.getBelongTo());
				if(StringUtils.contains(workArea,connectCity)){
					count++;
				}
			}
			if (count<threshold){
				setHitNum(1);
				hitRule.setValue(String.valueOf(count));
			}
			String msg = String.format("紧急联系人数量：%s，工作所在地：%s，匹配数量：%s，详情如下：%s",list.size(),workArea,count,JsonMapper.toJsonString(extInfo));
			hitRule.setRemark(msg);

		}else{
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
			String msg = (list==null)?"紧急联系人为空":"紧急联系人数量为："+list.size();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}


}
