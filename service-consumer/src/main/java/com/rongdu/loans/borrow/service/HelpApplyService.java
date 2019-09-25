/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.borrow.option.HelpApplyForListOP;
import com.rongdu.loans.borrow.option.HelpApplyOP;
import com.rongdu.loans.borrow.vo.HelpApplyVO;

/**
 * 助贷申请表-业务逻辑接口
 * @author liuliang
 * @version 2018-08-28
 */
public interface HelpApplyService {
	
	/**
	 * 获取待分配的助贷列表
	 * @return
	 */
	public List<HelpApplyVO> getHelpApplyList();
	
	/**
	 * 助贷产品分配定时任务
	 * @return
	 */
	public TaskResult borrowHelpAllot();

	/**
	 * 分页查询列表
	 * @param page
	 * @param op
	 * @return
	 */
	public Page<HelpApplyVO> getBorrowHelpList(@NotNull(message = "分页参数不能为空") Page page,HelpApplyForListOP op);
	
	public HelpApplyVO getHelpById(String id);
	
	/**
	 * code y0831
	* @Title: borrowHelpApply  
	* @Description: 助贷服务申请 
	* @param @param op
	* @return String    返回类型  
	 */
	String borrowHelpApply(HelpApplyOP op);
	
	void borrowHelpPaid(HelpApplyOP op);
	
	
	/**
	 * 申请了之后直接随机分配用户，返回营销员QQ
	 * @param borrowId
	 * @return
	 */
	public Map<String, Object> allotHelp(String borrowId,String idNo);
	
	/**
	 * 更新助贷申请表，分配的用户信息
	 * @param borrowId
	 * @param userId
	 * @param userName
	 * @return
	 */
	public int updateAllotApply(String borrowId,String userId,String userName,String updateBy);
}