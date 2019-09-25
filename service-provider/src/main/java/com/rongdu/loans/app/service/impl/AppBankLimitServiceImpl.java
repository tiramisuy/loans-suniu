package com.rongdu.loans.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.manager.AppBankLimitManager;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.app.vo.AppBanksVO;

/**
 * 银行信息处理服务类
 */
@Service("appBankLimitService")
public class AppBankLimitServiceImpl extends BaseService implements AppBankLimitService {
	@Autowired
	private AppBankLimitManager appBankLimitManager;

	@Override
	public List<AppBanksVO> getBanks() {
		return appBankLimitManager.getBanks();
	}

	@Override
	public AppBanksVO getBankNameAndNoByCode(String bankCode) {
		if (StringUtils.isNotBlank(bankCode)) {
			return appBankLimitManager.getBankNameAndNoByCode(bankCode);
		} else {
			logger.error("param bankCode is null");
		}
		return null;
	}

	public boolean isOpen(String bankCode) {
		List<AppBanksVO> list = getBanks();
		for (AppBanksVO vo : list) {
			if (vo.getBankCode().equals(bankCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isOpenByBankNo(String bankNo) {
		List<AppBanksVO> list = getBanks();
		/*bankNo = bankNo.replaceAll("^(0+)","");
		bankNo = String.format("%-7s",bankNo).replace(" ","0");*/
		for (AppBanksVO vo : list) {
			if (vo.getBankNo().equals(bankNo)) {
				return true;
			}
		}
		return false;
	}
}
