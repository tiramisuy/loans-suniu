package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询运营商通讯信息-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class MnoContactData implements Serializable {

	private static final long serialVersionUID = -6909977101992364369L;
	/**
	 * 运营商数据
	 */
	private MnoContactDetail mnoDetail;


	public MnoContactDetail getMnoDetail() {
		return mnoDetail;
	}

	public void setMnoDetail(MnoContactDetail mnoDetail) {
		this.mnoDetail = mnoDetail;
	}

}
