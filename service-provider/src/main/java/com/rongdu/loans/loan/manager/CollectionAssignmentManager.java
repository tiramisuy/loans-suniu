/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.CollectionAssignmentDao;
import com.rongdu.loans.loan.entity.CollectionAssignment;

/**
 * 催收分配记录-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-09-28
 */
@Service("collectionAssignmentManager")
public class CollectionAssignmentManager extends BaseManager<CollectionAssignmentDao, CollectionAssignment, String> {

    public List<CollectionAssignment> getAllByRepayPlanItemId(String repayPlanItemId){
        return dao.getAllByRepayPlanItemId(repayPlanItemId);
    }

    public List<CollectionAssignment> getAllByItemIdAndDel(List<String> idList, Integer del){
        return dao.getAllByItemIdAndDel(idList, del);
    }

    /**
     * 将之前数据失效，插入新的催收分配数据
     * @param list
     */
    public void doAllotment(List<CollectionAssignment> list){
        List<String> idList = new ArrayList<>();
        for (CollectionAssignment c : list){
            idList.add(c.getRepayPlanItemId());
        }
        dao.deleteByIdList(idList);
        super.insertBatch(list);
    }

    public List<CollectionAssignment> getForReturnBack(){
        return dao.getForReturnBack();
    }
    
    public CollectionAssignment getOperateByApplyId(String applyId){
    	return dao.getOperateByApplyId(applyId);
    }
    
    

}