package com.rongdu.loans.compute.helper;

import java.util.Date;

import com.rongdu.loans.loan.entity.Contract;
import com.rongdu.loans.loan.entity.LoanApply;

/**
 * Created by liuzhuang on 2017/7/26.
 */
public class ContractHelper {

	public static Date getLoanEndDate(LoanApply loanApply, Date loanStartDate) {
		Contract contract = new Contract();
		contract.setLoanStartDate(loanStartDate);

		Date loanEndDate = RepayPlanHelper.getRepayDate(loanApply, contract, loanApply.getTerm());// 最后一期还款时间为合同截止日
		return loanEndDate;
	}
}
