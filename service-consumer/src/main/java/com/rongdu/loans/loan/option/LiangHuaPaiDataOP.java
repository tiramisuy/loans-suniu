package com.rongdu.loans.loan.option;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/9/5.
 */
@Data
public class LiangHuaPaiDataOP implements Serializable {

    private static final long serialVersionUID = -3493810894515035065L;
    private String hitType;
    private String custName;
    private String idNo;
    private String ip;
    private String mobileNo;
    private String flowNo;
}
