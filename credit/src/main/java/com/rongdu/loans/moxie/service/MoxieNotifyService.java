/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.moxie.service;

import com.rongdu.loans.credit.moxie.vo.bank.BankReportNotifyVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportNotifyVO;

/**
 * 魔蝎service
 * 
 * @author liuzhuang
 * @version 2018-05-29
 */
public interface MoxieNotifyService {

	public int saveEmailReportNotify(EmailReportNotifyVO vo);

	public int saveBankReportNotify(BankReportNotifyVO vo);
}