package com.rongdu.loans.mq.dwd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.loan.option.dwd.ChargeInfo;
import com.rongdu.loans.loan.option.dwd.charge.Charge;
import com.rongdu.loans.loan.service.DWDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/3/14
 * @since 1.0.0
 */
@Service("dwdChargeConsumer")
public class DWDChargeConsumer implements ChannelAwareMessageListener {
    @Autowired
    private DWDService dwdService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            CommonMessage<ChargeInfo> msg = parseMessage(message);
            logger.debug("大王贷推送运营商数据消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            ChargeInfo chargeInfo = msg.getMessage();
            boolean flag = dwdService.saveChargeInfo(chargeInfo);
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
            e.printStackTrace();
        }
    }


    /**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<ChargeInfo> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<ChargeInfo>> reference = new TypeReference<CommonMessage<ChargeInfo>>() {
        };
        CommonMessage<ChargeInfo> msg =
                (CommonMessage<ChargeInfo>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }
}
