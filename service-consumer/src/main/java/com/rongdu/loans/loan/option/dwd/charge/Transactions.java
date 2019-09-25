package com.rongdu.loans.loan.option.dwd.charge;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class Transactions implements Serializable {

    private static final long serialVersionUID = -1865888472044685874L;
    private List<Smses> smses;
    private List<Calls> calls;
    private List<Nets> nets;
    private String datasource;
    private String juid;
    private List<Transaction> transactions;
    private Basic basic;
    private String version;
    private String token;

}