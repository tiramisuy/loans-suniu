/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.vo.BlacklistVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风控黑名单-数据访问接口
 *
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface BlacklistDao extends BaseDao<Blacklist, String> {
    public long findBlacklistCount(String userId);

    public long countInBlacklist(String userId);

    public List<BlacklistVO> selectBlackList(
            @Param("page") Page<BlacklistVO> page, @Param("op") BlacklistOP op);

    public long updateBlackById(@Param("id") String id, @Param("status") int status, @Param("updateBy") String updateBy);

    int inBlackList(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userIdCard") String userIdCard);

    public List<Blacklist> getALLBlackCust();

    List<Blacklist> getBlacklistByPhoneList(@Param("phoneList")List<String> phoneList);
}