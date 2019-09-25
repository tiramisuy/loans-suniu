package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class WorkStudentInfo implements Serializable {

    private static final long serialVersionUID = 3775299365973516100L;
    @JsonProperty("school_name")
    private String schoolName;
    private String location;
    private String entrance;

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getEntrance() {
        return entrance;
    }

}