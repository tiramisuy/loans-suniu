/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.risk.entity.RiskWhitelist;
import com.rongdu.loans.risk.option.WhitelistOP;
import com.rongdu.loans.risk.vo.WhitelistVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风控白名单-数据访问接口
 * @author yuanxianchu
 * @version 2018-12-27
 */
@MyBatisDao
public interface RiskWhitelistDao extends BaseDao<RiskWhitelist,String> {

    long countByUserId(String userId);

    List<WhitelistVO> selectWhiteList(
            @Param("page") Page<WhitelistVO> page, @Param("op") WhitelistOP op);
	
}