package com.arf.core.oldws.wx.entity;

public class WxUser {
	
	public WxUser(){};
	private String openid;
	private String unionid;
	private String nickname;
	private String sex;
	/**
	 * 订阅时间/秒(必填)
	 */
	
	private long subscribe_time;
	
	

	public WxUser(String openid, String unionid, String nickname, String sex, long subscribe_time) {
		super();
		this.openid = openid;
		this.unionid = unionid;
		this.nickname = nickname;
		this.sex = sex;
		this.subscribe_time = subscribe_time;
	}
	
	public long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
