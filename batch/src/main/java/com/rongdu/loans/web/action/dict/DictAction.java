package com.rongdu.loans.web.action.dict;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.dict.Dict;
import com.rongdu.loans.service.dict.DictManager;
import com.rongdu.loans.service.log.LogService;
import com.rongdu.loans.web.action.CrudActionSupport;

/**
 * 字典管理Action.
 */
@Namespace("/dict")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "dict.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/dict/dict/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/dict/dict/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/dict/dict/list.jsp")
})
@LogService(obj="数据字典")
public class DictAction extends CrudActionSupport<Dict> {

	private static final long serialVersionUID = -4052047494894591406L;

	private DictManager dictManager;

	//-- 页面属性 --//
	private String id;
	private Dict entity;
	private Page<Dict> page = new Page<Dict>(Constants.PAGE_SIZE);//每页5条记录

	//-- ModelDriven 与 Preparable函数 --//
	@Override
	public Dict getModel() {
		return entity;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Page<Dict> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			entity = dictManager.get(id);
		} else {
			entity = new Dict();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = dictManager.findPage(page, filters);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		if (StringUtils.isNotBlank(id)) {
			dictManager.update(entity);
		}else {
			dictManager.save(entity);
		}
		addActionMessage("保存字典成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		dictManager.delete(entity);
		addActionMessage("删除字典成功");
		return RELOAD;
	}

	@Override
	public String view() throws Exception {
		return VIEW;
	}

	@Autowired
	public void setDictManager(DictManager dictManager) {
		this.dictManager = dictManager;
	}
	
	/**
	 * 支持使用Jquery.validate Ajax检验字典代码是否重复.
	 */
	public String checkDictCode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String code = request.getParameter("code");
		if (dictManager.isPropertyValueExist("code", code)) {
			//验证未通过
			Struts2Utils.renderText("false");
		} else {
			//验证通过
			Struts2Utils.renderText("true");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
}