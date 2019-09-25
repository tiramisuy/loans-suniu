/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.ContactHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 历次贷款申请时的紧急联系人信息-数据访问接口
 * @author zhangxiaolong
 * @version 2017-08-01
 */
@MyBatisDao
public interface ContactHistoryDAO extends BaseDao<ContactHistory,String> {

    /**
     * 获取当前紧急联系人表信息
     * @param userId
     * @return
     */
    List<ContactHistory> getContactByUserId(@Param(value = "userId")String userId);
    
    /**
     * 根据申请编号获取当前紧急联系人表信息
     * @param applyId 申请编号
     * @return
     */
    List<ContactHistory> getContactHisByApplyNo(@Param(value = "applyId")String applyId);
    
	/**
	 * 删除联系人
	 * @param applyId
	 * @return
	 */
	int delContactHistory(@Param(value = "applyId")String applyId);

    List<ContactHistory> getByMobile(@Param(value = "list") List<String> list);
    
    /**
     * 删除催收来源的联系人
     * @param contactHistory
     * @return
     */
    int deleteFromCollection(ContactHistory contactHistory);
}