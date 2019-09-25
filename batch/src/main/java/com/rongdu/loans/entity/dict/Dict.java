package com.rongdu.loans.entity.dict;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "SYS_DICT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dict{
	/**   
	  *  字典代码
     */
	private String code;
	/**   
	  *  字典名称
     */
	private String name;
	/**   
	  *  备注
     */
	private String remark;
	/**   
	  *
     */
	private List<DictItem> dictItem = new ArrayList<DictItem>();

   public Dict() {
   }
	
   public Dict(String code) {
       this.code = code;
   }
   public Dict(String code, String name, String remark, List<DictItem> ssDictItems) {
      this.code = code;
      this.name = name;
      this.remark = remark;
//      this.ssDictItems = ssDictItems;
   }
  
  /**     
    *访问<字典代码>属性
    */
   @Id 
   @Column(name="CODE", unique=true, nullable=false, length=20)
   public String getCode() {
       return this.code;
   }	    
   /**     
    *设置<字典代码>属性
    */
   public void setCode(String code) {
       this.code = code;
   }
  /**     
    *访问<字典名称>属性
    */
   @Column(name="NAME", length=40)
   public String getName() {
       return this.name;
   }	    
   /**     
    *设置<字典名称>属性
    */
   public void setName(String name) {
       this.name = name;
   }
  /**     
    *访问<备注>属性
    */
   @Column(name="REMARK", length=100)
   public String getRemark() {
       return this.remark;
   }	    
   /**     
    *设置<备注>属性
    */
   public void setRemark(String remark) {
       this.remark = remark;
   }
  /**     
    *
    */
   @OneToMany(fetch=FetchType.LAZY, mappedBy="dict",cascade=CascadeType.ALL)
   public List<DictItem> getDictItems() {
       return this.dictItem;
   }	    
   /**     
    *
    */
   public void setDictItems(List<DictItem> dictItem) {
       this.dictItem = dictItem;
   }

}