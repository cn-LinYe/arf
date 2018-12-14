/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CommunityModelDao;
import com.arf.core.dao.MemberDao;
import com.arf.core.dao.RedRecordModelDao;
import com.arf.core.dao.UnionIDModelDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.RedRecordModel;
import com.arf.core.entity.UnionIDModel;
import com.arf.core.entity.VipRecordInformationModel;
import com.arf.core.oldws.wx.entity.RedEnvelopeResponse;
import com.arf.core.oldws.wx.util.RedEnvelopeUtils;
import com.arf.core.oldws.wx.util.WxRedEnvelopeApisImpl;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.RedRecordModelService;
import com.arf.core.service.SmsService;
import com.arf.core.service.VipRecordInformationModelService;
import com.arf.core.service.WxRedLogService;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.MStringUntils;


/**
 * Service - 发送红包纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Service("redRecordModelServiceImpl")
public class RedRecordModelServiceImpl extends BaseServiceImpl<RedRecordModel, Long> implements RedRecordModelService {

	private static Logger log = LoggerFactory.getLogger(RedRecordModelServiceImpl.class);
	
	@Resource(name = "redRecordModelDaoImpl")
	private RedRecordModelDao redRecordModelDao;
	
	@Resource(name = "vipRecordInformationModelServiceImpl")
	private VipRecordInformationModelService vipRecordInformationModelService;
	
	@Resource(name = "unionIDModelDaoImpl")
	private UnionIDModelDao unionIDModelDao;
	
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
	@Resource(name = "communityModelDaoImpl")
	private CommunityModelDao communityDao;
	
	@Resource(name = "wxRedLogServiceImpl")
	private WxRedLogService wxRedLogService;
	
	@Override
	protected BaseDao<RedRecordModel, Long> getBaseDao() {
		return redRecordModelDao;
	}

	@Override
	public List<RedRecordModel> sellectByStatus() {
		return redRecordModelDao.sellectByStatus();
	}

	@Override
	public RedRecordModel findByOutTradeNo(String outTradeNo) {
		return redRecordModelDao.findByOutTradeNo(outTradeNo);
	}
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;

	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	/**
	 * 针对同一用户发放红包过多做告警处理
	 * @param openId
	 * @return true 正常;false-异常 并告警
	 */
	private boolean reportEmergencyAtferSent(final String openId,final VipRecordInformationModel record){
		Integer sentCount = null;
		try{
			sentCount = redisService.get("RedRecordModelServiceImpl.reportEmergency:" + openId,Integer.class);
			if(sentCount == null){
				sentCount = 0;
				return true;
			}
			final int finalSentCount = sentCount;
			if(sentCount > 4){
				ThreadPoolFactory.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						smsService.send("13242034701", "警告!微信用户("+openId+","+record.getUser_name()+")于"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"发红包数已超"+finalSentCount+"请及时查看日志并处理");
						smsService.send("18666971335", "警告!微信用户("+openId+","+record.getUser_name()+")于"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"发红包数已超"+finalSentCount+"请及时查看日志并处理");
						smsService.send("13072877897", "警告!微信用户("+openId+","+record.getUser_name()+")于"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"发红包数已超"+finalSentCount+"请及时查看日志并处理");
					}
				});
				return false;
			}else if(sentCount >= 2){
				ThreadPoolFactory.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						smsService.send("13242034701", "警告!微信用户("+openId+","+record.getUser_name()+")于"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"发红包数已超"+finalSentCount+"请及时查看日志并处理");
					}
				});
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			log.error("生成微信发红包告警失败,openId=" + openId,e);
			return true;
		}finally {
			if(sentCount == null){
				sentCount = 1;
			}else{
				sentCount = sentCount + 1;
			}
			redisService.set("RedRecordModelServiceImpl.reportEmergency:" + openId,sentCount,2 * 24 * 60 * 60);
		}
	}
	
	@Override
	public boolean sendRedPkg(String unionId, String openId) {
		// 1.找到vip开通充值记录VipRecordInformation
		List<VipRecordInformationModel> vipRecords = vipRecordInformationModelService.selectNotSendRedPkgByUnionID(unionId);
		log.info("查询到的未返红包充值记录为:"+(CollectionUtils.isEmpty(vipRecords)?"[]":JSON.toJSONString(vipRecords)));
		if (CollectionUtils.isEmpty(vipRecords)) {
			return true;
		}

		for (VipRecordInformationModel record : vipRecords) {
			RedEnvelopeResponse redEnvelopeResponse = null;
			try{
				String userName = record.getUser_name();
				String outTreadeNo = record.getOut_trade_no();
				if (StringUtils.isBlank(userName) || StringUtils.isBlank(outTreadeNo)) {
					continue;
				}

				// 2.找到用户关注记录UnionIdModel
				UnionIDModel unionIdModel = unionIDModelDao.selectByOpenId(openId);
				log.info("查询到的用户关注信息记录为:"+(unionIdModel==null?"[]":JSON.toJSONString(unionIdModel)));
				if (unionIdModel == null) {
					continue;
				}
				// 3.两者都有就开始发红包
				// 用户是否存在
				Member member = memberDao.findByUsername(record.getUser_name());
				if (member == null) {
					continue;
				}
				// 用户小区编号是否存在
				if (StringUtils.isBlank(member.getCommunityNumber())) {
					continue;
				}

				RedRecordModel redRecordModel = this
						.findByOutTradeNo(outTreadeNo);
				if(redRecordModel != null && ("RECEIVED".equalsIgnoreCase(redRecordModel.getStatus()) || "SENT".equalsIgnoreCase(redRecordModel.getStatus())|| "SENDING".equalsIgnoreCase(redRecordModel.getStatus()))){
					log.info(outTreadeNo + "已经发送过红包了...........................");
					continue;
				}
				
				// 获取相关小区信息
				List<CommunityModel> communityModels = communityDao.checkByCommunity_number(member.getCommunityNumber());
				CommunityModel communityModel = (CollectionUtils.isEmpty(communityModels) ? null : communityModels.get(0));
				if (communityModel == null) {
					continue;
				}

				if (communityModel.getIsRed() == 1) {
					int amount = record.getTotal_fee().multiply(new BigDecimal(100)).intValue();
					// 发红包
					WxRedEnvelopeApisImpl wxRedEnvelopeApisImpl = new WxRedEnvelopeApisImpl();
					redEnvelopeResponse = wxRedEnvelopeApisImpl.sendRedEnvelope(unionIdModel.getOpenid(), amount,RedEnvelopeUtils.createBillNo(),"成功开启vip奖励","开闸日活动");
					if(redEnvelopeResponse != null){
						log.info("发送红包响应:\n"
								+ JSON.toJSONString(redEnvelopeResponse));
					}else{
						log.info("发送红包响应:\n"
								+"null");
					}
					// 添加红包处理结果
//					WxRedLog wxlog = new WxRedLog();
//					if (redEnvelopeResponse != null) {
//						wxlog.setRedrecord(redEnvelopeResponse.getMch_billno());
//						wxlog.setContent(redEnvelopeResponse.toString());
//						wxlog.setResultcode(redEnvelopeResponse.getResult_code());
//						wxlog.setReturncode(redEnvelopeResponse.getReturn_code());
//						wxlog.setReturnmsg(redEnvelopeResponse.getReturn_msg());
//						wxlog.setUsername(record.getUser_name());
//					} else {
//						wxlog.setResultcode("FAIL");
//						wxlog.setReturncode("FAIL");
//						wxlog.setReturnmsg("红包未发送成功");
//					}
					// 成功
					// 返回状态码 return_code SUCCESS/FAIL
					// 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
					// 业务结果 result_code 是 SUCCESS String(16) SUCCESS/FAIL
					if ("SUCCESS".equalsIgnoreCase(redEnvelopeResponse.getReturn_code())
							&& "SUCCESS".equalsIgnoreCase(redEnvelopeResponse.getResult_code())) {
						// 更新记录信息isRed
						record.setIsRed(0);
						vipRecordInformationModelService.update(record);
						// 保存红包记录
						if(redRecordModel != null){
							log.info("发红包时查询到的RedRecordModel--->"+JSON.toJSONString(redRecordModel));
						}else{
							log.info("发红包时查询到的RedRecordModel--->NULL");
						}
						
						if (redRecordModel == null) {
							redRecordModel = new RedRecordModel();
							redRecordModel.setUser_name(record.getUser_name());
							redRecordModel.setRedRecord(redEnvelopeResponse.getMch_billno());
							redRecordModel.setAmount(amount);
							redRecordModel.setOpenId(unionIdModel.getOpenid());
							redRecordModel.setCreateTime(redEnvelopeResponse.getSend_time());
							redRecordModel.setOut_trade_no(record.getOut_trade_no());
							redRecordModel.setStatus("SENT");
							Integer propertyNumber = null;
					    	Integer branchId = null;
					    	if(MStringUntils.isNotEmptyOrNull(member.getCommunityNumber())){
						    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(member.getCommunityNumber());
						    	if(communityList != null && communityList.size()>0){
						    		propertyNumber = communityList.get(0).getProperty_number();
						    		branchId = communityList.get(0).getBranchId();
						    	}
					    	}
					    	redRecordModel.setCommunityNumber(member.getCommunityNumber());
					    	redRecordModel.setPropertyNumber(propertyNumber);
					    	redRecordModel.setBranchId(branchId);
							
							this.save(redRecordModel);
						} else {
							redRecordModel.setCreateTime(redEnvelopeResponse.getSend_time());
							this.update(redRecordModel);
						}
						reportEmergencyAtferSent(openId, record);
					}
					//wxRedLogService.save(wxlog);
				}
			}catch(Exception e){
				e.printStackTrace();
				 log.error("发送红宝时出现异常,用户支付信息(VipRecordInformationModel)【"+record+"】, "
				 		+ "查询红包时微信响应:【"+(redEnvelopeResponse == null?"null":JSON.toJSONString(redEnvelopeResponse))+"】",e);
			}
		}
		return true;	
	}
 
}