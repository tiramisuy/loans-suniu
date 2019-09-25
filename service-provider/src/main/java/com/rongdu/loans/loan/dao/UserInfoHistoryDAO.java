package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.UserInfoHistory;
import org.apache.ibatis.annotations.Param;

/**
 * 用户基本信息快照
 * @author likang
 * @version 2017-08-02
 */
@MyBatisDao
public interface UserInfoHistoryDAO extends BaseDao<UserInfoHistory, String> {

	/**
	 * 根据用户id获取当前用户基本信息
	 * @param userId
	 * @return
	 */
	UserInfoHistory getCurUserInfo(@Param(value = "userId")String userId);
	
	/**
	 * 根据申请编号获取信息
	 * @param applyId
	 * @return
	 */
	UserInfoHistory getByApplyId(@Param(value = "applyId")String applyId);
	
	/**
	 * 根据申请编号统计
	 * @param applyId
	 * @return
	 */
	int countByApplyId(@Param(value = "applyId")String applyId);
}
