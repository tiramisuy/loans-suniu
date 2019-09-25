package com.rongdu.loans.risk.comparator;

/**
 * Created by MARK on 2017/8/23.
 */

import java.util.Comparator;

import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;

/**
 * 比较通话次数
 * 
 * @author liuzhuang
 * @version 2017-08-14
 */
public class SortByOriginatingCallCount implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		ReportMnoCcm ccm1 = (ReportMnoCcm) o1;
		ReportMnoCcm ccm2 = (ReportMnoCcm) o2;
		if (ccm1.getOriginatingCallCount() < ccm2.getOriginatingCallCount()) {
			return -1;
		}
		if (ccm1.getOriginatingCallCount() > ccm2.getOriginatingCallCount()) {
			return 1;
		}
		return 0;
	}
}
