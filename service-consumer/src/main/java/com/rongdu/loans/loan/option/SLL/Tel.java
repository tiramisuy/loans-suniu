package com.rongdu.loans.loan.option.SLL;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Tel implements Serializable{

    private static final long serialVersionUID = -1863414382804178804L;
    private List<Teldata> teldata;
    public void setTeldata(List<Teldata> teldata) {
         this.teldata = teldata;
     }
     public List<Teldata> getTeldata() {
         return teldata;
     }

}