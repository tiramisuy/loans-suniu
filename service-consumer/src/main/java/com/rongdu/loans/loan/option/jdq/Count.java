package com.rongdu.loans.loan.option.jdq;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/10/17.
 */
@Data
public class Count implements Serializable {

    private static final long serialVersionUID = 5298569440829139125L;
    private int count;
    private int sum;
    private String gsd;
    private String lastCall;
    private String firstCall;
}
