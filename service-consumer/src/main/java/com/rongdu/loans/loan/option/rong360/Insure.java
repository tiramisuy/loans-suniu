package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Insure implements Serializable{

	private static final long serialVersionUID = 4078200957391172337L;
	
	private User user;
    private List<Flow> flow;
    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void setFlow(List<Flow> flow) {
         this.flow = flow;
     }
     public List<Flow> getFlow() {
         return flow;
     }

}