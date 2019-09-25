/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.cust.entity.BindCard;
import com.rongdu.loans.cust.manager.BindCardManager;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.vo.BindCardVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 绑卡信息-业务逻辑实现类
 * @author sunda
 * @version 2017-07-21
 */
@Service("bindCardService")
public class BindCardServiceImpl  extends BaseService implements  BindCardService{
	/**
 	* 绑卡信息-实体管理接口
 	*/
	@Autowired
	private BindCardManager bindCardManager;

	@Override
	public BindCardVO get(String id) {
		BindCard entity = bindCardManager.get(id);
		BindCardVO vo = null;
		if (entity!=null) {
			vo = BeanMapper.map(entity, BindCardVO.class);		
		}
		return vo;
	}

	@Override
	public int save(BindCardVO vo) {
		BindCard entity = BeanMapper.map(vo, BindCard.class);
		return bindCardManager.insert(entity);
	}

	@Override
	public int cancelOtherBindInfo(BindCardVO vo) {
		BindCard entity = BeanMapper.map(vo, BindCard.class);
		return bindCardManager.cancelOtherBindInfo(entity);
	}

	@Override
	public int update(BindCardVO vo) {
		BindCard entity = BeanMapper.map(vo, BindCard.class);
		return bindCardManager.update(entity);
	}

	@Override
	public BindCardVO findByOrderNo(String orderNo) {
		BindCard entity = bindCardManager.findByOrderNo(orderNo);
		BindCardVO vo = null;
		if (entity!=null) {
			vo = BeanMapper.map(entity, BindCardVO.class);		
		}
		return vo;
	}

	/**
	 * 根据绑定号，查询绑卡信息
	 * @param bindId
	 * @return
	 */
	public BindCardVO findByBindId(String bindId){
		BindCard entity = bindCardManager.findByBindId(bindId);
		BindCardVO vo = null;
		if (entity!=null) {
			vo = BeanMapper.map(entity, BindCardVO.class);
		}
		return vo;
	}

	@Override
	public BindCardVO getBindCard(String mobile, String idCard, String cardNo, String realName) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("mobile", mobile));
		criteria.and(Criterion.eq("name", realName));
		criteria.and(Criterion.eq("card_no", cardNo));
		criteria.and(Criterion.eq("id_no", idCard));
		BindCard entity = bindCardManager.getByCriteria(criteria);
		BindCardVO vo = null;
		if (entity != null) {
			vo = BeanMapper.map(entity, BindCardVO.class);
		}
		return vo;
	}

}