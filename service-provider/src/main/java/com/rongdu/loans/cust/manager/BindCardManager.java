/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.manager;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.dao.BindCardDao;
import com.rongdu.loans.cust.entity.BindCard;
import org.springframework.stereotype.Service;

/**
 * 绑卡信息-实体管理接口
 * @author sunda
 * @version 2017-07-21
 */
@Service("bindCardManager")
public class BindCardManager extends BaseManager<BindCardDao, BindCard, String>{

	public int cancelOtherBindInfo(BindCard entity) {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("id", entity.getId()));
		criteria.and(Criterion.ne("user_id", entity.getUserId()));
		BindCard card = new BindCard();
		card.setDel(BaseEntity.DEL_DELETE);
		return getDao().updateByCriteriaSelective(card, criteria);
	}

	public BindCard findByOrderNo(String orderNo) {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("chl_order_no", orderNo));
		return getDao().getByCriteria(criteria);
	}

	public BindCard findByBindId(String bindId) {
		Criteria criteria = new Criteria();
		criteria.and(Criterion.eq("bind_id", bindId));
		return getDao().getByCriteria(criteria);
	}

}