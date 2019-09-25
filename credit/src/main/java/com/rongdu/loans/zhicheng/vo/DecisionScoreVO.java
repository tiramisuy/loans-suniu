package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:21
 */
@Data
public class DecisionScoreVO implements Serializable {

    private String compositeScore;

    private String decisionSuggest;
}
