package com.rongdu.loans.web.action.account;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.entity.account.VRole;
import com.rongdu.loans.service.account.RoleManager;
import com.rongdu.loans.service.account.VRoleManager;
import com.rongdu.loans.web.action.CrudActionSupport;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
@Namespace("/acl")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "role.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/role/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/role/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/role/list.jsp")
})
public class RoleAction extends CrudActionSupport<Role> {

	private static final long serialVersionUID = -4052047494894591406L;

	private RoleManager roleManager;
	
	@Autowired
	private VRoleManager vRoleManager;

	//-- 页面属性 --//
	private Long id;
	private Role entity;
	private Page<VRole> page = new Page<VRole>(Constants.PAGE_SIZE);//每页5条记录
	private List<Role> allRoleList;//角色列表
	private List<Long> checkedAuthIds;//页面中钩选的权限id列表

	//-- ModelDriven 与 Preparable函数 --//
	@Override
	public Role getModel() {
		return entity;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Page<VRole> getPage() {
		return page;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = roleManager.get(id);
		} else {
			entity = new Role();
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
		page = vRoleManager.findPage(page, filters);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合Role的Authorities Set.
//		HibernateUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthIds, Authority.class);
		//保存用户并放入成功信息.
		if (id != null) {
			roleManager.update(entity);
			addActionMessage("修改角色【"+entity.getName()+"】成功");		
		}else {
			roleManager.save(entity);
			addActionMessage("新增角色【"+entity.getName()+"】成功");			
		}
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		roleManager.delete(entity);
		addActionMessage("删除角色【"+entity.getName()+"】成功");
		return RELOAD;
	}

	//-- 页面属性访问函数 --//
	/**
	 * list页面显示所有角色列表.
	 */
	public List<Role> getAllRoleList() {
		return allRoleList;
	}

	/**
	 * input页面显示所有授权列表.
	 */
//	public List<Authority> getAllAuthorityList() {
//		return roleManager.getAllAuthority();
//	}

	/**
	 * input页面显示角色拥有的授权.
	 */
	public List<Long> getCheckedAuthIds() {
		return checkedAuthIds;
	}

	/**
	 * input页面提交角色拥有的授权.
	 */
	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
		this.checkedAuthIds = checkedAuthIds;
	}
	
	@Autowired
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	@Override
	public String view() throws Exception {
		return VIEW;
	}
}