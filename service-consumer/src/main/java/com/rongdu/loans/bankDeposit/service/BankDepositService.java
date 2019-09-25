package com.rongdu.loans.bankDeposit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.loans.bankDeposit.option.AgreeWithdrawOP;
import com.rongdu.loans.bankDeposit.option.OpenAccountOP;
import com.rongdu.loans.bankDeposit.option.TermsAuthOP;
import com.rongdu.loans.bankDeposit.vo.AuthQueryVO;
import com.rongdu.loans.bankDeposit.vo.OpenAccountResultVO;
import com.rongdu.loans.bankDeposit.vo.SMCodeVO;
import com.rongdu.loans.bankDeposit.vo.TermsAuthVO;
import com.rongdu.loans.bankDeposit.vo.TransactionRecordVO;

/**
 * 银行存管相关  服务接口
 * @author likang
 * @version 2017-07-23
 */
@Service
public interface BankDepositService {

	/**
	 * 发送存管（恒丰银行）短信验证码
	 * @param mobile
	 */
	public SMCodeVO sendBankDepositSMCode(String mobile);
	
	/**
	 * 发送四合一授权短信验证码
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public SMCodeVO sendTermsAuthSMCode(String mobile, String userId);
	
	/**
	 * 存管（恒丰银行）开户
	 * @param openAccountOP
	 * @return
	 */
	public OpenAccountResultVO openBankDepositAccount(OpenAccountOP openAccountOP ,String productId);
	
	/**
	 * 查询交易记录
	 * @param mobile 手机号
	 * @return
	 */
	public List<TransactionRecordVO> getTransactionRecordS(String mobile);
	
	/**
	 * 查询电子账户
	 * @param idNo 身份证号
	 * @param bindBankCode 绑定银行编号
	 * @return
	 */
	public String getAccountId(String idNo, String bindBankCode);
	
    /**
     * 四合一授权
     * @param termsAuthOP
     * @return
     */
	public TermsAuthVO termsAuth(TermsAuthOP termsAuthOP);
	
	/**
	 * 免密提现
	 * @param agreeWithdrawOP
	 * @return 提现金额
	 */
	public String agreeWithdraw(AgreeWithdrawOP agreeWithdrawOP);
	
	/**
	 * 四合一授权明细查询
	 * @param agreeWithdrawOP
	 * @return 四合一授权明细
	 */
	public AuthQueryVO authQuery(String accountId);
}
