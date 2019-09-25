package com.rongdu.loans.loan.option.jdq;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Telecom implements Serializable {

    private static final long serialVersionUID = -4644787648136561977L;

    private Basic basic;
    private List<Calls> calls;
    private String datasource;
    private String juid;
    private List<Nets> nets;
    private List<Smses> smses;
    private String token;
    private List<Transactions> transactions;
    private String version;

}
