package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class CorpAddrDetectionResult implements Serializable {

    private List<CorpAddrSimilarList> corpAddrSimilarList;
    private String corpName;
    private String corpAddr;
    private String resultVerificationCode;
    private String resultVerificationDesc;
}
