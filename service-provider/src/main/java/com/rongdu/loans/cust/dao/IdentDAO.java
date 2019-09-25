package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.Ident;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@MyBatisDao
public interface IdentDAO extends BaseDao<Ident, String> {
	
    /**
     * 根据用户id统计记录条数
     * @param userId
     * @return
     */
    int countByUserId(String userId);
    
    /**
     * 根据用户id更新
     * @param entity
     * @return
     */
    int updateByUserId(Ident entity);
}
