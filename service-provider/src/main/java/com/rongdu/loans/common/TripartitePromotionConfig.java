package com.rongdu.loans.common;

import java.math.BigDecimal;

import com.rongdu.common.config.Global;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.loan.option.PromotionCaseOP;

/**  
* @Title: TripartitePromotionConfig.java  
* @Package com.rongdu.loans.common  
* @Description: 三方营销产品配置  
* @author: yuanxianchu  
* @date 2018年10月31日  
* @version V1.0  
*/
public class TripartitePromotionConfig {

	public static PromotionCaseOP getPromotionCase(String flag) {
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        switch (flag) {
            case "3":
                promotionCaseOP.setApplyAmt(new BigDecimal("2000.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_AUTO_FQ_DAY_28);
                promotionCaseOP.setProductId(LoanProductEnum.JDQ.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(7);
                promotionCaseOP.setTerm(4);
                break;
            case "4":
                promotionCaseOP.setApplyAmt(new BigDecimal("1500.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_DQ_DAY_8);
                promotionCaseOP.setProductId(LoanProductEnum.JN.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(15);
                promotionCaseOP.setTerm(1);
                break;
            case "5":
                promotionCaseOP.setApplyAmt(new BigDecimal("2000.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_DQ_DAY_8);
                promotionCaseOP.setProductId(LoanProductEnum.JN2.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(15);
                promotionCaseOP.setTerm(1);
                break;
            case "6":
                promotionCaseOP.setApplyAmt(new BigDecimal("2500.00"));
                promotionCaseOP.setApplyTerm(Global.XJD_AUTO_FQ_DAY_28);
                promotionCaseOP.setProductId(LoanProductEnum.JNFQ.getId());

                promotionCaseOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                promotionCaseOP.setRepayFreq("D");
                promotionCaseOP.setRepayUnit(7);
                promotionCaseOP.setTerm(4);
                break;
            default:
                promotionCaseOP = getPromotionCase("3");
                break;
        }
        return promotionCaseOP;
    }
	
}
