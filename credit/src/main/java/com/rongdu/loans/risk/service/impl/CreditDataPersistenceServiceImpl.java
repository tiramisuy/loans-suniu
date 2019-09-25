/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.baiqishi.vo.DeviceInfoVO;
import com.rongdu.loans.baiqishi.vo.MnoContactVO;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.risk.service.CreditDataPersistenceService;
import com.rongdu.loans.tencent.vo.AntiFraudVO;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;
import com.rongdu.loans.zhicheng.vo.RiskListVO;

/**
 * 第三方征信数据持久化服务
 * 
 * @author sunda
 * @version 2017-08-14
 */
@Service("creditDataPersistenceService")
public class CreditDataPersistenceServiceImpl extends BaseService implements CreditDataPersistenceService {

	@Autowired
	private CreditDataInvokeService creditDataInvokeService;

	private FileServerClient fileServerClient = new FileServerClient();

	/**
	 * 预加载贷款审核所需征信数并且持久化
	 */
	public void preLoadCreditData(String applyId, String source) {
		// 目前仅支持ios/android获取设备相关信息
		if ("1".equals(source) || "2".equals(source)) {
			saveBaiqishiDeviceContactFile(applyId);
			saveBaiqishiDeviceInfoFile(applyId);
		}
		saveBaiqishiReportFile(applyId);

		// saveBaiqishiMnoContactFile(applyId);
		// saveTencentAntiFraud(applyId);
		// saveZhimaCreditScore(applyId);//芝麻停用
		// saveZhimaWatchList(applyId);//芝麻停用
		// saveZhichengLoanRecord(applyId);
		// saveZhichengQueryHistory(applyId);
		// saveZhichengRiskList(applyId);
	}

	/**
	 * 将白骑士资信报告的数据保存为文件
	 */
	public void saveBaiqishiReportFile(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		if (vo != null && vo.isSuccess()) {
			// 公共参数
			UploadParams params = new UploadParams();
			// 注意获取用户端的IP地址
			String clientIp = "127.0.0.1";
			// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
			String source = context.getApplyInfo().getSource();
			params.setUserId(context.getUserId());
			params.setApplyId(applyId);
			params.setIp(clientIp);
			params.setSource(source);
			params.setBizCode(FileBizCode.BAIQISHI_REPORT.getBizCode());
			String fileBodyText = JsonMapper.toJsonString(vo);
			fileServerClient.uploadDocumentString(fileBodyText, "txt", params);
		}

	}

	/**
	 * 将白骑士的移动设备信息保存为文件
	 */
	public void saveBaiqishiDeviceInfoFile(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		DeviceInfoVO vo = creditDataInvokeService.getBaiqishiDeviceInfo(context);
		if (vo != null && vo.isSuccess()) {
			// 公共参数
			UploadParams params = new UploadParams();
			// 注意获取用户端的IP地址
			String clientIp = "127.0.0.1";
			// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
			String source = context.getApplyInfo().getSource();
			params.setUserId(context.getUserId());
			params.setApplyId(applyId);
			params.setIp(clientIp);
			params.setSource(source);
			if (StringUtils.equals("1", source)) {
				params.setBizCode(FileBizCode.IOS_DEVICE.getBizCode());
			} else {
				params.setBizCode(FileBizCode.ANDROID_DEVICE.getBizCode());
			}
			String fileBodyText = JsonMapper.toJsonString(vo);
			String fileExt = "txt";
			fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		}
	}

	/**
	 * 将移动设备通讯录保存为文件
	 */
	public void saveBaiqishiDeviceContactFile(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		DeviceContactVO vo = creditDataInvokeService.getBaiqishiContactInfo(context);
		if (vo != null && vo.isSuccess()) {
			// 公共参数
			UploadParams params = new UploadParams();
			// 注意获取用户端的IP地址
			String clientIp = "127.0.0.1";
			// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
			String source = context.getApplyInfo().getSource();
			params.setUserId(context.getUserId());
			params.setApplyId(applyId);
			params.setIp(clientIp);
			params.setSource(source);
			params.setBizCode(FileBizCode.DEVICE_CONTACT.getBizCode());
			String fileBodyText = JsonMapper.toJsonString(vo);
			String fileExt = "txt";
			fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		}
	}

	/**
	 * 将移动网络运营商通讯录保存为文件
	 */
	public void saveBaiqishiMnoContactFile(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		MnoContactVO vo = creditDataInvokeService.getBaiqishiMnoContact(context);
		if (vo != null && vo.isSuccess()) {
			// 公共参数
			UploadParams params = new UploadParams();
			// 注意获取用户端的IP地址
			String clientIp = "127.0.0.1";
			// 上传文件来源于哪个终端（1-ios，2-android，3-H5，4-网站，5-system）
			String source = context.getApplyInfo().getSource();
			params.setUserId(context.getUserId());
			params.setApplyId(applyId);
			params.setIp(clientIp);
			params.setSource(source);
			params.setBizCode(FileBizCode.MNO_CONTACT.getBizCode());
			String fileBodyText = JsonMapper.toJsonString(vo);
			String fileExt = "txt";
			fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
		}
	}

	/**
	 * 将白骑士报告保存到数据库
	 */
	public void saveBaiqishiReportData(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
	}

	/**
	 * 将白骑士移动设备信息保存到数据库
	 */
	public void saveBaiqishiDeviceInfo(String applyId) {

	}

	/**
	 * 将通讯录保存到数据库
	 */
	public void saveBaiqishiContactInfo(String applyId) {

	}

	/**
	 * 保存芝麻信用分
	 */
	public void saveZhimaCreditScore(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		ZmScoreVO vo = creditDataInvokeService.getZmScore(context);
	}

	/**
	 * 保存芝麻行业关注名单
	 */
	public void saveZhimaWatchList(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		ZmWatchListVO vo = creditDataInvokeService.getZmWatchList(context);
	}

	/**
	 * 保存腾讯反欺诈服务调用结果
	 */
	public void saveTencentAntiFraud(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		AntiFraudVO vo = creditDataInvokeService.doTencentAntifraud(context);
	}

	/**
	 * 保存腾讯OCR认证结果
	 */
	public void saveTencentOcrResult(String applyId) {

	}

	/**
	 * 保存腾讯活体识别认证结果
	 */
	public void saveTencentFaceVerifyResult(String applyId) {

	}

	/**
	 * 保存同盾反欺诈规则引擎输出结果
	 */
	public void saveTongdunAntiFraud(String applyId) {

	}

	/**
	 * 保存同盾反欺诈命中规则详情查询
	 */
	public void saveTongdunRuleDetail(String applyId) {

	}

	/**
	 * 保存同盾设备信息
	 */
	public void saveTongdunDeviceInfo(String applyId) {

	}

	/**
	 * 保存致诚阿福贷款记录
	 */
	public void saveZhichengLoanRecord(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		CreditInfoVO vo = creditDataInvokeService.getZhichengCreditInfo(context);

	}

	/**
	 * 保存致诚阿福贷款记录
	 */
	public void saveZhichengQueryHistory(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		CreditInfoVO vo = creditDataInvokeService.getZhichengCreditInfo(context);
	}

	/**
	 * 保存致诚阿福风险名单
	 */
	public void saveZhichengRiskList(String applyId) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(applyId);
		RiskListVO vo = creditDataInvokeService.getZhichengRiskList(context);
	}
}