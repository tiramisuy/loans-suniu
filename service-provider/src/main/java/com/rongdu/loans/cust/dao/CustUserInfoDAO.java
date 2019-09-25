package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.option.TFLBaseInfoOP;
import com.rongdu.loans.cust.vo.CustUserInfoVO;

/**
 * 用户基本信息DAO
 * Created by likang on 2017/6/27.
 */
@MyBatisDao
public interface CustUserInfoDAO extends BaseDao<CustUserInfo, String> {

	/**
	 * 更新基础信息
	 * @param entity
	 * @return
	 */
	int updateBaseinfo(CustUserInfo entity);
	
	/**
	 * 根据用户id更新基础信息
	 * @param entity
	 * @return
	 */
	int updateById(CustUserInfo entity);
	
    /**
     * 根据证件号统计记录条数
     * @param userId
     * @return
     */
    int countById(String idNo);
    
    /**
     * 获取基本信息
     * @param id
     * @return
     */
    CustUserInfoVO getSimpleById(String id);
    
    /*
	 * 获取投复利用户信息
	 */
	TFLBaseInfoOP getTFLUserInfo(String id);
}
