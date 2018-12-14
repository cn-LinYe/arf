package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assistClassify")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assistClassify_sequence")
public class RelievedAssistClassify extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978186742048542769L;
	
	private String classifyName;//分类名称
	private String classifyCopywriting_1;//文案1
	private String classifyCopywriting_2;//文案2
	private String classifyCopywriting_3;//文案3
	private String classifyCopywriting_4;//文案4
	private String classifyCopywriting_5;//文案5
	private String classifyCopywriting_6;//文案6
	private String classifyCopywriting_7;//文案7
	private Status status;//普通	状态(0、正常Normal) 1、禁用 Disabled
	private String classifyUrl;//分类图标
	private String shortUrl;//分类小图标
	private String shortName;//简称
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,//正常
		Disabled;//禁用
	}
	
	@Column(length=20)
	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	@Column(length=50)
	public String getClassifyCopywriting_1() {
		return classifyCopywriting_1;
	}

	public void setClassifyCopywriting_1(String classifyCopywriting_1) {
		this.classifyCopywriting_1 = classifyCopywriting_1;
	}
	@Column(length=50)
	public String getClassifyCopywriting_2() {
		return classifyCopywriting_2;
	}

	public void setClassifyCopywriting_2(String classifyCopywriting_2) {
		this.classifyCopywriting_2 = classifyCopywriting_2;
	}
	@Column(length=50)
	public String getClassifyCopywriting_3() {
		return classifyCopywriting_3;
	}

	public void setClassifyCopywriting_3(String classifyCopywriting_3) {
		this.classifyCopywriting_3 = classifyCopywriting_3;
	}
	@Column(length=50)
	public String getClassifyCopywriting_4() {
		return classifyCopywriting_4;
	}

	public void setClassifyCopywriting_4(String classifyCopywriting_4) {
		this.classifyCopywriting_4 = classifyCopywriting_4;
	}
	@Column(length=50)
	public String getClassifyCopywriting_5() {
		return classifyCopywriting_5;
	}

	public void setClassifyCopywriting_5(String classifyCopywriting_5) {
		this.classifyCopywriting_5 = classifyCopywriting_5;
	}
	@Column(length=50)
	public String getClassifyCopywriting_6() {
		return classifyCopywriting_6;
	}
	@Column(length=50)
	public void setClassifyCopywriting_6(String classifyCopywriting_6) {
		this.classifyCopywriting_6 = classifyCopywriting_6;
	}

	public String getClassifyCopywriting_7() {
		return classifyCopywriting_7;
	}
	@Column(length=50)
	public void setClassifyCopywriting_7(String classifyCopywriting_7) {
		this.classifyCopywriting_7 = classifyCopywriting_7;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=100)
	public String getClassifyUrl() {
		return classifyUrl;
	}

	public void setClassifyUrl(String classifyUrl) {
		this.classifyUrl = classifyUrl;
	}
	@Column(length=100)
	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	@Column(length=20)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
