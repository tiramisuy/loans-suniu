package com.rongdu.loans.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.CrudService;
import com.rongdu.loans.cust.vo.CustUserAllotVO;
import com.rongdu.loans.cust.vo.CustUserExportVO;
import com.rongdu.loans.sys.dao.CustUserExportDAO;


@Service("expCustService")
public class CustUserExportService extends CrudService<CustUserExportDAO,CustUserExportVO> {
	

	public  Page<CustUserExportVO> findAllLoanApply(Page<CustUserExportVO> page,CustUserExportVO custOP) {
		return super.findPage(page, custOP);
	}
	
	
	public List<CustUserExportVO> findExpCustUser(CustUserExportVO custOP){
		return dao.findList(custOP);
	}
	
	
	/**
	 * 查询营销数据中新客户 老客户用 
	 * @param page
	 * @param custOP
	 * @return
	 */
	public Page<CustUserAllotVO> findMarketingUser(Page<CustUserAllotVO> page,CustUserAllotVO custOP){
		
		custOP.setPage(page);
		page.setList(dao.findMarketingUser(page,custOP));
		return page;
	}
	
	
	public List<CustUserAllotVO>  exportMarketUser(CustUserAllotVO custOP){
		custOP.setPageSize(-1);
		return dao.findMarketingUser(new Page<CustUserAllotVO>(), custOP);
	}
	
	

}
