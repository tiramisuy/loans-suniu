package com.rongdu.loans.mq.share;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.loan.option.share.CustInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Slf4j
@Service
public class SharedMessageService {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void pushCustInfo(CustInfo custInfo) {
        CommonMessage<CustInfo> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_SHARED_CUSTINFO.getName();
        String type = QueueConfig.PUSH_SHARED_CUSTINFO.getType();
        String source = "rms";
        String bizId = custInfo.getIdNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(custInfo);
        log.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void shareToJuCai(String order) {
        CommonMessage<String> message = new CommonMessage<>();
        String queueName = QueueConfig.SHARE_USERINFO_TO_JUCAI.getName();
        String type = QueueConfig.SHARE_USERINFO_TO_JUCAI.getType();
        String source = "rms";
        String bizId = order;
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(order);
        log.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }
}
