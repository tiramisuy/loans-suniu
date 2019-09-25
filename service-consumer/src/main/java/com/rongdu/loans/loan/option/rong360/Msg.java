package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Msg implements Serializable{

	private static final long serialVersionUID = 4692690899412831223L;
	
	private List<Msgdata> msgdata;
    private String url;
    public void setMsgdata(List<Msgdata> msgdata) {
         this.msgdata = msgdata;
     }
     public List<Msgdata> getMsgdata() {
         return msgdata;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}