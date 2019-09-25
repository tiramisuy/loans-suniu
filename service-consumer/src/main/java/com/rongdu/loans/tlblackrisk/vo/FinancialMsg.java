package com.rongdu.loans.tlblackrisk.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.vo
 * @date 2019/7/25 15:01
 */
@Data
public class FinancialMsg implements Serializable {

    private String isBlack;
    private String level;
}
