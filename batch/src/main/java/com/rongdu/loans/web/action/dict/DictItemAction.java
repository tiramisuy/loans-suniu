package com.rongdu.loans.web.action.dict;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.entity.dict.Dict;
import com.rongdu.loans.entity.dict.DictItem;
import com.rongdu.loans.service.dict.DictItemManager;
import com.rongdu.loans.web.action.CrudActionSupport;

/**
 * 字典项管理Action.
 */
@Namespace("/dict")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "dict-item.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/dict/dictitem/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/dict/dictitem/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/dict/dictitem/list.jsp")
})
public class DictItemAction extends CrudActionSupport<DictItem> {

	private static final long serialVersionUID = -4052047494894591406L;

	private DictItemManager dictItemManager;

	//-- 页面属性 --//
	private Long id;
	private DictItem entity;
	private Page<DictItem> page = new Page<DictItem>(8);//每页5条记录

	//-- ModelDriven 与 Preparable函数 --//
	@Override
	public DictItem getModel() {
		return entity;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Page<DictItem> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id!=null) {
			entity = dictItemManager.get(id);
		} else {
			entity = new DictItem();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		String dictCode = Struts2Utils.getParameter("dictCode");
		HttpSession session = Struts2Utils.getSession();
		dictCode = dictCode==null?(String)session.getAttribute("dictCode"):dictCode;
		session.setAttribute("dictCode", dictCode);			
		String code = Struts2Utils.getParameter("code");
		String name = Struts2Utils.getParameter("name");
		StringBuilder builder = new StringBuilder("from DictItem where 1=1");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dictCode)) {
			builder.append(" and dict.code=:dictCode");
			map.put("dictCode", dictCode);				
		}
		if (StringUtils.isNotBlank(code)) {
			builder.append(" and code=:code");
			map.put("code", code);
		}
		if (StringUtils.isNotBlank(name)) {
			builder.append(" and name=:name");
			map.put("name", name);
		}
		page = dictItemManager.findPage(page, builder.toString(), map);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		String dictCode = Struts2Utils.getParameter("dictCode");
		entity.setDict(new Dict(dictCode));
		if (id!=null) {
			dictItemManager.update(entity);
		}else {
			dictItemManager.save(entity);
		}
		addActionMessage("保存字典成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		dictItemManager.delete(entity);
		addActionMessage("删除字典成功");
		return RELOAD;
	}

	@Override
	public String view() throws Exception {
		return VIEW;
	}
	
	/**
	 * 支持使用Jquery.validate Ajax检验字典代码是否重复.
	 */
	public String checkDictItemCode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String code = request.getParameter("code");
		String dictCode = (String)Struts2Utils.getRequest().getSession().getAttribute("dictCode");
		if (dictItemManager.isPropertyValueExist("code", code,"dict.code",dictCode)) {
			//验证未通过
			Struts2Utils.renderText("false");
		} else {
			//验证通过
			Struts2Utils.renderText("true");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}

	@Autowired
	public void setDictItemManager(DictItemManager dictItemManager) {
		this.dictItemManager = dictItemManager;
	}
}