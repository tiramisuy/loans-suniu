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
import com.rongdu.loans.loan.option.LoanTripProductListOP;
import com.rongdu.loans.loan.service.LoanTripProductService;
import com.rongdu.loans.loan.vo.LoanTripProductListVO;
import com.rongdu.loans.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/loan/loanTrip")
public class LoanTripProductController extends BaseController{
	
	
	@Autowired
	private LoanTripProductService loanTripProductService;
	
	/**
	 * 已放款未发放旅游券列表
	 * @param op
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("op")LoanTripProductListOP op,Boolean first, Model model) {
    	if (null != first && first) {
            model.addAttribute("page", new Page(1,10));
            return "modules/loan/tripList";
        }
		model.addAttribute("op", op);
		model.addAttribute("page", loanTripProductService.getLoanTripList(op));
		return "modules/loan/tripList";
	}
	
	

	/**
	 * 发放旅游券
	 */
	@ResponseBody
	@RequestMapping(value = "issueTicket")
	public WebResult issueTicket(@RequestParam(value = "applyId") String applyId) {
		try {
			synchronized (ApplyController.class) {
				loanTripProductService.updateCustProductAndTicket(applyId,UserUtils.getUser().getName());
			}
			return new WebResult("1", "发放旅游券成功", null);
		} catch (Exception e) {
			logger.error("取消异常：applyId = " + applyId, e);
			return new WebResult("99", "发放旅游券异常");
		}
	}
}
