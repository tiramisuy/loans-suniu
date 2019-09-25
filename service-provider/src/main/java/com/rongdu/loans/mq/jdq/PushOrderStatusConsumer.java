package com.rongdu.loans.mq.jdq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.loan.service.JDQStatusFeedBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service("pushOrderStatusConsumer")
public class PushOrderStatusConsumer implements ChannelAwareMessageListener {

     Logger logger = LoggerFactory.getLogger(getClass());

     @Autowired
    JDQStatusFeedBackService jDQStatusFeedBackService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(1000);
            CommonMessage<String> msg = parseMessage(message);
            logger.debug("借点钱推送基本信息消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            String orderId = msg.getMessage();
            boolean flag = jDQStatusFeedBackService.orderStatusFeedBack(orderId);
            if (!flag) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                        message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBody());
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }

        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                    message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBody());
            logger.error("处理失败",  e);
        }
    }

    /**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<String> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<String>> reference = new TypeReference<CommonMessage<String>>() {
        };
        CommonMessage<String> msg =
                (CommonMessage<String>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }
}
