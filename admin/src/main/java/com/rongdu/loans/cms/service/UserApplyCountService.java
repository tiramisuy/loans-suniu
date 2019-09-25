/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cms.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cms.dao.UserApplyCountDao;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.vo.OperationalStatisticsVO;
import com.rongdu.loans.loan.vo.UserApplyCountVO;
import com.rongdu.loans.loan.vo.UserApplyRepayCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 运营统计Service
 * @author wy
 */
@Service
public class UserApplyCountService {
	
	@Autowired
	private UserApplyCountDao userApplyCountDao;
	
	public UserApplyCountVO getUserApplyCount(String startDate, String endDate, String channels, String productId,String termType){
		return userApplyCountDao.getUserApplyCount(startDate, endDate, channels, productId,termType);
	}

	/**
	 * 运营统计
	 * @param applyOP
	 * @return
	 */
	public OperationalStatisticsVO getOperationalStatistics(ApplyOP applyOP){
		return userApplyCountDao.getOperationalStatistics(applyOP);
	}

	public UserApplyCountVO getUserCountByOffice(String startDate, String endDate, String officeId, String productId, String groupId){
		return userApplyCountDao.getUserCountByOffice(startDate, endDate, officeId, productId, groupId);
	}

	public List<Map<String, Object>> getOfficeListByArea(String areaId){
		return userApplyCountDao.getOfficeListByArea(areaId);
	}
	
	public List<Map<String, Object>> getGroupByOffice(String officeId){
		return userApplyCountDao.getGroupByOffice(officeId);
	}
	
	/**
	* @Title: getRepayCount  
	* @Description: 现金贷还款统计 code y2316
	* @param @param startDate
	* @param @param endDate
	* @param @param channels
	* @return Page<UserApplyRepayCountVO>
	 */
	public Page<UserApplyRepayCountVO> getRepayCount(ApplyOP op) {
		Page<UserApplyRepayCountVO> page = new Page<UserApplyRepayCountVO>(op.getPageNo(),op.getPageSize());
		List<UserApplyRepayCountVO> list = userApplyCountDao.getRepayCount(page, op);
		for (UserApplyRepayCountVO vo : list) {
			if (StringUtils.isBlank(vo.getTotalNum())) {
				continue;
			}
			BigDecimal totalNum = new BigDecimal(vo.getTotalNum());
			if (StringUtils.isBlank(vo.getPayedNum())) {
				vo.setPayedNum("0");
			}
			BigDecimal payedNum = new BigDecimal(vo.getPayedNum());
			vo.setPayedRate(payedNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
			if (StringUtils.isBlank(vo.getUnpayNum())) {
				vo.setUnpayNum("0");
			}
			BigDecimal unpayNum = new BigDecimal(vo.getUnpayNum());
			vo.setUnpayRate(unpayNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
			if (StringUtils.isBlank(vo.getDelayNum())) {
				vo.setDelayNum("0");
			}
			BigDecimal delayNum = new BigDecimal(vo.getDelayNum());
			vo.setDelayRate(delayNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
		}
		page.setList(list);
		return page;
	}
	
	public Double getDelaySum(ApplyOP op) {
		return userApplyCountDao.getDelaySum(op);
	}
	
	public List<Map<String, Object>> getOfficeListByProductId(String productId){
		return userApplyCountDao.getOfficeListByProductId(productId);
	}
	

	public UserApplyCountVO getProductCountByOffice(String startDate, String endDate, String officeId, String productId, String groupId){
		return userApplyCountDao.getProductCountByOffice(startDate, endDate, officeId, productId, groupId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getThirtyRepayCount(ApplyOP applyOP) {
		Page page = new Page(applyOP.getPageNo(),applyOP.getPageSize());
		List<Map<String, Object>> list = null;
		if ("one".equals(applyOP.getTermType()) || "ones".equals(applyOP.getTermType())) {
			list = userApplyCountDao.oneRepayCount(page, applyOP);
		} else {
			list = userApplyCountDao.getThirtyRepayCount(page, applyOP);
		}
		page.setList(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page approverRepayCount(ApplyOP applyOP) {
		Page page = new Page(applyOP.getPageNo(),applyOP.getPageSize());
		List<Map<String, Object>> list = null;
		if ("one".equals(applyOP.getTermType())) {
			list = userApplyCountDao.approverOneRepayCount(page, applyOP);
		} else {
			list = userApplyCountDao.approverRepayCount(page, applyOP);
		}	
		page.setList(list);
		return page;
	}

	public UserApplyCountVO getXjbkUserApplyCount(String startDate, String endDate, String productId){
		return userApplyCountDao.getXjbkUserApplyCount(startDate, endDate, productId);
	}
	
	/**
	* @Title: getRepayCount  
	* @Description: 口袋还款统计 code y2316
	* @param @param startDate
	* @param @param endDate
	* @param @param channels
	* @return Page<UserApplyRepayCountVO>
	 */
	public Page<UserApplyRepayCountVO> getOtherRepayStat(ApplyOP op) {
		Page<UserApplyRepayCountVO> page = new Page<UserApplyRepayCountVO>(op.getPageNo(),op.getPageSize());
		List<UserApplyRepayCountVO> list = userApplyCountDao.getOtherRepayStat(page, op);
		for (UserApplyRepayCountVO vo : list) {
			if (StringUtils.isBlank(vo.getTotalNum())) {
				continue;
			}
			BigDecimal totalNum = new BigDecimal(vo.getTotalNum());
			if (StringUtils.isBlank(vo.getPayedNum())) {
				vo.setPayedNum("0");
			}
			BigDecimal payedNum = new BigDecimal(vo.getPayedNum());
			vo.setPayedRate(payedNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
			if (StringUtils.isBlank(vo.getUnpayNum())) {
				vo.setUnpayNum("0");
			}
			BigDecimal unpayNum = new BigDecimal(vo.getUnpayNum());
			vo.setUnpayRate(unpayNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
			if (StringUtils.isBlank(vo.getDelayNum())) {
				vo.setDelayNum("0");
			}
			BigDecimal delayNum = new BigDecimal(vo.getDelayNum());
			vo.setDelayRate(delayNum.divide(totalNum, 4, BigDecimal.ROUND_HALF_UP).toString());
		}
		page.setList(list);
		return page;
	}

	public Double getKDPayAmt(String startDate, String endDate) {
		return userApplyCountDao.getKDPayAmt(startDate, endDate);
	}

	public List<Map<String, Object>> getCheckAllotList(List<String> auditorId, ApplyOP applyOP, String type) {
		return userApplyCountDao.getCheckAllotList(auditorId, applyOP, type);
	}
}
