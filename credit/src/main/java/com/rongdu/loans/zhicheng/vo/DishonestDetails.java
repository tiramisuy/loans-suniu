package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class DishonestDetails implements Serializable {

    private String areaName;
    private String bussinessEntity;
    private String caseCode;
    private String courtName;
    private String disruptTypeName;
    private String duty;
    private String entityId;
    private String entityName;
    private String gistId;
    private String gistUnit;
    private String performance;
    private String isRemoved;
    private String publishDate;
    private String regDate;
}
