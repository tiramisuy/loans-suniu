package com.rongdu.loans.app.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.entity.AppBanner;
import com.rongdu.loans.app.manager.AppBannerManager;
import com.rongdu.loans.app.service.AppBannerService;
import com.rongdu.loans.app.vo.AppBannerVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 移动端Banner服务类
 * @author likang
 *
 */
@Service("appBannerService")
public class AppBannerServiceImpl extends BaseService implements AppBannerService {
	@Autowired
	private AppBannerManager appBannerManager;


	public List<AppBannerVO> getAppBannerByType(String categoryId) {
		// 初始化返回值
		if(StringUtils.isBlank(categoryId)) {
			logger.info("AppBannerService.getAppBannerByType： 参数不合法");
			return Collections.emptyList();
		}

		List<AppBanner> list = appBannerManager.getAppBannerByType(categoryId);
		if (CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		return BeanMapper.mapList(list, AppBannerVO.class);
	}


}
