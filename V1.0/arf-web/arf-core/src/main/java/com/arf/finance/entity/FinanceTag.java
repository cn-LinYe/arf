package com.arf.finance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="finance_tag")
@SequenceGenerator(name="sequenceGenerator",sequenceName="finance_tag_sequence")
public class FinanceTag extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private String name;//

	@Column(length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
