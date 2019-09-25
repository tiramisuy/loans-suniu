package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import com.rongdu.loans.loan.option.xjbk.ContactList;

import java.util.Comparator;


public class SortByCalInCnt implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ContactList ccm1 = (ContactList) o1;
        ContactList ccm2 = (ContactList) o2;
        if (ccm1.getCallInCnt() < ccm2.getCallInCnt()) {
            return -1;
        }
        if (ccm1.getCallInCnt() > ccm2.getCallInCnt()) {
            return 1;
        }
        return 0;
    }
}
