package com.rongdu.loans.loan.option.share;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/20
 * @since 1.0.0
 */
@Data
public class CustInfo implements Serializable {

    private String custName;// 用户姓名
    private String idNo;// 身份证号
    private String phone;// 手机号
    private Date refuseTime;// 拒绝时间
    private String type;// 1 机器审核；2 人工审核
    private String ruleCode;// 命中规则编码
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String remark;

}
