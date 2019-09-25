package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * 提交贷款申请参数对象
 * 
 * @author liuzhuang
 * 
 */
public class LoanApplyTFLOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1005646992743628884L;

	/**
	 * 贷款申请金额
	 */
	@NotNull(message = "贷款金额不能为空")
	@Range(min = 1000, max = 1000000, message = "借款金额需大于1000元且小于100万元")
	private BigDecimal applyAmt;

	/**
	 * 申请期限(按月)
	 */
	@NotNull(message = "申请期限不能为空")
	@Range(min = 1, max = 36, message = "申请期限超过限制")
	private Integer applyTerm;

	/**
	 * 进件来源（1-ios,2-android,3-h5,4-api）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;
	/**
	 * 还款方式
	 */
	@NotNull(message = "还款方式不能为空")
	private Integer repayMethod;
	/**
	 * 借款用途
	 */
	@NotBlank(message = "借款用途不能为空")
	private String purpose;

	/**
	 * 服务费
	 */
	private BigDecimal servFee;

	/**
	 * 日利息
	 */
	private BigDecimal dayInterest;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;
	/**
	 * 产品类型
	 */
	@NotBlank(message = "产品类型不能为空")
	@Pattern(regexp = "2", message = "产品类型有误")
	private String productType;

	/**
	 * 白骑士设备唯一标识
	 */
	@NotBlank(message = "白骑士设备唯一标识不能为空")
	private String bqsTokenKey;
	/**
	 * 同盾设备唯一标识
	 */
	@NotBlank(message = "同盾设备唯一标识不能为空")
	private String tdBlackBox;

	private String applyId; // 申请编号
	private String userId; // 客户ID
	private String ip; // IP地址

	private String orgId; // 受理机构代码
	private String orgName; // 受理机构名称
	private String channelId; // 渠道码（树形结构）
	private String salerId; // 推荐人ID

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public String getSource() {
		return source;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public String getApplyId() {
		return applyId;
	}

	public String getProductId() {
		return productId;
	}

	public String getUserId() {
		return userId;
	}

	public String getIp() {
		return ip;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getChannelId() {
		return channelId;
	}

	public String getSalerId() {
		return salerId;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getDayInterest() {
		return dayInterest;
	}

	public void setDayInterest(BigDecimal dayInterest) {
		this.dayInterest = dayInterest;
	}

	public String getBqsTokenKey() {
		return bqsTokenKey;
	}

	public String getTdBlackBox() {
		return tdBlackBox;
	}

	public void setBqsTokenKey(String bqsTokenKey) {
		this.bqsTokenKey = bqsTokenKey;
	}

	public void setTdBlackBox(String tdBlackBox) {
		this.tdBlackBox = tdBlackBox;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

}
