package com.rongdu.loans.sys.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.web.BaseController;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.vo.ConfigVO;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * Created by fy on 2018/10/18.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/config")
public class ConfigController extends BaseController {

	@Autowired
	private ConfigService configService;
	
	/**
	 * 配置列表
	 */
	@RequestMapping(value = "list")
	public String configList(Model model) {
		model.addAttribute("page", configService.getConfigList());
		return "modules/sys/configList";
	}

	/**
	 * 更改配置表
	 */
	@ResponseBody
	@RequestMapping(value = "updateBasicConfig")
	public WebResult updateBasicConfig(@RequestParam(value = "id") String id,@RequestParam(value = "value") String value) {
		User user = UserUtils.getUser();
		logger.info("更改放款配置表--->{}--->{}", user.getId(), user.getName());
		ConfigVO entity = new ConfigVO();
		entity.setId(id);
		entity.setValue(value);
		entity.setUpdateBy(user.getName());
		entity.setUpdateTime(new Date());
		configService.changeConfig(entity);
		return new WebResult("1", "修改成功");
	}
}
