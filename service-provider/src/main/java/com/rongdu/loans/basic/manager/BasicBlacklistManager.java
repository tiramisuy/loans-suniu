package com.rongdu.loans.basic.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.basic.dao.BasicBlacklistDAO;
import com.rongdu.loans.basic.entity.BasicBlacklist;


/**
 * 基础黑名单-实体管理实现类
 * @author likang
 * @version 2017-10-10
 */
@Service("basicBlacklistManager")
public class BasicBlacklistManager extends BaseManager<BasicBlacklistDAO, BasicBlacklist, String> {

	/**
	 * 该手机号或者ip是否命中黑名单
	 * @param value
	 * @return
	 */
    @Autowired
    private BasicBlacklistDAO blacklistDAO;
    
	public boolean isInBlacklist(String value) {
		return blacklistDAO.countByBlValue(value) > 0;
	}
}
