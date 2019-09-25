package com.rongdu.loans.app.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.CrudService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.app.dao.AppBannerDAO;
import com.rongdu.loans.app.entity.AppBanner;

/**
 * liuzhuang on 2018/6/11.
 */
@Service("appBannerManager")
public class AppBannerManager extends CrudService<AppBannerDAO, AppBanner, String> {
	public static final String APP_BANNER_LIST_CACHE_PREFIX = "APP_BANNER_LIST_";
	@Autowired
	private AppBannerDAO appBannerDAO;

	/**
	 * 根据分类编号查询
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<AppBanner> getAppBannerByType(String categoryId) {
		String cacheKey = APP_BANNER_LIST_CACHE_PREFIX + categoryId;
		List<AppBanner> list = (List<AppBanner>) JedisUtils.getObject(cacheKey);
		if (list == null) {
			list = appBannerDAO.getAppBannerByType(categoryId);
			JedisUtils.setObject(cacheKey, list, Global.ONE_DAY_CACHESECONDS);
		}
		return list;
	}
}
