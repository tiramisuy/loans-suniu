/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.persistence.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
/**
 * SQL WHERE过滤条件(复合)
 * 
 * 说明：List<Criteria>数组里的过滤条件是AND关系
 * 
        示例代码：
 		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		生成的查询条件为：
		WHERE ( status = 0 OR status = 1 ) AND ( del = 0 ) AND ( file_ext != 'jpg' )
		
 * @author sunda
 * @version 2017-06-30
 */
public class Criteria implements Serializable {

	private static final long serialVersionUID = 8294552713578321326L;
	private List<CriterionPair> pairList = new ArrayList<CriterionPair>();
	
	public Criteria(){
	}
	
	/**
	 * 添加过滤条件，第一个条件
	 *  e.g：add(criterion.eq("status",1))，生成SQL：status=1
	 * @param criterion
	 */
	public void add(Criterion criterion) {
		Assert.isTrue(!isValid(), "请使用and，or或not逻辑运算符");
		pairList.add(new CriterionPair(LogicType.NONE,criterion));
	}
	
	/**
	 * 逻辑或
	 * e.g：or(criterion.eq("status",1))，生成SQL：OR status=1
	 * @param criterion
	 */
	public void or(Criterion criterion) {
		pairList.add(new CriterionPair(LogicType.OR,criterion));
	}
	
	/**
	 * 逻辑与
	 * e.g：and(criterion.eq("status",1))，生成SQL：AND status=1
	 * @param criterion
	 */
	public void and(Criterion criterion) {
		pairList.add(new CriterionPair(LogicType.AND,criterion));
	}
	
	/**
	 * 逻辑否
	 * e.g：not(criterion.eq("status",1))，生成SQL：NOT status=1
	 * @param criterion
	 */
	public void not(Criterion criterion) {
		pairList.add(new CriterionPair(LogicType.NOT,criterion));
	}
	
    public boolean isValid() {
        return pairList.size() > 0;
    }

	public List<CriterionPair> getPairList() {
		return pairList;
	}

	public void setPairList(List<CriterionPair> pairList) {
		this.pairList = pairList;
	}
	
}
