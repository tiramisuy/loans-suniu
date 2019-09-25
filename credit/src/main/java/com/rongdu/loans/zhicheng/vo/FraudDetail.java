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
public class FraudDetail implements Serializable {

    private String fraudScore;
    private String fraudLevel;
    private List<RiskResult> riskResult;
    private SocialNetwork socialNetwork;
    private List<AddrDetectionList> addrDetectionList;
    private CorpAddrDetectionResult corpAddrDetectionResult;
}
