package com.rongdu.loans.loan.option.SLL;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Msg  implements Serializable{

    private static final long serialVersionUID = 8974309319087035943L;
    private List<Msgdata> msgdata;
    public void setMsgdata(List<Msgdata> msgdata) {
         this.msgdata = msgdata;
     }
     public List<Msgdata> getMsgdata() {
         return msgdata;
     }

}