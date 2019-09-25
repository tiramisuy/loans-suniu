/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.loans.baiqishi.vo.*;
import com.rongdu.loans.basic.vo.AreaVO;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListVO;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.credit100.vo.ApplyLoandVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.kdcredit.vo.KDBlackListVO;
import com.rongdu.loans.linkface.vo.IdnumberVerificationVO;
import com.rongdu.loans.loan.option.dwd.ChargeInfo;
import com.rongdu.loans.loan.option.dwd.DWDAdditionInfo;
import com.rongdu.loans.loan.option.dwd.DWDBaseInfo;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rong360Model.OrderBaseInfo;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.tencent.vo.AntiFraudVO;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListVO;
import com.rongdu.loans.tlblackrisk.vo.TongLianBlackVO;
import com.rongdu.loans.tongdun.vo.FraudApiVO;
import com.rongdu.loans.tongdun.vo.RuleDetailVO;
import com.rongdu.loans.xinyan.vo.*;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;
import com.rongdu.loans.zhicheng.vo.FraudScreenVO;
import com.rongdu.loans.zhicheng.vo.RiskListVO;

import java.util.List;
import java.util.Map;

/**
 * 现金贷自动审批所需的公共数据服务
 *
 * @author sunda
 * @version 2017-08-14
 */
public interface CreditDataInvokeService {

    /**
     * 查询贷款申请信息
     *
     * @param applyId
     * @return
     */
    public LoanApplySimpleVO getLoanApplyById(String applyId);

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    public CustUserVO getUserById(String userId);

    /**
     * 查询用户基本资料
     *
     * @param userId
     * @param applyId
     * @param b
     * @return
     */
    public UserInfoVO getUserInfo(String userId, String applyId, boolean b);

    /**
     * 初始化贷款自动审核的数据：用户信息，贷款申请信息，用户基本资料
     */
    public AutoApproveContext createAutoApproveContext(String applyId);

    /**
     * 获得某个特定的风控规则
     *
     * @param ruleId
     * @return
     */
    public RiskRule getRiskRule(String ruleId,Integer modelId);

    /**
     * 根据規則集查询一组风控规则Map
     *
     * @param ruleSetId 不能为空
     * @return
     */
    public Map<String, RiskRule> getRiskRuleMap(String ruleSetId, Integer modelId);

    /**
     * 百融特殊名单
     *
     * @param context
     * @return
     */
    public Map<String, String> getCredit100SpecialList(AutoApproveContext context);

    /**
     * 百融-当日多次申请
     *
     * @param context
     * @return
     */
    public ApplyLoandVO getCredit100ApplyLoand(AutoApproveContext context);

    /**
     * 百融-多次申请核查月度版
     *
     * @param context
     * @return
     */
    public Map<String, String> getCredit100ApplyLoanMon(AutoApproveContext context);

    // /**
    // * 百融-自定义汇总的多头申请规则
    // * @return
    // */
    // public Map<String,String> getCredit100RiskConfigMap();

    /**
     * 宜信致诚-查询借款、风险和逾期信息
     *
     * @param context
     * @return
     */
    public CreditInfoVO getZhichengCreditInfo(AutoApproveContext context);

    /**
     * 宜信致诚-福网-风险名单
     *
     * @param context
     * @return
     */
    public RiskListVO getZhichengRiskList(AutoApproveContext context);

    /**
     * 同盾-执行反欺诈决策引擎
     *
     * @return
     */
    public FraudApiVO doTongdunAntifraud(AutoApproveContext context);

    /**
     * 同盾-命中规则详情查询
     *
     * @return
     */
    public RuleDetailVO getTongdunRuleDetail(AutoApproveContext context);

    /**
     * 执行同盾-反欺诈决策引擎
     *
     * @param context
     * @return
     */
    public DecisionVO doBaishiqiDecision(AutoApproveContext context);

    /**
     * 执行腾讯-反欺诈决策引擎
     *
     * @param context
     * @return
     */
    public AntiFraudVO doTencentAntifraud(AutoApproveContext context);

    /**
     * 白骑士-资信云报告数据
     */
    public ReportDataVO getBaishiqiReportData(AutoApproveContext context);

    /**
     * 白骑士-获取资信云报告,数据源来自文件
     *
     * @return
     */
    public ReportDataVO getBaishiqiReportDataFromFile(String applyId);

    /**
     * 白骑士-上报联系人
     */
    public int reportContactToBaiqishi(String userId);

    /**
     * 根据地区编号获取地区信息
     */
    public AreaVO getArea(String areaId);

    /**
     * 查询设备信息
     *
     * @return
     */
    public DeviceInfoVO getBaiqishiDeviceInfo(AutoApproveContext context);

    /**
     * 获取手机通讯录信息
     *
     * @return
     */
    public DeviceContactVO getBaiqishiContactInfo(AutoApproveContext context);

    /**
     * 获取手机通讯录信息,数据源来自文件
     *
     * @param userId
     * @param applyId
     * @return
     */
    public DeviceContactVO getBaiqishiContactInfoFromFile(String userId, String applyId);

    /**
     * 查询芝麻信用授权结果
     *
     * @return
     */
    public AuthorizeResultVO getZmAuthorizeResult(AutoApproveContext context);

    /**
     * 查询芝麻信用分
     *
     * @return
     */
    public ZmScoreVO getZmScore(AutoApproveContext context);

    /**
     * 查询芝麻信用行业关注名单
     *
     * @return
     */
    public ZmWatchListVO getZmWatchList(AutoApproveContext context);

    /**
     * 向白骑士规则引擎上送的自定义参数
     *
     * @return
     */
    public Map<String, String> getBaishiqiExtParams(AutoApproveContext context);

    /**
     * 查询移动网络运营商的原始数据
     *
     * @param context
     * @return
     */
    public MnoContactVO getBaiqishiMnoContact(AutoApproveContext context);

    /**
     * 口袋查询黑名单
     *
     * @param context
     * @return
     */
    public KDBlackListVO getKDBlacklist(AutoApproveContext context);

    /**
     * 查询最长逾期天数
     *
     * @param context
     * @return
     */
    public int getMaxOverdueDays(AutoApproveContext context);

    /**
     * 查询累计逾期天数
     *
     * @param context
     * @return
     */
    public int getCountOverdueDays(AutoApproveContext context);

    /**
     * 三方征信1查询黑名单
     *
     * @param context
     * @return
     */
    public ThirdPartyCredit1BlackListVO getThirdPartyCredit1Blacklist(AutoApproveContext context);

    /**
     * 三方征信2查询黑名单
     *
     * @param context
     * @return
     */
    public ThirdPartyCredit1BlackListVO getThirdPartyCredit2Blacklist(AutoApproveContext context);

    /**
     * 新颜-负面拉黑
     */
    public BlackVO getXinyanBlack(AutoApproveContext context);

    /**
     * 新颜-共债档案
     */
    public TotaldebtVO getXinyanTotalDebt(AutoApproveContext context);

    /**
     * 新颜-逾期档案
     */
    public OverdueVO getXinyanOverdueVO(AutoApproveContext context);

    /**
     * 新颜-负面拉黑-聚宝钱包
     */
    public JbqbBlackVO getXinyanBlackJbqb(AutoApproveContext context);

    /**
     * 新颜-全景雷达
     */
    public RadarVO getXinyanRadar(AutoApproveContext context);

    /**
     * 魔蝎-信用卡邮箱报告
     */
    public List<EmailReportVO> getMoxieEmailReport(String userId);

    /**
     * 魔蝎-网银报告
     */
    public BankReportVO getMoxieBankReport(String userId);

    /**
     * 现金白卡数据
     */
    XianJinBaiKaCommonOP getXianJinBaiKaBase(AutoApproveContext context);

    XianJinBaiKaCommonOP getXianJinBaiKaAdditional(AutoApproveContext context);

    /**
     * 从文件系统获取 融360基础数据
     */
    OrderBaseInfo getRongBase(AutoApproveContext context);

    /**
     * @param context
     * @return IdnumberVerificationVO 返回类型
     * @throws
     * @Title: getLinkfaceIdnumberVerification
     * @Description: 获取商汤人脸识别接口
     */
    public IdnumberVerificationVO getLinkfaceIdnumberVerification(AutoApproveContext context);

    /**
     * 从文件系统获取 融360附加数据
     */
    OrderAppendInfo getRongAdditional(AutoApproveContext context);

    /**
     * 从文件系统获取 融天机运营商报告
     */
    TianjiReportDetailResp getRongTJReportDetail(AutoApproveContext context);

    /**
     * 抓取 融天机运营商报告
     */
    void crawlRongCarrierReport(String orderNo, String userId, String applyId);

    /**
     * 根据手机号查询未完结订单
     *
     * @param mobile
     * @return
     */
    public int countUnFinishApplyByMobile(String mobile);

    IntoOrder getjdqBase(AutoApproveContext context);

    IntoOrder getjdqBaseAdd(AutoApproveContext context);

    JDQReport getjdqReport(AutoApproveContext context);

    DWDReport getdwdReport(AutoApproveContext autoApproveContext);

    ChargeInfo getdwdChargeInfo(AutoApproveContext context);

    DWDAdditionInfo getdwdAdditionInfo(AutoApproveContext context);

    DWDBaseInfo getdwdBaseInfo(AutoApproveContext context);

    JDQReport getsllReport(AutoApproveContext autoApproveContext);

    /**
     * 统计 某个人的紧急联系人在近 多少小时/月 出现在其他紧急联系人的次数
     * @param context
     * @param type
     * @param time
     * @return
     */
    int countContract(AutoApproveContext context,String type,int time);

    /**
     * 宜信阿福综合决策报告接口
     *
     * @param context
     * @return
     */
    com.rongdu.loans.zhicheng.vo.DecisionVO getZhiChengDecision(AutoApproveContext context);
    /**
     * 宜信阿福欺诈甄别接口
     *
     * @param context
     * @return
     */
    FraudScreenVO getZhiChengFraudScreen(AutoApproveContext context);

    /**
     * 通联查询黑名单接口
     * @param context
     * @return
     */
    TongLianBlackVO getTongLianBlackDetail(AutoApproveContext context);
}