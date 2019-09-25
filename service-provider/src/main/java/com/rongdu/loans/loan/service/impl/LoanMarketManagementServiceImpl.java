package com.rongdu.loans.loan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanMarketManagement;
import com.rongdu.loans.loan.entity.User;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanMarketManagementManager;
import com.rongdu.loans.loan.manager.MarketAllotManager;
import com.rongdu.loans.loan.manager.UserManager;
import com.rongdu.loans.loan.option.LoanMarketCountOP;
import com.rongdu.loans.loan.option.LoanMarketManagementOP;
import com.rongdu.loans.loan.option.MarketAllotUserOP;
import com.rongdu.loans.loan.service.LoanMarketManagementService;
import com.rongdu.loans.loan.vo.LoanMarketCountVO;
import com.rongdu.loans.loan.vo.LoanMarketManagementVO;
import com.rongdu.loans.loan.vo.MarketAllotVO;
import com.rongdu.loans.loan.vo.MarketManagementVO;

@Service("loanMarketManagementService")
public class LoanMarketManagementServiceImpl extends BaseService implements LoanMarketManagementService {

	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private LoanMarketManagementManager loanMarketManagementManager;
	@Autowired
	private MarketAllotManager marketAllotManager;

	@Override
	public TaskResult batchInsertMarketManagemetn() {
		logger.info("开始执行营销管理分配任务。");
		long starTime = System.currentTimeMillis();
		int success = 0;
		int fail = 0;

		/** 查询申请表中待推送的数据用于营销分配 */
		List<LoanApply> applyList = loanApplyManager.getLoanApplyForMarket();
		if (CollectionUtils.isEmpty(applyList)) {
			long endTime = System.currentTimeMillis();
			logger.info("执行营销管理分配任务结束，暂无可分配数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}

		/** 查询可被分配的用户 */
		
		//获取可分配用户 ，传到页面用于查询	 1为可分配；0为不可分配
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("allotFlag", 1);
		condition.put("role","kefu_dianxiao");
		
		 List<User> userList = userManager.getRoleUserByCondition(condition);
		if (CollectionUtils.isEmpty(userList)) {
			long endTime = System.currentTimeMillis();
			logger.info("没有可供分配的用户。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 平均分配
		int total = userList.size();
		int m = 0;
		List<LoanMarketManagement> marketList = new ArrayList<LoanMarketManagement>();
		for (LoanApply apply : applyList) {
			LoanMarketManagement market = new LoanMarketManagement();
			//判断申请此订单的人之前是否有被分配营销过，如果有本次直接分配给之前人员
			MarketAllotVO marketAllot = marketAllotManager.getAllotMarketByCustUserId(apply.getUserId());	
			if(marketAllot==null){
				market.setApplyId(apply.getId());
				market.setUserId(userList.get(m % total).getId());
				market.setUserName(userList.get(m % total).getName());
				market.setUpdateTime(new Date());
				market.setAllotDate(new Date());
				marketList.add(market);
				m++;
			}else{
				market.setApplyId(apply.getId());
				market.setUserId(marketAllot.getUserId());
				market.setUserName(marketAllot.getUserName());
				market.setUpdateTime(new Date());
				market.setAllotDate(new Date());
				marketList.add(market);
			}
			
		
		}
		if (!CollectionUtils.isEmpty(marketList)) {
			success = loanMarketManagementManager.insertBatch(marketList);
		}
		long endTime = System.currentTimeMillis();
		logger.info("执行营销管理分配任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<LoanMarketManagementVO> getMarketList(Page page, LoanMarketManagementOP op) {
		List<LoanMarketManagementVO> voList = loanMarketManagementManager.getMarketList(page, op);
		if (CollectionUtils.isEmpty(voList)) {
			page.setList(Collections.emptyList());
			return page;
		}
		for (LoanMarketManagementVO vo : voList) {
			String stuStr = ApplyStatusLifeCycleEnum.getDesc(Integer.parseInt(vo.getStatus()));
			vo.setStatusStr(stuStr);
		}

		page.setList(voList);
		return page;
	}
	
	
	
	@Override
	public List<LoanMarketCountVO> getMarketCountList( LoanMarketCountOP op) {
		List<LoanMarketCountVO> voList =loanMarketManagementManager.getMarketCountList( op);
		if (CollectionUtils.isEmpty(voList)) {
			return Collections.emptyList();
		}
		for (LoanMarketCountVO vo : voList) {
			if(vo.getAllNum() ==0 || vo.getOutNum()==0){
				vo.setOutPersent(new BigDecimal(0));
			}else{
				Double persent = (Double.valueOf(vo.getOutNum())/Double.valueOf(vo.getAllNum()))*100;
				vo.setOutPersent(new BigDecimal(persent).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			}
		}
		return voList;
	}
	
	@Override
	public List<LoanMarketManagementVO> findMarketList(LoanMarketManagementOP op){
		return loanMarketManagementManager.getMarketList(new Page<>(),op);
	}
	
	
	@Override
	public MarketManagementVO getMarketById(String id){
		return loanMarketManagementManager.getMarketById(id);
	}
	
	
	@Override
	public int updateMarket(MarketManagementVO repa){
	return loanMarketManagementManager.updateMarket(repa);
	}
	
	@Override
	public MarketManagementVO getMarketByApplyId(String applyId){
		return loanMarketManagementManager.getMarketByApplyId(applyId);
	}
	
	@Override
	public int allotInsert(String updateBy,List<String> ids,List<MarketAllotUserOP> userList){
		int success = 0;
		int a = 0;
		int total = userList.size();
		if(total > 0){
			
				List<LoanMarketManagement> marketList = new ArrayList<LoanMarketManagement>();
				if(!CollectionUtils.isEmpty(ids)){
					for(String id : ids){
						MarketAllotUserOP allotUser = userList.get(a);
						LoanMarketManagement market = new LoanMarketManagement();
						market.setApplyId(id);
						market.setUserId(allotUser.getUserId());
						market.setUserName(allotUser.getUserName());
						market.setAllotDate(new Date());
						market.setUpdateTime(new Date());
						market.setUpdateBy(updateBy);
						marketList.add(market);
						a++;
					}
				}
				if (!CollectionUtils.isEmpty(marketList)) {
					success = loanMarketManagementManager.insertBatch(marketList);
				}
		}
		return success;
	}
}
