/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.loan.option.LoanTrafficOP;
import com.rongdu.loans.loan.option.LoanTrafficStatisticsOP;
import com.rongdu.loans.loan.service.LoanTrafficService;
import com.rongdu.loans.loan.vo.LoanTrafficStatisticsVO;
import com.rongdu.loans.loan.vo.LoanTrafficVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 导流平台产品信息Controller
 * @author raowb
 * @version 2018-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/loanTraffic")
public class LoanTrafficController extends BaseController {

	@Autowired
	private LoanTrafficService loanTrafficService;

	@ModelAttribute
	public LoanTrafficVO get(@RequestParam(required=false) String id) {
		LoanTrafficVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = loanTrafficService.get(id);
		}
		if (entity == null){
			entity = new LoanTrafficVO();
		}
		return entity;
	}
	
	//@RequiresPermissions("loan:loanTraffic:view")
	@RequestMapping(value = {"list", ""})
	public String list(LoanTrafficOP op, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LoanTrafficVO> page = loanTrafficService.findTrafficPage(new Page<LoanTrafficVO>(request, response), op); 
		model.addAttribute("page", page);
		return "modules/loan/loanTrafficList";
	}

	//@RequiresPermissions("loan:loanTraffic:view")
	@RequestMapping(value = "view")
	public String view(LoanTrafficVO loanTraffic, Model model) {
		model.addAttribute("loanTraffic", loanTraffic);
		return "modules/loan/loanTrafficView";
	}

	//@RequiresPermissions("loan:loanTraffic:view")
	@RequestMapping(value = "form")
	public String form(LoanTrafficVO loanTraffic, Model model) {
		
		if(StringUtils.isBlank(loanTraffic.getId())){
			loanTraffic.setType("0");
		}
		
		model.addAttribute("loanTraffic", loanTraffic);
		return "modules/loan/loanTrafficForm";
	}

	//@RequiresPermissions("loan:loanTraffic:edit")
	@RequestMapping(value = "save")
	public String save(LoanTrafficVO loanTraffic, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if(StringUtils.isNotBlank(loanTraffic.getId())){
			loanTraffic.setUpdateBy(user.getName());
		}else{
			loanTraffic.setCreateBy(user.getName());
			loanTraffic.setUpdateBy(user.getName());
		}
		
		if (!beanValidator(model, loanTraffic)){
			return form(loanTraffic, model);
		}
		loanTrafficService.saveOrUpdate(loanTraffic);
		addMessage(redirectAttributes, "保存导流平台产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/loan/loanTraffic/?repage";
	}
	
	
	//@RequiresPermissions("loan:loanTrafficStatistics:view")
	@RequestMapping(value = {"trafficStatisticsList"})
	public String trafficStatisticsList(LoanTrafficStatisticsOP loanTrafficStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LoanTrafficStatisticsVO> page = loanTrafficService.findTrafficStatisticsPage(new Page<LoanTrafficStatisticsVO>(request, response), loanTrafficStatistics); 

		model.addAttribute("page", page);
		return "modules/loan/loanTrafficStatisticsList";
	}
	
	
	
//	@RequiresPermissions("loan:loanTraffic:edit")
//	@RequestMapping(value = "delete")
//	public String delete(LoanTrafficVO loanTraffic, RedirectAttributes redirectAttributes) {
//		loanTrafficService.delete(loanTraffic);
//		addMessage(redirectAttributes, "删除导流平台产品信息成功");
//		return "redirect:"+Global.getAdminPath()+"/loan/loanTraffic/?repage";
//	}

}