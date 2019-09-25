package com.rongdu.loans.app.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.app.entity.AppBankLimit;
import com.rongdu.loans.app.vo.AppBanksVO;

import java.util.List;


/**
 * 银行信息的dao接口
 * @author likang
 *
 */
@MyBatisDao
public interface AppBankLimitDAO extends BaseDao<AppBankLimit, String> {

	/**
	 * 获取银行信息
	 * @return
	 */
	List<AppBanksVO> getBanks();
	
	/**
	 * 获取全部银行编号与银行名称
	 * @return
	 */
	List<AppBanksVO> getBankNameAndNo();
	
	/**
	 * 根据银行代码获银行编号与银行名称
	 * @return
	 */
	AppBanksVO getBankNameAndNoByCode(String bankCode);
}
