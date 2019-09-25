/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2017-7-13 3:15:47
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
import com.rongdu.loans.entity.account.Dept;
import com.rongdu.loans.service.account.DeptManager;
import com.rongdu.loans.web.action.CrudActionSupport;
import com.opensymphony.xwork2.ActionContext;
/**
 * DeptAction类,负责请求的处理及转发.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Namespace("/acl")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "dept.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/dept/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/dept/view.jsp"),
	@Result(name = "TREE", location = "/WEB-INF/pages/sys/acl/dept/tree.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/dept/list.jsp")
})
public class DeptAction extends CrudActionSupport<Dept>{

	private static final long serialVersionUID = -1L;
	
	//-- 页面属性 --//
	private Long id;
	private Long[] ids;
	private Dept entity;		
	private Page<Dept> page = new Page<Dept>(Constants.PAGE_SIZE);
	private DeptManager sysDeptManager;

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
	public Dept getModel() {
		return entity;
	}
	
	public Page<Dept> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = sysDeptManager.get(id);
		}else {
			entity = new Dept();
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
		page = sysDeptManager.findPage(page, filters);
		return SUCCESS;
	}
	
	public String tree() throws Exception {
		List<Dept> list = sysDeptManager.getAll("id", true);
		ActionContext.getContext().put("list", list);
		return "TREE";
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String save() throws Exception {
		if (id==null) {		
			sysDeptManager.save(entity);
		}else {
			sysDeptManager.update(entity);
		}
		addActionMessage("保存Dept成功");
		return RELOAD;
	}

	public String delete() throws Exception {
		sysDeptManager.delete(id);
		addActionMessage("删除Dept成功");
		return RELOAD;
	}

	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = sysDeptManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"条Dept信息");			
		}
		return RELOAD;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (sysDeptManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

	@Autowired
	public void setDeptManager(DeptManager sysDeptManager) {
		this.sysDeptManager = sysDeptManager;
	}
}
