package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 联系人表映射数据实体
 * Created by likang on 2017/6/27.
 */
public class Contact extends BaseEntity<Contact> {

    // 序列号
    private static final long serialVersionUID = 873769116246128268L;

    private String userId;		// 客户ID
    private Integer relationship; // 与本人关系
    private String name		;// 联系人姓名
    private String mobile;		// 手机号码

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
