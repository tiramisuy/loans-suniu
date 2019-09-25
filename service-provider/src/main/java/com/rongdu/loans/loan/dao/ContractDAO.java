package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 借款合同-数据访问接口
 * @author likang
 * @version 2017-07-11
 */
@MyBatisDao
public interface ContractDAO extends BaseDao<Contract,String> {

	/**
	 * 根据申请编号查询到账信息
	 * @param applyId
	 * @return
	 */
	Contract getByApplyId(String applyId);
	
	/**
	 * 根据用户id查询未结清合同
	 * @param userId
	 * @return
	 */
	Contract getUnFinishContractByUserId(String userId);
	
	/**
	 * 最近三天申请放款记录
	 * @return
	 */
	List<Contract> getRecentThreeDaysRecords();
	
	/**
	 * 更新合同状态
	 * @param contract
	 * @return
	 */
	int updateStatus(Contract contract);

	/**
	 * 根据id查询合同列表
	 * @param idList
	 * @return
	 */
	List<Contract> findByIdList(@Param("idList") List<String> idList);
	
	int updateForDelay(Contract contract);
	
	int updateforRepayTime(Contract contract);
	
	public int delByApplyId(@Param("applyId")String applyId);
}
