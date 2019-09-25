package com.rongdu.loans.mq;

import com.rongdu.common.config.Global;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.loan.option.SLL.AddData;
import com.rongdu.loans.loan.option.SLL.BaseData;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rong360Model.OrderBaseInfo;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Service
public class MessageProductorService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 向队列推送消息
     *
     * @param quene
     * @param obj
     */
    public void sendDataToQueue(String quene, Object obj) {
//    	logger.info("消息生产者：{}",obj);
        amqpTemplate.convertAndSend(quene, obj);
    }

    public void sendDataToPushUserBaseInfoQuene(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        CommonMessage<XianJinBaiKaCommonOP> message = new CommonMessage<XianJinBaiKaCommonOP>();
        String queueName = QueueConfig.PUSH_USER_BASEINFO.getName();
        String type = QueueConfig.PUSH_USER_BASEINFO.getType();
        String source = "rms";
        String bizId = xianJinBaiKaCommonRequest.getOrder_info().getOrderSn();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(xianJinBaiKaCommonRequest);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendDataToPushUserAdditionalInfoQuene(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        CommonMessage<XianJinBaiKaCommonOP> message = new CommonMessage<XianJinBaiKaCommonOP>();
        String queueName = QueueConfig.PUSH_USER_ADDITIONALINFO.getName();
        String type = QueueConfig.PUSH_USER_ADDITIONALINFO.getType();
        String source = "rms";
        String bizId = xianJinBaiKaCommonRequest.getOrder_info().getOrderSn();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(xianJinBaiKaCommonRequest);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendRongBaseInfoQuene(OrderBaseInfo orderBaseInfo) {
        CommonMessage<OrderBaseInfo> message = new CommonMessage<OrderBaseInfo>();
        String queueName = QueueConfig.PUSH_RONG_BASEINFO.getName();
        String type = QueueConfig.PUSH_RONG_BASEINFO.getType();
        String source = "rong360";
        // ytodo 0301 JHH
        if (orderBaseInfo.getOrderinfo().getProductId() == Global.RONG_JHH_PRODUCTID){
            source = "rongJHH";
        }
        String bizId = orderBaseInfo.getOrderinfo().getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(orderBaseInfo);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendRongAdditionalInfoQuene(OrderAppendInfo orderAppendInfo) {
        CommonMessage<OrderAppendInfo> message = new CommonMessage<OrderAppendInfo>();
        String queueName = QueueConfig.PUSH_RONG_ADDITIONALINFO.getName();
        String type = QueueConfig.PUSH_RONG_ADDITIONALINFO.getType();
        String source = "rong360";
        String bizId = orderAppendInfo.getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(orderAppendInfo);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendBaseInfo(BaseData baseData) {
        CommonMessage<BaseData> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_SLL_BASEINFO.getName();
        String type = QueueConfig.PUSH_SLL_BASEINFO.getType();
        String source = "rms";
        String bizId = baseData.getOrderinfo().getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(baseData);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendAdditionInfo(AddData addData) {
        CommonMessage<AddData> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_SLL_ADDITIONALINFO.getName();
        String type = QueueConfig.PUSH_SLL_ADDITIONALINFO.getType();
        String source = "rms";
        String bizId = addData.getOrderNo();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(addData);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }


}
