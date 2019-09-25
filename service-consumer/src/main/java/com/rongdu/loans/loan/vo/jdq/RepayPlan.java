package com.rongdu.loans.loan.vo.jdq;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
/**
 * Auto-generated: 2018-10-14 10:30:33
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class RepayPlan implements Serializable{

	private static final long serialVersionUID = -5356792023029535817L;
	
    private List<LoanTerm> loanTerm;
    private BigDecimal sumAmount;

}