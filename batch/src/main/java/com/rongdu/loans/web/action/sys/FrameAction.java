package com.rongdu.loans.web.action.sys;

 
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Multimap;
import com.rongdu.loans.entity.account.VRoleAuthority;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.service.account.VRoleAuthorityManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/")
@Results( {
	@Result(name = "top", location = "/page/top.jsp"),
	@Result(name = "left", location = "/page/left.jsp")
})
public class FrameAction extends ActionSupport {

	private static final long serialVersionUID = 4989311084866378322L;
	
	private String id;
	
	@Autowired
	private VRoleAuthorityManager vRoleAuthorityManager;
	
	public String top() throws Exception {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Multimap<String, VRoleAuthority> menus = vRoleAuthorityManager.loadMenuAuthorityByRole(user.getRoleIds());
		if (menus!=null) {
			ActionContext.getContext().put("menu", menus.get("1"));	
		}
		return "top";
	}
	
	public String left() throws Exception {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Multimap<String, VRoleAuthority> menus = vRoleAuthorityManager.loadMenuAuthorityByRole(user.getRoleIds());
		if (menus!=null) {
			//初始化二级菜单
			if (StringUtils.isBlank(id)) {
				Collection<VRoleAuthority> firstMenus = menus.get("1");
				VRoleAuthority first = firstMenus.iterator().next();
				if (first!=null) {
					id = String.valueOf(first.getAuthId());
				}			
			}
			ActionContext.getContext().put("menu", menus.get(id));		
		}
		return "left";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}