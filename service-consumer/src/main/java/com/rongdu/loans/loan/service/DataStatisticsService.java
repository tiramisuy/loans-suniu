package com.rongdu.loans.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rongdu.loans.loan.vo.RepayDetailCountVo;
import com.rongdu.loans.loan.vo.SettlementChannelVo;
import com.rongdu.loans.loan.vo.XJDCountVo;

/**  
* @Title: DataSatisticsService.java  
* @Package com.waterelephant.statistics.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年8月28日  
* @version V1.0  
*/
public interface DataStatisticsService {

	List<Map<String, Object>> getRepayStatistic(String dateType, String productType);
	
	List<Map<String, Object>> getRepayRate(String dateType, String productType);

	List<XJDCountVo> getIndexCount(String thisDate);

	List<List<RepayDetailCountVo>> getRepayCountDetail(String startDate, String endDate);

	List<Map<String, Object>> getLastDayReg(String startDate, String channel);

	Map<String, Object> getThisDayPayCount(String date, String channel);

	BigDecimal getThisDayOverAmount(String thisDate, String channel);

	SettlementChannelVo getUserCost(String thisDate, String channel);

	Integer saveChannelCount(SettlementChannelVo entity);

	Integer updateChannelCount(SettlementChannelVo entity);

	Map<String, Object> getAverageReg(String channel);

	Map<String, Object> getReapyCountByPayDate(String thisDate);

}
