/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.baiqishi.manager.ReportMnoMuiManager;
import com.rongdu.loans.baiqishi.service.ReportMnoMuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 白骑士-运营商月使用信息-mnoMonthUsedInfos-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportMnoMuiService")
public class ReportMnoMuiServiceImpl  extends BaseService implements  ReportMnoMuiService{
	
	/**
 	* 白骑士-运营商月使用信息-mnoMonthUsedInfos-实体管理接口
 	*/
	@Autowired
	private ReportMnoMuiManager reportMnoMuiManager;
	
}