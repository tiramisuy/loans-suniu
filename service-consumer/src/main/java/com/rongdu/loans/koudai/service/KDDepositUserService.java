package com.rongdu.loans.koudai.service;

import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountPageVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountQueryResultVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDPwdResetVO;

public interface KDDepositUserService {
	KDOpenAccountQueryResultVO queryAccountOpenDetail(String userId);

	KDOpenAccountPageVO openAccount(String userId);

	KDPwdResetVO resetPwd(String userId);
}
