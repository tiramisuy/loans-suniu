package com.rongdu.loans.web.action.sys;

import net.sf.ehcache.CacheManager;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/sys")
@Results( {
	@Result(name = "cache", location = "/WEB-INF/pages/sys/cache/cache.jsp")
})
public class CacheAction extends ActionSupport {

	private static final long serialVersionUID = 4989311084866378322L;
	
	@Autowired
	private CacheManager ehcacheManager;
	
	@Override
	public String execute() throws Exception {	
		return "cache";
	}
	
	public String flush() throws Exception {
		String[] cacheNames = ehcacheManager.getCacheNames();
		for (String name:cacheNames) {
			ehcacheManager.getCache(name).flush();
		}
		ActionContext.getContext().put("msg", "系统缓存已经刷新");
		return "cache";
	}
	
}