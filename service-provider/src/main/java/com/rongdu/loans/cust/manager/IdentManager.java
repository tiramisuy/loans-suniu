package com.rongdu.loans.cust.manager;


import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.dao.IdentDAO;
import com.rongdu.loans.cust.entity.Ident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("identManager")
public class IdentManager extends BaseManager<IdentDAO, Ident, String>{

    @Autowired
    private IdentDAO identDAO;

    /**
     * 保存证件信息
     * @param entity
     * @return
     */
    public int saveIdent(Ident entity) {
        return identDAO.insert(entity);
    }

    /**
     * 根据用户id统计记录条数
     * @param userId
     * @return
     */
    public int countByUserId(String userId) {
        return identDAO.countByUserId(userId);
    }

    /**
     * 判断用户id是否存在记录
     * @param userId
     * @return
     */
    public boolean isExistUserId(String userId) {
        return countByUserId(userId) > 0;
    }

    /**
     * 插入或者更新证件信息
     * @param entity
     * @return
     */
    public int saveOrUpdateIdent(Ident entity) {
        int rz = 0;
        if(null != entity
                && null != entity.getUserId()) {
            if(isExistUserId(entity.getUserId())) {
                entity.preUpdate();
                rz = identDAO.updateByUserId(entity);
            } else {
                entity.preInsert();
                rz = saveIdent(entity);
            }
        }
        return rz;
    }

}
