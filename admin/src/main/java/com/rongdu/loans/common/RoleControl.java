package com.rongdu.loans.common;

import java.util.ArrayList;
import java.util.List;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.sys.utils.UserUtils;

public class RoleControl {
	public final static List<RoleProductConfig> ROLE_PRODUCT_LIST = new ArrayList<RoleProductConfig>();
	public final static List<String> ROLE_STORE_FILTER_LIST = new ArrayList<String>();
	static {
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("ccd", LoanProductEnum.CCD));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("zjd", LoanProductEnum.ZJD));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("tyd", LoanProductEnum.TYD));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("tfl", LoanProductEnum.TFL));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("lyfq", LoanProductEnum.LYFQ));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("jbqb", LoanProductEnum.XJD));
		ROLE_PRODUCT_LIST.add(new RoleProductConfig("xjdfq", LoanProductEnum.XJDFQ));

		ROLE_STORE_FILTER_LIST.add("jbqb");
		ROLE_STORE_FILTER_LIST.add("xjdfq");
		ROLE_STORE_FILTER_LIST.add("allstore");
	}

	public static RoleControlParam get(String queryProductId, String queryCompanyId) {
		RoleControlParam p = new RoleControlParam();
		// 是否拥有超级角色
		Boolean isSuperRole = UserUtils.haveRole("superRole");
		if (isSuperRole) {
			// 所有公司所有产品 或者 某个产品所有门店
			p.setCompanyId(queryCompanyId);
			p.setProductId(StringUtils.isNotBlank(queryProductId) ? "'" + queryProductId + "'" : null);
		} else {
			// 门店
			p.setCompanyId(UserUtils.getUser().getCompany().getId());
			for (String r : ROLE_STORE_FILTER_LIST) {
				if (UserUtils.haveRole(r)) {
					p.setCompanyId("");
				}
			}

			// 产品
			StringBuffer sb = new StringBuffer();
			for (RoleProductConfig c : ROLE_PRODUCT_LIST) {
				if (UserUtils.haveRole(c.getRoleId())) {
					sb.append("'").append(c.getProduct().getId()).append("',");
				}
			}
			if (sb.length() > 0) {
				p.setProductId(sb.substring(0, sb.length() - 1));
			} else {
				p.setProductId("");
			}
		}
		return p;
	}
}
