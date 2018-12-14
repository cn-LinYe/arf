/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 参数值
 * 
 * @author arf
 * @version 4.0
 */
public class ParameterValue implements Serializable {

	private static final long serialVersionUID = 1915986624257267840L;

	/** 参数组 */
	private String group;

	/** 条目 */
	private List<ParameterValue.Entry> entries = new ArrayList<ParameterValue.Entry>();

	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@NotEmpty
	@Length(max = 200)
	public String getGroup() {
		return group;
	}

	/**
	 * 设置参数组
	 * 
	 * @param group
	 *            参数组
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * 获取条目
	 * 
	 * @return 条目
	 */
	@Valid
	@NotEmpty
	public List<ParameterValue.Entry> getEntries() {
		return entries;
	}

	/**
	 * 设置条目
	 * 
	 * @param entries
	 *            条目
	 */
	public void setEntries(List<ParameterValue.Entry> entries) {
		this.entries = entries;
	}

	/**
	 * Entity - 条目
	 * 
	 * @author arf
	 * @version 4.0
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 4931882586949537777L;

		/** 名称 */
		private String name;

		/** 值 */
		private String value;

		/**
		 * 获取名称
		 * 
		 * @return 名称
		 */
		@NotEmpty
		@Length(max = 200)
		public String getName() {
			return name;
		}

		/**
		 * 设置名称
		 * 
		 * @param name
		 *            名称
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取值
		 * 
		 * @return 值
		 */
		@NotEmpty
		@Length(max = 200)
		public String getValue() {
			return value;
		}

		/**
		 * 设置值
		 * 
		 * @param value
		 *            值
		 */
		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			Entry other = (Entry) obj;
			return new EqualsBuilder().append(getName(), other.getName()).isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37).append(getName()).toHashCode();
		}

	}

}