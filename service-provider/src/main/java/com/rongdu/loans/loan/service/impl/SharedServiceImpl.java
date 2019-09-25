package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.HttpUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.manager.ConfigManager;
import com.rongdu.loans.basic.vo.ConfigVO;
import com.rongdu.loans.loan.dao.ShareJucaiUserLogMapper;
import com.rongdu.loans.loan.entity.ShareJucaiUserLog;
import com.rongdu.loans.loan.option.share.CustInfo;
import com.rongdu.loans.loan.option.share.JCUserInfo;
import com.rongdu.loans.loan.option.share.ShareApiResult;
import com.rongdu.loans.loan.service.SharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/20
 * @since 1.0.0
 */
@Slf4j
@Service("sharedService")
public class SharedServiceImpl implements SharedService {

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private ShareJucaiUserLogMapper shareJucaiUserLogMapper;

    @Override
    public boolean pushCustInfo(CustInfo custInfo) {
        boolean result = false;
        ShareApiResult shareApiResult = null;
        String url = "http://47.96.79.203:8098/api/third/pushCustInfo";
        try {
            String response = HttpUtils.postForJson(url, BeanMapper.describeStringValue(custInfo));
            shareApiResult = JSONObject.parseObject(response, ShareApiResult.class);
            if (shareApiResult != null && ShareApiResult.CODE_SUCCESS.equals(shareApiResult.getCode())) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
            log.error("【共享服务-进件用户信息推送】接口异常!custInfo={}", custInfo, e);
        }
        log.debug("【共享服务-进件用户信息推送】custInfo={},响应结果={}", custInfo, JSONObject.toJSONString(shareApiResult));
        return result;
    }

    @Override
    public boolean shareToJuCai(JCUserInfo userInfo) {
        boolean flag = true;
        // 先查询配置表中 是否已达每日封顶的量 以及时间是否超出可发送时间
        List<ConfigVO> configList = configManager.getConfigList();
        String maxAmountStr = null;
        for (ConfigVO configVO : configList) {
            if ("share_jucai_status_flag".equals(configVO.getKey()) && "1".equals(configVO.getValue())) {
                log.info("【共享服务-聚财-推送用户订单信息-推送开关为关闭状态】");
                return flag;
            }
            if ("share_jucai_max_amount".equals(configVO.getKey())) {
                maxAmountStr = configVO.getValue();
            }
        }
        // 从缓存中获取已共享的数量
        String cacheKey = "SHARED_JUCAI_USERINFO_COUNT";
        String countStr = (String) JedisUtils.getObject(cacheKey);
        int count = StringUtils.isBlank(countStr) ? 0 : Integer.parseInt(countStr);
        int maxAmount = StringUtils.isBlank(maxAmountStr) ? 0 : Integer.parseInt(maxAmountStr);
        if (maxAmount <= count) {
            log.info("【共享服务-聚财-推送用户订单信息-推送数量已达今日上限-{}】", count);
            return flag;
        }
        // 从缓存中获取响应code为9999次数 超过十次不执行
        String cacheErrorKey = "SHARED_JUCAI_USERINFO_ERROR_COUNT";
        String errorCountStr = (String) JedisUtils.getObject(cacheErrorKey);
        int errorCount = StringUtils.isBlank(errorCountStr) ? 0 : Integer.parseInt(errorCountStr);
        if (10 < errorCount) {
            log.info("【共享服务-聚财-推送用户订单信息-推送失败数量已达今日上限-{}】", errorCountStr);
            return flag;
        }

        String url = "http://third.cqwod.com/notify";
        String param = JsonMapper.toJsonString(userInfo);
        ShareApiResult result = null;
        try {
            log.info("【共享服务-聚财-推送用户订单信息-请求地址-{}】", url);
            log.info("【共享服务-聚财-推送用户订单信息-请求参数-{}】", param);
            String response = RestTemplateUtils.getInstance().postForJsonRaw(url, param);
            log.info("【共享服务-聚财-推送用户订单信息-响应结果-{}】", response);
            result = JSONObject.parseObject(response, ShareApiResult.class);
        } catch (Exception e) {
            // 记录失败次数 期限为当天 重新消费
            flag = false;
            errorCount++;
            JedisUtils.setObject(cacheErrorKey, String.valueOf(errorCount), DateUtils.getMiao());
            log.error("【共享服务-聚财-推送用户订单信息-请求异常-{}】", e);
        }
        if (null == result) {
            return flag;
        }
        // 调用接口成功处理逻辑
        if ("0000".equals(result.getCode())) {
            // 记录成功次数 期限为当天
            count++;
            JedisUtils.setObject(cacheKey, String.valueOf(count), DateUtils.getMiao());
            // 插入日志表
            ShareJucaiUserLog entity = new ShareJucaiUserLog();
            entity.setUserName(userInfo.getName());
            entity.setIdNo(userInfo.getIdCard());
            entity.setPhone(userInfo.getMobile());
            entity.setCreateTime(new Date());
            shareJucaiUserLogMapper.insert(entity);
        } else {
            // 记录失败次数,重新消费消息
            flag = false;
            errorCount++;
            JedisUtils.setObject(cacheErrorKey, String.valueOf(errorCount), DateUtils.getMiao());
        }
        return flag;
    }
}
