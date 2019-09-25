package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 贷款操作日志DAO接口
 * @author likang
 * @version 2017-07-04
 */
@MyBatisDao
public interface OperationLogDAO extends BaseDao<OperationLog, String>{

	/**
	 * 根据申请编号查询操作记录
	 * @param applyId
	 * @return
	 */
	List<OperationLog> getByApplyId(String applyId);
	
	/**
	 * 根据userId查询操作记录
	 * @param applyId
	 * @return
	 */
	List<OperationLog> getByUserId(String userId);
	
	/**
	 * 根据userId查询审核前的操作记录
	 * @param applyId
	 * @return
	 */
	List<OperationLog> getUnAuditbyUserId(String userId);


	/**
	 * 删除认证记录
	 * @param id
	 * @return
	 */
	int delAuthByStatus(@Param("userId")String userId, @Param("status")Integer status);
}
