package com.rongdu.loans.anrong.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BlackDatas implements Serializable {

    /**
     * 欠款总额
     */
    private String arrears;
    /**
     * 报送/公开日期
     */
    private String createDate;
    /**
     * 借款地点
     */
    private String creditAddress;
    /**
     * 现居地址
     */
    private String currentAddress;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 最近逾期开始日期
     */
    private String lastOverdueDate;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 电话
     */
    private String phone;
    /**
     * 户籍地址
     */
    private String residenceAddress;
    /**
     * 信息来源
     */
    private String source;
    /**
     * 状态
     */
    private String status;
}