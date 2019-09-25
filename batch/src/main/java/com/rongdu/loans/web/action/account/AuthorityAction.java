package com.rongdu.loans.web.action.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.core.orm.Page;
import com.rongdu.core.utils.web.struts2.Struts2Utils;
import com.rongdu.loans.constant.AuthorityType;
import com.rongdu.loans.constant.Constants;
import com.rongdu.loans.entity.account.Authority;
import com.rongdu.loans.entity.account.VAuthority;
import com.rongdu.loans.service.account.AuthorityManager;
import com.rongdu.loans.web.action.CrudActionSupport;
import com.opensymphony.xwork2.ActionContext;

/**
 * 角色管理Action.
 * 
 * @author calvin
 */
@Namespace("/acl")
@Results( {
	@Result(name = CrudActionSupport.RELOAD, location = "authority.action?resId=${resId}", type = "redirect"),
	@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/pages/sys/acl/auth/form.jsp"),
	@Result(name = CrudActionSupport.VIEW, location = "/WEB-INF/pages/sys/acl/auth/view.jsp"),
	@Result(name = "tree", location = "/WEB-INF/pages/sys/acl/auth/tree.jsp"),
	@Result(name = CrudActionSupport.SUCCESS, location = "/WEB-INF/pages/sys/acl/auth/authority.jsp")
})
public class AuthorityAction extends CrudActionSupport<Authority> {

	private static final long serialVersionUID = -4052047494894591406L;
	
	@Autowired
	private AuthorityManager authorityManager;

	//-- 页面属性 --//
	private Long eid;
	private Long[] ids;
	private Authority entity;
	private Page<Authority> page = new Page<Authority>(Constants.PAGE_SIZE);
	
	//-- ModelDriven 与 Preparable函数 --//
	@Override
	public Authority getModel() {
		return entity;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}
	
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	public Page<Authority> getPage() {
		return page;
	}
	
	
	@Override
	protected void prepareModel() throws Exception {
		if (eid != null) {
			entity = authorityManager.get(eid);
		} else {
			entity = new Authority();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		return SUCCESS;
	}

	@Override
	public String save() throws Exception {
		if (eid==null) {		
			authorityManager.save(entity);
			addActionMessage("新增【"+entity.getCode()+"】成功");
			eid = entity.getPid();
		}else {
			authorityManager.update(entity);
			addActionMessage("修改【"+entity.getCode()+"】成功");
		}
		saveOperation();
		ActionContext.getContext().put("isReload", true);
		return viewAuthority();
	}

	private void saveOperation() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long pid = entity.getId();
		String pcode = entity.getCode()+":";
		String[] optIds = request.getParameterValues("optId");
		String[] optUrls = request.getParameterValues("optUrl");
		String[] optCodes = request.getParameterValues("optCode");
		String[] optNames = request.getParameterValues("optName");
		if (optIds!=null) {
			Authority authority= null;
			Long id = null;
			for (int i = 0; i < optCodes.length; i++) {
			  if (StringUtils.isNotBlank(optCodes[i])&&StringUtils.isNotBlank(optNames[i])) {
				 if (StringUtils.isNotBlank(optIds[i])) {
						id = Long.parseLong(optIds[i]);
						authority = authorityManager.get(id);
						authority.setCode(genOptCode(pcode,optCodes[i]));
						authority.setName(optNames[i]);
						authority.setUrl(optUrls[i]);
						authorityManager.update(authority);
					}else {
						id = null;
						authority = new Authority(id, pid, genOptCode(pcode,optCodes[i]), optNames[i], AuthorityType.OPT, Long.valueOf(i), optUrls[i]);
						authorityManager.save(authority);
					}	
				}
			}
		}
		
	}

	private String genOptCode(String pcode, String code) {
		if (!StringUtils.startsWith(code, pcode)) {
			code = pcode+code;
		}
		return code;
	}

	@Override
	public String delete() throws Exception {
		eid = entity.getPid();
		authorityManager.delete(entity);
		ActionContext.getContext().put("isReload", true);
		addActionMessage("删除【"+entity.getCode()+"】成功");
		return viewAuthority();
	}
	
	public String ajaxDelete() throws Exception {
		entity = authorityManager.get(eid);
		authorityManager.delete(entity);
		Struts2Utils.renderJson("{'code':'sucess'}");
		return null;
	}
	
	public String batchDelete() throws Exception {
		if(ids!=null){
			int num = authorityManager.batchDelete(ids);
			addActionMessage("已经删除"+num+"个权限");			
		}
		return RELOAD;
	}
	
	public String tree() throws Exception {
		List<Authority> list = authorityManager.loadAuthorityTree();
		ActionContext.getContext().put("list", list);
		return "tree";
	}
	
	public String viewAuthority() throws Exception {
		VAuthority entity = authorityManager.getVAuthority(eid);
		ActionContext.getContext().put("entity", entity);
		loadOperation();
		return VIEW;
	}
	
	private void loadOperation() {
		if (eid!=null) {
			List<Authority> list = authorityManager.loadOperation(eid);
			if (list.size()>0) {
				ActionContext.getContext().put("list", list);				
			}
		}
	}
	
	public String form() throws Exception {
		if (eid==null) {
			String pid = Struts2Utils.getParameter("pid");
			if (StringUtils.isNotBlank(pid)) {
				VAuthority entity = authorityManager.getSimpleVAuthority(Long.parseLong(pid));
				ActionContext.getContext().put("entity", entity);
			}
		}else {			
			VAuthority entity = authorityManager.getVAuthority(eid);
			ActionContext.getContext().put("entity", entity);
			loadOperation();
		}
		return INPUT;
	}
	
	public String checkUnique() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String propertyName = request.getParameter("propertyName");
		String value = request.getParameter(propertyName);
		if (authorityManager.isUnique(propertyName, value)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}	

	@Override
	public String view() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}
}