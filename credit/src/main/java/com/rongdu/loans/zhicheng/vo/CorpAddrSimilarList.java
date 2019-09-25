package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class CorpAddrSimilarList implements Serializable {

    private String address;
    private String effectiveCode;
    private String effectiveDesc;
    private String sourceCode;
    private String sourceDesc;
}
