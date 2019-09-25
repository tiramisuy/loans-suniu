package com.rongdu.loans.tlblackrisk.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.vo
 * @date 2019/7/25 14:59
 */
@Data
public class TongLianBlackDetail implements Serializable {

    private String result;
    private String resultMsg;
    private FinancialMsg financialMsg;
}
