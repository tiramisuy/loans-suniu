package com.rongdu.common.utils;

import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.app.vo.AppBanksVO;

import java.util.List;

/**
 * 银行支付限额工具类，可能会被频繁调用
 * 支付限额可能随时调整，存储在缓存中，30分钟失效
 * 	银行代码、银行简称、银行名称三者基本不会改变，直接静态变量存储，访问更快
 * @author sunda
 *
 */
public class BankLimitUtils {
	
	private static List<AppBanksVO>  bankLimits = null;
	public static final String BANK_LIMIT_CACHE_ID = "BankLimit";
	/**
	 * 获取银行限额列表
	 * @return
	 */
	public static List<AppBanksVO> getBankLimits(){
		String cacheId = BANK_LIMIT_CACHE_ID;
		List<AppBanksVO>  list = (List<AppBanksVO>)JedisUtils.getObject(cacheId);
		if (list==null) {
			AppBankLimitService bankLimitService  = SpringContextHolder.getBean("appBankLimitService");
			list = bankLimitService.getBanks();
			if (list!=null) {
				JedisUtils.setObject(cacheId, list, 30*60);
				bankLimits = list;
			}
		}
		return list;
	}
	
	/**
	 * 清空银行限额列表的缓存
	 */
	public static void clearCache(){
		JedisUtils.del(BANK_LIMIT_CACHE_ID);
	}
	
	/**
	 * 获取银行限额列表
	 * @return
	 */
	private static List<AppBanksVO> getMemBankLimits(){
		return bankLimits==null?getBankLimits():bankLimits;
	}
	
	/**
	 * 根据银行代码获取支付限额
	 * @param bankCode
	 * @return
	 */
	public static AppBanksVO getByBankCode(String bankCode){
		List<AppBanksVO> list = getMemBankLimits();
		for (AppBanksVO limit:list) {
			if (limit.getBankCode().equals(bankCode)) {
				return limit;
			}
		}
		return null;
	}
	
	/**
	 * 根据银行代码获取银行名称
	 * @param bankCode
	 * @return
	 */
	public static String getNameByBankCode(String bankCode){
		List<AppBanksVO> list = getMemBankLimits();
		for (AppBanksVO limit:list) {
			if (limit.getBankCode().equals(bankCode)) {
				return limit.getBankName();
			}
		}
		return null;
	}
	
}
