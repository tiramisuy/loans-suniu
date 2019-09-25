package com.rongdu.loans.baiqishi.vo;

import com.rongdu.loans.baiqishi.entity.MnoBillRecord;
import com.rongdu.loans.baiqishi.entity.MnoCallRecord;
import com.rongdu.loans.baiqishi.entity.MnoDetail;
import com.rongdu.loans.baiqishi.entity.MnoSmsRecord;

import java.io.Serializable;
import java.util.List;

/**
 * 运营商详细信息
 * @author sunda
 * @version 2017-07-10
 */
public class MnoContactDetail implements Serializable {

	private static final long serialVersionUID = -4772970826740978137L;
	/**
	 * 原始数据存储时间（时间戳)
	 */
	public String storeTime;
	/**
	 * 运营商数据
	 */
	public MnoDetail mnoPersonalInfo;

	/**
	 * 通话记录
	 */
	private List<MnoCallRecord> mnoCallRecords;
	/**
	 * 短信记录
	 */
	private List<MnoSmsRecord> mnoSmsRecords;
	/**
	 * 账单记录
	 */
	private List<MnoBillRecord> mnoBillRecords;

	public MnoContactDetail() {
	}

	public String getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(String storeTime) {
		this.storeTime = storeTime;
	}

	public MnoDetail getMnoPersonalInfo() {
		return mnoPersonalInfo;
	}

	public void setMnoPersonalInfo(MnoDetail mnoPersonalInfo) {
		this.mnoPersonalInfo = mnoPersonalInfo;
	}

	public List<MnoCallRecord> getMnoCallRecords() {
		return mnoCallRecords;
	}

	public void setMnoCallRecords(List<MnoCallRecord> mnoCallRecords) {
		this.mnoCallRecords = mnoCallRecords;
	}

	public List<MnoSmsRecord> getMnoSmsRecords() {
		return mnoSmsRecords;
	}

	public void setMnoSmsRecords(List<MnoSmsRecord> mnoSmsRecords) {
		this.mnoSmsRecords = mnoSmsRecords;
	}

	public List<MnoBillRecord> getMnoBillRecords() {
		return mnoBillRecords;
	}

	public void setMnoBillRecords(List<MnoBillRecord> mnoBillRecords) {
		this.mnoBillRecords = mnoBillRecords;
	}
}
