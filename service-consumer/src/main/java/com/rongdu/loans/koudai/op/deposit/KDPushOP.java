package com.rongdu.loans.koudai.op.deposit;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/12/5.
 */
@Data
public class KDPushOP implements Serializable {
    private static final long serialVersionUID = 8889209320810774371L;
    private String platNo;
    private Integer txTime;
    private String subscribes;
}
