package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.baiqishi.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 白骑士-资信云报告数据-应答报文
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class ReportDataDetail implements Serializable {

	private static final long serialVersionUID = 1687027729736356630L;
	/**
	 * 报告编号
	 */
	private String reportSn;
	/**
	 * 报告时间
	 */
	private String reportTime;
	/**
	 * 申请人信息
	 */
	private Report petitioner;
	/**
	 * 运营商基本信息
	 */
	private ReportMnoBasicInfo mnoBaseInfo;
	/**
	 * 数据来源
	 */
	private List<ReportWebDataSource> webDataSources;
	/**
	 * 高风险名单
	 */
	private Map<String, List<ReportHighRiskList>> bqsHighRiskList;
	/**
	 * 反欺诈云
	 */
	private ReportAntiFraudCloud bqsAntiFraudCloud;
	/**
	 * 用户行为检测
	 */
	// private Map<String,ReportCrossValidation> crossValidation;
	private ReportCrossValidation crossValidation;
	/**
	 * 出行数据
	 */
	private List<ReportGoOutData> goOutDatas;
	/**
	 * 紧急联系人
	 */
	private List<ReportEmergencyContact> emergencyContacts;
	/**
	 * 常用联系人（推测）
	 */
	private List<ReportCommonlyContact> commonlyContacts;
	// /**
	// * 常用地址数据统计
	// */
	// private List<Reportcu> commonlyUsedAddresses;
	/**
	 * 常用联系电话(近6个月)
	 */
	private List<ReportMnoCcm> mnoCommonlyConnectMobiles;
	/**
	 * 常用联系电话（近1个月)
	 */
	private List<ReportMnoOmccm> mnoOneMonthCommonlyConnectMobiles;
	/**
	 * 本人通话活动地区
	 */
	private List<ReportMnoCca> mnoCommonlyConnectAreas;
	/**
	 * 联系人通话活动地区
	 */
	private List<ReportMnoCcca> mnoContactsCommonlyConnectAreas;
	/**
	 * 分时间段统计数据
	 */
	private List<ReportMnoPui> mnoPeriodUsedInfos;
	/**
	 * 新版分时间段统计数据
	 */
	private List<ReportMnoPui> mnoPeriodUsedInfosNew;
	/**
	 * 月使用信息
	 */
	private List<ReportMnoMui> mnoMonthUsedInfos;

	/**
	 * 催收指标
	 */
	private MnoCuiShouInfo mnoCuiShouInfo;

	public String getReportSn() {
		return reportSn;
	}

	public void setReportSn(String reportSn) {
		this.reportSn = reportSn;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public Report getPetitioner() {
		return petitioner;
	}

	public void setPetitioner(Report petitioner) {
		this.petitioner = petitioner;
	}

	public ReportMnoBasicInfo getMnoBaseInfo() {
		return mnoBaseInfo;
	}

	public void setMnoBaseInfo(ReportMnoBasicInfo mnoBaseInfo) {
		this.mnoBaseInfo = mnoBaseInfo;
	}

	public List<ReportWebDataSource> getWebDataSources() {
		return webDataSources;
	}

	public void setWebDataSources(List<ReportWebDataSource> webDataSources) {
		this.webDataSources = webDataSources;
	}

	public Map<String, List<ReportHighRiskList>> getBqsHighRiskList() {
		return bqsHighRiskList;
	}

	public void setBqsHighRiskList(Map<String, List<ReportHighRiskList>> bqsHighRiskList) {
		this.bqsHighRiskList = bqsHighRiskList;
	}

	public ReportAntiFraudCloud getBqsAntiFraudCloud() {
		return bqsAntiFraudCloud;
	}

	public void setBqsAntiFraudCloud(ReportAntiFraudCloud bqsAntiFraudCloud) {
		this.bqsAntiFraudCloud = bqsAntiFraudCloud;
	}

	public ReportCrossValidation getCrossValidation() {
		return crossValidation;
	}

	public void setCrossValidation(ReportCrossValidation crossValidation) {
		this.crossValidation = crossValidation;
	}

	public List<ReportGoOutData> getGoOutDatas() {
		return goOutDatas;
	}

	public void setGoOutDatas(List<ReportGoOutData> goOutDatas) {
		this.goOutDatas = goOutDatas;
	}

	public List<ReportEmergencyContact> getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(List<ReportEmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	public List<ReportCommonlyContact> getCommonlyContacts() {
		return commonlyContacts;
	}

	public void setCommonlyContacts(List<ReportCommonlyContact> commonlyContacts) {
		this.commonlyContacts = commonlyContacts;
	}

	public List<ReportMnoCcm> getMnoCommonlyConnectMobiles() {
		return mnoCommonlyConnectMobiles;
	}

	public void setMnoCommonlyConnectMobiles(List<ReportMnoCcm> mnoCommonlyConnectMobiles) {
		this.mnoCommonlyConnectMobiles = mnoCommonlyConnectMobiles;
	}

	public List<ReportMnoOmccm> getMnoOneMonthCommonlyConnectMobiles() {
		return mnoOneMonthCommonlyConnectMobiles;
	}

	public void setMnoOneMonthCommonlyConnectMobiles(List<ReportMnoOmccm> mnoOneMonthCommonlyConnectMobiles) {
		this.mnoOneMonthCommonlyConnectMobiles = mnoOneMonthCommonlyConnectMobiles;
	}

	public List<ReportMnoCca> getMnoCommonlyConnectAreas() {
		return mnoCommonlyConnectAreas;
	}

	public void setMnoCommonlyConnectAreas(List<ReportMnoCca> mnoCommonlyConnectAreas) {
		this.mnoCommonlyConnectAreas = mnoCommonlyConnectAreas;
	}

	public List<ReportMnoCcca> getMnoContactsCommonlyConnectAreas() {
		return mnoContactsCommonlyConnectAreas;
	}

	public void setMnoContactsCommonlyConnectAreas(List<ReportMnoCcca> mnoContactsCommonlyConnectAreas) {
		this.mnoContactsCommonlyConnectAreas = mnoContactsCommonlyConnectAreas;
	}

	public List<ReportMnoPui> getMnoPeriodUsedInfos() {
		return mnoPeriodUsedInfos;
	}

	public void setMnoPeriodUsedInfos(List<ReportMnoPui> mnoPeriodUsedInfos) {
		this.mnoPeriodUsedInfos = mnoPeriodUsedInfos;
	}

	public List<ReportMnoMui> getMnoMonthUsedInfos() {
		return mnoMonthUsedInfos;
	}

	public void setMnoMonthUsedInfos(List<ReportMnoMui> mnoMonthUsedInfos) {
		this.mnoMonthUsedInfos = mnoMonthUsedInfos;
	}

	public MnoCuiShouInfo getMnoCuiShouInfo() {
		return mnoCuiShouInfo;
	}

	public void setMnoCuiShouInfo(MnoCuiShouInfo mnoCuiShouInfo) {
		this.mnoCuiShouInfo = mnoCuiShouInfo;
	}

	public List<ReportMnoPui> getMnoPeriodUsedInfosNew() {
		return mnoPeriodUsedInfosNew;
	}

	public void setMnoPeriodUsedInfosNew(List<ReportMnoPui> mnoPeriodUsedInfosNew) {
		this.mnoPeriodUsedInfosNew = mnoPeriodUsedInfosNew;
	}

}
