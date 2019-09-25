/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.OverdueCountDao;
import com.rongdu.loans.loan.option.OverdueCountOP;
import com.rongdu.loans.loan.vo.OverdueCountVO;

/**
 * 逾期统计
 * @author liuliang
 * @version 2018-05-31
 */
@Service("overdueCountManager")
public class OverdueCountManager extends BaseManager<OverdueCountDao, OverdueCountVO, String> {
		
	public List<OverdueCountVO> overdueCountList(Page<OverdueCountVO> page,OverdueCountOP op){
		return dao.overdueCountList(page,op);
	}
}