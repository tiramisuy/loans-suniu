/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.cust.option.ShopOrderOP;
import com.rongdu.loans.loan.service.ShopService;
import com.rongdu.loans.loan.vo.GoodsOrderVO;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 购物订单表Controller
 * @author fy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/shopOrder")
public class ShopOrderController extends BaseController {

	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "list")
	public String list(ShopOrderOP shopOrder, Model model) {
		model.addAttribute("shopOrder", shopOrder);
		Page<GoodsOrderVO> page = shopService.goodsOrderList(shopOrder);
		model.addAttribute("page", page);
		return "modules/loan/shopOrderList";
	}
	
	
	
	/**
	 * 加载确认发货页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toConfimShop")
	public WebResult toConfimShop(@RequestParam(value = "shopOrderID") String shopOrderID) {
		try {
			return new WebResult("1", "提交成功", shopOrderID);
		} catch (Exception e) {
			logger.error("加载催收页面异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	

	/**
	 * 确认发货,修改商品订单状态
	 */
	@ResponseBody
	@RequestMapping(value = "deliverConfirm")
	public WebResult deliverConfirm(@RequestParam(value = "shopOrderID") String shopOrderID,@RequestParam(value = "expressNo") String expressNo) {
		try {
			int num = shopService.updateGoodsOrderDeliver(shopOrderID,expressNo,UserUtils.getUser().getName());
			if (0 != num) {
				return new WebResult("1", "提交成功");
			} else {
				return new WebResult("99", "发货失败");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return new WebResult("99", "系统异常");
		}
	}
	/**
	 * 取消发货,修改商品订单状态
	 */
	@ResponseBody
	@RequestMapping(value = "cancelDeliver")
	public WebResult cancelDeliver(@RequestParam(value = "shopOrderID") String shopOrderID) {
		try {
			Boolean flag = shopService.cancelDeliver(shopOrderID,UserUtils.getUser().getName());
			
			if (flag) {
				return new WebResult("1", "提交成功");
			} else {
				return new WebResult("99", "取消失败");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return new WebResult("99", "系统异常");
		}
	}
}