package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "relieved_assist_sensitive_word_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_sensitive_word_record_sequence")
public class RelievedAssistSensitiveWordRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5144076951967068783L;
	
	private String sensitiveWord;//	敏感词
	private Integer hitNumber;//命中次数
	
	@Column(length=10)
	public String getSensitiveWord() {
		return sensitiveWord;
	}
	public void setSensitiveWord(String sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
	}
	public Integer getHitNumber() {
		return hitNumber;
	}
	public void setHitNumber(Integer hitNumber) {
		this.hitNumber = hitNumber;
	}

}
