/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.persistence.dialect;

/**
 * 类似hibernate的Dialect,但只精简出分页部分
 *
 * @author poplar.yfyang
 * @version 1.0 2011-11-18 下午12:31
 * @since JDK 1.5
 */
public abstract class Dialect {

    /**
     * 数据库本身是否支持分页当前的分页查询方式
     * 如果数据库不支持的话，则不进行数据库分页
     *
     * @return true：支持当前的分页查询方式
     */
    public abstract boolean supportsLimit();

    /**
     * 将sql转换为分页SQL，分别调用分页sql
     *
     * @param sql    SQL语句
     * @param offset 开始条数
     * @param limit  每页显示多少纪录条数
     * @return 分页查询的sql
     */
    public abstract String getLimitString(String sql, int offset, int limit);

    /**
     * 数据库本身是否支持排序当前的排序查询方式
     * 如果数据库不支持的话，则不进行数据库排序
     * 默认不支持，需要支持的时候由具体的数据库子类覆盖该方法
     *
     * @return
     */
    public boolean supportsOrderBy(){
        return false;
    }

    /**
     * 将sql转换为排序SQL
     * 支持排序的方言必须覆盖整个方法，否则默认不排序
     *
     * @param sql
     * @param orderColumns 排序的列，多个由逗号隔开
     * @return
     */
    public String getOrderString(String sql, String orderColumns){
        return sql;
    }

}
