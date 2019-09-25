/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import java.util.List;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.Config;
import com.rongdu.loans.basic.vo.ConfigVO;

/**
 * 公共配置表-数据访问接口
 * 
 * @author liuzhuang
 * @version 2018-07-16
 */
@MyBatisDao
public interface ConfigDao extends BaseDao<Config, String> {
	public List<Config> findAll();

	public List<ConfigVO> getConfigList();

	public void changeConfig(ConfigVO entity);
}