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
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.service.account.RoleManager;
import com.rongdu.loans.service.account.VUserManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 角色管理Action.
 */
@Namespace("/acl")
@Results( {
	@Result(name = "assign-role-list", location = "/WEB-INF/pages/sys/acl/user/assign-role-list.jsp"),
	@Result(name = "assign-role", location = "/WEB-INF/pages/sys/acl/user/assign-role.jsp")
})
public class AssignRoleAction extends ActionSupport {

	private static final long serialVersionUID = -4052047494894591406L;
	
	private Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private VUserManager vUserManager;
	
	private List<Long> checkedRoleIds;
	private Long id;
	
	@Override
	public String execute() {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		page = vUserManager.findPage(page, filters);
		return "assign-role-list";
	}
	
	public String assignRole() throws Exception {
		VUser user = vUserManager.get(id);
		checkedRoleIds = user.getRoleIds();
		List<Role> allRoleList = roleManager.getAll();
		ActionContext.getContext().put("allRoleList", allRoleList);
		ActionContext.getContext().put("user", user);
		return "assign-role";
	}
	
	public Page<VUser> getPage() {
		return page;
	}

	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	public Long getId() {
		return id;
	}

	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	public void setId(Long id) {
		this.id = id;
	}	

}