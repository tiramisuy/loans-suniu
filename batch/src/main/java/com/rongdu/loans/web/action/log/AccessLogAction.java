/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 13:21:12
 *
 *******************************************************************************/
package com.rongdu.loans.web.action.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.log.AccessLog;
import com.rongdu.loans.service.log.AccessLogManager;
import com.rongdu.loans.web.action.CrudActionSupport;
/**
 * 访问日志Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/log")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "access-log.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/log/AccessLog/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/log/AccessLog/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/log/AccessLog/list.jsp")
})
public class AccessLogAction extends CrudActionSupport<AccessLog>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private Long id;
	private Long[] ids;
	private AccessLog entity;		
	private Page<AccessLog> page = new Page<AccessLog>(Constants.PAGE_SIZE);
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
	public AccessLog getModel() {
		return entity;
	}
	
	public Page<AccessLog> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accessLogManager.get(id);
		}else {
			entity = new AccessLog();
		}
	}

	//-- CRUD Action 函数 --//
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		page = accessLogManager.findPage(page, filters);
		return SUCCESS;
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (id==null) {		
			accessLogManager.save(entity);
		}else {
			accessLogManager.update(entity);
		}
		addActionMessage("保存访问日志成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		accessLogManager.delete(id);
		addActionMessage("删除访问日志成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = accessLogManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"条访问日志信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (accessLogManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

	@Autowired
	public void setAccessLogManager(AccessLogManager accessLogManager) {
		this.accessLogManager = accessLogManager;
	}
}
