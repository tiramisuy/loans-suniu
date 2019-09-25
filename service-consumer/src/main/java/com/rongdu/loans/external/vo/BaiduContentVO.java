package com.rongdu.loans.external.vo;

import java.io.Serializable;

public class BaiduContentVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -6727888466086362385L;
    /**
     * 简要地址  
     */
    private String address;

    /**
     * 详细地址信息 
     */
    private BaiduAddressDetailVO address_detail;
    
    /**
     * 当前城市中心点(经纬度坐标)
     */
    private BaiduPointVO point;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BaiduAddressDetailVO getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(BaiduAddressDetailVO address_detail) {
        this.address_detail = address_detail;
    }

	public BaiduPointVO getPoint() {
		return point;
	}

	public void setPoint(BaiduPointVO point) {
		this.point = point;
	}
    
}
