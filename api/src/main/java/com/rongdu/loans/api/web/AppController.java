/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.api.web;

import java.util.List;

import javax.validation.Valid;

import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.app.vo.AppBanksVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.app.option.UpgradeOP;
import com.rongdu.loans.app.service.AppBannerService;
import com.rongdu.loans.app.service.AppUpgradeService;
import com.rongdu.loans.app.vo.AppBannerVO;
import com.rongdu.loans.app.vo.AppUpgradeVO;
import com.rongdu.loans.external.vo.BaiduIpLocationVO;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.mq.MessageProductorService;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * App版本升级Controller
 * @author likang
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/app")
public class AppController extends BaseController {

	@Autowired
	private AppUpgradeService appUpgradeService;
	
	@Autowired
	private AppBannerService appBannerService;

	@Autowired
	private AppBankLimitService appBankLimitService;

	
	/**
	 * 获取最新版本 
	 * @param vo
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/getNewVersion", method = RequestMethod.POST, name="获取最新版本")
	@ResponseBody
	public ApiResult getNewVersion(@Valid UpgradeOP vo,Errors errors) {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 初始化返回对象
		ApiResult result = new ApiResult();
		AppUpgradeVO entity = null;
		//安卓2.0及以上版本才提示更新
		int versionSta = 2;
		if(StringUtils.isNotBlank(vo.getAppVerson())) {
			versionSta = Integer.parseInt(vo.getAppVerson().substring(0, 1));
		}
		if("2".equals(vo.getType()) && versionSta >= 2) {
			vo.setType("22");
			entity = appUpgradeService.getNewVersion(vo);
		} else {
			entity = appUpgradeService.getNewVersion(vo);
		}
		if(null == entity) {
			result.set(ErrInfo.NOTFIND_VERSION);
			return result;
		}
		result.setData(entity);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pushMessage")
	public ApiResult pushMessage() {
		ApiResult result = new ApiResult();
//		long start  = System.currentTimeMillis();
//		messageProductorService.sendDataToQueue("loanApplyQuene", "message content");
//		long end  = System.currentTimeMillis();
//		logger.debug("消息推送耗时：{}ms",(end-start));
		return result;
	}
	
	/**
	 * 判断是否为最新版本
	 * @param vo
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/isNewVersion", method = RequestMethod.POST, name="判断是否为最新版本")
	@ResponseBody
	public ApiResult isNewVersion(@Valid UpgradeOP vo,Errors errors) {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 初始化返回对象
		ApiResult result = new ApiResult();
		boolean is = appUpgradeService.isNewVersion(vo);
		result.setData(is);
		return result;
	}

	/**
	 * 获取Banner图片
	 * @param categoryId 
	 * @return
	 */
	@RequestMapping(value = "/getBanners", method = RequestMethod.POST, name="获取Banner图片")
	@ResponseBody
	public ApiResult getBanners(@RequestParam(value = "source", required = false) Integer source,
			@RequestParam(value = "productId", required = false) String productId) {
		// 初始化返回对象
		ApiResult result = new ApiResult();
		List<AppBannerVO> list = null;
		//新版安卓或者苹果
		if((source != null && source == 2) || StringUtils.isNotBlank(productId)) {
			list = appBannerService.getAppBannerByType("10");
		} else {
			list = appBannerService.getAppBannerByType(Global.APP_BANNERS_CATEGORY_ID); //安卓老版本
		}
		result.setData(list);
		return result;
	}

	/**
	 * 获取银行列表
	 * @return
	 */
	@RequestMapping(value = "/getBanks", method = RequestMethod.POST, name="获取银行列表")
	@ResponseBody
	public ApiResult getBanks() {
		// 初始化返回对象
		ApiResult result = new ApiResult();
		List<AppBanksVO> list =
				appBankLimitService.getBanks();
		result.setData(list);
		return result;
	}
	
	@RequestMapping(value = "/ip", method = RequestMethod.POST, name="ip定位")
	@ResponseBody
	public ApiResult ip(String ip) {
		// 初始化返回对象
		ApiResult result = new ApiResult();
		BaiduIpLocationVO is = appUpgradeService.IpLocation(ip);
		result.setData(is);
		return result;
	}
}