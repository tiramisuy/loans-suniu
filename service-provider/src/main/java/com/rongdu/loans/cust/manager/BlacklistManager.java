package com.rongdu.loans.cust.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.cust.dao.BlacklistDAO;
import com.rongdu.loans.cust.entity.Blacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("blacklistManager")
public class BlacklistManager extends BaseManager<BlacklistDAO, Blacklist, String>{

    @Autowired
    private BlacklistDAO blacklistDAO;

    public Blacklist getById(String id) {
        return blacklistDAO.getById(id);
    }

    public List<Blacklist> getBlacklistByUserIdList(List<String> userIdList) {
        return blacklistDAO.getBlacklistByUserIdList(userIdList);
    }
}