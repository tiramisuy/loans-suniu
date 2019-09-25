/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.app.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.app.entity.AppUpgrade;
import com.rongdu.loans.app.option.UpgradeOP;

/**
 * App版本升级DAO接口
 * @author sunda
 * @version 2017-06-12
 */
@MyBatisDao
public interface AppUpgradeDAO extends BaseDao<AppUpgrade, String> {
	
	/**
	 * 获取最新版本信息
	 * @param 手机系统类型: [1]-ios; [2]-android
	 * @return AppVersion
	 */
	AppUpgrade getNewVersion(String type);
	
	/**
	 * 是否是最新版本
	 * @return true/false
	 */
	int isNewVersion(UpgradeOP vo);
}