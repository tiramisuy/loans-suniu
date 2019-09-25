package com.rongdu.loans.risk.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈风控白名单VO〉
 *
 * @author yuanxianchu
 * @create 2019/1/24
 * @since 1.0.0
 */
@Data
public class WhitelistVO implements Serializable {
    private static final long serialVersionUID = 8038024877287953735L;

    private String id;

    /**
     *用户ID
     */
    private String userId;
    /**
     *证件号码
     */
    private String idNo;
    /**
     *手机号码
     */
    private String mobile;
    /**
     *用户姓名
     */
    private String name;
    /**
     *来源类型：1-平台优质客户; 2-外部导入数据;3-特殊名单
     */
    private Integer sourceType;
    /**
     *来源渠道
     */
    private String sourceChannel;
    /**
     *进出白名单的时间
     */
    private Date time;
    /**
     *白名单状态：0-注销;1-生效
     */
    private Integer status;

    private String createBy;
    private long createTime;

    private String updateBy;
    private long updateTime;
}
