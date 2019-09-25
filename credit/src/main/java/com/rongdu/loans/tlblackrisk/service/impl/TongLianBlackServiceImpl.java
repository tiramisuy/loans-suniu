package com.rongdu.loans.tlblackrisk.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinpay.mcp.comm.AllinpayUtils;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.tlblackrisk.common.HttpClientUtil;
import com.rongdu.loans.tlblackrisk.common.OrgProduct;
import com.rongdu.loans.tlblackrisk.op.TongLianBlackOP;
import com.rongdu.loans.tlblackrisk.service.TongLianBlackService;
import com.rongdu.loans.tlblackrisk.vo.TongLianBlackVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * 通联查询网贷黑名单
 *
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.service.impl
 * @date 2019/7/25 14:38
 */
@Service("tongLianBlackService")
public class TongLianBlackServiceImpl extends PartnerApiService implements TongLianBlackService {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TongLianBlackVO getBlackDetail(TongLianBlackOP op) {
        String url = Global.getConfig("tonglian_blackdetail_query_url");

        op.setMerId(OrgProduct.mer_id);
        op.setTimestamp(String.valueOf(System.currentTimeMillis()));
        op.setBeforeDate(DateUtils.getDate(DateUtils.FORMAT_INT_DATE));
        Map<String, String> param = BeanMapper.describeStringValue(op);
        String paramsStr = JSON.toJSONString(param);
        AllinpayUtils allinpayUtils = new AllinpayUtils();
        TongLianBlackVO vo = null;
        try {
            // 私钥生成的密文
            String params_encrypt = allinpayUtils.encryptByPrivateKey(paramsStr, OrgProduct.privateKey);
            String sign = allinpayUtils.sign(paramsStr, OrgProduct.privateKey);
            String requestUrl = url + "?key=" + OrgProduct.key + "&sign=" + URLEncoder.encode(sign, "UTF-8") + "&params=" + URLEncoder.encode(params_encrypt, "UTF-8");
            long start = System.currentTimeMillis();
            logger.debug("通联-查询网贷黑名单-请求地址：{}", url);
            logger.debug("通联-查询网贷黑名单-请求报文-未加密：{}", param);
            String result = HttpClientUtil.doPost(requestUrl, param);
            long end = System.currentTimeMillis();
            logger.debug("通联-查询网贷黑名单-响应结果：{}", result);
            vo = (TongLianBlackVO) JsonMapper.fromJsonString(result, TongLianBlackVO.class);
            LogParam log = new LogParam();
            log.setPartnerId("tonglian");
            log.setPartnerName("通联");
            log.setBizCode("blackquery");
            log.setBizName("通联网贷黑名单");
            log.setInvokeTime(new Date());
            log.setCostTime(end - start);
            log.setUrl(url);
            log.setReqContent(paramsStr);
            log.setSyncRespContent(result);
            if (vo != null){
                log.setSuccess(true);
                log.setCode(vo.getCode());
                log.setMsg(vo.getMsg());
            } else {
                log.setSuccess(false);
                log.setCode("ERROR");
                log.setMsg("通联查询网贷黑名单异常");
            }
            saveApiInvokeLog(log);
        } catch (Exception e) {
            logger.error("通联-查询网贷黑名单-出现异常：{}", e);
        }
        return vo;
    }
}
