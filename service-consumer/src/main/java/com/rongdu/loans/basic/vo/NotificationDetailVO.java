package com.rongdu.loans.basic.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */
public class NotificationDetailVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3183890389741594384L;

    /**
     * 有效消息统计
     */
    private Integer validCount;

    /**
     * 公告列表
     */
    private List<NotificationVO> NotificationList;

    public Integer getValidCount() {
        return validCount;
    }

    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
    }

    public List<NotificationVO> getNotificationList() {
        return NotificationList;
    }

    public void setNotificationList(List<NotificationVO> notificationList) {
        NotificationList = notificationList;
    }

}
