/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service;

import java.util.List;

import com.rongdu.loans.credit.moxie.vo.email.EmailReportOP;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;

/**
 * 魔蝎
 * 
 * @author liuzhuang
 * @version 2018-05-18
 */
public interface MoxieEmailService {

	/**
	 * 信用卡邮箱报告
	 */
	public List<EmailReportVO> getReportData(EmailReportOP op);

}