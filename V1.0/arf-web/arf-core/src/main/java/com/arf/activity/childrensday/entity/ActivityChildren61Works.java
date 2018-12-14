package com.arf.activity.childrensday.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "activity_children61_works",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "worksNum"}, name = "unique_worksNum")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "activity_children61_works_sequence")
public class ActivityChildren61Works extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String worksPreviewUrl;//预览图相对路径地址
	private String worksLargeUrl;//大图相对路径地址
	private String authorName;//作者姓名
	private Integer worksNum;//作品编号,61前缀打头,自增
	private Integer totalVotedCount;//总投票数,默认0
	
	@Column(length = 100)
	public String getWorksPreviewUrl() {
		return worksPreviewUrl;
	}
	@Column(length = 100)
	public String getWorksLargeUrl() {
		return worksLargeUrl;
	}
	@Column(length = 30)
	public String getAuthorName() {
		return authorName;
	}
	public Integer getWorksNum() {
		return worksNum;
	}
	public Integer getTotalVotedCount() {
		return totalVotedCount;
	}
	public void setWorksPreviewUrl(String worksPreviewUrl) {
		this.worksPreviewUrl = worksPreviewUrl;
	}
	public void setWorksLargeUrl(String worksLargeUrl) {
		this.worksLargeUrl = worksLargeUrl;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public void setWorksNum(Integer worksNum) {
		this.worksNum = worksNum;
	}
	public void setTotalVotedCount(Integer totalVotedCount) {
		this.totalVotedCount = totalVotedCount;
	}

}
