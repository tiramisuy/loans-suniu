package com.rongdu.loans.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.ProvinceService;
import com.rongdu.loans.cust.service.CustCouponService;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.option.GoodsOrderOP;
import com.rongdu.loans.loan.service.ShopService;
import com.rongdu.loans.loan.vo.GoodsAddressVO;
import com.rongdu.loans.loan.vo.GoodsListVO;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * Created by lee on 2018/8/28.
 */
@Controller
public class ShopController {
	@Autowired
	private ShopService shopService;

	@Autowired
	private CustCouponService custCouponService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "${adminPath}/anon/shop/getGoodsList", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult getGoodsList(HttpServletRequest request) {
		ApiResult result = new ApiResult();
		Map<String, Object> data = new HashMap<String, Object>();
		result.setData(data);

		int isLogin = 0;// 是否需要登录，0=不需要，1=需要
		String userId = request.getHeader("userId");
		String token = request.getHeader("tokenId");
		// 从缓存获取tokenid
		String cloudTokenid = JedisUtils.get(Global.USER_TOKEN_PREFIX + userId);
		if (null == cloudTokenid) {
			isLogin = 1;
		} else if (!cloudTokenid.equals(token)) {
			isLogin = 1;
		}

		int isBorrow = 0;// 是否需要借款，0=不需要，1=需要
//		if (isLogin == 0) {
//			long count = custCouponService.findUnusedCouponCount(userId);
//			if (count == 0) {
//				isBorrow = 1;
//			}
//		}
		String shopGoodsListKey = "SHOP_GOODS_LIST";
		List<GoodsListVO> goodsList = (List<GoodsListVO>) JedisUtils.getObject(shopGoodsListKey);
		if (goodsList == null) {
			goodsList = shopService.getGoodsList();
			JedisUtils.setObject(shopGoodsListKey, goodsList, Global.ONE_DAY_CACHESECONDS);
		}
		data.put("isLogin", isLogin);
		data.put("isBorrow", isBorrow);
		data.put("list", goodsList);
		return result;
	}

	@RequestMapping(value = "${adminPath}/shop/buyGoods")
	@ResponseBody
	public ApiResult buyGoods(GoodsOrderOP goodsOrderOP) {
		ApiResult result = new ApiResult();
		ApiResultVO apiResultVO = shopService.buyGoods(goodsOrderOP);
		BeanUtils.copyProperties(apiResultVO, result);
		return result;
	}

	@RequestMapping(value = "${adminPath}/shop/payGoods")
	@ResponseBody
	public ApiResult payGoods(String id, HttpServletRequest request) {
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");
		ApiResultVO apiResultVO = shopService.payGoods(id, userId);
		BeanUtils.copyProperties(apiResultVO, result);
		return result;
	}

	@RequestMapping(value = "${adminPath}/shop/cancelOrder")
	@ResponseBody
	public ApiResult cancelOrder(String id) {
		ApiResult result = new ApiResult();
		ApiResultVO apiResultVO = shopService.cancelOrder(id);
		BeanUtils.copyProperties(apiResultVO, result);
		return result;
	}

	@RequestMapping(value = "${adminPath}/shop/updateAddress")
	@ResponseBody
	public ApiResult updateAdress(GoodsAddressVO goodsAddressVO, HttpServletRequest request) {
		ApiResult result = new ApiResult();
		ApiResultVO apiResultVO = shopService.updateAdress(goodsAddressVO);
		BeanUtils.copyProperties(apiResultVO, result);
		return result;
	}

	// @RequestMapping(value = "${adminPath}/shop/selectAddress")
	// @ResponseBody
	// public ApiResult selectAdress(HttpServletRequest request) {
	// ApiResult result = new ApiResult();
	// String userId = request.getHeader("userId");
	// ApiResultVO apiResultVO = shopService.selectAdress(userId);
	// BeanUtils.copyProperties(apiResultVO, result);
	// return result;
	// }

	/**
	 * 临时begin
	 */
	@Autowired
	private CityService cityService;

	@Autowired
	private ProvinceService provinceService;

	/**
	 * 临时 获取市、县列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/shop/selectAddress", name = "获取市列表")
	@ResponseBody
	public ApiResult selectAddress(HttpServletRequest request) {
		String userId = request.getHeader("userId");
		String sPid = request.getParameter("pid");
		// 初始化返回对象
		ApiResult result = new ApiResult();

		if (StringUtils.isNotBlank(userId) && StringUtils.isBlank(sPid)) {
			ApiResultVO apiResultVO = shopService.selectAdress(userId);
			BeanUtils.copyProperties(apiResultVO, result);
		} else {
			Integer pid = Integer.parseInt(sPid);
			List list = new ArrayList();
			if (pid == null || pid < 0) {
				list = provinceService.getAllList();
				if (null == list || list.size() == 0) {
					result.set(ErrInfo.DEL_CACHE_FAIL);
					return result;
				}
			} else {
				list = cityService.getAllCityByPid(pid);
				if (null == list || list.size() == 0) {
					result.set(ErrInfo.DEL_CACHE_FAIL);
					return result;
				}
			}
			result.setData(list);
		}
		return result;
	}
	/**
	 * 临时end
	 */
}
