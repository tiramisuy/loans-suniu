/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:07:48
 *
 *******************************************************************************/
package com.rongdu.loans.web.action.batch;

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
import com.rongdu.loans.entity.batch.ScheduleTaskLog;
import com.rongdu.loans.service.batch.ScheduleTaskLogManager;
import com.rongdu.loans.web.action.CrudActionSupport;
/**
 * 定时任务执行日志Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/batch")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "schedule-task-log.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/batch/ScheduleTaskLog/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/batch/ScheduleTaskLog/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/batch/ScheduleTaskLog/list.jsp")
})
public class ScheduleTaskLogAction extends CrudActionSupport<ScheduleTaskLog>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private String id;
	private String[] ids;
	private ScheduleTaskLog entity;		
	private Page<ScheduleTaskLog> page = new Page<ScheduleTaskLog>(Constants.PAGE_SIZE);
	private ScheduleTaskLogManager scheduleTaskLogManager;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	//-- ModelDriven 与 Preparable函数 --//
	public ScheduleTaskLog getModel() {
		return entity;
	}
	
	public Page<ScheduleTaskLog> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = scheduleTaskLogManager.get(id);
		}else {
			entity = new ScheduleTaskLog();
		}
	}

	//-- CRUD Action 函数 --//
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("startTime");
			page.setOrder(Page.DESC);
		}
		page = scheduleTaskLogManager.findPage(page, filters);
		return SUCCESS;
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (id==null) {		
			scheduleTaskLogManager.save(entity);
		}else {
			scheduleTaskLogManager.update(entity);
		}
		addActionMessage("保存定时任务执行日志成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		scheduleTaskLogManager.delete(id);
		addActionMessage("删除定时任务执行日志成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = scheduleTaskLogManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"条定时任务执行日志信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (scheduleTaskLogManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

	@Autowired
	public void setScheduleTaskLogManager(ScheduleTaskLogManager scheduleTaskLogManager) {
		this.scheduleTaskLogManager = scheduleTaskLogManager;
	}
}
