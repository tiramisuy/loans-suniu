/**
 * Copyright 2015-2017 聚财 All rights reserved.
 */
package com.rongdu.loans.cust.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileType;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.IdcardUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.manager.AppBankLimitManager;
import com.rongdu.loans.basic.dao.CityDAO;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.Dict;
import com.rongdu.loans.cust.entity.*;
import com.rongdu.loans.cust.manager.*;
import com.rongdu.loans.cust.option.*;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.*;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.ContactHistorySaveOP;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.OverdueService;
import com.rongdu.loans.loan.vo.CheckLogVO;
import com.rongdu.loans.loan.vo.ContactToCollectionVO;
import com.rongdu.loans.risk.service.RiskService;
import com.rongdu.loans.risk.vo.HitRuleVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 消费用户Service实现
 *
 * @author likang
 * @version 2017-06-15
 */
@Service("custUserService")
public class CustUserServiceImpl extends BaseService implements CustUserService {

	@Autowired
	private CustUserManager custUserManager;
	@Autowired
	private CustUserInfoManager custUserInfoManager;
	@Autowired
	private ContactManager contactManager;
	@Autowired
	private IdentManager identManager;
	@Autowired
	private BlacklistManager blacklistManager;
	@Autowired
	private AppBankLimitManager appBankLimitManager;
	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private OperationLogManager operationLogManager;
	@Autowired
	private RefuseReasonManager refuseReasonManager;
	@Autowired
	private FileInfoManager fileInfoManager;
	@Autowired
	private UserInfoHistoryManager userInfoHistoryManager;
	@Autowired
	private ContactHistoryManager contactHistoryManager;
	@Autowired
	private AreaManager areaManager;
	@Autowired
	private RiskService riskService;
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired
	private OperationLogLaterManager operationLogLaterManager;
	@Autowired
	private OverdueService overdueService;
	@Autowired
	private CityDAO cityDao;

	/**
	 * 间隔符
	 */
	private static final String SPACE_MARK = "  ";

	public CustUserVO getCustUserByMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return null;
		}
		CustUser custUser = custUserManager.getByMobile(mobile);
		if (custUser == null) {
			return null;
		}
		return BeanMapper.map(custUser, CustUserVO.class);
	}

	@Transactional
	public String saveRegister(RegisterOP registerOP) {
		if (null == registerOP) {
			logger.error("参数异常");
			return "0";
		}
		CustUser user = new CustUser();
		// 用户手机号
		user.setMobile(registerOP.getAccount());
		// 用户密码
		user.setPassword(registerOP.getPassword());
		// 注册时间
		Date currentDate = new Date();
		user.setLoginTime(currentDate);
		// 当前登录时间
		user.setRegisterTime(currentDate);
		// 当前登录ip
		user.setLoginIp(registerOP.getIp());
		// 邀请码
		user.setInviteCode(registerOP.getInviteCode());
		// 门店id 存入remark字段
		user.setRemark(registerOP.getCompanyId());
		// 来源 (1-ios，2-android，3-H5，4-api，5-网站，6-system)
		if (StringUtils.isBlank(registerOP.getSource())) {
			user.setSource(SourceEnum.ANDROID.getValue());
		} else {
			try {
				Integer source = Integer.valueOf(registerOP.getSource());
				user.setSource(source);
			} catch (Exception e) {
				user.setSource(SourceEnum.ANDROID.getValue());
			}
		}
		// 注册渠道
		if (StringUtils.isBlank(registerOP.getChannel())) {
			user.setChannel(ChannelEnum.JUQIANBAO.getCode());
		} else {
			user.setChannel(registerOP.getChannel());
		}

		if (StringUtils.isNotBlank(registerOP.getRealName())) {
			user.setRealName(registerOP.getRealName());
		}

		// 数据有效性状态 默认有效
		user.setStatus(CustUserInfo.USER_STATUS_UNLOCK);
		user.preInsert();
		int rz = custUserManager.insert(user);
		if (rz == 1) {
			return user.getId();
		}
		return "0";
	}

	@Override
	public int updateLoginRecord(LoginOP loginOP) {
		if (null == loginOP) {
			logger.error("参数异常");
			return 0;
		}
		Date loginTime = new Date();
		// 更新逾期还款用户登陆记录
		overdueService.updateLastLoginTimeByUserId(loginOP.getUserId(), loginTime);

		CustUser entity = new CustUser();
		entity.setMobile(loginOP.getAccount());
		entity.setLastLoginIp(loginOP.getLastIp());
		entity.setLoginIp(loginOP.getCurrentIp());
		entity.setLastLoginTime(loginOP.getLastLoginTime());
		entity.setLoginNum(loginOP.getLoginNum());
		entity.setLoginTime(loginTime);
		entity.preUpdate();
		return custUserManager.updateloginRecord(entity);
	}

	public boolean isRegister(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		return custUserManager.coutByMobile(mobile) > 0;
	}

	public boolean isRegisterByMobileMD5(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		return custUserManager.coutByMobileMD5(mobile) > 0;
	}

	public int updatePwd(UpdatePwdOP updatePwdOP) {
		if (null == updatePwdOP || StringUtils.isBlank(updatePwdOP.getAccount())
				|| StringUtils.isBlank(updatePwdOP.getPassword())) {
			logger.error("param is error!");
			return 0;
		} else {
			updatePwdOP.setUpdateTime(new Date());
			return custUserManager.updatePwd(updatePwdOP);
		}
	}

	@Override
	@Transactional
	public int saveIdentityInfo(IdentityInfoOP identityInfoOP) {
		if (null == identityInfoOP) {
			logger.error("param is error!");
			return 0;
		}
		// 构造返回值
		int rz = 0;
		// 设置CustUser对象
		CustUser entity = new CustUser();
		// 银行编号
		entity.setBankCode(identityInfoOP.getBankCode());
		// 真实姓名
		entity.setRealName(identityInfoOP.getTrueName());
		// 用户昵称
		entity.setName(identityInfoOP.getUname());
		// 银行卡号
		entity.setCardNo(identityInfoOP.getCardNo());
		// 开户行地址
		entity.setBankCityId(identityInfoOP.getCityId());
		// 银行预留手机号
		if (StringUtils.isNotBlank(identityInfoOP.getBankMobile())) {
			entity.setBankMobile(identityInfoOP.getBankMobile());
		}
		// 邮箱
		entity.setEmail(identityInfoOP.getEmail());
		// 证件类型
		entity.setIdType(Global.DEFAULT_ID_TYPE);
		// entity.setIdType(identityInfoOP.getIdType());
		// if (null == identityInfoOP.getIdType()) {
		// entity.setIdType(Global.DEFAULT_ID_TYPE);
		// }
		// 证件号
		entity.setIdNo(identityInfoOP.getIdNo());
		// 用户id
		entity.setId(identityInfoOP.getUserId());
		// 江西银行电子账户
		entity.setAccountId(identityInfoOP.getAccountId());
		// 第三方支付绑定id
		entity.setBindId(identityInfoOP.getBindId());
		entity.setProtocolNo(identityInfoOP.getProtocolNo());
		// 身份认证状态-认证完成
		entity.setIdentityStatus(Global.IDENTITY_STATUS_OK);
		entity.preUpdate();
		// 更新身份信息
		rz = custUserManager.updateIdentityInfo(entity);
		if (rz == 1) {
			// 更新申请状态，保存操作记录
			/*
			 * LoanApply loanApply = new LoanApply();
			 * loanApply.setId(identityInfoOP.getApplyId());
			 * loanApply.setUserId(identityInfoOP.getUserId());
			 * loanApply.setStage(XjdLifeCycle.LC_ID);
			 * loanApply.setStatus(XjdLifeCycle.LC_ID_1);
			 * loanApply.setUserName(identityInfoOP.getTrueName());
			 * loanApply.setIdNo(identityInfoOP.getIdNo());
			 * loanApply.setSource(identityInfoOP.getSource()); rz =
			 * loanApplyManager.updateStatusAndSaveLog(loanApply);
			 */

			// 保存操作记录
			OperationLog operationLog = new OperationLog();
			operationLog.setUserId(identityInfoOP.getUserId());
			operationLog.setStage(XjdLifeCycle.LC_ID);
			operationLog.setStatus(XjdLifeCycle.LC_ID_1);
			if (null != identityInfoOP.getSource()) {
				operationLog.setSource(Integer.parseInt(identityInfoOP.getSource()));
			} else {
				operationLog.setSource(Global.DEFAULT_SOURCE);
			}
			operationLog.defOperatorIdAndName();
			operationLog.preInsert();
			rz = loanApplyManager.saveOperationLog(operationLog);
		}
		return rz;
	}

	@Override
	@Transactional
	public int saveBaseInfo(BaseInfoOP op) {
		long t1 = System.currentTimeMillis();
		// 参数判断
		if (null == op) {
			logger.error("param is error!");
			return 0;
		}
		// 保存用户基本信息
		CustUserInfo info = new CustUserInfo();
		if (StringUtils.isBlank(op.getIdType()) && StringUtils.isNotBlank(op.getIdNo())) {
			info.setIdType(Global.DEFAULT_ID_TYPE);
		}
		info.setId(op.getUserId());
		info.setUserName(op.getUserName());
		info.setIdType(op.getIdType());
		info.setIdNo(op.getIdNo());
		info.setMobile(op.getMobile());
		info.setComName(op.getComName());
		info.setWorkAddr(op.getWorkAddr());
		info.setWorkProvince(op.getWorkProvince());
		info.setWorkCity(op.getWorkCity());
		info.setWorkDistrict(op.getWorkDistrict());
		info.setWorkPosition(op.getWorkPosition());
		info.setComTelExt(op.getComTelExt());
		info.setComTelNo(op.getComTelNo());
		info.setComTelZone(op.getComTelZone());
		info.setResideAddr(op.getResideAddr());
		info.setResideProvince(op.getResideProvince());
		info.setResideCity(op.getResideCity());
		info.setResideDistrict(op.getResideDistrict());
		info.setResideYear(op.getResideYear());
		info.setMarital(op.getMarital());
		info.setDegree(op.getDegree());
		info.setStatus(CustUserInfo.USER_STATUS_UNLOCK);
		info.preUpdate();
		// 父母居住地址
		info.setParentProvince(op.getParentProvince());
		info.setParentCity(op.getParentCity());
		info.setParentDistrict(op.getParentDistrict());
		info.setParentAddress(op.getParentAddress());
		long t21 = System.currentTimeMillis();
		logger.info("custUserInfoDao.insert spend times :[{}]", t21 - t1);
		int baseInfoRz = custUserInfoManager.updateBaseinfo(info);
		logger.debug("the result of custUserInfoDao.insert :[{}]", baseInfoRz);
		long t22 = System.currentTimeMillis();
		logger.info("custUserInfoDao.insert spend times :[{}]", t22 - t1);
		if (baseInfoRz == 1) {
			long t3 = System.currentTimeMillis();
			// 清理之前的联系人
			contactManager.delContact(op.getUserId());
			// 父母
			int parent = splitContact(op.getContactParent(), op.getUserId());
			logger.debug("the result of insert contact parent  :[{}]", parent);
			// 母亲
			int mother = splitContact(op.getContactMother(), op.getUserId());
			logger.debug("the result of insert contact mother  :[{}]", mother);
			// 配偶
			int spouse = splitContact(op.getContactSpouse(), op.getUserId());
			logger.debug("the result of insert contact spouse  :[{}]", spouse);
			// 朋友
			int friend = splitContact(op.getContactFriend(), op.getUserId());
			logger.debug("the result of insert contact friend  :[{}]", friend);
			// 同事
			int colleague = splitContact(op.getContactColleague(), op.getUserId());
			logger.debug("the result of insert contact colleague  :[{}]", colleague);
			long t4 = System.currentTimeMillis();
			logger.info("contact.insert spend times :[{}]", t4 - t3);
			// 联系人保存结果汇总
			int contact = parent + spouse + friend + colleague;
			// 如果qq号不为空，更新到custUser数据中
			if (null != op.getQq() || null != op.getEmail()) {
				CustUser custUser = new CustUser();
				custUser.setQq(op.getQq());
				custUser.setEmail(op.getEmail());
				custUser.setId(op.getUserId());
				custUser.preUpdate();
				custUser.setChannel(op.getChannelId());
				if ("XJBK".equals(op.getChannelId())) { // 现金白卡渠道客户更新注册时间
					custUser.setRegisterTime(new Date());
				}
				int userRz = custUserManager.updateBaseInfo(custUser);
				logger.debug("the result of dao.updateBaseInfo :[{}]", userRz);
				long t5 = System.currentTimeMillis();
				logger.info("dao.updateBaseInfo spend times :[{}]", t5 - t3);
			}
			// 日志记录与状态更新结果
			int logRz = 0;
			if (contact > 0) {
				/** 保存操作记录数据 */
				OperationLog operationLog = new OperationLog();
				operationLog.setUserId(op.getUserId());
				operationLog.setStage(XjdLifeCycle.LC_BASE);
				operationLog.setStatus(XjdLifeCycle.LC_BASE_1);
				if (null != op.getSource()) {
					operationLog.setSource(Integer.parseInt(op.getSource()));
				} else {
					operationLog.setSource(Global.DEFAULT_SOURCE);
				}
				operationLog.defOperatorIdAndName();
				operationLog.preInsert();
				logRz = loanApplyManager.saveOperationLog(operationLog);
			}
			if (baseInfoRz > 0 && contact > 0 && logRz > 0) {
				long t6 = System.currentTimeMillis();
				logger.info("saveBaseInfo spend times :[{}]", t6 - t1);
				return 1;
			}
		}
		long t7 = System.currentTimeMillis();
		logger.info("saveBaseInfo spend times :[{}]", t7 - t1);
		return 0;
	}

	@Override
	@Transactional
	public int saveTFLBaseInfo(TFLBaseInfoOP op) {
		long t1 = System.currentTimeMillis();
		// 参数判断
		if (null == op) {
			logger.error("param is error!");
			return 0;
		}
		// 保存用户基本信息
		CustUserInfo info = new CustUserInfo();
		if (StringUtils.isBlank(op.getIdType()) && StringUtils.isNotBlank(op.getIdNo())) {
			info.setIdType(Global.DEFAULT_ID_TYPE);
		}
		// 基本信息
		info.setId(op.getUserId());
		info.setUserName(op.getUserName());
		info.setIdType(op.getIdType());
		info.setIdNo(op.getIdNo());
		info.setMobile(op.getMobile());
		// 个人信息
		info.setResideAddr(op.getResideAddr());
		info.setResideProvince(op.getResideProvince());
		info.setResideCity(op.getResideCity());
		info.setResideDistrict(op.getResideDistrict());
		info.setMarital(op.getMarital());
		info.setDegree(op.getDegree());

		// 工作信息
		info.setWorkAddr(op.getWorkAddr());
		info.setWorkProvince(op.getWorkProvince());
		info.setWorkCity(op.getWorkCity());
		info.setWorkDistrict(op.getWorkDistrict());
		info.setWorkPosition(op.getWorkPosition());
		info.setWorkYear(op.getWorkYear());
		info.setIndivMonthIncome(op.getIndivMonthIncome());
		info.setIndustry(op.getIndustry());

		// 父母居住地址
		info.setParentProvince(op.getParentProvince());
		info.setParentCity(op.getParentCity());
		info.setParentDistrict(op.getParentDistrict());
		info.setParentAddress(op.getParentAddress());

		Map<String, String> map = new HashMap<String, String>();
		map.put("workCategory", op.getWorkCategory());
		map.put("workSize", op.getWorkSize());
		map.put("house", op.getHouse());
		map.put("houseLoan", op.getHouseLoan());
		map.put("car", op.getCar());
		map.put("carLoan", op.getCarLoan());
		info.setRemark(JsonMapper.toJsonString(map));

		info.setStatus(CustUserInfo.USER_STATUS_UNLOCK);
		info.preUpdate();

		long t21 = System.currentTimeMillis();
		logger.info("custUserInfoDao.insert spend times :[{}]", t21 - t1);
		int baseInfoRz = custUserInfoManager.updateBaseinfo(info);
		logger.debug("the result of custUserInfoDao.insert :[{}]", baseInfoRz);
		long t22 = System.currentTimeMillis();
		logger.info("custUserInfoDao.insert spend times :[{}]", t22 - t1);
		if (baseInfoRz == 1) {
			long t3 = System.currentTimeMillis();
			// 清理之前的联系人
			contactManager.delContact(op.getUserId());
			// 父母
			int parent = splitContact(op.getContactParent(), op.getUserId());
			logger.debug("the result of insert contact parent  :[{}]", parent);
			// 配偶
			int spouse = splitContact(op.getContactSpouse(), op.getUserId());
			logger.debug("the result of insert contact spouse  :[{}]", spouse);
			// 朋友
			int friend = splitContact(op.getContactFriend(), op.getUserId());
			logger.debug("the result of insert contact friend  :[{}]", friend);
			// 同事
			int colleague = splitContact(op.getContactColleague(), op.getUserId());
			logger.debug("the result of insert contact colleague  :[{}]", colleague);
			long t4 = System.currentTimeMillis();
			logger.info("contact.insert spend times :[{}]", t4 - t3);
			// 联系人保存结果汇总
			int contact = parent + spouse + friend + colleague;
			// 如果邮箱不为空
			if (null != op.getEmail()) {
				CustUser custUser = new CustUser();
				custUser.setEmail(op.getEmail());
				custUser.setId(op.getUserId());
				custUser.preUpdate();
				int userRz = custUserManager.updateBaseInfo(custUser);
				logger.debug("the result of dao.updateBaseInfo :[{}]", userRz);
				long t5 = System.currentTimeMillis();
				logger.info("dao.updateBaseInfo spend times :[{}]", t5 - t3);
			}
			// 日志记录与状态更新结果
			int logRz = 0;
			if (contact > 0) {
				/** 保存操作记录数据 */
				OperationLog operationLog = new OperationLog();
				operationLog.setUserId(op.getUserId());
				operationLog.setStage(XjdLifeCycle.LC_BASE);
				operationLog.setStatus(XjdLifeCycle.LC_BASE_1);
				if (null != op.getSource()) {
					operationLog.setSource(Integer.parseInt(op.getSource()));
				} else {
					operationLog.setSource(Global.DEFAULT_SOURCE);
				}
				operationLog.defOperatorIdAndName();
				operationLog.preInsert();
				logRz = loanApplyManager.saveOperationLog(operationLog);
			}
			if (baseInfoRz > 0 && logRz > 0 && contact > 0) {
				long t6 = System.currentTimeMillis();
				logger.info("saveBaseInfo spend times :[{}]", t6 - t1);
				return 1;
			}
		}
		long t7 = System.currentTimeMillis();
		logger.info("saveBaseInfo spend times :[{}]", t7 - t1);
		return 0;
	}

	@Transactional
	int splitContact(String contactStr, String userId) {
		if (StringUtils.isNotBlank(contactStr)) {
			String[] temp = contactStr.trim().split(",");
			if (temp.length < 3) {
				// 联系人信息不全的情况不更新入库
				logger.debug("[{}]:联系人信息不全", contactStr);
				return 0;
			}
			Contact contact = new Contact();
			contact.setUserId(userId);
			// 关系
			try {
				Integer relationship = Integer.parseInt(temp[0]);
				contact.setRelationship(relationship);
			} catch (Exception e) {
				logger.error("relationship [{}] is error", temp[0]);
				return 0;
			}
			if (StringUtils.isNotBlank(temp[1]) && StringUtils.isNotBlank(temp[2])) {
				// 名称
				contact.setName(temp[1]);
				// 手机号
				contact.setMobile(temp[2]);
				contact.preInsert();
				return contactManager.insert(contact);
			}
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<BorrowerVO> custUserList(BorrowerOP dto) {

		Page voPage = new Page(dto.getPageNo(), dto.getPageSize());
		dto.setPage(voPage);
		List<CustUser> custUserList = custUserManager.custUserList(dto);

		if (CollectionUtils.isEmpty(custUserList)) {
			voPage.setList(Collections.emptyList());
			return voPage;
		}
		List<BorrowerVO> borrowerVOList = BeanMapper.mapList(custUserList, BorrowerVO.class);
		/** 设置黑名单 */
		// setBlacklist(borrowerVOList);
		// /** 设置银行名称 */
		setBankName(borrowerVOList);
		voPage.setList(borrowerVOList);
		return voPage;
	}

	private void setBlacklist(List<BorrowerVO> borrowerVOList) {
		List<String> userIdList = new ArrayList<>();
		for (BorrowerVO vo : borrowerVOList) {
			userIdList.add(vo.getId());
		}
		List<Blacklist> blacklistList = blacklistManager.getBlacklistByUserIdList(userIdList);
		if (CollectionUtils.isNotEmpty(blacklistList)) {
			for (BorrowerVO vo : borrowerVOList) {
				for (Blacklist black : blacklistList) {
					if (StringUtils.equals(vo.getId(), black.getUserId())) {
						vo.setBlacklist(1);
					}
				}
			}
		}
	}

	private void setBankName(List<BorrowerVO> borrowerVOList) {
		for (BorrowerVO vo : borrowerVOList) {
			vo.setBankName(appBankLimitManager.getBankName(vo.getBankCode()));
		}
	}

	public Boolean updateStatus(CustUserStatusOP dto) {
		return custUserManager.updateStatus(dto);
	}

	public List<FileInfoVO> getFileinfo(String userId) {
		/** 组装 图片&影像 */
		List<String> bizCodeList = Arrays.asList(FileBizCode.FRONT_IDCARD.getBizCode(),
				FileBizCode.BACK_IDCARD.getBizCode(), FileBizCode.FACE_VERIFY.getBizCode(),
				FileBizCode.ENTERPRISE_LICENSE.getBizCode());
		List<FileInfo> fileInfoList = fileInfoManager.getLastFileByUserId(userId, bizCodeList);
		if (CollectionUtils.isNotEmpty(fileInfoList)) {
			return BeanMapper.mapList(fileInfoList, FileInfoVO.class);
		}
		return Collections.emptyList();
	}

	public List<FileInfoVO> getAllContactFileinfo(String userId) {
		List<FileInfo> fileInfoList = fileInfoManager.getAllFile(userId, FileBizCode.DEVICE_CONTACT.getBizCode(),
				FileType.DOC.getType());
		if (CollectionUtils.isNotEmpty(fileInfoList)) {
			return BeanMapper.mapList(fileInfoList, FileInfoVO.class);
		}
		return null;
	}

	public List<FileInfoVO> getCustContactFile(String userId) {
		String bizCode = FileBizCode.CONTACT_FILE.getBizCode();
		List<FileInfo> fileInfoList = fileInfoManager.getContactFileList(userId, bizCode);
		if (CollectionUtils.isNotEmpty(fileInfoList)) {
			return BeanMapper.mapList(fileInfoList, FileInfoVO.class);
		}
		return Collections.emptyList();
	}

	public FileInfoVO getEnterpriseLicenseFileInfo(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.ENTERPRISE_LICENSE.getBizCode(),
				FileType.IMG.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public FileInfoVO getLastReportByApplyId(String applyId) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(applyId, FileBizCode.BAIQISHI_REPORT.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public FileInfoVO getLastContactInfoByApplyId(String applyId) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(applyId, FileBizCode.DEVICE_CONTACT.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public FileInfoVO getLastEmailReportByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.MOXIE_EMAIL_REPORT.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public FileInfoVO getLastBankReportByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.MOXIE_BANK_REPORT.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public CardVO getCardinfo(String userId) {
		CustUser custUser = custUserManager.getById(userId);
		CardVO cardVO = BeanMapper.map(custUser, CardVO.class);
		if (StringUtils.isNotBlank(cardVO.getBankCode())) {
			cardVO.setBankName(appBankLimitManager.getBankName(cardVO.getBankCode()));
		}
		return cardVO;
	}

	@Override
	public QueryUserVO custUserDetail(QueryUserOP queryUserOP) {
		QueryUserVO vo = new QueryUserVO();
		/** 组装 客户信息 */
		UserInfoVO userInfoVO = getUserInfo(queryUserOP.getId(), queryUserOP.getApplyId(), queryUserOP.getSnapshot());
		vo.setUserInfoVO(userInfoVO);
		/** 组装 贷款订单审核记录 */
		vo.setCheckLogVOList(assembleCheckLogVOList(queryUserOP.getApplyId()));

		/** 审核页面需要组装 命中规则记录 */
		if (queryUserOP.getSnapshot()) {
			List<HitRuleVO> hitRuleVOList = riskService.getHitRuleList(queryUserOP.getApplyId(), null);
			if (CollectionUtils.isNotEmpty(hitRuleVOList)) {
				Collections.sort(hitRuleVOList, new Comparator<HitRuleVO>() {
					@Override
					public int compare(HitRuleVO o1, HitRuleVO o2) {
						String rank1 = o1.getRiskRank();
						String rank2 = o2.getRiskRank();
						int r1 = StringUtils.isNotBlank(rank1) ? RiskRankEnum.get(rank1).getSort() : 100;
						int r2 = StringUtils.isNotBlank(rank2) ? RiskRankEnum.get(rank2).getSort() : 100;
						return r1 - r2;
					}
				});
				group(vo, hitRuleVOList);
			}
		}

		return vo;
	}

	/**
	 * 将命中规则进行分组
	 *
	 * @param juqianbao
	 * @param juqianbaoBlackList
	 * @param third
	 * @param thirdBlackList
	 * @param hitRuleVOList
	 */
	private void group(QueryUserVO vo, List<HitRuleVO> hitRuleVOList) {
		vo.setJuqianbao(new ArrayList<CustHitRuleVO>());
		vo.setJuqianbaoBlackList(new ArrayList<CustHitRuleVO>());
		vo.setThird(new ArrayList<CustHitRuleVO>());
		vo.setThirdBlackList(new ArrayList<CustHitRuleVO>());

		Map<String, List<CustHitRuleVO>> thirdMap = new LinkedHashMap<String, List<CustHitRuleVO>>();
		for (RiskSourceEnum source : RiskSourceEnum.values()) {
			if (!RiskSourceEnum.JUCAI.getId().equals(source.getId())) {
				thirdMap.put(source.getId(), new ArrayList<CustHitRuleVO>());
			}
		}
		for (HitRuleVO hitRuleVO : hitRuleVOList) {
			CustHitRuleVO custHitRuleVO = BeanMapper.map(hitRuleVO, CustHitRuleVO.class);
			custHitRuleVO.setName(RiskSourceEnum.get(hitRuleVO.getSource()).getName());
			custHitRuleVO.setRiskRank(StringUtils.isNotBlank(hitRuleVO.getRiskRank())
					? RiskRankEnum.get(hitRuleVO.getRiskRank()).getName()
					: "");
			String source = hitRuleVO.getSource();
			String riskRank = hitRuleVO.getRiskRank();
			// 进件大纲列表
			if (RiskSourceEnum.JUCAI.getId().equals(source) && !"A".equals(riskRank)) {
				vo.getJuqianbao().add(custHitRuleVO);
				continue;
			}
			// 黑名单列表
			if (RiskSourceEnum.JUCAI.getId().equals(source) && "A".equals(riskRank)) {
				vo.getJuqianbaoBlackList().add(custHitRuleVO);
				continue;
			}
			// 三方黑名单列表
			if (!RiskSourceEnum.JUCAI.getId().equals(source) && "A".equals(riskRank)) {
				vo.getThirdBlackList().add(custHitRuleVO);
				continue;
			}
			// 三方数据
			List<CustHitRuleVO> tmpThird = thirdMap.get(source);
			custHitRuleVO.setSort(tmpThird.size() + 1);
			tmpThird.add(custHitRuleVO);

		}
		for (String key : thirdMap.keySet()) {
			List<CustHitRuleVO> tmpThird = thirdMap.get(key);
			if (tmpThird.size() > 3) {
				tmpThird.get(tmpThird.size() - 1).setSort(-1);
				vo.setMore(1);
			}
			vo.getThird().addAll(tmpThird);
		}
	}

	@Override
	public UserInfoVO getUserInfo(String userId, String applyId, Boolean snapshot) {
		Integer count = null;
		Integer loanSuccCount = null;
		/** 用户数据 */
		CustUser custUser = custUserManager.getById(userId);
		if (custUser == null) {
			return new UserInfoVO();
		}
		/** 用户详情数据 */
		CustUserInfo custUserInfo = null;
		/** 联系人数据 */
		List<CustContactVO> contactList = null;
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("user_id", userId));
		criteria.and(Criterion.eq("del", 0));

		if (snapshot) {
			UserInfoHistory history = userInfoHistoryManager.getByApplyId(applyId);
			if (history == null) {
				return new UserInfoVO();
			}
			Criteria contactHistoryCriteria = new Criteria();
			contactHistoryCriteria.add(Criterion.eq("apply_id", applyId));
			contactHistoryCriteria.and(Criterion.eq("del", 0));
			List<ContactHistory> historyList = contactHistoryManager.findAllByCriteria(contactHistoryCriteria);
			if (CollectionUtils.isNotEmpty(historyList)) {
				contactList = BeanMapper.mapList(historyList, CustContactVO.class);
			}
			if (history != null) {
				custUserInfo = BeanMapper.map(history, CustUserInfo.class);
				custUserInfo.setId(history.getUserId());
				count = history.getApplyCount();
				loanSuccCount = history.getLoanSuccCount();
			}
		} else {
			custUserInfo = custUserInfoManager.getById(userId);
			/** 贷款申请次数 */
			count = loanApplyManager.countApply(userId);
			/** 贷款成功次数 */
			loanSuccCount = loanApplyManager.countOverLoanByRepay(userId);
			/** 联系人数据 */
			List<Contact> contacts = contactManager.findAllByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(contacts)) {
				contactList = BeanMapper.mapList(contacts, CustContactVO.class);
			}
		}
		/** 用户证件数据 */
		Ident ident = identManager.getByCriteria(criteria);
		/** 黑名单数据 */
		Blacklist blacklist = blacklistManager.getById(userId);
		/** 借款信息 **/
		List<RepayItemDetailVO> repayItemDetailList = loanRepayPlanService.findUserRepayList(userId);
		/** 影像信息 **/
		List<FileInfoVO> fileList = getFileinfo(userId);

		/** 组装 客户信息 */
		return assembleUserInfoVO(custUser, custUserInfo, ident, blacklist, contactList, count, loanSuccCount,
				repayItemDetailList, fileList);
	}

	private UserInfoVO assembleUserInfoVO(CustUser custUser, CustUserInfo custUserInfo, Ident ident,
			Blacklist blacklist, List<CustContactVO> contactList, Integer count, Integer loanSuccCount,
			List<RepayItemDetailVO> repayItemDetailList, List<FileInfoVO> fileList) {
		/** 组装 custUserInfo 信息 */
		UserInfoVO vo = BeanMapper.map(custUserInfo, UserInfoVO.class);
		vo.setRealName(custUserInfo.getUserName());
		if (StringUtils.isNotBlank(vo.getDegree())) {
			vo.setDegree(DegreeEnum.getDesc(Integer.valueOf(vo.getDegree())));
		}
		if (StringUtils.isNotBlank(custUserInfo.getRemark())) {
			Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(custUserInfo.getRemark(),
					Map.class);
			vo.setWorkCategory(Dict.jobCompanyType.get(map.get("workCategory")));
			vo.setWorkSize(Dict.jobCompanyScale.get(map.get("workSize")));
			vo.setHouse(Dict.haveHouse.get(map.get("house")));
			vo.setHouseLoan(Dict.houseLoan.get(map.get("houseLoan")));
			vo.setCar(Dict.haveCar.get(map.get("car")));
			vo.setCarLoan(Dict.carLoan.get(map.get("carLoan")));
		}
		vo.setDegree(Dict.education.get(custUserInfo.getDegree()));
		vo.setIndivMonthIncome(Dict.jobSalary.get(custUserInfo.getIndivMonthIncome() + ""));
		vo.setIndustry(Dict.jobIndustry.get(custUserInfo.getIndustry()));
		vo.setWorkYear(Dict.jobYears.get(custUserInfo.getWorkYear()));
		vo.setMarital(MaritalEnum.getDesc(custUserInfo.getMarital()));
		Map<String, String> areaMap = areaManager.getAreaCodeAndName();
		vo.setWorkProvince(areaMap.get(vo.getWorkProvince()));
		vo.setWorkCity(areaMap.get(vo.getWorkCity()));
		vo.setWorkDistrict(areaMap.get(vo.getWorkDistrict()));
		vo.setResideProvince(areaMap.get(vo.getResideProvince()));
		vo.setResideCity(areaMap.get(vo.getResideCity()));
		vo.setResideDistrict(areaMap.get(vo.getResideDistrict()));
		vo.setAge(IdcardUtils.getAgeByIdCard(vo.getIdNo()));
		vo.setParentProvince(areaMap.get(vo.getParentProvince()));
		vo.setParentCity(areaMap.get(vo.getParentCity()));
		vo.setParentDistrict(areaMap.get(vo.getParentDistrict()));

		/** 组装 custUser 信息 */
		vo.setBirthday(custUser.getBirthday());
		vo.setQq(custUser.getQq());
		vo.setAccountId(custUser.getAccountId());
		vo.setCreateTime(custUser.getCreateTime());
		vo.setUpdateTime(custUser.getUpdateTime());
		vo.setStatus(custUser.getStatus());
		vo.setType(UserTypeEnum.getName(custUser.getType()));
		vo.setEmail(custUser.getEmail());
		vo.setChannelName(custUser.getChannel());

		/** 组装 contactList 信息 */
		vo.setContactList(contactList);

		/** 组装 ident 信息 */
		if (ident != null) {
			vo.setIdTermBegin(ident.getIdTermBegin());
			vo.setIdTermEnd(ident.getIdTermEnd());
			vo.setIdRegOrg(ident.getIdRegOrg());
		}

		/** 组装 blacklist 信息 */
		vo.setBlacklist(blacklist == null ? 0 : 1);
		/** 申请贷款次数 */
		vo.setCount(count);
		/** 贷款成功次数 */
		vo.setLoanSuccCount(loanSuccCount);
		/** 借款信息 **/
		vo.setRepayItemDetailList(repayItemDetailList);
		/** 影像信息 **/
		vo.setFileList(fileList);
		return vo;
	}

	/**
	 * 组装贷款审核记录数据
	 *
	 * @param applyId
	 * @return
	 */
	private List<CheckLogVO> assembleCheckLogVOList(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return Collections.emptyList();
		}
		List<OperationLogLater> operationLogList = operationLogLaterManager.getCheckLog(applyId);
		if (CollectionUtils.isEmpty(operationLogList)) {
			return Collections.emptyList();
		}
		List<CheckLogVO> checkLogVOList = BeanMapper.mapList(operationLogList, CheckLogVO.class);
		// todo List<RefuseReason>做成缓存后优化该方法
		/** 获取拒绝原因 */
		Criteria criteria = new Criteria();
		List<RefuseReason> list = refuseReasonManager.findAllByCriteria(criteria);
		if (CollectionUtils.isNotEmpty(list)) {
			for (CheckLogVO vo : checkLogVOList) {
				if (StringUtils.isNotBlank(vo.getRefuseId())) {
					String refuse = "";
					for (RefuseReason reason : list) {
						// 如果是二级原因，先获取一级原因
						if (vo.getRefuseId().equalsIgnoreCase(reason.getId())) {
							// if (reason.getLevel().equals(Global.LEVEL_2)) {
							// for (RefuseReason item : list) {
							// if
							// (reason.getPid().equalsIgnoreCase(item.getId()))
							// {
							// refuse = refuse + item.getReason();
							// break;
							// }
							// }
							// }
							// refuse = refuse + ", " + reason.getReason();
							refuse = refuse + reason.getReason();
							vo.setRefuse(refuse);
							break;
						}
					}
				}

			}
		}
		return checkLogVOList;
	}

	@Transactional
	public int saveDoOcr(OcrOP ocrOp) {
		if (null == ocrOp || null == ocrOp.getIdcard()) {
			logger.error("param ocrOp is null!");
			return 0;
		}
		// 默认返回值
		int rz = 0;
		// 来自ocr接口返回数据入库
		// 更新用户表
		CustUser custUser = new CustUser();
		custUser.setRealName(ocrOp.getName());
		custUser.setIdNo(ocrOp.getIdcard());
		custUser.setIdType(Global.DEFAULT_ID_TYPE);
		custUser.setId(ocrOp.getUserId());
		custUser.setSex(SexEnum.getValue(ocrOp.getSex()));
		custUser.setBirthday(IdcardUtils.getBirthByIdCard(ocrOp.getIdcard()));
		custUser.setIdentityStatus(Global.IDENTITY_STATUS_UN);
		custUser.preUpdate();
		rz = custUserManager.updateIdentityInfo(custUser);
		logger.info("ocr, updateIdentityInfo result[{}]", rz);
		if (rz == 1) {
			// 更新用户信息表
			CustUserInfo info = new CustUserInfo();
			// 用户id
			info.setId(ocrOp.getUserId());
			// 用户名称
			info.setUserName(ocrOp.getName());
			// 用户性别
			info.setSex(SexEnum.getValue(ocrOp.getSex()));
			// 用户民族
			info.setNation(ocrOp.getNation());
			// 用户证件类型
			info.setIdType(Global.DEFAULT_ID_TYPE);
			// 用户证件号
			info.setIdNo(ocrOp.getIdcard());
			// 户籍地址
			info.setRegAddr(ocrOp.getAddress());
			// 获取省、市、区编号
			Map<String, String> codeMap = IdcardUtils.getAddrCodeByIdCard(ocrOp.getIdcard());
			if (null != codeMap) {
				// 户籍省
				info.setRegProvince(codeMap.get(IdcardUtils.PROVINCE_CODE_KEY));
				// 户籍市
				info.setRegCity(codeMap.get(IdcardUtils.CITY_CODE_KEY));
				// 户籍区县
				info.setRegDistrict(codeMap.get(IdcardUtils.DISTRICT_CODE_KEY));
			}
			// 用户状态
			info.setStatus(CustUserInfo.USER_STATUS_UNLOCK);
			rz = custUserInfoManager.saveOrUpdateBaseinfo(info);
			logger.info("ocr, saveOrUpdateBaseinfo result[{}]", rz);
			if (rz == 1) {
				// 保存证件信息
				Ident entity = new Ident();
				entity.setUserId(ocrOp.getUserId());
				entity.setIdType(Global.DEFAULT_ID_TYPE_INT);
				entity.setIdNo(ocrOp.getIdcard());
				// 证件所属国家
				entity.setIdCtry(Global.DEFAULT_ID_COUNTRY);
				// 分割证件有效期
				if (null != ocrOp.getValidDate()) {
					String[] temp = ocrOp.getValidDate().split(Global.ID_VALID_DATE_HYPHEN);
					if (temp.length > 1) {
						entity.setIdTermBegin(StringUtils.hyphenReplacePeriod(temp[0]));
						entity.setIdTermEnd(StringUtils.hyphenReplacePeriod(temp[1]));
						if (StringUtils.equals(temp[1], Global.ID_VALID_DATE_LONG)) {
							entity.setIdTermLong(Ident.ID_TERM_LONG_YES);
						} else {
							entity.setIdTermLong(Ident.ID_TERM_LONG_NO);
						}
					}
				}
				// 发证机关
				entity.setIdRegOrg(ocrOp.getAuthority());
				rz = identManager.saveOrUpdateIdent(entity);
				logger.info("ocr, saveOrUpdateIdent result[{}]", rz);
				if (rz == 1) {
					/** 保存操作记录数据 */
					OperationLog operationLog = new OperationLog();
					operationLog.setUserId(ocrOp.getUserId());
					operationLog.setStage(XjdLifeCycle.LC_OCR);
					operationLog.setStatus(XjdLifeCycle.LC_OCR_1);
					operationLog.setIp(ocrOp.getIp());
					if (null != ocrOp.getSource()) {
						operationLog.setSource(Integer.parseInt(ocrOp.getSource()));
					} else {
						operationLog.setSource(Global.DEFAULT_SOURCE);
					}
					operationLog.defOperatorIdAndName();
					operationLog.preInsert();
					rz = loanApplyManager.saveOperationLog(operationLog);
					logger.info("ocr, updateStatusAndSaveLog result[{}]", rz);
				}
			}
		}
		return rz;
	}

	@Override
	public int saveDoFaceRecognition(FaceRecogOP faceOp) {
		if (null == faceOp) {
			logger.error("param faceOp is null!");
			return 0;
		}
		int rz = 0;
		/** 保存操作记录数据 */
		OperationLog operationLog = new OperationLog();
		operationLog.setUserId(faceOp.getUserId());
		operationLog.setStage(XjdLifeCycle.LC_FACE);
		operationLog.setStatus(XjdLifeCycle.LC_FACE_1);
		operationLog.setIp(faceOp.getIp());
		if (null != faceOp.getSource()) {
			operationLog.setSource(Integer.parseInt(faceOp.getSource()));
		} else {
			operationLog.setSource(Global.DEFAULT_SOURCE);
		}
		operationLog.defOperatorIdAndName();
		operationLog.preInsert();
		rz = loanApplyManager.saveOperationLog(operationLog);
		return rz;
	}

	@Override
	public CustUserVO getCustUserById(String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.error("param userId is null!");
			return null;
		}
		CustUser custUser = custUserManager.getById(userId);
		if (custUser == null) {
			return null;
		}
		return BeanMapper.map(custUser, CustUserVO.class);
	}

	@Override
	public BindInfoVO getBindInfoById(String userId) {
		// 参数判断
		if (StringUtils.isBlank(userId)) {
			logger.error("param userId is null!");
			return null;
		}
		// 查询绑定信息
		// BindInfoVO bindInfoVO = custUserManager.getBindInfoById(userId);
		// if (null != bindInfoVO) {
		// return bindInfoVO;
		// }
		BindInfoVO bindInfoVO = null;
		// 查询用户信息
		CustUser custUser = custUserManager.getById(userId);
		if (null != custUser) {
			// 对象赋值
			bindInfoVO = BeanMapper.map(custUser, BindInfoVO.class);
			bindInfoVO.setUserId(custUser.getId());
			bindInfoVO.setBankName(BankCodeEnum.getName(custUser.getBankCode()));
			if (StringUtils.isNotBlank(custUser.getBankCityId()) && !"(null)".equals(custUser.getBankCityId())) {
				bindInfoVO.setBankCityId(custUser.getBankCityId());
				bindInfoVO.setBankCityName(cityDao.getById(Integer.valueOf(custUser.getBankCityId())));
			}
		}
		return bindInfoVO;
	}

	@Override
	@Transactional
	public int saveDoSesameCredit(SesameCreditOP sesameCreditOP) {
		if (null == sesameCreditOP) {
			logger.error("param sesameCreditOP is null!");
			return 0;
		}
		int rz = 0;
		// 保存操作记录
		OperationLog operationLog = new OperationLog();
		operationLog.setUserId(sesameCreditOP.getUserId());
		operationLog.setStage(XjdLifeCycle.LC_SESAME);
		if (sesameCreditOP.isAuthorized()) {
			operationLog.setStatus(XjdLifeCycle.LC_SESAME_1);
			StringBuilder bf = new StringBuilder();
			bf.append("{zmScore:").append(sesameCreditOP.getZmScore()).append("; flowNo:")
					.append(sesameCreditOP.getFlowNo()).append("; bizNo:").append(sesameCreditOP.getBizNo())
					.append("}");
			operationLog.setRemark(bf.toString());
		} else {
			operationLog.setStatus(XjdLifeCycle.LC_SESAME_0);
		}
		if (null != sesameCreditOP.getSource()) {
			operationLog.setSource(Integer.parseInt(sesameCreditOP.getSource()));
		} else {
			operationLog.setSource(Global.DEFAULT_SOURCE);
		}
		operationLog.setIp(sesameCreditOP.getIp());
		operationLog.defOperatorIdAndName();
		operationLog.preInsert();
		rz = loanApplyManager.saveOperationLog(operationLog);
		return rz;
	}

	@Override
	public int saveDoTelOperator(TelOperatorOP telOperatorOP) {
		if (null == telOperatorOP) {
			logger.error("param telOperatorOP is null!");
			return 0;
		}
		int rz = 0;
		// 保存操作记录
		OperationLog operationLog = new OperationLog();
		operationLog.setUserId(telOperatorOP.getUserId());
		operationLog.setStage(XjdLifeCycle.LC_TEL);
		if (StringUtils.equals(TelOperatorOP.DO_OPERATOR_SUCC, telOperatorOP.getDoOperatorResult())) {
			operationLog.setStatus(XjdLifeCycle.LC_TEL_1);
		} else {
			operationLog.setStatus(XjdLifeCycle.LC_TEL_0);
		}
		if (null != telOperatorOP.getSource()) {
			operationLog.setSource(Integer.parseInt(telOperatorOP.getSource()));
		} else {
			operationLog.setSource(Global.DEFAULT_SOURCE);
		}
		operationLog.setIp(telOperatorOP.getIp());
		operationLog.defOperatorIdAndName();
		operationLog.preInsert();
		rz = loanApplyManager.saveOperationLog(operationLog);
		return rz;
	}

	@Override
	public int updateBindInfo(CustUserVO user) {
		if (user != null) {
			CustUser entity = BeanMapper.map(user, CustUser.class);
			entity.setUpdateBy(user.getId());
			entity.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
			return custUserManager.updateBindInfo(entity);
		}
		return 0;
	}

	@Override
	public CustUserInfoVO getSimpleUserInfo(String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.debug("param userId is null!");
		} else {
			// 获取当前基本信息
			CustUserInfoVO custUserInfoVO = new CustUserInfoVO();
			custUserInfoVO = custUserInfoManager.getSimpleById(userId);
			// 获取地区列表
			Map<String, String> areaMap = areaManager.getAreaCodeAndName();
			if (null != areaMap) {
				// 居住省份
				if (StringUtils.isNotBlank(custUserInfoVO.getResideProvince())) {
					custUserInfoVO.setResideProvinceName(areaMap.get(custUserInfoVO.getResideProvince()));
				}
				// 居住市
				if (StringUtils.isNotBlank(custUserInfoVO.getResideCity())) {
					custUserInfoVO.setResideCityName(areaMap.get(custUserInfoVO.getResideCity()));
				}
				// 居住地区
				if (StringUtils.isNotBlank(custUserInfoVO.getResideDistrict())) {
					custUserInfoVO.setResideDistrictName(areaMap.get(custUserInfoVO.getResideDistrict()));
				}

				// 工作省份
				if (StringUtils.isNotBlank(custUserInfoVO.getWorkProvince())) {
					custUserInfoVO.setWorkProvinceName(areaMap.get(custUserInfoVO.getWorkProvince()));
				}
				// 工作市
				if (StringUtils.isNotBlank(custUserInfoVO.getWorkCity())) {
					custUserInfoVO.setWorkCityName(areaMap.get(custUserInfoVO.getWorkCity()));
				}
				// 工作地区
				if (StringUtils.isNotBlank(custUserInfoVO.getWorkDistrict())) {
					custUserInfoVO.setWorkDistrictName(areaMap.get(custUserInfoVO.getWorkDistrict()));
				}

				// 父母省份
				if (StringUtils.isNotBlank(custUserInfoVO.getParentProvince())) {
					custUserInfoVO.setParentProvinceName(areaMap.get(custUserInfoVO.getParentProvince()));
				}
				// 父母市
				if (StringUtils.isNotBlank(custUserInfoVO.getParentCity())) {
					custUserInfoVO.setParentCityName(areaMap.get(custUserInfoVO.getParentCity()));
				}
				// 父母地区
				if (StringUtils.isNotBlank(custUserInfoVO.getParentDistrict())) {
					custUserInfoVO.setParentDistrictName(areaMap.get(custUserInfoVO.getParentDistrict()));
				}
			}
			// 获取qq号
			CustUser custUser = custUserManager.getById(userId);
			if (null != custUser) {
				if (null != custUser.getQq()) {
					custUserInfoVO.setQq(custUser.getQq());
				}
				if (null != custUser.getEmail()) {
					custUserInfoVO.setEmail(custUser.getEmail());
				}
			}
			// 获取当前联系人信息
			List<CustContactVO> list = contactManager.getByUserId(userId);
			if (null == list || list.size() == 0) {
				// 初始值设置
				list = new ArrayList<CustContactVO>();
				CustContactVO temp = new CustContactVO();
				list.add(temp);
			}
			custUserInfoVO.setContactList(list);
			return custUserInfoVO;
		}
		return null;
	}

	@Override
	public List<ContactToCollectionVO> getContactHisByApplyNo(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			logger.error("param applyId is null!");
			return null;
		}
		// 初始化返回对象
		List<ContactToCollectionVO> clist = new LinkedList<ContactToCollectionVO>();
		// 查询本人信息
		LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
		if (null == loanApply) {
			logger.error("there is no apply info by [{}]", applyId);
			return null;
		}
		// 查询联系人
		List<ContactHistory> list = contactHistoryManager.getContactHisByApplyNo(applyId);
		if (null != list && list.size() > 0) {
			ContactToCollectionVO ccVo = null;
			StringBuilder bf = null;
			for (ContactHistory temp : list) {
				if (null != temp) {
					ccVo = new ContactToCollectionVO();
					ccVo.setId(temp.getId());
					ccVo.setApplyId(applyId);
					ccVo.setUserId(temp.getUserId());
					ccVo.setMobile(temp.getMobile());
					ccVo.setName(temp.getName());
					ccVo.setRelationship(temp.getRelationship());
					ccVo.setRelationshipStr(RelationshipEnum.getDesc(temp.getRelationship()));
					ccVo.setSource(temp.getSource());
					bf = new StringBuilder();
					bf.append(ccVo.getRelationshipStr()).append(SPACE_MARK).append(ccVo.getName()).append(SPACE_MARK)
							.append(ccVo.getMobile());
					if (StringUtils.equals(temp.getSource(), ContactHistory.SOURCE_COL)) {
						bf.append(SPACE_MARK).append(ContactHistory.SOURCE_COL_DESC);
					}
					ccVo.setDetail(bf.toString());
					clist.add(ccVo);
				}
			}
		}
		// 本人信息设置
		ContactToCollectionVO self = new ContactToCollectionVO();
		self.setId(loanApply.getUserId());
		self.setApplyId(applyId);
		self.setUserId(loanApply.getUserId());
		self.setName(loanApply.getUserName());
		self.setMobile(loanApply.getMobile());
		self.setRelationship(RelationshipEnum.SELF.getValue());
		self.setRelationshipStr(RelationshipEnum.SELF.getDesc());
		self.setSource(ContactHistory.SOURCE_DEF);
		StringBuilder selfDetail = new StringBuilder();
		selfDetail.append(self.getRelationshipStr()).append(SPACE_MARK).append(self.getName()).append(SPACE_MARK)
				.append(self.getMobile());
		self.setDetail(selfDetail.toString());
		((LinkedList<ContactToCollectionVO>) clist).addFirst(self);
		return clist;
	}

	@Override
	public int saveContactHistory(ContactHistorySaveOP contactHistoryOP) {
		// 初始化参数
		ContactHistory entity = BeanMapper.map(contactHistoryOP, ContactHistory.class);
		return contactHistoryManager.insert(entity);
	}

	@Override
	public int delContactHisById(String id) {
		if (StringUtils.isBlank(id)) {
			logger.error("联系人id不正确");
			return 0;
		}
		return contactHistoryManager.deleteFromCollection(id);
	}

	@Override
	public int updateCustUser(CustUserVO user) {
		CustUser custUser = new CustUser();
		BeanMapper.copy(user, custUser);
		custUser.preUpdate();
		return custUserManager.updateCustUser(custUser);
	}

	@Override
	public TFLBaseInfoOP getTFLUserInfo(String userid) {
		if (StringUtils.isBlank(userid)) {
			logger.debug("param userId is null!");
		} else {
			// 获取当前基本信息
			TFLBaseInfoOP tFLBaseInfoOP = new TFLBaseInfoOP();
			tFLBaseInfoOP = custUserInfoManager.getTFLUserInfo(userid);
			if (null != tFLBaseInfoOP) {
				if (null != tFLBaseInfoOP.getRemark()) {
					Map<String, String> resultMap = (Map<String, String>) JsonMapper
							.fromJsonString(tFLBaseInfoOP.getRemark(), Map.class);
					tFLBaseInfoOP.setCar(resultMap.get("car"));
					tFLBaseInfoOP.setCarLoan(resultMap.get("carLoan"));
					tFLBaseInfoOP.setHouse(resultMap.get("house"));
					tFLBaseInfoOP.setHouseLoan(resultMap.get("houseLoan"));
					tFLBaseInfoOP.setWorkCategory(resultMap.get("workCategory"));
					tFLBaseInfoOP.setWorkSize(resultMap.get("workSize"));
				}
				// 获取qq号
				CustUser custUser = custUserManager.getById(userid);
				if (null != custUser) {
					if (null != custUser.getEmail()) {
						tFLBaseInfoOP.setEmail(custUser.getEmail());
					}
				}
				// 获取地区列表
				Map<String, String> areaMap = areaManager.getAreaCodeAndName();
				if (null != areaMap) {
					// 居住
					String resideAll = areaMap.get(tFLBaseInfoOP.getResideProvince()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getResideCity()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getResideDistrict());
					tFLBaseInfoOP.setResideAll(resideAll);
					// 工作
					String workAll = areaMap.get(tFLBaseInfoOP.getWorkProvince()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getWorkCity()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getWorkDistrict());
					tFLBaseInfoOP.setWorkAll(workAll);
					// 父母
					String parentAddrAll = areaMap.get(tFLBaseInfoOP.getParentProvince()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getParentCity()) + "-"
							+ areaMap.get(tFLBaseInfoOP.getParentDistrict());
					tFLBaseInfoOP.setParentAddrAll(parentAddrAll);
				}
				// 获取当前联系人信息
				List<CustContactVO> list = contactManager.getByUserId(userid);
				if (null != list) {
					for (CustContactVO custContactVO : list) {
						if (custContactVO.getRelationship() == 1) {
							String contactParent = custContactVO.getRelationship() + "," + custContactVO.getName() + ","
									+ custContactVO.getMobile();
							tFLBaseInfoOP.setContactParent(contactParent);
						}
						if (custContactVO.getRelationship() == 2) {
							String contactSpouse = custContactVO.getRelationship() + "," + custContactVO.getName() + ","
									+ custContactVO.getMobile();
							tFLBaseInfoOP.setContactSpouse(contactSpouse);
						}
						if (custContactVO.getRelationship() == 3) {
							String contactFriend = custContactVO.getRelationship() + "," + custContactVO.getName() + ","
									+ custContactVO.getMobile();
							tFLBaseInfoOP.setContactFriend(contactFriend);
						}
						if (custContactVO.getRelationship() == 4) {
							String contactColleague = custContactVO.getRelationship() + "," + custContactVO.getName()
									+ "," + custContactVO.getMobile();
							tFLBaseInfoOP.setContactColleague(contactColleague);
						}
					}
				}
				return tFLBaseInfoOP;
			}
		}
		return null;
	}

	@Override
	public int saveDoSignOperator(String uid) {
		CustUser vo = custUserManager.getByAccountId(uid);
		if (vo != null) {
			/** 保存操作记录数据 */
			OperationLog operationLog = new OperationLog();
			operationLog.setUserId(vo.getId());
			operationLog.setStage(XjdLifeCycle.LC_TFL_1);
			operationLog.setStatus(XjdLifeCycle.LC_TFL_1);
			operationLog.setSource(Global.DEFAULT_SOURCE);
			operationLog.defOperatorIdAndName();
			operationLog.preInsert();
			return loanApplyManager.saveOperationLog(operationLog);
		}
		return 0;
	}

	@Override
	public int saveDoDepositOperator(String uid) {
		CustUser vo = custUserManager.getByAccountId(uid);
		if (vo != null) {
			/** 保存操作记录数据 */
			OperationLog operationLog = new OperationLog();
			operationLog.setUserId(vo.getId());
			operationLog.setStage(XjdLifeCycle.LC_DEPOSIT_1);
			operationLog.setStatus(XjdLifeCycle.LC_DEPOSIT_1);
			operationLog.setSource(Global.DEFAULT_SOURCE);
			operationLog.defOperatorIdAndName();
			operationLog.preInsert();
			return loanApplyManager.saveOperationLog(operationLog);
		}
		return 0;
	}

	@Override
	public int deleteTruely(String id) {
		return fileInfoManager.deleteTruely(id);
	}

	@Override
	public boolean isRepeatByIdNo(String idNo) {
		if (StringUtils.isBlank(idNo)) {
			logger.error("参数异常，身份证号不能为空！！！");
			return true;
		}
		return custUserManager.countByIdNo(idNo) == 0 ? false : true;
	}

	@Override
	public String getLoanCountByMobile(String mobile) {
		return custUserManager.getLoanCountByMobile(mobile);
	}

	@Override
	public int updateEmail(String userId, String email) {
		return custUserManager.updateEmail(userId, email);
	}

	@Override
	public CustUserVO isRegister(String userName, String userPhone, String userIdCard) {
		CustUser vo = custUserManager.countByUserInfo(userName, userPhone, userIdCard);
		if (vo == null) {
			return null;
		}
		return BeanMapper.map(vo, CustUserVO.class);
	}

	public FileInfoVO getLastXianJinCardBaseByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.XJBK_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	public FileInfoVO getLastXianJinCardAdditionalByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.XJBK_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastBaseDataByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.XJBK_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastAdditionalDataByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.XJBK_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongBaseByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.RONG_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongAdditionalByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.RONG_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongBaseByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.RONG_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongAdditionalByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.RONG_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongTJReportDetailByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.RONGTJ_REPORT_DETAIL.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastSLLAdditionalByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.SLL_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastSLLReportByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.SLL_REPORT_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastSLLBaseByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.SLL_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastRongTJReportDetailByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.RONGTJ_REPORT_DETAIL.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public int insertBlickList(CustUserVO custUser) {
		Blacklist entity = new Blacklist();
		entity.setId(IdGen.uuid());
		entity.setUserId(custUser.getId());
		entity.setIdType(custUser.getIdType());
		entity.setIdNo(custUser.getIdNo());
		entity.setUserName(custUser.getRealName());
		/**
		 * 0-网贷行业公布的黑名单 1-在平台有欠息或有不良贷款 2-提供虚假材料或拒绝接受检查 3-由恶意或严重逃废债记录
		 */
		entity.setBlackType("1");
		/**
		 * 0－系统内不宜贷款户（系统生成） 1－系统内不宜贷款户（人工审批） 2－ 系统外不宜贷款户'
		 */
		entity.setSource("1");
		// private BigDecimal overduePeriod; // '贷款逾期期数'
		entity.setBlackTime(DateUtils.getDateTime()); // blackTime; // '进出时间'
		entity.setReason("人工加入黑名单"); // reason; // '进出黑名单原因'
		// inOutFlag; // '进出标志'
		/**
		 * 0-预登记 1-生效 2-否决 3-注销
		 */
		entity.setStatus("2");

		return blacklistManager.insert(entity);
	}

	@Override
	public FileInfoVO getLastJDQBaseByOrderSn(String jdqOrderId, String bizCode) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(jdqOrderId, bizCode,
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastJDQReportByOrderSn(String jdqOrderId) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(jdqOrderId, FileBizCode.JDQ_REPORT_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastDWDBaseByOrderSn(String orderNo) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderNo, FileBizCode.DWD_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastDWDAdditionalByOrderSn(String orderSn) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderSn, FileBizCode.DWD_ADDITIONAL_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastDWDReportByOrderSn(String orderNo) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderNo, FileBizCode.DWD_REPORT_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public FileInfoVO getLastDWDChargeInfoByOrderSn(String orderNo) {
		FileInfo fileInfo = fileInfoManager.getLastFileByApplyId(orderNo,
				FileBizCode.DWD_CHARGE_DATA.getBizCode(), FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}

	@Override
	public UserInfoVO getUserInfoByMobile(String mobile) {

		/** 用户数据 */
		CustUser custUser = custUserManager.getByMobile(mobile);
		if (custUser == null) {
			return new UserInfoVO();
		}
		/** 用户详情数据 */
		CustUserInfo custUserInfo = null;

		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("user_id", custUser.getId()));
		criteria.and(Criterion.eq("del", 0));

		custUserInfo = custUserInfoManager.getById(custUser.getId());

		/** 用户证件数据 */
		Ident ident = identManager.getByCriteria(criteria);

		/** 组装 custUserInfo 信息 */
		UserInfoVO vo = BeanMapper.map(custUserInfo, UserInfoVO.class);
		vo.setRealName(custUserInfo.getUserName());
		if (StringUtils.isNotBlank(vo.getDegree())) {
			vo.setDegree(DegreeEnum.getDesc(Integer.valueOf(vo.getDegree())));
		}
		if (StringUtils.isNotBlank(custUserInfo.getRemark())) {
			Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(custUserInfo.getRemark(),
					Map.class);
			vo.setWorkCategory(Dict.jobCompanyType.get(map.get("workCategory")));
			vo.setWorkSize(Dict.jobCompanyScale.get(map.get("workSize")));
			vo.setHouse(Dict.haveHouse.get(map.get("house")));
			vo.setHouseLoan(Dict.houseLoan.get(map.get("houseLoan")));
			vo.setCar(Dict.haveCar.get(map.get("car")));
			vo.setCarLoan(Dict.carLoan.get(map.get("carLoan")));
		}
		vo.setDegree(Dict.education.get(custUserInfo.getDegree()));
		vo.setIndivMonthIncome(Dict.jobSalary.get(custUserInfo.getIndivMonthIncome() + ""));
		vo.setIndustry(Dict.jobIndustry.get(custUserInfo.getIndustry()));
		vo.setWorkYear(Dict.jobYears.get(custUserInfo.getWorkYear()));
		vo.setMarital(MaritalEnum.getDesc(custUserInfo.getMarital()));
		Map<String, String> areaMap = areaManager.getAreaCodeAndName();
		vo.setWorkProvince(areaMap.get(vo.getWorkProvince()));
		vo.setWorkCity(areaMap.get(vo.getWorkCity()));
		vo.setWorkDistrict(areaMap.get(vo.getWorkDistrict()));
		vo.setResideProvince(areaMap.get(vo.getResideProvince()));
		vo.setResideCity(areaMap.get(vo.getResideCity()));
		vo.setResideDistrict(areaMap.get(vo.getResideDistrict()));
		vo.setAge(IdcardUtils.getAgeByIdCard(vo.getIdNo()));
		vo.setParentProvince(areaMap.get(vo.getParentProvince()));
		vo.setParentCity(areaMap.get(vo.getParentCity()));
		vo.setParentDistrict(areaMap.get(vo.getParentDistrict()));

		/** 组装 custUser 信息 */
		vo.setBirthday(custUser.getBirthday());
		vo.setQq(custUser.getQq());
		vo.setAccountId(custUser.getAccountId());
		vo.setCreateTime(custUser.getCreateTime());
		vo.setUpdateTime(custUser.getUpdateTime());
		vo.setStatus(custUser.getStatus());
		vo.setType(UserTypeEnum.getName(custUser.getType()));
		vo.setEmail(custUser.getEmail());
		vo.setChannelName(custUser.getChannel());

		vo.setCardNo(custUser.getCardNo());
		vo.setBankCode(custUser.getBankCode());
		if (StringUtils.isNotBlank(vo.getBankCode())) {
			vo.setBankName(BankCodeEnum.getName(vo.getBankCode()));
		}

		/** 组装 ident 信息 */
		if (ident != null) {
			vo.setIdTermBegin(ident.getIdTermBegin());
			vo.setIdTermEnd(ident.getIdTermEnd());
			vo.setIdRegOrg(ident.getIdRegOrg());
		}

		return vo;
	}

	public int countContractNum(Map map){
		return contactManager.countNum(map);
	}

	@Override
	public FileInfoVO getLastJDQBaseByUserId(String userId) {
		FileInfo fileInfo = fileInfoManager.getLastFile(userId, FileBizCode.JDQ_BASE_DATA.getBizCode(),
				FileType.DOC.getType());
		if (fileInfo != null)
			return BeanMapper.map(fileInfo, FileInfoVO.class);
		return null;
	}
}