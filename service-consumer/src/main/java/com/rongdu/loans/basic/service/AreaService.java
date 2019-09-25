/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import com.rongdu.loans.basic.vo.AreaVO;

import java.util.List;
import java.util.Map;

/**
 * 地区-业务逻辑接口
 * @author sunda
 * @version 2017-08-23
 */
public interface AreaService {

    /**
     * 获得所有的地区List
     * @return
     */
    public List<AreaVO> getAllAreaList();

    /**
     * 获得所有的地区Map
     * @return
     */
    public Map<String,AreaVO> getAllAreaMap();

}