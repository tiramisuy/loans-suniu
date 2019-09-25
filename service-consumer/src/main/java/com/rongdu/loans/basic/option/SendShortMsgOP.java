package com.rongdu.loans.basic.option;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
@Data
public class SendShortMsgOP implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = -7113946971315875419L;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 信息
     */
    private String message;
    
    /**
     * 信息类型（请在此扩展）
	 * 1-注册
	 * 2-...
	 * 99-报警短信
     */
    private Integer msgType;
    
    /**
     * 用户id
     */
    private String userId;
    
    /**
     * ip
     */
    private String ip;
    
    /**
	 * 来源于哪个终端（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private String source;
	
	/**
	 * 渠道id
	 */
	private String channelId;
	
	private String remark;
	
	/**
	 * 产品
	 */
	private String productId;
	
	private String channelName;

	/**
	 * 聚合数据短信模板ID
	 */
	private String tplId;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 当前还款期数
	 */
	private String term;

	/**
	 * 当前还款金额
	 */
	private BigDecimal amount;

}
