package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Tel implements Serializable{

	private static final long serialVersionUID = 9129384067407983934L;
	
	private List<Teldata> teldata;
    private String url;
    public void setTeldata(List<Teldata> teldata) {
         this.teldata = teldata;
     }
     public List<Teldata> getTeldata() {
         return teldata;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}