package com.arf.core.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 用户意见反馈
 */
@Entity(name="advice_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "advice_type_sequence")
public class AdviceType extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = 5421830082515027363L;
    
    /**
     * 类型含义
     */
   private String typeContent;
   
   /**
    * 类型等级（1级别没有父类型一共两级）
    */
   private Integer level;
   /**
    * 父类型id
    */
   private Long partentId;
	   
	public String getTypeContent() {
		return typeContent;
	}
	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getPartentId() {
		return partentId;
	}
	public void setPartentId(Long partentId) {
		this.partentId = partentId;
	}
	   
   
}
