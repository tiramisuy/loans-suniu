package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 贷款申请已结清记录
 * @author likang
 *
 */
public class LoansApplyFinishedVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5654238131904810142L;
    
    private Integer totalCount; // 已结清的笔数合计
    private Integer totalTerm;  // 已结清的贷款天数合计
    private List<LoansApplySummaryVO>  LoansApplySummaryList; // 贷款申请摘要列表
    
	public Integer getTotalCount() {
		return totalCount;
	}
	public Integer getTotalTerm() {
		return totalTerm;
	}
	public List<LoansApplySummaryVO> getLoansApplySummaryList() {
		return LoansApplySummaryList;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}
	public void setLoansApplySummaryList(
			List<LoansApplySummaryVO> loansApplySummaryList) {
		LoansApplySummaryList = loansApplySummaryList;
	}
}
