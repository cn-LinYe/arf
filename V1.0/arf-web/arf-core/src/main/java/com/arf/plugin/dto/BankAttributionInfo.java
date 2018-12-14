package com.arf.plugin.dto;

public class BankAttributionInfo {
	 private Integer status; //0 201银行卡号为空 202 卡号不正确 210 没有信息
	 private String msg;	 
	 private String bankNumber;//卡号
	 private String bankShortName;//银行简称
	 private String name;//牡丹卡普卡
	 private String province; //浙江
	 private String bankBranch;//支行名称
	 private String city; //杭州
	 private String type; //借记卡
	 private Integer len; //19
	 private String bankName; //中国工商银行
	 private String logo; //http://www.jisuapi.com/api/bankcard/upload/80/2.png
	 private String tel; //95588
	 private String website; //http://www.icbc.com.cn
	 private Integer iscorrect; //0错误 1正确
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getLen() {
		return len;
	}
	public void setLen(Integer len) {
		this.len = len;
	}	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Integer getIscorrect() {
		return iscorrect;
	}
	public void setIscorrect(Integer iscorrect) {
		this.iscorrect = iscorrect;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	 
	 
}
