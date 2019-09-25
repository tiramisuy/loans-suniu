/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:15:10
 *
 *******************************************************************************/
package com.rongdu.loans.web.action.account;

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
import com.rongdu.loans.entity.account.Emp;
import com.rongdu.loans.service.account.EmpManager;
import com.rongdu.loans.web.action.CrudActionSupport;
/**
 * EmpAction类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/acl")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "emp.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/emp/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/emp/view.jsp"),
	@Result(name = "MAIN", location = "/WEB-INF/pages/sys/acl/dept/main.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/emp/list.jsp")
})
public class EmpAction extends CrudActionSupport<Emp>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private Long id;
	private Long[] ids;
	private Emp entity;		
	private Page<Emp> page = new Page<Emp>(Constants.PAGE_SIZE);
	private EmpManager sysEmpManager;

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
	public Emp getModel() {
		return entity;
	}
	
	public Page<Emp> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = sysEmpManager.get(id);
		}else {
			entity = new Emp();
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
		page = sysEmpManager.findPage(page, filters);
		return SUCCESS;
	}
	
	public String main() throws Exception {
		return "MAIN";
	}
	
	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (id==null) {		
			sysEmpManager.save(entity);
		}else {
			sysEmpManager.update(entity);
		}
		addActionMessage("保存Emp成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		sysEmpManager.delete(id);
		addActionMessage("删除Emp成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = sysEmpManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"条Emp信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (sysEmpManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

	@Autowired
	public void setEmpManager(EmpManager sysEmpManager) {
		this.sysEmpManager = sysEmpManager;
	}
}
