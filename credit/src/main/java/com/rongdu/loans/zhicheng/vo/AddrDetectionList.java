package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class AddrDetectionList implements Serializable {

    private String addr;
    private AddrEffective addrEffective;
    private AddrMobileMatching addrMobileMatching;
    private AddrTimeEffective addrTimeEffective;
    private EffectiveAddrNum effectiveAddrNum;
    private String mobile;
    private RecentAddrTime recentAddrTime;
    private String type;
}
