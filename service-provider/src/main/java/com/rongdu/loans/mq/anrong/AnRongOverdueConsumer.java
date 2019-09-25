package com.rongdu.loans.mq.anrong;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.anrong.service.AnRongService;
import com.rongdu.loans.anrong.vo.ShareResultVO;
import com.rongdu.loans.anrong.vo.ShareVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/6/24
 * @since 1.0.0
 */
@Service("anRongOverdueConsumer")
public class AnRongOverdueConsumer implements ChannelAwareMessageListener {
    @Autowired
    private AnRongService anRongService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        CommonMessage<ShareVO> msg = parseMessage(message);
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            logger.debug("【安融-共享新增逾期】消息消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            ShareVO shareVO = msg.getMessage();

            // 共享新增逾期 参数组装
            Map<String, String> result = new HashMap<>();
            result.put("customerName", shareVO.getCustomerName());//姓名
            result.put("paperNumber", shareVO.getPaperNumber());//身份证号
            result.put("loanId", shareVO.getLoanId());
            result.put("loanTypeDesc", shareVO.getLoanTypeDesc());
            result.put("overdueStartDate", shareVO.getOverdueStartDate());//逾期开始时间
            result.put("nbMoney", shareVO.getNbMoney());
            result.put("state", shareVO.getState());//设置为固定值“02”
            ShareResultVO shareResultVO = anRongService.sendShareOrderResult(result);
            if (shareResultVO == null || shareResultVO.getErrors() != null) {
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
            logger.error("【安融-共享新增逾期】消息消费异常：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
        }
    }


    /**
     * 将消息映射为Java对象
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private CommonMessage<ShareVO> parseMessage(Message message)
            throws UnsupportedEncodingException {
        String jsonMsg = new String(message.getBody(), "UTF-8");
        TypeReference<CommonMessage<ShareVO>> reference = new TypeReference<CommonMessage<ShareVO>>() {
        };
        CommonMessage<ShareVO> msg =
                (CommonMessage<ShareVO>) JsonMapper.fromJsonString(jsonMsg, reference);
        return msg;
    }
}
