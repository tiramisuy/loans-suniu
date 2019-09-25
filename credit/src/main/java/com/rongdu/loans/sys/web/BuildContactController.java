package com.rongdu.loans.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.credit.common.RedisPrefix;
import com.rongdu.loans.risk.service.CreditDataPersistenceService;

/**
 * 
 * @author liuzhuang
 * @version 2017-10-18
 */
@Controller
public class BuildContactController extends BaseController {
	@Autowired
	private CreditDataPersistenceService creditDataPersistenceService;

	@RequestMapping(value = "credit/anon/buildContact", method = RequestMethod.POST)
	@ResponseBody
	public Object buildBaiqishiReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String priKey = request.getParameter("priKey");
		String applyId = request.getParameter("applyId");
		String myPriKey = "juqianbao123qweQWE+456";
		if (!myPriKey.equals(priKey)) {
			return "fail";
		}
		if (StringUtils.isBlank(applyId)) {
			return "fail";
		}
		String cacheKey = RedisPrefix.BAIQISHI_CONTACT_INFO + applyId;
		JedisUtils.del(cacheKey);

		creditDataPersistenceService.saveBaiqishiDeviceContactFile(applyId);
		return "success";
	}

}
