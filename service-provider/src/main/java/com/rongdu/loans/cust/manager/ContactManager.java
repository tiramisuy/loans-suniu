package com.rongdu.loans.cust.manager;


import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.dao.ContactDAO;
import com.rongdu.loans.cust.entity.Contact;
import com.rongdu.loans.cust.vo.CustContactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("contactManager")
public class ContactManager extends BaseManager<ContactDAO, Contact, String>{

	@Autowired
	private ContactDAO contactDAO;

	/**
	 * 单条插入联系人
	 * @param contact
	 * @return
	 */
	public int insert(Contact contact) {
		return contactDAO.insert(contact);
	}

	/**
	 * 根据用户id清理联系人
	 * @return
	 */
	public int delContact(String userId) {
		return contactDAO.delContact(userId);
	}

	/**
	 * 获取当前紧急联系人表信息
	 * @param userId
	 * @return
	 */
	public List<CustContactVO> getByUserId(String userId) {
		return contactDAO.getByUserId(userId);
	}

	/**
	 * 统计 手机号近长时间 出现在他人紧急联系人中的次数
	 * @param map
	 * @return
	 */
	public int countNum(Map map) {
		return contactDAO.countNum(map);
	}
}
