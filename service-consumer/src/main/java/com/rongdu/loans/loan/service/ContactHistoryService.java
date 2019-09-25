/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.loans.loan.option.ContactHistoryOP;
import com.rongdu.loans.loan.vo.ContactHistoryVO;

/**
 * 历次贷款申请时的紧急联系人信息-业务逻辑接口
 * @author zhangxiaolong
 * @version 2017-08-01
 */
public interface ContactHistoryService {

    /**
     * 审核详情 ->查重信息
     *
     * 1.查询客户订单的（最多4个）常用联系人
     * 2.将客户及常用联系人（最多共5个电话号码）到历史联系人表中匹配，查询所有以这些号码作为联系人的数据
     * 3.以这5个联系人分组展示匹配到的数据
     *
     * @param op
     * @return
     */
    List<ContactHistoryVO> removeDuplicateQuery(ContactHistoryOP op);

}