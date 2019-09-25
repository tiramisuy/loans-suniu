package com.rongdu.loans.loan.vo.jdq;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * Auto-generated: 2018-10-14 10:30:33
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class LoanTerm implements Serializable{

	private static final long serialVersionUID = -6419045184102536834L;
	
    private String repayTime;
    private BigDecimal repayAmount;

}