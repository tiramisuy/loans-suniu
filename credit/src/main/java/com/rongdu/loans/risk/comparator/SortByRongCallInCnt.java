package com.rongdu.loans.risk.comparator;

import java.util.Comparator;

import com.rongdu.loans.loan.option.rongTJreportv1.CallLog;

/**  
* @Title: SortByRongCallInCnt.java  
* @Package com.rongdu.loans.risk.comparator  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年7月13日  
* @version V1.0  
*/
public  class SortByRongCallInCnt implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		CallLog ccm1 = (CallLog) o1;
		CallLog ccm2 = (CallLog) o2;
        if (ccm1.getCalledCnt() < ccm2.getCalledCnt()) {
            return -1;
        }
        if (ccm1.getCalledCnt() > ccm2.getCalledCnt()) {
            return 1;
        }
        return 0;
    }

}
