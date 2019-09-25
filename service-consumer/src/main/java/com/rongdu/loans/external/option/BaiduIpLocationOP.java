package com.rongdu.loans.external.option;

import java.io.Serializable;

public class BaiduIpLocationOP implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6517086351224224576L;
    
    /**
     * IP地址
     */
    private String ip;
    /**
     * 开发者密钥
     */
    private String ak;
    /**
     * 用户的权限签名
     */
    private String sn;
    /**
     * 输出的坐标格式
     */
    private String coor;
}
