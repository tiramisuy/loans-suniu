/******************************************************************************
 * 
 * 聚宝钱包-任务调度平台
 *
 * Copyright © Hanxin Internet Finance Service(Wuhan)co.,Itd. All Rights Reserved.
 * 
 * Created on 2012-11-10 22:34:58
 *
 *******************************************************************************/
package com.rongdu.loans.service.account;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.dao.account.AuthorityDao;
import com.rongdu.loans.dao.account.VAuthorityDao;
import com.rongdu.loans.entity.account.Authority;
import com.rongdu.loans.entity.account.VAuthority;
import com.rongdu.loans.service.EntityManager;
import com.rongdu.loans.service.log.Log;
import com.rongdu.loans.utils.StringUtil;
/**
 * 系统资源业务逻辑处理类，Spring Bean采用注解方式来定义，默认将类中的所有函数纳入事务管理.
 *
 * @version 1.0
 *
 * @author sunda
 */
@Service
@Transactional
public class AuthorityManager extends EntityManager<Authority, Long>{
	
	@Autowired
	private AuthorityDao authorityDao;	
	@Autowired
	private VAuthorityDao vAuthorityDao;
	@Autowired
	private CacheManager ehcacheManager;

	@Override
	protected HibernateDao<Authority, Long> getEntityDao() {
		return authorityDao;
	}
	
	public int batchDelete(Long[] ids) {
		return authorityDao.batchDelete(ids);
	}
	
	public boolean isUnique(String propertyName, String strValue) {
		Object value = StringUtil.convertType(strValue,Reflections.getFieldType(Authority.class, propertyName));
		Long count = authorityDao.countByProperty(propertyName,value);
		return count==0L;
	}
	
	public List<Authority> loadAuthorityTree() {
		return authorityDao.getAllMenu();
	}
	
	public VAuthority getVAuthority(Long id) {
		return vAuthorityDao.get(id);
	}

	public VAuthority getSimpleVAuthority(Long id) {
		return vAuthorityDao.getSimpleVAuthority(id);
	}
	
	public List<Authority> loadOperation(Long id) {
		return authorityDao.loadOperation(id);
	}
	
	public Map<String , Authority> getAllAuthorityMap() {
		Map<String , Authority> allAuthorityMap = new HashMap<String, Authority>();
		List<Authority> menus = authorityDao.getAllMenu();
		//不能对opts属性进行修改，会自动同步到数据库
		List<Authority> opts = authorityDao.getAllOpt();
		for (Authority auth:menus) {
			allAuthorityMap.put(auth.getUrl(), auth);
		}
		//对瞬时对象进行修改
		List<Authority> optsTransient = new ArrayList<Authority>();
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.map(opts, optsTransient);
		for (Authority auth:optsTransient) {
			Authority parent = getParentAuthority(menus,auth.getPid());
			if (null != parent) {
				String optName = parent.getName()+"-"+auth.getName();
				auth.setName(optName);
			}
			allAuthorityMap.put(auth.getUrl(), auth);
		}
		return allAuthorityMap;
	}
	
	@SuppressWarnings("unchecked")
	public Authority getAuthorityByUrl(String url) {
		Map<String , Authority> allAuthorityMap = null;
		Cache cache = ehcacheManager.getCache("AllAuthorityMap");
		Element element = cache.get("AuthorityMap");
		if (element == null) {		
			allAuthorityMap = getAllAuthorityMap();
			if (allAuthorityMap!=null) {
				cache.put(new Element("AuthorityMap", allAuthorityMap));				
			}			
		} else {
			allAuthorityMap = (Map<String , Authority>) element.getObjectValue();
		}
		if (allAuthorityMap!=null) {
			return allAuthorityMap.get(url);
		}
		return null;
	}
	
	private Authority getParentAuthority(List<Authority> menus, Long pid) {
		for (Authority auth:menus) {
			if (pid.intValue()==auth.getId().intValue()) {
				return auth; 
			}
		}
		return null;
	}

	@Override
	@Log(opt="删除资源权限")
	public void delete(Authority entity) {
		authorityDao.deleteOperation(entity.getId());
		authorityDao.delete(entity);
	}
	
	public Multimap<String, Authority> getAllMenu() {
		List<Authority> list = authorityDao.getAllMenu();
		Multimap<String, Authority> map = LinkedHashMultimap.create();
		for (Authority a:list) {
			map.put(String.valueOf(a.getPid()), a);
		}
		return map;
	}
	
	public Multimap<String, Authority> getAllOpt() {
		List<Authority> list = authorityDao.getAllOpt();
		Multimap<String, Authority> map = LinkedHashMultimap.create();
		for (Authority a:list) {
			map.put(String.valueOf(a.getPid()), a);
		}
		return map;
	}
	
}
