package com.rongdu.loans.mq.jdq;

import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.basic.service.ProductorService;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 */
@Service("productorService")
public class MessageProductorService implements ProductorService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendIntoOrder(IntoOrder intoOrder, String type) {
        CommonMessage<IntoOrder> message = new CommonMessage<IntoOrder>();
        String queueName = QueueConfig.PUSH_JDQ.getName();
        //String type = QueueConfig.PUSH_JDQ.getType();
        String source = "rms";
        String bizId = intoOrder.getJdqOrderId();
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(intoOrder);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        amqpTemplate.convertAndSend(queueName, message);
    }

    public void sendOrderStatus(String orderId, String bizId, String type) {
        CommonMessage<String> message = new CommonMessage<>();
        String queueName = QueueConfig.PUSH_JDQ_ORDER_STATUS.getName();
        String source = "rms";
        String id = String.format("%s_%s", type, bizId);
        message.setQueueName(queueName);
        message.setType(type);
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(orderId);
        logger.debug("消息生产者：{}，{}，{}", source, type, bizId);
        try{
            amqpTemplate.convertAndSend(queueName, message);
        } catch (Exception e){
            logger.error("推送订单状态失败,orderId:{}", orderId, e);
        }

    }

    public void sendOverdueStatusAnRong(ShareVO vo, QueueConfig config) {
        CommonMessage<ShareVO> message = new CommonMessage<>();
        String source = "rms";
        String bizId = vo.getLoanId();
        String id = String.format("%s_%s", config.getType(), bizId);
        message.setQueueName(config.getName());
        message.setType(config.getType());
        message.setSource(source);
        message.setId(id);
        message.setBizId(bizId);
        message.setMessage(vo);
        logger.debug("【安融-共享新增逾期】消息生产者：{}，{}，{}", source, config.getType(), bizId);
        try{
            amqpTemplate.convertAndSend(config.getName(), message);
        } catch (Exception e){
            logger.error("推送【安融-共享新增逾期】失败,applyId:{}", bizId, e);
        }
    }
}
