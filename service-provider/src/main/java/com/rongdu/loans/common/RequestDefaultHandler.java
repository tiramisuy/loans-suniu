package com.rongdu.loans.common;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/11
 * @since 1.0.0
 */
@Slf4j
public abstract class RequestDefaultHandler {

    public <T> T requestHandler(ThirdApiDTO thirdApiDTO, Class<T> responseVO) throws Exception {
        //String reqData = JSONObject.toJSONString(requestOP);
        //String reqData = JsonMapper.toJsonString(requestOP);
        String serviceName = thirdApiDTO.getServiceName();
        String url = thirdApiDTO.getUrl();
        String data = thirdApiDTO.getData();

        log.debug("{}-请求业务数据：{}", serviceName, data);
        Map<String, String> params = this.createPostParam(thirdApiDTO);
        log.debug("{}-请求报文：{}", serviceName, params);

        // 请求结果响应
        String result = HttpClientUtils.postForJson(url,null,JsonMapper.toJsonString(params));
		//log.debug("{}-请求结果响应：{}", serviceName, result);
        if (result == null || result.length() == 0) {
            throw new Exception("Request api " + serviceName + " returns null");
        }
        // Object resp = (T) JsonMapper.fromJsonString(result, responseVO);
        T resp = JSONObject.parseObject(result, responseVO);
        if (resp == null) {
            throw new Exception("Request api " + serviceName + " got a non-json result");
        }
        return resp;
    }

    public abstract Map<String, String> createPostParam(ThirdApiDTO thirdApiDTO) throws Exception;

}
