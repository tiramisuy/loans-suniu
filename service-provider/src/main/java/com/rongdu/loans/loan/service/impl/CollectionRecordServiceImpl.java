package com.rongdu.loans.loan.service.impl;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.entity.CollectionRecord;
import com.rongdu.loans.loan.entity.ContactHistory;
import com.rongdu.loans.loan.entity.Overdue;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.CollectionRecordManager;
import com.rongdu.loans.loan.manager.ContactHistoryManager;
import com.rongdu.loans.loan.manager.OverdueManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.CollectionRecordOP;
import com.rongdu.loans.loan.service.CollectionRecordService;
import com.rongdu.loans.loan.vo.CollectionRecordVO;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 催收记录-业务逻辑实现类
 * @author zhangxiaolong
 * @version 2017-10-9
 */
@Service("collectionRecordService")
public class CollectionRecordServiceImpl extends BaseService implements CollectionRecordService{

    @Autowired
    private CollectionRecordManager collectionRecordManager;
    @Autowired
    private ContactHistoryManager contactHistoryManager;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private OverdueManager overdueManager;

    @Override
    @Transactional
    public Integer save(CollectionRecordOP op) {
        CollectionRecord collectionRecord = BeanMapper.map(op, CollectionRecord.class);
        Overdue overdue = overdueManager.getById(op.getItemId());
        if (overdue == null){
            logger.error("逾期还款数据不存在：itemId = ", op.getItemId());
            return 0;
        }
        //1父母，2配偶，3朋友，4同事
        Integer relationship = 6;
        String name = "";
        String mobile = "";
        //如果是本人
        if (StringUtils.equals(op.getContactId(), overdue.getUserId())){
            name = overdue.getUserName();
            mobile = overdue.getMobile();
        } else {
            ContactHistory contact = contactHistoryManager.getById(op.getContactId());
            if (contact != null){
            	relationship = contact.getRelationship();
                name = contact.getName();
                mobile = contact.getMobile();
//                logger.warn("历史联系人数据不存在：contactId = ", op.getContactId());
//                return 0;
            } else {
            	relationship = 7;
            }
        }

        RepayPlanItem repayPlanItem = repayPlanItemManager.getById(op.getItemId());
        if (repayPlanItem == null){
            logger.error("还款计划明细数据不存在：itemId = ", op.getItemId());
            return 0;
        }
        collectionRecord.setRelationship(relationship);
        collectionRecord.setContactName(name);
        collectionRecord.setContactMobile(mobile);
        collectionRecord.setRepayPlanItemId(op.getItemId());
        collectionRecord.setRepayPlanItemId(repayPlanItem.getId());
        collectionRecord.setApplyId(repayPlanItem.getApplyId());
        collectionRecord.setContNo(repayPlanItem.getContNo());
        collectionRecord.setUserId(repayPlanItem.getUserId());
        collectionRecord.setUserName(repayPlanItem.getUserName());
        collectionRecord.setCreateBy(collectionRecord.getOperatorName());
        collectionRecord.setCreateTime(new Date());
        collectionRecord.preUpdate();
        if (StringUtils.isNotBlank( op.getNextContactTimeStr())) {
            collectionRecord.setNextContactTime(DateUtils.parse(op.getNextContactTimeStr()));
        }
        if (StringUtils.isNotBlank(op.getPromiseDateStr())) {
            collectionRecord.setPromiseDate(DateUtils.parse(op.getPromiseDateStr()));
        }
        int result = collectionRecordManager.insert(collectionRecord);
        if (result > 0){
            Overdue updateEntity = new Overdue();
            updateEntity.setId(overdue.getId());
            updateEntity.setResult(op.getResult());
            updateEntity.setContent(op.getContent());
            updateEntity.setUpdateBy(op.getOperatorName());
            updateEntity.setUpdateTime(new Date());
            overdueManager.updateByIdSelective(updateEntity);
        }
        return result;
    }

    @Override
    public List<CollectionRecordVO> list(String itemId) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("repay_plan_item_id", itemId));
        List<CollectionRecord> list = collectionRecordManager.findAllByCriteria(criteria);
        if (CollectionUtils.isNotEmpty(list)){
            return BeanMapper.mapList(list, CollectionRecordVO.class);
        }
        return Collections.EMPTY_LIST;
    }
}
