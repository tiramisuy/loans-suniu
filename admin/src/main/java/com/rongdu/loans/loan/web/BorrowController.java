package com.rongdu.loans.loan.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.loan.service.BorrowInfoService;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/borrow")
public class BorrowController extends BaseController {

	@Autowired
	private BorrowInfoService borrowInfoService;

	/**
	 * 手动推标
	 */
	@ResponseBody
	@RequestMapping(value = "processAdminSendBorrow")
	public WebResult processAdminSendBorrow(@RequestParam(value = "applyId") String applyId) {
		try {
//			Boolean isLendPay = UserUtils.haveRole("sdcz");
//			if (isLendPay) {
//				synchronized (BorrowController.class) {
//					borrowInfoService.processAdminSendBorrow(applyId);
//				}
//			}
			return new WebResult("1", "提交成功", null);
		} catch (RuntimeException e) {
			logger.error("手动推标异常：applyId = " + applyId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("手动推标异常：applyId = " + applyId, e);
			return new WebResult("99", "系统异常");
		}
	}

}
