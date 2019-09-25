package com.rongdu.loans.koudai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.koudai.api.service.KDDepositUserApiService;
import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountOP;
import com.rongdu.loans.koudai.op.deposit.user.KDOpenAccountQueryOP;
import com.rongdu.loans.koudai.op.deposit.user.KDPwdResetOP;
import com.rongdu.loans.koudai.service.KDDepositUserService;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountQueryResultVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountPageVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDPwdResetVO;

@Service("kdDepositUserServiceImpl")
public class KDDepositUserServiceImpl implements KDDepositUserService {
	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private KDDepositUserApiService kdDepositUserApiService;

	@Override
	public KDOpenAccountQueryResultVO queryAccountOpenDetail(String userId) {
		CustUser user = custUserManager.getById(userId);
		KDOpenAccountQueryOP op = new KDOpenAccountQueryOP();
		// op.setIdNumber(user.getIdNo());
		op.setIdNumber("421003199210134023");
		return kdDepositUserApiService.queryAccountOpenDetail(op);
	}

	public KDOpenAccountPageVO openAccount(String userId) {
		CustUser user = custUserManager.getById(userId);
		String sex = user.getSex() == null || user.getSex() == 1 ? "M" : "F";
		KDOpenAccountOP op = new KDOpenAccountOP();
		// op.setName(user.getRealName());
		// op.setMobile(user.getMobile());
		// op.setGender(sex);

		op.setName("孙春艳");
		op.setMobile("13277091298");
		op.setGender("F");

		// op.setIndustry("互联网");
		// op.setJobNature("普通职员");
		// op.setRepaymentMoneySource("个人收入");
		// op.setAnnualIncome("年入100w");
		// op.setDebtState("无");
		return kdDepositUserApiService.accountOpenEncryptPage(op);
	}

	public KDPwdResetVO resetPwd(String userId) {
		CustUser user = custUserManager.getById(userId);
		KDPwdResetOP op = new KDPwdResetOP();
		// op.setIdNo(user.getIdNo());
		op.setIdNo("421003199210134023");
		return kdDepositUserApiService.passwordResetPage(op);
	}
}
