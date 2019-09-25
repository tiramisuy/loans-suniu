package com.rongdu.loans.web.action.internet;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * www目录下的资源可以对外网发布
 */
@Namespace("/www")
@Results( {
	@Result(name = "login", location = "/login2.jsp"),
	@Result(name = "device-map", location = "/www/device-map.action",type="redirect")
})
public class InternetAction extends ActionSupport {

	private static final long serialVersionUID = 4989311084866378323L;
	
	public String execute() throws Exception {
		return "login";
	}
	
	public String deviceMap() throws Exception {
		return "device-map";
	}
		
}