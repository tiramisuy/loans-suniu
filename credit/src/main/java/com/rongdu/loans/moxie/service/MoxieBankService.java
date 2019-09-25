/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service;

import java.util.List;

import com.rongdu.loans.credit.moxie.vo.bank.BankReportOP;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;

/**
 * 魔蝎
 * 
 * @author liuzhuang
 * @version 2018-05-18
 */
public interface MoxieBankService {

	/**
	 * 网银报告
	 */
	public BankReportVO getReportData(BankReportOP op);

}