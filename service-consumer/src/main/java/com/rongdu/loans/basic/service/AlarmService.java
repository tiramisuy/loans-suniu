package com.rongdu.loans.basic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 报警服务
 * @author sunda
 * @version 2017-07-13
 */
public interface AlarmService {


    /**
     * 报警短信
     * @param mobiles 多个手机号码用,号分隔
     * @param message 短消息，最多70个字符
     */
    public void sendAlarmSms(String mobiles,String message);

}
