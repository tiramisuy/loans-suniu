package com.rongdu.loans.cust.manager;

import com.rongdu.loans.cust.dao.CustUserInfoDAO;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.option.TFLBaseInfoOP;
import com.rongdu.loans.cust.vo.CustUserInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("custUserInfoManager")
public class CustUserInfoManager{

    @Autowired
    private CustUserInfoDAO custUserInfoDAO;

    /**
     * 更新基础信息
     * @param entity
     * @return
     */
    public int updateBaseinfo(CustUserInfo entity) {
        return custUserInfoDAO.updateBaseinfo(entity);
    }

    /**
     * 根据ID进行查询
     * @param id
     * @return
     */
    public CustUserInfo getById(String id) {
        return custUserInfoDAO.getById(id);
    }

    /**
     * 插入基础信息
     * @param entity
     * @return
     */
    public int insertBaseinfo(CustUserInfo entity) {
        return custUserInfoDAO.insert(entity);
    }

    /**
     * 根据证件号统计记录条数
     * @param idNo
     * @return
     */
    public int countById(String idNo) {
        return custUserInfoDAO.countById(idNo);
    }

    /**
     * 判断某个证件号是否存在
     * @param idNo
     * @return
     */
    public boolean isExistCust(String idNo) {
        return countById(idNo) > 0;
    }

    /**
     * 根据用户id更新基础信息
     * @param entity
     * @return
     */
    public int updateById(CustUserInfo entity) {
        return custUserInfoDAO.updateById(entity);
    }

    /**
     * 插入或者更新数据
     * @param entity
     * @return
     */
    public int saveOrUpdateBaseinfo(CustUserInfo entity) {
        int rz = 0;
        if(null != entity
                && null != entity.getId()
                && null != entity.getIdNo()) {
            if(isExistCust(entity.getId())) {
                entity.preUpdate();
                rz = updateById(entity);
            } else {
                entity.setIsNewRecord(true);
                entity.preInsert();
                rz = insertBaseinfo(entity);
            }
        }
        return rz;
    }

    /**
     * 获取基本信息
     * @param id
     * @return
     */
    public CustUserInfoVO getSimpleById(String id) {
        return custUserInfoDAO.getSimpleById(id);
    }

    /*
	 * 获取投复利用户信息
	 */
	
	public TFLBaseInfoOP getTFLUserInfo(String userid){
		return custUserInfoDAO.getTFLUserInfo(userid);
	};
}
