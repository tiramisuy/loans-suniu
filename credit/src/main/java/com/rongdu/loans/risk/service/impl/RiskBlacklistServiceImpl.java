/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.manager.BlacklistManager;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import com.rongdu.loans.risk.vo.BlacklistVO;

/**
 * 风控黑名单-业务逻辑实现类
 * 
 * @author sunda
 * @version 2017-08-14
 */
@Service("riskBlacklistService")
public class RiskBlacklistServiceImpl extends BaseService implements RiskBlacklistService {

	/**
	 * 风控黑名单-实体管理接口
	 */
	@Autowired
	private BlacklistManager blacklistManager;

	@Autowired
	private CustUserService custUserService;

	public long countInBlacklist(String userId) {
		return blacklistManager.countInBlacklist(userId); // 判断该用户是否已经被拉黑
	}

	public int insertBlacklist(String userId, String remark, String applyId, Integer status,String updateUser) {
		CustUserVO vo = custUserService.getCustUserById(userId);

		Blacklist blacklist = new Blacklist();
		blacklist.setId(IdGen.uuid());
		blacklist.setUserId(userId);
		blacklist.setIdNo(vo.getIdNo());
		blacklist.setMobile(vo.getMobile());
		blacklist.setTelphone(vo.getMobile());
		blacklist.setQq(vo.getQq());
		blacklist.setName(vo.getRealName());
		blacklist.setReason(remark); // 进出黑名单原因
		blacklist.setSourceType("1");// 来源类型：0-网贷行业公布的黑名单;1-在平台有欠息或有不良贷款;//
										// 2-提供虚假材料或拒绝接受检查;3-命中合作机构的黑名单；4-网络黑名单
		blacklist.setSourceOrg("juqianbao");
		blacklist.setTime(new java.sql.Date(new java.util.Date().getTime()));
		blacklist.setStatus(status);// 黑名单状态：0-预登记;1-生效;2-否决;3-注销
		blacklist.setCreateBy(updateUser);
		blacklist.setUpdateBy(updateUser);

		blacklist.setDel(0);// 0-正常，1-已经删除

		blacklist.setExtendInfo(applyId); // 扩展信息暂存拉黑可会的applyId

		return blacklistManager.insert(blacklist);
	}

	public Page<BlacklistVO> selectBlackList(BlacklistOP op) {
		Page<BlacklistVO> voPage = new Page<BlacklistVO>(op.getPageNo(), op.getPageSize());
		List<BlacklistVO> voList = (List<BlacklistVO>) blacklistManager.selectBlackList(voPage, op);
		voPage.setList(voList);
		return voPage;

	}

	public long updateBlackById(String id, int status,String updateBy) {
		return blacklistManager.updateBlackById(id, status,updateBy);
	}

	public BlacklistVO getById(String id) {
		Blacklist entity = blacklistManager.get(id);
		return BeanMapper.map(entity, BlacklistVO.class);
	}

	@Override
	public boolean inBlackList(String userName, String userPhone, String userIdCard) {
		int count = blacklistManager.inBlackList(userName, userPhone, userIdCard);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	 public int deleteBlickList(String blackId){
		 return blacklistManager.deleteTruely(blackId);
	 }
}