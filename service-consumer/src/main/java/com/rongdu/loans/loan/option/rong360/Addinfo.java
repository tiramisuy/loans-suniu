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
public class Addinfo implements Serializable{

	private static final long serialVersionUID = 6534328077386654779L;
	
	private Zhima zhima;
    private Mobile mobile;
    private List<Icredit> icredit;
    private List<Alipay> alipay;
    private Ibank ibank;
    private List<Ec> ec;
    private Jd jd;
    private Insure insure;
    @JsonProperty("tjy_model")
    private TjyModel tjyModel;
    private Fund fund;
    private Contacts contacts;
    public void setZhima(Zhima zhima) {
         this.zhima = zhima;
     }
     public Zhima getZhima() {
         return zhima;
     }

    public void setMobile(Mobile mobile) {
         this.mobile = mobile;
     }
     public Mobile getMobile() {
         return mobile;
     }

    public void setIcredit(List<Icredit> icredit) {
         this.icredit = icredit;
     }
     public List<Icredit> getIcredit() {
         return icredit;
     }

    public void setAlipay(List<Alipay> alipay) {
         this.alipay = alipay;
     }
     public List<Alipay> getAlipay() {
         return alipay;
     }

    public void setIbank(Ibank ibank) {
         this.ibank = ibank;
     }
     public Ibank getIbank() {
         return ibank;
     }

    public void setEc(List<Ec> ec) {
         this.ec = ec;
     }
     public List<Ec> getEc() {
         return ec;
     }

    public void setJd(Jd jd) {
         this.jd = jd;
     }
     public Jd getJd() {
         return jd;
     }

    public void setInsure(Insure insure) {
         this.insure = insure;
     }
     public Insure getInsure() {
         return insure;
     }

    public void setTjyModel(TjyModel tjyModel) {
         this.tjyModel = tjyModel;
     }
     public TjyModel getTjyModel() {
         return tjyModel;
     }

    public void setFund(Fund fund) {
         this.fund = fund;
     }
     public Fund getFund() {
         return fund;
     }

    public void setContacts(Contacts contacts) {
         this.contacts = contacts;
     }
     public Contacts getContacts() {
         return contacts;
     }

}