/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;


import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.dao.ContactHistoryDAO;
import com.rongdu.loans.loan.entity.ContactHistory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 历次贷款申请时的紧急联系人信息-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-08-01
 */
@Service("contactHistoryManager")
public class ContactHistoryManager
        extends BaseManager<ContactHistoryDAO, ContactHistory, String> {

    /**
     * 获取当前紧急联系人表信息
     * @param userId
     * @return
     */
    public List<ContactHistory> getContactByUserId(String userId) {
        return dao.getContactByUserId(userId);
    }

    public List<ContactHistory> getByMobile(List<String> list) {
        return dao.getByMobile(list);
    }

    /**
     * 保存历史快照
     * @param applyId
     * @param userId
     * @return
     */
    public int saveContactSnap(String applyId, String userId) {
        // 获取当前联系人信息
        List<ContactHistory> list = getContactByUserId(userId);
        if(null == list || list.size() ==0) {
            return 1;
        }
        int size = list.size();
        for(int i=0;i<size;i++) {
            ContactHistory temp = list.get(i);
            temp.setApplyId(applyId);
            temp.preInsert();
        }
        return dao.insertBatch(list);
    }

    /**
     * 删除联系人
     * @param applyId
     * @return
     */
    public int delContactSnap(String applyId) {
        if(StringUtils.isNotBlank(applyId)) {
            return dao.delContactHistory(applyId);
        }
        return 0;
    }

    /**
     * 根据申请编号获取当前紧急联系人表信息
     * @param applyId 申请编号
     * @return
     */
    public List<ContactHistory> getContactHisByApplyNo(String applyId) {
        return dao.getContactHisByApplyNo(applyId);
    }

    /**
     * 删除催收来源的联系人
     * @param id
     * @return
     */
    public int deleteFromCollection(String id) {
        // 初始化参数对象
        ContactHistory contactHistory = new ContactHistory();
        contactHistory.setId(id);
        contactHistory.preUpdate();
        return dao.deleteFromCollection(contactHistory);
    }

	
}