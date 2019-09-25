
package com.rongdu.core.orm;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Lists;


public class Page<T> {
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	
	protected int pageNo = 1;
	protected int pageSize = -1;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	
	protected List<T> result = Lists.newArrayList();
	protected long totalCount = -1;

	
	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	

	
	
	public int getPageNo() {
		return pageNo;
	}

	
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	
	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	
	public int getPageSize() {
		return pageSize;
	}

	
	public void setPageSize(final int pageSize) {
		if (pageSize>30) {
			this.pageSize = 30;
		}else {
			this.pageSize = pageSize;			
		}
	}

	
	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	@JsonIgnore
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	@JsonIgnore
	public String getOrder() {
		return order;
	}

	
	public void setOrder(final String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		
		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = lowcaseOrder;
	}

	
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	@JsonIgnore
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	@JsonIgnore
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return totalCount;
	}

	
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
}
