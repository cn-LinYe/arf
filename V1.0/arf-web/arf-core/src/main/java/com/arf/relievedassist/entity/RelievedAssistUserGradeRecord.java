package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "relieved_assist_user_grade_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_user_grade_record_sequence")
public class RelievedAssistUserGradeRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6026533260120343074L;
	
	private Integer grade;//等级
	private String gradeName;//等级名称
	private String gradeUrl;//等级图标
	private Integer gradeMinPoint;//等级最小分
	private Integer gradeMaxPoint;//等级最大分
	
	
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	@Column(length=20)
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	@Column(length=65)
	public String getGradeUrl() {
		return gradeUrl;
	}
	public void setGradeUrl(String gradeUrl) {
		this.gradeUrl = gradeUrl;
	}
	public Integer getGradeMinPoint() {
		return gradeMinPoint;
	}
	public void setGradeMinPoint(Integer gradeMinPoint) {
		this.gradeMinPoint = gradeMinPoint;
	}
	public Integer getGradeMaxPoint() {
		return gradeMaxPoint;
	}
	public void setGradeMaxPoint(Integer gradeMaxPoint) {
		this.gradeMaxPoint = gradeMaxPoint;
	}

}
