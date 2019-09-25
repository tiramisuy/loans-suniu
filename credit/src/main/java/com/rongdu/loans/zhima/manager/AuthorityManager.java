/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.zhima.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.zhima.dao.AuthorityDao;
import com.rongdu.loans.zhima.entity.Authority;
import org.springframework.stereotype.Service;

/**
 * 芝麻信用授权-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("authorityManager")
public class AuthorityManager extends BaseManager<AuthorityDao, Authority, String>{
	
}