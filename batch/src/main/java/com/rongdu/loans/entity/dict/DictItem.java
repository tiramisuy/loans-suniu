package com.rongdu.loans.entity.dict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "SYS_DICT_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictItem{
		
	private Long id;
	/**   
	  *  字典代码
     */
	private String code;
	/**   
	  *  字典代码
     */
	private Dict dict;
	/**   
	  *  字典名称
     */
	private String name;
	/**   
	  *  备注
     */
	private String remark;

   public DictItem() {
   }
	
   public DictItem(String code, String name) {
       this.code = code;
       this.name = name;
   }
   public DictItem(String code, Dict dict, String name, String remark) {
      this.code = code;
      this.dict = dict;
      this.name = name;
      this.remark = remark;
   }
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 19, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
  
  /**     
    *访问<字典代码>属性
    */
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
    *访问<字典代码>属性
    */
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="DICT_CODE")
   public Dict getDict() {
       return this.dict;
   }	    
   /**     
    *设置<字典代码>属性
    */
   public void setDict(Dict dict) {
       this.dict = dict;
   }
  /**     
    *访问<字典名称>属性
    */
   @Column(name="NAME", nullable=false, length=40)
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

}