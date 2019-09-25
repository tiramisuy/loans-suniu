package com.rongdu.loans.loan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.loans.loan.vo.AuthenticationStatusVO;
import com.rongdu.loans.loan.vo.OperationLogVO;

/**
 * 操作日志接口
 * 
 * @author likang
 * @version 2017-07-04
 */
@Service
public interface OperationLogService {

	/**
	 * 根据申请编号查询操作记录
	 * 
	 * @param applyId
	 * @return
	 */
	// List<OperationLogVO> getByApplyId(String applyId);

	/**
	 * 根据申请编号返回相关认证状态
	 * 
	 * @param applyId
	 * @return
	 */
	AuthenticationStatusVO getAuthenticationStatus(String userId, String productId);

	/**
	 * 是否已认证
	 * 
	 * @param userId
	 * @return
	 */
	int getAuthStatus(String userId, String productId);

	/**
	 * 是否更多已认证
	 * 
	 * @param userId
	 * @return
	 */
	int getMoreAuthStatus(String userId, String productId);

	public List<OperationLogVO> getByUserId(String userId);

	public int delAuthByStatus(String userId, Integer status);
}
