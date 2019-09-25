package com.rongdu.loans.app.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.app.dao.AppUpgradeDAO;
import com.rongdu.loans.app.entity.AppUpgrade;
import com.rongdu.loans.app.option.UpgradeOP;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("appUpgradeManager")
public class AppUpgradeManager extends BaseManager<AppUpgradeDAO, AppUpgrade, String> {
	public static final String APP_UPGRADE_NEW_VERSION_CACHE_PREFIX = "APP_UPGRADE_NEW_VERSION_";
	@Autowired
	private AppUpgradeDAO appUpgradeDAO;

	/**
	 * 获取最新版本信息
	 * 
	 * @param type
	 *            手机系统类型: [1]-ios; [2]-android
	 * @return AppVersion
	 */
	public AppUpgrade getNewVersion(String type) {
		String cacheKey = APP_UPGRADE_NEW_VERSION_CACHE_PREFIX + type;
		AppUpgrade appUpgrade = (AppUpgrade) JedisUtils.getObject(cacheKey);
		if (appUpgrade == null) {
			appUpgrade = appUpgradeDAO.getNewVersion(type);
			JedisUtils.setObject(cacheKey, appUpgrade, Global.ONE_DAY_CACHESECONDS);
		}
		return appUpgrade;
	}

	/**
	 * 是否是最新版本
	 * 
	 * @return true/false
	 */
	public int isNewVersion(UpgradeOP vo) {
		return appUpgradeDAO.isNewVersion(vo);
	}
}
