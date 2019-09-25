package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;

import java.util.Comparator;

/**
 * 比较通话时长
 * @author sunda
 * @version 2017-08-14
 */
public class SortByConnectTime  implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ReportMnoCcm ccm1 = (ReportMnoCcm)o1;
        ReportMnoCcm ccm2 = (ReportMnoCcm)o2;
        if (ccm1.getConnectTime()<ccm2.getConnectTime()){
            return -1;
        }
        if (ccm1.getConnectTime()>ccm2.getConnectTime()){
            return 1;
        }
        return 0;
    }
}
