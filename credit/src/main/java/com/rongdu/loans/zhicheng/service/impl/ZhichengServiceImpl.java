package com.rongdu.loans.zhicheng.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.hanjs.util.MD5Util;
import com.rongdu.loans.zhicheng.common.ZhichengcreditConfig;
import com.rongdu.loans.zhicheng.message.CreditInfoRequestParam;
import com.rongdu.loans.zhicheng.message.EchoQueryApiRequest;
import com.rongdu.loans.zhicheng.message.RiskListRequest;
import com.rongdu.loans.zhicheng.service.ZhichengService;
import com.rongdu.loans.zhicheng.vo.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 宜信致诚-阿福共享平台征信服务
 *
 * @author sunda
 * @version 2017-07-20
 */
@Service("zhichengService")
public class ZhichengServiceImpl extends PartnerApiService implements ZhichengService {

    private static final String channelId = "10001";
    private static final String thirdKey = "jcfq2019";

    /**
     * 查询借款、风险名单和逾期信息
     *
     * @return
     */
    public CreditInfoVO queryCreditInfo(CreditInfoOP op) {
        //配置参数
        String partnerId = ZhichengcreditConfig.partner_id;
        String partnerName = ZhichengcreditConfig.partner_name;
        String bizCode = ZhichengcreditConfig.echo_query_api_biz_code;
        String bizName = ZhichengcreditConfig.echo_query_api_biz_name;
        String url = ZhichengcreditConfig.echo_query_api_url;

        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);

        Map<String, String> params = new HashMap<String, String>();
        String input = ZhichengcreditConfig.userId + ZhichengcreditConfig.password;
        String sign = Digests.md5(input);
        params.put("userid", ZhichengcreditConfig.userId);
        params.put("sign", sign);
        EchoQueryApiRequest request = new EchoQueryApiRequest();
        CreditInfoRequestParam requestData = BeanMapper.map(op, CreditInfoRequestParam.class);
        request.setData(requestData);
        //请求字符串
        String jsonParams = JsonMapper.toJsonString(request);
        params.put("params", jsonParams);
        //发送请求
        CreditInfoVO vo = (CreditInfoVO) postForJson(url, params, CreditInfoVO.class, log);
        return vo;
    }

    /**
     * 查询风险名单
     *
     * @return
     */
    public RiskListVO queryMixedRiskList(RiskListOP op) {
        //配置参数
        String partnerId = ZhichengcreditConfig.partner_id;
        String partnerName = ZhichengcreditConfig.partner_name;
        String bizCode = ZhichengcreditConfig.mixed_risk_list_biz_code;
        String bizName = ZhichengcreditConfig.mixed_risk_list_biz_name;
        String url = ZhichengcreditConfig.mixed_risk_list_url;

        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);

        Map<String, String> params = new HashMap<String, String>();
        String input = ZhichengcreditConfig.userId + ZhichengcreditConfig.password;
        String sign = Digests.md5(input);
        params.put("userid", ZhichengcreditConfig.userId);
        params.put("sign", sign);
        RiskListRequest request = new RiskListRequest();
        request.setData(op);
        //请求字符串
        String jsonParams = JsonMapper.toJsonString(request);
        params.put("params", jsonParams);
        //发送请求
        RiskListVO vo = (RiskListVO) getForJson(url, params, RiskListVO.class, log);
        return vo;
    }

    @Override
    public CreditInfoVO queryCreditInfoOther(CreditInfoOP op) {
        String url = ZhichengcreditConfig.echo_query_api_url;
        //配置参数
        Map<String, String> paramMap = new HashedMap();
        paramMap.put("name", op.getName());
        paramMap.put("idNo", op.getIdNo());
        // 第三方机构提供的渠道号
        paramMap.put("channelId", channelId);
        String sign = MD5Util.createSign(paramMap, true, thirdKey);
        paramMap.put("sign",sign);
        logger.debug("宜信阿福-请求地址：{}", url);
        logger.debug("宜信阿福-请求报文：{}", JsonMapper.toJsonString(paramMap));
        String jsonResult = null;
        try {
            jsonResult = RestTemplateUtils.getInstance().postForJson(url, paramMap);
        } catch (Exception e) {
            logger.error("宜信阿福-出现异常：{}", e);
        }
        logger.debug("宜信阿福-应答结果：{}", jsonResult);
        CreditInfoVO vo = null;
        try {
            vo = (CreditInfoVO) JsonMapper.fromJsonString(jsonResult, CreditInfoVO.class);
        } catch (Exception e) {
            logger.error("宜信阿福-json解析出现异常：{}", e);
        }
        //发送请求
        return vo;
    }

    @Override
    public DecisionVO queryDecisionOther(CreditInfoOP op) {
        String partnerId = ZhichengcreditConfig.partner_id;
        String partnerName = ZhichengcreditConfig.partner_name;
        String bizCode = ZhichengcreditConfig.echo_query_decision_biz_code;
        String bizName = ZhichengcreditConfig.echo_query_decision_biz_name;

        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);
        log.setUserId(op.getUserId());
        log.setApplyId(op.getApplyId());

        String url = ZhichengcreditConfig.echo_query_decision_url;
        //配置参数
        Map<String, String> paramMap = new HashedMap();
        paramMap.put("name", op.getName());
        paramMap.put("idNo", op.getIdNo());
        paramMap.put("mobile", op.getMobile());
        paramMap.put("channelId", channelId);
        // 获取秘钥
        String sign = MD5Util.createSign(paramMap, true, thirdKey);
        paramMap.put("sign",sign);

        logger.debug("宜信阿福-请求地址：{}", url);
        logger.debug("宜信阿福-请求报文：{}", JsonMapper.toJsonString(paramMap));
        long start = System.currentTimeMillis();
        String jsonResult = null;
        try {
            jsonResult = RestTemplateUtils.getInstance().postForJson(url, paramMap);
        } catch (Exception e) {
            logger.error("宜信阿福-出现异常：{}", e);
        }
        logger.debug("宜信阿福-应答结果：{}", jsonResult);
        DecisionVO vo = null;
        try {
            vo = (DecisionVO) JsonMapper.fromJsonString(jsonResult, DecisionVO.class);
        } catch (Exception e) {
            logger.error("宜信阿福-json解析出现异常：{}", e);
        }
        long end = System.currentTimeMillis();
        log.setCostTime(end - start);
        log.setInvokeTime(new java.sql.Date(start));
        if (vo != null) {
            log.setSuccess(vo.getSuccess());
            log.setCode(vo.getCode());
            log.setMsg(vo.getMsg());
        } else {
            log.setSuccess(false);
            log.setCode("ERROR");
            log.setMsg("宜信阿福出现异常");
        }
        log.setUrl(url);
        log.setReqContent(JsonMapper.toJsonString(paramMap));
        log.setSyncRespContent(jsonResult);
        saveApiInvokeLog(log);
        return vo;
    }

    @Override
    public FraudScreenVO queryFraudScreenOther(CreditInfoOP op) {
        String partnerId = ZhichengcreditConfig.partner_id;
        String partnerName = ZhichengcreditConfig.partner_name;
        String bizCode = ZhichengcreditConfig.echo_query_fraudscreen_biz_code;
        String bizName = ZhichengcreditConfig.echo_query_fraudscreen_biz_name;

        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);
        log.setUserId(op.getUserId());
        log.setApplyId(op.getApplyId());

        String url = ZhichengcreditConfig.echo_query_fraudscreen_url;
        //配置参数
        Map<String, String> paramMap = new HashedMap();
        paramMap.put("name", op.getName());
        paramMap.put("idNo", op.getIdNo());
        paramMap.put("mobile", op.getMobile());
        paramMap.put("channelId", channelId);
        // 获取秘钥
        String sign = MD5Util.createSign(paramMap, true, thirdKey);
        paramMap.put("sign",sign);

        logger.debug("宜信阿福-请求地址：{}", url);
        logger.debug("宜信阿福-请求报文：{}", JsonMapper.toJsonString(paramMap));
        long start = System.currentTimeMillis();
        String jsonResult = null;
        try {
            jsonResult = RestTemplateUtils.getInstance().postForJson(url, paramMap);
        } catch (Exception e) {
            logger.error("宜信阿福-出现异常：{}", e);
        }
        logger.debug("宜信阿福-应答结果：{}", jsonResult);
        FraudScreenVO vo = null;
        try {
            vo = (FraudScreenVO) JsonMapper.fromJsonString(jsonResult, FraudScreenVO.class);
        } catch (Exception e) {
            logger.error("宜信阿福-json解析出现异常：{}", e);
        }
        long end = System.currentTimeMillis();
        log.setCostTime(end - start);
        log.setInvokeTime(new java.sql.Date(start));
        if (vo != null) {
            log.setSuccess(vo.getSuccess());
            log.setCode(vo.getCode());
            log.setMsg(vo.getMsg());
        } else {
            log.setSuccess(false);
            log.setCode("ERROR");
            log.setMsg("宜信阿福出现异常");
        }
        log.setUrl(url);
        log.setReqContent(JsonMapper.toJsonString(paramMap));
        log.setSyncRespContent(jsonResult);
        saveApiInvokeLog(log);
        return vo;
    }

}
