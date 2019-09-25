package com.rongdu.loans.loan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.loan.option.ShoppedCouponOP;
import com.rongdu.loans.loan.service.ShoppedCouponService;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/loan/shopcoupon")
public class ShoppedCouponController extends BaseController{

	@Autowired
	private ShoppedCouponService shoppedCouponService;
	
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("ShoppedCouponOP")ShoppedCouponOP op,Boolean first, Model model) {
    	if (null != first && first) {
            model.addAttribute("page", new Page(1,10));
            return "modules/loan/shoppedCouponList";
        }
		model.addAttribute("op", op);
		model.addAttribute("page", shoppedCouponService.getShoppedCouponList(op));
		return "modules/loan/shoppedCouponList";
	}
	
	@ResponseBody
	@RequestMapping(value = "generateCoupon")
	public WebResult generateCoupon(@RequestParam(value = "applyId") String applyId) {
		User user = UserUtils.getUser();
		logger.info("发放购物券--->{}--->{}", user.getId(), user.getName());
		try {
			shoppedCouponService.generateCoupon(applyId, user.getName());
			return new WebResult("1", "发放成功");
		} catch (Exception e) {
			logger.error("发放购物券异常：applyId = " + applyId, e);
			return new WebResult("99", "发放购物券异常");
		}
	}
}
