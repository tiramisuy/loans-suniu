/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.gen.dao;

import java.util.List;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.GenMyBatisDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.gen.entity.GenTable;
import com.rongdu.loans.gen.entity.GenTableColumn;
import org.springframework.stereotype.Repository;

/**
 * 业务表字段DAO接口
 * @author sunda
 * @version 2013-10-15
 */
@GenMyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
