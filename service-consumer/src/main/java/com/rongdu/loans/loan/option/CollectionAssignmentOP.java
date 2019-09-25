package com.rongdu.loans.loan.option;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/9/29.
 */
public class CollectionAssignmentOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;
    /**
     * 拼装的id
     */
    @NotEmpty(message = "id不能为空")
    private List<String> idList;
    /**
     * 催收员id
     */
    @NotBlank(message = "催收员id为空")
    private String id;
    /**
     * 催收员姓名
     */
    /*@NotBlank(message = "催收员姓名为空")*/
    private String name;
    /**
     * 操作员id
     */
    @NotBlank(message = "操作员id为空")
    private String operatorId;
    /**
     * 操作员姓名
     */
    @NotBlank(message = "操作员姓名为空")
    private String operatorName;
    /**
     * 退回类型
     */
    @NotNull(message = "退回类型不能为空")
    private Integer returnType;
    /**
     * 退回时间
     */
    private Date returnTime;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
