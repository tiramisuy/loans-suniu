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
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.service.account.UserManager;
import com.rongdu.loans.service.account.VUserManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
@Namespace("/acl")
@Results( {
	@Result(name = "black-list", location = "/WEB-INF/pages/sys/acl/user/black-list.jsp"),
	@Result(name = "white-list", location = "/WEB-INF/pages/sys/acl/user/white-list.jsp")
})
public class BlackWhiteAction extends ActionSupport {

	private static final long serialVersionUID = -4052047494894591406L;
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private VUserManager vUserManager;
	private Long id;
	private Long[] ids;
	private Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);
	
	public String blackList() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		filters.add(new PropertyFilter("EQS_status", "0")); 
		page = vUserManager.findPage(page, filters);
		return "black-list";
	}
	
	public String whiteList() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		filters.add(new PropertyFilter("EQS_status", "1")); 
		page = vUserManager.findPage(page, filters);
		return "white-list";
	}
	
	public String toBlackList() throws Exception {
		if(id!=null){
			userManager.disabledUser(id);
			addActionMessage("已经将该用户加入黑名单");			
		}
		return blackList();
	}
	
	public String toWhiteList() throws Exception {
		if(id!=null){
			userManager.enabledUser(id);
			addActionMessage("已经将该用户加入白名单");			
		}
		return whiteList();
	}
	
	public String batchToBlackList() throws Exception {
		if(ids!=null){
			int num = userManager.batchDisabledUser(ids);
			addActionMessage("已经将这"+num+"个用户加入黑名单");			
		}
		return blackList();
	}
	
	public String batchToWhiteList() throws Exception {
		if(ids!=null){
			int num = userManager.batchEnabledUser(ids);
			addActionMessage("已经将这"+num+"个用户加入白名单");			
		}
		return whiteList();
	}
	
	public Long getId() {
		return id;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	public Page<VUser> getPage() {
		return page;
	}

}