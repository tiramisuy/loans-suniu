package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import com.rongdu.loans.loan.option.xjbk.ContactRegion;

import java.util.Comparator;


public class SortByContactRegion implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ContactRegion ccm1 = (ContactRegion) o1;
        ContactRegion ccm2 = (ContactRegion) o2;
        if (ccm1.getRegionUniqNumCnt() < ccm2.getRegionUniqNumCnt()) {
            return -1;
        }
        if (ccm1.getRegionUniqNumCnt() > ccm2.getRegionUniqNumCnt()) {
            return 1;
        }
        return 0;
    }
}
