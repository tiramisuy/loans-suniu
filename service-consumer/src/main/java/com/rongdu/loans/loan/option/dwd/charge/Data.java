package com.rongdu.loans.loan.option.dwd.charge;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Data implements Serializable {

    private static final long serialVersionUID = -8534887767648398615L;
    private String msg;
    private Report report;
    private int state;
    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setReport(Report report) {
         this.report = report;
     }
     public Report getReport() {
         return report;
     }

    public void setState(int state) {
         this.state = state;
     }
     public int getState() {
         return state;
     }

}