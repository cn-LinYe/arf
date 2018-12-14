package com.arf.core.oldws;

import java.io.Serializable;
import java.util.HashMap;

public class PushMessage extends HashMap<String,String> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 推送消息的ContentType枚举,根据不同业务定义,前端获取到该参数后做不同业务区别
	 * @author Caolibao
	 * 2016年7月15日 下午4:26:08
	 *
	 */
	public enum ContentType{
		/**
		 * 点滴洗-订单
		 */
		CARBRIGHTER_ORDER,
		/**
		 * 强制下线
		 */
		FORCE_LOGOUT,
		/**
		 * 洗衣-订单
		 */
		LAUNDRY_ORDER,
		/**
		 *保险—订单 
		 */
		INSURANCE_ORDER,
		/**
		 * 快递—订单
		 */
		EXPRESS_ORDER,
		/**
		 * 违章处理—订单
		 */
		VIOLATION_ORDER,
		/**
		 * 安心点闸门
		 */
		AXD_SALESMAN_ORDER,
		/**
		 * 洗车机支付
		 */
		CAR_WASH_ORDER,
		/**
		 * 用户没签到
		 */
		USER_NOT_SIGN,
		/**
		 * 套餐过期提醒
		 */
		PACKAGE_EXPIRES,
		/**
		 * 会员升级
		 */
		VIP_LEVEL_UPGRADE,
		/**
		 * 停车费减免申请
		 */
		APPLY_PARKING_REDUCTION,
		/**
		 * 门禁授权
		 */
		ACCESS_AUTHORIZATION,
		/**
		 * 门禁拜访申请
		 */
		ACCESS_GUEST_APPLY,
		/**
		 * 商户强制下线
		 */
		MERCHANT_FORCE_LOGOUT,
		/**
		 * 房间添加住户
		 */
		ROOM_ADD_USER,
		/**
		 * 房间添加管理员
		 */
		ROOM_ADD_MANAGER,
		/**
		 * 房间通知，不显示按钮，用于前端门禁消息中增加连接显示
		 */
		ROOM_NOTIFICATION,
		/**
		 * 物业费推送
		 */
		PROPERTY_FEE,
		/**
		 * 家加油会员注册推送
		 */
		REFUEL_GAS_REGISTER,
		/**
		 * 家加油会员新增车辆推送
		 */
		REFUEL_GAS_ADDLICENSE,
		/**
		 * 家加油开卡设置推送
		 */
		REFUEL_GAS_OPENCARD,
		/**
		 * 家加油支付推送
		 */
		REFUEL_GAS_ORDER,
		
		/**
		 * 家加油余额不足推送
		 */
		REFUEL_GAS_BALANCE,
		/**
		 * 家加油充值推送
		 */
		REFUEL_GAS_RECHARGE,
		/**
		 * 家加油安全推送
		 */
		REFUEL_GAS_SAFE,
		/**
		 * 修改门禁密码
		 */
		ACCESS_MODIFY_PASSWORD,
		/**
		 * 可视化门禁视频语音对讲推送
		 */
		ACCESS_VIDEO_VOICE,
		/**
		 * 安心帮回复推送
		 */
		RELIEVED_ASSIST_REPLY,
		/**
		 * 热点文章推送
		 */
		HOT_ARTICLE_PUSH,
		;
	}
	
	public PushMessage(){
		super();
	}
	
	public PushMessage(String title,ContentType contentType,String msgContent){
		super();
		this.setTitle(title);
		this.setContentType(contentType.toString());
		this.setMsgContent(msgContent);
	}
	
	public String getTitle() {
		return this.get("title") == null ?null:(String)this.get("title");
	}

	public void setTitle(String title) {
		this.put("title", title);
	}
	
	public String getContentType() {
		return this.get("contentType") == null ?null:(String)this.get("contentType");
	}

	public void setContentType(String contentType) {
		this.put("contentType", contentType);
	}
	
	public String getMsgContent() {
		return this.get("msgContent") == null ?null:(String)this.get("msgContent");
	}
	public void setMsgContent(String msgContent) {
		this.put("msgContent", msgContent);
	}
}