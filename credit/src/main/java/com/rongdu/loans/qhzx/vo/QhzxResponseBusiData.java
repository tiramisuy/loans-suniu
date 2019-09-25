package com.rongdu.loans.qhzx.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前海征信-请求对象-业务数据
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxResponseBusiData {
	
	/**
	 * 批次号
	 */
	private String batchNo = "";
	/**
	 * 具体业务请求参数
	 * (上限50条)
	 */
	private List<Map<String, String>> records = new ArrayList<Map<String, String>>();
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public List<Map<String, String>> getRecords() {
		return records;
	}
	public void setRecords(List<Map<String, String>> records) {
		this.records = records;
	}

}
