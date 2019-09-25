/**
 * Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.api.web.controller;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.RC4_128_V2;
import com.rongdu.common.utils.Encodes;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.web.vo.RecordParamsVO;
import com.rongdu.loans.api.web.vo.RecordVO;
import com.rongdu.loans.cust.vo.UserRecordVO;
import com.rongdu.loans.loan.service.LoanApplyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 宜信阿福上报接口
 *
 * @author likang
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "zhicheng/record")
public class CreditController extends BaseController {

    @Autowired
    private LoanApplyService loanApplyService;

    /**
     * 第三方回传上报宜信阿福数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "userInfo")
    @ResponseBody
    public RecordVO userInfo(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String idNo = request.getParameter("idNo");
        String channelId = request.getParameter("channelId");
//        String sign = request.getParameter("sign");
        RecordVO recordVO = new RecordVO();

        if (StringUtils.isBlank(name) || StringUtils.isBlank(channelId) || StringUtils.isBlank(idNo)) {
            //参数错误
            logger.error("第三方回传上报宜信阿福数据参数错误");
            recordVO.setErrorCode("4200");
            return recordVO;
        }
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("name", name);
        paramMap.put("idNo", idNo);
        // 第三方机构提供的渠道号
        paramMap.put("channelId", channelId);
        // 比对秘钥
//        Boolean flag = MD5Util.checkSign(sign, paramMap);
//        if (!flag) {
//            logger.error("第三方回传上报宜信阿福数据Sign错误：{},{}", name, idNo);
//            //参数错误
//            recordVO.setErrorCode("4002");
//            return recordVO;
//        }
        RecordParamsVO recordParamsVO = new RecordParamsVO();
        String errorCode = "0000";
        UserRecordVO vo = null;
        try {
            vo = loanApplyService.getUserRecord(name, idNo);
        } catch (Exception e) {
            logger.error("调用阿福共享用户记录接口失败", e);
            //服务器繁忙
            errorCode = "-1002";
        }

        recordParamsVO.setData(vo);
        String result = JsonMapper.getInstance().toJson(recordParamsVO);
        logger.info("/zhicheng/credit/userRecord 返回明文：{}", result);

        recordVO.setParams(encode(result));
        if (vo != null && CollectionUtils.isEmpty(vo.getLoanRecords())) {
            //查询成功，无数据
            errorCode = "0001";
        }
        recordVO.setErrorCode(errorCode);
        return recordVO;
    }

    /*
    @RequestMapping(value = "userRecord")
    @ResponseBody
    public RecordVO userRecord(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("params");
        //logger.info("/zhicheng/credit/userRecord 加密参数params：{}", params);
        RecordVO recordVO = new RecordVO();
        if (StringUtils.isBlank(params)){
            //参数错误
            recordVO.setErrorCode("4200");
            return recordVO;
        }
        UserRecordOP userRecordOP = JsonMapper.getInstance().fromJson(params, UserRecordOP.class);
        params = userRecordOP.getParams();
        params = decode(params);
        logger.info("/zhicheng/credit/userRecord 明文参数params：{}", params);
        UserRecordParamsOP op = JsonMapper.getInstance().fromJson(params, UserRecordParamsOP.class);
        RecordParamsVO recordParamsVO = new RecordParamsVO();
        String errorCode = "0000";
        String tx = op.getTx();
        if (!StringUtils.equals(tx, "201")){
            //参数错误
            recordVO.setErrorCode("4200");
            return recordVO;
        }
        UserRecordDataOP user = op.getData();
        UserRecordVO vo = null;
        try {
            vo = loanApplyService.getUserRecord(user.getName(), user.getIdNo());
        }catch (Exception e){
            logger.error("调用阿福共享用户记录接口失败", e);
            //服务器繁忙
            errorCode = "-1002";
        }

        recordParamsVO.setData(vo);
        String result = JsonMapper.getInstance().toJson(recordParamsVO);
        //logger.info("/zhicheng/credit/userRecord 返回明文：{}", result);

        recordVO.setParams(encode(result));
        if (vo != null && CollectionUtils.isEmpty(vo.getLoanRecords())){
            //查询成功，无数据
            errorCode = "0001";
        }
        recordVO.setErrorCode(errorCode);
        //logger.info("/zhicheng/credit/userRecord 返回密文：{}", JsonMapper.getInstance().toJson(recordVO));
        return recordVO;
    }

    *//**
     * 解密
     * @param params
     * @return
     *//*
    private String decode(String params){
        params = Encodes.urlDecode(params);
        params = RC4_128_V2.decode(params, Global.getConfig("zhicheng.rc4key"));
        return params;
    }

    */

    /**
     * 加密
     *
     * @param params
     * @return
     */
    private String encode(String params) {
        params = RC4_128_V2.encode(params, Global.getConfig("zhicheng.rc4key"));
        params = Encodes.urlEncode(params);
        return params;
    }

}