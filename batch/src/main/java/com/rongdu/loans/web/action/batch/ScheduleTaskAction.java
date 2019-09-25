/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-14 16:08:07
 *
 *******************************************************************************/
package com.rongdu.loans.web.action.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.CronExpression;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.IDGeneratorUtil;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.batch.ScheduleTask;
import com.rongdu.loans.service.batch.ScheduleTaskService;
import com.rongdu.loans.utils.DateUtil;
import com.rongdu.loans.web.action.CrudActionSupport;
/**
 * 定时任务Action类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/batch")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "schedule-task.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/batch/ScheduleTask/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/batch/ScheduleTask/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/batch/ScheduleTask/list.jsp")
})
public class ScheduleTaskAction extends CrudActionSupport<ScheduleTask>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private String id;
	private String[] ids;
	private ScheduleTask entity;		
	private Page<ScheduleTask> page = new Page<ScheduleTask>(Constants.PAGE_SIZE);
	private ScheduleTaskService scheduleTaskService;

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
	public ScheduleTask getModel() {
		return entity;
	}
	
	public Page<ScheduleTask> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
		}else {
			entity = new ScheduleTask();
		}
	}

	//-- CRUD Action 函数 --//
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("createTime");
			page.setOrder(Page.DESC);
		}
		List<ScheduleTask> triggerList = scheduleTaskService.getScheduleTask();
		Map<String , String> triggerStatusMap = new HashMap<String, String>();
		for (ScheduleTask task:triggerList) {
			triggerStatusMap.put(task.getTaskName(), task.getStatus());
		}
		ServletActionContext.getContext().put("triggerStatusMap", triggerStatusMap);
		page = scheduleTaskService.findPage(page, filters);
		return SUCCESS;
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (StringUtils.isBlank(id)) {
			entity.setId(IDGeneratorUtil.uuid());
			scheduleTaskService.save(entity);
		}else {
			scheduleTaskService.update(entity);
		}
		addActionMessage("保存定时任务成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		scheduleTaskService.delete(id);
		addActionMessage("删除定时任务成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = scheduleTaskService.batchDelete(ids);
			addActionMessage("已经删除"+num+"条定时任务信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (scheduleTaskService.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	
	
	public String deleteTask() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
			scheduleTaskService.deleteTask(entity);		
			addActionMessage("已经移除任务【"+entity.getDescription()+"】");
		}
		return RELOAD;
	}
	
	public String pauseTask() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
			scheduleTaskService.pauseTask(entity);		
			addActionMessage("已经暂停任务【"+entity.getDescription()+"】");
		}
		return RELOAD;
	}
	
	public String resumeTask() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
			scheduleTaskService.resumeTask(entity);		
			addActionMessage("已经恢复任务【"+entity.getDescription()+"】");
		}
		return RELOAD;
	}
	
	
	public String runTask() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
			scheduleTaskService.runTask(entity);		
			addActionMessage("已经执行任务【"+entity.getDescription()+"】");
		}
		return RELOAD;
	}
	
	public String addTask() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = scheduleTaskService.get(id);
			scheduleTaskService.addTask(entity);		
			addActionMessage("已将任务【"+entity.getDescription()+"】加入计划");
		}
		return RELOAD;
	}
	
	public String validateCronExpression() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cronExpression = request.getParameter("cronExpression");
		boolean cronExpressionFlag = CronExpression.isValidExpression(cronExpression);
		if (cronExpressionFlag) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
	
	public String calcRunTime() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cronExpression = request.getParameter("cronExpression");
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        cronTriggerImpl.setCronExpression(cronExpression); 
        List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 5);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < dates.size(); i++) {
        	list.add(DateUtil.format(dates.get(i), "yyyy-MM-dd HH:mm:ss"));
        }
		Struts2Utils.renderJson(list);
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
	
	

	@Autowired
	public void setScheduleTaskService(ScheduleTaskService scheduleTaskService) {
		this.scheduleTaskService = scheduleTaskService;
	}
}
