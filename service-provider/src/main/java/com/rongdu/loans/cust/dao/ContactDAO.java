package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.Contact;
import com.rongdu.loans.cust.vo.CustContactVO;

import java.util.List;
import java.util.Map;

/**
 *  联系人DAO接口
 * @author likang
 * @version 2017-06-27
 */
@MyBatisDao
public interface ContactDAO extends BaseDao<Contact, String> {

//    /**
//     * 批量保存联系人
//     * @param list
//     * @return
//     */
//    int batchSave(List<Contact> list);

	/**
	 * 删除联系人
	 * @param userId
	 * @return
	 */
	int delContact(String userId);
	
    /**
	 * 获取当前紧急联系人表信息
	 * @param userId
	 * @return
	 */
	List<CustContactVO> getByUserId(String userId);

	/**
	 * 统计 手机号近长时间 出现在他人紧急联系人中的次数
	 * @param map
	 * @return
	 */
	int countNum(Map map);
}
