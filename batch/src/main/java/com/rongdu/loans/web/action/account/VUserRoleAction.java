package com.rongdu.loans.web.action.account;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.entity.account.VUserRole;
import com.rongdu.loans.service.account.VUserRoleManager;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/acl")
@Results( {
	@Result(name = "reload", location = "v-user-role.action", type = "redirect"),
	@Result(name = "success", location = "/WEB-INF/pages/sys/acl/user/user-role-list.jsp")
})
public class VUserRoleAction extends ActionSupport{

	private static final long serialVersionUID = -4052047494894591405L;
	
	@Autowired
	private VUserRoleManager vUserRoleManager; 

	private Long userId;  
	private Long[] ids;  
	private Page<VUserRole> page = new Page<VUserRole>(8);
	
	@Override
	public String execute() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		if (!page.isOrderBySetted()) {
			page.setOrderBy("userId");
			page.setOrder(Page.ASC);
		}
		page = vUserRoleManager.findPage(page, filters);
		return SUCCESS;
	}

	public String delete() throws Exception {
		Long roleId = Long.parseLong(Struts2Utils.getParameter("filter_EQL_roleId"));
		VUserRole vUserRole = vUserRoleManager.findUniqueBy(roleId,userId);
		addActionMessage(new StringBuilder("已将用户").
				append(vUserRole.getUsername()).append("的【").append(vUserRole.getRoleName()).append("】角色移除").toString());
		vUserRoleManager.delete(vUserRole);
		return  execute();
	}
	
	public String batchDelete() throws Exception {
		Long roleId = Long.parseLong(Struts2Utils.getParameter("filter_EQL_roleId"));
		  vUserRoleManager.batchDelete(roleId,ids);
		addActionMessage("批量删除成功");
		return  execute();
	}

	public Long getUserId() {
		return userId;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Page<VUserRole> getPage() {
		return page;
	}
	
}