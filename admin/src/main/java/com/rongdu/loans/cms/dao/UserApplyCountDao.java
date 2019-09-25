/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cms.dao;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.vo.OperationalStatisticsVO;
import com.rongdu.loans.loan.vo.UserApplyCountVO;
import com.rongdu.loans.loan.vo.UserApplyRepayCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 运营数据统计DAO接口
 * @author wy
 */
@MyBatisDao
public interface UserApplyCountDao {

    UserApplyCountVO getUserApplyCount(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("channels") String channels, @Param("productId") String productId, @Param("termType") String termType);

    OperationalStatisticsVO getOperationalStatistics(@Param("applyOP") ApplyOP applyOP);

    UserApplyCountVO getUserCountByOffice(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("offices") String offices, @Param("productId") String productId, @Param("groupId") String groupId);

    List<Map<String, Object>> getOfficeListByArea(String areaId);

    List<Map<String, Object>> getGroupByOffice(String officeId);

    List<UserApplyRepayCountVO> getRepayCount(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    Double getDelaySum(@Param("applyOP") ApplyOP applyOP);

    List<Map<String, Object>> getOfficeListByProductId(String productId);

    UserApplyCountVO getProductCountByOffice(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("officeId") String officeId, @Param("productId") String productId, @Param("groupId") String groupId);

    List<Map<String, Object>> getThirtyRepayCount(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    List<Map<String, Object>> approverRepayCount(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    List<Map<String, Object>> approverOneRepayCount(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    List<Map<String, Object>> oneRepayCount(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    UserApplyCountVO getXjbkUserApplyCount(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("productId") String productId);

    List<UserApplyRepayCountVO> getOtherRepayStat(@Param("page") Page<UserApplyRepayCountVO> page, @Param("applyOP") ApplyOP applyOP);

    Double getKDPayAmt(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map<String, Object>> getCheckAllotList(@Param("auditorId") List<String> auditorId, @Param("applyOP") ApplyOP applyOP, @Param("type") String type);
}
