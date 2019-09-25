/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

/**
 * 第三方征信数据持久化服务
 * 
 * @author sunda
 * @version 2017-08-14
 */
public interface CreditDataPersistenceService {

	/**
	 * 预加载贷款审核所需征信数并且持久化
	 */
	public void preLoadCreditData(String applyId,String source);

	/**
	 * 将白骑士资信报告的数据保存为文件
	 */
	public void saveBaiqishiReportFile(String applyId);

	/**
	 * 将白骑士的移动设备信息保存为文件
	 */
	public void saveBaiqishiDeviceInfoFile(String applyId);

	/**
	 * 将移动设备通讯录保存为文件
	 */
	public void saveBaiqishiDeviceContactFile(String applyId);

	/**
	 * 将移动网络运营商通讯录保存为文件
	 */
	public void saveBaiqishiMnoContactFile(String applyId);

	/**
	 * 将白骑士移动设备的数据保存到数据库
	 */
	public void saveBaiqishiReportData(String applyId);

	/**
	 * 将白骑士移动设备信息保存到数据库
	 */
	public void saveBaiqishiDeviceInfo(String applyId);

	/**
	 * 将通讯录保存到数据库
	 */
	public void saveBaiqishiContactInfo(String applyId);

	/**
	 * 保存芝麻信用分
	 */
	public void saveZhimaCreditScore(String applyId);

	/**
	 * 保存芝麻行业关注名单
	 */
	public void saveZhimaWatchList(String applyId);

	/**
	 * 保存腾讯反欺诈服务调用结果
	 */
	public void saveTencentAntiFraud(String applyId);

	/**
	 * 保存腾讯OCR认证结果
	 */
	public void saveTencentOcrResult(String applyId);

	/**
	 * 保存腾讯活体识别认证结果
	 */
	public void saveTencentFaceVerifyResult(String applyId);

	/**
	 * 保存同盾反欺诈规则引擎输出结果
	 */
	public void saveTongdunAntiFraud(String applyId);

	/**
	 * 保存同盾反欺诈命中规则详情查询
	 */
	public void saveTongdunRuleDetail(String applyId);

	/**
	 * 保存同盾设备信息
	 */
	public void saveTongdunDeviceInfo(String applyId);

	/**
	 * 保存致诚阿福贷款记录
	 */
	public void saveZhichengLoanRecord(String applyId);

	/**
	 * 保存致诚阿福贷款记录
	 */
	public void saveZhichengQueryHistory(String applyId);

	/**
	 * 保存致诚阿福风险名单
	 */
	public void saveZhichengRiskList(String applyId);

}