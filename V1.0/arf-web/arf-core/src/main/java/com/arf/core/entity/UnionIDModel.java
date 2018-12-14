package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "unionIDModel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "unionIDModel_sequence")
public class UnionIDModel extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = 3482556583773882888L;
    
    private int subscribe;
	/** 唯一标示变化（必填） */
	private String openid;
	/** 唯一标示不变（必填）*/
	private String unionID;
	/** 订阅时间/秒(必填)*/
	private long subscribe_time;
	/** 语言*/
	private String  language;
	/** 城市*/
	private String city ;
	/** 省份*/
	private String province;
	/** 国家*/
	private String country;
	/** 昵称 */
	private String nickname;
	/** 性别1:男2：:女*/
	private int sex;
	
	@Transient
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	
	@Column(name = "openid", length = 64, nullable = false)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name = "unionID", length = 128, nullable = false)
	public String getUnionID() {
		return unionID;
	}
	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}
	
	@Column(name = "subscribe_time", nullable = false)
	public long getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	
	@Column(name = "language",length=32)
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Column(name = "city",length=32)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "province",length=32)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "country",length=32)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "nickname",length=32)
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column(name = "sex",length=32)
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
}
