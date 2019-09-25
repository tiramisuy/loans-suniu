/**
 * Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.option.BorrowerOP;
import com.rongdu.loans.cust.option.CustUserStatusOP;
import com.rongdu.loans.cust.option.UpdatePwdOP;
import com.rongdu.loans.cust.vo.BindInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消费用户DAO接口
 *
 * @author likang
 * @version 2017-06-12
 */
@MyBatisDao
public interface CustUserDAO extends BaseDao<CustUser, String> {

    /**
     * 根据userId查询用户信息
     *
     * @param userId
     *            用户id
     * @return
     */
    CustUser getById(@Param("userId") String userId);

    /**
     * 根据userId查询用户绑定相关信息
     *
     * @param userId
     * @return
     */
    BindInfoVO getBindInfoById(@Param("userId") String userId);

    /**
     * 根据用户id查询qq
     *
     * @param userId
     * @return
     */
    String getQq(@Param("userId") String userId);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile
     *            手机号
     * @return
     */
    CustUser getByMobile(String mobile);

    /**
     * 根据手机号查询用户数
     *
     * @param mobile
     *            手机号
     * @return
     */
    int coutByMobile(String mobile);

    /**
     * 根据手机号MD5查询用户数
     *
     * @param mobileMD5
     *            手机号
     * @return
     */
    int coutByMobileMD5(String mobile);


    /**
     * 更新登录记录
     *
     * @param entity
     * @return
     */
    int updateloginRecord(CustUser entity);

    /**
     * 修改密码
     *
     * @param mobile
     *            手机号
     * @param password
     *            新密码
     * @return
     */
    int updatePwd(UpdatePwdOP updatePwdOP);

    /**
     * 保存身份信息
     *
     * @return
     */
    int updateIdentityInfo(CustUser entity);

    /**
     * 更新身份认证状态
     *
     * @return
     */
    int updateIdentityStatus(CustUser entity);

    /**
     * 根据用户id查询身份认证状态
     *
     * @return
     */
    Integer getIdentityStatus(String userId);

    /**
     * 保存基础信息
     *
     * @return
     */
    int updateBaseInfo(CustUser entity);

    /**
     * 更新四合一授权id
     *
     * @param entity
     * @return
     */
    int updateTermsAuthId(CustUser entity);

    List<CustUser> custUserList(@Param("dto") BorrowerOP dto);

    Boolean updateStatus(CustUserStatusOP dto);

    /**
     * 更新绑卡信息
     *
     * @param entity
     * @return
     */
    int updateBindInfo(CustUser entity);

    /**
     * 根据id查询用户列表
     *
     * @param idList
     * @return
     */
    List<CustUser> findByIdList(@Param("idList") List<String> idList);

    List<CustUser> findByNameAndIdNo(@Param("realName") String trueName, @Param("idNo") String idNo);

    List<CustUser> findByNameAndIdNoByYixin(@Param("realName") String trueName, @Param("idNo") String idNo);

    CustUser getByAccountId(String accountId);

    /**
     * @Title: countByIdNo
     * @Description: 根据idNo查询用户数
     * @param @param idNo
     * @param @return 参数
     * @return int 返回类型
     */
    int countByIdNo(String idNo);

    String getLoanCountByMobile(String mobile);

    int updateEmail(@Param("userId") String userId, @Param("email") String email);

    CustUser countByUserInfo(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("userIdCard") String userIdCard);
}