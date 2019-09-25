package com.rongdu.loans.anrong.op;

import lombok.Data;

import java.io.Serializable;

/**
 * 安融-迭代获取待共享接口-请求报文
 * @author fy
 * @version 2019-06-17
 */
@Data
public class IterationShareOP implements Serializable {

    private String member;
    private String sign;
    /**
     * “1”获取合同/审批待共享列表；“2”获取合同结清状态待共享列表；“3”获取逾期状态待共享列表；“4”获取审批结果待共享列表；“5”获取审批通过无合同待共享列表
     * 其中4、5与1的区别为：“1”反馈的列表包括没有审批结果的和审批通过没有合同的；而“4”是单独未审批的（或延迟处理的），“5”是单独审批通过无合同的；也就是1是4和5的并集
     */
    private String type;
    private String maxId;
}
