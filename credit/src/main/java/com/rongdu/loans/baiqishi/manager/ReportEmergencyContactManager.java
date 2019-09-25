/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.baiqishi.dao.ReportEmergencyContactDao;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import org.springframework.stereotype.Service;

/**
 * 白骑士-紧急联系人信息-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("reportEmergencyContactManager")
public class ReportEmergencyContactManager extends BaseManager<ReportEmergencyContactDao, ReportEmergencyContact, String>{
	
}