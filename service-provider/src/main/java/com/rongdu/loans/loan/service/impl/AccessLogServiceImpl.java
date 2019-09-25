package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.app.manager.AccessLogManager;
import com.rongdu.loans.loan.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * APP访问日志-业务逻辑实现类
 * @author likang
 * @version 2017-08-11
 */
@Service("accessLogService")
public class AccessLogServiceImpl extends BaseService implements AccessLogService{

	/**
 	* APP访问日志-实体管理接口
 	*/
	@Autowired
	private AccessLogManager accessLogManager;
}
