/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.loan.entity.ContactHistory;
import com.rongdu.loans.loan.manager.ContactHistoryManager;
import com.rongdu.loans.loan.option.ContactHistoryOP;
import com.rongdu.loans.loan.service.ContactHistoryService;
import com.rongdu.loans.loan.vo.ContactHistoryVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 历次贷款申请时的紧急联系人信息-业务逻辑实现类
 * @author zhangxiaolong
 * @version 2017-08-01
 */
@Service("contactHistoryService")
public class ContactHistoryServiceImpl  extends BaseService implements  ContactHistoryService{

	@Autowired
	private ContactHistoryManager contactHistoryManager;

	@Override
	public List<ContactHistoryVO> removeDuplicateQuery(ContactHistoryOP op) {
		//查询客户订单的常用联系人
		List<ContactHistory> userContactList = getUserContactList(op);
		//将客户及常用联系人（最多共5个电话号码）到历史联系人表中匹配，查询所有以这些号码作为联系人的数据
		List<ContactHistory> list = getContactHistorieList(op, userContactList);
		if (CollectionUtils.isEmpty(list)){
			return Collections.EMPTY_LIST;
		}
		//分组匹配数据
		return assembleVO(op, userContactList, list);
	}

	/**
	 * 分组匹配数据
	 * @param op
	 * @param userContactList
	 * @param list
	 * @return
	 */
	private List<ContactHistoryVO> assembleVO(ContactHistoryOP op, List<ContactHistory> userContactList, List<ContactHistory> list) {
		List<ContactHistoryVO> voList = new ArrayList<>();

		for (ContactHistory contactHistory : list){
			ContactHistoryVO vo = BeanMapper.map(contactHistory, ContactHistoryVO.class);
			//匹配到用户本人
			if (StringUtils.equals(contactHistory.getMobile(), op.getMobile())){
				vo.setSourceUserName(op.getUserName());
				vo.setSourceMobile(op.getMobile());
				//本就就不填写具体关系，页面展示为“本人”
				vo.setSourceRelationship(null);
				voList.add(vo);
				continue;
			}
			//匹配到用户联系人
			for (ContactHistory contact : userContactList){
				if (StringUtils.equals(contactHistory.getId(), contact.getId())){
					//如果id相等，说明该数据二次被查出来，需要剔除掉
					break;
				}
				if (StringUtils.equals(contactHistory.getMobile(), contact.getMobile())){
					vo.setSourceUserName(contact.getName());
					vo.setSourceMobile(contact.getMobile());
					vo.setSourceRelationship(contact.getRelationship());
					voList.add(vo);
					break;
				}
			}

		}
		return voList;
	}

	/**
	 * 将客户及常用联系人（最多共5个电话号码）到历史联系人表中匹配，查询所有以这些号码作为联系人的数据
	 * @param op
	 * @param userContactList
	 * @return
	 */
	private List<ContactHistory> getContactHistorieList(ContactHistoryOP op, List<ContactHistory> userContactList) {
		List<String> mobileList = new ArrayList<>();
		mobileList.add(op.getMobile());
		if (CollectionUtils.isNotEmpty(userContactList)){
			for (ContactHistory contact : userContactList){
				if (StringUtils.isNotBlank(contact.getMobile())){
					mobileList.add(contact.getMobile());
				}
			}
		}
		return contactHistoryManager.getByMobile(mobileList);
	}

	/**
	 * 查询客户订单的常用联系人
	 * @param op
	 * @return
	 */
	private List<ContactHistory> getUserContactList(ContactHistoryOP op) {
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("user_id", op.getUserId()));
		criteria.and(Criterion.eq("apply_id", op.getApplyId()));
		criteria.and(Criterion.ne("relationship", RelationshipEnum.BAIQISHI.getValue()));
		return contactHistoryManager.findAllByCriteria(criteria);
	}
}