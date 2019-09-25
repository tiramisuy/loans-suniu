package com.rongdu.loans.credit.moxie.vo.bank;

import java.io.Serializable;
import java.util.List;

import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardAccountSummaryVO;
import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardDebitcardDetailsesVO;
import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardDebitcardSummaryVO;
import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardDebitcardUndueRegularBasisVO;
import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardUserBasicInfoVO;
import com.rongdu.loans.credit.moxie.vo.bank.debitcard.DebitcardWorkDetailsVO;

public class BankReportDebitcardVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 1.1 用户基本信息 （user_basic_info）
	private DebitcardUserBasicInfoVO user_basic_info;
	// 1.2 账户摘要 （account_summary）
	private DebitcardAccountSummaryVO account_summary;
	// 1.3 借记卡摘要(debitcard_summary)
	private DebitcardDebitcardSummaryVO debitcard_summary;
	// 2.1 借记卡 （debitcard_detailses）
	private DebitcardDebitcardDetailsesVO debitcard_detailses;
	// 2.2 借记卡未到期定期详情 （debitcard_undue_regular_basis_list）
	private List<DebitcardDebitcardUndueRegularBasisVO> debitcard_undue_regular_basis_list;
	// 2.3 工作单位详情 （work_details_list）
	private List<DebitcardWorkDetailsVO> work_details_list;

	public DebitcardUserBasicInfoVO getUser_basic_info() {
		return user_basic_info;
	}

	public void setUser_basic_info(DebitcardUserBasicInfoVO user_basic_info) {
		this.user_basic_info = user_basic_info;
	}

	public DebitcardAccountSummaryVO getAccount_summary() {
		return account_summary;
	}

	public void setAccount_summary(DebitcardAccountSummaryVO account_summary) {
		this.account_summary = account_summary;
	}

	public DebitcardDebitcardSummaryVO getDebitcard_summary() {
		return debitcard_summary;
	}

	public void setDebitcard_summary(DebitcardDebitcardSummaryVO debitcard_summary) {
		this.debitcard_summary = debitcard_summary;
	}

	public DebitcardDebitcardDetailsesVO getDebitcard_detailses() {
		return debitcard_detailses;
	}

	public void setDebitcard_detailses(DebitcardDebitcardDetailsesVO debitcard_detailses) {
		this.debitcard_detailses = debitcard_detailses;
	}

	public List<DebitcardDebitcardUndueRegularBasisVO> getDebitcard_undue_regular_basis_list() {
		return debitcard_undue_regular_basis_list;
	}

	public void setDebitcard_undue_regular_basis_list(
			List<DebitcardDebitcardUndueRegularBasisVO> debitcard_undue_regular_basis_list) {
		this.debitcard_undue_regular_basis_list = debitcard_undue_regular_basis_list;
	}

	public List<DebitcardWorkDetailsVO> getWork_details_list() {
		return work_details_list;
	}

	public void setWork_details_list(List<DebitcardWorkDetailsVO> work_details_list) {
		this.work_details_list = work_details_list;
	}

}
