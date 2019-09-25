/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.entity.ApplyTripartiteDwd;
import com.rongdu.loans.loan.dao.ApplyTripartiteDwdDao;

/**
 * 工单映射（大王贷）-实体管理实现类
 * @author Lee
 * @version 2018-10-30
 */
@Service("applyTripartiteDwdManager")
public class ApplyTripartiteDwdManager extends BaseManager<ApplyTripartiteDwdDao, ApplyTripartiteDwd, String> {
	
	public List<String> findThirdIdsByApplyIds(List<String> applyIds){
		return dao.findThirdIdsByApplyIds(applyIds);
	}
}