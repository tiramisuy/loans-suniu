package com.rongdu.loans.app.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.entity.AppUpgrade;
import com.rongdu.loans.app.manager.AppUpgradeManager;
import com.rongdu.loans.app.option.UpgradeOP;
import com.rongdu.loans.app.service.AppUpgradeService;
import com.rongdu.loans.app.vo.AppUpgradeVO;
import com.rongdu.loans.external.manager.BaiduMapManager;
import com.rongdu.loans.external.vo.BaiduIpLocationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * App版本升级Service实现
 * @author likang
 * @version 2017-06-20
 */
@Service("appUpgradeService")
public class AppUpgradeServiceImpl extends BaseService implements AppUpgradeService {

	@Autowired
	private AppUpgradeManager appUpgradeManager;
	@Autowired
	private BaiduMapManager baiduMapManager;
	
	public AppUpgradeVO getNewVersion(UpgradeOP vo) {
		// 参数判断
		if(null == vo || StringUtils.isBlank(vo.getAppVerson()) 
				|| null == vo.getType()) {
			logger.info("AppUpgradeService.getNewVersion： 参数不合法");
			return null;
		}
		// 获取最新版本
		AppUpgrade newVersion = appUpgradeManager.getNewVersion(vo.getType());
		if (newVersion == null){
			logger.info("AppUpgradeService.getNewVersion： 数据异常");
			return null;
		}
		// 判断是否为最新版本   
		AppUpgradeVO result = BeanMapper.map(newVersion, AppUpgradeVO.class);
		result.setNewVersionToReq(StringUtils.equals(newVersion.getVersion(), vo.getAppVerson()));
		return result;

	}

	public boolean isNewVersion(UpgradeOP vo) {
		// 参数判断
		if(null == vo || StringUtils.isBlank(vo.getAppVerson()) 
				|| null == vo.getType()) {
			logger.info("AppUpgradeService.isNewVersion： 参数不合法");
			return false;
		} else {
			// 统计当前版本是最新版本的记录条数
			int rz = appUpgradeManager.isNewVersion(vo);
			return rz == 1;
		}
	}

	@Override
	public BaiduIpLocationVO IpLocation(String ip) {
		return baiduMapManager.IpLocation(ip);
	}
	
	@Override
	public int saveJWdLocation(String lat, String lng, String ip, String userId) {
		return baiduMapManager.saveLocation(lat, lng, ip, userId);
	}
	
}