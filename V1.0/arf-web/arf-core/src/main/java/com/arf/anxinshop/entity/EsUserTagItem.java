package com.arf.anxinshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "es_user_tag_item")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "es_user_tag_item_sequence")
public class EsUserTagItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -2907633336836924764L;
	
	private Double weight;//权重
	private String expression;//表达式,多个用英文逗号分隔
	private String name;//项目名
	private Integer groupId;//组id
	
	@Column(precision=2,scale=1)
	public Double getWeight() {
		return weight;
	}
	@Column(length=1024)
	public String getExpression() {
		return expression;
	}
	@Column(length=30)
	public String getName() {
		return name;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
}
