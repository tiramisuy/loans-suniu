package com.rongdu.loans.loan.option.dwd.report;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 3436762081626364786L;
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