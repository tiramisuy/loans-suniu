package com.rongdu.loans.loan.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.service.CrudService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.common.IdUtils;
import com.rongdu.loans.loan.dao.LoanApplyDAO;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.CountApplyOP;
import com.rongdu.loans.loan.option.LoanApplyCustOP;
import com.rongdu.loans.loan.option.xjbk.ApplyThirdOP;
import com.rongdu.loans.loan.option.xjbk.ApproveFeedbackOP;
import com.rongdu.loans.loan.vo.ApplyIdVO;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.loan.vo.LoanApplyStatusVO;
import com.rongdu.loans.loan.vo.LoansApplySummaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("loanApplyManager")
public class LoanApplyManager extends CrudService<LoanApplyDAO, LoanApply, String> {

    @Autowired
    private OperationLogManager operationLogManager;

    /**
     * 根据用户基本信息查询贷款申请
     *
     * @param op
     * @return
     */
    public List<LoanApply> getLoanApplyFromUser(LoanApplyCustOP op) {
        return dao.getLoanApplyFromUser(op);
    }

    /**
     * 更新申请表的阶段与状态
     *
     * @param loanApply
     * @return
     */
    public int updateStageOrStatus(LoanApply loanApply) {
        //XianJinCardUtils.rongPayFeedback(loanApply.getId());
        if (null != loanApply) {
            loanApply.preUpdate();
        }
        return dao.updateStageOrStatus(loanApply);
    }

    /**
     * 根据用户基本信息查询贷款申请概要列表
     *
     * @param op
     * @return
     */
    public List<LoansApplySummaryVO> getLoanApplySummaryFromUser(LoanApplyCustOP op) {
        return dao.getLoanApplySummaryFromUser(op);
    }

    /**
     * 根据用户基本信息查询贷款申请
     *
     * @param id
     * @return
     */
    public LoanApply getLoanApplyById(String id) {
        return dao.getById(id);
    }

    /**
     * 根据用户id查询未完结的申请记录
     *
     * @param userId
     * @return
     */
    public LoanApply getUnFinishLoanApplyByUserId(String userId) {
        return dao.getUnFinishLoanApplyByUserId(userId);
    }

    /**
     * 根据用户基本信息查询贷款申请
     *
     * @param userId
     * @return
     */
    public List<LoanApply> getRejectLoanApplyByUserId(String userId) {
        return dao.getRejectLoanApplyByUserId(userId);
    }

    /**
     * 查询用户所有的贷款申请单
     *
     * @param userId
     * @return
     */
    public List<LoanApply> getLoanApplyByUserId(String userId) {
        return dao.getLoanApplyByUserId(userId);
    }

    public int saveLoanApplyInfo(LoanApply loanApply) {
        return dao.insert(loanApply);
    }

    /**
     * 跟进申请编号获取申请单当前阶段与状态
     *
     * @param applyId
     * @return
     */
    public LoanApplyStatusVO getCurrentApplyStatus(String applyId) {
        return dao.getCurrentApplyStatus(applyId);
    }

    /**
     * 更新申请单状态
     *
     * @param loanApply
     * @return
     */
    public int updateStatusAndSaveLog(LoanApply loanApply) {
        //XianJinCardUtils.rongPayFeedback(loanApply.getId());
        int rz = 0;
        // 构造日志入参
        if (null != loanApply && StringUtils.isNotBlank(loanApply.getId())) {
            /** 更新申请表信息 */
            loanApply.preUpdate();
            rz = dao.updateStageOrStatus(loanApply);
        }
        return rz;
    }

    /**
     * 保存操作记录
     *
     * @param entity
     * @return
     */
    public int saveOperationLog(OperationLog entity) {
        int rz = operationLogManager.saveOperationLog(entity);
        if (rz > 0) {
            if (StringUtils.isNotBlank(entity.getUserId())) {
                // 删除缓存用户认证信息
                JedisUtils.delObject(Global.USER_AUTH_PREFIX + entity.getUserId());
            } else {
                logger.error("用户id为空");
            }
        }
        return rz;
    }

    /**
     * 更新申请单变数据
     *
     * @param loanApply
     * @return
     */
    public int updateLoanApplyInfo(LoanApply loanApply) {
        //XianJinCardUtils.rongPayFeedback(loanApply.getId());
        //XianJinCardUtils.rongApproveFeedback(loanApply.getId());
        loanApply.preUpdate();// code y0706
        return dao.update(loanApply);
    }

    public List<ApplyListVO> getLoanApplyList(Page page, ApplyListOP op) {
        return dao.getLoanApplyList(page, op);
    }

    public int countApply(String userId) {
        return dao.countApply(userId);
    }

    /**
     * 根据用户id获取当前（未完结）申请的申请编号
     *
     * @param userId
     * @return
     */
    public String getCurApplyIdByUserId(String userId) {
        return dao.getCurApplyIdByUserId(userId);
    }

    /**
     * 生成申请编号
     *
     * @param userId
     * @param productType
     */
    private String genApplyNo(String userId, String productType) {
        String cacheKey = userId + Global.APPLY_NO_SUFFIX;
        String id = JedisUtils.get(cacheKey);
        if (StringUtils.isBlank(id)) {
            // 生成申请id
            id = IdUtils.getApplyId(productType);
            // 缓存申请编号
            if (StringUtils.isNotBlank(userId)) {
                JedisUtils.set(cacheKey, id, Global.APPLY_NO_CACHESECONDS);
            }
        }
        return id;
    }

    /**
     * 录入贷款申请
     *
     * @param loanApply
     * @return
     */
    private int saveLoanApplyInfoLocal(LoanApply loanApply) {
        // 不自动生成主键
        loanApply.setIsNewRecord(true);
        loanApply.preInsert();
        loanApply.setApplyDate(DateUtils.getYYYYMMDD2Int(new Date()));
        loanApply.setApplyTime(new Date());
        return saveLoanApplyInfo(loanApply);
    }

    /**
     * 获取或生成申请编号
     *
     * @param userId
     * @return
     */
    public ApplyIdVO getApplyId(String userId) {
        // 构造返回对象
        ApplyIdVO rz = new ApplyIdVO();
        // 获取未完结的申请
        String applyId = dao.getCurApplyIdByUserId(userId);
        if (StringUtils.isBlank(applyId)) {
            // 生成申请编号
            applyId = genApplyNo(userId, Global.DEFAULT_PRODUCT_TYPE);
            rz.setApplyId(applyId);
            rz.setNewApplyId(true);
            logger.info("genApplyNo:[{}]", applyId);
            return rz;
        }
        rz.setApplyId(applyId);
        rz.setNewApplyId(false);
        return rz;
    }

    /**
     * 根据id统计当前记录
     *
     * @param id
     * @return
     */
    public int countById(String id) {
        return dao.countById(id);
    }

    public List<LoanApply> findByIdList(List<String> idList) {
        return dao.findByIdList(idList);
    }

    public int updateOverdue(List<String> idList) {
        return dao.updateOverdue(idList);
    }

    /**
     * 根据用户id查询初始与未完结的申请记录
     *
     * @param userId
     * @return
     */
    public LoanApply getInitUnFinishLoanApplyByUserId(String userId) {
        return dao.getInitUnFinishLoanApplyByUserId(userId);
    }

    /**
     * 根据用户id统计最近一月申请次数
     *
     * @param countApplyOP
     * @return
     */
    public int countApplyNearMonth(CountApplyOP countApplyOP) {
        return dao.countApplyNearMonth(countApplyOP);
    }

    /**
     * 根据用户id查询最后一次已完结贷款申请
     *
     * @param userId
     * @return
     */
    public LoanApply getLastFinishApplyByUserId(String userId) {
        return dao.getLastFinishApplyByUserId(userId);
    }

    /**
     * 根据用户id统计正常还款完结的次数
     *
     * @param userId
     * @return
     */
    public int countOverLoanByRepay(String userId) {
        return dao.countOverLoanByRepay(userId);
    }

    /**
     * 统计查询审核管理列表
     *
     * @return
     */
    public Map<String, Object> getCheckAllotListTotal(String startDate, String endDate, List<String> auditorId,
                                                      String type) {
        return dao.getCheckAllotListTotal(startDate, endDate, auditorId, type);
    }

    ;

    /*
     * 更新商户id
     */
    public void updateCompanyId(String companyId, String id) {
        dao.updateCompanyId(companyId, id);
        dao.updateUserCompanyId(companyId, id);
    }

    public int updateForDelay(LoanApply loanApply) {
        return dao.updateForDelay(loanApply);
    }

    /**
     * 未结清数量
     *
     * @param userId
     * @return
     */
    public int countUnFinishLoanApplyByUserId(String userId) {
        return dao.countUnFinishLoanApplyByUserId(userId);
    }

    /**
     * 所有未结清订单
     *
     * @param userId
     * @return
     */
    public List<LoanApply> findUnFinishListByUserId(String userId) {
        return dao.findUnFinishListByUserId(userId);
    }

    /**
     * 审核中数量
     *
     * @param userId
     * @return
     */
    public int countApprovingByUserId(String userId) {
        return dao.countApprovingByUserId(userId);
    }

    /**
     * 未结清金额
     *
     * @param userId
     * @return
     */
    public BigDecimal getUnFinishLoanAmtByUserId(String userId) {
        return dao.getUnFinishLoanAmtByUserId(userId);
    }

    public LoanApply getAuditLoanApply(String userId) {
        return dao.getAuditLoanApply(userId);
    }

    /**
     * 根据用户id查找订单
     */
    public List<LoansApplySummaryVO> getLoanApplyListByUserId(String userId) {
        return dao.getLoanApplyListByUserId(userId);
    }

    /**
     * 根据用户id查询累计被拒次数
     *
     * @param userId
     * @return
     */
    public int countRejectByUserId(String userId) {
        return dao.countRejectByUserId(userId);
    }

    public int countApproveByCompanyId(String companyId, String approveMonth) {
        return dao.countApproveByCompanyId(companyId, approveMonth);
    }

    public Map<String, Object> findContractInfo(String contNo) {
        return dao.findContractInfo(contNo);
    }

    public List<Map<String, Object>> custApplyList(String id) {
        return dao.custApplyList(id);
    }

    public List<ApproveFeedbackOP> getLoanApplyThirdList(Page page, ApplyThirdOP applyThirdOP) {
        return dao.getLoanApplyThirdList(page, applyThirdOP);
    }

    /**
     * 查询最近一次还款的申请
     *
     * @param userId
     * @param productId
     * @return
     */
    public LoanApply getLastRepayedByUserId(String userId, String productId) {
        return dao.getLastRepayedByUserId(userId, productId);
    }

    /**
     * 获取状态为带推送 status = 410 的申请数据用于 营销分配
     *
     * @return
     */
    public List<LoanApply> getLoanApplyForMarket() {
        return dao.getLoanApplyForMarket();
    }

    /**
     * 统计初审员回款率
     *
     * @return
     */
    public List<Map<String, Object>> getReturnRate(String startDate, String endDate, String auditorId, String channel,
                                                   String productId, String termType) {
        return dao.getReturnRate(startDate, endDate, auditorId, channel, productId, termType);
    }

    ;

    /**
     * 查询未完结订单
     *
     * @param mobile 手机号
     * @return
     */
    public int countUnFinishApplyByMobile(String mobile) {
        return dao.countUnFinishApplyByMobile(mobile);
    }

    public int updateNoAnswer(String applyId, Integer callNum, String remark) {
        return dao.updateNoAnswer(applyId, callNum, remark);
    }

    public List<ApplyListVO> getLoanApplyList1(ApplyListOP op) {
        return dao.getLoanApplyList1(op);
    }
    
    public int countUnFinishByMobileAndIdNo(String idNo,String mobile) {
    	return dao.countUnFinishByMobileAndIdNo(idNo, mobile);
    }
    
    /**
     * 根据ID查询是否存在9月26号之前申请的单期订单
     * @param applyId
     * @return
     */
    public int getCountByCondition(String applyId){
    	return dao.getCountByCondition(applyId);
    }
    

    public int updateByCriteriaSelective(LoanApply loanApply, Criteria criteria) {
        return dao.updateByCriteriaSelective(loanApply, criteria);
    }
    
    /**
     * 更新借款渠道
     * @param loanApply
     * @return
     */
    public int updatePayChannel(LoanApply loanApply) {
        if (null != loanApply) {
            loanApply.preUpdate();
        }
        return dao.updatePayChannel(loanApply);
    }   
    
    /**
	 * 根据过滤条件查询所有符合要求的数据列表
	 * @param criteria
	 * @return
	 */
	public List<LoanApply> findAllByCriteria(Criteria criteria){
		return dao.findAllByCriteria(criteria);
	}

    public List<ApplyListVO> getLoanApplyByApi(Page page, ApplyListOP op) {
        return dao.getLoanApplyByApi(page, op);
    }

    public List<LoanApply> getPassNoBindCardList(){
	    return dao.getPassNoBindCardList();
    }
}
