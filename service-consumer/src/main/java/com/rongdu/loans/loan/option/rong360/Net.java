package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Net implements Serializable{

	private static final long serialVersionUID = -7859072999518129684L;
	
	@JsonProperty("netdata_key")
    private String netdataKey;
    private List<Netdata> netdata;
    public void setNetdataKey(String netdataKey) {
         this.netdataKey = netdataKey;
     }
     public String getNetdataKey() {
         return netdataKey;
     }

    public void setNetdata(List<Netdata> netdata) {
         this.netdata = netdata;
     }
     public List<Netdata> getNetdata() {
         return netdata;
     }

}