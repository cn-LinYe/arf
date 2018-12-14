package com.arf.crowdfunding.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.arf.core.AppMessage;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CommunityModelDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.MemberService;
import com.arf.core.service.SmsService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.crowdfunding.dao.ZcEntrepreneurialCommunityDao;
import com.arf.crowdfunding.dao.ZcShopkeeperPartnerOrderDao;
import com.arf.crowdfunding.entity.ZcEntrepreneurialCommunity;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerOrder;
import com.arf.crowdfunding.service.ZcEntrepreneurialCommunityService;
import com.arf.crowdfunding.service.ZcOrderPaymentService;
import com.arf.crowdfunding.service.ZcShopkeeperPartnerOrderService;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.payment.service.PaymentCallbackService;
import com.arf.payment.service.RegisterPaymentCallBackService;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

/**
 * 支付宝 微信支付后回调修改订单状态
 * @author LW on 2016-06-23
 * @version 1.0
 *
 */
@Service("zcOrderPaymentServiceImpl")
public class ZcOrderPaymentServiceImpl extends BaseServiceImpl<ZcShopkeeperPartnerOrder, Long> implements ZcOrderPaymentService,PaymentCallbackService {

	@Resource(name="zcShopkeeperPartnerOrderDao")
	private ZcShopkeeperPartnerOrderDao zcShopkeeperPartnerOrderDao;
	@Resource(name="zcEntrepreneurialCommunityDao")
	private ZcEntrepreneurialCommunityDao zcEntrepreneurialCommunityDao;
	@Resource(name = "communityModelDaoImpl")
	private CommunityModelDao communityModelDao;
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	@Resource(name="memberServiceImpl")
	private MemberService memberService;
	@Resource(name ="redisService")
	private RedisService redisService;
	@Resource(name ="zcShopkeeperPartnerOrderServiceImpl")
	private ZcShopkeeperPartnerOrderService zcShopkeeperPartnerOrderService;
	@Resource(name= "zcEntrepreneurialCommunityServiceImpl")
	private ZcEntrepreneurialCommunityService zcEntrepreneurialCommunityService;
	
	private final String ADS_AREA_SETTINGS_PROGECT_NAME="AdvertisementsJson:AdvertisementsProjectMap";	
	private static Logger log = LoggerFactory.getLogger(ZcOrderPaymentServiceImpl.class);
	
	@Override
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData) {
		
		log.info("微信支付完成回调开始，订单号："+callbackData.getOut_trade_no());
		boolean isHandle = zcShopkeeperPartnerOrderDao.isHandle(callbackData.getOut_trade_no());
		if(isHandle)
			return true;
		else
			return updateOrderStatus(callbackData.getOut_trade_no(),1);
	}

	@Override
	public boolean alipayCallback(AliPaymentCallBackData callbackData) {
		
		log.info("支付宝支付完成回调开始，订单号："+callbackData.getOut_trade_no());
		boolean isHandle = zcShopkeeperPartnerOrderDao.isHandle(callbackData.getOut_trade_no());
		if(isHandle)
			return true;
		else
			return updateOrderStatus(callbackData.getOut_trade_no(),2);
	}

	
	protected boolean updateOrderStatus(String orderNo,int payMode){ 
		
		boolean size = zcShopkeeperPartnerOrderService.saveShopkeeperPaymentMessage(orderNo, payMode) >0 ?true:false;		
		
		
		final ZcShopkeeperPartnerOrder zspo = zcShopkeeperPartnerOrderDao.getOrderMessage(orderNo,1);
		final List<CommunityModel> list = communityModelDao.checkByCommunity_number(zspo.getCommunityNumber());
		
		if(zspo != null && size){
			//当申请成为店主成功时
			if(zspo.getShopkeeperPartner() == 0){
				
				log.info("修改订单状态成功，订单号："+orderNo);
				
				//当有店主支付成功时 去除首页成为店主广告
				Map<byte[], byte[]> map=new HashMap<byte[], byte[]>();				
	            map.put(zspo.getCommunityNumber().getBytes(), ("ZC10002:projectid="+zspo.getProjectId()+"").getBytes());
	            redisService.hMSet(ADS_AREA_SETTINGS_PROGECT_NAME, map);				
				//修改项目状态
	            zcEntrepreneurialCommunityService.updateStatus(zspo.getCommunityNumber(),zspo.getProjectId(),1);
				//对该项目的店主申请订单全部更新为已取消
				zcShopkeeperPartnerOrderService.updateShopKeeperOrderStatus(zspo.getCommunityNumber(),zspo.getProjectId());
				
				log.info("修改订单状态成功，申请成为店主，并开始推送消息 订单号："+orderNo);
				//当申请成为店主成功时 分别推送消息				
				ThreadPoolFactory.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						
						//给店主发送短信
						if(!StringUtils.isBlank(zspo.getMobile())){
							log.info("修改订单状态成功，申请成为店主，给店主推送消息");
							smsService.send(zspo.getMobile(), "恭喜您成为"+list.get(0).getCommunityName()+" 服务店准店主。赶快介绍小区内的亲朋好友参加创业项目，成为您的合伙人吧！");
						}
						try {
							//给地区负责人发送短信
							final String principalMobile = communityModelDao.getPrincipalMobile(zspo.getCommunityNumber());
							if(!StringUtils.isBlank(principalMobile)){
								log.info("修改订单状态成功，申请成为店主，给地区负责人发短信");
								smsService.send(principalMobile,""+zspo.getShopkeeperPartnerName()+"成为"+list.get(0).getCommunityName()+"服务店准店主，电话 "+zspo.getMobile()+"");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//给店主推送信息	
						try {
							Map<String,String> msgMap = new HashMap<String,String>();
							msgMap.put("extras", "{\"msgType\":1,\"shopName\":\""+list.get(0).getCommunityName()+"\"}");
							log.info("修改订单状态成功，申请成为店主，给店主推送消息");
							PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(zspo.getShopkeeperPartnerUser(), "恭喜您成为准店主", "恭喜您成为"+list.get(0).getCommunityName()+" 服务店准店主。赶快介绍小区内的亲朋好友参加创业项目，成为您的合伙人吧！", msgMap, null);
							PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(zspo.getShopkeeperPartnerUser(), "恭喜您成为"+list.get(0).getCommunityName()+" 服务店准店主。赶快介绍小区内的亲朋好友参加创业项目，成为您的合伙人吧！", msgMap, null);
						} catch (APIConnectionException | APIRequestException e) {
							e.printStackTrace();
						}
						
						try {
							//查找小区的所有用户
							final List<Member> memberList = memberService.findByCommunityNumber(zspo.getCommunityNumber());
							final Map<String,String> msgMapCom = new HashMap<String,String>();
							msgMapCom.put("extras", "{\"msgType\":2,\"projectId\":\""+zspo.getProjectId()+"\"}");
							log.info("修改订单状态成功，申请成为店主，给小区所有人推送消息");
							for(final Member m : memberList){
								if(!StringUtils.isBlank(m.getUsername()) && !m.getUsername().equals(zspo.getShopkeeperPartnerUser())){
									PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(m.getUsername(), "快来成为合伙人", list.get(0).getCommunityName()+" 服务店已有准店主。赶快来参加创业项目，成为合伙人吧！", msgMapCom, null);
									PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(m.getUsername(), list.get(0).getCommunityName()+" 服务店已有准店主。赶快来参加创业项目，成为合伙人吧！", msgMapCom, null);
								}
							}	
							
						} catch (APIConnectionException | APIRequestException e) {
							e.printStackTrace();
						}
						
					}
				});				
				
			}
			//当申请成为合伙人时
			else{
				log.info("修改订单状态成功，申请成为合伙人， 订单号："+orderNo);
				//当申请成为合伙人成功时 分别推送消息
				ThreadPoolFactory.getInstance().addTask(new Runnable() {					
					@Override
					public void run() {
						//给此合伙人发送短信
						if(!StringUtils.isBlank(zspo.getMobile())){
							log.info("修改订单状态成功，申请成为合伙人， 给合伙人发送短信");
							smsService.send(zspo.getMobile(), "恭喜您成为"+list.get(0).getCommunityName()+" 服务店合伙人。赶快介绍小区内的亲朋好友加入创业项目，与您一道成为合伙人吧！");
						}
						//给合伙人推送信息	
						try {
							Map<String,String> msgMap = new HashMap<String,String>();
							msgMap.put("extras", "{\"msgType\":3,\"shopName\":\""+list.get(0).getCommunityName()+"\"}");
							log.info("修改订单状态成功，申请成为合伙人， 给合伙人推送消息");
							PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(zspo.getShopkeeperPartnerUser(), "众筹成功", "恭喜您成为"+list.get(0).getCommunityName()+" 服务店合伙人。赶快介绍您在小区内的亲朋好友参加创业项目，与您一道成为合伙人吧！", msgMap, null);
							PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(zspo.getShopkeeperPartnerUser(), "恭喜您成为"+list.get(0).getCommunityName()+" 服务店合伙人。赶快介绍您在小区内的亲朋好友参加创业项目，与您一道成为合伙人吧！", msgMap, null);
						} catch (APIConnectionException | APIRequestException e) {
							e.printStackTrace();
						}
						//给店主发送短信
						String shopkeeperMobile = zcShopkeeperPartnerOrderDao.getShopkeeperMobile(zspo.getProjectId(),zspo.getCommunityNumber());
						if(StringUtils.isBlank(zspo.getShopkeeperPartnerName()) || StringUtils.isBlank(zspo.getMobile())){
							smsService.send(shopkeeperMobile,"有人成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元");
						}else{
							smsService.send(shopkeeperMobile,""+zspo.getShopkeeperPartnerName()+"成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元，电话 "+zspo.getMobile()+"");
						}
						String shopkeeperAcount = zcShopkeeperPartnerOrderDao.getShopkeeperAccount(zspo.getCommunityNumber(),zspo.getProjectId());
						//给店主推送APP消息
						if(StringUtils.isBlank(zspo.getShopkeeperPartnerName()) || StringUtils.isBlank(zspo.getMobile())){
							log.info("修改订单状态成功，申请成为合伙人， 给店主推送消息");
							try {
								Map<String,String> msgMap = new HashMap<String,String>();
								msgMap.put("extras", "{\"msgType\":4}");
								PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(shopkeeperAcount, "有合伙人加入", "有人成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元。", msgMap, null);
								PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(shopkeeperAcount,  "有人成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元。", msgMap, null);
							} catch (APIConnectionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (APIRequestException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							try {
								Map<String,String> msgMap = new HashMap<String,String>();
								msgMap.put("extras", "{\"msgType\":4}");
								PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(shopkeeperAcount, "有合伙人加入", ""+zspo.getShopkeeperPartnerName()+"成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元，电话 "+zspo.getMobile()+"", msgMap, null);
								PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(shopkeeperAcount, ""+zspo.getShopkeeperPartnerName()+"成为"+list.get(0).getCommunityName()+"服务店合伙人，支持金额"+zspo.getCrowdfundingAmount()+"元，电话 "+zspo.getMobile()+"", msgMap, null);
							} catch (APIConnectionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (APIRequestException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				
				
				ZcEntrepreneurialCommunity crowdMoney = zcEntrepreneurialCommunityDao.getTotalSharesNum(zspo.getCommunityNumber(),zspo.getProjectId());					
				//计算出众筹总股数
				int totalShareNum = (crowdMoney.getPartnerAmount().divide(crowdMoney.getLeastPartnerAmount(),0,BigDecimal.ROUND_HALF_EVEN)).intValue();
				//再次获取已经售出的金额
				BigDecimal againOrderedMoneyNum = zcShopkeeperPartnerOrderDao.getOrderedShareNum(zspo.getCommunityNumber(),zspo.getProjectId());
				//计算出已经售出的众筹股数
				int againTotalOrderSharesNum = (againOrderedMoneyNum.divide(crowdMoney.getLeastPartnerAmount(),0,BigDecimal.ROUND_HALF_EVEN)).intValue();		
				//当众筹已满时 修改众筹项目状态
				if(againTotalOrderSharesNum == totalShareNum){
					
					//当众筹满时 去除首页成为合伙人广告						
		            redisService.hdel(ADS_AREA_SETTINGS_PROGECT_NAME, zspo.getCommunityNumber());
					//修改项目状态
		            zcEntrepreneurialCommunityService.updateStatus(zspo.getCommunityNumber(),zspo.getProjectId(),2);
					//对申请成为该项目合伙人的订单全部设置为已取消
					zcShopkeeperPartnerOrderService.updateParenterOrderStatus(zspo.getCommunityNumber(),zspo.getProjectId());
					log.info("众筹满时，开始推送消息");
					//当众筹满时 分别推送消息
					ThreadPoolFactory.getInstance().addTask(new Runnable() {
						
						@Override
						public void run() {
							String shopkeeperAcountM = zcShopkeeperPartnerOrderDao.getShopkeeperAccount(zspo.getCommunityNumber(),zspo.getProjectId());	
							String shopkeeperMobile = zcShopkeeperPartnerOrderDao.getShopkeeperMobile(zspo.getProjectId(),zspo.getCommunityNumber());
							//给店主推送消息
							if(!StringUtils.isBlank(shopkeeperMobile)){
								smsService.send(shopkeeperMobile, "恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为服务店店主，稍后会有专业团队联系您，为您提供完善的培训与开业辅导。");
							}
							//给店主推送消息
							try {
								Map<String,String> msgMap = new HashMap<String,String>();
								msgMap.put("extras", "{\"msgType\":5}");
								log.info("众筹满时，开始给店主推送消息");
								PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(shopkeeperAcountM, "众筹项目成功", "恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为服务店店主，稍后会有专业团队联系您，为您提供完善的培训与开业辅导。", msgMap, null);
								PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(shopkeeperAcountM,"恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为服务店店主，稍后会有专业团队联系您，为您提供完善的培训与开业辅导。", msgMap, null);
							} catch (APIConnectionException | APIRequestException e) {
								e.printStackTrace();
							}
							
							List<ZcShopkeeperPartnerOrder> PartnerMobile = zcShopkeeperPartnerOrderDao.getAllPartnerMobile(zspo.getProjectId());
							log.info("众筹满时，开始给合伙人推送消息");
							for(final ZcShopkeeperPartnerOrder partner: PartnerMobile){
								//给项目所有合伙人发送短信
								if(!StringUtils.isBlank(partner.getMobile())){
									smsService.send(partner.getMobile(), "恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为合伙人，洗车券将在开店时发送到您的APP上，敬请期待。");
									
								}
								final Map<String,String> msgMapM = new HashMap<String,String>();
								msgMapM.put("extras", "{\"msgType\":6}");
								//给项目所有合伙人推送APP信息
								if(StringUtils.isNotBlank(partner.getShopkeeperPartnerUser())){
									try {
										if(StringUtils.isNotBlank(partner.getShopkeeperPartnerUser())){
											PushUntils.getUserPushUntils().pushNotificationMsgToAndroidUser(partner.getShopkeeperPartnerUser(), "众筹项目成功", "恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为合伙人，洗车券将在开店时发送到您的APP上，敬请期待。", msgMapM, null);
											PushUntils.getUserPushUntils().pushNotificationMsgToIOSUser(partner.getShopkeeperPartnerUser(), "恭喜您，"+list.get(0).getCommunityName()+"服务店招募成功啦！您正式成为合伙人，洗车券将在开店时发送到您的APP上，敬请期待。", msgMapM, null);
										}											
									} catch (APIConnectionException | APIRequestException e) {
										e.printStackTrace();
									}
								}
							}
						}
					});
				}	
			}
			
				
		}
		
		return size;
	}
	
	@Override
	protected BaseDao<ZcShopkeeperPartnerOrder, Long> getBaseDao() {
		
		return zcShopkeeperPartnerOrderDao;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			//注册本service为支付回调业务逻辑
			RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_CROW_FOUND, getClass());
		}
	}

	@Override
	public AppMessage walletAccountCallback(String outTradeNo, BigDecimal totalFee) {
		//do nothing
		return AppMessage.error("未注册的服务");
	}

	

}
