package com.rongdu.loans.mq.anrong;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.anrong.service.AnRongService;
import com.rongdu.loans.anrong.vo.ShareResultVO;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.loan.service.ApplyTripartiteAnrongService;
import com.rongdu.loans.loan.service.LoanApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 安融共享订单结果(包含共享逾期状态)消费者
 */
@Service("AnRongShareOrderConsumer")
public class AnRongShareOrderConsumer implements ChannelAwareMessageListener {
    @Autowired
    private AnRongService anRongService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ApplyTripartiteAnrongService applyTripartiteAnrongService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(1000);
            CommonMessage<ShareVO> msg = parseMessage(message);
            logger.debug("安融共享订单结果消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            ShareVO shareVO = msg.getMessage();
            // 处理数据
            Map<String, String> param = loanApplyService.handleOrder(shareVO, msg.getSource());
            // 发送请求
            if (null != param) {
                ShareResultVO vo = anRongService.sendShareOrderResult(param);
                if (!msg.getSource().endsWith("3") && vo != null && vo.getErrors() == null){
                    applyTripartiteAnrongService.update(shareVO.getLoanId(),param.get("state"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
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