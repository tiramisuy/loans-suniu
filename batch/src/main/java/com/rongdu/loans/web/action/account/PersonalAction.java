package com.rongdu.loans.web.action.account;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.entity.account.User;
import com.rongdu.loans.service.account.UserManager;
import com.rongdu.loans.utils.SecurityUtil;
import com.rongdu.loans.web.action.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 演示带分页的管理界面.
 */
@Namespace("/per")
@Results( {
	@Result(name = "changePswd", location = "/WEB-INF/pages/sys/acl/user/change-pswd.jsp")
})
public class PersonalAction extends CrudActionSupport<User> {

	private static final long serialVersionUID = 8683878162525847072L;
	
	@Autowired
	private UserManager userManager;

	//-- 页面属性 --//
	private Long id;
	private User entity;
	
	//-- ModelDriven 与 Preparable函数 --//
	public void setId(Long id) {
		this.id = id;
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

	public String savePswd() throws Exception {
		entity = userManager.get(id);
		entity.setPassword(ServletActionContext.getRequest().getParameter("password"));
		userManager.changePassword(entity);
		addActionMessage("密码已修改");
		return "changePswd";
	}
	
	public String changePswd() throws Exception {
		return "changePswd";
	}


	public String checkPswd() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String origPwd = request.getParameter("origPwd");
		User user = userManager.get(id);	
		String hashPass = SecurityUtil.shaPassword(origPwd, user.getSalt());
		if (user.getPassword().equals(hashPass)){
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}	

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String view() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
