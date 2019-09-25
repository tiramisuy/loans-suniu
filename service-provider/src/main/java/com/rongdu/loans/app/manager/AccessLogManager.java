package com.rongdu.loans.app.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.app.dao.AccessLogDAO;
import com.rongdu.loans.app.entity.AccessLog;
import org.springframework.stereotype.Service;

/**
 * APP访问日志-实体管理接口
 * @author likang
 * @version 2017-08-11
 */
@Service("accessLogManager")
public class AccessLogManager extends BaseManager<AccessLogDAO, AccessLog, String> {

}
