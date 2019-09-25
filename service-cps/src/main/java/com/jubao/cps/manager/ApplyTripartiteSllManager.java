/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.jubao.cps.manager;

import com.jubao.cps.dao.ApplyTripartiteSllDao;
import com.jubao.cps.entity.ApplyTripartiteSll;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;

import java.util.List;


/**
 * 工单映射（sll）-实体管理实现类
 * @author Lee
 * @version 2018-12-10
 */
@Service("applyTripartiteSllManager")
public class ApplyTripartiteSllManager extends BaseManager<ApplyTripartiteSllDao, ApplyTripartiteSll, String> {

    public List<String> findThirdIdsByApplyIds(List<String> applyIds){
        return dao.findThirdIdsByApplyIds(applyIds);
    }
}