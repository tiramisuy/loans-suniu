package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Mobile implements Serializable{

	private static final long serialVersionUID = 427046482307947051L;
	
	private User user;
    private Userdata userdata;
    private List<Tel> tel;
    private List<Msg> msg;
    private List<Bill> bill;
    private List<Net> net;
    private List<Recharge> recharge;
    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void setUserdata(Userdata userdata) {
         this.userdata = userdata;
     }
     public Userdata getUserdata() {
         return userdata;
     }

    public void setTel(List<Tel> tel) {
         this.tel = tel;
     }
     public List<Tel> getTel() {
         return tel;
     }

    public void setMsg(List<Msg> msg) {
         this.msg = msg;
     }
     public List<Msg> getMsg() {
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

    public void setRecharge(List<Recharge> recharge) {
         this.recharge = recharge;
     }
     public List<Recharge> getRecharge() {
         return recharge;
     }

}