package com.rongdu.loans.risk.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 短号联系过多
 *
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030049Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030049);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBase(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null) {
            return;
        }
        // 加载风险分析数据
        JDQReport xianJinBaiKaBase = getDataInvokeService().getjdqReport(context);
        //6个月通话记录
        List<MoxieTelecomReport.CallContactDetailBean> ccmList = vo.getMoxieTelecomReport().call_contact_detail;
        // 命中的规则
        HitRule hitRule = checkShortNumber(xianJinBaiKaBase);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), evidence);
    }


    /**
     * 近6个月通话记录Top10里电话号码位数小于8的个数>=3
     *
     * @return
     */
    private HitRule checkShortNumber(JDQReport jdqReport) {

        HitRule hitRule = createHitRule(getRiskRule());

        List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
        List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
        int remaider = contactChecks.size() % 2; // 余数
        int number = contactChecks.size() / 2; // 商
        int offset = 0;// 偏移量
        for (int i = 0; i < 2; i++) {
            List<ContactCheck> value = null;
            if (remaider > 0) {
                value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
            }
            List<ContactCheck> value1 = new ArrayList<ContactCheck>();
            value1.addAll(value);
            result.add(value1);
        }
        int size = result.get(0).size();
        for (ContactCheck contactCheck : result.get(1)) {
            contactCheck.setIndex(size);
            size++;
        }

        int index = 0;
        int countIndex = 0;
        for (int i = 0; i < contactChecks.size(); i++) {
            ContactCheck check = contactChecks.get(i);
            if (check.getMobile().length() < 8) {
                countIndex++;
            }
            index++;
            if (index > 9) {
                break;
            }
        }
        if (countIndex >= 3) {
            setHitNum(1);
        }

        String msg = String.format("近6个月通话记录Top10里电话号码位数小于8的个数>=3的数量有：%s", countIndex);
        hitRule.setRemark(msg);
        return hitRule;
    }


//	@Override
//	public void doExecute(AutoApproveContext context) {
//		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
//		// 近6个月通话记录top10
//		List<ReportMnoCcm> ccmList = AutoApproveUtils.getTopCcmList(vo.getData().getMnoCommonlyConnectMobiles(), 10);
//
//		// 命中的规则
//		HitRule hitRule = checkShortNumber(ccmList);
//		// 决策依据
//		String evidence = hitRule.getRemark();
//		// 命中规则的数量
//		int hitNum = getHitNum();
//		if (hitNum > 0) {
//			addHitRule(context, hitRule);
//		}
//		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
//				context.getApplyId(), getHitNum(), evidence);
//	}
//
//	/**
//	 * 近6个月通话记录Top10里电话号码位数小于8的个数>=3
//	 *
//	 * @return
//	 */
//	private HitRule checkShortNumber(List<ReportMnoCcm> ccmList) {
//		HitRule hitRule = createHitRule(getRiskRule());
//		int threshold = 3;
//		int count = 0;
//		List<String> extInfo = new ArrayList<String>();
//		for (ReportMnoCcm ccm : ccmList) {
//			String ccmMobile = ccm.getMobile();
//			if (StringUtils.isNotBlank(ccmMobile) && ccmMobile.length() < 8) {
//				count++;
//				extInfo.add(ccmMobile);
//			}
//		}
//		if (count >= threshold) {
//			setHitNum(1);
//			hitRule.setValue(String.valueOf(count));
//		}
//		String msg = String.format("短号数量：%s，详情如下：%s", count, JsonMapper.toJsonString(extInfo));
//		hitRule.setRemark(msg);
//		return hitRule;
//	}

}