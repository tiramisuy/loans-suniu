/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.BaseEntity;

/**
 * Service基类
 * @author sunda
 * @version 2017-05-16
 */
public class CrudService<D extends BaseDao<T,PK>,T extends BaseEntity<T>, PK extends Serializable>  extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	


}
