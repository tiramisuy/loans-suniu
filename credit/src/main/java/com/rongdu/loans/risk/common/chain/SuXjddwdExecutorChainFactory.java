package com.rongdu.loans.risk.common.chain;

import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.common.RiskContants;
import com.rongdu.loans.risk.executor.*;
import com.rongdu.loans.risk.executor.dwd.R10010001Executor;
import com.rongdu.loans.risk.executor.dwd.R10020001Executor;
import com.rongdu.loans.risk.executor.dwd.R10030001Executor;
import com.rongdu.loans.risk.executor.dwd.R10030004Executor;
import com.rongdu.loans.risk.executor.dwd.R10030005Executor;
import com.rongdu.loans.risk.executor.dwd.R10030017Executor;
import com.rongdu.loans.risk.executor.dwd.R10030019Executor;
import com.rongdu.loans.risk.executor.dwd.R10030028Executor;
import com.rongdu.loans.risk.executor.dwd.*;
import com.rongdu.loans.risk.executor.dwd.R10030049Executor;
import com.rongdu.loans.risk.executor.dwd.R1005Executor;
import com.rongdu.loans.risk.executor.dwd.R1009Executor;

public class SuXjddwdExecutorChainFactory {
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
			chains.addAll(XjdAuthExecutorChainFactory.createAuthExecutorChain(context));// 二次机审规则集
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
		// 宜信阿福
		chains.addExecutor(new R1005Executor());
		// 申请单完整性检查
		chains.addExecutor(new R10010001Executor());
		// 自有黑名单
		chains.addExecutor(new R10020001Executor());
		// 10030069 该身份证绑定其他手机号有未完成订单
		chains.addExecutor(new R10030069Executor());
		// 10030060 配偶有未完结订单，数据来源于：平台数据
		chains.addExecutor(new R10030060Executor());
		// 10030055 手机号码为170号段拒绝
		chains.addExecutor(new R10030055Executor());
		// 10030049 短号联系过多
		chains.addExecutor(new R10030049Executor());
		// 10030034 白骑士建议拒绝 根据白骑士反欺诈风控引擎进行决策，建议拒绝 ,
		chains.addExecutor(new R10030034Executor());
		// 10030029 上笔借款未结清，数据来源于：贷后数据
		chains.addExecutor(new R10030029Executor());
		// 10030028 还款记录出现5天及以上的逾期，数据来源于：贷后数据
		chains.addExecutor(new R10030028Executor());
		// 10030021 工作地址“省市”与居住地址“省市”不一致，数据来源于：申请表
		chains.addExecutor(new R10030021Executor());
		// 10030020 单位名称含“金融”“贷款”“小贷”关键字，数据来源于：申请数据
		chains.addExecutor(new R10030020Executor());
		// 10030019 申请人手机号归属所在地不合要求
		chains.addExecutor(new R10030019Executor());
		// 10030018 申请人工作单位所在地“西藏”“新疆”“宁德”，数据来源于：OCR
		chains.addExecutor(new R10030018Executor());
		// 10030017 设备通讯录联系含有敏感词
		chains.addExecutor(new R10030017Executor());
		// 10030005 设备通讯录联系人号码重复比例较大
		chains.addExecutor(new R10030005Executor());
		// 10030004 设备通讯录手机号码较少
		chains.addExecutor(new R10030004Executor());
		// 10030001 申请人年龄＜22或年龄＞46，数据来源于：OCR 数据来源于：自有解析数据
		chains.addExecutor(new R10030001Executor());

		// 10030077 申请人归属地与紧急联系人归属地不一致
		chains.addExecutor(new R10030077Executor());
		// 10030078 申请人近3个月互通过的号码数量小于等于5
		// 0621改：申请人近3个月互通过的号码数量过少 阈值提高到7
		chains.addExecutor(new R10030078Executor());
		// 10030079 申请人未呼叫过紧急联系人
		chains.addExecutor(new R10030079Executor());
		// 10030080 近1个月与紧急联系人通话次数小于等于1
		chains.addExecutor(new R10030080Executor());
		// 10030081 近3个月与紧急联系人通话次数小于等于2
		chains.addExecutor(new R10030081Executor());
		// 10030082 近6个月与紧急联系人通话次数小于等于5
		chains.addExecutor(new R10030082Executor());
		// 10030083 运营商手机号近6个月连续3天以上无通话记录次数大于等于4
		chains.addExecutor(new R10030083Executor());
		// 10030084 手机号组合过其它身份证个数大于等于4
		chains.addExecutor(new R10030084Executor());
		// 10030085 近6个月与紧急联系人通话次数大于等于600次，且通话时长小于等于180分钟
		chains.addExecutor(new R10030085Executor());
		// 10030086 近1个月通话次数top10的号码与通讯录名单匹配小于等于2
		chains.addExecutor(new R10030086Executor());
		// 10030087 近6个月通话次数top10的号码与通讯录名单匹配小于等于5
		chains.addExecutor(new R10030087Executor());
		// 10030088 近3个月通话总次数的月均值<=50或>=150
		// 改：调整为近3个月通话总次数的月均值<=50拒绝
		chains.addExecutor(new R10030088Executor());
		// 10030089 近3个月通话总时长的月均值<=0.2小时或>=75小时
		chains.addExecutor(new R10030089Executor());
		// 10030090 近6个月呼出总次数的月均值<=15或>=600
		chains.addExecutor(new R10030090Executor());
		// 10030091 近6个月呼入总时长的月均值<=0.2小时或>=75小时
		// 0621改：近6个月呼入总时长的月均值过高或过低 阈值要提高到低于1.3，高于16
		chains.addExecutor(new R10030091Executor());
		// 10030092 近6个月通话号码”数量众多“，大于100
		chains.addExecutor(new R10030092Executor());
		// 10030093 近6个月主叫号码命中命中本地黑名单的号码个数>=2
		chains.addExecutor(new R10030093Executor());
		// 10030094 近6个月主叫号码命中本地黑名单的通话次数>=3
		chains.addExecutor(new R10030094Executor());
		// 10030095 手机号组合过其它身份证个数大于等于1，小于等于3
		chains.addExecutor(new R10030095Executor());
		// 10030096 近6个月通话时长top10的号码与通讯录名单匹配数≦4
		chains.addExecutor(new R10030096Executor());
		// 10030097 近6个月呼入总次数的月均值<=15或>=600
		chains.addExecutor(new R10030097Executor());
		// 10030098 近6个月呼出总时长的月均值<=0.2小时或>=75小时
		chains.addExecutor(new R10030098Executor());
		// 10030099 申请人近6个月互通过号码”数量稀少“，小于10
		chains.addExecutor(new R10030099Executor());
		// 10030100 运营商前3名最常用联系人近半年累计通话时长有一人≤20分钟
		chains.addExecutor(new R10030100Executor());
		// 10030101 近6个月与填写的2个紧急联系人通话时长都小于等于20分钟
		chains.addExecutor(new R10030101Executor());
		// 10030102 身份证组合过其它电话个数大于等于4
		chains.addExecutor(new R10030102Executor());
		// 10030103 与直亲联系人第一次通话时间距进件时间小于150天
		chains.addExecutor(new R10030103Executor());
		// 10030104 入网时长小于60个月且与直亲近6个月通话次数小于180次，与直亲的平均通话时长小于60秒
		chains.addExecutor(new R10030104Executor());
		// 10030105 直亲的最近一次通话时间（月）距进件日期（月）大于1个月
		chains.addExecutor(new R10030105Executor());
		// 10030106 运营商手机号近3个月关机天数大于等于8
		// 0621改：运营商手机号近3个月关机天数较多 从C改为B，阈值改为20
		chains.addExecutor(new R10030106Executor());
		// 10030107 运营商手机号近6个月关机天数大于等于30
		chains.addExecutor(new R10030107Executor());
		// 10030108 运营商金融服务类机构黑名单身份证号检查
		chains.addExecutor(new R10030108Executor());
		// 10030109 身份证组合过其它电话个数大于等于1，小于等于3
		chains.addExecutor(new R10030109Executor());
		// 10030110 澳门电话通话情况经常联系
		chains.addExecutor(new R10030110Executor());
		// 10030111 贷款类号码联系情况经常联系
		chains.addExecutor(new R10030111Executor());
		// 10030112 运营商金融服务类机构黑名单电话号检查
		chains.addExecutor(new R10030112Executor());
		// 10030113 通讯录异号段占比情况较少
		chains.addExecutor(new R10030113Executor());
		// 10030114 设备通讯录号码与运营商近6个月的常用通话记录TOP20中一致数＜7
		chains.addExecutor(new R10030114Executor());
		// 10030115 通讯录电话号码小于等于10个
		chains.addExecutor(new R10030115Executor());
		// 10030116 手机号入网时长小于6个月
		chains.addExecutor(new R10030116Executor());
		// 10030117 临时小号检查
		chains.addExecutor(new R10030117Executor());
		// 10030118 运营商法院黑名单检查
		chains.addExecutor(new R10030118Executor());
		// 10030119 申请人手机归属城市与申请人朋友圈主要活跃省份不一致
		chains.addExecutor(new R10030119Executor());
		// 10030120 申请人近6个月通话城市个数>=30
		chains.addExecutor(new R10030120Executor());
		// 10030121 申请人近6个月主叫异地号码占比>=80%
		chains.addExecutor(new R10030121Executor());
		// 10030122 近6个月与银行通话“经常被联系”
		chains.addExecutor(new R10030122Executor());
		// 10030123 近6个月与法院号码通话次数过多
		chains.addExecutor(new R10030123Executor());
		// 10030124 近6个月与信用卡银行通话次数过多
		chains.addExecutor(new R10030124Executor());
		// 10030125 近6个月与110电话通话次数过多
		chains.addExecutor(new R10030125Executor());
		// 10030126 申请人历史6个账单月的消费合计波动系数>15%
		chains.addExecutor(new R10030126Executor());
		// 10030127 申请人最近一月消费过低
		chains.addExecutor(new R10030127Executor());
		// 10030128 运营商近6个月拨出电话号码个数＜40
		chains.addExecutor(new R10030128Executor());
		// 10030133 进件单位不符合
		chains.addExecutor(new R10030133Executor());


		// ============ 公用 begin ================
		// 10030074 申请人所填写的紧急联系人手机号近3小时出现在他人紧急联系人中的次数≥2 数据来源于：自有解析数据
		chains.addExecutor(new R10030074Executor());//紧急联系人近3小时关联其他申请人较多

		// 10030075 申请人所填写的紧急联系人手机号近72小时出现在他人紧急联系人中的次数≥3 数据来源于：自有解析数据
		chains.addExecutor(new R10030075Executor());//紧急联系人近72小时关联其他申请人较多

		// 10030076 申请人所填写的紧急联系人手机号近1个月内出现在他人紧急联系人中的次数≥4  数据来源于：自有解析数据
		chains.addExecutor(new R10030076Executor());//紧急联系人近一个月关联其他申请人较多
		// ============ 公用 end ================

		// 白骑士
		chains.addExecutor(new R1012Executor());
		// 百融
		chains.addExecutor(new R1009Executor());
		// 逾期机构过多（新颜） 申请客户6个月内逾期机构数>=4 数据来源:5-新颜:逾期档案数据
		chains.addExecutor(new R10170004Executor());
		// 逾期订单数过多（新颜） 申请客户6个月内逾期订单数>=7  数据来源:5-新颜:逾期档案数据
		chains.addExecutor(new R10170005Executor());
		// 申请客户存在逾期天数为M1的订单（新颜） 申请客户6个月内存在逾期天数为M1的订单 数据来源:5-新颜:逾期档案数据
		chains.addExecutor(new R10170006Executor());
		// 申请客户当前未结清的逾期订单数过多（新颜） 申请客户6个月内当前未结清的逾期订单数>=2 数据来源:5-新颜:逾期档案数据
		chains.addExecutor(new R10170007Executor());
		// 申请客户存在逾期金额过高（新颜）申请客户6个月内存在逾期金额为2000以上的订单 数据来源:5-新颜:逾期档案数据
		chains.addExecutor(new R10170008Executor());
		// 通联网贷黑名单
		chains.addExecutor(new R10220001Executor());
		return chains;
	}

	/**
	 * 复贷的规则及顺序
	 * 
	 * @return
	 */
	private static ExecutorChain createReloanExecutorChain() {
		ExecutorChain chains = new ExecutorChain();
		// 10030001 申请人年龄＜22或年龄＞46
		chains.addExecutor(new R10030001Executor());
		// 10030028 历史存在逾期，且最大逾期天数大于等于3天
		chains.addExecutor(new R10030028Executor());
		// 10030129 历史存在逾期，且历史订单逾期累计天数大于等于10天
		chains.addExecutor(new R10030129Executor());
		// 1003030 历史累计已放款次数大于等于8次
		chains.addExecutor(new R10030130Executor());
		// 10020001 自有黑名单
		chains.addExecutor(new R10020001Executor());
		// 10030131 通讯录命中本地黑名单
		chains.addExecutor(new R10030131Executor());
		// 10030132 通讯录命中本地黑名单2
		chains.addExecutor(new R10030132Executor());
		// 10030017 设备通讯录联系含有敏感词
		chains.addExecutor(new R10030017Executor());
		// 宜信阿福
		chains.addExecutor(new R1005Executor());
		return chains;
	}

}
