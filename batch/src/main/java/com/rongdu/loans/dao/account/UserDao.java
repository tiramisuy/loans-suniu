package com.rongdu.loans.dao.account;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.rongdu.core.orm.hibernate.HibernateDao;
import com.rongdu.loans.entity.account.User;


/**
 * 用户对象的泛型DAO类.
 */
@Component
public class UserDao extends HibernateDao<User, Long> {

	public int batchUpdateUserStatus(String status,Long... ids) {
		Object[] params = new Object[0];
		params = ArrayUtils.add(params, status);
		params = ArrayUtils.addAll(params, ids);
		String lp = toLocationParameters(ids);
		StringBuilder hql =  new StringBuilder();
		hql.append("update User user set user.status=?  where user.id in ");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString(),params);
	}

	public int batchDelete(Long[] ids) {
		String lp = StringUtils.join(ids, ',');
		StringBuilder hql =  new StringBuilder();
		hql.append("delete from User user where user.id in ");
		hql.append("(").append(lp).append(")");
		return batchExecute(hql.toString());
	}
	/**
	 * 将数组转化为位置参数,如：?,?,?,?
	 * @param ids
	 * @return
	 */
	private String toLocationParameters(Long[] ids) {
		StringBuilder builder = new StringBuilder();
		int length = ids.length;
		for (int i = 0; i < length; i++) {
			builder.append("?");
			if (i!=length-1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	public long[]  findUserIdByRole(Long rolrId) {
		String sql = "select t.user_id from sys_user_role t where  t.role_id=?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setLong(0, rolrId);
		List<BigInteger> ids = sqlQuery.list();
		int size =  ids.size();
		long[] userIds = new long[size];
		for (int i = 0; i < size; i++) {
			userIds[i] = ids.get(i).longValue();
		}
		return userIds;
	}

	public Long countByProperty(String propertyName, Object value) {
		String hql = "from User where "+propertyName+"=?";
		return countHqlResult(hql, value);
	}


}
