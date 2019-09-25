package com.rongdu.common.config;

/**
 * 现金贷生命周期
 * @author likang
 * @version 2017-07-06
 */
public class BaiduMap {

	/**
	 * 国际地址
	 */
	public static final String FIR_ADDRESS_KEY = "address";

	/**
	 * 详细内容
	 */
	public static final String FIR_CONTENT_KEY = "content";
	
	/**
	 * 详细内容-地址
	 */
	public static final String CONTENT_ADDRESS_KEY = "address";
	
	/**
	 * 详细内容-地址明细
	 */
	public static final String CONTENT_ADDRESS_DETAIL_KEY = "address_detail";
	
	/**
	 * 详细内容-地址明细-城市
	 */
	public static final String ADDRESSDETAIL_CITY_KEY = "city";
	
	/**
	 * 详细内容-地址明细-城市码
	 */
	public static final String ADDRESSDETAIL_CITY_CODE_KEY = "city_code";
	
	/**
	 * 详细内容-地址明细-区县
	 */
	public static final String ADDRESSDETAIL_DISTRICT_KEY = "district";
	
	/**
	 * 详细内容-地址明细-省份
	 */
	public static final String ADDRESSDETAIL_PROVINCE_KEY = "province";
	
	/**
	 * 详细内容-地址明细-街道
	 */
	public static final String ADDRESSDETAIL_STREET_KEY = "street";
	
	/**
	 * 详细内容-地址明细-门牌
	 */
	public static final String ADDRESSDETAIL_STREET_NUMBER_KEY = "street_number";
	

	/**
	 * 当前城市中心点(坐标)
	 */
	public static final String FIR_POINT_KEY = "point";
	
	/**
	 * 当前城市中心点(坐标)-经度
	 */
	public static final String POINT_X_KEY = "x";
	
	/**
	 * 当前城市中心点(坐标)-纬度
	 */
	public static final String POINT_Y_KEY = "y";
	
	
	/**
	 * sk
	 */
	public static final String AK_KEY = "ak";
	
	/**
	 * sn
	 */
	public static final String SN_KEY = "sn";
	
	/**
	 * ip
	 */
	public static final String IP_KEY = "ip";
	
	/**
	 * coor
	 */
	public static final String COOR_KEY = "coor";
	
	/**
	 * 坐标格式-百度经纬度坐标（默认）
	 */
	public static final String COOR_BD09LL = "bd09ll";
	
	/**
	 * 坐标格式-百度墨卡托坐标
	 */
	public static final String COOR_BD09MC = "bd09mc";
	
	/**
	 * 坐标格式-国测局坐标
	 */
	public static final String COOR_GCJ02 = "gcj02";
	
	/**
	 * 接口成功返回码
	 */
	public static final Integer SUCCSS_STATUS = 0;

}
