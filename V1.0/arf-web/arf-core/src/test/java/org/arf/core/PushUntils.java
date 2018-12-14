package org.arf.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.PropertyAccessorUtils;

import com.arf.core.oldws.PushMessage;
import com.arf.core.util.MD5Util;
import com.arf.core.util.PropertiesUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送util.
 * 推送配置appkey,masterSecret见{@link com.arf.core.oldws.PushUntils.PushConstant}
 */
public class PushUntils {

	public class PushConstant{
		/**
		 * 安心点用户版appkey
		 */
		public static final String AXD_USER_APPKEY = "408d5a1ba470aa26fdb9782d";
		/**
		 * 安心点用户版masterSercet
		 */
		public static final String AXD_USER_MASTERSERCET = "41489972980b2102c74b9060";
		
		/**
		 * 安心点用户版appkey
		 */
		public static final String AXD_MERCHANT_APPKEY = "a37a8382be0a61ba661a54a7";
		/**
		 * 安心点用户版masterSercet
		 */
		public static final String AXD_MERCHANT_MASTERSERCET = "6b2115ab4ae844debf236665";
	}
	
	private String appkey;
	private String masterSecret;
	
	private boolean apnsProduction=true;
	
	private JPushClient getJPushClient(String appkey,String masterSecret) {
		return new JPushClient(masterSecret,appkey);
	}

	private PushUntils(String appkey, String masterSecret) {
		super();
		this.appkey = appkey;
		this.masterSecret = masterSecret;
	}
	
	private static final PushUntils userPushUntils = new PushUntils(PushConstant.AXD_USER_APPKEY,PushConstant.AXD_USER_MASTERSERCET);
	/**
	 * 获得用户版推送util
	 * @return
	 */
	public static PushUntils getUserPushUntils(){
		return userPushUntils;
	}
	
	private static final PushUntils merchantPushUntils = new PushUntils(PushConstant.AXD_MERCHANT_APPKEY,
			PushConstant.AXD_MERCHANT_MASTERSERCET);
	/**
	 * 获得商都版推送util
	 * @return
	 */
	public static PushUntils getMerchantPushUntils(){
		return merchantPushUntils ;
	}

	/**
	 * 单个用户推送消息(全平台)
	 * 
	 * @param userName
	 *            用户名
	 * @param title
	 *            通知栏标题
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
	
	public PushResult pushNotificationMsgToUser(String userName, String title, String msg, Map<String, String> extras,	Long timeToLive) throws APIConnectionException, APIRequestException {
		String md5u =MD5Util.getMD5(userName);
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();

		jBuilder.setPlatform(Platform.all());
		jBuilder.setAudience(Audience.alias(md5u));

		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		/*Message message = Message.newBuilder()
				.setMsgContent(msg)
				.setTitle(title)
				.addExtras(extras)
				.build();
		jBuilder.setMessage(message);*/
		jBuilder.setNotification(Notification.alert(title));
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}

	/**
	 * 单个用户推送消息
	 * 
	 * @param userName
	 *            用户名
	 * @param title
	 *            通知栏标题
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
//	public static void main(String[] args) {
//		PushUntils pushUntils = new PushUntils();
//		try {
//			Map<String, String> map=new HashMap<String,String>();
//			pushUntils.pushNotificationMsgToAndroidUser("13794899253","安心点","sss", map, null);
//		} catch (APIConnectionException e) {
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			e.printStackTrace();
//		}
//	}
	
	public PushResult pushNotificationMsgToAndroidUser(String userName, String title, String msg,
			Map<String, String> extras, Long timeToLive) throws APIConnectionException, APIRequestException {
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		String md5u =MD5Util.getMD5(userName);
		System.out.println(md5u);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.android()).setAudience(Audience.alias(md5u));

		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		jBuilder.setNotification(Notification.android(msg, title, extras));
		jBuilder.setOptions(Options.newBuilder().setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}

	/**
	 * 单个用户推送消息
	 * 
	 * @param userName
	 *            用户名
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
	
//	public static void main(String[] args) {
//	PushUntils push = new PushUntils();
//	Map<String, String> map = new HashMap<String,String>();
//	map.put("system", "1");
//	try {
////		String b = MD5Util.getMD5("18122849405");
//		System.out.println("18122849405");
//		push.pushNotificationMsgToIOSUser("18122849405", "测试", map,null);
//	} catch (APIConnectionException e) {
//		e.printStackTrace();
//	} catch (APIRequestException e) {
//		e.printStackTrace();
//	}
//	}
	
	public PushResult pushNotificationMsgToIOSUser(String userName, String msg, Map<String, String> extras,Long timeToLive) throws APIConnectionException, APIRequestException {
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		String md5u =MD5Util.getMD5(userName);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.ios()).setAudience(Audience.alias(md5u));
		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		jBuilder.setNotification(Notification.ios(msg, extras));
		// 生产环境IOS
		/**
		 * 生产环境和开发环境
		 */
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}

	/**
	 * 广播推送。
	 * 
	 * @param title
	 *            通知栏标题
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
	public PushResult pushNotificationMsgToAll(String title, String msg, Map<String, String> extras, Long timeToLive)
			throws APIConnectionException, APIRequestException {
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.all()).setAudience(Audience.all());

		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		/*Message message = Message.newBuilder()
				.setMsgContent(msg)
				.setTitle(title)
				.addExtras(extras)
				.build();
		jBuilder.setMessage(message);*/
		jBuilder.setNotification(Notification.alert(title));
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}

	/**
	 * android广播推送
	 * 
	 * @param title
	 *            标题
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
	public PushResult pushNotificationMsgToAndroids(String title, String msg, Map<String, String> extras,
			Long timeToLive) throws APIConnectionException, APIRequestException {
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.android()).setAudience(Audience.all());

		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		jBuilder.setNotification(Notification.android(msg, title, extras));
		jBuilder.setOptions(Options.newBuilder().setTimeToLive(timeToLive).build());

		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}

	/**
	 * ios 广播推送
	 * 
	 * @param msg
	 *            消息体
	 * @param extras
	 *            额外信息。（不会在通知栏上显示）
	 * @param timeToLive
	 *            离线消息保留时长(秒) null:默认时长1天,最长10天。
	 * @return 请求结果
	 * @throws APIConnectionException
	 *             极光API连接异常
	 * @throws APIRequestException
	 *             极光API请求异常
	 */
	public PushResult pushNotificationMsgToIOSs(String msg, Map<String, String> extras, Long timeToLive)
			throws APIConnectionException, APIRequestException {
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.ios()).setAudience(Audience.all());

		if (null == extras)
			extras = new HashMap<String, String>();
		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}

		jBuilder.setNotification(Notification.ios(msg, extras));
		// 生产环境IOS
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());

		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}
	
	/**
	 * 推送自定义消息
	 * @param pushMessage
	 * @param timeToLive 为null 或者小于0则24小时有效 单位:秒
	 * @param registrationIds
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult pushCustomMsg(PushMessage pushMessage, Long timeToLive,String ...registrationIds) throws APIConnectionException, APIRequestException{
		if(registrationIds == null || registrationIds.length == 0 || pushMessage == null){
			return null;
		}
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder.setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationIds));

		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		
		Message message = Message.newBuilder()
				.setTitle(pushMessage.getTitle())
				.setContentType(pushMessage.getContentType())
				.setMsgContent(pushMessage.getMsgContent())
				.addExtras(pushMessage)
				.build();
		
		jBuilder.setMessage(message);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(21)
				.build());

		PushPayload pushPayload = jBuilder.build();
		return jPushClient.sendPush(pushPayload);
	}
}
