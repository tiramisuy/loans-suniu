package com.rongdu.loans.basic.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.SendMailUtil;
import com.rongdu.loans.basic.service.AlarmService;
import com.rongdu.loans.basic.service.ShortMsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("alarmService")
public class AlarmServiceImpl extends BaseService implements AlarmService {

    @Autowired
    private ShortMsgService shortMsgService;


    /**
     * 预警短信
     * @param mobiles 多个手机号码用,号分隔
     * @param message 短消息，最多70个字符
     */
    public void sendAlarmSms(String mobiles,String message){
        logger.info("发送预警邮件：提示信息：{}", message);
/*        SendShortMsgOP op = new SendShortMsgOP();
        op.setMessage(message);
        op.setMobile(mobiles);
        op.setMsgType(99);
        op.setSource(String.valueOf(SourceEnum.SYSTEM.getValue()));
        op.setIp("127.0.0.1");
        shortMsgService.saveSmsLog(op,null);
        String url = "http://apo.rongdu.com/sms/sendSms.form?mobile={mobile}&content={message}";
        RestTemplate client = new RestTemplate();
        try {
            logger.info("发送预警短信：{}，【{}】", mobiles,message);
            String result = client.getForObject(url, String.class, mobiles,new String(message.getBytes("utf-8"), "iso-8859-1"));
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        
		//SendMailUtil.sendCommonMail("275819103@qq.com", "预警信息", message);
		
    }

}
