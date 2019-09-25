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
public class Icredit implements Serializable{

	private static final long serialVersionUID = 1501554860195465407L;
	
	private List<Card> card;
    @JsonProperty("bill_flow")
    private List<BillFlow> billFlow;
    public void setCard(List<Card> card) {
         this.card = card;
     }
     public List<Card> getCard() {
         return card;
     }

    public void setBillFlow(List<BillFlow> billFlow) {
         this.billFlow = billFlow;
     }
     public List<BillFlow> getBillFlow() {
         return billFlow;
     }

}