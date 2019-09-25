/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.basic.entity.Config;
import com.rongdu.loans.basic.manager.ConfigManager;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.vo.ConfigVO;

/**
 * 公共配置表-业务逻辑实现类
 * 
 * @author liuzhuang
 * @version 2018-07-16
 */
@Service("configService")
public class ConfigServiceImpl extends BaseService implements ConfigService {
	public static final String CONFIG_LIST_CACHE_KEY = "CONFIG_LIST";
	/**
	 * 公共配置表-实体管理接口
	 */
	@Autowired
	private ConfigManager configManager;

	@Override
	public String getValue(String key) {
		String value = null;
		List<Config> list = (List<Config>) JedisUtils.getObject(CONFIG_LIST_CACHE_KEY);
		if (list == null) {
			list = findAll();
			JedisUtils.setObject(CONFIG_LIST_CACHE_KEY, list, Global.THREE_DAY_CACHESECONDS);
		}
		if (list != null) {
			for (Config c : list) {
				if (key.equals(c.getKey())) {
					value = c.getValue();
					break;
				}
			}
		}
		Assert.notNull(value, key + "未匹配到");
		return value;
	}

	private List<Config> findAll() {
		return configManager.findAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<ConfigVO> getConfigList() {
		Page voPage = new Page(1, 20);
		List<ConfigVO> voList = configManager.getConfigList();
		// setWarnData(op, voList);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public void changeConfig(ConfigVO entity) {
		configManager.changeConfig(entity);
		JedisUtils.del(CONFIG_LIST_CACHE_KEY);
	}
}