/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.app.service;

import org.springframework.stereotype.Service;

import com.rongdu.loans.app.option.UpgradeOP;
import com.rongdu.loans.app.vo.AppUpgradeVO;
import com.rongdu.loans.external.vo.BaiduIpLocationVO;
import com.rongdu.loans.loan.vo.OverdueRepayNoticeVO;
import com.rongdu.loans.loan.vo.RepayNoticeVO;


/**
 * App版本升级Service
 * @author likang
 * @version 2017-06-18
 */
@Service
//@Transactional(readOnly = true)
public interface AppUpgradeService {

	/**
	 * 获取最新版本信息
	 * @return AppVersion
	 */
	AppUpgradeVO getNewVersion(UpgradeOP vo);
	
	/**
	 * 是否是最新版本
	 * @return true/false
	 */
	boolean isNewVersion(UpgradeOP vo);
	
	
	/**
	 * ip定位
	 * @return
	 */
	BaiduIpLocationVO IpLocation(String ip);
	
	
	/**
	 * 保存经纬度定位信息
	 * return int
	 */
	int saveJWdLocation(String lat, String lng, String ip, String userId);
}