package com.rongdu.loans.cust.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.dao.CustUserDAO;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.option.BorrowerOP;
import com.rongdu.loans.cust.option.CustUserStatusOP;
import com.rongdu.loans.cust.option.UpdatePwdOP;
import com.rongdu.loans.cust.vo.BindInfoVO;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("custUserManager")
public class CustUserManager extends BaseManager<CustUserDAO, CustUser, String>{

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustUserDAO dao;

	/**
	 * 保存用户信息
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(CustUser entity) {
		return dao.insert(entity);
	}

	/**
	 * 根据userId查询用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public CustUser getById(String userId) {
		return dao.getById(userId);
	}

	/**
	 * 根据手机号查询用户信息
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	public CustUser getByMobile(String mobile) {
		return dao.getByMobile(mobile);
	}

	/**
	 * 根据手机号查询用户数
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	public int coutByMobile(String mobile) {
		return dao.coutByMobile(mobile);
	}
	
	/**
	 * 根据手机号MD5查询用户数
	 * 
	 * @param mobile MD5
	 *            手机号
	 * @return
	 */
	public int coutByMobileMD5(String mobile) {
		return dao.coutByMobileMD5(mobile);
	}
		

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public int updatePwd(UpdatePwdOP updatePwdOP) {
		return dao.updatePwd(updatePwdOP);
	}

	/**
	 * 修改用户信息
	 * 
	 * @return
	 */
	public int updateCustUser(CustUser entity) {
		return dao.update(entity);
	}

	/**
	 * 保存身份信息
	 * 
	 * @return
	 */
	public int updateIdentityInfo(CustUser entity) {
		return dao.updateIdentityInfo(entity);
	}

	/**
	 * 保存基础信息
	 * 
	 * @return
	 */
	public int updateBaseInfo(CustUser entity) {
		return dao.updateBaseInfo(entity);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param dto
	 * @return
	 */
	public List<CustUser> custUserList(BorrowerOP dto) {
		return dao.custUserList(dto);
	}

	/**
	 * 更新用户状态
	 * 
	 * @param dto
	 * @return
	 */
	public Boolean updateStatus(CustUserStatusOP dto) {
		return dao.updateStatus(dto);
	}

	/**
	 * 安全修改用户密码
	 * 
	 * @param updatePwdOP
	 * @return
	 */
	public int updatePwdFormSafe(UpdatePwdOP updatePwdOP) {
		if (null == updatePwdOP || StringUtils.isBlank(updatePwdOP.getAccount())
				|| StringUtils.isBlank(updatePwdOP.getPassword()) || StringUtils.isBlank(updatePwdOP.getOldPwd())) {
			logger.error("param is error!");
			return 0;
		}
		updatePwdOP.setUpdateTime(new Date());
		return dao.updatePwd(updatePwdOP);

	}

	/**
	 * 更新身份认证状态
	 * 
	 * @return
	 */
	public int updateIdentityStatus(CustUser entity) {
		return dao.updateIdentityInfo(entity);
	}

	/**
	 * 根据用户id查询身份认证状态
	 * 
	 * @return
	 */
	public Integer getIdentityStatus(String userId) {
		return dao.getIdentityStatus(userId);
	}

	/**
	 * 更新登录记录
	 * 
	 * @param entity
	 * @return
	 */
	public int updateloginRecord(CustUser entity) {
		return dao.updateloginRecord(entity);
	}

	/**
	 * 根据userId查询用户绑定相关信息
	 * 
	 * @param userId
	 * @return
	 */
	public BindInfoVO getBindInfoById(String userId) {
		return dao.getBindInfoById(userId);
	}

	/**
	 * 更新绑卡信息
	 * 
	 * @param entity
	 * @return
	 */
	public int updateBindInfo(CustUser entity) {
		return dao.updateBindInfo(entity);
	}

	/**
	 * 根据id查询用户列表
	 * 
	 * @param idList
	 * @return
	 */
	public List<CustUser> findByIdList(List<String> idList) {
		return dao.findByIdList(idList);
	}

	public List<CustUser> findByNameAndIdNo(String realName, String idNo) {
		return dao.findByNameAndIdNo(realName, idNo);
	}


	public List<CustUser> findByNameAndIdNoByYixin(String realName, String idNo) {
		return dao.findByNameAndIdNoByYixin(realName, idNo);
	}

	/**
	 * 根据用户id查询qq
	 * 
	 * @param userId
	 * @return
	 */
	public String getQq(@Param("userId") String userId) {
		return dao.getQq(userId);
	}

	/**
	 * 更新四合一授权id
	 * 
	 * @param entity
	 * @return
	 */
	public int updateTermsAuthId(CustUser entity) {
		return dao.updateTermsAuthId(entity);
	}

	public CustUser getByAccountId(String accountId) {
		return dao.getByAccountId(accountId);
	}

	/**
	 * @Title: countByIdNo
	 * @Description: 根据idNo查询用户数
	 * @param @param idNo
	 * @param @return 参数
	 * @return int 返回类型
	 */
	public int countByIdNo(String idNo) {
		return dao.countByIdNo(idNo);
	}

	/**
	 * 通过手机号查询客户贷款次数
	 */
	public String getLoanCountByMobile(String mobile) {
		return dao.getLoanCountByMobile(mobile);
	}

	public int updateEmail(String userId, String email) {
		return dao.updateEmail(userId, email);
	}


	public CustUser countByUserInfo(String userName, String userPhone, String userIdCard) {
		return dao.countByUserInfo(userName, userPhone, userIdCard);
	}
}
