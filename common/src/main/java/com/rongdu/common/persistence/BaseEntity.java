/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.StringUtils;

/**
 * Entity支持类
 * @author sunda
 * @version 2014-05-16
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 删除标记（0：正常；1：已经删除）
	 */
	public static final int DEL_NORMAL = 0;
	public static final int DEL_DELETE = 1;
	public static final String DEFAULT_USER = "system";

	/**
	 * ID，实体唯一标识
	 */
	protected String id;
	
	/**
	 * 	备注
	 */
	protected String remark;
	
	/**
	 * 	创建者userId
	 */
	protected String createBy;
	/**
	 * 	创建日期
	 */
	protected Date createTime;
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	protected Date updateTime;	

	/**
	 *  删除标识（0：正常；1：删除）
	 */
	protected int del = BaseEntity.DEL_NORMAL;

	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;
	
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewRecord = false;

	public BaseEntity() {
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonIgnore
	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		if (StringUtils.isBlank(this.createBy)) {
			createBySystem();
			updateBySystem();
		}
		this.createTime = new Date();
		this.updateTime = this.createTime;
	}
	
	
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		if (StringUtils.isBlank(this.updateBy)) {
			updateBySystem();
		}
		this.updateTime = new Date();
	}
	
    /**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * @return
     */
	@JsonIgnore
	public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getId());
    }

	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	
	/**
	 * 由system创建
	 */
	public void createBySystem() {
		this.createBy = BaseEntity.DEFAULT_USER;
	}
	
	/**
	 * 由system修改
	 */
	public void updateBySystem() {
		this.updateBy = BaseEntity.DEFAULT_USER;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

	@JsonIgnore
	@XmlTransient
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}

	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}


	/**
	 * 全局变量对象
	 */
	@JsonIgnore
	public Global getGlobal() {
		return Global.getInstance();
	}

	/**
	 * 获取数据库名称
	 */
	@JsonIgnore
	public String getDbName(){
		return Global.getConfig("jdbc.type");
	}
}
