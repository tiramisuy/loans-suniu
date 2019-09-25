package com.rongdu.loans.loan.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.statistical.service.ZhimaStatisticalService;

/**
 * Created by liuzhuang
 */
@Controller
@RequestMapping(value = "${adminPath}/zhima")
public class ZhimaPushController extends BaseController {
	@Autowired
	private ZhimaStatisticalService zhimaStatisticalService;

	/**
	 * 芝麻数据收集
	 */
	@ResponseBody
	@RequestMapping(value = "collect")
	public WebResult collect(HttpServletRequest request) {
		try {
			String date=request.getParameter("date");
			Date d=DateUtils.parse(date, "yyyy-MM-dd");
			TaskResult result = zhimaStatisticalService.collect(d);
			return new WebResult("1", "提交成功", result);
		} catch (RuntimeException e) {
			logger.error("芝麻数据收集异常：", e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("芝麻数据收集异常：", e);
			return new WebResult("99", e.getMessage());
		}
	}
	/**
	 * 芝麻数据推送
	 */
	@ResponseBody
	@RequestMapping(value = "push")
	public WebResult push(HttpServletRequest request) {
		try {
			String date=request.getParameter("date");
			Date d=DateUtils.parse(date, "yyyy-MM-dd");
			TaskResult result = zhimaStatisticalService.pushZhimaCreditStatistics(d);
			return new WebResult("1", "提交成功", result);
		} catch (RuntimeException e) {
			logger.error("芝麻数据推送异常：", e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("芝麻数据推送异常：", e);
			return new WebResult("99", e.getMessage());
		}
	}
	
}
