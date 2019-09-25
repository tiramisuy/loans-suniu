package com.rongdu.loans.pay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 同步电子账户余额及线下充值流水
 * 
 * @author sunda
 * 
 * @verion 2017-03-27
 *
 */
public class EAccoutSyncUtils {
	
	/**
	 * 日志对象
	 */
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 同步电子账户URL
	 */
	public static String SYNC_URL = "http://iweb.rongdu.com/user/updateChargeRecord.do?userId={userId}";
	/**
	 * 获取银行限额列表
	 * @return
	 */
	public void syncEAccoutRecord(String userId){
		RestTemplate client = new RestTemplate();
		String result = client.getForObject(SYNC_URL, String.class, userId);		
		logger.info("正在同步电子账户余额及交易流水：{}，{}", userId,result);
	}
	
	
}
