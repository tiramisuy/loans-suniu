package com.rongdu.loans.loan.option.SLL;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Mobile implements Serializable{

    private static final long serialVersionUID = -6229649781888762489L;
    private int uptime;
    private User user;
    private Tel tel;
    private Msg msg;
    private List<Bill> bill;
    private List<Net> net;
    public void setUptime(int uptime) {
         this.uptime = uptime;
     }
     public int getUptime() {
         return uptime;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void setTel(Tel tel) {
         this.tel = tel;
     }
     public Tel getTel() {
         return tel;
     }

    public void setMsg(Msg msg) {
         this.msg = msg;
     }
     public Msg getMsg() {
         return msg;
     }

    public void setBill(List<Bill> bill) {
         this.bill = bill;
     }
     public List<Bill> getBill() {
         return bill;
     }

    public void setNet(List<Net> net) {
         this.net = net;
     }
     public List<Net> getNet() {
         return net;
     }

}