package com.rongdu.loans.loan.service;

import java.text.ParseException;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.vo.RepayLogVO;

public interface RepayUnsolvedService {
	public TaskResult processRepayUnsolvedOrders();

	public TaskResult processTLRepayUnsolvedOrders();

	public void updateProcessOrderXianFeng(RepayLogVO repayLogVO) throws ParseException;

	public RepayLogVO queryRepayResultTL(RepayLogVO vo);
}
