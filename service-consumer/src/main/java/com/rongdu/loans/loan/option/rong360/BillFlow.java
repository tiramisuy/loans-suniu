package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class BillFlow implements Serializable{

	private static final long serialVersionUID = 2747309813676841776L;
	
	private Bill bill;
    private List<Flow> flow;
    public void setBill(Bill bill) {
         this.bill = bill;
     }
     public Bill getBill() {
         return bill;
     }

    public void setFlow(List<Flow> flow) {
         this.flow = flow;
     }
     public List<Flow> getFlow() {
         return flow;
     }

}