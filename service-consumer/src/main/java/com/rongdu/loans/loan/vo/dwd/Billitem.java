package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**  
* @Title: Billitem.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class Billitem implements Serializable{

	private static final long serialVersionUID = -761081972198356212L;

	private String feetype;
	
	private BigDecimal dueamount;
}
