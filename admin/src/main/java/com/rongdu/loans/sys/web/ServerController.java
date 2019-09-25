/**
 * 聚宝钱包-资金存管系统对接程序
 */
package com.rongdu.loans.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.sys.entity.Office;

/**
 * 服务器Controller
 * @author sunda
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/server")
public class ServerController extends BaseController {


//	@RequiresPermissions("sys:server:view")
	@RequestMapping(value = {"info"})
	public String info(Office office, Model model) {
		return "modules/sys/serverInfo";
	}


}
