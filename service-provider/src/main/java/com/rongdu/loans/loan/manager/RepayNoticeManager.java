/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;


import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.loan.dto.OverdueRepayNoticeDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author zhangxiaolong
 * @version 2017-08-24
 */
@Service("repayNoticeManager")
public class RepayNoticeManager  {


    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FAIL = "99";
    private static final String SUCCESS = "00000000";

    public String overdueRepayNotice(OverdueRepayNoticeDTO dto) {
        Map<String, String> params = null;
        try {
            params = BeanUtils.describe(dto);
        } catch (Exception e) {
            logger.error(" 逾期还款通知参数异常：dto = " + JsonMapper.getInstance().toJson(dto), e);
            return FAIL;
        }
        if (params == null || params.size() == 0){
            logger.error(" 逾期还款通知参数异常：dto = " + JsonMapper.getInstance().toJson(dto));
            return FAIL;
        }
        String url = Global.getConfig("batchservlet.overdueRepayment.url");
        // rest模式调用接口
        String result = (String) RestTemplateUtils.getInstance()
                .postForObject(url, params, String.class);

        return result;
    }
	
}