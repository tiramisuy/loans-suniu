package com.rongdu.loans.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.loans.app.vo.AppBannerVO;

/**
 * app首页Banner处理服务接口
 * @author likang
 * @version 2017-06-20
 */
@Service
public interface AppBannerService {

	/**
	 * 根据分类类型查询Banner列表
	 * @param categoryId
	 * @return
	 */
	List<AppBannerVO> getAppBannerByType(String categoryId);
}
