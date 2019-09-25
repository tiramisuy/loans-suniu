package com.rongdu.loans.statistical.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.loans.loan.entity.OperationLogLater;
import com.rongdu.loans.loan.manager.OperationLogLaterManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.CharsetUtils;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.OperationLogManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.statistical.dto.ZhimaCreditStatisticsDTO;
import com.rongdu.loans.statistical.dto.ZhimaCreditStatisticsExtParamDTO;
import com.rongdu.loans.statistical.dto.ZhimaCreditStatisticsResponseDTO;
import com.rongdu.loans.statistical.entity.ZhimaCreditStatistics;
import com.rongdu.loans.statistical.enums.SceneStatus;
import com.rongdu.loans.statistical.manager.CreditStatisticsManager;
import com.rongdu.loans.statistical.service.ZhimaStatisticalService;

/**
 * Created by zhangxiaolong on 2017/8/29.
 */
@Service("zhimaStatisticalService")
public class ZhimaStatisticalServiceImpl extends BaseService implements ZhimaStatisticalService {

    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private OperationLogManager operationLogManager;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private CreditStatisticsManager creditStatisticsManager;

    @Autowired
    private OperationLogLaterManager operationLogLaterManager;

    private FileServerClient fileServerClient = new FileServerClient();

    private String partnerId = Global.getConfig("partnerId");
    private String verifyKey = Global.getConfig("verifyKey");
    private String linkedMerchantId = Global.getConfig("linkedMerchantId");
    private String url = Global.getConfig("push.zhima.url");


    @Override
    public TaskResult collect() {
        Date date = DateUtils.addDay(new Date(), -1);
        return collect(date);
    }

    @Override
    @Transactional
    public TaskResult collect(Date date){
        logger.info("统计ZhimaCreditStatistics数据开始。date={}", DateUtils.formatDateTime(date));
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        try {
            if (date == null){
                date = DateUtils.addDay(new Date(), -1);
            }
            /** 1.统计还款数据，区分履约、违约和结清状态 */
            /** 违约数据 */
            List<RepayPlanItem> defaultList = getDefaultItemList(date);
            /** 还款的数据 */
            List<RepayPlanItem> repayList = getRepayList(date);
            List<ZhimaCreditStatistics> itemList = item2Zhimadto(date, defaultList, repayList);

            /** 2.统计完成审批的数据 & 统计放款的数据 */
            String start = DateUtils.formatDate(DateUtils.getDayBegin(date), DateUtils.FORMAT_LONG);
            String end = DateUtils.formatDate(DateUtils.getDayEnd(date), DateUtils.FORMAT_LONG);
            List<OperationLogLater> approveList = operationLogLaterManager.collectLog(start, end);
            List<ZhimaCreditStatistics> logList = log2Zhimadto(date, approveList);

            /** 3.补充字段并保存数据 */
            List<ZhimaCreditStatistics> all = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(itemList)){
                all.addAll(itemList);
            }
            if (CollectionUtils.isNotEmpty(logList)){
                all.addAll(logList);
            }
            if (CollectionUtils.isEmpty(all)){
                long endTime = System.currentTimeMillis();
                logger.info("统计ZhimaCreditStatistics数据结束，暂无数据。执行耗时{}", endTime - starTime);
                return new TaskResult(success, fail);
            }
            List<String> applyIdList = new ArrayList<>();
            for (ZhimaCreditStatistics zhimaCreditStatistics : all){
                applyIdList.add(zhimaCreditStatistics.getApply_id());
            }
            List<LoanApply> loanApplyList = loanApplyManager.findByIdList(applyIdList);
            for (ZhimaCreditStatistics item : all){
                for (LoanApply apply : loanApplyList){
                    if (item.getApply_id().equals(apply.getId())){
                        item.setUser_credentials_no(apply.getIdNo());
                        item.setUser_name(apply.getUserName());
                        item.setCreate_amt(apply.getApproveAmt().toString());
                        item.preInsert();
                        item.setOrder_no(item.getId());
                        item.setPush_status("0");
                    }
                }
            }
            creditStatisticsManager.insertBatch(all);
            success++;
        }catch (Exception e){
            logger.error("统计ZhimaCreditStatistics数据失败", e);
            fail++;
        }
        long endTime = System.currentTimeMillis();
        logger.info("统计ZhimaCreditStatistics数据结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 上送某日的芝麻接口数据
     * @param date
     */
    @Transactional
    public TaskResult pushZhimaCreditStatistics(Date date){
        logger.info("上送ZhimaCreditStatistics数据开始。date={}", DateUtils.formatDateTime(date));
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        try{
            if (date == null){
                date = new Date();
            }
            Date begin = DateUtils.getDayBegin(date);
            Date end = DateUtils.getDayEnd(date);
            /** 1.查询当日需要发送的数据 */
            List<ZhimaCreditStatistics> records = creditStatisticsManager.getStatisticsData(begin, end, "0");
            if (CollectionUtils.isEmpty(records)){
                long endTime = System.currentTimeMillis();
                logger.info("上送ZhimaCreditStatistics数据结束，暂无数据。执行耗时{}", endTime - starTime);
                return new TaskResult(success, fail);
            }
            Map<String,Object> recordsMap=new HashMap<String,Object>();
            recordsMap.put("records", records);
            String fileBodyText = JsonMapper.getInstance().toJson(recordsMap);
            /** 2.文件服务器保存上送json文件 */
            saveJsonFile(fileBodyText, date);
            /** 3.组装 */
            ZhimaCreditStatisticsDTO dto = assembly(fileBodyText, String.valueOf(records.size()));
            if (dto == null){
                fail++;
                return new TaskResult(success, fail);
            }
            /** 4.上送数据 */
            String respString = RestTemplateUtils.getInstance().postForJsonRaw(url, JsonMapper.getInstance().toJson(dto));
            ZhimaCreditStatisticsResponseDTO responseDTO = JsonMapper.getInstance().fromJson(respString, ZhimaCreditStatisticsResponseDTO.class);
            /** 5.修改上送状态  1推送成功，2推送失败 */
            List<String> ids = new ArrayList<>();
            for (ZhimaCreditStatistics item : records){
                ids.add(item.getOrder_no());
            }
            String pushStatus = responseDTO.success() ? "1" : "2";
            creditStatisticsManager.updatePushStatusBatch(ids, pushStatus);
            /** 6.根据上送结果分析任务是否成功 */
            if (!responseDTO.success()){
                fail++;
            }else {
                success++;
            }
        }catch (Exception e){
            logger.error("上送ZhimaCreditStatistics数据失败", e);
            fail++;
        }
        long endTime = System.currentTimeMillis();
        logger.info("上送ZhimaCreditStatistics数据结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 组装参数
     * @param fileBodyText
     * @param records
     * @return
     */
    private ZhimaCreditStatisticsDTO assembly(String fileBodyText, String records){
        try {
            byte[] file = fileBodyText.getBytes(CharsetUtils.DEFAULT_CHARSET);
            ZhimaCreditStatisticsExtParamDTO extParamDTO = new ZhimaCreditStatisticsExtParamDTO();
            extParamDTO.setRecords(records);
            extParamDTO.setPrimaryKeyColumns("order_no");
            extParamDTO.setFile(file);

            ZhimaCreditStatisticsDTO dto = new ZhimaCreditStatisticsDTO();
            dto.setPartnerId(partnerId);
            dto.setVerifyKey(verifyKey);
            dto.setLinkedMerchantId(linkedMerchantId);
            dto.setExtParam(extParamDTO);
            return dto;
        } catch (UnsupportedEncodingException e) {
            logger.error("上送ZhimaCreditStatistics数据失败", e);
            return null;
        }
    }

    /**
     * 保存json文件
     * @param fileBodyText
     * @param date
     */
    private void saveJsonFile(String fileBodyText, Date date){
        try {
            UploadParams params = new UploadParams();
            params.setBizCode(FileBizCode.ZHIMA_PUSH.getBizCode());
            String origName = FileBizCode.ZHIMA_PUSH.getBizCode() + DateUtils.formatDate(date, DateUtils.FORMAT_INT_MINITE);
            params.setApplyId("0");
            params.setOrigName(origName);
            params.setSource("5");
            params.setIp("127.0.0.1");
            params.setUserId("system");
            String respString = fileServerClient.uploadDocumentString(fileBodyText, "json",params);
            logger.info("保存json文件成功，origName={}", origName);
        }catch (Exception e){
            logger.error("保存json文件发生异常", e);
        }catch (Error e){
            logger.error("保存json文件发生错误", e);
        }
    }

    /**
     * 查询违约数据
     * @param date
     * @return
     */
    private List<RepayPlanItem> getDefaultItemList(Date date) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("repay_date", DateUtils.formatDate(date, DateUtils.FORMAT_SHORT)));
        criteria.and(Criterion.eq("status", 0));
        criteria.and(Criterion.eq("del", 0));
        return repayPlanItemManager.findAllByCriteria(criteria);
    }
    /**
     * 查询还款数据
     * @param date
     * @return
     */
    private List<RepayPlanItem> getRepayList(Date date) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("actual_repay_date", DateUtils.formatDate(date, DateUtils.FORMAT_SHORT)));
        criteria.and(Criterion.eq("status", 1));
        criteria.and(Criterion.eq("del", 0));
        return repayPlanItemManager.findAllByCriteria(criteria);
    }

    private List<ZhimaCreditStatistics> log2Zhimadto(Date date, List<OperationLogLater> approveList){
        if (CollectionUtils.isEmpty(approveList)){
            return Collections.EMPTY_LIST;
        }
        List<ZhimaCreditStatistics> recordList = new ArrayList<>();
        String dateStr = DateUtils.formatDate(date, DateUtils.FORMAT_SHORT);
        for (OperationLogLater log : approveList){
            ZhimaCreditStatistics zhimaCreditStatistics = new ZhimaCreditStatistics();
            zhimaCreditStatistics.setBiz_date(dateStr);
            zhimaCreditStatistics.setScene_status(getSceneStatus(log));
            zhimaCreditStatistics.setInstallment_due_date(dateStr);
            zhimaCreditStatistics.setOverdue_amt("0");
            zhimaCreditStatistics.setGmt_ovd_date(null);
            zhimaCreditStatistics.setApply_id(log.getApplyId());
            zhimaCreditStatistics.setUser_id(log.getUserId());
            recordList.add(zhimaCreditStatistics);
        }
        return recordList;
    }

    /**
     * 合并违约数据和还款数据，并返回需要保存的数据
     * @param date
     * @param defaultList
     * @param repayList
     * @return
     */
    private List<ZhimaCreditStatistics> item2Zhimadto(Date date, List<RepayPlanItem> defaultList, List<RepayPlanItem> repayList){
        List<RepayPlanItem> itemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(defaultList)){
            itemList.addAll(defaultList);
        }
        if (CollectionUtils.isNotEmpty(repayList)){
            itemList.addAll(repayList);
        }
        if (CollectionUtils.isEmpty(itemList)){
            return Collections.EMPTY_LIST;
        }
        List<ZhimaCreditStatistics> recordList = new ArrayList<>();
        String dateStr = DateUtils.formatDate(date, DateUtils.FORMAT_SHORT);
        for (RepayPlanItem item : itemList){
            ZhimaCreditStatistics zhimaCreditStatistics = new ZhimaCreditStatistics();
            zhimaCreditStatistics.setBiz_date(dateStr);
            zhimaCreditStatistics.setScene_status(getSceneStatus(item));
            boolean defaultStatus =  SceneStatus.DEFAULT_STATUS.getStatus().equals(zhimaCreditStatistics.getScene_status());
            zhimaCreditStatistics.setInstallment_due_date(dateStr);
            String overdueAmt = defaultStatus ?
                    item.getTotalAmount().setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() : "0";
            zhimaCreditStatistics.setOverdue_amt(overdueAmt);
            zhimaCreditStatistics.setGmt_ovd_date(defaultStatus ? dateStr : null);
            zhimaCreditStatistics.setApply_id(item.getApplyId());
            zhimaCreditStatistics.setUser_id(item.getUserId());
            recordList.add(zhimaCreditStatistics);
        }
        return recordList;
    }

    /**
     *  返回场景状态
     * @param object
     * @return
     */
    private String getSceneStatus(Object object){

        if (object instanceof RepayPlanItem){
            RepayPlanItem item = (RepayPlanItem)object;
            if (item.getStatus().equals(ApplyStatusEnum.UNFINISH.getValue())){
                return SceneStatus.DEFAULT_STATUS.getStatus();
            }
            if (item.getStatus().equals(ApplyStatusEnum.FINISHED.getValue())
                    && item.getThisTerm().equals(item.getTotalTerm())){
                return SceneStatus.FINISH_STATUS.getStatus();
            }
            if (item.getStatus().equals(ApplyStatusEnum.FINISHED.getValue())
                    && !item.getThisTerm().equals(item.getTotalTerm())){
                return SceneStatus.NORMAL_STATUS.getStatus();
            }
        }

        if (object instanceof OperationLog){
            OperationLog log = (OperationLog)object;
            if (ApplyStatusLifeCycleEnum.AOTUCHECK_PASS.getValue().equals(log.getStatus())
                    || ApplyStatusLifeCycleEnum.MANUALCHECK_PASS.getValue().equals(log.getStatus())){
                return SceneStatus.PASS_STATUS.getStatus();
            }
            if (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(log.getStatus())
                    || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS.getValue().equals(log.getStatus())){
                return SceneStatus.NO_PASS_STATUS.getStatus();
            }
            if (ApplyStatusLifeCycleEnum.SIGNED.getValue().equals(log.getStatus())){
                return SceneStatus.NO_PASS_STATUS.getStatus();
            }
        }
        return null;
    }
}
