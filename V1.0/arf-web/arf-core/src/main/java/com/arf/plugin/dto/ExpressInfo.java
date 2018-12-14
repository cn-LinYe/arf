package com.arf.plugin.dto;

import com.alibaba.fastjson.JSONArray;

public class ExpressInfo {
	public int resCode;//平台返回码,0为成功,其他为失败
	public String resError;//平台返回的错误信息	
	public String mailNo ;//快递单号"968018776110"
	public Long update; //数据最后查询的时间1466926312666
	public String updateStr;//数据最后更新的时间"2016-06-26 15:31:52"
	public Status status; //-1 待查询 0 查询异常 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单 8 超时单 9 签收失败 10 退回
	public String tel;//快递公司电话 400-889-5543
	public String expSpellName;//快递字母简称shentong
	public JSONArray data; //具体快递路径信息{"time": "2016-06-26 12:26","context": "已签收,签收人是:【本人】"}
	public String expTextName;//快递公司名 申通快递
	
	public enum Status{
		To_Be_Inquired,//-1 待查询 
		Query_Exception,//0 查询异常
		No_Record,// 1 暂无记录 
		On_The_Way,//2 在途中 
		Delivery,//3 派送中 
		Have_Been_Received,//4 已签收
		User_Refused,// 5 用户拒签 
		Difficult_Pieces,//6 疑难件 		
		Invalid_Single,//7 无效单
		Timeout,//8 超时单
		Signing_Failed, //9 签收失败 
		Return;//10 退回
		public static Status get(int ordinal){
			Status statuss[] = Status.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResError() {
		return resError;
	}

	public void setResError(String resError) {
		this.resError = resError;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public Long getUpdate() {
		return update;
	}

	public void setUpdate(Long update) {
		this.update = update;
	}

	public String getUpdateStr() {
		return updateStr;
	}

	public void setUpdateStr(String updateStr) {
		this.updateStr = updateStr;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getExpSpellName() {
		return expSpellName;
	}

	public void setExpSpellName(String expSpellName) {
		this.expSpellName = expSpellName;
	}

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

	public String getExpTextName() {
		return expTextName;
	}

	public void setExpTextName(String expTextName) {
		this.expTextName = expTextName;
	}
	
}
