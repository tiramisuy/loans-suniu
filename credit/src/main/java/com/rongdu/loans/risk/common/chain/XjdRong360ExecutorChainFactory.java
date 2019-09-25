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
import com.rongdu.loans.risk.executor.R10160002Executor;
import com.rongdu.loans.risk.executor.R10170009Executor;
import com.rongdu.loans.risk.executor.R10170010Executor;
import com.rongdu.loans.risk.executor.rong360.*;

/**
 * @Title: XjdRong360ExecutorChainFactory.java
 * @Package com.rongdu.loans.risk.common.chain
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yuanxianchu
 * @date 2018年7月3日
 * @version V1.0
 */
public class XjdRong360ExecutorChainFactory {
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
		chains.addExecutor(new R10010001Executor());// 贷款申请信息不完整 1
		// 聚宝钱包黑名单
		chains.addExecutor(new R10020001Executor());
		// 口袋黑名单
		// chains.addExecutor(new R10150001Executor());
		// 三方征信1黑名单
		// chains.addExecutor(new R10160001Executor());
		// chains.addExecutor(new R10160002Executor());

		chains.addExecutor(new R10030001Executor());
		chains.addExecutor(new R10030004Executor());// 通讯录有效电话少于20个
		chains.addExecutor(new R10030006Executor());// 运营商手机号入网时长较短 1
		chains.addExecutor(new R10030028Executor());

		// chains.addExecutor(new R10030002Executor());//手机号未实名认证 1
		chains.addExecutor(new R10030044Executor());// 互通电话号码较低
//update 20190308		chains.addExecutor(new R10030049Executor());// 呼入或者呼出前10短号超过2个
		chains.addExecutor(new R10030052Executor());// 呼入呼出第一名,次数都小于30
//update 20190308		chains.addExecutor(new R10030057Executor());// 有过电话来往的小于10个
//update 20190308		chains.addExecutor(new R10030058Executor());// 联系次数至少有一个大于等于50次

		// 融360规则
		// chains.addExecutor(new R10190001Executor());
		chains.addExecutor(new R10190002Executor());
		chains.addExecutor(new R10190003Executor());
		chains.addExecutor(new R10190004Executor());
		chains.addExecutor(new R10190005Executor());
		chains.addExecutor(new R10190006Executor());
		chains.addExecutor(new R10190007Executor());
		chains.addExecutor(new R10190008Executor());
		chains.addExecutor(new R10190009Executor());
		chains.addExecutor(new R10190010Executor());
		chains.addExecutor(new R10190011Executor());
		chains.addExecutor(new R10190012Executor());
		chains.addExecutor(new R10190013Executor());
        chains.addExecutor(new R10190014Executor());
        chains.addExecutor(new R10190015Executor());
        chains.addExecutor(new R10190016Executor());
        chains.addExecutor(new R10190017Executor());
        chains.addExecutor(new R10190018Executor());
        chains.addExecutor(new R10190019Executor());

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
		chains.addExecutor(new R10170009Executor());
		chains.addExecutor(new R10170010Executor());
		// chains.addExecutor(new R10170002Executor());
		// chains.addExecutor(new R10170003Executor());
		// chains.addExecutor(new R10170004Executor());
		// chains.addExecutor(new R10170005Executor());
		// chains.addExecutor(new R10170006Executor());
		// chains.addExecutor(new R10170007Executor());
		// chains.addExecutor(new R10170008Executor());

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
