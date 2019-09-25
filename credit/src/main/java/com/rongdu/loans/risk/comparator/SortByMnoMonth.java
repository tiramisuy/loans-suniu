package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoMui;

import java.util.Comparator;

/**
 * 比较运营商月份
 * @author sunda
 * @version 2017-08-14
 */
public class SortByMnoMonth implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ReportMnoMui object1 = (ReportMnoMui)o1;
        ReportMnoMui object2 = (ReportMnoMui)o2;
        long m1 = DateUtils.parseDate(object1.getMonth()).getTime();
        long m2 = DateUtils.parseDate(object2.getMonth()).getTime();
        if (m1<m2){
            return -1;
        }
        if (m1>m2){
            return 1;
        }
        return 0;
    }
}
