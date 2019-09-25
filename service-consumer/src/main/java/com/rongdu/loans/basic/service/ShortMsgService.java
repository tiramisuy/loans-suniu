package com.rongdu.loans.basic.service;

import com.rongdu.common.task.TaskResult;
import org.springframework.stereotype.Service;

import com.rongdu.loans.basic.option.BasicBlacklistOP;
import com.rongdu.loans.basic.option.SendShortMsgOP;

/**
 * 短信服务
 * @author likang
 * @version 2017-07-15
 */
@Service
public interface ShortMsgService {

    /**
     * 发送短信
     * @param sendShortMsgOP
     * @return
     */
    void sendMsg(SendShortMsgOP sendShortMsgOP);
	
    /**
     * 发送短信验证码
     * @param sendShortMsgOP
     * @return
     */
	String sendMsgCode(SendShortMsgOP sendShortMsgOP);

    /**
     * 保存短信发送日志
     * @param sendShortMsgOP
     * @return
     */
    int saveSmsLog(SendShortMsgOP sendShortMsgOP, String msgVerCode);
    
	/**
	 * ip 注册黑名单
	 * @param ip
	 * @return
	 */
	boolean isRegBlackList(String ip);
	
	/**
	 * ip、手机号是否录入在黑名单表
	 * @param ip
	 * @param mob
	 * @return
	 */
	boolean isInBlackListTab(String ip, String mob);
	
	/**
	 * 录入在黑名单表
	 */
	int saveToBlackListTab(BasicBlacklistOP op);

	boolean sendMsgYiMei(String seqid, String phones, String msg);

    boolean sendMsgXuanWu(String phones, String msg);
    
    boolean sendMsgKjcx(String phones, String msg);
    
    /**
     * 发送短信后不保存
     */
    void sendMsgComplainRecord(String mobile,String template);

	/**
	 * 聚合数据短信API
	 * @param mobile 手机号
	 * @param tplId 短信模板ID
	 * @param tplValue 短信模板变量值对
	 */
    void sendMsgJuHe(String mobile, String tplId, String tplValue);

	void sendShortMsg(SendShortMsgOP sendShortMsgOP);

	/**
	 * 终审通过，发送绑卡通知短信
	 * @return
	 */
	TaskResult sendBindCardMsg();

	/**
	 * 还款日前一天，发送还款提醒短信
	 * @return
	 */
	TaskResult sendRepayNotice();
}
