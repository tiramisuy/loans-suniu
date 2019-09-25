package com.rongdu.loans.loan.option.jdq;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-10-14 10:55:49
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Operator implements Serializable{

    private static final long serialVersionUID = 892225486076642410L;
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