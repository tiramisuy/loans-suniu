/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;

/**
 * 实体管理基础类
 * 
 * @author sunda
 * @version 2017-07-05
 */
public class BaseManager<D extends BaseDao<T, PK>, T extends BaseEntity<T>, PK extends Serializable> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 数据访问对象
	 */
	@Autowired
	protected D dao;

	/**
	 * 获取数据访问对象
	 * 
	 * @return
	 */
	public D getDao() {
		return this.dao;
	}

	/**
	 * 根据ID获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id) {
		return dao.get(id);
	}

	/**
	 * 根据ID获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public T getById(PK id) {
		return dao.getById(id);
	}

	/**
	 * 根据过滤条件获取单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public T getByCriteria(Criteria criteria) {
		return dao.getByCriteria(criteria);
	}

	/**
	 * 根据过滤条件查询所有符合要求的数据列表
	 * 
	 * @param criteria
	 * @return
	 */
	public List<T> findAllByCriteria(Criteria criteria) {
		return dao.findAllByCriteria(criteria);
	}

	/**
	 * 根据一组过滤条件查询所有符合要求的数据列表
	 * 
	 * @param criteriaList
	 * @return
	 */
	public List<T> findAllByCriteriaList(List<Criteria> criteriaList) {
		return dao.findAllByCriteriaList(criteriaList);
	}

	/**
	 * 查询数据分页列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<T> findPage(Page<T> page) {
		List<T> list = dao.getPage(page);
		page.setList(list);
		return page;
	}

	/**
	 * 根据过滤条件，查询数据分页列表
	 * 
	 * @param page
	 * @param criteria
	 * @return
	 */
	public Page<T> findPageByCriteria(Page<T> page, Criteria criteria) {
		List<T> list = dao.getPageByCriteria(page, criteria);
		page.setList(list);
		return page;
	}

	/**
	 * 根据一组过滤条件，查询数据分页列表
	 * 
	 * @param page
	 * @param criteriaList
	 * @return
	 */
	public Page<T> findPageByCriteriaList(Page<T> page, List<Criteria> criteriaList) {
		List<T> list = dao.getPageByCriteriaList(page, criteriaList);
		page.setList(list);
		return page;
	}

	/**
	 * 查询数据分页列表(弃用)
	 * 
	 * @param page
	 * @return
	 */
	@Deprecated
	public List<T> getPage(Page<T> page) {
		return dao.getPage(page);
	}

	/**
	 * 根据过滤条件，查询数据分页列表(弃用)
	 * 
	 * @param page
	 * @param criteria
	 * @return
	 */
	@Deprecated
	public List<T> getPageByCriteria(Page<T> page, Criteria criteria) {
		return dao.getPageByCriteria(page, criteria);
	}

	/**
	 * 根据一组过滤条件，查询数据分页列表(弃用)
	 * 
	 * @param page
	 * @param criteriaList
	 * @return
	 */
	@Deprecated
	public List<T> getPageByCriteriaList(Page<T> page, List<Criteria> criteriaList) {
		return dao.getPageByCriteriaList(page, criteriaList);
	}

	/**
	 * 统计符合过滤条件的记录数量
	 * 
	 * @param criteria
	 * @return
	 */
	public long countByCriteria(Criteria criteria) {
		return dao.countByCriteria(criteria);
	}

	/**
	 * 统计符合一组过滤条件的记录数量
	 * 
	 * @param filters
	 * @return
	 */
	public long countByCriteriaList(List<Criteria> criteriaList) {
		return dao.countByCriteriaList(criteriaList);
	}

	/**
	 * 插入单条数据
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(T entity) {
		entity.preInsert();
		return dao.insert(entity);
	}

	/**
	 * 批量插入数据
	 * 
	 * @param list
	 * @return
	 */
	public int insertBatch(List<T> list) {
		for (T entity : list) {
			entity.preInsert();
		}
		return dao.insertBatch(list);
	}

	/**
	 * 根据ID更新数据，需要先查询数据，再更新数据
	 * 
	 * @param entity
	 * @return
	 */
	public int update(T entity) {
		entity.preUpdate();
		return dao.update(entity);
	}

	/**
	 * 根据ID更新数据，对不为空的字段进行更新
	 * 
	 * @param id
	 * @return
	 */
	public int updateByIdSelective(T entity) {
		entity.preUpdate();
		return dao.updateByIdSelective(entity);
	}

	/**
	 * 根据过滤条件来更新数据
	 * 
	 * @param entity
	 * @return
	 */
	public int updateByCriteriaSelective(T entity, Criteria criteria) {
		entity.preUpdate();
		return dao.updateByCriteriaSelective(entity, criteria);
	}

	/**
	 * 根据一组过滤条件来更新数据
	 * 
	 * @param entity
	 * @return
	 */
	public int updateByCriteriaListSelective(T entity, List<Criteria> criteriaList) {
		entity.preUpdate();
		return dao.updateByCriteriaListSelective(entity, criteriaList);
	}

	/**
	 * 删除数据（逻辑删除，将del字段的值设为1）
	 * 
	 * @param id
	 * @return
	 */
	public int delete(T entity) {
		entity.preUpdate();
		return dao.delete(entity);
	}

	/**
	 * 根据过滤条件来删除数据（逻辑删除，将del字段的值设为1）
	 * 
	 * @param criteria
	 * @return
	 */
	public int deleteByCriteria(T entity, Criteria criteria) {
		entity.preUpdate();
		return dao.deleteByCriteria(entity, criteria);
	}

	/**
	 * 根据一组过滤条件来删除数据（逻辑删除，将del字段的值设为1）
	 * 
	 * @param criteriaList
	 * @return
	 */
	public int deleteByCriteriaList(T entity, List<Criteria> criteriaList) {
		entity.preUpdate();
		return dao.deleteByCriteriaList(entity, criteriaList);
	}

	/**
	 * 删除数据（物理删除）
	 * 
	 * @param id
	 * @return
	 */
	public int deleteTruely(PK id) {
		return dao.deleteTruely(id);
	}

	/**
	 * 根据过滤条件来删除数据（物理删除）
	 * 
	 * @param criteria
	 * @return
	 */
	public int deleteTruelyByCriteria(Criteria criteria) {
		return dao.deleteTruelyByCriteria(criteria);
	}

	/**
	 * 根据一组过滤条件来删除数据（物理删除）
	 * 
	 * @param criteriaList
	 * @return
	 */
	public int deleteTruelyByCriteriaList(List<Criteria> criteriaList) {
		return dao.deleteTruelyByCriteriaList(criteriaList);
	}

	/**
	 * 批量删除数据（逻辑删除，将del字段的值设为1）
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatch(T entity, List<PK> ids) {
		entity.preUpdate();
		return dao.deleteBatch(entity, ids);
	}

	/**
	 * 批量删除数据（物理删除）
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatchTruely(List<PK> ids) {
		return dao.deleteBatchTruely(ids);
	}
}