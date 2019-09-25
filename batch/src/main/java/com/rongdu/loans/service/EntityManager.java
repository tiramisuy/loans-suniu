package com.rongdu.loans.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.core.orm.Page;
import com.rongdu.core.orm.PropertyFilter;
import com.rongdu.core.orm.PropertyFilter.MatchType;
import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.service.log.Log;


/**
 * Service层领域对象业务管理类基类.
 * 使用HibernateDao<T,PK>进行业务对象的CRUD操作,子类需重载getEntityDao()函数提供该DAO.
 * 
 * @param <T>
 *            领域对象类型
 * @param <PK>
 *            领域对象的主键类型
 * 
 *            eg. public class UserManager extends EntityManager<User, Long>{ }
 * 
 * @author
 */
@Transactional
public abstract class EntityManager<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 在子类实现此函数,为下面的CRUD操作提供DAO.
	 */
	protected abstract HibernateDao<T, PK> getEntityDao();

	// CRUD函数 //
	/**
	 * 保存新增或修改的对象.主键自增或为序列情况下适用
	 */
	public void saveOrUpdate(final T entity) {
		getEntityDao().saveOrUpdate(entity);
	}
	
	/**
	 * 保存新增的对象.
	 */
	@Log(opt="新增")
	public void save(final T entity) {
		getEntityDao().save(entity);
	}
	
	/**
	 * 保存修改的对象.
	 */
	@Log(opt="修改")
	public void update(final T entity) {
		getEntityDao().update(entity);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	@Log(opt="删除")
	public void delete(final T entity) {
		getEntityDao().delete(entity);
	}

	/**
	 * 按id删除对象.
	 */
	@Log(opt="删除")
	public void delete(final PK id) {
		getEntityDao().delete(id);
	}

	/**
	 * 按id获取对象.
	 */
	public T load(final PK id) {
		return getEntityDao().load(id);
	}
	
	/**
	 * 按id获取对象.
	 */
	public T get(final PK id) {
		return getEntityDao().get(id);
	}
	
	/**
	 * 按id列表获取对象列表.
	 */
	public List<T> get(final Collection<PK> ids) {
		return getEntityDao().get(ids);
	}

	/**
	 *	获取全部对象.
	 */
	public List<T> getAll() {
		return getEntityDao().getAll();
	}

	/**
	 *	获取全部对象, 支持按属性行序.
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		return getEntityDao().getAll(orderByProperty,isAsc);
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		return getEntityDao().findBy(propertyName,value);
	}

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		return getEntityDao().findUniqueBy(propertyName,value);
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		return getEntityDao().isPropertyUnique(propertyName,newValue,oldValue);
	}
	
	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return getEntityDao().getAll(page);
	}

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		return getEntityDao().findBy(propertyName,value,matchType);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		return getEntityDao().find(filters);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		return getEntityDao().findPage(page, filters);
	}
	/**
	 * 按hql语句分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		return getEntityDao().findPage(page,hql ,values);
	}
	
	public <T> List<T> findByHql(final String hql,Class<T> cls,final Object...values){
		return (List<T>)getEntityDao().find(hql, values);
	}
	
	public <T> T findByFirst(final String hql,Class<T> cls,final Object...values){
		List<T> list=(List<T>)getEntityDao().find(hql, values);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
