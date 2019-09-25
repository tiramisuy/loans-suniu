/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-10-7 20:53:39
 *
 *******************************************************************************/
package com.rongdu.loans.web.action.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.ServletUtils;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.log.AccessLog;
import com.rongdu.loans.entity.log.LoginLog;
import com.rongdu.loans.entity.log.VLoginLog;
import com.rongdu.loans.service.log.AccessLogManager;
import com.rongdu.loans.service.log.LoginLogManager;
import com.rongdu.loans.utils.ExcelExporter;
import com.rongdu.loans.web.action.CrudActionSupport;
import com.opensymphony.xwork2.ActionContext;
/**
 * 登录日志Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/log")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "login-log.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/log/LoginLog/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/log/LoginLog/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/log/LoginLog/list.jsp")
})
public class LoginLogAction extends CrudActionSupport<LoginLog>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private Long id;
	private Long[] ids;
	private LoginLog entity;		
	private Page<VLoginLog> page = new Page<VLoginLog>(Constants.PAGE_SIZE);
	@Autowired
	private LoginLogManager loginLogManager;
	@Autowired
	private AccessLogManager accessLogManager;

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	//-- ModelDriven 与 Preparable函数 --//
	public LoginLog getModel() {
		return entity;
	}
	
	public Page<VLoginLog> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = loginLogManager.get(id);
		}else {
			entity = new LoginLog();
		}
	}

	//-- CRUD Action 函数 --//
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("loginTime");
			page.setOrder(Page.DESC);
		}
		page = loginLogManager.findVLoginLogByPage(page, filters);
		return SUCCESS;
	}
	
	public String exportExcel() throws Exception {
		String username = Struts2Utils.getParameter("filter_LIKES_username");
		String status = Struts2Utils.getParameter("filter_EQS_status");	
		String empName = Struts2Utils.getParameter("filter_LIKES_empName");	
		String loginTime = Struts2Utils.getParameter("filter_LIKES_loginTime");

		StringBuilder builder = new StringBuilder("from VLoginLog where 1=1");
		if (StringUtils.isNotBlank(username)) {
			builder.append(" and username like '%").append(username).append("%'");
			ActionContext.getContext().put("filter_LIKES_username", username);
		}
		if (StringUtils.isNotBlank(status)) {
			builder.append(" and status like '%").append(status).append("%'");
			ActionContext.getContext().put("filter_EQS_status", status);
		}
		if (StringUtils.isNotBlank(empName)) {
			builder.append(" and emp_name like '%").append(empName).append("%'");
			ActionContext.getContext().put("filter_LIKES_empName", empName);
		}
		if (StringUtils.isNotBlank(loginTime)) {
			builder.append(" and login_time like '%").append(loginTime).append("%'");
			ActionContext.getContext().put("filter_LIKES_loginTime", loginTime);
		}
		builder.append(" order by id desc");
		
		List<VLoginLog> data = loginLogManager.findVLoginLogOrderby(builder.toString());	
		//生成Excel文件.
		Workbook wb = new ExcelExporter().export("后台登录日志", data);		
		//输出Excel文件.
		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType(ServletUtils.EXCEL_TYPE);
		ServletUtils.setFileDownloadHeader(response, "后台登录日志.xls");		
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		return null;
	}
	

	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (id==null) {		
			loginLogManager.save(entity);
		}else {
			loginLogManager.update(entity);
		}
		addActionMessage("保存登录日志成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		loginLogManager.delete(entity);
		addActionMessage("删除登录日志成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String viewLog() throws Exception {
		VLoginLog entity = loginLogManager.getVLoginLog(id);
		List<AccessLog> list = accessLogManager.findBy("loginLogId", id);
		ActionContext.getContext().put("entity", entity);
		ActionContext.getContext().put("list", list);
		return VIEW;
	}
	
	
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = loginLogManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"条登录日志信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (loginLogManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

}
