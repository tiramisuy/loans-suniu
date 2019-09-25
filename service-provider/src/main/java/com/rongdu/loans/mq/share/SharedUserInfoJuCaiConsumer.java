package com.rongdu.loans.mq.share;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserInfoVO;
import com.rongdu.loans.loan.option.share.JCUserInfo;
import com.rongdu.loans.loan.service.SharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service("sharedUserInfoJuCaiConsumer")
public class SharedUserInfoJuCaiConsumer implements ChannelAwareMessageListener {
    @Autowired
    private SharedService sharedService;
    @Autowired
    private CustUserService custUserService;


    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //控制接口调用频率
            Thread.sleep(3000);
            CommonMessage<String> msg = parseMessage(message);
            log.debug("【共享服务-聚财-推送用户订单信息】消费者：{}，{}，{}", msg.getSource(), msg.getType(), msg.getBizId());
            String orderNo = msg.getMessage();
            // 设定参数
            JCUserInfo param = new JCUserInfo();
            FileInfoVO additionVO = custUserService.getLastDWDAdditionalByOrderSn(orderNo);
            param.setAdditionDataUrl(additionVO.getUrl());
            FileInfoVO chargeInfoVO = custUserService.getLastDWDChargeInfoByOrderSn(orderNo);
            param.setChargeDataUrl(chargeInfoVO.getUrl());
            List<FileInfoVO> fileinfo = custUserService.getFileinfo(chargeInfoVO.getUserId());
            for (FileInfoVO fileInfoVO : fileinfo) {
                if (FileBizCode.FRONT_IDCARD.getBizCode().equals(fileInfoVO.getBizCode())){
                    param.setFrontIdcard(fileInfoVO.getUrl());
                }
                if (FileBizCode.BACK_IDCARD.getBizCode().equals(fileInfoVO.getBizCode())){
                    param.setBackIdcard(fileInfoVO.getUrl());
                }
                if (FileBizCode.FACE_VERIFY.getBizCode().equals(fileInfoVO.getBizCode())){
                    param.setFaceVerify(fileInfoVO.getUrl());
                }
            }
            CustUserInfoVO userInfo = custUserService.getSimpleUserInfo(chargeInfoVO.getUserId());
            param.setName(userInfo.getUserName());
            param.setMobile(userInfo.getMobile());
            param.setIdCard(userInfo.getIdNo());
            param.setWorkCity(userInfo.getWorkCity());

            boolean flag = sharedService.shareToJuCai(param);
            if (flag) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                        message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBody());
            }

        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                    message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBody());
            log.error("【共享服务-聚财-推送用户订单信息】处理失败",  e);
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