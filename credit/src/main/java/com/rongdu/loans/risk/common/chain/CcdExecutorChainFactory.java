package com.rongdu.loans.risk.common.chain;

import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.common.RiskContants;
import com.rongdu.loans.risk.executor.R10010001Executor;
import com.rongdu.loans.risk.executor.R10020001Executor;
import com.rongdu.loans.risk.executor.R10030001Executor;
import com.rongdu.loans.risk.executor.R10030002Executor;
import com.rongdu.loans.risk.executor.R10030003Executor;
import com.rongdu.loans.risk.executor.R10030004Executor;
import com.rongdu.loans.risk.executor.R10030005Executor;
import com.rongdu.loans.risk.executor.R10030006Executor;
import com.rongdu.loans.risk.executor.R10030007Executor;
import com.rongdu.loans.risk.executor.R10030008Executor;
import com.rongdu.loans.risk.executor.R10030009Executor;
import com.rongdu.loans.risk.executor.R10030010Executor;
import com.rongdu.loans.risk.executor.R10030011Executor;
import com.rongdu.loans.risk.executor.R10030012Executor;
import com.rongdu.loans.risk.executor.R10030013Executor;
import com.rongdu.loans.risk.executor.R10030014Executor;
import com.rongdu.loans.risk.executor.R10030015Executor;
import com.rongdu.loans.risk.executor.R10030016Executor;
import com.rongdu.loans.risk.executor.R10030017Executor;
import com.rongdu.loans.risk.executor.R10030018Executor;
import com.rongdu.loans.risk.executor.R10030019Executor;
import com.rongdu.loans.risk.executor.R10030020Executor;
import com.rongdu.loans.risk.executor.R10030021Executor;
import com.rongdu.loans.risk.executor.R10030023Executor;
import com.rongdu.loans.risk.executor.R10030024Executor;
import com.rongdu.loans.risk.executor.R10030025Executor;
import com.rongdu.loans.risk.executor.R10030026Executor;
import com.rongdu.loans.risk.executor.R10030027Executor;
import com.rongdu.loans.risk.executor.R10030028Executor;
import com.rongdu.loans.risk.executor.R10030030Executor;
import com.rongdu.loans.risk.executor.R10030033Executor;
import com.rongdu.loans.risk.executor.R10030034Executor;
import com.rongdu.loans.risk.executor.R10030036Executor;
import com.rongdu.loans.risk.executor.R10030037Executor;
import com.rongdu.loans.risk.executor.R10030044Executor;
import com.rongdu.loans.risk.executor.R10030045Executor;
import com.rongdu.loans.risk.executor.R10030046Executor;
import com.rongdu.loans.risk.executor.R10030047Executor;
import com.rongdu.loans.risk.executor.R10030048Executor;
import com.rongdu.loans.risk.executor.R10030049Executor;
import com.rongdu.loans.risk.executor.R10030050Executor;
import com.rongdu.loans.risk.executor.R10030051Executor;
import com.rongdu.loans.risk.executor.R10030052Executor;
import com.rongdu.loans.risk.executor.R10030053Executor;
import com.rongdu.loans.risk.executor.R10030054Executor;
import com.rongdu.loans.risk.executor.R10030055Executor;
import com.rongdu.loans.risk.executor.R10030056Executor;
import com.rongdu.loans.risk.executor.R10030057Executor;
import com.rongdu.loans.risk.executor.R10030058Executor;
import com.rongdu.loans.risk.executor.R10040003Executor;
import com.rongdu.loans.risk.executor.R1009Executor;
import com.rongdu.loans.risk.executor.R1011Executor;
import com.rongdu.loans.risk.executor.R1012Executor;
import com.rongdu.loans.risk.executor.R1014Executor;
import com.rongdu.loans.risk.executor.R10150001Executor;
import com.rongdu.loans.risk.executor.R10160001Executor;
import com.rongdu.loans.risk.executor.R10160002Executor;
import com.rongdu.loans.risk.executor.R10170001Executor;
import com.rongdu.loans.risk.executor.R10170002Executor;
import com.rongdu.loans.risk.executor.R10170003Executor;
import com.rongdu.loans.risk.executor.R10170004Executor;
import com.rongdu.loans.risk.executor.R10170005Executor;
import com.rongdu.loans.risk.executor.R10170006Executor;
import com.rongdu.loans.risk.executor.R10170007Executor;
import com.rongdu.loans.risk.executor.R10170008Executor;

public class CcdExecutorChainFactory {
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
		chains.addExecutor(new R10150001Executor());
		// 三方征信1黑名单
		chains.addExecutor(new R10160001Executor());
		chains.addExecutor(new R10160002Executor());
		// 聚宝钱包自有规则
		chains.addExecutor(new R10030001Executor());
		chains.addExecutor(new R10030002Executor());
		chains.addExecutor(new R10030003Executor());
		chains.addExecutor(new R10030006Executor());
		chains.addExecutor(new R10030007Executor());
		chains.addExecutor(new R10030018Executor());
		chains.addExecutor(new R10030019Executor());
		chains.addExecutor(new R10030028Executor());
		chains.addExecutor(new R10030036Executor());
		chains.addExecutor(new R10030037Executor());
		chains.addExecutor(new R10030044Executor());
		chains.addExecutor(new R10030045Executor());
		chains.addExecutor(new R10030046Executor());
		chains.addExecutor(new R10030048Executor());
		chains.addExecutor(new R10030049Executor());
		chains.addExecutor(new R10030052Executor());
		chains.addExecutor(new R10030053Executor());
		chains.addExecutor(new R10030054Executor());
		chains.addExecutor(new R10030055Executor());
		chains.addExecutor(new R10030056Executor());
		chains.addExecutor(new R10030057Executor());

		chains.addExecutor(new R10030004Executor());
		chains.addExecutor(new R10030005Executor());
		chains.addExecutor(new R10030008Executor());
		chains.addExecutor(new R10030009Executor());
		chains.addExecutor(new R10030010Executor());
		chains.addExecutor(new R10030011Executor());
		chains.addExecutor(new R10030012Executor());
		chains.addExecutor(new R10030013Executor());
		chains.addExecutor(new R10030014Executor());
		chains.addExecutor(new R10030015Executor());
		chains.addExecutor(new R10030016Executor());
		chains.addExecutor(new R10030017Executor());
		chains.addExecutor(new R10030020Executor());
		chains.addExecutor(new R10030021Executor());
		chains.addExecutor(new R10030023Executor());
		chains.addExecutor(new R10030024Executor());
		chains.addExecutor(new R10030025Executor());
		chains.addExecutor(new R10030026Executor());
		chains.addExecutor(new R10030027Executor());
		chains.addExecutor(new R10030030Executor());
		chains.addExecutor(new R10030033Executor());
		chains.addExecutor(new R10030047Executor());
		chains.addExecutor(new R10030050Executor());
		chains.addExecutor(new R10030051Executor());
		chains.addExecutor(new R10030058Executor());
		// 白骑士资信报告黑名单
		chains.addExecutor(new R1014Executor());
		// 白骑士
		chains.addExecutor(new R10030034Executor());
		chains.addExecutor(new R10040003Executor());
		chains.addExecutor(new R1012Executor());
		// 百融
		chains.addExecutor(new R1009Executor());
		chains.addExecutor(new R1011Executor());

		// 新颜
		// chains.addExecutor(new R10170001Executor());
		// chains.addExecutor(new R10170002Executor());
		// chains.addExecutor(new R10170003Executor());
		// chains.addExecutor(new R10170004Executor());
		// chains.addExecutor(new R10170005Executor());
		// chains.addExecutor(new R10170006Executor());
		// chains.addExecutor(new R10170007Executor());
		// chains.addExecutor(new R10170008Executor());
		// 同盾
		// chains.addExecutor(new R10030035Executor());
		// chains.addExecutor(new R10040004Executor());
		// chains.addExecutor(new R1013Executor());
		// 白骑士和同盾都建议通过
		// chains.addExecutor(new R10000001Executor());
		return chains;
	}

	/**
	 * 复贷的规则及顺序
	 * 
	 * @return
	 */
	private static ExecutorChain createReloanExecutorChain() {
		ExecutorChain chains = new ExecutorChain();
		// 申请单完整性检查
		chains.addExecutor(new R10010001Executor());
		// 聚宝钱包黑名单
		chains.addExecutor(new R10020001Executor());
		// 口袋黑名单
		chains.addExecutor(new R10150001Executor());
		// 三方征信1黑名单
		chains.addExecutor(new R10160001Executor());
		// 聚宝钱包自有规则
		chains.addExecutor(new R10030001Executor());
		chains.addExecutor(new R10030002Executor());
		chains.addExecutor(new R10030003Executor());
		chains.addExecutor(new R10030006Executor());
		chains.addExecutor(new R10030007Executor());
		chains.addExecutor(new R10030018Executor());
		chains.addExecutor(new R10030019Executor());
		chains.addExecutor(new R10030028Executor());
		chains.addExecutor(new R10030036Executor());
		chains.addExecutor(new R10030037Executor());
		chains.addExecutor(new R10030044Executor());
		chains.addExecutor(new R10030045Executor());
		chains.addExecutor(new R10030046Executor());
		chains.addExecutor(new R10030048Executor());
		chains.addExecutor(new R10030049Executor());
		chains.addExecutor(new R10030052Executor());
		chains.addExecutor(new R10030053Executor());
		chains.addExecutor(new R10030054Executor());
		chains.addExecutor(new R10030055Executor());
		chains.addExecutor(new R10030056Executor());
		chains.addExecutor(new R10030057Executor());

		chains.addExecutor(new R10030004Executor());
		chains.addExecutor(new R10030005Executor());
		chains.addExecutor(new R10030008Executor());
		chains.addExecutor(new R10030009Executor());
		chains.addExecutor(new R10030010Executor());
		chains.addExecutor(new R10030011Executor());
		chains.addExecutor(new R10030012Executor());
		chains.addExecutor(new R10030013Executor());
		chains.addExecutor(new R10030014Executor());
		chains.addExecutor(new R10030015Executor());
		chains.addExecutor(new R10030016Executor());
		chains.addExecutor(new R10030017Executor());
		chains.addExecutor(new R10030020Executor());
		chains.addExecutor(new R10030021Executor());
		chains.addExecutor(new R10030023Executor());
		chains.addExecutor(new R10030024Executor());
		chains.addExecutor(new R10030025Executor());
		chains.addExecutor(new R10030026Executor());
		chains.addExecutor(new R10030027Executor());
		chains.addExecutor(new R10030030Executor());
		chains.addExecutor(new R10030033Executor());
		chains.addExecutor(new R10030047Executor());
		chains.addExecutor(new R10030050Executor());
		chains.addExecutor(new R10030051Executor());
		chains.addExecutor(new R10030058Executor());
		// 白骑士资信报告黑名单
		chains.addExecutor(new R1014Executor());
		// 白骑士
		chains.addExecutor(new R10030034Executor());
		chains.addExecutor(new R10040003Executor());
		chains.addExecutor(new R1012Executor());
		// 百融
		chains.addExecutor(new R1009Executor());
		chains.addExecutor(new R1011Executor());
		// 同盾
		// chains.addExecutor(new R10030035Executor());
		// chains.addExecutor(new R10040004Executor());
		// chains.addExecutor(new R1013Executor());
		// 白骑士和同盾都建议通过
		// chains.addExecutor(new R10000001Executor());
		return chains;
	}

}
