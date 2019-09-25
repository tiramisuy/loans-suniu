package com.rongdu.loans.credit.baiqishi.service;

import java.util.List;
import java.util.Map;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.anrong.vo.MSPReprtVO;
import com.rongdu.loans.credit.baiqishi.vo.CuishouOP;
import com.rongdu.loans.credit.baiqishi.vo.CuishouVO;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageOP;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageVO;
import com.rongdu.loans.credit.moxie.vo.CreditcardReportVO;
import com.rongdu.loans.cust.vo.CustContactVO;

public interface ReportService {

    /**
     * 查询资信云报告页面URL
     *
     * @param op
     * @return
     */
    ReportPageVO getReportPage(ReportPageOP op);

    /**
     * 查询通讯录联系人通讯信息
     *
     * @param context
     * @return
     */
    List<Map<String, Object>> getContactConnectInfo(String applyId);

    /**
     * 上传查询催收指标用户
     *
     * @param userId
     * @return
     */
    CuishouVO uploadCuishou(CuishouOP op);

    /**
     * 上报联系人
     *
     * @param userId
     * @return
     */
    int reportContact(String userId);

    /**
     * 获取资信云报告
     *
     * @param applyId
     * @param type
     * @return
     */
    public String getReportData(String applyId, int type);

    /**
     * 联系人匹配结果
     *
     * @param applyId
     * @return
     */
    public List<CustContactVO> contactMatch(String userId, String applyId, List<CustContactVO> contactList);
    
    /**
     * rong360联系人匹配结果
     *
     * @param applyId
     * @return
     */
    public List<CustContactVO> rongContactMatch(String userId, String applyId, List<CustContactVO> contactList);

    /**
     * 魔蝎信用卡报告
     *
     * @param userId
     * @return
     */
    public CreditcardReportVO getMoxieCreditcardReport(String userId);
    
    /**
     * 获取融360通讯录通话信息
     * @param applyId
     */
    Object getRongConnectInfo(String applyId,String userId);

    String getjdqData(String applyId);

    String getXianJinCardData(String applyId);
    
    /**
     * 获取融360运营商报告信息
     * @param applyId
     */
    String getRong360Data(String applyId);

    String getdwdData(String applyId);

    String getsllData(String applyId);

    /**
     * 获取安融报告信息
     * @param applyId
     * @return
     */
    MSPReprtVO getAnRongData(String applyId);

    TaskResult dealWithAnRong();
}