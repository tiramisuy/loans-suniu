/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.persistence;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.criteria.Criteria;

/**
 * DAO支持类实现
 * @author sunda
 * @version 2017-07-4
 */
public interface BaseDao<T, PK extends Serializable>{
	
	/**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	public T getById(PK id);
	
	/**
	 * 根据过滤条件获取单条数据
	 * @param criteria
	 * @return
	 */
	public T getByCriteria(@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据过滤条件查询所有符合要求的数据列表
	 * @param criteria
	 * @return
	 */
	public List<T> findAllByCriteria(@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件查询所有符合要求的数据列表
	 * @param criteriaList
	 * @return
	 */
	public List<T> findAllByCriteriaList(@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 查询数据分页列表
	 * @param page
	 * @return
	 */
	public Page<T> findPage(@Param(value="page")Page<T> page);
	
	/**
	 * 根据过滤条件，查询数据分页列表
	 * @param page
	 * @param criteria
	 * @return
	 */
	public Page<T> findPageByCriteria(@Param(value="page")Page<T> page,@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件，查询数据分页列表
	 * @param page
	 * @param criteriaList
	 * @return
	 */
	public Page<T> findPageByCriteriaList(@Param(value="page")Page<T> page,@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 查询数据分页列表
	 * @param page
	 * @return
	 */
	List<T> getPage(@Param(value="page")Page<T> page);
	
	/**
	 * 根据过滤条件，查询数据分页列表
	 * @param page
	 * @param criteria
	 * @return
	 */
	List<T> getPageByCriteria(@Param(value="page")Page<T> page,@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件，查询数据分页列表
	 * @param page
	 * @param criteriaList
	 * @return
	 */
	List<T> getPageByCriteriaList(@Param(value="page")Page<T> page,@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 统计符合过滤条件的记录数量
	 * @param criteria
	 * @return
	 */
	public long countByCriteria(@Param(value="criteria")Criteria criteria);
	
	/**
	 * 统计符合一组过滤条件的记录数量
	 * @param criteriaList
	 * @return
	 */
	public long countByCriteriaList(@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 插入单条数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 */
	public int insertBatch(@Param(value="list")List<T> list);
	
	/**
	 * 根据ID更新数据，需要先查询数据，再更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 根据ID更新数据，对不为空的字段进行更新
	 * @param entity
	 * @return
	 */
	public int updateByIdSelective(@Param(value="entity")T entity);
	
	/**
	 * 根据过滤条件来更新数据
	 * @param entity
	 * @return
	 */
	public int updateByCriteriaSelective(@Param(value="entity")T entity,@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件来更新数据
	 * @param entity
	 * @return
	 */
	public int updateByCriteriaListSelective(@Param(value="entity")T entity,@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 删除数据（逻辑删除，将del字段的值设为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);
	
	/**
	 * 根据过滤条件来删除数据（逻辑删除，将del字段的值设为1）
	 * @param criteria
	 * @return
	 */
	public int deleteByCriteria(@Param(value="entity")T entity,@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件来删除数据（逻辑删除，将del字段的值设为1）
	 * @param criteriaList
	 * @return
	 */
	public int deleteByCriteriaList(@Param(value="entity")T entity,@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 批量删除数据（逻辑删除，将del字段的值设为1）
	 * @param ids
	 * @return
	 */
	public int deleteBatch(@Param(value="entity")T entity,@Param(value="ids")List<PK> ids);
	
	/**
	 * 删除数据（物理删除）
	 * @param id
	 * @return
	 */
	public int deleteTruely(PK id);
	
	/**
	 * 根据过滤条件来删除数据（物理删除）
	 * @param criteria
	 * @return
	 */
	public int deleteTruelyByCriteria(@Param(value="criteria")Criteria criteria);
	
	/**
	 * 根据一组过滤条件来删除数据（物理删除）
	 * @param criteriaList
	 * @return
	 */
	public int deleteTruelyByCriteriaList(@Param(value="criteriaList")List<Criteria> criteriaList);
	
	/**
	 * 批量删除数据（物理删除）
	 * @param ids
	 * @return
	 */
	public int deleteBatchTruely(@Param(value="ids")List<PK> ids);
	
}