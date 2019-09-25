package com.rongdu.loans.anrong.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.anrong.common.HttpsUtils;
import com.rongdu.loans.anrong.op.IterationShareOP;
import com.rongdu.loans.anrong.op.MSPReportOP;
import com.rongdu.loans.anrong.service.AnRongService;
import com.rongdu.loans.anrong.vo.IterationShareVO;
import com.rongdu.loans.anrong.vo.MSPReprtVO;
import com.rongdu.loans.anrong.vo.ShareResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 安融-业务接口实现类
 *
 * @author fy
 * @version 2019-06-17
 */
@Service("anRongService")
public class AnRongServiceImpl implements AnRongService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public MSPReprtVO getMSPReport(LoanApplySimpleVO vo,String regCity) {
        // 获取秘钥以及url
        String member = Global.getConfig("anrong.member");
        String sign = Global.getConfig("anrong.sign");
        String url = Global.getConfig("anrong.msp.url");

        // 配置传输参数
        MSPReportOP op = new MSPReportOP();
        op.setMember(member);
        op.setSign(sign);
        op.setCustomerName(vo.getUserName());
        op.setPaperNumber(vo.getIdNo());
        op.setLoanId(vo.getApplyId());
        op.setLoanTypeDesc("信用借款");
        op.setApplyAssureType("D");
        op.setApplyLoanMoney(vo.getApproveAmt().toString());
        // 此处后期增加月度产品注意期限
        if (vo.getApproveTerm().intValue() <= 31) {
            op.setApplyLoanTimeLimit("1");
        } else {
            op.setApplyLoanTimeLimit(vo.getTerm().toString());
        }
        op.setApplyDate(DateUtils.formatDate(DateUtils.parse(vo.getApplyTime()), DateUtils.FORMAT_SHORT));
        op.setApplyCreditCity(regCity);
        op.setQuickRisk("1");
        op.setPhone(vo.getMobile());

        // 请求字符串
        Map<String, String> describe = BeanMapper.describe(op);
        logger.debug("安融-MSP报告获取-请求地址：{}", url);
        logger.debug("安融-MSP报告获取-请求报文：{}", describe);
        // 发起请求
        String result = null;
        try {
            result = HttpsUtils.sendGet(url, describe);
        } catch (Exception e) {
            logger.error("安融-报告获取异常-{}",e);
        }
        logger.debug("安融-MSP报告获取-应答结果：{}", result);
        MSPReprtVO mspReprtVO = (MSPReprtVO) JsonMapper.fromJsonString(result, MSPReprtVO.class);
        return mspReprtVO;
    }

    @Override
    public IterationShareVO getIterationShareList(IterationShareOP op) {
        // 获取秘钥以及url
        String member = Global.getConfig("anrong.member");
        String sign = Global.getConfig("anrong.sign");
        String url = Global.getConfig("anrong.iteration.url");

        // 配置传输参数
        op.setMember(member);
        op.setSign(sign);

        // 请求字符串
        Map<String, String> describe = BeanMapper.describe(op);
        logger.debug("安融-迭代获取待共享列表-请求地址：{}", url);
        logger.debug("安融-迭代获取待共享列表-请求报文：{}", describe);
        // 发起请求
        String result = null;
        try {
            result = HttpsUtils.sendGet(url, describe);
        } catch (Exception e) {
            logger.error("安融-迭代获取待共享列表异常-{}",e);
        }
        logger.debug("安融-迭代获取待共享列表-应答结果：{}", result);
        IterationShareVO iterationShareVO = (IterationShareVO) JsonMapper.fromJsonString(result, IterationShareVO.class);
        return iterationShareVO;
    }

    @Override
    public ShareResultVO sendShareApproveResult(Map<String, String> param) {
        // 获取秘钥以及url
        String member = Global.getConfig("anrong.member");
        String sign = Global.getConfig("anrong.sign");
        String url = Global.getConfig("anrong.share.approveresult.url");
        // 处理参数
        param.put("member", member);
        param.put("sign", sign);
        logger.debug("安融-共享审批结果-请求地址：{}", url);
        logger.debug("安融-共享审批结果-请求报文：{}", param);
        // 发起请求
        String result = null;
        try {
            result = HttpsUtils.sendGet(url, param);
        } catch (Exception e) {
            logger.error("安融-共享审批结果异常-{}",e);
        }
        logger.debug("安融-共享审批结果-应答结果：{}", result);
        ShareResultVO vo = (ShareResultVO) JsonMapper.fromJsonString(result, ShareResultVO.class);
        return vo;
    }

    @Override
    public ShareResultVO sendShareOrderResult(Map<String, String> param) {
        // 获取秘钥以及url
        String member = Global.getConfig("anrong.member");
        String sign = Global.getConfig("anrong.sign");
        String url = Global.getConfig("anrong.share.orderesult.url");
        // 处理参数
        param.put("member", member);
        param.put("sign", sign);
        logger.debug("安融-共享订单结果-请求地址：{}", url);
        logger.debug("安融-共享订单结果-请求报文：{}", param);
        // 发起请求
        String result = null;
        try {
            result = HttpsUtils.sendGet(url, param);
        } catch (Exception e) {
            logger.error("安融-共享订单结果-{}",e);
        }
        logger.debug("安融-共享订单结果-应答结果：{}", result);
        ShareResultVO vo = (ShareResultVO) JsonMapper.fromJsonString(result, ShareResultVO.class);
        return vo;
    }
}
