/**
 * Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.rongdu.common.utils.PropertiesLoader;
import com.rongdu.common.utils.StringUtils;

/**
 * 全局配置类
 * 
 * @author sunda
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config.properties");

	public static String PROFILE = Global.getConfig("profile");

	/**
	 * 短信配置
	 */
	public static String MONGATE_USER_ID = Global.getConfig("mongate_user_id");
	public static String MONGATE_PWD = Global.getConfig("mongate_pwd");
	// 短信息发送接口（相同内容群发，可自定义流水号）
	public static String MONGATE_SEND_SUBMIT_URL = Global.getConfig("mongate_send_submit_url");

	public static String YIMEI_USER_ID = Global.getConfig("yimei_user_id");
	public static String YIMEI_PWD = Global.getConfig("yimei_pwd");
	// 短信息发送接口（相同内容群发，可自定义流水号）
	public static String YIMEI_URL = Global.getConfig("yimei_url");

	public static String XUANWU_USER_ID = Global.getConfig("xuanwu_user_id");
	public static String XUANWU_ACCOUNT = Global.getConfig("xuanwu_account");
	public static String XUANWU_PWD = Global.getConfig("xuanwu_pwd");
	public static String XUANWU_URL = Global.getConfig("xuanwu_url");

	/**
	 * 默认操作id
	 */
	public static final String DEFAULT_OPERATOR_ID = "0";
	/**
	 * APP消息通知类型， 0 系统
	 */
	public static final int CUST_MESSAGE_TYPE_SYS = 0;
	/**
	 * APP消息通知方式，0站内信
	 */
	public static final int CUST_MESSAGE_NOTIFY_TYPE_0 = 0;
	/**
	 * APP消息未读
	 */
	public static final int CUST_MESSAGE_VIEW_STATUS_0 = 0;
	/**
	 * APP消息已读
	 */
	public static final int CUST_MESSAGE_VIEW_STATUS_1 = 1;

	/**
	 * 默认消息来源[6](系统)
	 */
	public static final int DEFAULT_SOURCE = 6;

	/**
	 * 默认操作id
	 */
	public static final String DEFAULT_OPERATOR_NAME = "system";

	/**
	 * 一月内最大申请次数
	 */
	public static final int MAX_APPLY_COUNT_MONTH = 5;

	/**
	 * 上次申请完结后不允许再次申请的时间差（小时）
	 */
	public static final int MIN_AGAIN_APPLY_HOUR = 24;

	/**
	 * 上次申请完结后不允许再次申请的时间差（天）--主要是申请被拒的情况
	 */
	public static final int MIN_AGAIN_APPLY_DAY = 45;

	/**
	 * 上次申请完结后不允许再次申请的时间差（天）--主要是申请被拒的情况
	 */
	public static final int MIN_XJD_APPLY_DAY = 30;

	/**
	 * 累计被拒次数
	 */
	public static final int MAX_REJECT_COUNT = 3;

	/**
	 * 标的默认标题
	 */
	public static final String DEFAULT_PRODUCT_TYPE = "0";

	/**
	 * 标的默认标题
	 */
	public static final String DEFAULT_TITLE = "资金周转";
	/**
	 * 旅游分期默认标题
	 */
	public static final String DEFAULT_LYFQ_TITLE = "旅游乐";
	/**
	 * 标的默认类型 信用 1 抵押 2 担保标3 质押 5 车保宝6
	 */
	public static final Integer DEFAULT_BORROW_TYPE = 1;
	/**
	 * D-天,M-月
	 */
	public static final String DEFAULT_PERIOD_UNIT = "M";

	/**
	 * 不保留小数
	 */
	public static final int DEFAULT_ZERO_SCALE = 0;

	/**
	 * 金额默认精确位数
	 */
	public static final int DEFAULT_AMT_SCALE = 2;

	/**
	 * 精确6数
	 */
	public static final int SIX_SCALE = 6;

	/**
	 * 精确4数
	 */
	public static final int FOUR_SCALE = 4;

	/**
	 * 默认证件类型
	 */
	public static final String DEFAULT_ID_TYPE = "0";

	/**
	 * 默认证件类型-Integer
	 */
	public static final Integer DEFAULT_ID_TYPE_INT = 1;

	/**
	 * 存管业务-发送短信验证码-业务交易代码
	 */
	public static final String BANKDEPOSIT_SMCODE_SRVTXCODE_VAL = "accountOpenPlus";

	/**
	 * 存管业务-发送短信验证码-四合一授权
	 */
	public static final String BANKDEPOSIT_SMCODE_SRVTXCODE_VAL_2 = "termsAuth";

	/**
	 * 存管业务-发送短信验证码-业务交易代码
	 */
	public static final String BANKDEPOSIT_SMCODE_SRVTXCODE_KEY = "srvTxCode";

	/**
	 * 存管业务-发送短信验证码-sn
	 */
	public static final String BANKDEPOSIT_SMCODE_SN_KEY = "sn";

	/**
	 * 存管业务-发送短信验证码-手机号
	 */
	public static final String BANKDEPOSIT_SMCODE_MOBILE_KEY = "phoneNumber";

	/**
	 * 存管业务-发送短信验证码-请求类型
	 */
	public static final String BANKDEPOSIT_SMCODE_REQTYPE_KEY = "reqType";

	/**
	 * 存管业务-发送短信验证码-请求类型-四合一授权
	 */
	public static final String BANKDEPOSIT_SMCODE_REQTYPE_TERMSAUTH = "2";

	/**
	 * 存管业务-发送短信验证码-银行卡号
	 */
	public static final String BANKDEPOSIT_CARDNO_KEY = "cardNo";

	/**
	 * 存管业务-开户-手机号
	 */
	public static final String BANKDEPOSIT_OPEN_MOBILE_KEY = "phoneNumber";

	/**
	 * 存管业务-返回码key
	 */
	public static final String BANKDEPOSIT_RETCODE_KEY = "retCode";

	/**
	 * 存管业务-响应描述key
	 */
	public static final String BANKDEPOSIT_RETMSG_KEY = "retMsg";

	/**
	 * 存管业务-发送短信验证码-业务授权码key
	 */
	public static final String BANKDEPOSIT_SRVAUTHCODE_KEY = "srvAuthCode";

	/**
	 * 存管业务-发送短信验证码-短信序列号key
	 */
	public static final String BANKDEPOSIT_SMSSEQ_KEY = "smsSeq";

	/**
	 * 存管业务-接口成功码
	 */
	public static final String BANKDEPOSIT_SUCCSS_8 = "00000000";

	/**
	 * 存管业务-接口成功码
	 */
	public static final String BANKDEPOSIT_SUCCSS_4 = "0000";

	/**
	 * 存管业务-发送短信验证码接口失败码
	 */
	public static final String BANKDEPOSIT_FAIL = "9999";

	/**
	 * 存管业务-开户电子账户key
	 */
	public static final String BANKDEPOSIT_ACCOUNT_ID_KEY = "accountId";

	/**
	 * 存管业务-账户明细查询- 交易类型key
	 */
	public static final String BANKDEPOSIT_QUERY_TYPE_KEY = "type";

	/**
	 * 存管业务-账户明细查询- 证件类型key
	 */
	public static final String BANKDEPOSIT_QUERY_IDTYPE_KEY = "idType";

	/**
	 * 存管业务-账户明细查询- 证件类型值
	 */
	public static final String BANKDEPOSIT_QUERY_IDTYPE_VAL = "01";

	/**
	 * 存管业务-账户明细查询- 证件号key
	 */
	public static final String BANKDEPOSIT_QUERY_IDNO_KEY = "idNo";

	/**
	 * 存管业务-账户明细查询- 交易类型值 （0-所有交易记录）
	 */
	public static final String BANKDEPOSIT_QUERY_TYPE_VAL = "0";

	/**
	 * 存管业务-账户明细查询- 开始时间key
	 */
	public static final String BANKDEPOSIT_QUERY_BEGINDATE_KEY = "beginDate";

	/**
	 * 存管业务-账户明细查询- 当前日往前 月数-默认6个月
	 */
	public static final int BANKDEPOSIT_QUERY_SUB_MONTH = -6;

	/**
	 * 存管业务-账户明细查询- 结束时间key
	 */
	public static final String BANKDEPOSIT_QUERY_ENDDATE_KEY = "endDate";

	/**
	 * 存管业务-账户明细查询- 手机号key
	 */
	public static final String BANKDEPOSIT_QUERY_PHONENUMBER_KEY = "phoneNumber";

	/**
	 * 存管业务-账户明细查询- 页数key
	 */
	public static final String BANKDEPOSIT_QUERY_PAGE_KEY = "page";

	/**
	 * 存管业务-账户明细查询- 页数值
	 */
	public static final String BANKDEPOSIT_QUERY_PAGE_VAL = "1";

	/**
	 * 存管业务-账户明细查询- 每页条数key
	 */
	public static final String BANKDEPOSIT_QUERY_ROWS_KEY = "rows";

	/**
	 * 存管业务-账户明细查询- 每页条条数值
	 */
	public static final String BANKDEPOSIT_QUERY_ROWS_VAL = "50";

	/**
	 * 存管业务-账户明细查询- 交易类型-提现(银联代收付渠道)
	 */
	public static final int TX_TYPE_CASH = 2616;

	/**
	 * 存管业务-账户明细查询- 交易类型-提现(行内渠道资金转出、二代支付转出)
	 */
	public static final int TX_TYPE_CASH_2 = 2820;

	/**
	 * 存管业务-账户明细查询- 交易类型-放款
	 */
	public static final int TX_TYPE_LENDERS = 7780;

	/**
	 * 存管业务-账户明细查询- 交易类型-还款
	 */
	public static final int TX_TYPE_REPAY = 2781;

	/**
	 * 存管业务-账户明细查询- 交易类型-服务费(银联代收付渠道)
	 */
	public static final int TX_TYPE_SERV_FEE = 2748;
	/**
	 * 存管业务-账户明细查询- 交易类型-服务费()
	 */
	public static final int TX_TYPE_SERV_FEE_2 = 9780;

	/**
	 * 存管业务-账户明细查询- 交易类型-逾期管理费
	 */
	public static final int TX_TYPE_OVERDUE_FEE = 4781;

	/**
	 * 存管业务-账户明细查询- 交易类型-逾期管理费
	 */
	public static final int TX_TYPE_OVERDUE_FEE_2 = 2747;

	/**
	 * 存管业务-资产合作单位码-聚宝钱包
	 */
	public static final String ASSET_CODE_JUQIANBAO = "1182";

	/**
	 * 存管业务-四合一授权- 是否维护标志位key
	 */
	public static final String BANKDEPOSIT_BITMAP_KEY = "bitMap";

	public static final String BANKDEPOSIT_BITMAP_VAL = "11110000000000000000";

	/**
	 * 存管业务-四合一授权- 开通自动投标功能标志key
	 */
	public static final String BANKDEPOSIT_AUTOBID_KEY = "autoBid";

	/**
	 * 存管业务-四合一授权- 开通自动债转功能标志key
	 */

	public static final String BANKDEPOSIT_AUTOTRANSFER_KEY = "autoTransfer";

	/**
	 * 存管业务-四合一授权- 开通预约取现功能标志key
	 */
	public static final String BANKDEPOSIT_AGREEWITHDRAW_KEY = "agreeWithdraw";

	/**
	 * 存管业务-四合一授权- 开通无密消费功能标识key
	 */
	public static final String BANKDEPOSIT_DIRECTCONSUME_KEY = "directConsume";

	/**
	 * 存管业务-四合一授权- 电子账户标识key
	 */
	public static final String BANKDEPOSIT_ACCOUNTID_KEY = "accountId";
	/**
	 * 存管业务-四合一授权- 授权状态-取消
	 */
	public static final String BANKDEPOSIT_TERMSAUTH_CHANNEL = "0";

	/**
	 * 存管业务-四合一授权- 授权状态-开通
	 */
	public static final String BANKDEPOSIT_TERMSAUTH_OPEN = "1";

	/**
	 * 默认证件所属国家-中国（CHN）
	 */
	public static final String DEFAULT_ID_COUNTRY = "CHN";

	/**
	 * 证件有效期连接符
	 */
	public static final String ID_VALID_DATE_HYPHEN = "-";

	/**
	 * 证件有效期-长期
	 */
	public static final String ID_VALID_DATE_LONG = "长期";

	/**
	 * 一年计息天数
	 */
	public static final int YEAR_DAYS = 365;

	/**
	 * 一年计息天数(分期)
	 */
	public static final int YEAR_DAYS_FENQI = 360;

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	/**
	 * 放款信息提示模板1
	 */
	public static final String MAKE_LOAN_TIPS_1 = "尾号";

	/**
	 * 放款信息提示模板2
	 */
	public static final String MAKE_LOAN_TIPS_2 = "，成功借款";
	/**
	 * 放款信息提示模板3
	 */
	public static final String MAKE_LOAN_TIPS_3 = "元";

	/**
	 * app端 BANNERS的默认分类编号
	 */
	public static final String APP_BANNERS_CATEGORY_ID = "19";

	/**
	 * 合同编号前缀
	 */
	public static final String AGREEMENT_NO_PREFIX = "XY";

	/**
	 * 用户信息缓存key前缀
	 */
	public static final String USER_CACHE_PREFIX = "user_";

	/**
	 * 用户密码输错次数统计缓存key前缀
	 */
	public static final String PWDERROR_CNT_CACHE_PREFIX = "pwd_err_cnt_";

	/**
	 * 用户锁定标识缓存key前缀
	 */
	public static final String LOCK_USER_PREFIX = "lock_user_";

	/**
	 * 用户认证标识缓存key前缀
	 */
	public static final String USER_AUTH_PREFIX = "user_auth_";

	/**
	 * 用户锁定标识
	 */
	public static final Integer LOCK_USER_FLAG = 0;

	/**
	 * 密码输错达6次用户锁定时间（4小时）
	 */
	public static final int USER_LOCK_CACHESECONDS = 60 * 60 * 4;

	/**
	 * 用户密码输错最大次数
	 */
	public static final int PWDERROR_MAX_CNT = 6;

	/**
	 * 营销方案缓存key前缀
	 */
	public static final String PROMOTION_CASE_CACHE_PREFIX = "promotionCase_";

	/**
	 * 短信验证码日限制条数
	 */
	public static final int MSG_SEND_DAY_LIMIT = 3;

	/**
	 * 短信验证码类型限制条数
	 */
	public static final int MSG_SEND_TYPE_LIMIT = 3;

	/**
	 * 短信验证码ip黑名单允许最大值
	 */
	public static final int MSG_IP_BL_LIMIT = 6;

	/**
	 * 腾讯调用接口类型 - orc接口
	 */
	public static final String TENCENT_OCR = "1";

	/**
	 * 腾讯调用接口类型 - orc接口扫码成功码（身份证正面，反面）
	 */
	public static final String TENCENT_OCR_SCAN_OK = "0";

	/**
	 * 腾讯调用接口类型 - 人脸识别
	 */
	public static final String TENCENT_FACE = "2";

	/**
	 * OCR调用日限制条数
	 */
	public static final int OCR_DAY_LIMIT = 6;

	/**
	 * 人脸识别调用日限制条数
	 */
	public static final int FACE_DAY_LIMIT = 5;

	/**
	 * 运营商调用日限制条数
	 */
	public static final int OPERATOR_DAY_LIMIT = 5;
	/**
	 * 信用卡认证调用日限制条数
	 */
	public static final int CREDIT_CARD_DAY_LIMIT = 5;

	/**
	 * 芝麻信用调用日限制条数
	 */
	public static final int SESAME_DAY_LIMIT = 100;

	/**
	 * 运营商调、芝麻信用有效天数
	 */
	public static final int OPERATOR_SESAME_EFFECTIVE_DAY = 30;

	/**
	 * 运营商调、芝麻信用有效最大天数
	 */
	public static final int OPERATOR_SESAME_EFFECTIVE_MAXDAY = 90;

	/**
	 * 短信验证码日统计缓存key后缀
	 */
	public static final String COUNT_DAY_MCODE_SUFFIX = "_mcode_day_count";

	/**
	 * 短信验证码日统计缓存key后缀
	 */
	public static final String COUNT_TYPE_MCODE_SUFFIX = "_mcode_type_count";

	/**
	 * 梦网短信天总发送条数缓存key
	 */
	public static final String MW_SEND_DAY_COUNT = "mw_send_day_count";

	/**
	 * 每天短信发送量预警提醒基数
	 */
	public static final int MSG_DAY_WARN_RADIX = 5000;

	/**
	 * 手机号限制1统计缓存key后缀
	 */
	public static final String MCODE_MOB_LIMT_S_SUFFIX = "_mcode_mob_ls_count";

	/**
	 * 手机号限制1最大条数
	 */
	public static final int MCODE_MOB_LIMT_S = 5;

	/**
	 * 手机号限制2统计缓存key后缀
	 */
	public static final String MCODE_MOB_LIMT_A_SUFFIX = "_mcode_mob_la_count";

	/**
	 * 手机号限制1最大条数
	 */
	public static final int MCODE_MOB_LIMT_A = 7;

	/**
	 * ip限制1统计缓存key后缀
	 */
	public static final String MCODE_IP_LIMT_S_SUFFIX = "_mcode_ip_ls_count";

	/**
	 * ip限制1最大条数
	 */
	public static final int MCODE_IP_LIMT_S = 5;

	/**
	 * ip限制2统计缓存key后缀
	 */
	public static final String MCODE_IP_LIMT_A_SUFFIX = "_mcode_ip_la_count";
	/**
	 * ip限制2最大条数
	 */
	public static final int MCODE_IP_LIMT_A = 7;

	/**
	 * 贷款产品信息缓存key后缀
	 */
	public static final String LOAN_PRO_SUFFIX = "_loan_pro";

	/**
	 * ocr接口调用次数缓存key后缀
	 */
	public static final String COUNT_OCR_SUFFIX = "_count_ocr";

	/**
	 * 人类识别接口调用次数缓存key后缀
	 */
	public static final String COUNT_FACE_SUFFIX = "_count_face";

	/**
	 * 运营商接口调用次数缓存key后缀
	 */
	public static final String COUNT_OPERATOR_SUFFIX = "_count_operator";

	/**
	 * 芝麻信用接口调用次数缓存key后缀
	 */
	public static final String COUNT_SESAME_SUFFIX = "_count_sesame";
	/**
	 * 信用卡接口调用次数缓存key后缀
	 */
	public static final String COUNT_CREDIT_CARD_SUFFIX = "_count_credit_card";

	/**
	 * 预支付结果缓存key后缀
	 */
	public static final String PREPAY_RESULT_SUFFIX = "_repay_result";

	/**
	 * 注册短信验证码key后缀
	 */
	public static final String REG_MCODE_SUFFIX = "_reg_mcode";

	/**
	 * 忘记密码修改密钥后缀
	 */
	public static final String FORGET_PWD_SUFFIX = "_forget_pwd";

	/**
	 * 忘记密码短信验证码key后缀
	 */
	public static final String FORGET_MCODE_SUFFIX = "_forget_mcode";

	/**
	 * 登录短信验证码key后缀
	 */
	public static final String LOGIN_MCODE_SUFFIX = "_login_mcode";

	/**
	 * 白骑士设备唯一标识缓存key后缀
	 */
	public static final String BQS_KEY_SUFFIX = "_bqs";

	/**
	 * 白骑士设备唯一标识
	 */
	public static final String BQS_KEY = "bqsTokenKey";

	/**
	 * 同盾士设备唯一标识缓存key后缀
	 */
	public static final String TD_KEY_SUFFIX = "_td";

	/**
	 * 同盾士设备唯一标识
	 */
	public static final String TD_KEY = "tdBlackBox";

	/**
	 * 默认码短信验证码位数
	 */
	public static final int MSG_CODE_SIZE = 6;

	/**
	 * 短信验证码类型-注册
	 */
	public static final int MSG_TYPE_REG = 1;

	/**
	 * 短信验证码类型-忘记密码
	 */
	public static final int MSG_TYPE_FORGET = 2;

	/**
	 * 短信验证码类型-短信登录验证
	 */
	public static final int MSG_TYPE_LOGIN = 3;

	/**
	 * 默认码短信验证码位数
	 */
	public static final int MSG_CODE_FOUR = 4;

	/**
	 * 存管开户业务授权码key后缀
	 */
	public static final String BANKDEPOSIT_SRVAUTHCODE_SUFFIX = "_bankdeposit_srvauthcode";

	/**
	 * 存管短信序列号key后缀
	 */
	public static final String BANKDEPOSIT_SMSSEQ_SUFFIX = "_bankdeposit_smsseq";

	/**
	 * 申请编号缓存key后缀
	 */
	public static final String APPLY_NO_SUFFIX = "_apply_no";

	/**
	 * 用户token缓存前缀
	 */
	public static final String USER_TOKEN_PREFIX = "user_token_";

	/**
	 * 用户appkey缓存前缀
	 */
	public static final String APP_TEY_PREFIX = "app_key_";

	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 还款方式 - 主动还款
	 */
	public static final String REPAY_TYPE_MANUAL = "manual";

	/**
	 * 还款方式 - 自动扣款
	 */
	public static final String REPAY_TYPE_AUTO = "auto";

	/**
	 * 还款方式 - 手动还款
	 */
	public static final String REPAY_TYPE_MANPAY = "manpay";
	/**
	 * 还款方式 - 主动延期
	 */
	public static final String REPAY_TYPE_MANUALDELAY = "manualdelay";
	/**
	 * 还款方式 - 手动延期
	 */
	public static final String REPAY_TYPE_MANDELAY = "mandelay";

	/**
	 * code y0524 还款方式 - 代扣延期(该状态暂未启用)
	 */
	public static final String REPAY_TYPE_AUTODELAY = "autodelay";

	/**
	 * 还款计划状态 - 已结清
	 */
	public static final int REPAY_PLAN_STATUS_OVER = 1;

	/**
	 * 还款计划状态 - 未结清
	 */
	public static final int REPAY_PLAN_STATUS_UNOVER = 0;

	/**
	 * 宽限期类型（1-产品默认允许宽限，2-运营特许宽限）
	 */
	public static final int DEFAULT_GRACE_TYPE = 1;
	public static final int CHARTER_GRACE_TYPE = 2;

	/**
	 * 宝付确认支付接口时间格式
	 */
	public static final String BAOFOO_TIEM_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 宝付渠道代码
	 */
	public static final String BAOFOO_CHANNEL_CODE = "BAOFOO";

	/**
	 * 宝付渠道名称
	 */
	public static final String BAOFOO_CHANNEL_NAME = "宝付支付";


	/**
	 * 通联渠道代码
	 */
	public static final String TONGLIAN_CHANNEL_CODE = "TONGLIAN";

	/**
	 * 通联渠道名称
	 */
	public static final String TONGLIAN_CHANNEL_NAME = "通联支付";

	/**
	 * 通联商户2渠道代码
	 */
	public static final String TONGLIAN_LOAN_CHANNEL_CODE = "TONGLIAN_LOAN";

	/**
	 * 通联商户2渠道名称
	 */
	public static final String TONGLIAN_LOAN_CHANNEL_NAME = "通联独立放款支付";

	/**
	 * 通融宝付渠道代码
	 */
	public static final String TONGRONG_BAOFOO_CHANNEL_CODE = "TONGRONG";

	/**
	 * 通融宝付渠道名称
	 */
	public static final String TONGRONG_BAOFOO_CHANNEL_NAME = "通融";

	/**
	 * 先锋支付接口时间格式
	 */
	public static final String XIANFENG_TIEM_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 先锋渠道代码
	 */
	public static final String XIANFENG_CHANNEL_CODE = "XIANFENG";

	/**
	 * 先锋渠道名称
	 */
	public static final String XIANFENG_CHANNEL_NAME = "先锋支付";

	/**
	 * 海尔渠道代码
	 */
	public static final String HAIER_CHANNEL_CODE = "KJTPAY";

	/**
	 * 海尔渠道名称
	 */
	public static final String HAIER_CHANNEL_NAME = "海尔支付";

	/**
	 * 线下还款渠道代码
	 */
	public static final String XIANXIA_CHANNEL_CODE = "XIANXIA";

	/**
	 * 线下还款渠道名称
	 */
	public static final String XIANXIA_CHANNEL_NAME = "线下支付";

	/**
	 * 支付订单商品名称
	 */
	public static final String PAY_GOODS_NAME = "借款人还款";

	/**
	 * 支付订单商品名称
	 */
	public static final String PAY_GOODS_ID = "1";

	/**
	 * ip是否黑名单-否
	 */
	public static final Integer IP_NOTIN_BLACKLIST = 0;

	/**
	 * ip是否黑名单-是
	 */
	public static final Integer IP_IN_BLACKLIST = 1;

	/**
	 * 等级
	 */
	public static final int LEVEL_1 = 1;
	public static final int LEVEL_2 = 2;

	/**
	 * 未完成身份认证
	 */
	public static final int IDENTITY_STATUS_UN = 0;
	/**
	 * 成身份认证
	 */
	public static final int IDENTITY_STATUS_OK = 1;

	/**
	 * 来源：1-ios,2-android,3-h5,4-api,5-后台网址,6-系统
	 */
	public static final int SOURCE_IOS = 1;
	public static final int SOURCE_ANDROID = 2;
	public static final int SOURCE_H5 = 3;
	public static final int SOURCE_API = 4;
	public static final int SOURCE_WEB = 5;
	public static final int SOURCE_SYSTEM = 6;

	/**
	 * 减免审核状态 0待审核，1通过，2不通过
	 */
	public static final int DEDUCTION_INIT = 0;
	public static final int DEDUCTION_PASS = 1;
	public static final int DEDUCTION_NO_PASS = 2;

	/**
	 * 还款推送失败后复出推送次数
	 */
	public static final int REPAY_PUSH_REPEAT_LIMIT = 1;

	/**
	 * 还款任务推送次数预警值
	 */
	public static final int REPAY_PUSH_TASK_LIMIT = 2;

	/**
	 * 银行名称和编号缓存key
	 */
	public static final String BANK_NAME_NO_CACHE_KEY = "bank_name_no_list";

	/**
	 * 区域名称和编号缓存key
	 */
	public static final String AREA_CACHE_KEY = "area_code_name";

	/**
	 * 申请编号缓存时间（1小时） 3*24*60*60
	 */
	public static final int APPLY_NO_CACHESECONDS = 60 * 60;

	/**
	 * 缓存有效时间1天
	 */
	public static final int ONE_DAY_CACHESECONDS = 24 * 60 * 60;

	/**
	 * 缓存有效时间0.5天
	 */
	public static final int HALF_DAY_CACHESECONDS = 12 * 60 * 60;

	/**
	 * 缓存有效时间2小时
	 */
	public static final int TWO_HOURS_CACHESECONDS = 2 * 60 * 60;

	/**
	 * 缓存有效时间3天
	 */
	public static final int THREE_DAY_CACHESECONDS = 3 * 24 * 60 * 60;

	/**
	 * 缓存有效时间2分钟
	 */
	public static final int TWO_MINUTES_CACHESECONDS = 2 * 60;

	/**
	 * 所有地区List
	 */
	public static final String ALL_AREA_LIST_CACHE_KEY = "ALL_AREA_LIST";

	/**
	 * 所有地区Map
	 */
	public static final String ALL_AREA_MAP_CACHE_KEY = "ALL_AREA_MAP";

	/**
	 * 存管业务-投复利虚拟开户接口返回成功码
	 */
	public static final String BANKDEPOSIT_SUCCSS_OK = "OK";

	/*
	 * 存管业务-虚拟账户开通成功APP返回码
	 */
	public static final String BANKDEPOSIT_RESULT_APP = "SUCCESS";

	/**
	 * 上传企业执照缓存key前缀
	 */
	public static final String UPLOAD_ENTERPRISE_LICENSE_PREFIX = "UPLOAD_ENTERPRISE_LICENSE_";

	/**
	 * 防hack得分
	 */
	public static final float HACK_SCORE = 0.98f;

	/**
	 * 置信度
	 */
	public static final float VERIFY_SCORE = 0.7f;

	public static final String FIRST_FEE_DESC = "购物券"; // 旅游券
	public static final String SECOND_FEE_DESC = "购物金"; // 旅游券

	/**
	 * 默认旅游券产品id
	 */
	public static final String DEFAULT_TRIP_PRODUCT_ID = "93";

	public static final String REPAY_STATUS_FEEDBACK = "XJBK:RepayStatusFeedbackToRedis";
	public static final String REPAY_PLAN_FEEDBACK = "XJBK:xianJinCardCallback";
	public static final String XJBK_SUCCESS = "success";
	public static final String XJBK_FAIL = "fail";
	public static final String XJBK_THIRD_KEY = "XJBK:third_key";
	public static final String RONG_THIRD_KEY = "RONG:third_key";
	public static final String SLL_THIRD_KEY = "SLL:third_key";
	public static final String SLL_APPROVE_FEEDBACK = "SLL:approve_feedback";
	public static final String SLL_ORDERSTATUS_FEEDBACK = "SLL:orderstatus_feedback";
	public static final String SLL_LEND_FEEDBACK = "SLL:lend_feedback";
	public static final String SLL_SETTLEMENT_FEEDBACK = "SLL:settlement_feedback";
	public static final String SLL_JQB_PRODUCTID = "1";// SLL聚宝钱包产品ID
	public static final String SLL_JHH_PRODUCTID = "2";// SLL聚花花产品ID
	public static final String JDQ_THIRD_KEY = "JDQ:third_key";
	public static final String JDQ_ORDERSTATUS_FEEDBACK = "JDQ:orderstatus_feedback";

	public static final String RONG_PAY_FEEDBACK = "RONG:pay_feedback";
	public static final String RONG_APPROVE_FEEDBACK = "RONG:approve_feedback";
	public static final String RONG_ORDERSTATUS_FEEDBACK = "RONG:orderstatus_feedback";
	public static final String RONG_SETTLEMENT_FEEDBACK = "RONG:settlement_feedback";
	public static final String RONG_CREATE_ACCOUNT = "rong_create_account_";// 融360-口袋存管渠道-确认借款标识
	public static final int RONG_JQB_PRODUCTID = 10390907;// RONG聚宝钱包产品ID
	public static final int RONG_JHH_PRODUCTID = 10434548;// RONG聚花花产品ID

	public static final String DWD_THIRD_KEY = "DWD:third_key";
	public static final String DWD_APPROVE_FEEDBACK = "DWD:approve_feedback";
	public static final String DWD_LEND_FEEDBACK = "DWD:lend_feedback";
	public static final String DWD_SETTLEMENT_FEEDBACK = "DWD:settlement_feedback";

	public static final String JBD_ORDER_LOCK = "JBD:order_lock_";// 聚宝贷订单公共锁
	public static final String JBD_PAY_LOCK = "JBD:pay_lock_";// 聚宝贷支付公共锁
	public static final String BORROW_INFO_LOCK = "borrow_info_lock_";// 聚宝贷支付公共锁
	public static final String PAYING_LOCK = "PAYING_LOCK_";// 放款查询公共锁
	public static final String REPAYING_LOCK = "REPAYING_LOCK_";// 代扣查询公共锁

	public static final String JBD_15_DERATE_COUNT = "JBD:15_derate_count";// 聚宝贷复贷减免
	public static final String RONG_PRODUCT_FLAG = "rong_product_flag";// 融360产品标识1：28天分4期，2：15天单期
	public static final String SLL_PRODUCT_FLAG = "sll_product_flag";// 奇虎360产品标识1：28天分4期，2：15天单期
	public static final String JDQ_PRODUCT_FLAG = "jdq_product_flag";// 借点钱产品标识1：28天分4期，2：15天单期
	public static final String JDQ_PRODUCT_FLAG2 = "jdq_product_flag2";// 借点钱产品标识1：28天分4期，2：15天单期
	public static final String DWD_PRODUCT_FLAG = "dwd_product_flag";// 大王贷产品标识1：28天分4期，2：15天单期

	public static final String DAY_15_DERATE = "day_15_derate";// 15天产品减免活动标识0：关闭，1：启动

	public static final String HJS_OPEN1 = "HJS:OPEN1_";
	public static final String HJS_OPEN2 = "HJS:OPEN2_";
	public static final String HJS_OPEN3 = "HJS:OPEN3_";

	public static final String KD_OPEN1 = "KD:OPEN1_";
	public static final String KD_OPEN2 = "KD:OPEN2_";
	public static final String KD_OPEN3 = "KD:OPEN3_";

	/**
	 * 延期比例
	 */
	public static final String DELAY_RATE = "0.265";

	/**
	 * 现金贷自动分期天数90天分期
	 */
	public static final int XJD_AUTO_FQ_DAY_90 = 90;

	/**
	 * 现金贷自动分期天数28天分期
	 */
	public static final int XJD_AUTO_FQ_DAY_28 = 28;
	/**
	 * 现金贷单期14天
	 */
	public static final int XJD_DQ_DAY_14 = 14;

	public static final int XJD_DQ_DAY_15 = 15;

	/**
	 * 现金贷单期8天
	 */
	public static final int XJD_DQ_DAY_8 = 8;

	/**
	 * 延期成功标识前缀
	 */
	public static final String DELAY_SUCCESS_FLAG_PREFIX = "DELAY_SUCCESS_FLAG_";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 获取配置
	 * 
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}

	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 页面获取常量
	 * 
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	// /**
	// * 获取上传文件的根目录
	// * @return
	// */
	// public static String getUserfilesBaseDir() {
	// String dir = getConfig("userfiles.basedir");
	// if (StringUtils.isBlank(dir)){
	// try {
	// dir = ServletContextFactory.getServletContext().getRealPath("/");
	// } catch (Exception e) {
	// return "";
	// }
	// }
	// if(!dir.endsWith("/")) {
	// dir += "/";
	// }
	// System.out.println("userfiles.basedir: " + dir);
	// return dir;
	// }

	/**
	 * 获取上传文件的根目录
	 * 
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		return dir;
	}

	/**
	 * ftp服务器IP
	 */
	public static String getFTPServer() {
		return getConfig("ftp.server");
	}

	/**
	 * ftp服务器端口
	 */
	public static int getFTPPort() {
		return Integer.parseInt(getConfig("ftp.port"));
	}

	/**
	 * ftp服务器登录名
	 */
	public static String getFTPUsername() {
		return getConfig("ftp.username");
	}

	/**
	 * ftp服务器密码
	 */
	public static String getFTPPwd() {
		return getConfig("ftp.pwd");
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)) {
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}

}
