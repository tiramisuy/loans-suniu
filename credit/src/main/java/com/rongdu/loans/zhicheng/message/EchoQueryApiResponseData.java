package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;
import java.util.List;

/**
 * 查询借款、风险和逾期信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class EchoQueryApiResponseData implements Serializable{

	private static final long serialVersionUID = 7209682953717889561L;
	
	/**
	 * 流水号
	 * 标识每一次查询，平台内唯一
	 */
	private String flowId;
	/**
	 * 致诚信用分
	 * 取值 300~850
	 */
	private String zcCreditScore;
	/**
	 * 违约概率
	 * 取值 0.73%~73.6%
	 */
	private String contractBreakRate;
	/**
	 * 被查询统计
	 */
	private QueryStatistics queryStatistics;
	/**
	 * 被查询历史 
	 */
	private List<QueryHistory>  queryHistory;
	/**
	 * 被查询历史 
	 */
	private List<LoanRecord>  loanRecords;
	/**
	 * 风险项记录
	 */
	private List<ResponseRiskItem>  riskResults;
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getZcCreditScore() {
		return zcCreditScore;
	}
	public void setZcCreditScore(String zcCreditScore) {
		this.zcCreditScore = zcCreditScore;
	}
	public String getContractBreakRate() {
		return contractBreakRate;
	}
	public void setContractBreakRate(String contractBreakRate) {
		this.contractBreakRate = contractBreakRate;
	}
	public QueryStatistics getQueryStatistics() {
		return queryStatistics;
	}
	public void setQueryStatistics(QueryStatistics queryStatistics) {
		this.queryStatistics = queryStatistics;
	}
	public List<QueryHistory> getQueryHistory() {
		return queryHistory;
	}
	public void setQueryHistory(List<QueryHistory> queryHistory) {
		this.queryHistory = queryHistory;
	}
	public List<LoanRecord> getLoanRecords() {
		return loanRecords;
	}
	public void setLoanRecords(List<LoanRecord> loanRecords) {
		this.loanRecords = loanRecords;
	}
	public List<ResponseRiskItem> getRiskResults() {
		return riskResults;
	}
	public void setRiskResults(List<ResponseRiskItem> riskResults) {
		this.riskResults = riskResults;
	}

}