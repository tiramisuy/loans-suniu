package com.rongdu.loans.risk.common.chain;

import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.common.RiskContants;
import com.rongdu.loans.risk.executor.R10030034Executor;
import com.rongdu.loans.risk.executor.R10030069Executor;
import com.rongdu.loans.risk.executor.R10040003Executor;
import com.rongdu.loans.risk.executor.R1012Executor;
import com.rongdu.loans.risk.executor.R10170001Executor;
import com.rongdu.loans.risk.executor.R10170009Executor;
import com.rongdu.loans.risk.executor.R10170010Executor;
import com.rongdu.loans.risk.executor.xjbk.R10010001Executor;
import com.rongdu.loans.risk.executor.xjbk.R10020001Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030001Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030006Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030028Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030044Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030052Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030057Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030058Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030059Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030061Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030062Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030063Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030064Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030065Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030066Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030067Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030068Executor;
import com.rongdu.loans.risk.executor.xjbk.R1009Executor;
import com.rongdu.loans.risk.executor.xjbk.R1011Executor;

public class XjdxjbkExecutorChainFactory {
	public static ExecutorChain createExecutorChain(AutoApproveContext context) {
		ExecutorChain chains = null;
		UserInfoVO userInfo = context.getUserInfo();
		if (userInfo != null && userInfo.getLoanSuccCount() != null && userInfo.getLoanSuccCount() > 0) {
			// 复贷
			chains = createReloanExecutorChain();
			context.put(RiskContants.KEY_BAIQISHI_EVENT_TYPE, BaiqishiConfig.decision_event_type_reloan);
		} else {
			// 默认
			chains = createFirstExecutorChain();
			context.put(RiskContants.KEY_BAIQISHI_EVENT_TYPE, BaiqishiConfig.decision_event_type_default);
		}
		return chains;
	}

	/**
	 * 首贷的规则及顺序
	 * 
	 * @return
	 */
	private static ExecutorChain createFirstExecutorChain() {
		ExecutorChain chains = new ExecutorChain();
		// 申请单完整性检查
		chains.addExecutor(new R10010001Executor());
		// 聚宝钱包黑名单
		chains.addExecutor(new R10020001Executor());
		// 口袋黑名单
		// chains.addExecutor(new R10150001Executor());
		// 三方征信1黑名单
		// chains.addExecutor(new R10160001Executor());
		// chains.addExecutor(new R10160002Executor());

		chains.addExecutor(new R10030001Executor());// 申请人年龄＜=19或年龄＞=41
		chains.addExecutor(new R10030006Executor());// 入网时间少于6个月
		// chains.addExecutor(new R10030002Executor());//手机号码是否经过实名认证
		// 民族维吾尔族和藏族 （无）
		chains.addExecutor(new R10030061Executor());// 运营商账单<4个月
		chains.addExecutor(new R10030062Executor());// 连续3天关机次数>=2次
		chains.addExecutor(new R10030063Executor());// 紧急联系人在通话详单中的个数<1个
		chains.addExecutor(new R10030028Executor());// 历史逾期超过15天
		chains.addExecutor(new R10030064Executor());// 非活跃总天数>90
		chains.addExecutor(new R10030044Executor());// 互通电话号码较低
		chains.addExecutor(new R10030065Executor());// 贷款类通话次数>=22
		// 催收类通话总数>=11---贷款类号码联系情况 （无）
		chains.addExecutor(new R10030066Executor());// 110通话次数
		chains.addExecutor(new R10030067Executor());// 平均月消费>400
		chains.addExecutor(new R10030068Executor());// 夜间活动情况>20%
		// 同一个device_id对应不同申请手机号的个数 （无）
		chains.addExecutor(new R10030052Executor());
		chains.addExecutor(new R10030057Executor());
		chains.addExecutor(new R10030058Executor());

		chains.addExecutor(new R10030069Executor());// 该身份证绑定其他手机号有未完成订单

		// 白骑士
		chains.addExecutor(new R10030034Executor());
		chains.addExecutor(new R10040003Executor());
		chains.addExecutor(new R1012Executor());

		// 百融
		chains.addExecutor(new R1009Executor());
		chains.addExecutor(new R1011Executor());
		// // 新颜
		chains.addExecutor(new R10170001Executor());// R10170009Executor替代
		// chains.addExecutor(new R10170009Executor());
		chains.addExecutor(new R10170010Executor());
		// chains.addExecutor(new R10170002Executor());
		// chains.addExecutor(new R10170003Executor());
		// chains.addExecutor(new R10170004Executor());
		// chains.addExecutor(new R10170005Executor());
		// chains.addExecutor(new R10170006Executor());
		// chains.addExecutor(new R10170007Executor());
		// chains.addExecutor(new R10170008Executor());
		// 商汤人脸识别
		chains.addExecutor(new R10030059Executor());

		return chains;
	}

	/**
	 * 复贷的规则及顺序
	 * 
	 * @return
	 */
	private static ExecutorChain createReloanExecutorChain() {
		ExecutorChain chains = new ExecutorChain();
		// 聚宝钱包黑名单
		chains.addExecutor(new R10020001Executor());
		// 口袋黑名单
		// chains.addExecutor(new R10150001Executor());
		// 三方征信1黑名单
		// chains.addExecutor(new R10160001Executor());
		// chains.addExecutor(new R10160002Executor());
		// 聚宝钱包自有规则
		chains.addExecutor(new R10030001Executor());
		chains.addExecutor(new R10030028Executor());

		// 白骑士
		chains.addExecutor(new R10030034Executor());
		chains.addExecutor(new R10040003Executor());
		chains.addExecutor(new R1012Executor());
		// 百融
		chains.addExecutor(new R1009Executor());
		chains.addExecutor(new R1011Executor());
		// 新颜
		chains.addExecutor(new R10170001Executor());// R10170009Executor替代
		// chains.addExecutor(new R10170009Executor());
		chains.addExecutor(new R10170010Executor());
		return chains;
	}

}
