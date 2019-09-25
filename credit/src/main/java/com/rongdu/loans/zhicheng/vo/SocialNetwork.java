package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class SocialNetwork implements Serializable {

    private Integer activeCallBlackCnt;
    private Integer activeCallCnt;
    private Integer activeCallOverdueCnt;
    private Integer age;
    private Integer courtCallNum;
    private Integer fictionCallCnt;
    private Integer fictionCallNum;
    private Integer fictionCallSeconds;
    private Integer firstOrderBlackCnt;
    private String firstOrderBlackRate;
    private Integer firstOrderM3Cnt;
    private Integer firstOrderOverdueCnt;
    private String firstOrderOverdueRate;
    private String gender;
    private String idNoLocation;
    private Integer lawyerCallNum;
    private Integer loanCallNum;
    private Integer loanCallSeconds;
    private Integer macaoCallCnt;
    private Integer macaoCallNum;
    private Integer macaoCallSeconds;
    private String mobileLocation;
    private String mobileOperator;
    private Integer nightCallCnt;
    private Integer nightCallNum;
    private Integer nightCallSeconds;
    private Integer remoteCallCnt;
    private Integer remoteCallNum;
    private Integer remoteCallSeconds;
    private Integer secondOrderBlackCnt;
    private Integer secondOrderM3Cnt;
    private Integer secondOrderOverdueCnt;
    private Integer loanCallCnt;
}
