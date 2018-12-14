package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_interest_label")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_interest_label_sequence")
public class RelievedAssistInterestLabel extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6820052565535054952L;
	
	private String name;//标签分类名称
	private String lableList;//分类标签列表（id 以“，”号分割）
	private Status status;//状态（0 Normal 正常 1 Disabled 禁用）
	private Long parentId;//分类id
	private LabelType labelType;//标签类型（0 大分类 Modular 1 Label 标签）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum LabelType{
		Modular,//大分类
		Label;//标签
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Disabled;// 禁用
	}
	@Column(length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=100)
	public String getLableList() {
		return lableList;
	}

	public void setLableList(String lableList) {
		this.lableList = lableList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public LabelType getLabelType() {
		return labelType;
	}

	public void setLabelType(LabelType labelType) {
		this.labelType = labelType;
	}

}
