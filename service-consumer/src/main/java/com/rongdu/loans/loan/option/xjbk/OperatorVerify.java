package com.rongdu.loans.loan.option.xjbk;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OperatorVerify implements Serializable{

    private static final long serialVersionUID = -4652827438818878844L;
    private Basic basic;
    private List<Calls> calls;
    private String datasource;
    private List<Nets> nets;
    private List<Smses> smses;
    private List<Transactions> transactions;

}