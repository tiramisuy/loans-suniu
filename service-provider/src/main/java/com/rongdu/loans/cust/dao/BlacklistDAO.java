package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.Blacklist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@MyBatisDao
public interface BlacklistDAO extends BaseDao<Blacklist, String> {

    List<Blacklist> getBlacklistByUserIdList(@Param("userIdList") List<String> userIdList);
}
