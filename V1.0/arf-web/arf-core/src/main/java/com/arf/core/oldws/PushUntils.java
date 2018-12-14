package com.arf.core.oldws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dto.MemberCachedDto;
import com.arf.core.entity.MessagePushed;
import com.arf.core.entity.MessagePushed.AppType;
import com.arf.core.entity.MessagePushed.UserType;
import com.arf.core.oldws.PushMessage.ContentType;
import com.arf.core.service.MessagePushedService;
import com.arf.core.util.MD5Util;
import com.arf.core.util.SpringUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
/**
 * 极光推送util.
 * 推送配置appkey,masterSecret见{@link com.arf.core.oldws.PushUntils.PushConstant}
 */
public class PushUntils {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private MessagePushedService messagePushedService;
	
	private RedisService redisService;

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
		
		/**
		 * 办公室门禁app用户版appkey
		 */
		public static final String OFFICE_USER_APPKEY = "56ce5e56de806c94952f17f2";
		/**
		 * 办公室门禁app用户版masterSercet
		 */
		public static final String OFFICE_USER_MASTERSERCET = "315bd7f14395d2e88454129d";
	}
	
	private String appkey;
	private String masterSecret;
	private UserType userType;//推送对象类型，0普通用户，1商户
	private AppType appType;//app类型 0或null 安心点app，1 办公室门禁app
	
	private boolean apnsProduction=true;
	
	private String[] Unimportant_Push = {ContentType.FORCE_LOGOUT.toString(),ContentType.MERCHANT_FORCE_LOGOUT.toString()};
	
	private JPushClient getJPushClient(String appkey,String masterSecret) {
		return new JPushClient(masterSecret,appkey);
	}

	private PushUntils(String appkey, String masterSecret, UserType userType,AppType appType) {
		super();
		this.appkey = appkey;
		this.masterSecret = masterSecret;
		this.userType = userType;
		this.appType = appType;
		try {
			Properties properties = new Properties();
			URL res = PushUntils.class.getClassLoader().getResource("dbconfig.properties");
			properties.load(new FileInputStream(new File(res.getPath())));
			this.apnsProduction = Boolean.valueOf(properties.getProperty("apns.production"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.messagePushedService = SpringUtils.getBean("messagePushedService", MessagePushedService.class);
		this.redisService = SpringUtils.getBean("redisService", RedisService.class);
	}
	
	private static final PushUntils userPushUntils = new PushUntils(PushConstant.AXD_USER_APPKEY,PushConstant.AXD_USER_MASTERSERCET,UserType.USER,AppType.AXD);
	/**
	 * 获得用户版推送util
	 * @return
	 */
	public static PushUntils getUserPushUntils(){
		return userPushUntils;
	}
	
	private static final PushUntils merchantPushUntils = new PushUntils(PushConstant.AXD_MERCHANT_APPKEY,
			PushConstant.AXD_MERCHANT_MASTERSERCET,UserType.MERCHANT,AppType.AXD);
	/**
	 * 获得商都版推送util
	 * @return
	 */
	public static PushUntils getMerchantPushUntils(){
		return merchantPushUntils ;
	}
	
	private static final PushUntils officeUserPushUntils = new PushUntils(PushConstant.OFFICE_USER_APPKEY,PushConstant.OFFICE_USER_MASTERSERCET,UserType.OFFICEUSER,AppType.OFFICE);
	/**
	 * 获得办公室门禁用户版推送util
	 * @return
	 */
	public static PushUntils getOfficeUserPushUntils(){
		return officeUserPushUntils;
	}

	/**
	 * 单个用户推送消息(全平台) <b>调用此方法请仔细阅读传入参数说明</b>
	 * 
	 * @param userName
	 *            用户名
	 * @param title
	 *            通知栏标题( <b>注意</b>：实际上此<b>title</b>当成<b>msg</b>使用 )
	 * @param msg
	 *            消息体( <b>注意</b>：实际上此<b>msg</b>当成<b>title</b>使用 )
	 * @param extras
	 *            额外信息。（不会在通知栏上显示 <b>注意</b>：实际上此<b>extras</b>并未使用）
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
		
		//查询未读消息数量
		int unreadCount = messagePushedService.findUnreadCount(userName, this.userType);
		
		//jBuilder.setNotification(Notification.ios_set_badge(unreadCount + 1).alert(title));
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(title).setBadge(unreadCount+1).build())
				.addPlatformNotification(AndroidNotification.newBuilder().setAlert(title).setTitle(title).build())
				.build()
		);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult = null;
		try{
			boolean isLogout = false;
			isLogout = isLogout(userName, null);
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
			}
//			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(userName);
				messagePushed.setAlias(md5u);
//				messagePushed.setTitle("安心点");//
				messagePushed.setTitle(msg);//
				messagePushed.setMsgContent(title);//
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
//				messagePushed.setExtras(JSON.toJSONString(extras));
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
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
		jBuilder.setNotification(Notification.android(msg.
				replaceAll("<(\\S*?) [^>]*>.*?</\\1>|<.*?/>", ""), //修复通知消息内部有HTML标签导致通知栏的消息显示HTML源码的bug
				title, extras));
		jBuilder.setOptions(Options.newBuilder().setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult = null;
		try{
			boolean isLogout = false;
			isLogout = isLogout(userName, null);
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
			}
//			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(userName);
				messagePushed.setAlias(md5u);
				messagePushed.setTitle(title);
				messagePushed.setMsgContent(msg);
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
				messagePushed.setExtras(JSON.toJSONString(extras));
				messagePushed.setTransactionId(extras.get("id"));
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
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
		
		int unreadCount = messagePushedService.findUnreadCount(userName, this.userType);
		
		//jBuilder.setNotification(Notification.ios(msg, extras));
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().
						setAlert(msg.replaceAll("<(\\S*?) [^>]*>.*?</\\1>|<.*?/>", "")) //修复通知消息内部有HTML标签导致通知栏的消息显示HTML源码的bug
						.setBadge(unreadCount+1)
						.build())
				.build()
		);
		// 生产环境IOS
		/**
		 * 生产环境和开发环境
		 */
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();

		PushResult pushResult = null;
		try{
			boolean isLogout = false;
			isLogout = isLogout(userName, null);
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
			}
//			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(userName);
				messagePushed.setAlias(md5u);
//				messagePushed.setTitle("安心点");
				messagePushed.setMsgContent(msg);
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
				messagePushed.setExtras(JSON.toJSONString(extras));
				messagePushed.setTransactionId(extras.get("id"));
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
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
		
		//jBuilder.setNotification(Notification.ios(msg, extras));
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(msg).incrBadge(1).build())
				.addPlatformNotification(AndroidNotification.newBuilder().setAlert(title).setTitle(title).build())
				.build()
		);
		jBuilder.setNotification(Notification.alert(title));
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult = null;
		try{
			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(MessagePushed.AUDIENCE_ALL);
				//messagePushed.setAlias(md5u);
				messagePushed.setTitle(title);
				messagePushed.setMsgContent(title);
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
				/*messagePushed.setExtras(JSON.toJSONString(extras));*/
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
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

		PushResult pushResult = null;
		try{
			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(MessagePushed.AUDIENCE_ALL);
				//messagePushed.setAlias(md5u);
				messagePushed.setTitle(title);
				messagePushed.setMsgContent(msg);
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
				messagePushed.setExtras(JSON.toJSONString(extras));
				messagePushed.setTransactionId(extras.get("id"));
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
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
		//jBuilder.setNotification(Notification.ios(msg, extras));
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(msg).incrBadge(1).build())
				.build()
		);
		// 生产环境IOS
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());

		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult = null;
		try{
			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				MessagePushed messagePushed = new MessagePushed();
				messagePushed.setUserName(MessagePushed.AUDIENCE_ALL);
				//messagePushed.setAlias(md5u);
				//messagePushed.setTitle(title);
				messagePushed.setMsgContent(msg);
				messagePushed.setPushType(MessagePushed.PushType.NOTIFICATION);
				messagePushed.setExtras(JSON.toJSONString(extras));
				messagePushed.setTransactionId(extras.get("id"));
				messagePushed.setTimeToLive(timeToLive);
				messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
				if(pushResult!=null){
					messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
					messagePushed.setSendno(String.valueOf(pushResult.sendno));
				}else{
					String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
					messagePushed.setMsgId(uuid);
					messagePushed.setSendno(uuid);
				}
				messagePushed.setUserType(this.userType);
				messagePushed.setAppType(this.appType);
				messagePushedService.save(messagePushed);
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
	}
	
	/**
	 * 通过registrationId单个推送自定义消息
	 * @param pushMessage 不能为空
	 * @param timeToLive 为null 或者小于0则24小时有效 单位:秒
	 * @param registrationId 不能为空
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult pushCustomMsgSingle(PushMessage pushMessage, Long timeToLive,String registrationId) throws APIConnectionException, APIRequestException{
		if(pushMessage == null){
			return null;
		}
		if(StringUtils.isBlank(registrationId)){
			return null;
		}
		
		PushMessage copyMessage = new PushMessage();
		for(String key : pushMessage.keySet()){
			Object val = pushMessage.get(key);
			if(val != null){
				if(!(val instanceof String)){
					copyMessage.put(key, JSON.toJSONString(val));
				}else{
					copyMessage.put(key, val.toString());
				}
			}
		}
		
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder = jBuilder.setPlatform(Platform.all());
		jBuilder.setAudience(Audience.registrationId(registrationId));

		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		String msgUUID = UUID.randomUUID().toString().trim().replaceAll("-", "");
		copyMessage.put("msgUUID", msgUUID);
		Message message = Message.newBuilder()
				.setTitle(copyMessage.getTitle())
				.setContentType(copyMessage.getContentType())
				.setMsgContent(copyMessage.getMsgContent())
				.addExtras(copyMessage)
				.build();
		
		jBuilder.setMessage(message);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive)
				.build());

		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult = null;
		try{
			boolean isLogout = false;
			isLogout = isLogout(null, registrationId);
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
			}
//			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			if(!unimportant(pushMessage.getContentType())){
				//保存推送的消息记录
				try{
					alertInactiveAppSingle(StringUtils.isNotBlank(copyMessage.getMsgContent())?copyMessage.getMsgContent():copyMessage.getTitle(),timeToLive,null,null,registrationId);
					MessagePushed messagePushed = new MessagePushed();
					//messagePushed.setUserName(MessagePushed.AUDIENCE_ALL);
					//messagePushed.setAlias(md5u);
					messagePushed.setRegistrationId(registrationId);
					messagePushed.setTitle(pushMessage.getTitle());
					messagePushed.setMsgContent(pushMessage.getMsgContent());
					messagePushed.setContentType(pushMessage.getContentType());
					messagePushed.setExtras(JSON.toJSONString(copyMessage));
					messagePushed.setTransactionId(copyMessage.get("id"));
					messagePushed.setPushType(MessagePushed.PushType.CUSTOM_MESSAGE);
					messagePushed.setTimeToLive(timeToLive);
					messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
					if(pushResult!=null){
						messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
						messagePushed.setSendno(String.valueOf(pushResult.sendno));
					}else{
						String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
						messagePushed.setMsgId(uuid);
						messagePushed.setSendno(uuid);
					}
					if(this.userType == UserType.USER){
						messagePushed.setUserType(MessagePushed.UserType.USER);
						MemberCachedDto memberCachedDto = redisService.get(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId + registrationId, 
								MemberCachedDto.class);
						if(memberCachedDto!=null){
							messagePushed.setUserName(memberCachedDto.getUsername());
						}
					}else{
						messagePushed.setUserType(MessagePushed.UserType.MERCHANT);
					}
					messagePushed.setMsgUUID(msgUUID);
					messagePushed.setAppType(this.appType);
					messagePushedService.save(messagePushed);
				}catch(Exception e){
					logger.error("推送消息保存到数据库错误",e);
				}
			}
		}
		
	}
	
	private boolean unimportant(String contentType){
		if(ArrayUtils.contains(Unimportant_Push, contentType)){//强制下线不存
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 推送自定义消息
	 * @param pushMessage 不能为空
	 * @param timeToLive 为null 或者小于0则24小时有效 单位:秒
	 * @param alias 不能为空（MD5加密）
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult pushCustomMsgByAlias(PushMessage pushMessage, Long timeToLive,String alias,String userName) throws APIConnectionException, APIRequestException{
		if(pushMessage == null){
			return null;
		}
		if(StringUtils.isBlank(alias)){
			return null;
		}
		PushMessage copyMessage = new PushMessage();
		for(String key : pushMessage.keySet()){
			Object val = pushMessage.get(key);
			if(val != null){
				if(!(val instanceof String)){
					copyMessage.put(key, JSON.toJSONString(val));
				}else{
					copyMessage.put(key, val.toString());
				}
			}
		}
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();
		jBuilder = jBuilder.setPlatform(Platform.all());
		jBuilder.setAudience(Audience.alias(alias));

		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		String msgUUID = UUID.randomUUID().toString().trim().replaceAll("-", "");
		copyMessage.put("msgUUID", msgUUID);
		Message message = Message.newBuilder()
				.setTitle(copyMessage.getTitle())
				.setContentType(copyMessage.getContentType())
				.setMsgContent(copyMessage.getMsgContent())
				.addExtras(copyMessage)
				.build();
		
		jBuilder.setMessage(message);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive)
				.build());

		PushPayload pushPayload = jBuilder.build();
		
		PushResult pushResult=null;
		try{
			boolean isLogout = false;
			isLogout = isLogout(userName, null);
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
			}
//			pushResult = jPushClient.sendPush(pushPayload);
			return pushResult;
		}finally{
			//保存推送的消息记录
			try{
				if(!unimportant(pushMessage.getContentType())){
					alertInactiveAppSingle(StringUtils.isNotBlank(copyMessage.getMsgContent())?copyMessage.getMsgContent():copyMessage.getTitle(),timeToLive,alias,userName,null);
					MessagePushed messagePushed = new MessagePushed();
					//messagePushed.setUserName(MessagePushed.AUDIENCE_ALL);
					messagePushed.setAlias(alias);
					//messagePushed.setRegistrationId(registrationId);
					messagePushed.setTitle(pushMessage.getTitle());
					messagePushed.setMsgContent(pushMessage.getMsgContent());
					messagePushed.setContentType(pushMessage.getContentType());
					messagePushed.setExtras(JSON.toJSONString(copyMessage));
					messagePushed.setTransactionId(copyMessage.get("id"));
					messagePushed.setPushType(MessagePushed.PushType.CUSTOM_MESSAGE);
					messagePushed.setTimeToLive(timeToLive);
					messagePushed.setMsgStatus(MessagePushed.MsgStatus.UNREAD);
					if(pushResult!=null){
						messagePushed.setMsgId(String.valueOf(pushResult.msg_id));
						messagePushed.setSendno(String.valueOf(pushResult.sendno));
					}else{
						String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
						messagePushed.setMsgId(uuid);
						messagePushed.setSendno(uuid);
					}
					messagePushed.setUserType(this.userType);
					messagePushed.setAppType(this.appType);
					messagePushed.setMsgUUID(msgUUID);
					messagePushedService.save(messagePushed);
				}
			}catch(Exception e){
				logger.error("推送消息保存到数据库错误",e);
			}
		}
	}
	
	/**
	 * 根据userName进行单个推送,传入的是用户名 但最终会被转换为其MD5
	 * @param pushMessage
	 * @param timeToLive
	 * @param userName
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult pushCustomMsgByUserNameSingle(PushMessage pushMessage, Long timeToLive,String userName) throws APIConnectionException, APIRequestException{
//		String[] alias = null;
//		if(userName != null && userName.length>0){
//			alias =  new String[userName.length];
//			for(int i=0;i<userName.length;i++){
//				alias[i] = MD5Util.getMD5(userName[i]);
//			}
//		}
		return pushCustomMsgByAlias(pushMessage,timeToLive, MD5Util.getMD5(userName),userName);
	}
	
	/**
	 * 推送自定义消息时候由于前端APP(主要针对IOS用户)
	 * 没有启动APP导致自定义消息无法进入,调用该方法推送一个通知栏消息提醒用户,不进行存库
	 * @param msg
	 * @param timeToLive
	 * @param alias
	 * @param registrationIds
	 */
	private void alertInactiveApp(String msg,Long timeToLive,String []alias,String []registrationIds){
		if((alias == null || alias.length == 0) && (registrationIds == null || registrationIds.length == 0)){
			return;
		}
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();

		jBuilder.setPlatform(Platform.all());
		
		Audience.Builder audienceBuilder = Audience.newBuilder();
		if(alias != null && alias.length != 0){
			audienceBuilder.addAudienceTarget(AudienceTarget.alias(alias));
		}
		if(registrationIds != null && registrationIds.length != 0){
			audienceBuilder.addAudienceTarget(AudienceTarget.registrationId(registrationIds));
		}
		
		jBuilder.setAudience(audienceBuilder.build());

		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(msg).incrBadge(1).build())
				.addPlatformNotification(AndroidNotification.newBuilder().setAlert(msg).setTitle(msg).build())
				.build()
		);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		
		try{
			PushResult pushResult = jPushClient.sendPush(pushPayload);
			logger.info("自定义消息通知用户结果alertUnActiveApp:pushResult="+pushResult==null?"null":pushResult.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送自定义消息时候由于前端APP(主要针对IOS用户)
	 * 没有启动APP导致自定义消息无法进入,调用该方法推送一个通知栏消息提醒用户,不进行存库
	 * @param msg
	 * @param timeToLive
	 * @param alias
	 * @param registrationIds
	 */
	private void alertInactiveAppSingle(String msg,Long timeToLive,String alias,String userName,String registrationId){
		if(StringUtils.isBlank(alias) && StringUtils.isBlank(registrationId)){
			return;
		}
		JPushClient jPushClient = getJPushClient(appkey,masterSecret);
		PushPayload.Builder jBuilder = PushPayload.newBuilder();

		jBuilder.setPlatform(Platform.all());
		
		Audience.Builder audienceBuilder = Audience.newBuilder();
		if(StringUtils.isNotBlank(alias)){
			audienceBuilder.addAudienceTarget(AudienceTarget.alias(alias));
		}
		if(StringUtils.isNotBlank(registrationId)){
			audienceBuilder.addAudienceTarget(AudienceTarget.registrationId(registrationId));
		}
		
		jBuilder.setAudience(audienceBuilder.build());

		if (null == timeToLive || timeToLive < 0l) {
			timeToLive = 60 * 60 * 24l;
		} else if (timeToLive > 60 * 60 * 24 * 10l) {
			timeToLive = 60 * 60 * 24 * 10l;
		}
		jBuilder.setNotification(Notification.newBuilder()
				.addPlatformNotification(IosNotification.newBuilder().setAlert(msg).incrBadge(1).build())
				.addPlatformNotification(AndroidNotification.newBuilder().setAlert(msg).setTitle(msg).build())
				.build()
		);
		jBuilder.setOptions(Options.newBuilder().setApnsProduction(apnsProduction).setTimeToLive(timeToLive).build());
		PushPayload pushPayload = jBuilder.build();
		
		try{
			PushResult pushResult = null;
			boolean isLogout = false;
			if(StringUtils.isNotBlank(userName)){
				isLogout = isLogout(userName, null);
			}else{
				isLogout = isLogout(null, registrationId);
			}
			if(!isLogout){
				pushResult = jPushClient.sendPush(pushPayload);
				logger.info("自定义消息通知用户结果alertUnActiveApp:pushResult="+pushResult==null?"null":pushResult.toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isLogout(String userName,String registrationId){
		boolean isLogout = false;
		try {
			if(StringUtils.isNotBlank(userName)){
				MemberCachedDto memberCachedDto = redisService.get(MemberCachedDto.Prefix_Cache_Key_Member_Username 
						+ userName,MemberCachedDto.class);
				if(memberCachedDto != null){
					if(StringUtils.isNotBlank(memberCachedDto.getRegistrationId())){
						if(redisService.get(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId 
								+ memberCachedDto.getRegistrationId(),MemberCachedDto.class) == null){
							isLogout = true;
						}
					}
				}else{
					isLogout = true;
				}
			}else{
				if(StringUtils.isNotBlank(registrationId)){
					if(redisService.get(MemberCachedDto.Prefix_Cache_Key_Member_RegistrationId 
							+ registrationId,MemberCachedDto.class) == null){
						isLogout = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("消息推送 PushUntils.isLogout 异常", e);
		}
		return isLogout;
	}
}
