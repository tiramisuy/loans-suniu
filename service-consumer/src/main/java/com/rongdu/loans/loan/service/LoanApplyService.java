package com.rongdu.loans.loan.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.cust.vo.UserRecordVO;
import com.rongdu.loans.loan.option.*;
import com.rongdu.loans.loan.option.xjbk.ApplyThirdOP;
import com.rongdu.loans.loan.option.xjbk.ApproveFeedbackOP;
import com.rongdu.loans.loan.vo.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请Service接口
 *
 * @author likang
 * @version 2017-06-22
 */
@Service
// @Transactional(readOnly = true)
public interface LoanApplyService {

    /**
     * 通过用户id获取当前申请信息
     *
     * @param userId
     * @return
     */
    LoanApplySimpleVO getCurrentLoanApplyInfo(String userId);

    /**
     * 获取当前借款状态(新)
     *
     * @param userId
     * @param productId
     * @return
     */
    Map<String, Object> getCurrentLoanStatusByPid(String userId, String productId);

    /**
     * 通过用户id获取未完成申请信息
     *
     * @param userId
     * @return
     */
    LoanApplySimpleVO getUnFinishLoanApplyInfo(String userId);

    /**
     * 根据用户id判断是否存在未完结的申请单
     *
     * @param userId
     * @return
     */
    boolean isExistUnFinishLoanApply(String userId);

    /**
     * 根据用户基本信息查询已结清贷款申请汇总信息
     *
     * @param op
     * @return
     */
    LoansApplyFinishedVO getFinishedLoansApplyFromUser(LoanApplyCustOP op);

    /**
     * 通过申请id获取贷款信息
     *
     * @param applyId
     * @return
     */
    LoanApplySimpleVO getLoanApplyById(String applyId);

    /**
     * 贷款信息列表 后台贷前管理，贷中管理列表
     *
     * @param applyListOP
     */
    Page<ApplyListVO> getLoanApplyList(@NotNull(message = "分页参数不能为空") Page page,
                                       @NotNull(message = "参数不能为空") ApplyListOP applyListOP);

    /**
     * 导出已过审数据
     */
    List<ApplyListVO> getLoanApplyListExport(Page page, ApplyListOP op);

    /**
     * 导出已过审数据,到呼叫系统
     */
    List<ApplyListCalloutVO> getLoanApplyListExportCallout(Page page, ApplyListOP op);

    ApplyListVO getLastFinishApplyByUserId(String userId);

    ApplyListVO getBaseLoanApplyById(String applyId);

    /**
     * 保存贷款申请信息
     *
     * @param loanApplyOP
     * @return
     */
    SaveApplyResultVO saveLoanApply(LoanApplyOP loanApplyOP);

    /**
     * 根据用户id获取当前（未完结）申请的申请编号
     *
     * @param userId
     * @return
     */
    String getCurApplyIdByUserId(String userId);

    /**
     * 获取或生成申请编号
     *
     * @param userId
     * @return
     */
    ApplyIdVO getApplyId(String userId);

    ContentTableVO contentTable(@NotNull(message = "参数不能为空") ContentTableOP op);

    /**
     * 贷款单审核接口
     * <p>
     * 操作入口上分为系统自动审核和人工审核； 审核结果上分为通过和不通过。
     * <p>
     * 业务逻辑： 1.修改贷款单状态 2.增加操作日志 3.发送短信通知 4.借款标的推送
     *
     * @param op
     * @return
     */
    Boolean approve(@NotNull(message = "参数不能为空") LoanCheckOP op);

    /**
     * 获取协议所需要素
     *
     * @param agreementOP
     * @return
     */
    AgreementVO getAgreementFactor(AgreementOP agreementOP);

    /**
     * 阿福获取用户记录
     *
     * @return
     */
    UserRecordVO getUserRecord(@NotNull(message = "参数不能为空") String trueName, @NotNull(message = "参数不能为空") String idNo);

    /**
     * 更新贷款申请进件的状态,保存操作日志
     *
     * @param applyId
     */
    public int updateApplyStatus(String applyId, int status);

    /**
     * 更新淘宝、学信等认证状态
     *
     * @param userId
     * @param status
     */
    public int updateAuthStatus(String userId, Integer status);

    public Map<String, Object> getCheckAllotListTotal(String startDate, String endDate, List<String> auditorId,
                                                      String type);

    /**
     * 申请是否受限判断
     */
    public boolean isApplyLimit(String userId);

    /*
     * 更新商户id
     */
    public void updateCompanyId(String companyId, String id);

    /**
     * 根据用户id获得贷款记录
     */
    public List<LoansApplySummaryVO> getLoanApplyListByUserId(String userId);

    public int countUnFinishLoanApplyByUserId(String userId);

    public int countApprovingByUserId(String userId);

    public BigDecimal getUnFinishLoanAmtByUserId(String userId);

    public long getLastRejectDaysByUserId(String userId);

    public int countRejectByUserid(String userId);

    /**
     * 根据申请编号查询外部流水号
     */
    String getOutSideNumByApplyId(String applyId);

    /**
     * 更新订单为重新审核
     */
    int updateResetCheck(String applyId);

    /**
     * 取消订单
     */
    int updateCancel(String applyId, String operatorName);


    /**
     * 修改用户是否访问借点钱绑卡页状态
     */
    int updateCountUserBindCardPv(String applyId);

    /**
     * 根据客户手机号查询客户订单
     */
    List<Map<String, Object>> custApplyList(String id);

    /**
     * 是否有其他产品未结清
     *
     * @param userId
     * @param productId
     * @return
     */
    public boolean hasOtherProductUnFinishApply(String userId, String productId);

    /**
     * 购买商品后插入标的
     *
     * @param applyId
     * @param loanPayType 0=先支付，1=后代扣
     * @return
     */
    boolean saveShopedBorrowInfo(String applyId, Integer loanPayType);

    /**
     * 是否购买购物金
     */
    public boolean isBuyShoppingGold(String applyId);

    Page<ApproveFeedbackOP> getLoanApplyThirdList(Page<ApproveFeedbackOP> page, ApplyThirdOP op);

    /**
     * 查询标的类型
     */
    Integer getBorrowType(String applyId);

    ApplyAllotVO getApplyById(String id);

    /**
     * 取消订单-代扣旅游券专用
     */
    public int updateCancelShopWithhold(String applyId);

    /**
     * 统计初审员新单回款率
     */
    List<Map<String, Object>> getReturnRate(String startDate, String endDate, String auditorId, String channel,
                                            String productId, String termType);

    /**
     * 组装协议数据
     */
    Map<String, Object> getContractDetail(String contNo, String type);

    /**
     * 查询未完结订单
     *
     * @param mobile
     * @return
     */
    public int countUnFinishApplyByMobile(String mobile);

    /**
     * 保存审核操作日志
     *
     * @param op
     * @param now
     * @param auto
     * @param operatorId
     * @param operatorName
     * @param applyId
     * @return
     */
    public int saveApproveLogLatter(LoanCheckOP op, Date now, Boolean auto, String operatorId, String operatorName,
                                    String applyId);

    /**
     * 未接电话更新申请表
     *
     * @param applyId
     * @param callNum
     * @param remark
     * @return
     */
    public int updateNoAnswer(String applyId, Integer callNum, String remark);

    /**
     * 成功放款次数
     *
     * @param userId
     * @return
     */
    public int countOverLoanByRepay(String userId);

    /**
     * code y1030 查询同以身份证号不同手机号用户未结清数量
     */
    int countUnFinishByMobileAndIdNo(String idNo, String mobile);

    /**
     * @Title: updateKDPayChannel @Description: 更新口袋支付渠道 @return int
     * 返回类型 @throws
     */
    int updateKDPayChannel(String applyId);

    /**
     * 修改放款渠道
     *
     * @param applyId
     * @param payChannel 渠道号
     * @return
     */
    int updatePayChannel(String applyId, int payChannel);

    /**
     * 修改放款渠道,不在borrowInfo中才做修改
     *
     * @param applyId
     * @param payChannel
     * @return
     */
    int updatePChannel(String applyId, int payChannel);

    int updateChannel(String applyId, String sll);

    void saveOperationLog(String userId, String ip);

    /**
     * 通过用户id获取当前未结清订单的放款渠道
     *
     * @param userId
     * @return
     */
    String getPayChannelByUserId(String userId);

    /**
     * 更新汉金所放款渠道,插入borrowINfo
     *
     * @param applyId
     * @return
     */
    int updateHanJSPayChannel(String applyId);

    List<LoanApplyVO> getLoanApplyByUserId(String userId);

    /**
     * rong_create_account_*
     *
     * @return
     */
    int rong360KoudaiBuildBorrowInfo(int n);

    /**
     * 乐视-手动放款,
     *
     * @param applyId
     * @return
     */
    int handelExtend(String applyId);

    Page<ApplyListVO> getLoanApplyByApi(@NotNull(message = "分页参数不能为空") Page page,
                                        @NotNull(message = "参数不能为空") ApplyListOP applyListOP);

    /**
     * 更新加急券支付状态
     *
     * @param applyId
     * @param status
     */
    boolean updateUrgentPayed(String applyId, String status);

    TaskResult cancelLoanApply();
    TaskResult cancelLoanApplyByDwd();

    /**
     *
     * @param shareVO
     * @return
     */
    Map<String,String> handleApproveOrder(ShareVO shareVO, String type);

    Map<String,String> handleOrder(ShareVO shareVO, String type);

    int updateCompositeScore(String applyId, String compositeScore);

    int updateZhiChengLoanRecord(String applyId, int size);
}
