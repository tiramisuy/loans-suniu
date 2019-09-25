package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 贷款产品表映射数据实体
 * @author likang
 *
 */
public class LoanProduct extends BaseEntity<LoanProduct> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1092106600859412280L;
	
	/**
	 * 默认产品id
	 */
	public static final String DEF_PRODUCT_ID = "XJD";
	
	private String name;		// 产品名称
	private String type;		// 产品类型（0-现金贷）
	private String description;		// 产品描述
	private Integer minIncrAmt;		// 最小递增金额
	private BigDecimal minAmt;		// 单笔贷款最小金额
	private BigDecimal maxAmt; // 单笔贷款最大金额
	private String repayFreq;		// 还款间隔单位（M-月、Q-季、Y-年）,多期情况下有值
	private BigDecimal repayUnit;		// 还款间隔（1）,多期情况下有值
	private Integer repayMethod;		// 还款方式（1按月等额本息，2按月等额本金，3一次性还本付息，4按月付息、到期还本）
	private Integer prepay;		//  是否可以提前还款(0-否，1-是)
	private Integer minLoanDay;		// 最少持有天数(不可提前还款)
	private Integer startInterest; // 起息日延后期限（默认从放款当天计息）
	private Integer graceDay; // 逾期宽限天数
	private String status;		// 产品状态(0-已售罄，1-正常)
	private String imgUrl; //图片路径
	private String loanPeriod;		// 借款期限
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public Integer getMinIncrAmt() {
		return minIncrAmt;
	}
	public BigDecimal getMinAmt() {
		return minAmt;
	}
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}
	public Integer getRepayMethod() {
		return repayMethod;
	}
	public Integer getPrepay() {
		return prepay;
	}
	public Integer getMinLoanDay() {
		return minLoanDay;
	}
	public Integer getStartInterest() {
		return startInterest;
	}
	public Integer getGraceDay() {
		return graceDay;
	}
	public String getStatus() {
		return status;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMinIncrAmt(Integer minIncrAmt) {
		this.minIncrAmt = minIncrAmt;
	}
	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}
	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}
	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}
	public void setPrepay(Integer prepay) {
		this.prepay = prepay;
	}
	public void setMinLoanDay(Integer minLoanDay) {
		this.minLoanDay = minLoanDay;
	}
	public void setStartInterest(Integer startInterest) {
		this.startInterest = startInterest;
	}
	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRepayFreq() {
		return repayFreq;
	}
	public BigDecimal getRepayUnit() {
		return repayUnit;
	}
	public void setRepayFreq(String repayFreq) {
		this.repayFreq = repayFreq;
	}
	public void setRepayUnit(BigDecimal repayUnit) {
		this.repayUnit = repayUnit;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
