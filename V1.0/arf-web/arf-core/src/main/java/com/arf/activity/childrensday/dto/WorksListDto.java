package com.arf.activity.childrensday.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WorksListDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String worksPreviewUrl;//预览图相对路径地址
	private String worksLargeUrl;//大图相对路径地址
	private String authorName;//作者姓名
	private Integer worksNum;//作品编号,61前缀打头,自增
	private Integer totalVotedCount;//总投票数,默认0
	
	private boolean isVoted;//是否已经投票
	private Integer myVotedCount;//我投的票数
	private BigDecimal votedRate;//得票率百分比 0.01
	private int rank;
	
	public int getRank() {
		return rank;
	}
	public String getWorksPreviewUrl() {
		return worksPreviewUrl;
	}
	public String getWorksLargeUrl() {
		return worksLargeUrl;
	}
	public String getAuthorName() {
		return authorName;
	}
	public Integer getWorksNum() {
		return worksNum;
	}
	public Integer getTotalVotedCount() {
		return totalVotedCount;
	}
	public boolean isVoted() {
		return isVoted;
	}
	public Integer getMyVotedCount() {
		return myVotedCount;
	}
	public BigDecimal getVotedRate() {
		return votedRate;
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
	public void setVoted(boolean isVoted) {
		this.isVoted = isVoted;
	}
	public void setMyVotedCount(Integer myVotedCount) {
		this.myVotedCount = myVotedCount;
	}
	public void setVotedRate(BigDecimal votedRate) {
		this.votedRate = votedRate;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
