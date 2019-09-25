package com.rongdu.loans.mq.dwd;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.loan.option.dwd.ChargeInfo;
import com.rongdu.loans.loan.option.dwd.DWDAdditionInfo;
import com.rongdu.loans.loan.option.dwd.DWDBaseInfo;
import com.rongdu.loans.loan.option.dwd.charge.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Service
public class DWDMessageService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendBaseInfo(DWDBaseInfo dwdBaseInfo) {
        CommonMessage<DWDBaseInfo> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_DWD_BASEINFO.getName();
        String type = QueueConfig.PUSH_DWD_BASEINFO.getType();
        String source = "rms";
        String bizId = dwdBaseInfo.getOrderinfo().getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(dwdBaseInfo);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendAdditionInfo(DWDAdditionInfo dwdAdditionInfo) {
        CommonMessage<DWDAdditionInfo> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_DWD_ADDITIONALINFO.getName();
        String type = QueueConfig.PUSH_DWD_ADDITIONALINFO.getType();
        String source = "rms";
        String bizId = dwdAdditionInfo.getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(dwdAdditionInfo);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendChargeInfo(ChargeInfo chargeInfo) {
        CommonMessage<ChargeInfo> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_DWD_CHARGEINFO.getName();
        String type = QueueConfig.PUSH_DWD_CHARGEINFO.getType();
        String source = "rms";
        String bizId = chargeInfo.getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(chargeInfo);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }
}
