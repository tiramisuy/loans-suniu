/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.loan.entity.ComplainRecord;
import com.rongdu.loans.loan.service.ComplainRecordService;
import com.rongdu.loans.loan.vo.ComplainRecordExportVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.SystemService;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 投诉工单Controller
 * @author system
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/complainRecord")
public class ComplainRecordController extends BaseController {

	@Autowired
	private ComplainRecordService complainRecordService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ShortMsgService shortMsgService;
	
	@ModelAttribute
	public ComplainRecord get(@RequestParam(required=false) String id) {
		ComplainRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = complainRecordService.get(id);
		}
		if (entity == null){
			entity = new ComplainRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("loan:complainRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ComplainRecord complainRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//有complain_handle_user角色用户 只能看自己的单据
	/*	if(UserUtils.haveRole("complain_handle_user")){
			complainRecord.setHandleUserId(UserUtils.getUser().getId());
			if (StringUtils.isBlank(complainRecord.getCreateBy())) {
				complainRecord.setCreateBy(UserUtils.getUser().getName());
			}
		}*/
		String lognName = UserUtils.getUser().getName();
		//如果登陆人是提交人（创建人） ，不显示跟进处理和时间
			model.addAttribute("lognUName",lognName);
		
		Page<ComplainRecord> page = complainRecordService.findPage(new Page<ComplainRecord>(request, response), complainRecord); 
		model.addAttribute("page", page);
		
		model.addAttribute("handleUserList", systemService.getUserByRoleAndDept("complain_handle_user","2002","2006","2007","2009","2011","2012"));
				
		return "modules/loan/complainRecordList";
	}

	@RequiresPermissions("loan:complainRecord:view")
	@RequestMapping(value = "view")
	public String view(ComplainRecord complainRecord, Model model) {
		model.addAttribute("complainRecord", complainRecord);
		String loginId = UserUtils.getUser().getId();
		//如果跟进人id 等于当前登录用户id才回修改已读，未读状态
		if(loginId.equals(complainRecord.getHandleUserId())){	
			complainRecord.setMessage(2);
		}
		complainRecord.setUpdateBy(UserUtils.getUser().getName());
		complainRecordService.updateByIdSelective(complainRecord);
		return "modules/loan/complainRecordView";
	}

	@RequiresPermissions("loan:complainRecord:view")
	@RequestMapping(value = "form")
	public String form(ComplainRecord complainRecord, Model model) {
		model.addAttribute("complainRecord", complainRecord);
		//model.addAttribute("handleUserList", systemService.findUserByOfficeCode("2007"));
		String lognName = UserUtils.getUser().getName();
		if(lognName.equals(complainRecord.getCreateBy())){	//如果登陆人是提交人（创建人） ，不显示跟进处理和时间
			model.addAttribute("isCreate", "y");
		}
		if(lognName.equals(complainRecord.getHandleUserName())){	//如果登陆人是提交人（创建人） ，不显示跟进处理和时间
			model.addAttribute("isHandle", "y");
		}
		
		return "modules/loan/complainRecordForm";
	}
	
	@RequestMapping(value = "getUserByDept")
	@ResponseBody
	public List getUserByDept(ComplainRecord complainRecord, Model model) {		
		return systemService.getUserByRoleAndDept("complain_handle_user",complainRecord.getType());
	}

	@RequiresPermissions("loan:complainRecord:edit")
	@RequestMapping(value = "save")
	public String save(ComplainRecord complainRecord, Model model, RedirectAttributes redirectAttributes) {
			if(complainRecord.getIsHhandleUser()==1){
				complainRecord.setLastCreaterTime(new Date());
			}else if(complainRecord.getIsHhandleUser()==2){
				complainRecord.setLastHanderTime(new Date());
			}
			else if(complainRecord.getIsHhandleUser()==3){
				complainRecord.setLastCreaterTime(new Date());
				complainRecord.setLastHanderTime(new Date());
			}else{
				complainRecord.setLastCreaterTime(new Date());
			}
		
		if (!beanValidator(model, complainRecord)){
			return form(complainRecord, model);
		}
		
		if(StringUtils.isNotBlank(complainRecord.getHandleUserId())){
			complainRecord.setHandleUserName(UserUtils.get(complainRecord.getHandleUserId()).getName());
		}
		Boolean flag = false;
		if (StringUtils.isBlank(complainRecord.getId())) {
			complainRecord.setMessage(1);
			flag = true;
		} else {
			String loginId = UserUtils.getUser().getId();
			//如果跟进人id 等于当前登录用户id才回修改已读，未读状态
			if(loginId.equals(complainRecord.getHandleUserId())){	
				complainRecord.setMessage(2);
			}else{
				complainRecord.setMessage(1);
			}
		}
		complainRecord.setUpdateTime(new Date());
		complainRecord.setUpdateBy(UserUtils.getUser().getName());
		complainRecord.setCreateBy(UserUtils.getUser().getName());
		
		complainRecordService.save(complainRecord);
		User user = UserUtils.get(complainRecord.getHandleUserId());
		if (flag && user != null && StringUtils.isNotBlank(user.getLoginName())) {
			shortMsgService.sendMsgComplainRecord(user.getLoginName(), ShortMsgTemplate.COMPLAIN_ALERT);
	        logger.info("发送工单提醒信息--->{}--->{}", user.getId(), user.getName());
		}
		addMessage(redirectAttributes, "保存投诉工单成功");
		return "redirect:"+Global.getAdminPath()+"/loan/complainRecord/?repage";
	}
	
	@RequiresPermissions("loan:complainRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ComplainRecord complainRecord, RedirectAttributes redirectAttributes) {
		complainRecord.setUpdateBy(UserUtils.getUser().getName());
		complainRecordService.delete(complainRecord);
		addMessage(redirectAttributes, "删除投诉工单成功");
		return "redirect:"+Global.getAdminPath()+"/loan/complainRecord/?repage";
	}

	@RequestMapping(value = "exportComplainRecord")
	@ExportLimit
	public void exportComplainRecord(ComplainRecord complainRecord, Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		logger.info("导出投诉工单数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("投诉工单数据",ComplainRecordExportVO.class);
			String fileName = "投诉工单数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<ComplainRecord> result = complainRecordService.findList(complainRecord);
			for (ComplainRecord entity : result) {
				if (1 == entity.getStatus()) {
					entity.setStatusStr("未处理");
				} else if (2 == entity.getStatus()) {
					entity.setStatusStr("待跟进");
				} else {
					entity.setStatusStr("已处理");
				}
				if (1 == entity.getEmergency()) {
					entity.setEmergencyStr("一般");
				} else {
					entity.setEmergencyStr("紧急");
				}
				if ("2002".equals(entity.getType())) {
					entity.setType("风控");
				} else if ("2006".equals(entity.getType())) {
					entity.setType("催收");
				} else if ("2007".equals(entity.getType())) {
					entity.setType("客服");
				} else if ("2009".equals(entity.getType())) {
					entity.setType("质检");
				} else if ("2011".equals(entity.getType())) {
					entity.setType("预提醒");
				} else if ("2012".equals(entity.getType())) {
					entity.setType("电商");
				}
			}
			excel.setDataList(result).write(response, fileName);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出投诉工单数据失败！失败信息：" + e.getMessage());
		} finally {
			if (excel != null)
				excel.dispose();			
		}
	}
}