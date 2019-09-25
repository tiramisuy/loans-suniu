package com.rongdu.loans.config;

import com.rongdu.loans.common.WebResult;
import com.rongdu.common.utils.CacheUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.web.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/config/cache/")
public class CacheManagerController extends BaseController {

	/**
	 * 缓存管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cacheManager")
	public String custuserList(Model model) {
		return "modules/config/cacheManager";
	}

	/**
	 * 短信缓存管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "msgCacheManager")
	public String msgCacheManager(Model model) {
		return "modules/config/msgCacheManager";
	}

	/**
	 * 命中规则列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "clearCache")
	public WebResult clearCache(@RequestParam(value = "key") String key, @RequestParam(value = "type") Integer type) {
		try {
			List<String> list = JedisUtils.batchDel(key, type);
			StringBuffer result = new StringBuffer("清除成功： ");
			for (String item : list) {
				result.append(item).append(",");
			}
			if (list.size() > 0) {
				result.deleteCharAt(result.length() - 1);
			}
			return new WebResult("1", "提交成功", result.toString());
		} catch (Exception e) {
			logger.error("系统异常", e);
			return new WebResult("99", "系统异常");
		}
	}

	@ResponseBody
	@RequestMapping(value = "clearEhCache")
	public WebResult clearEhCache(@RequestParam(value = "key") String key, @RequestParam(value = "name") String name) {
		try {
			CacheUtils.remove(name, key);
			return new WebResult("1", "提交成功", "清除成功");
		} catch (Exception e) {
			logger.error("系统异常", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 命中规则列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "clearMsgCache")
	public WebResult clearMsgCache(@RequestParam(value = "key") String key, @RequestParam(value = "type") Integer type) {
		try {
			// 1=注册，2=忘记密码，5=短信验证码登录
			String[] msgTypes = { "1", "2", "5" };
			for (String msgType : msgTypes) {
				JedisUtils.batchDel(key + "_" + msgType + "_mcode_mob_ls_count", type);
				JedisUtils.batchDel(key + "_" + msgType + "_mcode_mob_la_count", type);
			}
			JedisUtils.batchDel("pwd_err_cnt_" + key, type);
			JedisUtils.batchDel("lock_user_" + key, type);
			return new WebResult("1", "提交成功", "清除成功");
		} catch (Exception e) {
			logger.error("系统异常", e);
			return new WebResult("99", "系统异常");
		}
	}
}
