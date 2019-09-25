package com.rongdu.common.config;

/**
 * 短信模板
 * @author likang
 * @version 2017-07-15
 */
public class ShortMsgTemplate {

	/**
	 * app名称
	 */
	public static final String APP_NAME = "聚宝钱包";
	
	/**
	 * 验证码替换关键字
	 */
	public static final String REPLACE_CODE = "code";
	
	/**
	 * 注册短信验证码-短信模板
	 */
	public static final String MSG_TEMP_REG = "您好，您正在进行手机注册，验证码为code。请于5分钟内完成验证。若非您本人操作，请忽略本短信。";
	
	/**
	 * 短信类型码-注册
	 */
	public static final String MSG_TYPE_REG = "1";
	
	/**
	 * 修改登录密码短信验证码-短信模板
	 */
	public static final String MSG_TEMP_UPDATE_PWD = "您好，您正在申请修改登录密码，验证码code。请于5分钟内完成验证。工作人员不会向您索取，请勿泄露。";
	
	/**
	 * 找回密码短信验证码-短信模板
	 */
	public static final String MSG_TEMP_FIND_PWD = "您好，您正在找回登录密码，验证码code。请于5分钟内完成验证。工作人员不会向您索取，请勿泄露。";
	
	/**
	 * 登录短信验证码-短信模板
	 */
	public static final String MSG_TEMP_LOGIN = "您好，您正在使用验证码登录，验证码code。请于5分钟内完成验证。工作人员不会向您索取，请勿泄露。";
	
	/**
	 * 找回密码短信验证码-短信模板
	 */
	public static final String MSG_TEMP_SEND_PWD = "您好，您已成功注册聚宝钱包，登录密码%s ,请勿泄露。客服4001622772";
	
	/**
	 * 短信类型码-找回密码
	 */
	public static final String MSG_TYPE_FIND_PWD = "2";
	
	/**
	 * 开通恒丰银行电子验证码-短信模板
	 */
	public static final String MSG_TEMP_JXBANK = "尊敬的用户，您正在使用开户业务，验证码code。请于5分钟内完成验证。工作人员不会向您索取，请勿泄露。";

	/**
	 * 短信类型码-开通恒丰银行开户
	 */
	public static final String MSG_TYPE_JXBANK = "3";

	/**
	 * 短信类型码-开通通联协议支付短信验证码
	 */
	public static final String MSG_TYPE_TL_AGREEMENT = "5";

	/**
	 * 短信类型码-四合一授权
	 */
	public static final String MSG_TYPE_TERMSAUTH = "4";

	/**
	 * APP消息审核通过标题
	 */
	public static final String MSG_TEMP_APPROVE_TITLE = "申请成功";
	/**
	 * 现金贷单期
	 */
//	public static final String MSG_TEMP_APPROVE = "尊敬的%s，您的借款申请已审核通过，借款金额：%s元，借款期限：%s天，每日利息：%s元，购买%s,可以立刻下款。若有疑问，详询客服：4001622772。";
	
	public static final String MSG_TEMP_APPROVE = "尊敬的%s，您的借款申请已审核通过，借款金额：%s元，每日利息：%s元，点击确认借款，可以立刻安排放款。若有疑问，详询客服：4001622772";
	
	/**
	 * 现金贷分期短信
	 */
	public static final String MSG_TEMP_APPROVE3 = "尊敬的%s，您的借款申请已审核通过，借款金额：%s元，借款期限：%s期，预计于1~2个工作日内到账，请注意查收。若有疑问，详询客服：4001622772 。";
	
	/**
	 * APP消息审核不通过标题
	 */
	public static final String MSG_TEMP_APPROVE_FAIL_TITLE = "借款失败";
	/**
	 * 借款申请短信，审核不通过
	 * APP消息
	 */
	public static final String MSG_TEMP_APPROVE_FAIL = "尊敬的用户，您的借款申请资料审核未通过，您可以修改资料重新提交。您一个月内只有一次申请机会，本月申请未通过，您可以下个月再申请，若有疑问，详询客服：4001622772。";
	/**
	 * 借款资金放款到银行卡账户
	 */
	public static final String MSG_TEMP_HAS_BEEN_LENDING = "尊敬的%s，您的借款已到您的银行卡账户，借款金额：%s元。若有疑问，详询客服：4001622772。";
	/**
	 * APP消息到账通知标题
	 */
	public static final String APP_MESSAGE_HAS_BEEN_LENDING_TITLE = "到账通知";
	/**
	 * APP消息到账通知
	 */
	public static final String APP_MESSAGE_HAS_BEEN_LENDING = "尊敬的用户，您的借款已到您的银行卡账户，借款金额：%s元，服务费%s元。若有疑问，详询客服：4001622772。";
	/**
	 * APP消息到账通知
	 */
	public static final String APP_MESSAGE_HAS_BEEN_LENDING2 = "尊敬的用户，您的借款已到您的银行卡账户，借款金额：%s元。若有疑问，详询客服：4001622772。";
	/**
	 * APP消息还款成功标题
	 */
	public static final String REPAY_SUCCESS_TITLE = "还款成功";
	/**
	 * APP消息还款成功
	 */
	//public static final String REPAY_SUCCESS = "尊敬的用户，您有1笔借款还款成功，共计%s元已从您的代扣账户中扣除，感谢您的支持，若有问题，请咨询客服：4001622772。";

	public static final String REPAY_SUCCESS = "尊敬的用户，您有1笔借款还款成功，共计%s元已从您的代扣账户中扣除，感谢您的支持，若有问题，请咨询客服：4001622772。";

	/**
	 * 还款失败短信
	 */
	public static final String REPAY_FAIL = "尊敬的%s，您在聚宝钱包申请的借款扣款失败，应还金额%s元，为避免逾期，请最迟于今日还款，感谢您的支持，若有问题，请咨询客服：4001622772。";

	public static final String OVERDUE_REPAY_FAIL = "尊敬的%s，您在聚宝钱包申请的借款扣款失败，应还金额%s元，您的借款已逾期，请尽快还款，感谢您的支持，若有问题，请咨询客服：4001622772。";
	/**
	 * APP消息还款通知标题
	 */
	public static final String REPAY_NOTICE_TITLE = "还款通知";
	/**
	 * 还款通知
	 */
	public static final String REPAY_NOTICE = "尊敬的用户，您有1笔借款将于%s到期，共计%s元，您可以通过聚宝钱包还款，或由系统自动代扣，若有问题，请咨询客服：4001622772。";
	
	/**
	 * 还款提示理财端失败短信预警
	 */
	public static final String MSG_TEMP_REPAYPUSH_FAIL = "尊敬的同事，资产编号%s还款推送，理财端返回异常，请及时处理。";
	
	/**
	 * 短信数量预警提醒
	 */
	public static final String MSG_COUNT_WARN = "尊敬的同事，今天短信发送量已达%s，请关注。";
	
	/**
	 * 注册成功短信
	 */
	public static final String REG_SUCCESS = "尊敬的用户，您已成功注册聚宝钱包，请下载APP，即可在线申请。";
	
	/**
	 * 助贷申请成功短信
	 */
	public static final String HELP_APPLY_SUCCESS = "尊敬的用户，您的助贷申请已提交，请耐心等待，也可加专属业务员qq:%s联系，客服：4001622772";

	public static final String DESCRIPTION = "一、基本情况描述：<br />借款人就职于%s。因资金周转需求，特通过投复利申请借款%s元。借款人以工作收入作为还款来源。<br />二、借款详情：<br />还款方式为等额本息，实际借款期限为%s个月，发标金额为%s元。<br />"
			+ "三、借款用途：<br />借款人因资金周转需要，需借款%s元进行资金周转。<br />四、提供资料：<br />1、身份证。<br />"
			+ "五、风控审核：<br />拥有自主研发的风控决策引擎,依托海量大数据，针对身份信息等实体数据关联分析，多角度、全方位进行风险量化,综合审核分析，给予借款额度%s元。<br />";
	/**
	 * 投诉工单提醒
	 */
	public static final String COMPLAIN_ALERT = "您收到一笔投诉工单，请尽快处理。";

	/**
	 *  通知提现 
	 */
	public static final String WITHDRAW_NOTICE ="尊敬的%s，您的借款%s元已到账，请登录聚宝钱包操作提现。客服：4001622772";
}
