package com.rongdu.loans.koudai.api.service;

import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountOP;
import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountQueryOP;
import com.rongdu.loans.koudai.op.deposit.user.KDPwdResetOP;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountQueryResultVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountPageVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDPwdResetVO;

public interface KDDepositUserApiService {

	KDOpenAccountQueryResultVO queryAccountOpenDetail(KDOpenAccountQueryOP op);

	KDOpenAccountPageVO accountOpenEncryptPage(KDOpenAccountOP op);

	KDPwdResetVO passwordResetPage(KDPwdResetOP op);
}
