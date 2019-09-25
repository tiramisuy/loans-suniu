package com.rongdu.loans.mq.jdq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.utils.json.Json;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.service.JDQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 */
@Service("jDQConsumer")
public class JDQConsumer implements ChannelAwareMessageListener {
    @Autowired
    private JDQService jdqService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            CommonMessage<IntoOrder> msg = parseMessage(message);
            logger.debug("借点钱推送基本信息消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            IntoOrder intoOrder = msg.getMessage();
            boolean flag = jdqService.saveIntoOrder(intoOrder, msg.getType());
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
    private CommonMessage<IntoOrder> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<IntoOrder>> reference = new TypeReference<CommonMessage<IntoOrder>>() {
        };
        CommonMessage<IntoOrder> msg =
                (CommonMessage<IntoOrder>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }

}