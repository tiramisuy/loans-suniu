package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



/**
 * 贷款产品VO
 * @author likang
 *
 */
public class LoanProductVO implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2943308870425637382L;
	
	private String productId;          // 产品代码
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
	private String status;		// 产品状态(0-下架，1-正常)
	private String imgUrl; //图片路径
	private String loanPeriod;		// 借款期限
	
	/*private List<LoanProductTermVO> loanProductTerm;*/ // 贷款期限列表
	private List<PromotionCaseVO> loanProductRate; // 贷款利率列表
	
	private BigDecimal personAmt; //自然人最大可借
	private Map<String, String> loanPurpose; //借款用途
	private Map<String, String> loanProductTerm; // 贷款期限列表
	private Map<Integer, String> repayMethods;		// 还款方式（1按月等额本息，4按月付息、到期还本）
	
	private String remark; //文案提示 服务费or购物金
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<PromotionCaseVO> getLoanProductRate() {
		return loanProductRate;
	}
	public void setLoanProductRate(List<PromotionCaseVO> loanProductRate) {
		this.loanProductRate = loanProductRate;
	}
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
	
/*	public List<LoanProductTermVO> getLoanProductTerm() {
		return loanProductTerm;
	}
	public void setLoanProductTerm(List<LoanProductTermVO> loanProductTerm) {
		this.loanProductTerm = loanProductTerm;
	}*/
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
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
	public BigDecimal getPersonAmt() {
		return personAmt;
	}
	public void setPersonAmt(BigDecimal personAmt) {
		this.personAmt = personAmt;
	}
	public Map<String, String> getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(Map<String, String> loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public Map<String, String> getLoanProductTerm() {
		return loanProductTerm;
	}
	public void setLoanProductTerm(Map<String, String> loanProductTerm) {
		this.loanProductTerm = loanProductTerm;
	}
	public Map<Integer, String> getRepayMethods() {
		return repayMethods;
	}
	public void setRepayMethods(Map<Integer, String> repayMethods) {
		this.repayMethods = repayMethods;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}	
	
}
