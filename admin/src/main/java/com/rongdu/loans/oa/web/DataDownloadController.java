/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.oa.web;

import java.util.Date;

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
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.enums.DataDownloadTypeEnum;
import com.rongdu.loans.oa.entity.DataDownload;
import com.rongdu.loans.oa.op.DataDownloadOP;
import com.rongdu.loans.oa.service.DataDownloadService;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 数据下载表Controller
 * @author zhuchangbing
 * @version 2018-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/dataDownload")
public class DataDownloadController extends BaseController {

	@Autowired
	private DataDownloadService dataDownloadService;
	
	@ModelAttribute
	public DataDownload get(@RequestParam(required=false) String id) {
		DataDownload entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dataDownloadService.get(id);
		}
		if (entity == null){
			entity = new DataDownload();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:dataDownload:view")
	@RequestMapping(value = {"list", ""})
	public String list(DataDownloadOP dataDownloadOP, Boolean first, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(first == null || !first) {
			DataDownload dataDownload = new DataDownload();
			dataDownload.setTitle(dataDownloadOP.getTitle());
			dataDownload.setStartTime(DateUtils.parseDate(dataDownloadOP.getStartTime()));
			dataDownload.setEndTime(DateUtils.parseDate(dataDownloadOP.getEndTime()));
			Page<DataDownload> page = dataDownloadService.findPage(new Page<DataDownload>(request, response), dataDownload);
			model.addAttribute("page", page);
		}
		model.addAttribute("dataDownloadOP", dataDownloadOP);
		model.addAttribute("typeList", DataDownloadTypeEnum.values());
		return "modules/oa/dataDownloadList";
	}

	@RequiresPermissions("oa:dataDownload:view")
	@RequestMapping(value = "view")
	public String view(DataDownload dataDownload, Model model) {
		model.addAttribute("dataDownload", dataDownload);
		model.addAttribute("typeList", DataDownloadTypeEnum.values());
		return "modules/oa/dataDownloadView";
	}

	@RequiresPermissions("oa:dataDownload:view")
	@RequestMapping(value = "form")
	public String form(DataDownload dataDownload, Model model) {
		model.addAttribute("dataDownload", dataDownload);
		return "modules/oa/dataDownloadForm";
	}

	@RequiresPermissions("oa:dataDownload:edit")
	@RequestMapping(value = "save")
	public String save(DataDownload dataDownload, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dataDownload)){
			return form(dataDownload, model);
		}
		dataDownloadService.save(dataDownload);
		addMessage(redirectAttributes, "保存数据下载表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/dataDownload/?repage";
	}
	
	@RequiresPermissions("oa:dataDownload:edit")
	@RequestMapping(value = "delete")
	public String delete(DataDownload dataDownload, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		dataDownload.setUpdateBy(user);
		dataDownload.setUpdateDate(new Date());
		dataDownloadService.delete(dataDownload);
		addMessage(redirectAttributes, "删除数据下载表成功");
		return "redirect:"+Global.getAdminPath()+"/oa/dataDownload/?repage";
	}

}