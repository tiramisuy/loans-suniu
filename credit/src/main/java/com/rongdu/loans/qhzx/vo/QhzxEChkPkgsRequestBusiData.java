package com.rongdu.loans.qhzx.vo;

import com.rongdu.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 前海征信-请求对象（好信一鉴通）-业务数据
 * 
 * @author sunda
 * 
 * @version 2017-06-20
 */
public class QhzxEChkPkgsRequestBusiData {

	/**
	 * 批次号
	 */
	private String batchNo = "JQB" + DateUtils.getDate("yyyyMMddHHmmssSSS");
	/**
	 * 1、该值是一个标准二进制字符串
	 * 2、从右到左每一位代表一种认证模式(测试环境可以查询所有的子产品的权限，生产环境切换的时候，请选择贵司签约的子产品。)，如下： 
	 * 第1位：实名验证
	 * 第2位：地址验证 
	 * 第3位：工作单位验证 
	 * 第4位：手机验证(请用第十位，为升级版) 
	 * 第5位：关系人验证 
	 * 第6位：车辆验证 
	 * 第7位：房产验证
	 * 第8位：人脸识别 
	 * 第9位：学历验证
	 * 第10位：手机验证II 
	 * 3、每位值的含义说明：'1'表示验证，'0'表示不验证
	 * 4、长度必须为16位，左补0对齐 
	 * 5、举例说明： 0000000000010101-表示本次交易进行实名认证、工作单位验证、关系人验证
	 */
	private String subProductInc = "0000000000010101";
	/**
	 * 具体业务请求参数 (上限50条)
	 */
	private List<Object> records = new ArrayList<Object>();

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}

}
