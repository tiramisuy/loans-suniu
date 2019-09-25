/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.manager;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.borrow.dao.HelpApplyDao;
import com.rongdu.loans.borrow.entity.HelpApply;
import com.rongdu.loans.borrow.option.HelpApplyForListOP;
import com.rongdu.loans.borrow.vo.HelpApplyVO;

/**
 * 助贷申请表-实体管理实现类
 * @author liuliang
 * @version 2018-08-28
 */
@Service("helpApplyManager")
public class HelpApplyManager extends BaseManager<HelpApplyDao, HelpApply, String> {
	
	public List<HelpApplyVO> getHelpApplyList(){
		return dao.getHelpApplyList();
	}
	
	public List<HelpApplyVO> getBorrowHelpList(Page page,HelpApplyForListOP op){
		return dao.getBorrowHelpList(page,op);
	}
	
	public boolean isBorrowHelping(String mobile) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("a.mobile", mobile));
		criteria.and(Criterion.eq("a.status", "2"));
		criteria.and(Criterion.eq("a.del", "0"));
		HelpApply helpApply = dao.getByCriteria(criteria);
		if (helpApply != null) {
			return true;
		}
		return false;
	}
	
	public HelpApply getBorrowHelping(String mobile) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("a.mobile", mobile));
		criteria.and(Criterion.eq("a.status", "2"));
		criteria.and(Criterion.eq("a.del", "0"));
		return dao.getByCriteria(criteria);
	}
	
	public HelpApply getBorrowHelpByIdNo(String idNo) {
		Date date = DateUtils.addDay(new Date(), -1);
		return dao.getBorrowHelpByIdNo(idNo,DateUtils.formatDateTime(date));
	}
	
	/**
	 * 更新助贷申请表，分配的用户信息
	 * @param borrowId
	 * @param userId
	 * @param userName
	 * @return
	 */
	public int updateAllotApply(String borrowId,String userId,String userName,String updateBy){
		return dao.updateAllotApply(borrowId,userId,userName,updateBy);
	}
	
	
	public HelpApply getHelpApplyByIdNo(String idNo){
		return dao.getHelpApplyByIdNo(idNo);
	}
	
	public int updateConcurent(HelpApply entity) {
		entity.preUpdate();
		return dao.updateConcurent(entity);
	}
	
}