/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.rongdu.test.entity.SysEmp;
import com.rongdu.test.service.SysEmpService;
/**
 * 员工信息Controller
 * @author sunda
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/test/sysEmp")
public class SysEmpController extends BaseController {
	@Autowired
	private SysEmpService sysEmpService;
	
	/**
 	* 查看员工信息
 	* @author sunda
 	*/
	@ModelAttribute
	public SysEmp get(@RequestParam(required=false) String id) {
		SysEmp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysEmpService.get(id);
		}
		if (entity == null){
			entity = new SysEmp();
		}
		return entity;
	}
	
	/**
 	* 查询员工信息
 	* @author sunda
 	*/
	@RequiresPermissions("test:sysEmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysEmp sysEmp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysEmp> page = sysEmpService.findPage(new Page<SysEmp>(request, response), sysEmp); 
		model.addAttribute("page", page);
		return "modules/test/sysEmpList";
	}
	
	/**
 	* 新增/修改员工信息
 	* @author sunda
 	*/
	@RequiresPermissions("test:sysEmp:view")
	@RequestMapping(value = "form")
	public String form(SysEmp sysEmp, Model model) {
		model.addAttribute("sysEmp", sysEmp);
		return "modules/test/sysEmpForm";
	}
	
	/**
 	* 保存员工信息
 	* @author sunda
 	*/
	@RequiresPermissions("test:sysEmp:edit")
	@RequestMapping(value = "save")
	public String save(SysEmp sysEmp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysEmp)){
			return form(sysEmp, model);
		}
		sysEmpService.save(sysEmp);
		addMessage(redirectAttributes, "保存员工信息成功");
		return "redirect:"+ Global.getAdminPath()+"/test/sysEmp/?repage";
	}
	
	/**
 	* 删除员工信息
 	* @author sunda
 	*/
	@RequiresPermissions("test:sysEmp:edit")
	@RequestMapping(value = "delete")
	public String delete(SysEmp sysEmp, RedirectAttributes redirectAttributes) {
		sysEmpService.delete(sysEmp);
		addMessage(redirectAttributes, "删除员工信息成功");
		return "redirect:"+Global.getAdminPath()+"/test/sysEmp/?repage";
	}
	
}