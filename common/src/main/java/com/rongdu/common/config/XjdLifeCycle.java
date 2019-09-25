package com.rongdu.common.config;

/**
 * 现金贷生命周期
 * @author likang
 * @version 2017-07-06
 */
public class XjdLifeCycle {

	/**
	 * 身份认证（身份证OCR识别）
	 */
	public static final int LC_OCR = 11;
	
	/**
	 * 身份认证（身份证OCR识别）-未认证
	 */
	public static final int LC_OCR_0 = 110;
	
	/**
	 * 身份认证（身份证OCR识别）-已认证
	 */
	public static final int LC_OCR_1 = 111;
	
	/**
	 * 身份认证（开立电子账户）
	 */
	public static final int LC_ID = 12;
	/**
	 * 身份认证（开立电子账户）-未认证
	 */
	public static final int LC_ID_0 = 120;
	
	/**
	 * 身份认证（开立电子账户）-已认证
	 */
	public static final int LC_ID_1 = 121;
	
	/**
	 * 投复利开户状态
	 */
	public static final int LC_DEPOSIT_1 = 122;
	
	/**
	 * 填写基本信息
	 */
	public static final int LC_BASE = 13;
	/**
	 * 填写基本信息 -未完成
	 */
	public static final int LC_BASE_0 = 130;
	
	/**
	 * 填写基本信息 -已完成
	 */
	public static final int LC_BASE_1 = 131;
	
	/**
	 * 授权认证（影像认证）
	 */
	public static final int LC_FACE = 14;
	
	/**
	 * 授权认证（影像认证） -未完成
	 */
	public static final int LC_FACE_0 = 140;
	
	/**
	 * 授权认证（影像认证） -已完成
	 */
	public static final int LC_FACE_1 = 141;
	
	/**
	 * 授权认证（运营商认证）
	 */
	public static final int LC_TEL = 15;
	/**
	 * 授权认证（运营商认证） -未完成
	 */
	public static final int LC_TEL_0 = 150;
	
	/**
	 * 授权认证（运营商认证） -已完成
	 */
	public static final int LC_TEL_1 = 151;
	
	/**
	 * 授权认证（芝麻信用）
	 */
	public static final int LC_SESAME = 16;
	/**
	 * 授权认证（芝麻信用） -未完成
	 */
	public static final int LC_SESAME_0 = 160;
	
	/**
	 * 授权认证（芝麻信用） -已完成
	 */
	public static final int LC_SESAME_1 = 161;
	
	/**
	 * 学信网认证-已完成
	 */
	public static final int LC_XUEXIN_1 = 162;
	
	/**
	 * 淘宝认证-已完成
	 */
	public static final int LC_TAOBAO_1 = 163;
	
	/**
	 * 投复利代扣授权-已完成
	 */
	public static final int LC_TFL_1 = 164;
	
	/**
	 * 信用卡认证已完成
	 */
	public static final int LC_CREDIT_1 = 165;
	
	/**
	 * 借款申请
	 */
	public static final int LC_APPLY = 17;
	/**
	 * 借款申请 -未完成
	 */
	public static final int LC_APPLY_0 = 170;
	
	/**
	 * 借款申请 -已完成
	 */
	public static final int LC_APPLY_1 = 171;
	
	/**
	 *  自动审批
	 */
	public static final int LC_AUTO_AUDIT = 21;
	/**
	 *  自动审批 -待审批
	 */
	public static final int LC_AUTO_AUDIT_0 = 210;
	
	/**
	 *  自动审批 -审批通过
	 */
	public static final int LC_AUTO_AUDIT_1 = 211;
	
	/**
	 *  自动审批 -审批否决
	 */
	public static final int LC_AUTO_AUDIT_2 = 212;
	
	/**
	 *  自动审批 -进入人工复核
	 */
	public static final int LC_AUTO_AUDIT_3 = 213;
	
	/**
	 *  人工审批
	 */
	public static final int LC_ARTIFICIAL_AUDIT = 22;
	/**
	 *  人工审批 -待审批
	 */
	public static final int LC_ARTIFICIAL_AUDIT_0 = 220;
	
	/**
	 *  人工审批 -审批通过
	 */
	public static final int LC_ARTIFICIAL_AUDIT_1 = 221;
	
	/**
	 *  人工审批 -审批否决
	 */
	public static final int LC_ARTIFICIAL_AUDIT_2 = 222;
	
	/**
	 * 人工复审
	 */
	public static final int LC_ARTIFICIAL_AUDIT_3 = 223;
	
	/**
	 * 签订
	 */
	public static final int LC_SIGNED = 31;
	/**
	 * 签订 -未完成
	 */
	public static final int LC_SIGNED_0 = 310;
	
	/**
	 * 签订 -已完成
	 */
	public static final int LC_SIGNED_1 = 311;
	
	/**
	 * 募集资金
	 */
	public static final int LC_RAISE = 41;
	/**
	 * 募集资金 -待推送（购物标待推送）
	 */
	public static final int LC_RAISE_0 = 410;
	
	/**
	 * 募集资金 -推送成功（购物标推送成功）
	 */
	public static final int LC_RAISE_1 = 411;
	/**
	 * 募集资金 -推送失败
	 */
	public static final int LC_RAISE_2 = 412;
	
	/**
	 * 取消
	 */
	public static final int LC_CHANNEL = 42;
	
	/**
	 * 已取消
	 */
	public static final int LC_CHANNEL_0 = 420;
	
	/**
	 * 放款 
	 */
	public static final int LC_LENDERS = 51;
	/**
	 * 放款 -待放款
	 */
	public static final int LC_LENDERS_0 = 510;
	
	/**
	 * 放款 -已放款
	 */
	public static final int LC_LENDERS_1 = 511;
	/**
	 * 放款 -待提现
	 */
	public static final int LC_CASH_2 = 512;
	
	/**
	 * 放款 -提现处理中
	 */
	public static final int LC_CASH_3 = 513;
	
	/**
	 * 放款 -提现成功（最终放款成功）
	 */
	public static final int LC_CASH_4 = 514;
	
	/**
	 * 放款 -提现失败
	 */
	public static final int LC_CASH_5 = 515;
	
	/**
	 * 还款 
	 */
	public static final int LC_REPAY = 61;
	/**
	 * 还款 -待还款
	 */
	public static final int LC_REPAY_0 = 610;
	
	/**
	 * 还款 -提前还款
	 */
	public static final int LC_REPAY_1 = 611;
	/**
	 * 还款 -正常还款
	 */
	public static final int LC_REPAY_2 = 612;
	
	/**
	 * 逾期
	 */
	public static final int LC_OVERDUE = 71;
	/**
	 * 逾期 -待还款
	 */
	public static final int LC_OVERDUE_0 = 710;
	
	/**
	 * 逾期 -逾期还款
	 */
	public static final int LC_OVERDUE_1 = 711;
	
	/**
	 * 核销
	 */
	public static final int LC_WRITE_OFF = 81;
	/**
	 * 核销-待核销
	 */
	public static final int LC_WRITE_OFF_0 = 810;
	
	/**
	 * 核销 -已核销
	 */
	public static final int LC_WRITE_OFF_1 = 811;
}
