package com.rongdu.loans.credit.common;

/**
 * Redis缓存前缀
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class RedisPrefix {

	/**
	 * 贷款申请信息
	 */
	public static String LOAN_APPLY = "LOAN_APPLY_";
	/**
	 * 用户信息
	 */
	public static String USER = "USER_";
	/**
	 * 用户信息
	 */
	public static String USER_INFO = "USER_INFO_";
	/**
	 * 自动审核上下文环境
	 */
	public static String AUTO_APPROVE_CONTEXT = "AUTO_APPROVE_CONTEXT_";

	/**
	 * 风控列表
	 */
	public static String RISK_RULE_LIST = "RISK_RULE_LIST";
	/**
	 * 风控列表List
	 */
	public static String RISK_RULE_LIST_PREFIX = "RISK_RULE_LIST_";
	/**
	 * 风控列表Map
	 */
	public static String RISK_RULE_MAP_PREFIX = "RISK_RULE_MAP_";
	/**
	 * 百融-TokenID
	 */
	public static String CREDIT100_TOKEN_ID = "CREDIT100_TOKEN_ID";
	/**
	 * 百融-登录重试次数
	 */
	public static String CREDIT100_RETRY_TIMES = "CREDIT100_RETRY_TIMES_";

	/**
	 * 百融-特殊名单核查
	 */
	public static String CREDIT100_SPECIAL_LIST = "CREDIT100_SPECIAL_LIST_";
	/**
	 * 百融-当日多次申请
	 */
	public static String CREDIT100_APPLY_LOAN_DAY = "CREDIT100_APPLY_LOAN_DAY_";
	/**
	 * 百融-当月多次申请
	 */
	public static String CREDIT100_APPLY_LOAN_MONTH = "CREDIT100_APPLY_LOAN_MONTH_";

	/**
	 * 致诚-查询借款、风险和逾期信息
	 */
	public static String ZHICHENG_CREDIT_INFO = "ZHICHENG_CREDIT_INFO_";
	/**
	 * 致诚-查询综合决策信息
	 */
	public static String ZHICHENG_DECISION_INFO = "ZHICHENG_DECISION_INFO_";
	/**
	 * 致诚-查询欺诈甄别信息
	 */
	public static String ZHICHENG_FRAUDSCREEN_INFO = "ZHICHENG_FRAUDSCREEN_INFO_";
	/**
	 * 致诚-风险名单
	 */
	public static String ZHICHENG_RISK_LIST = "ZHICHENG_RISK_LIST_";

	/**
	 * 同盾-反欺诈决策引擎
	 */
	public static String TONGDUN_ANTIFRAUD = "TONGDUN_ANTIFRAUD_";
	/**
	 * 同盾-命中规则详情
	 */
	public static String TONGDUN_RULE_DETAIL = "TONGDUN_RULE_DETAIL_";
	/**
	 * 腾讯-反欺诈服务
	 */
	public static String TENCENT_ANTIFRAUD = "TENCENT_ANTIFRAUD_";

	/**
	 * 白骑士-资信云报告数据
	 */
	public static String BAIQISHI_REPORT = "BAIQISHI_REPORT_";
	/**
	 * 白骑士-资信云报告数据，数据源来自文件
	 */
	public static String BAIQISHI_REPORT_FROM_FILE = "BAIQISHI_REPORT_FROM_FILE_";
	/**
	 * 白骑士-手机设备信息
	 */
	public static String BAIQISHI_DEVICE = "BAIQISHI_DEVICE_";
	/**
	 * 白骑士-手机设备通讯录信息
	 */
	public static String BAIQISHI_CONTACT_INFO = "BAIQISHI_DEVICE_CONTACT_";
	/**
	 * 白骑士-手机设备通讯录信息，数据源来自文件
	 */
	public static String BAIQISHI_CONTACT_INFO_FROM_FILE = "BAIQISHI_DEVICE_CONTACT_FROM_FILE_";
	/**
	 * 白骑士-移动网络运营通讯信息
	 */
	public static String BAIQISHI_MNO_CONTACT = "BAIQISHI_MNO_CONTACT_";
	/**
	 * 白骑士-反欺诈决策引擎
	 */
	public static String BAIQISHI_DECISION = "BAIQISHI_DECISION_";

	/**
	 * 芝麻信用-授权结果
	 */
	public static String ZHIMA_AUTHORIZE_RESULT = "ZHIMA_AUTHORIZE_RESULT_";

	/**
	 * 芝麻信用-芝麻分
	 */
	public static String ZHIMA_SCORE = "ZHIMA_SCORE_";

	/**
	 * 芝麻信用-行业关注名单
	 */
	public static String ZHIMA_WATCH_LIST = "ZHIMA_WATCH_LIST_";

	/**
	 * 白骑士-向白骑士规则引擎上送的自定义参数
	 */
	public static String BAIQISHI_EXT_PARAMS = "BAIQISHI_EXT_PARAMS";

	/**
	 * 口袋-黑名单查询服务
	 */
	public static String KDCREDIT_BLACKLIST = "KDCREDIT_BLACKLIST_";
	/**
	 * 三方征信1-黑名单查询服务
	 */
	public static String THIRDPARTYCREDIT1_BLACKLIST = "THIRDPARTYCREDIT1_BLACKLIST_";
	/**
	 * 三方征信2-黑名单查询服务
	 */
	public static String THIRDPARTYCREDIT2_BLACKLIST = "THIRDPARTYCREDIT2_BLACKLIST_";
	/**
	 * 新颜-负面拉黑
	 */
	public static String XINYAN_BLACK = "XINYAN_BLACK_";
	/**
	 * 新颜-共债档案
	 */
	public static String XINYAN_TOTALDEBT = "XINYAN_TOTALDEBT_";
	/**
	 * 新颜-逾期档案
	 */
	public static String XINYAN_OVERDUE = "XINYAN_OVERDUE_";
	/**
	 * 新颜-负面拉黑-聚宝钱包
	 */
	public static String XINYAN_BLACK_JBQB = "XINYAN_BLACK_JBQB_";
	/**
	 * 新颜-全景雷达
	 */
	public static String XINYAN_RADAR = "XINYAN_RADAR_";
	/**
	 * 魔蝎-信用卡邮箱报告
	 */
	public static String MOXIE_EMAIL_REPORT = "MOXIE_EMAIL_REPORT_";
	/**
	 * 魔蝎-网银报告
	 */
	public static String MOXIE_BANK_REPORT = "MOXIE_BANK_REPORT_";
	/**
	 * 现金白卡基础数据
	 */
	public static String XIANJINCARD_BASE = "XJBK:BASE_";
	/**
	 * 现金白卡附加数据
	 */
	public static String XIANJINCARD_ADDITIONAL = "XJBK:ADDITIONAL_";
	/**
	 * 融360基础数据
	 */
	public static String RONG360_BASE = "RONG360:BASE_";
	/**
	 * 融360附加数据
	 */
	public static String RONG360_ADDITIONAL = "RONG360:ADDITIONAL_";
	/**
	 * 融天机运营商报告
	 */
	public static String RONGTJ_REPORT_DETAIL = "RONGTJ:REPORT_";
	/**
	 * 魔蝎-信用卡报告
	 */
	public static String MOXIE_CREDITCARD_REPORT = "MOXIE_CREDITCARD_REPORT_";
	/**
	 * 商汤-人脸识别
	 */
	public static String LINKFACE_IDNUMBER_VERIFICATION = "LINKFACE_IDNUMBER_VERIFICATION_";

	/**
	 * 借点钱基础数据
	 */
	public static String JDQ_BASE = "JDQ:BASE_";
	/**
	 * 借点钱补充数据
	 */
	public static String JDQ_BASE_ADD = "JDQ:BASE_ADD";

	/**
	 * 借点钱报告数据
	 */
	public static String JDQ_REPORT = "JDQ:REPORT_";
	public static String DWD_REPORT = "DWD:REPORT_";
	public static String DWD_BASE_ADD = "DWD:BASE_ADD";
	public static String DWD_BASE= "DWD:BASE_";
	public static String DWD_CHARGEINFO = "DWD:CHARGEINFO_";
	public static String SLL_REPORT = "SLL:REPORT_";
	/**
	 * 通联网贷黑名单信息
	 */
	public static String TONGLIAN_BLACK_INFO = "TONGLIAN_BLACK_INFO_";
}
