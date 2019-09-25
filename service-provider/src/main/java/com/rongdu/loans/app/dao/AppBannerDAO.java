package com.rongdu.loans.app.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.app.entity.AppBanner;

import java.util.List;

/**
 * 终端Banner的dao接口
 * @author likang
 *
 */
@MyBatisDao
public interface AppBannerDAO extends BaseDao<AppBanner, String> {

	/**
	 * 根据分类编号查询
	 * @param categoryId
	 * @return
	 */
	List<AppBanner> getAppBannerByType(String categoryId);
}
