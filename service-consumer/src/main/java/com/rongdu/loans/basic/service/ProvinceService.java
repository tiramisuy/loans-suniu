/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service;

import java.util.List;

import com.rongdu.loans.basic.vo.ProvinceVO;

/**
 * 省-业务逻辑接口
 * @author sunda
 * @version 2017-08-23
 */
public interface ProvinceService {

    /**
     * 获得所有的省List
     * @return
     */
    public List<ProvinceVO> getAllList();


}