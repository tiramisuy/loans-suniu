package com.rongdu.loans.web.action.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.dao.HibernateUtils;
import com.rongdu.loans.entity.account.Role;
import com.rongdu.loans.entity.account.User;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.exception.ServiceException;
import com.rongdu.loans.service.account.RoleManager;
import com.rongdu.loans.service.account.UserManager;
import com.rongdu.loans.service.account.VUserManager;
import com.rongdu.loans.web.action.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 */
//定义URL映射对应/acl/user.action
@Namespace("/acl")
//定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "user.action", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/user/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/user/view.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/user/list.jsp")
})
public class UserAction extends CrudActionSupport<User> {

	private static final long serialVersionUID = 8683878162525847072L;
	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private VUserManager vUserManager;

	//-- 页面属性 --//
	private Long id;
	private Long[] ids;
	private User entity;
	private Page<VUser> page = new Page<VUser>(Constants.PAGE_SIZE);//每页5条记录
	private List<Long> checkedRoleIds; //页面中钩选的角色id列表
	
	//-- ModelDriven 与 Preparable函数 --//
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	/**
	 * 实现ModelDriven接口方法
	 * 
	 * 该模型可以接收用户输入的数据，
	 */
	@Override
	public User getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = userManager.get(id);
		} else {
			entity = new User();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.DESC);
		}
		page = vUserManager.findPage(page, filters);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		checkedRoleIds = entity.getRoleIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox选择 整合User的Roles Set
		HibernateUtils.mergeByCheckedIds(entity.getRoleList(), checkedRoleIds, Role.class);
		if (id==null) {
			userManager.save(entity);
			addActionMessage("新增用户【"+entity.getUsername()+"】成功");
		}else {
			userManager.update(entity);
			addActionMessage("修改用户【"+entity.getUsername()+"】成功");
		}
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			userManager.delete(entity);
			addActionMessage("删除用户【"+entity.getUsername()+"】成功");
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除用户【"+entity.getUsername()+"】失败");
		}
		return RELOAD;
	}
	
	public String changePassword() throws Exception {
		return "changePassword";
	}
	
	@Override
	public String view() throws Exception {
		return VIEW;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = userManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"个用户");			
		}
		return RELOAD;
	}

	//-- 其他Action函数 --//
	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUsername() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String newUsername = request.getParameter("username");
		String oldUsername = request.getParameter("oldUsername");
		if (userManager.isUnique("username", newUsername)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}

	//-- 页面属性访问函数 --//
	/**
	 * list页面显示用户分页列表.
	 */
	public Page<VUser> getPage() {
		return page;
	}

	/**
	 * input页面显示所有角色列表.
	 */
	public List<Role> getAllRoleList() {
		return roleManager.getAll("id", true);
	}

	/**
	 * input页面显示用户拥有的角色.
	 */
	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	/**
	 * input页面提交用户拥有的角色.
	 */
	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
