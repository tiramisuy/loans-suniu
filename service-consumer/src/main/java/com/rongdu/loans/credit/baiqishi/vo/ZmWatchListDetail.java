package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 查询芝麻行业关注名单(详情)-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListDetail implements Serializable{
	
	private static final long serialVersionUID = -5732406827250736618L;
	/**
	 *风险信息行业编码，编码由芝麻进行维护和升级，会存在新增编码的可能
	 */
	private  String	bizCode;
	/**
	 * level 字段当前为保留字段，只返回 0 0 ，当前请忽略该字段
	 */
	private  String	level;
	/**
	 * 行业名单风险类型，编码由芝麻进行维护和升级，会存在新增编码的可能
	 */
	private  String	type;
	/**
	 * 风险编码，编码由芝麻进行维护和升级，会存在新增编码的可能
	 */
	private  String	code;
	/**
	 * 风险编码 对应中文描述
	 */
	private  String	codeName;
	/**
	 * 数据刷新时间
	 */
	private  String	refreshTime;
	/**
	 * 结清状态
	 */
	private  boolean settlement;
	/**
	 * 当用户进行异议处理，并核查完毕之后，仍有异议时，给出的声明
	 */
	private  String	status;
	/**
	 * 扩展信息
	 */
	private  List<ZmWatchListExtendInfo>	extendInfo;
	
	public ZmWatchListDetail(){
		
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public boolean isSettlement() {
		return settlement;
	}

	public void setSettlement(boolean settlement) {
		this.settlement = settlement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ZmWatchListExtendInfo> getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(List<ZmWatchListExtendInfo> extendInfo) {
		this.extendInfo = extendInfo;
	}

	
}

