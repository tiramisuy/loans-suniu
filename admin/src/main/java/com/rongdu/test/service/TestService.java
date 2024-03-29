/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.service.CrudService;
import com.rongdu.test.dao.TestDao;
import com.rongdu.test.entity.Test;

/**
 * 测试Service
 * @author sunda
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
