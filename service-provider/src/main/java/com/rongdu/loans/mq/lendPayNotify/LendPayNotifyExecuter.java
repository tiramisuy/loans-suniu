package com.rongdu.loans.mq.lendPayNotify;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.loan.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/25.
 */
@Component("lendPayNotifyExecuter")
public class LendPayNotifyExecuter {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContractService contractService;


    public void execute(Message message){
        String body = new String(message.getBody());
        Date payTime = getBatchLendPayDate(body);
        String outsideSerialNo = getProductId(body);
//        contractService.process(outsideSerialNo, payTime);

    }

    private Date getBatchLendPayDate(String body){
        try {
            LendPayNotify notify =  JsonMapper.getInstance().fromJson(body, LendPayNotify.class);
            return DateUtils.parse(notify.getBatchLendPayDate());
        }catch (Exception e){
            logger.error("消息解析异常，body = {}", body);
            return null;
        }

    }

    private String getProductId(String body){
        try {
            LendPayNotify notify =  JsonMapper.getInstance().fromJson(body, LendPayNotify.class);
            return notify.getProductId();
        }catch (Exception e){
            logger.error("消息解析异常，body = {}", body);
            return null;
        }
    }

//    {
//        "strbgData":"{"bankCode":"30050000","batchNo":"144654","seqNo":"139734","txTime":"144654","channel":"000002", "sign":"1Ziyo/dBmzPkQ3pEiON8As5E1qix/rABWBgtEcpgezMlRzPmy1jIdJ65QvR/mfq+bLFN3U5W5y66YSjCS3WDtBA4ETJnxYMi/VpH23aQJXzwX1EjxuSlPqu0M+1yOeJ4tWbTSadNH7oCo07rs/yFs/vosVxvPpKiaBkueOD4O30=","retCode":"00000000", "version":"10","retMsg":"成功", "sucAmount":"10000.00","failCounts":"0","failAmount":"0","instCode":"00620001","txCode":"batchLendPay","acqRes":"20170720144654117lz2bz22-4227-1","sucCounts":"2","txDate":"20170720"}",
//            "batchLendPayDate":"2017-07-26 19:29:04",
//            "productId":"A00128"
//    }
}
