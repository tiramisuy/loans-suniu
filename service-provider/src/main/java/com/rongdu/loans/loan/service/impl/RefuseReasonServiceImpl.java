/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.RefuseReason;
import com.rongdu.loans.loan.manager.RefuseReasonManager;
import com.rongdu.loans.loan.service.RefuseReasonService;
import com.rongdu.loans.loan.vo.RefuseReasonVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 贷款审核拒绝原因-业务逻辑实现类
 * @author zhangxiaolong
 * @version 2017-07-07
 */
@Service("refuseReasonService")
public class RefuseReasonServiceImpl  extends BaseService implements  RefuseReasonService{
	
	/**
 	* 贷款审核拒绝原因-实体管理接口
 	*/
	@Autowired
	private RefuseReasonManager refuseReasonManager;


	@Override
	public List<RefuseReasonVO> findAll() {
		Criteria criteria = new Criteria();
		List<RefuseReason> list = refuseReasonManager.findAllByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		return BeanMapper.mapList(list, RefuseReasonVO.class);
	}
}