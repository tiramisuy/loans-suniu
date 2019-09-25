
package com.rongdu.loans.sys.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.web.BaseController;

/**
 * 登录Controller
 * @author sunda
 * @version 2013-5-31
 */
@Controller
public class AuthorizeController extends BaseController{
	
	/**
	 * Shiro认证未通过，则返回如下结果
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "${adminPath}/anon/unauthorized", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> unauthorized() throws Exception {
		ApiResult result = new ApiResult(ErrInfo.FORBIDDEN);
		return result;
	}
	
}
