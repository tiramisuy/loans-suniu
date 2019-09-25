/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.basic.entity.Config;
import com.rongdu.loans.basic.vo.ConfigVO;
import com.rongdu.loans.basic.dao.ConfigDao;

/**
 * 公共配置表-实体管理实现类
 * 
 * @author liuzhuang
 * @version 2018-07-16
 */
@Service("configManager")
public class ConfigManager extends BaseManager<ConfigDao, Config, String> {
	public List<Config> findAll() {
		return dao.findAll();
	}

	public List<ConfigVO> getConfigList() {
		return dao.getConfigList();
	}

	public void changeConfig(ConfigVO entity) {
		dao.changeConfig(entity);
	}
}