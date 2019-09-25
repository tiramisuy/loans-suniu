package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.vo.DeviceInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Android设备安装了过多可疑的应用程序 数据来源于：白骑士资信云报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030030Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030030);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder
				.getBean("creditDataInvokeService");
		DeviceInfoVO vo = creditDataInvokeService
				.getBaiqishiDeviceInfo(context);
		if (vo == null || !vo.isSuccess() || vo.getResultData() == null) {
			return;
		}
		String source = context.getApplyInfo().getSource();
		Map<String, Object> deviceInfo = vo.getResultData();

		// 命中的规则
		HitRule hitRule = checkDeviceInfo(deviceInfo, source);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				getHitNum(), evidence);
	}

	/**
	 * Android设备安装有可疑的应用程序（大于等于5个，小于20个）
	 * 
	 * @param deviceInfo
	 * @return
	 */
	private HitRule checkDeviceInfo(Map<String, Object> deviceInfo,
			String source) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		if (StringUtils.equals("2", source)) {
			String appNameString = (String) deviceInfo.get("appNames");
			if (StringUtils.isNotBlank(appNameString)) {
				String[] appNames = StringUtils.split(appNameString, ",");
				List<String> appNameList = Arrays.asList(appNames);
				List<String> sensltiveWords = containsSensltiveWords(appNameList);
				List<String> forbiddenWords = containsForbiddenWords(appNameList);
				if ((sensltiveWords.size() >= 5 && sensltiveWords.size() < 20)
						|| forbiddenWords.size() > 0) {
					setHitNum(1);
				}
				count = sensltiveWords.size() + forbiddenWords.size();
				hitRule.setValue(String.valueOf(count));
				String msg = String.format("匹配数量：%s，敏感应用：%s，禁止应用：%s", count,
						JsonMapper.toJsonString(sensltiveWords),
						JsonMapper.toJsonString(forbiddenWords));
				hitRule.setRemark(msg);
			}

		} else {
			hitRule.setRemark("非Android设备，不进行验证");
		}
		return hitRule;
	}

	private List<String> containsForbiddenWords(List<String> appNameList) {
		List<String> matchWords = new ArrayList<>();
		List<String> words = getForbiddenWords();
		for (String appName : appNameList) {
			if (words.contains(appName)) {
				matchWords.add(appName);
			}
		}
		return matchWords;
	}

	private List<String> containsSensltiveWords(List<String> appNameList) {
		List<String> matchWords = new ArrayList<>();
		List<String> words = getSensltiveWords();
		for (String appName : appNameList) {
			if (words.contains(appName)) {
				matchWords.add(appName);
			}
		}
		return matchWords;

	}

	/**
	 * 可疑的应用名称
	 * 
	 * @return
	 */
	public List<String> getSensltiveWords() {
		String[] array = { "借", "贷", "钱包", "现金", "分期", "白条", "借条", "信而富", "捷信",
				"用钱宝", "闪银", "花无缺", "51人品", "马上金融", "牛呗", "千百块", "豆豆钱", "零零期",
				"钱站", "给你花", "玖富", "先花花", "莫愁花", "付壹代", "靠谱鸟", "钱有路", "钱袋",
				"乐花花", "读秒", "零用金", "快金", "小意思", "先花一亿元", "还呗", "招联金融", "务工宝",
				"省呗", "融360", "u族", "月光蓝卡", "小安时代", "任性付", "用钱呗", "嗨付", "简融",
				"信用花", "小钱", "够花", "任性花", "人人花", "淘钱宝", "先花钱", "月光足", "江湖救急",
				"替你还", "信金宝", "unifi", "月光宝盒", "月光族", "用钱无忧", "替你还", "及时雨",
				"量化派", "南京银行", "钞市", "秒啦", "安家派", "指还王", "随手微利", "乐花", "钱师爷",
				"小树普惠", "急用钱", "还卡超人", "钱到", "包银消费", "快来钱", "中安信业", "闪电周转",
				"轻松有钱", "币下", "缺钱么", "嗨钱", "95金融", "中滕信", "金稻草", "零用钱", "好快信",
				"要钱花", "钱庄", "51返呗", "钱急送", "有额度", "马上有钱", "给你用", "钱到到", "微钱宝",
				"淘钱宝", "钱宝宝", "给你钱", "趣花花" };
		List<String> list = Arrays.asList(array);
		return list;
	}

	/**
	 * 被禁止的应用名称
	 * 
	 * @return
	 */
	public List<String> getForbiddenWords() {
		String[] array = { "葡京", "永利", "澳门", "老虎机", "百家乐", "赌博", "赌庄", "博彩",
				"赌场", "轮盘赌" };
		List<String> list = Arrays.asList(array);
		return list;
	}
}
