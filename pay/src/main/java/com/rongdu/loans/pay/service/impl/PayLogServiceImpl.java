/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.koudai.vo.KDwithdrawRecodeVO;
import com.rongdu.loans.loan.option.GeneratorEquitableAssignmentDataOP;
import com.rongdu.loans.loan.option.WithdrawDetailListOP;
import com.rongdu.loans.loan.service.CarefreeCounterfoilService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.vo.PactRecord;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.WithdrawDetailListVO;
import com.rongdu.loans.pay.entity.PayLog;
import com.rongdu.loans.pay.manager.PayLogManager;
import com.rongdu.loans.pay.utils.WithdrawErrInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL;

/**
 * 提现/代付-业务逻辑实现类
 *
 * @author zhangxiaolong
 * @version 2017-07-10
 */
@Service("payLogService")
public class PayLogServiceImpl extends BaseService implements PayLogService {

    /**
     * 提现/代付-实体管理接口
     */
    @Autowired
    private PayLogManager payLogManager;
    @Autowired
    private CarefreeCounterfoilService carefreeCounterfoilService;

    @Override
    public Page<WithdrawDetailListVO> withdrawList(WithdrawDetailListOP op) {
        Page voPage = new Page(op.getPageNo(), op.getPageSize());
        List<WithdrawDetailListVO> list = payLogManager.withdrawList(voPage, op);
        setOperations(list);
        voPage.setList(list);
        return voPage;
    }

    @Override
    public List<WithdrawDetailListVO> exportWithdrawList(@NotNull(message = "参数不能为空") WithdrawDetailListOP op) {
        return payLogManager.exportWithdrawList(op);
    }

    /**
     * 如果存在提现失败的订单数据，并且该订单之后提现成功，这将失败数据的最新状态更新为成功，页面不再显示提现按钮
     *
     * @param list
     */
    private void setOperations(List<WithdrawDetailListVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<String> successList = new ArrayList<String>();
        List<String> applyIdList = new ArrayList<String>();
        for (WithdrawDetailListVO vo : list) {
            applyIdList.add(vo.getApplyId());
//            if (isBaofooPaySuccessApplyId(vo.getChlCode(), vo.getStatus())) {
//                successList.add(vo.getApplyId());
//            }
            if (isTonglianPaySuccessApplyId(vo.getChlCode(), vo.getStatus())) {
                successList.add(vo.getApplyId());
            }
        }
        List<String> payLogSuccessList = payLogManager.successLogFilter(applyIdList);
        if (CollectionUtils.isNotEmpty(payLogSuccessList)) {
            successList.addAll(payLogSuccessList);
        }
        for (String successApplyId : successList) {
            for (WithdrawDetailListVO vo : list) {
                // 如果存在提现失败的订单数据，并且该订单之后提现成功，这将失败数据的最新状态更新为成功，页面不再显示提现按钮
                if (StringUtils.equals(vo.getApplyId(), successApplyId)) {
                    vo.setLastStatus(ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue().toString());
                }
            }
        }
    }

    private boolean isBaofooPaySuccessApplyId(String chlCode, String status) {
        if ("BAOFOO".equals(chlCode)) {
            if (BaofooWithdrawServiceImpl.BAOFOO_PAY_UNSOLVED_STATUS.contains(status)
                    || BaofooWithdrawServiceImpl.BAOFOO_PAY_SUCCESS_STATUS.contains(status)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTonglianPaySuccessApplyId(String chlCode, String status) {
        if ("TONGLIAN_LOAN".equals(chlCode)) {
            if (TonglianWidthdrawServiceImpl.TONGLIAN_PAY_UNSOLVED_STATUS.contains(status) || TonglianWidthdrawServiceImpl.TONGLIAN_LOAN_UNSOLVED_STATUS.contains(status)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PayLogVO get(String id) {
        PayLog entity = payLogManager.get(id);
        PayLogVO vo = null;
        if (entity != null) {
            vo = BeanMapper.map(entity, PayLogVO.class);
        }
        return vo;
    }

    @Override
    public int save(PayLogVO vo) {
        PayLog entity = BeanMapper.map(vo, PayLog.class);
        return payLogManager.insert(entity);
    }

    @Override
    public int update(PayLogVO vo) {
        PayLog entity = BeanMapper.map(vo, PayLog.class);
        return payLogManager.update(entity);
    }

    @Override
    public PayLogVO findByOrigOrderNo(String origOrderNo) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("orig_order_no", origOrderNo));
        PayLog entity = payLogManager.getByCriteria(criteria);
        PayLogVO vo = BeanMapper.map(entity, PayLogVO.class);
        return vo;
    }

    @Override
    public int updatePayResult(PayLogVO vo) {
        PayLog entity = BeanMapper.map(vo, PayLog.class);
        return payLogManager.update(entity);
    }

    @Override
    public PayLogVO findByChlOrderNo(String orderNo) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("chl_order_no", orderNo));
        PayLog entity = payLogManager.getByCriteria(criteria);
        PayLogVO vo = BeanMapper.map(entity, PayLogVO.class);
        return vo;
    }

    @Override
    public List<PayLogVO> findUnsolvedOrders() {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.ne("status", ErrInfo.SUCCESS.getCode()));
        criteria.and(Criterion.eq("tx_type", "withdraw"));
        // 一小时间以内的订单
        Date date = new Date(System.currentTimeMillis() - 1 * 3600 * 1000);
        criteria.and(Criterion.ge("create_time", DateUtils.formatDateTime(date)));
        List<PayLog> list = payLogManager.findAllByCriteria(criteria);
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }

    @Override
    public Double findPayedAmt(String origOrderNo) {
        Double amt = payLogManager.findPayedAmt(origOrderNo);
        if (amt == null) {
            amt = 0D;
        }
        return amt;
    }

    @Override
    public int delete(PayLogVO vo) {
        PayLog entity = BeanMapper.map(vo, PayLog.class);
        return payLogManager.delete(entity);
    }

    @Override
    public int findPaymentRecord(String applyId) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("apply_id", applyId));
        criteria.and(Criterion.eq("status", WithdrawErrInfo.E0000.getCode()));
        criteria.and(Criterion.eq("tx_type", "withdraw"));
        List<PayLog> list = payLogManager.findAllByCriteria(criteria);
        return list != null ? list.size() : 0;
    }

    @Override
    public int findUnsolvedXfWithdrawRecord(String applyId) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("apply_id", applyId));
        criteria.and(Criterion.eq("status", CASH_WITHDRAWAL.getValue().toString()));
        criteria.and(Criterion.eq("chl_code", Global.XIANFENG_CHANNEL_CODE));
        criteria.and(Criterion.eq("tx_type", "withdraw"));
        List<PayLog> list = payLogManager.findAllByCriteria(criteria);
        return list != null ? list.size() : 0;
    }

    @Override
    public List<PayLogVO> findPayUnsolvedOrders() {
        List<PayLog> list = payLogManager.findPayUnsolvedOrders();
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }

    @Override
    public List<PayLogVO> findBaofooPayUnsolvedOrders(List<String> statusList) {
        List<PayLog> list = payLogManager.findBaofooPayUnsolvedOrders(statusList);
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }


    @Override
    public List<PayLogVO> findTonglianPayUnsolvedOrders(List<String> statusList) {
        List<PayLog> list = payLogManager.findTonglianPayUnsolvedOrders(statusList);
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }

    @Override
    public List<PayLogVO> findTonglianLoanPayUnsolvedOrders(List<String> statusList) {
        List<PayLog> list = payLogManager.findTonglianLoanPayUnsolvedOrders(statusList);
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }

    @Override
    public List<PayLogVO> findTRBaofooPayUnsolvedOrders(List<String> statusList) {
        List<PayLog> list = payLogManager.findTRBaofooPayUnsolvedOrders(statusList);
        List<PayLogVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            voList = BeanMapper.mapList(list, PayLogVO.class);
        }
        return voList;
    }

    @Override
    public String generatorContract(String payLogId) {
        if (StringUtils.isBlank(payLogId))
            throw new IllegalArgumentException("[payLogId]参数不能为空!");
        PayLogVO payLog = get(payLogId);
        if (payLog == null)
            throw new IllegalArgumentException("[payLogId=" + payLogId + "]不存在!");
        String applyId = payLog.getApplyId();
        PactRecord pactRecord = carefreeCounterfoilService.generate(payLog.getUserId(), applyId, payLog.getId(),
                payLog.getTxTime());
        if (pactRecord != null) {
            payLog.setContractUrl(pactRecord.getLoanRecordNo() + "," + pactRecord.getShoppingRecordNo());
            update(payLog);
            return payLog.getContractUrl();
        } else {
            throw new RuntimeException("生成合同失败");
        }
    }


    @Override
    public String generateEquitableAssignment(WithdrawDetailListOP op) {
        if (op == null) op = new WithdrawDetailListOP();
        op.setStatus(ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue());
        op.setChlCode(Global.BAOFOO_CHANNEL_CODE);
        List<WithdrawDetailListVO> list = payLogManager.equitableAssignmentList(op);
        if (list.isEmpty()) throw new RuntimeException("没有找到债权转让信息");
        List<GeneratorEquitableAssignmentDataOP> opList = new ArrayList<>();
        for (WithdrawDetailListVO withdrawDetailListVO : list) {
            GeneratorEquitableAssignmentDataOP generatorEquitableAssignmentDataOP = new GeneratorEquitableAssignmentDataOP();
            generatorEquitableAssignmentDataOP.setId(withdrawDetailListVO.getId());
            generatorEquitableAssignmentDataOP.setIdNo(withdrawDetailListVO.getIdNo());
            generatorEquitableAssignmentDataOP.setName(withdrawDetailListVO.getName());
            generatorEquitableAssignmentDataOP.setTxAmt(String.valueOf(withdrawDetailListVO.getTxAmt().intValue()));
            String pattern = "yyyy/MM/dd";
            generatorEquitableAssignmentDataOP.setLoanStartDate(DateUtils.formatDate(withdrawDetailListVO.getLoanStartDate(), pattern));
            generatorEquitableAssignmentDataOP.setTxDate(
                    DateUtils.formatDate(new Date(withdrawDetailListVO.getLoanStartDate().getTime() + 24 * 60 * 60 * 1000), pattern)); // 债权转让日期比放款日期多一天
            generatorEquitableAssignmentDataOP.setLoanEndDate(DateUtils.formatDate(withdrawDetailListVO.getLoanEndDate(), pattern));
            opList.add(generatorEquitableAssignmentDataOP);
        }
        return carefreeCounterfoilService.generateEquitableAssignment(opList);
    }

    @Override
    public Page getBFPayCount(KDPayCountOP payOP) {
        Page page = new Page(payOP.getPageNo(), payOP.getPageSize());
        List<Map<String, Object>> list = null;
        list = payLogManager.getBFPayCount(payOP);
        page.setCount(list.size());
        page.setList(list);
        return page;
    }

    @Override
    public Long countBaofooPayUnsolvedAndSuccess(String applyId, List<String> statusList) {
        return payLogManager.countBaofooPayUnsolvedAndSuccess(applyId, statusList);
    }

    @Override
    public Long countTonglianPayUnsolvedAndSuccess(String applyId, List<String> statusList) {
        return payLogManager.countTonglianPayUnsolvedAndSuccess(applyId, statusList);
    }

    @Override
    public PayLogVO findWithdrawAmount(String userId) {
        PayLog payLog = payLogManager.findWithdrawAmount(userId);
        if (null != payLog) {
            PayLogVO map = BeanMapper.map(payLog, PayLogVO.class);
            return map;
        }
        return null;
    }

    @Override
    public List<KDwithdrawRecodeVO> findHJSWithdrawRecode(String userId) {
        Criteria criteria = new Criteria();
        criteria.and(Criterion.eq("user_id", userId));
        criteria.and(Criterion.eq("chl_code", "HANJS"));
        List<PayLog> list = payLogManager.findAllByCriteria(criteria);
        List<KDwithdrawRecodeVO> retList = new ArrayList<>();
        for (PayLog log : list) {
            KDwithdrawRecodeVO recodeVO = new KDwithdrawRecodeVO();
            recodeVO.setId(log.getId());
            recodeVO.setApplyId(log.getApplyId());
            if (null != log.getSuccAmt()) {
                recodeVO.setMoney(log.getSuccAmt().setScale(2).toString());
            } else {
                recodeVO.setMoney("0");
            }
            switch (log.getStatus()) {
                case "512":
                    recodeVO.setStatus(0);
                    break;
                case "514":
                    recodeVO.setStatus(1);
                    break;
                default:
                    recodeVO.setStatus(2);
                    break;
            }
            if (recodeVO.getStatus() != null) {
                recodeVO.setStatusDesc(recodeVO.getStatus() == 1 ? "已提现" : recodeVO.getStatus() == 2 ? "提现中" : "未提现");
            }

            recodeVO.setDate(DateUtils.formatDate(log.getCreateTime(), DateUtils.FORMAT_LONG));
            retList.add(recodeVO);
        }
        return retList;
    }


    @Override
    public BigDecimal sumHanjsCurrPayedAmt() {
        return payLogManager.sumHanjsCurrPayedAmt();
    }

    @Override
    public PayLogVO findWithdrawLogByApplyId(String applyId) {
        PayLog payLog = payLogManager.findWithdrawLogByApplyId(applyId);
        if (null != payLog) {
            PayLogVO map = BeanMapper.map(payLog, PayLogVO.class);
            return map;
        }
        return null;
    }
}