package com.arf.activity.parkrate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.arf.activity.parkrate.dao.IActivityRecordDao;
import com.arf.activity.parkrate.dto.ParkRateActivityDto;
import com.arf.activity.parkrate.entity.ActivityRecord;
import com.arf.activity.parkrate.entity.ActivityRecord.RedPackStatus;
import com.arf.activity.parkrate.entity.CommunityActivity;
import com.arf.activity.parkrate.service.IActivityRecordService;
import com.arf.activity.parkrate.service.ICommunityActivityService;
import com.arf.carbright.entity.AxdVouchers;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.carbright.service.IAxdVouchersService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.ParkRateRecordModelService;
import com.arf.core.service.SmsService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.HttpUtil;
import com.arf.core.util.weixin.WxUtils;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.propertymgr.entity.PropertyFeeBill;
import com.arf.propertymgr.entity.PropertyFeeBillOrder;
import com.arf.propertymgr.entity.PropertyFeeBillOrder.TradeStatus;
import com.arf.propertymgr.service.IPropertyFeeBillOrderService;
import com.arf.propertymgr.service.IPropertyFeeBillService;
import com.arf.thirdlogin.entity.ThirdPartyAccount;
import com.arf.thirdlogin.service.ThirdPartyAccountService;
import com.arf.wechat.entity.WXUser;
import com.arf.wechat.service.WXUserService;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;

@Service("activityRecordService")
public class ActivityRecordServiceImpl extends
		BaseServiceImpl<ActivityRecord, Long> implements IActivityRecordService {
	
	private Logger log = LoggerFactory.getLogger(ActivityRecordServiceImpl.class);
	
	@Resource(name = "activityRecordDaoImpl")
	private IActivityRecordDao activityRecordDaoImpl;
	
	@Resource(name = "communityActivityServiceImpl")
	private ICommunityActivityService communityActivityServiceImpl;
	
	@Resource(name = "thirdPartyAccountService")
	private ThirdPartyAccountService thirdPartyAccountService;
	
	@Resource(name = "parkRateRecordModelServiceImpl")
	private ParkRateRecordModelService parkRateRecordModelServiceImpl;
	
	@Resource(name = "axdVouchersServiceImpl")
	private IAxdVouchersService axdVouchersServiceImpl;
	
	@Resource(name = "axdVouchersRecordServiceImpl")
	private IAxdVouchersRecordService axdVouchersRecordServiceImpl;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;

	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityService;
	
	@Value("${Host_Domain}")
	public String Host_Domain;
	
	@Resource(name = "wxUserServiceImpl")
	private WXUserService wxUserService;
	
	@Resource(name = "parkRateRecordModelServiceImpl")
	private ParkRateRecordModelService parkRateRecordService;
	
	@Resource(name = "propertyFeeBillOrderService")
	private IPropertyFeeBillOrderService propertyFeeBillOrderService;
	
	@Resource(name = "propertyFeeBillService")
	private IPropertyFeeBillService propertyFeeBillService;
	
	private static final String WXTEMPLATE_MSG_ID = "HZw0RgZa4-b2sAW6GrcNS53b20YXvIbDEqnjazbOeQI";
	
	@Override
	protected BaseDao<ActivityRecord, Long> getBaseDao() {
		return activityRecordDaoImpl;
	}

	@Override
	public ParkRateActivityDto parkRateActivity(String userName,String outTradeNo) {
		String parkRateType = "月租费";
		String propertyType = "物业费";
		String activityType = "";
		
		//券和红包
		String appHasVouchersRedMsg = "首次缴纳%s成功，恭喜您获得一张洗车体验券和一次微信红包抽奖机会！点击<a href=\"%s\" style='color:blue;font-Size:16px;'>%s</a>立即参与！";
		//券
		String  appOnlyVouchersMsg  = "首次缴纳%s成功，恭喜您获得洗车代金券一张，到指定洗车店消费，用安心点扫码支付只需1元钱！详情请点击<a href=\"%s\" style='color:blue;font-Size:16px;'>%s</a>";
		//红包
		String appOnlyRedMsg= "首次缴纳%s成功，恭喜您获得一次微信红包抽奖机会！点击<a href=\"%s\" style='color:blue;font-Size:16px;'>%s</a>立即参与！";
		
		//券和红包
		String wxHasVouchersRedMsg = "首次缴纳%s成功，恭喜您获得一张洗车体验券和一次微信红包抽奖机会！点击立即参与！";
		//券
		String wxOnlyVouchersMsg  = "首次缴纳%s成功，恭喜您获得洗车代金券一张，到指定洗车店消费，用安心点扫码支付只需1元钱！详情请点击";
		//红包
		String wxOnlyRedMsg= "首次缴纳%s成功，恭喜您获得一次微信红包抽奖机会！点击立即参与！";
		
		//券和红包
		String smsHasVouchersRedMsg = "首次缴纳%s成功，恭喜您获得一张洗车体验券和一次微信红包抽奖机会！点击 %s  立即参与！";
		//券
		String  smsOnlyVouchersMsg  = "首次缴纳%s成功，恭喜您获得洗车代金券一张，到指定洗车店消费，用安心点扫码支付只需1元钱！详情请点击 %s  ";
		//红包
		String smsOnlyRedMsg= "首次缴纳%s成功，恭喜您获得一次微信红包抽奖机会！点击 %s  立即参与！";
		
		try {
			ActivityRecord activityRecordQuery = this.findByOutTradeNo(outTradeNo);
			if(activityRecordQuery != null){
				log.info(String.format("订单号已参加活动 userName = %s , outTradeNo = %s", 
						userName,outTradeNo));
				return new ParkRateActivityDto(-5,"订单号已参加活动");
			}
			
			String communityNumber = null;
			BigDecimal parkRateAmount = BigDecimal.ZERO;
			
			//1、判断用户是否首次参与月租缴费(表park_rate_record)
			if(outTradeNo.startsWith(OrderNumPrefixConstraint.PREFIX_PARKING_MONTH_RENT_FEE_PAY)){
				ParkRateRecordModel parkRateRecordModel = parkRateRecordService.selectByouttradeno(outTradeNo);
				if(parkRateRecordModel == null){
					log.info(String.format("月租缴费记录不存在 userName = %s , outTradeNo = %s", 
							userName,outTradeNo));
					return new ParkRateActivityDto(-1,"月租缴费记录不存在");
				}
				
				//查询是否首次
				List<Filter> filters = new ArrayList<Filter>();
				filters.add(new Filter("userName",Operator.eq,userName));
				filters.add(new Filter("out_trade_no",Operator.ne,outTradeNo));
				filters.add(new Filter("tradeStatus",Operator.eq,"9000"));
				List<ParkRateRecordModel> parkRateRecordModels = parkRateRecordModelServiceImpl.findList(null, filters, null);
				if(CollectionUtils.isNotEmpty(parkRateRecordModels)){
					log.info(String.format("不是首次参加月租缴费 userName = %s , outTradeNo = %s", 
							userName,outTradeNo));
					return new ParkRateActivityDto(-2,"不是首次参加月租缴费");
				}
				
				communityNumber = parkRateRecordModel.getCommunityNumber();
				parkRateAmount = parkRateRecordModel.getAmount();
				activityType = parkRateType;
			}
			//1、判断用户是否首次参与物业缴费(表park_rate_record)
			else if(outTradeNo.startsWith(OrderNumPrefixConstraint.PREFIX_PROPERTY_MGR_FEE_ORDER)){
				PropertyFeeBillOrder order = propertyFeeBillOrderService.findByOutTradeNo(outTradeNo);
				if(order == null){
					log.info(String.format("物业缴费记录不存在 userName = %s , outTradeNo = %s", 
							userName,outTradeNo));
					return new ParkRateActivityDto(-1,"物业缴费记录不存在");
				}
				//查询是否首次
				List<Filter> filters = new ArrayList<Filter>();
				filters.add(new Filter("userName",Operator.eq,userName));
				filters.add(new Filter("outTradeNo",Operator.ne,outTradeNo));
				filters.add(new Filter("tradeStatus",Operator.eq,TradeStatus.SUCCESS));
				List<PropertyFeeBillOrder> propertyFeeBillOrders = propertyFeeBillOrderService.findList(null, filters, null);
				if(CollectionUtils.isNotEmpty(propertyFeeBillOrders)){
					log.info(String.format("不是首次参加物业缴费 userName = %s , outTradeNo = %s", 
							userName,outTradeNo));
					return new ParkRateActivityDto(-2,"不是首次参加物业缴费");
				}
				parkRateAmount = order.getTotalFee();
				String billNos = order.getBillNos();
				if(StringUtils.isNotBlank(billNos)){
					String[] billNosArray = billNos.split(",");
					List<String> billNoList = new ArrayList<String>();
					for (String billNo : billNosArray) {
						billNoList.add(billNo);
					}
					List<PropertyFeeBill> propertyFeeBills = propertyFeeBillService.findByBillNos(billNoList);
					if(CollectionUtils.isNotEmpty(propertyFeeBills)){
						for (PropertyFeeBill propertyFeeBill : propertyFeeBills) {
							communityNumber = propertyFeeBill.getCommunityNumber();
							CommunityActivity communityActivity = communityActivityServiceImpl.findByCommunity(communityNumber);
							if(communityActivity != null){
								Integer communityActivityStatus = communityActivity.getStatus();
								Integer enableDripWashVoucher = communityActivity.getEnableDripWashVoucher();//是否开启洗车代金券
								Integer enableRedPkg = communityActivity.getEnableRedPkg();//是否开启红包发送
								if(communityActivityStatus != null && communityActivityStatus == 1
										&& 
									((enableDripWashVoucher != null && enableDripWashVoucher == 1)||(enableRedPkg != null && enableRedPkg == 1))){
									break;
								}
							}
							communityNumber = "";
						}
					}
					if(StringUtils.isBlank(communityNumber)){
						log.info(String.format("物业缴费未找到活动小区 userName = %s , outTradeNo = %s , communityNumber = %s", 
								userName,outTradeNo,communityNumber));
						return new ParkRateActivityDto(-4,"物业缴费未找到活动小区");
					}
				}
				activityType = propertyType;
			}
			
			//2、根据月租缴费活动表(pr_activity_community的vouchers_num关联p_axd_vouchers表)为其发放一张金额面值为0的点滴洗代金券(表r_axd_vouchers_record)
			CommunityActivity communityActivity = communityActivityServiceImpl.findByCommunity(communityNumber);
			if(communityActivity == null){
				log.info(String.format("小区未参加活动 userName = %s , outTradeNo = %s , communityNumber = %s", 
						userName,outTradeNo,communityNumber));
				return new ParkRateActivityDto(-3,"小区未参加活动");
			}
			Integer communityActivityStatus = communityActivity.getStatus();
			if(communityActivityStatus == null
					|| communityActivityStatus == 0){
				log.info(String.format("小区未参加活动 userName = %s , outTradeNo = %s , communityNumber = %s", 
						userName,outTradeNo,communityNumber));
				return new ParkRateActivityDto(-3,"小区未参加活动");
			}
			Integer enableDripWashVoucher = communityActivity.getEnableDripWashVoucher();//是否开启洗车代金券
			Integer enableRedPkg = communityActivity.getEnableRedPkg();//是否开启红包发送
			if(enableDripWashVoucher != 1
					&& enableRedPkg != 1){
				log.info(String.format("小区未参加活动 userName = %s , outTradeNo = %s , communityNumber = %s", 
						userName,outTradeNo,communityNumber));
				return new ParkRateActivityDto(-3,"小区未参加活动");
			}
			//3、生成一张洗车体验券
			String vouchersNum = communityActivity.getVouchersNum();
			AxdVouchersRecord vr = new AxdVouchersRecord();
			if(enableDripWashVoucher != null
					&& enableDripWashVoucher == 1){
				if(StringUtils.isNotBlank(vouchersNum)){
					AxdVouchers voucher = axdVouchersServiceImpl.findByVouchersNum(vouchersNum);
					if(voucher != null){
						vr.setVouchersType(voucher.getVouchersType());
						vr.setVouchersMoney(voucher.getVouchersMoney());
						vr.setVouchersSalesMoney(BigDecimal.ZERO);
						vr.setUsedStatus(AxdVouchersRecord.UsedStatus.Available.ordinal());
						vr.setEffectiveStartDate(new Date());
						if(voucher.getEffectiveDate() == 0){
							//永远不失效
							vr.setEffectiveEndDate((new GregorianCalendar(9999,11,31,0,0,0)).getTime());
						}else{
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.MINUTE, voucher.getEffectiveDate()==null?0:voucher.getEffectiveDate());
							vr.setEffectiveEndDate(cal.getTime());
						}
						vr.setVouchersNum(voucher.getVouchersNum());
						vr.setVouchersName(voucher.getVouchersName());
						vr.setUseRules(voucher.getUseRules());
						vr.setScope(voucher.getScope());
						vr.setUseScope(voucher.getUseScope());
						vr.setAxdUsed(voucher.getAxdUsed());
						vr.setWechatUsed(voucher.getWechatUsed());
						vr.setUserName(userName);
//						vr.setBusinessNum(businessNum);
						vr.setFeePayType(AxdVouchersRecord.FeePayType.Donation.ordinal());
						vr.setOutTradeNo(outTradeNo);
						vr.setFeePayStatus(AxdVouchersRecord.FeePayStatus.PaySuccess.ordinal());
						vr.setUseCity(voucher.getUseCity());
						vr.setUseBusinessNum(voucher.getUseBusinessNum());
						vr.setVouchersPic(voucher.getVouchersPic());
						vr.setUseAmount(voucher.getUseAmount());
						vr.setUseTime(null);
						vr.setVouchersSource(voucher.getVouchersSource());
						vr = axdVouchersRecordServiceImpl.save(vr);
					}
				}
				
			}
			
			//是否开启红包发送
			String wxOpenId = "";
			
			boolean isBoundWx = false;
			//判断是否绑定微信并关注微信公众号
			ThirdPartyAccount thirdPartyAccount = thirdPartyAccountService.findByOpenidOrUid(null,null,null,userName);
			if(thirdPartyAccount != null
					&& StringUtils.isNotBlank(thirdPartyAccount.getWxUnionid())){
				WXUser wxUser = wxUserService.findByUnionId(thirdPartyAccount.getWxUnionid());
				if(wxUser != null && StringUtils.isNotBlank(wxUser.getOpenId())){
					wxOpenId = wxUser.getOpenId();
					isBoundWx = true;
				}
				
			}
			double redAmout = 0d;
			if(enableRedPkg != null
					&& enableRedPkg == 1){
				//计算中奖金额
				Integer redPkgRate = communityActivity.getRedPkgRate();//红包中奖概率,eg. 10则中奖概率为10%
				String redPkgInterval = communityActivity.getRedPkgInterval();//中间区间，上下限通过英文逗号分隔
				BigDecimal redPkgMin = communityActivity.getRedPkgMin();//红包中奖最小金额
				BigDecimal redPkgMax = communityActivity.getRedPkgMax();//红包中奖最大金额
				double redPkgIntervalMin = Double.valueOf(redPkgInterval.split(",")[0]);
				double redPkgIntervalMax = Double.valueOf(redPkgInterval.split(",")[1]);
				//随机中奖
				if(redPkgRate != 0){//中奖概率不为0
					double reuslt = Math.random()*100;
					if(redPkgIntervalMin <= reuslt
							&& reuslt <= redPkgIntervalMax){
						//随机金额
						if(redPkgMin.doubleValue() == redPkgMax.doubleValue()
								&& redPkgMin.doubleValue() == 0d){//红包中奖金额为0
						}else if(redPkgMin.doubleValue() == redPkgMax.doubleValue()){
							redAmout = redPkgMin.doubleValue();
						}else{
							while(true){
								reuslt = Math.random()*redPkgMax.doubleValue();
								if(redPkgMin.doubleValue() <= reuslt
										&& reuslt <= redPkgMax.doubleValue()){
									redAmout = reuslt;
									break;
								}
							}
						}
					}
				}
			}
			ActivityRecord activityRecord = new ActivityRecord();
			activityRecord.setUserName(userName);
			activityRecord.setAxdVoucherNum(vouchersNum);
			activityRecord.setOpenId(wxOpenId);
			activityRecord.setRedPackReSent(0);
			activityRecord.setOutTradeNo(outTradeNo);
			activityRecord.setAxdVouchersRecordId(vr.getId());
			activityRecord.setCommunityNumber(communityNumber);
			//开启发红包
			if(enableRedPkg == 1){
				activityRecord.setRedPackStatus(RedPackStatus.NOT_RAFFLE);
				activityRecord.setRedPackAmount(new BigDecimal(redAmout));
			}else{
				activityRecord.setRedPackStatus(RedPackStatus.NOT_OBTAIN);
				activityRecord.setRedPackAmount(BigDecimal.ZERO);
			}
			activityRecord = this.save(activityRecord);
			//6、推送消息
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("userName", userName);
			data.put("activityRecordId", activityRecord.getId());
			String shotenUrl = getUrl(JSON.toJSONString(data));
			CommunityModel comunity = communityService.findByNumber(communityActivity.getCommunityNumber());
			String communityName = "";
			if(comunity != null){
				communityName = comunity.getCommunityName();
			}
			//红包（未开通代金券）
			if(enableDripWashVoucher == 0){
				String textMsg = String.format(appOnlyRedMsg,activityType,shotenUrl,shotenUrl);
				pushMessage(userName, textMsg, null);
				//绑定微信
				if(isBoundWx){
					//TODO 需要增加物业缴费微信模板信息
					//微信推送
					this.pushWxTemplateMsg(wxOpenId,shotenUrl,String.format(wxOnlyRedMsg,activityType),parkRateAmount,communityName,outTradeNo);
				}else{
					String smsMsg = String.format(smsOnlyRedMsg,activityType, shotenUrl);
					smsService.send(userName, smsMsg);
				}
			}
			//洗车券（未开通红包）
			else if(enableRedPkg == 0){
				String textMsg =  String.format(appOnlyVouchersMsg,activityType,shotenUrl,shotenUrl);
				pushMessage(userName, textMsg, null);
				if(isBoundWx){
					//TODO 需要增加物业缴费微信模板信息
					//微信推送
					this.pushWxTemplateMsg(wxOpenId,shotenUrl,String.format(wxOnlyVouchersMsg,activityType),parkRateAmount,communityName,outTradeNo);
				}else{
					String smsMsg = String.format(smsOnlyVouchersMsg,activityType,shotenUrl);
					smsService.send(userName, smsMsg);
				}
			}
			//洗车券、红包
			else{
				String textMsg = String.format(appHasVouchersRedMsg,activityType,shotenUrl,shotenUrl);
				pushMessage(userName, textMsg, null);
				//绑定微信
				if(isBoundWx){
					//微信推送
					this.pushWxTemplateMsg(wxOpenId,shotenUrl,String.format(wxHasVouchersRedMsg,activityType),parkRateAmount,communityName,outTradeNo);
				}else{
					String smsMsg = String.format(smsHasVouchersRedMsg,activityType,shotenUrl);
					smsService.send(userName,smsMsg);
				}
			}
			log.error(String.format("成功 userName = %s , outTradeNo = %s", 
					userName,outTradeNo));
			return new ParkRateActivityDto(0,"成功");
		} catch (Exception e) {
			log.error(String.format("月租缴费成功参与活动公共接口方法：程序异常 userName = %s , outTradeNo = %s", 
					userName,outTradeNo),e);
			return new ParkRateActivityDto(500,"程序异常");
		}
	}
	
	private void pushWxTemplateMsg(String reOpenId,String shortenUrl,String remarkMsg,BigDecimal parkRateAmount,String communityName,String outTradeNo){
		try{
			String accessToken = WxUtils.getInstance().getAccessToken(null);
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
			String color = "#173177";
			Map<String,Object> msg = new HashMap<String,Object>();
			msg.put("touser", reOpenId);
			msg.put("template_id", WXTEMPLATE_MSG_ID);
			msg.put("url", shortenUrl);
			
			String titleType = "";
			if(outTradeNo.startsWith(OrderNumPrefixConstraint.PREFIX_PROPERTY_MGR_FEE_ORDER)){ //物业缴费
				titleType = "物业缴费";
			}else if(outTradeNo.startsWith(OrderNumPrefixConstraint.PREFIX_PARKING_MONTH_RENT_FEE_PAY)){//月租缴费
				titleType = "月租缴费";
			}
			
			Map<String,Object> data = new HashMap<String,Object>();
			
			Map<String,Object> first = new HashMap<String,Object>();
			first.put("value", "恭喜您," + titleType + "成功!");
			first.put("color", color);
			
			data.put("first", first);
			
			Map<String,Object> orderProductName = new HashMap<String,Object>();
			orderProductName.put("value", titleType + "-" + communityName);
			orderProductName.put("color", color);
			
			data.put("orderProductName", orderProductName);
			
			Map<String,Object> orderMoneySum = new HashMap<String,Object>();
			if(parkRateAmount != null){
				parkRateAmount = parkRateAmount.setScale(2, RoundingMode.DOWN);
				orderMoneySum.put("value", parkRateAmount.toString());
			}else{
				orderMoneySum.put("value", "-");
			}
			orderMoneySum.put("color", color);
			
			data.put("orderMoneySum", orderMoneySum);
			
			Map<String,Object> Remark = new HashMap<String,Object>();
			Remark.put("value", remarkMsg);
			Remark.put("color", color);
			
			data.put("Remark", Remark);
			
			msg.put("data", data);
			
//			{
//				   "touser":"o0bjkt6H-Aty-HwL01NNlYlt7aZw",
//				   "template_id":"HZw0RgZa4-b2sAW6GrcNS53b20YXvIbDEqnjazbOeQI",
//				   "url":"http://weixin.qq.com/download",         
//				   "data":{
//				           "first": {
//				               "value":"恭喜你购买成功！",
//				               "color":"#173177"
//				           },
//				           "orderProductName":{
//				               "value":"巧克力",
//				               "color":"#173177"
//				           },
//				           "orderMoneySum": {
//				               "value":"39.8元",
//				               "color":"#173177"
//				           },
//				           "Remark": {
//				               "value":"2014年9月22日",
//				               "color":"#173177"
//				           }
//				   }
//				}
			String resp = HttpUtil.sendHttpsPOST(url, JSON.toJSONString(msg));
			log.info("在线缴费发送微信模板消息结果"+resp);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("在线缴费发送微信模板消息异常",e);
		}
	}
	
	/**
	 * ${HOST_DOMAIN}+"/activity/parkrate/parseShortLink.jhtml?data="+com.arf.core.util.DesUtil.encrypt((data, ActivityRecord.class.getName())
	 * 将上面的数据转换为短连接,
	 * 其中data为{userName,activityRecordId}的JSON字符串
	 * com.arf.core.util.ShortenUrl.shortenUrl(String)
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private String getUrl(String data) throws Exception{
		String arfDefaultUrl = Host_Domain + "/activity/parkrate/parseShortLink.jhtml?data=";
		String arfProcessUrl = arfDefaultUrl + URLEncoder.encode(com.arf.core.util.DesUtil.encrypt(data, ActivityRecord.class.getName()),"utf-8");
		return com.arf.core.util.ShortenUrl.shortenUrl(arfProcessUrl);
	}

	public void pushMessage(final String userName, final String content, final Map<String, String> map) {
		ThreadPoolFactory.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				try{
					PushUntils pushUntils = PushUntils.getUserPushUntils();
					Member member = memberService.findByUsername(userName);
					if (member != null && member.getCurrentEquipment() != null && member.getCurrentEquipment() == 1) {
						try {
							PushResult res = pushUntils.pushNotificationMsgToAndroidUser(member.getUsername(), "安心点", content, map,null);
						} catch (APIConnectionException e) {
							e.printStackTrace();
						} catch (APIRequestException e) {
							e.printStackTrace();
						}
					}
					// 设备是ios
					if (member != null &&  member.getCurrentEquipment() != null && member.getCurrentEquipment() == 2) {
						try {
							PushResult res = pushUntils.pushNotificationMsgToIOSUser(member.getUsername(), content, map, null);
						} catch (APIConnectionException e) {
							e.printStackTrace();
						} catch (APIRequestException e) {
							e.printStackTrace();
						}
					}
				}catch(Exception e){
					log.error("推送消息出现异常",e);
				}
			}
		});
	}

	@Override
	public ActivityRecord findByOutTradeNo(String outTradeNo) {
		return activityRecordDaoImpl.findByOutTradeNo(outTradeNo);
	}
	
}
