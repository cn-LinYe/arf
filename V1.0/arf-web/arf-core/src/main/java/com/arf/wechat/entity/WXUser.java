package com.arf.wechat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
		name = "wx_user",
		uniqueConstraints={@UniqueConstraint(columnNames={"openId"},name="uk_openId")}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "wx_user_sequence")
public class WXUser extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 8713449258219471835L;
	
	private String openId;//微信openid
	private String nickName;//微信昵称
	private String city ;//城市
	private String country;//国家
	private String language;//语言
	private String unionid;//关注unionId
	private Date subscribeTime	;//关注时间
	private String province;//省份
	private Integer sex;//性别 1:男2:女
	private String headimgurl;//用户头像
	
	@Column(length = 40,nullable = false)
	public String getOpenId() {
		return openId;
	}
	@Column(length = 50)
	public String getNickName() {
		return nickName;
	}
	@Column(length = 50)
	public String getCity() {
		return city;
	}
	@Column(length = 50)
	public String getCountry() {
		return country;
	}
	@Column(length = 50)
	public String getLanguage() {
		return language;
	}
	@Column(length = 50)
	public String getUnionid() {
		return unionid;
	}
	public Date getSubscribeTime() {
		return subscribeTime;
	}
	@Column(length = 50)
	public String getProvince() {
		return province;
	}
	@Column(length = 11)
	public Integer getSex() {
		return sex;
	}
	@Column(length = 200)
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

}
