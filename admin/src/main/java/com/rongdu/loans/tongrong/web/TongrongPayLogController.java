package com.rongdu.loans.tongrong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.tongrong.op.TongrongPayLogOP;
import com.rongdu.loans.tongrong.service.TRPayService;

@Controller
@RequestMapping(value = "${adminPath}/tongrong")
public class TongrongPayLogController extends BaseController{

	@Autowired
	private TRPayService tRPayService;
	/**
	 * 通融放款记录
	 * @param op
	 * @param first
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payLog/list")
	public String list(TongrongPayLogOP op,Boolean first, Model model) {
		model.addAttribute("payLogListOP", op);
    	if (null != first && first) {
            model.addAttribute("page", new Page(1,10));
            return "modules/tongrong/payLogList";
        }
    	Page page = new Page();
		page.setPageNo(op.getPageNo());
		page.setPageSize(op.getPageSize());
		page.setOrderBy("pay_time desc");
		Page voPage = tRPayService.findList(page, op);
		model.addAttribute("page", voPage);
		return "modules/tongrong/payLogList";
	}
	
	/**
	 * 通融手动放款
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/adminPay")
	public WebResult adminPay(String id) {
		try {
			AdminWebResult result = tRPayService.adminPay(id);
			return new WebResult(result.getCode(), result.getMsg());
		} catch (Exception e) {
			logger.error("手动放款异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	/**
	 * 通融取消放款
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/adminCancel")
	public WebResult adminCancel(String id) {
		try {
			AdminWebResult result = tRPayService.adminCancel(id);
			return new WebResult(result.getCode(), result.getMsg());
		} catch (Exception e) {
			logger.error("取消订单异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	
	
}
