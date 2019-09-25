package com.rongdu.loans.loan.option.rong360Model;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Auto-generated: 2018-07-03 10:38:10
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Mobile implements Serializable{

	private static final long serialVersionUID = 2533921630071223215L;
	
	private User user;
    private Userdata userdata;
    private List<Tel> tel;
    private List<Msg> msg;
    private List<Bill> bill;
    private List<Recharge> recharge;
    private List<Net> net;

}