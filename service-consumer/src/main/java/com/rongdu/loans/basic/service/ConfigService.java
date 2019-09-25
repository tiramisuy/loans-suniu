/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.basic.vo.ConfigVO;

/**
 * 公共配置表-业务逻辑接口
 * 
 * @author liuzhuang
 * @version 2018-07-16
 */
public interface ConfigService {

	public String getValue(String key);

	public Page<ConfigVO> getConfigList();

	public void changeConfig(ConfigVO entity);
}