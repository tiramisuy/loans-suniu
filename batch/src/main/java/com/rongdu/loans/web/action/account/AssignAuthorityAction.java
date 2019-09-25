package com.rongdu.loans.web.action.account;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Multimap;
import com.rongdu.loans.entity.account.Authority;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.service.account.AuthorityManager;
import com.rongdu.loans.service.account.RoleManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 角色管理Action.
 * 
 */
@Namespace("/acl")
@Results( {
	@Result(name = "main", location = "/WEB-INF/pages/sys/acl/role/main.jsp"),
	@Result(name = "role-list", location = "/WEB-INF/pages/sys/acl/role/role-list.jsp"),
	@Result(name = "assign-authority", location = "/WEB-INF/pages/sys/acl/role/assign-authority.jsp")
})
public class AssignAuthorityAction extends ActionSupport {

	private static final long serialVersionUID = -4052047494894591406L;
	
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private AuthorityManager authorityManager;
	
	@Autowired
	private CacheManager ehcacheManager;

	private Long roleId;
	private Long[] id;
	
	@Override
	public String execute() {
		return "main";
	}
	
	public String roleList() {
		List<Role> list = roleManager.getAll("id", true);
		ActionContext.getContext().put("list", list);
		return "role-list";
	}
	
	public String assignAuthority() {
		Multimap<String, Authority> menu = authorityManager.getAllMenu();
		Multimap<String, Authority> opt = authorityManager.getAllOpt();
		Role role  = roleManager.get(roleId);	
		ActionContext.getContext().put("menu", menu);
		ActionContext.getContext().put("opt", opt);
		ActionContext.getContext().put("ids", role.getAuthIds());
		return "assign-authority";
	}
	
	public String save() {
 		Role role  = roleManager.get(roleId);
		List<Authority> authorityList = new ArrayList<Authority>();
		if (id!=null) {
			for (Long i:id) {
				authorityList.add(new Authority(i));
			}
		}
		role.setAuthorityList(authorityList);
		roleManager.updateRole(role);
		addActionMessage("已经给【"+role.getName()+"】角色授权");
		
 		//清除缓存
		clearCache("OperationAuthority");
		clearCache("MenuAuthority");
 		
		return assignAuthority();
	}

	public Long getRoleId() {
		return roleId;
	}

	public Long[] getId() {
		return id;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setId(Long[] id) {
		this.id = id;
	}

	private void clearCache(String cacheName){
		Cache cache = ehcacheManager.getCache(cacheName);
		if(cache!=null){
			cache.removeAll();
		}
	}
	
}