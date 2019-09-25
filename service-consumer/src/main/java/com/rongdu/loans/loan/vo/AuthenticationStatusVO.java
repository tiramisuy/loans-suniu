package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 认证结果值对象
 * 
 * @author likang
 * 
 */
public class AuthenticationStatusVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7399583888023032662L;

	// 状态是
	public static final Integer YES = 1;
	// 状态否
	public static final Integer NO = 0;
	// 状态认证中
	public static final Integer ING = 2;
	
	/**
	 * 运营商认证方式 1：白骑士 2：天机
	 */
	private Integer operatorType;
	
	/**
	 * 身份认证结果
	 */
	private Integer identityStatus;

	/**
	 * ocr结果
	 */
	private Integer ocrStatus;

	/**
	 * 开电子账户结果
	 */
	private Integer eleAccountStatus;

	/**
	 * 四合一授权结果
	 */
	private Integer termsAuthStatus;

	/**
	 * 基础信息认证结果
	 */
	private Integer baseInfoStatus;

	/**
	 * 授权认证结果
	 */
	private Integer authorizationStatus;

	/**
	 * 人脸识别授权认证结果
	 */
	private Integer faceStatus;

	/**
	 * 运营商授权认证结果
	 */
	private Integer telOperatorStatus;

	/**
	 * 芝麻信息授权认证结果
	 */
	private Integer sesameCreditStatus;

	/**
	 * 借款申请受理结果
	 */
	private Integer applyStatus;

	/**
	 * 学信网认证
	 */
	private Integer xueXinStatus;

	/**
	 * 淘宝认证
	 */
	private Integer taoBaoStatus;

	/**
	 * 投复利签约授权
	 */
	private Integer signStatus;

	/**
	 * 收款绑卡认证
	 */
	private Integer bindCardStatus;

	/**
	 * 开通存管状态
	 */
	private Integer depositStatus;

	/**
	 * 信用卡认证状态
	 */
	private Integer creditCardStatus;

	public AuthenticationStatusVO() {
		operatorType = 1;
		identityStatus = NO;
		ocrStatus = NO;
		eleAccountStatus = NO;
		termsAuthStatus = NO;
		baseInfoStatus = NO;
		authorizationStatus = NO;
		faceStatus = NO;
		telOperatorStatus = NO;
		sesameCreditStatus = NO;
		applyStatus = NO;
		xueXinStatus = NO;
		taoBaoStatus = NO;
		signStatus = NO;
		bindCardStatus = NO;
		depositStatus = NO;
		creditCardStatus = NO;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Integer getCreditCardStatus() {
		return creditCardStatus;
	}

	public void setCreditCardStatus(Integer creditCardStatus) {
		this.creditCardStatus = creditCardStatus;
	}

	public Integer getIdentityStatus() {
		return identityStatus;
	}

	public Integer getOcrStatus() {
		return ocrStatus;
	}

	public Integer getEleAccountStatus() {
		return eleAccountStatus;
	}

	public Integer getBaseInfoStatus() {
		return baseInfoStatus;
	}

	public Integer getAuthorizationStatus() {
		return authorizationStatus;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setIdentityStatus(Integer identityStatus) {
		this.identityStatus = identityStatus;
	}

	public void setOcrStatus(Integer ocrStatus) {
		this.ocrStatus = ocrStatus;
	}

	public void setEleAccountStatus(Integer eleAccountStatus) {
		this.eleAccountStatus = eleAccountStatus;
	}

	public void setBaseInfoStatus(Integer baseInfoStatus) {
		this.baseInfoStatus = baseInfoStatus;
	}

	public void setAuthorizationStatus(Integer authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Integer getFaceStatus() {
		return faceStatus;
	}

	public Integer getTelOperatorStatus() {
		return telOperatorStatus;
	}

	public Integer getSesameCreditStatus() {
		return sesameCreditStatus;
	}

	public void setFaceStatus(Integer faceStatus) {
		this.faceStatus = faceStatus;
	}

	public void setTelOperatorStatus(Integer telOperatorStatus) {
		this.telOperatorStatus = telOperatorStatus;
	}

	public void setSesameCreditStatus(Integer sesameCreditStatus) {
		this.sesameCreditStatus = sesameCreditStatus;
	}

	public Integer getTermsAuthStatus() {
		return termsAuthStatus;
	}

	public void setTermsAuthStatus(Integer termsAuthStatus) {
		this.termsAuthStatus = termsAuthStatus;
	}

	public Integer getXueXinStatus() {
		return xueXinStatus;
	}

	public void setXueXinStatus(Integer xueXinStatus) {
		this.xueXinStatus = xueXinStatus;
	}

	public Integer getTaoBaoStatus() {
		return taoBaoStatus;
	}

	public void setTaoBaoStatus(Integer taoBaoStatus) {
		this.taoBaoStatus = taoBaoStatus;
	}

	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getBindCardStatus() {
		return bindCardStatus;
	}

	public void setBindCardStatus(Integer bindCardStatus) {
		this.bindCardStatus = bindCardStatus;
	}

	public Integer getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
}
