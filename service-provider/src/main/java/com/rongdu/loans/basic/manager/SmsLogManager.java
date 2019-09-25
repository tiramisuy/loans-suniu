package com.rongdu.loans.basic.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.dao.SmsLogDAO;
import com.rongdu.loans.basic.entity.SmsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信日志本地接口
 * @author likang
 *
 */
@Service("smsLogManager")
public class SmsLogManager {

	@Autowired
	private SmsLogDAO smsLogDAO;

	/**
	 * 保存短信发送日志
	 * @return
	 */
	public int saveSmsLog(SmsLog smsLog) {
		return smsLogDAO.insert(smsLog);
	}

	/**
	 * ip 注册黑名单
	 * @param ip
	 * @return
	 */
	public boolean isRegBlackList(String ip) {
		String limit  = Global.getConfig("ip.smscode.reg.max");
		int blLimit = 6;
		if(StringUtils.isNotBlank(limit)) {
			blLimit = Integer.valueOf(limit).intValue();
		}
		return smsLogDAO.countRegBlackList(ip) > blLimit;
	}

}
