package com.rongdu.loans.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.loans.app.vo.AppBanksVO;

/**
 * 银行信息处理服务接口
 * 
 * @author likang
 * @version 2017-06-26
 */
@Service
public interface AppBankLimitService {

	/**
	 * 获取银行列表
	 * 
	 * @return
	 */
	List<AppBanksVO> getBanks();

	/**
	 * 根据银行代码获银行编号与银行名称
	 * 
	 * @return
	 */
	AppBanksVO getBankNameAndNoByCode(String bankCode);

	/**
	 * 银行是否开启
	 * 
	 * @param bankCode
	 * @return
	 */
	boolean isOpen(String bankCode);

	boolean isOpenByBankNo(String bankNo);
}
