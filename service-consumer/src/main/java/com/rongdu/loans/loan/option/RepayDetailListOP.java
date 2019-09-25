package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class RepayDetailListOP implements Serializable {

	private static final long serialVersionUID = -6219847033510500874L;
	/**
	 * 待还款ID
	 */
	private String id;
	/**
	 * 申请id
	 */
	private String applyId;
	/**
	 * 合同编号
	 */
	private String contNo;
	/**
	 * 客户名称
	 */
	private String userName;
	/**
	 * 客户ID
	 */
	private String userId;
	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 是否已经结清（0-否，1-是）
	 */
	private Integer status;
	/**
	 * 0.正常 1.提前 2.逾期
	 */
	private Integer sign;
	/**
	 * 还款类型（0-主动还款，1-自动还款）
	 */
	private String repayType;
	/**
	 * 借款时间
	 */
	private Date borrowTimeStart;
	private Date borrowTimeEnd;
	/**
	 * 应该还款时间
	 */
	private Date expectTimeStart;
	private Date expectTimeEnd;
	/**
	 * 实际还款时间
	 */
	private Date actualTimeStart;
	private Date actualTimeEnd;
	/**
	 * 复选逾期时间段
	 */
	private List<OverdueTime> overdueList;

	private Integer stage = 0; // 前端页面：0:还款明细 1逾期催收列表 2催收已还列表 3还款明细
	private Integer pageNo = 1;
	private Integer pageSize = 10;

	private String productId; // 产品id

	private String companyId; // 商户Id

	private String approverName; // 审核人

	private Integer isDelaySettlement;// 是否延期结清，1=是，2=否
	
	private String channelId; // 渠道码
	
	private String termType;	//申请期限类型 1:14天；2:90天；3：184天；4：其他
	
	private Integer withdrawalSource;	//'放款来源：0:线上,1:线下
	
	private String outsideID;	//标Id
	
	private Integer loanStatus;	//借款状态 0:放款；1:取消

	private String thisTerm;

	/** 保存工单时间 */
	private Date applyCreateTimeStart;
	private Date applyCreateTimeEnd;

	/**
	 * 是否为复贷
	 */
	private String loanSucess;

	// 宜信决策分
	private String compositeScore;

	public Date getApplyCreateTimeStart() {
		return applyCreateTimeStart;
	}

	public void setApplyCreateTimeStart(Date applyCreateTimeStart) {
		this.applyCreateTimeStart = applyCreateTimeStart;
	}

	public Date getApplyCreateTimeEnd() {
		return applyCreateTimeEnd;
	}

	public void setApplyCreateTimeEnd(Date applyCreateTimeEnd) {
		this.applyCreateTimeEnd = applyCreateTimeEnd;
	}

	public String getThisTerm() {
		return thisTerm;
	}

	public void setThisTerm(String thisTerm) {
		this.thisTerm = thisTerm;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public class OverdueTime implements Serializable {
		/**
		 * 逾期时间
		 */
		private Integer min;
		private Integer max;

		public Integer getMin() {
			return min;
		}

		public void setMin(Integer min) {
			this.min = min;
		}

		public Integer getMax() {
			return max;
		}

		public void setMax(Integer max) {
			this.max = max;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public Date getBorrowTimeStart() {
		return borrowTimeStart;
	}

	public void setBorrowTimeStart(Date borrowTimeStart) {
		this.borrowTimeStart = borrowTimeStart;
	}

	public Date getBorrowTimeEnd() {
		return borrowTimeEnd;
	}

	public void setBorrowTimeEnd(Date borrowTimeEnd) {
		this.borrowTimeEnd = borrowTimeEnd;
	}

	public Date getExpectTimeStart() {
		return expectTimeStart;
	}

	public void setExpectTimeStart(Date expectTimeStart) {
		this.expectTimeStart = expectTimeStart;
	}

	public Date getExpectTimeEnd() {
		return expectTimeEnd;
	}

	public void setExpectTimeEnd(Date expectTimeEnd) {
		this.expectTimeEnd = expectTimeEnd;
	}

	public Date getActualTimeStart() {
		return actualTimeStart;
	}

	public void setActualTimeStart(Date actualTimeStart) {
		this.actualTimeStart = actualTimeStart;
	}

	public Date getActualTimeEnd() {
		return actualTimeEnd;
	}

	public void setActualTimeEnd(Date actualTimeEnd) {
		this.actualTimeEnd = actualTimeEnd;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public List<OverdueTime> getOverdueList() {
		return overdueList;
	}

	public void setOverdueList(List<OverdueTime> overdueList) {
		this.overdueList = overdueList;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Integer getIsDelaySettlement() {
		return isDelaySettlement;
	}

	public void setIsDelaySettlement(Integer isDelaySettlement) {
		this.isDelaySettlement = isDelaySettlement;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Integer getWithdrawalSource() {
		return withdrawalSource;
	}

	public void setWithdrawalSource(Integer withdrawalSource) {
		this.withdrawalSource = withdrawalSource;
	}

	public String getOutsideID() {
		return outsideID;
	}

	public void setOutsideID(String outsideID) {
		this.outsideID = outsideID;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLoanSucess() {
		return loanSucess;
	}

	public void setLoanSucess(String loanSucess) {
		this.loanSucess = loanSucess;
	}

	public String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(String compositeScore) {
		this.compositeScore = compositeScore;
	}
}
