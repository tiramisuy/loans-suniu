package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import java.util.Comparator;
import java.util.Map;

/**
 * 比较通话时长
 * @author sunda
 * @version 2017-08-14
 */
public class SortByContactConnectTime  implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
    	Map<String, Object> m1 = (Map<String, Object>)o1;
    	Map<String, Object> m2 = (Map<String, Object>)o2;
    	Integer connectTime1=(Integer) m1.get("connectTime");
    	Integer connectTime2=(Integer) m2.get("connectTime");
        if (connectTime1<connectTime2){
            return -1;
        }
        if (connectTime1>connectTime2){
            return 1;
        }
        return 0;
    }
}
