package com.rongdu.loans.common;

import java.util.List;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.sys.entity.Office;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.OfficeService;

public class RoleControlUtils {

	public static List<ChannelVO> getChannelList(){
		ChannelService channelService =SpringContextHolder.getBean(ChannelService.class);
		List<ChannelVO> channelList = channelService.findAllChannel();
		return channelList;
	}
	
	public static List<Office> getCompanyList(){
		OfficeService officeService =SpringContextHolder.getBean(OfficeService.class);
		List<Office> companyList = officeService.getAllCompanyFromCache();
		return companyList;
	}
	
	public static LoanProductEnum[] getProductList(){
		return LoanProductEnum.values();
	}
}
