/**
 *Copyright 2014-2017 www.jucai.com  All rights reserved.
 */
package com.rongdu.loans.cust.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.cust.option.*;
import com.rongdu.loans.cust.vo.*;
import com.rongdu.loans.loan.option.ContactHistorySaveOP;
import com.rongdu.loans.loan.vo.ContactToCollectionVO;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 消费用户Service
 * 
 * @author likang
 * @version 2017-06-15
 */
@Service
// @Transactional(readOnly = true)
public interface CustUserService {

	/**
	 * 通过手机号获取用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	CustUserVO getCustUserByMobile(String mobile);

	/**
	 * 通过用户id获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	CustUserVO getCustUserById(String userId);

	/**
	 * 根据userId查询用户绑定相关信息
	 * 
	 * @param userId
	 * @return
	 */
	BindInfoVO getBindInfoById(String userId);

	/**
	 * 保存注册信息
	 * 
	 * @param registerOP
	 * @return
	 */
	String saveRegister(RegisterOP registerOP);

	/**
	 * 更新登录记录
	 * 
	 * @param loginOP
	 * @return
	 */
	int updateLoginRecord(LoginOP loginOP);

	/**
	 * 当前用户是否注册
	 * 
	 * @param mobile
	 * @return [true] 已经注册过; [false] 未注册
	 */
	boolean isRegister(String mobile);

	/**
	 * 当前用户是否注册
	 * 
	 * @param mobileMD5
	 * @return [true] 已经注册过; [false] 未注册
	 */
	boolean isRegisterByMobileMD5(String mobileMD5);

	/**
	 * 忘记密码修改用户密码
	 * 
	 * @param updatePwdOP
	 * @return
	 */
	int updatePwd(UpdatePwdOP updatePwdOP);

	/**
	 * 保存身份认证信息
	 * 
	 * @param identityInfoOP
	 * @return
	 */
	int saveIdentityInfo(IdentityInfoOP identityInfoOP);

	/**
	 * 保存基本信息
	 * 
	 * @return
	 */
	int saveBaseInfo(BaseInfoOP baseInfoOP);

	/**
	 * 保存用户基本信息
	 * 
	 * @return
	 */
	int saveTFLBaseInfo(TFLBaseInfoOP tFLbaseInfoOP);

	/**
	 * 保证ocr调用保存
	 * 
	 * @return
	 */
	int saveDoOcr(OcrOP ocrOp);

	/**
	 * 保存人脸识别调用记录
	 * 
	 * @return
	 */
	int saveDoFaceRecognition(FaceRecogOP faceOp);

	/**
	 * 保存运营商调用记录
	 * 
	 * @return
	 */
	int saveDoTelOperator(TelOperatorOP telOperatorOP);

	/**
	 * 保存芝麻信用调用记录
	 * 
	 * @return
	 */
	int saveDoSesameCredit(SesameCreditOP sesameCreditOP);

	/**
	 * 借款人列表
	 * 
	 * @param borrowerOP
	 * @return
	 */
	Page<BorrowerVO> custUserList(@NotNull(message = "参数不能为空") BorrowerOP borrowerOP);

	/**
	 * 借款人冻结/解冻
	 * 
	 * @param custUserStatusOP
	 * @return
	 */
	Boolean updateStatus(@NotNull(message = "参数不能为空") CustUserStatusOP custUserStatusOP);

	/**
	 * 查询用户影像信息
	 * 
	 * @param userId
	 * @return
	 */
	List<FileInfoVO> getFileinfo(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询用户所有通讯录信息
	 * 
	 * @param userId
	 * @return
	 */
	List<FileInfoVO> getAllContactFileinfo(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询用户通讯录文件
	 * 
	 * @param userId
	 * @return
	 */
	List<FileInfoVO> getCustContactFile(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询用户企业营业执照
	 * 
	 * @param userId
	 * @return
	 */
	FileInfoVO getEnterpriseLicenseFileInfo(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询订单最新报告
	 * 
	 * @param applyId
	 * @return
	 */
	FileInfoVO getLastReportByApplyId(@NotNull(message = "参数不能为空") String applyId);

	/**
	 * 查询订单最新联系人
	 * 
	 * @param applyId
	 * @return
	 */
	FileInfoVO getLastContactInfoByApplyId(@NotNull(message = "参数不能为空") String applyId);

	/**
	 * 查询最新的信用卡邮箱报告
	 * 
	 * @param userId
	 * @return
	 */
	public FileInfoVO getLastEmailReportByUserId(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询最新的网银报告
	 * 
	 * @param userId
	 * @return
	 */
	public FileInfoVO getLastBankReportByUserId(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 查询用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	CardVO getCardinfo(@NotNull(message = "参数不能为空") String userId);

	/**
	 * 借款人详情
	 * 
	 * @param queryUserOP
	 * @return
	 */
	QueryUserVO custUserDetail(@NotNull(message = "参数不能为空") QueryUserOP queryUserOP);

	/**
	 * 借款人信息
	 * 
	 * @param userId
	 * @return
	 */
	UserInfoVO getUserInfo(@NotNull(message = "参数不能为空") String userId, String applyId,
                           @NotNull(message = "参数不能为空") Boolean snapshot);

	/**
	 * 更新用户绑卡信息
	 * 
	 * @param user
	 * @return
	 */
	int updateBindInfo(CustUserVO user);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	int updateCustUser(CustUserVO user);

	/**
	 * 获取用户基本信息
	 * 
	 * @param id
	 * @return
	 */
	CustUserInfoVO getSimpleUserInfo(String userId);

	/**
	 * 根据申请编号获取当前紧急联系人表信息
	 * 
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	List<ContactToCollectionVO> getContactHisByApplyNo(String applyId);

	/**
	 * 保存新增历史联系人信息
	 * 
	 * @param contactHistoryOP
	 * @return
	 */
	int saveContactHistory(@NotNull(message = "参数不能为空") ContactHistorySaveOP contactHistoryOP);

	/**
	 * 根据联系人id删除联系人
	 * 
	 * @param id
	 * @return
	 */
	int delContactHisById(String id);

	/*
	 * 获取用户信息
	 */
	TFLBaseInfoOP getTFLUserInfo(String userid);

	int saveDoSignOperator(String uid);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	int saveDoDepositOperator(String uid);

	/**
	 * 删除通讯录附件
	 */
	int deleteTruely(String id);

	/**
	 * @Title: isRepeatByIdNo
	 * @Description: 身份证是否重复绑定
	 * @param @param
	 *            idNo
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 */
	boolean isRepeatByIdNo(String idNo);

	/**
	 * 通过手机号查询客户贷款次数
	 */
	String getLoanCountByMobile(String mobile);

	/**
	 * 更新用户邮箱
	 * 
	 * @return
	 */
	int updateEmail(String userId, String email);

	CustUserVO isRegister(String userName, String userPhone, String userIdCard);

	FileInfoVO getLastXianJinCardBaseByUserId(String userId);

	FileInfoVO getLastXianJinCardAdditionalByUserId(String userId);

	FileInfoVO getLastBaseDataByOrderSn(String orderSn);

	FileInfoVO getLastAdditionalDataByOrderSn(String orderSn);

	FileInfoVO getLastRongBaseByOrderSn(String orderSn);

	FileInfoVO getLastRongAdditionalByOrderSn(String orderSn);

	FileInfoVO getLastRongBaseByUserId(String userId);

	FileInfoVO getLastRongAdditionalByUserId(String userId);

	FileInfoVO getLastRongTJReportDetailByUserId(String userId);

	FileInfoVO getLastRongTJReportDetailByOrderSn(String orderSn);

	int insertBlickList(CustUserVO custUser); // 插入 cust_blickList

	FileInfoVO getLastJDQBaseByOrderSn(String jdqOrderId, String bizCode);

	FileInfoVO getLastJDQReportByOrderSn(String jdqOrderId);

	FileInfoVO getLastDWDBaseByOrderSn(String orderNo);

	FileInfoVO getLastDWDAdditionalByOrderSn(String orderSn);

	FileInfoVO getLastDWDReportByOrderSn(String orderNo);

	FileInfoVO getLastDWDChargeInfoByOrderSn(String orderNo);

	UserInfoVO getUserInfoByMobile(String mobile);

	FileInfoVO getLastSLLAdditionalByOrderSn(String orderSn);

	FileInfoVO getLastSLLReportByOrderSn(String orderSn);

	FileInfoVO getLastSLLBaseByOrderSn(String orderSn);

	int countContractNum(Map map);

	/**
	 * 根据userId获取最新的借点钱基本信息
	 * @param userId
	 * @return
	 */
	FileInfoVO getLastJDQBaseByUserId(String userId);

}