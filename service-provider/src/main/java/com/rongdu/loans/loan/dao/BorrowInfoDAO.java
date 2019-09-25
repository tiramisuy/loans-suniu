/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import java.math.BigDecimal;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.BorrowInfo;

/**
 * 借款标的推送-数据访问接口
 * @author zhangxiaolong
 * @version 2017-07-22
 */
@MyBatisDao
public interface BorrowInfoDAO extends BaseDao<BorrowInfo,String> {

    int updatePushStatus(BorrowInfo borrowInfo);

    BorrowInfo getByOutsideSerialNo(String outsideSerialNo);

    /**
     * 根据申请编号查询借款标的信息
     * @param applyId
     * @return
     */
    BorrowInfo getByApplyId(String applyId);
    
    /**
     * 根据申请编号查询外部流水号
     */
    String getOutSideNumByApplyId(String applyId);
    
    /**
     * 根据外部流水号查询申请编号
     */
    String getApplyIdByOutSideNum(String outSideNum);
    
    /**
     * 汉金所当日放款限额
     */
    BigDecimal getHanjscurrPayedAmt();
    
    public int delByApplyId(String applyId);
    
    /**
     * 乐视今日已推标金额
     * @return
     */
    BigDecimal sumLeshiPayedAmt();
}