/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.manager;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.basic.dao.IpLocationDAO;
import com.rongdu.loans.basic.entity.IpLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基于IP的地理定位信息-实体管理接口
 * @author likang
 * @version 2017-08-15
 */
@Service("ipLocationManager")
public class IpLocationManager extends BaseManager<IpLocationDAO, IpLocation, String> {

	@Autowired
	private IpLocationDAO ipLocationDAO;

	/**
	 * 根据ip查询地理位置信息
	 * @param ip
	 * @return
	 */
	public IpLocation getByIp(String ip) {
		return ipLocationDAO.getByIp(ip);
	}

	/**
	 * 判断该ip是否录入
	 * @param ip
	 * @return
	 */
	public boolean isExistIp(String ip) {
		return ipLocationDAO.countByIp(ip) > 0;
	}

}
