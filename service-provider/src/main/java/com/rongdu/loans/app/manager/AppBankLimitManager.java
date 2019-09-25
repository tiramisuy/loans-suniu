package com.rongdu.loans.app.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.CrudService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.dao.AppBankLimitDAO;
import com.rongdu.loans.app.entity.AppBankLimit;
import com.rongdu.loans.app.vo.AppBanksVO;

/**
 * liuzhuang
 */
@Service("appBankLimitManager")
public class AppBankLimitManager extends CrudService<AppBankLimitDAO, AppBankLimit, String> {
	public static final String APP_BANK_LIMIT_LIST_CACHE_KEY = "APP_BANK_LIMIT_LIST";
	@Autowired
	private AppBankLimitDAO appBankLimitDAO;

	/**
	 * 获取银行列表
	 * 
	 * @return
	 */
	public List<AppBanksVO> getBanks() {
		String cacheKey = APP_BANK_LIMIT_LIST_CACHE_KEY;
		List<AppBanksVO> list = (List<AppBanksVO>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = appBankLimitDAO.getBanks();
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return list;
	}

	/**
	 * 获取银行编号与银行名称
	 * 
	 * @return
	 */
	public Map<String, AppBanksVO> getBankNameAndNo() {
		Map<String, AppBanksVO> rzMap = new HashMap<String, AppBanksVO>();
		// 从缓存中获取银行编号与银行名称
		List<AppBanksVO> list = (List<AppBanksVO>) JedisUtils.getObjectList(Global.BANK_NAME_NO_CACHE_KEY);
		if (null == list || list.size() == 0) {
			list = appBankLimitDAO.getBankNameAndNo();
			if (null != list && list.size() > 0) {
				// 缓存
				long rz = JedisUtils.setObjectList(Global.BANK_NAME_NO_CACHE_KEY, list, Global.ONE_DAY_CACHESECONDS);
				logger.debug("[{}] cache result:[{}]", Global.BANK_NAME_NO_CACHE_KEY, rz);
			}
		}
		if (null != list && list.size() > 0) {
			// list 转存map
			int size = list.size();
			for (int i = 0; i < size; i++) {
				AppBanksVO temp = list.get(i);
				if (null != temp) {
					rzMap.put(temp.getBankCode(), temp);
				}
			}
			return rzMap;
		}
		return null;
	}

	/**
	 * 根据银行代码获银行编号与银行名称
	 * 
	 * @return
	 */
	public AppBanksVO getBankNameAndNoByCode(String bankCode) {
		return appBankLimitDAO.getBankNameAndNoByCode(bankCode);
	}

	/**
	 * 获取银行名字
	 * 
	 * @param bankCode
	 * @return
	 */
	public String getBankName(String bankCode) {
		Map<String, AppBanksVO> map = getBankNameAndNo();
		if (map == null || map.size() == 0) {
			return null;
		}
		AppBanksVO bank = map.get(bankCode);
		if (bank != null && StringUtils.isNotBlank(bank.getBankName())) {
			return bank.getBankName();
		}
		return null;
	}
}
