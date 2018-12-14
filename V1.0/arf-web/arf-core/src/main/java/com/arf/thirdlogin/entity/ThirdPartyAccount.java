package com.arf.thirdlogin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "third_party_account")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "third_party_account_sequence")
public class ThirdPartyAccount extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4904249042847824223L;
	
	private String userName;//登陆账号（手机号码）
	private String wxOpenId;//微信openid
	private String wxUnionid;//微信unionid
	private String wxNickName;//微信昵称
	private String wxPhoto;//微信头像
	private String qqOpenId;//qq openid
	private String qqNickName;//qq昵称
	private String qqPhoto;//qq头像
	private String weiboUid;//微博uid
	private String weiboNickName;//微博昵称
	private String weiboPhoto;//微博头像
	private DefaultAccount defaultAccount;//默认账号枚举0 phone手机号码,1 qq 腾讯QQ,2 wechat 微信,3 weibo 新浪微博
	
	/**0 phone手机号码
	 * 1 qq 腾讯QQ
	 * 2 wechat 微信
	 * 3 weibo 新浪微博
	 * */
	public enum DefaultAccount{
		phone,//手机号码
		qq,//腾讯QQ,
		wechat,//微信
		weibo;// 新浪微博
	}
	
	@Column(length=40 ,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=100)
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	@Column(length=100)
	public String getWxUnionid() {
		return wxUnionid;
	}
	public void setWxUnionid(String wxUnionid) {
		this.wxUnionid = wxUnionid;
	}
	@Column(length=40)
	public String getWxNickName() {
		return wxNickName;
	}
	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}
	@Column(length=1000)
	public String getWxPhoto() {
		return wxPhoto;
	}
	public void setWxPhoto(String wxPhoto) {
		this.wxPhoto = wxPhoto;
	}
	@Column(length=100)
	public String getQqOpenId() {
		return qqOpenId;
	}
	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}
	@Column(length=40)
	public String getQqNickName() {
		return qqNickName;
	}
	public void setQqNickName(String qqNickName) {
		this.qqNickName = qqNickName;
	}
	@Column(length=1000)
	public String getQqPhoto() {
		return qqPhoto;
	}
	public void setQqPhoto(String qqPhoto) {
		this.qqPhoto = qqPhoto;
	}
	@Column(length=100)
	public String getWeiboUid() {
		return weiboUid;
	}
	public void setWeiboUid(String weiboUid) {
		this.weiboUid = weiboUid;
	}
	@Column(length=40)
	public String getWeiboNickName() {
		return weiboNickName;
	}
	public void setWeiboNickName(String weiboNickName) {
		this.weiboNickName = weiboNickName;
	}
	@Column(length=1000)
	public String getWeiboPhoto() {
		return weiboPhoto;
	}
	public void setWeiboPhoto(String weiboPhoto) {
		this.weiboPhoto = weiboPhoto;
	}
	public DefaultAccount getDefaultAccount() {
		return defaultAccount;
	}
	public void setDefaultAccount(DefaultAccount defaultAccount) {
		this.defaultAccount = defaultAccount;
	}
    

}
