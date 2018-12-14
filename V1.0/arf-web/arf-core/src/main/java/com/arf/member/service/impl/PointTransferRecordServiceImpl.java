package com.arf.member.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.arf.axd.axdgift.entity.AxdGiftConfig.GiftType;
import com.arf.axd.axdgift.entity.AxdGiftConfig.RewardSource;
import com.arf.axd.axdgift.entity.AxdGiftRecord;
import com.arf.axd.axdgift.service.IAxdGiftRecordService;
import com.arf.base.PageResult;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dto.AxdGiftConfig;
import com.arf.core.dto.AxdGiftMemberCache;
import com.arf.core.dto.AxdGiftStatistic;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.oldws.PushMessage;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.BeanUtils;
import com.arf.member.dao.IPointTransferRecordDao;
import com.arf.member.dto.PointGiftDto;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.entity.PointTransferRecord.PointGiftBusinessType;
import com.arf.member.entity.PointTransferRecord.SubType;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.entity.VipLevel;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.member.service.IVipLevelService;
import com.arf.member.vo.PointGiftVo;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.review.entity.ServiceReview;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

@Service("pointTransferRecordServiceImpl")
public class PointTransferRecordServiceImpl extends BaseServiceImpl<PointTransferRecord, Long> implements IPointTransferRecordService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * sys_config 配置参数key,是否普通注册用户不通过开通ECC就可以直接升级到高级会员,为1表示没开通不可以升级,其它则可以
	 */
	public static final String Config_Key_Level0_Upgraded_ECC = "Level0_Not_Upgraded_ECC";

	@Resource(name = "pointTransferRecordDaoImpl")
	private IPointTransferRecordDao pointTransferRecordDaoImpl;
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelServiceImpl; 
	
	@Resource( name = "axdGiftRecordServiceImpl")
	private IAxdGiftRecordService axdGiftRecordServiceImpl;
	
	@Resource( name = "pointTransferRecordServiceImpl")
	private IPointTransferRecordService pointTransferRecordServiceImpl;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "vipLevelService")
	private IVipLevelService vipLevelService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Override
	protected BaseDao<PointTransferRecord, Long> getBaseDao() {
		return pointTransferRecordDaoImpl;
	}

	@Override
	public PageResult<PointTransferRecord> findListOrders(String accountNumber,PointTransferRecord.PointType pointType, int pageSize, int pageNo){
		return pointTransferRecordDaoImpl.findListOrders(accountNumber, pointType, pageSize, pageNo);
	}

	@Override
	public int findCount(Integer subType,String userName) {
		return pointTransferRecordDaoImpl.findCount(subType,userName);
	}

	@Override
	public PointGiftDto pointGiftByBusiness(PointGiftVo vo) {
		if(vo == null){
			return new PointGiftDto(false, null,null);
		}
		String userName = vo.getUserName();//用户名
		PointGiftBusinessType businessType = vo.getBusinessType();//业务类型
		if(StringUtils.isBlank(userName) || businessType == null){
			return new PointGiftDto(false, null,null);
		}
		//积分账户
		SMemberAccount sMemberAccount = sMemberAccountServiceImpl.findByUserNameUserTypeStatus(userName, (byte)SMemberAccount.Type.member.ordinal(),
				(byte)SMemberAccount.Status.usable.ordinal());
		//小区信息
		CommunityModel communityModel = null;
		String communityNumber = vo.getCommunityNumber();//小区编号
		if(StringUtils.isNotBlank(communityNumber)){
			communityModel = communityModelServiceImpl.findByNumber(communityNumber);
		}
		Date now = new Date();
		PointGiftDto pointGiftDto = new PointGiftDto();
		if(businessType == PointGiftBusinessType.ECC_SIGN){//签到
			pointGiftDto = pointGiftBySign(vo, sMemberAccount, communityModel, now);
		}else if(businessType == PointGiftBusinessType.ECC_AXD){//安心点
			pointGiftDto = pointGiftByAxd(vo, sMemberAccount, communityModel, now);
		}else if(businessType == PointGiftBusinessType.REVIEW){//评价
			pointGiftDto = pointGiftByReview(vo, sMemberAccount, communityModel, now);
		}else if(businessType == PointGiftBusinessType.PURCHASE){//购买
			pointGiftDto = pointGiftByPurchase(vo, sMemberAccount, communityModel, now);
		}
		return pointGiftDto;
	}
	
	/**
	 * 签到赠送积分
	 * @param vo
	 * @param sMemberAccount
	 * @param communityModel
	 * @param now
	 * @return
	 */
	private PointGiftDto pointGiftBySign(PointGiftVo vo,SMemberAccount sMemberAccount,CommunityModel communityModel,Date now) {
		String userName = vo.getUserName();//用户名
		String communityNumber = vo.getCommunityNumber();//小区编号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = sdf.format(now);
		AxdGiftConfig axdGiftConfig = vo.getAxdGiftConfig();//安心点积分赠送相关配置
		if(communityModel == null 
				|| axdGiftConfig == null){
			return new PointGiftDto(false, null,null);
		}
		Integer pointPool = axdGiftConfig.getPointPool();//赠送积分池, 为Integer. MAX_VALUE则不对小区做总积分数量限制 
		Integer rewardPoints = axdGiftConfig.getRewardPoints();//每天签到赠送积分
		Integer rewardSource = axdGiftConfig.getRewardSource();//积分来源平台还是积分池
		String  lastSignTime= null;//当天签到时间(格式:yyyy-MM-dd HH:mm:ss)
		
		
		//小区已赠送总额统计的缓存
		Map<String, String> communityAll = redisService.hgetAll(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber);
		AxdGiftStatistic axdGiftStatistic = null;
		if(communityAll != null && !communityAll.isEmpty()){
			axdGiftStatistic = JSON.parseObject(JSON.toJSONString(communityAll), AxdGiftStatistic.class);
		}
		//个人当天的缓存
		Map<String, String> memberAll = redisService.hgetAll(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName);
		AxdGiftMemberCache axdGiftMemberCache = null;
		if(!memberAll.isEmpty()){
			axdGiftMemberCache = JSON.parseObject(JSON.toJSONString(memberAll), AxdGiftMemberCache.class);
		}
		if(axdGiftMemberCache != null){
			lastSignTime=axdGiftMemberCache.getLastSignTime();
		}
		if (lastSignTime!=null) {
			return new PointGiftDto(true, 0,"今天已经签到过");
		}
		if (rewardSource==null) {
			return new PointGiftDto(true, 0,"签到成功此小区尚未配置积分赠送方式");
		}
		Integer platformAmount = rewardPoints;
		if (rewardSource == RewardSource.Platform.ordinal()) {
			platformAmount=0;
		}else if (rewardSource == RewardSource.IntegralPool.ordinal()) {
			Integer pointTotal = 0;//已赠送总积分
			if(axdGiftStatistic != null){
				pointTotal = axdGiftStatistic.getPointTotal();
			}
			if (pointPool==null || rewardPoints==null) {
				return new PointGiftDto(true, 0,"签到成功此小区尚未配置积分赠送方式");
			}
			if(pointPool <= 0 && rewardPoints <= 0){
				return new PointGiftDto(true, 0,"签到成功小区尚未配置赠送积分");
			}
			if (pointPool != Integer.MAX_VALUE||(pointTotal + rewardPoints > pointPool)) {
				return new PointGiftDto(true, 0,"签到成功小区赠送积分不足");
			}
		}
		if (rewardPoints != null && rewardPoints > 0) {
			//个人账户 update
			sMemberAccount.setPoint((sMemberAccount.getPoint()==null?0:sMemberAccount.getPoint()) + rewardPoints);
			sMemberAccountServiceImpl.update(sMemberAccount);
			
			//积分账户流转记录生成 save
			PointTransferRecord pointTransferRecord = new PointTransferRecord();
			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
			pointTransferRecord.setPointSub(rewardPoints);
			pointTransferRecord.setOperateTime(now);
			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
			pointTransferRecord.setComment("每日任务:签到");
			pointTransferRecord.setSubType((int)SubType.ECC_SIGN_Gift);
			pointTransferRecord.setCommunityNumber(communityNumber);
			Integer propertyNumber = communityModel.getProperty_number();//物业编号 
			Integer branchId = communityModel.getBranchId();//子公司编号 
			pointTransferRecord.setPropertyNumber(propertyNumber);
			pointTransferRecord.setBranchId(branchId);
			pointTransferRecordServiceImpl.save(pointTransferRecord);
			
			//赠送记录 save
			AxdGiftRecord axdGiftRecord = new AxdGiftRecord();
			axdGiftRecord.setUserName(userName);
			axdGiftRecord.setGiftAmout(new BigDecimal(0));
			axdGiftRecord.setGiftPoint(rewardPoints);
			axdGiftRecord.setGiftType(GiftType.Point.ordinal());
			axdGiftRecord.setCommunityNo(communityNumber);
			axdGiftRecord.setPropertyNumber(Integer.valueOf(communityModel.getProperty_number()).toString());
			axdGiftRecord.setBranchId(Integer.valueOf(communityModel.getBranchId()).toString());
			axdGiftRecord.setLicense(null);
			axdGiftRecord.setComment("每日任务:签到");
			axdGiftRecordServiceImpl.save(axdGiftRecord);
		}
		if(axdGiftStatistic == null){
			axdGiftStatistic = new AxdGiftStatistic();
			axdGiftStatistic.setCommunityNo(communityNumber);
			axdGiftStatistic.setAmountTotal(new BigDecimal(0));
			axdGiftStatistic.setPointTotal(platformAmount);
			Map<String, Object> mapStr = new HashMap<String, Object>();
			mapStr = BeanUtils.bean2Map(axdGiftStatistic);
			Map<byte[], byte[]> mapByte = new HashMap<byte[], byte[]>();
			Set<Entry<String, Object>>  setStr = mapStr.entrySet();
			for (Entry<String, Object> key : setStr) {
				mapByte.put(key.getKey().getBytes(), mapStr.get(key.getKey()).toString().getBytes());
			}
			redisService.hMSet(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber, mapByte);
		}else{
			redisService.hIncrBy(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber,
					AxdGiftStatistic.Key_PointTotal, platformAmount.longValue());
		}
		if(axdGiftMemberCache == null){
			axdGiftMemberCache = new AxdGiftMemberCache();
			axdGiftMemberCache.setUserName(userName);
			axdGiftMemberCache.setGiftCountOfToday(0);
			axdGiftMemberCache.setGiftAmountOfToday(new BigDecimal(0));
			axdGiftMemberCache.setGiftPointOfToday(0);
//			axdGiftMemberCache.setLastAxdTime(sdf.format(new Date()));
			axdGiftMemberCache.setLastSignTime(nowStr);//新增签到时间
			Map<String, Object> mapStr = new HashMap<String, Object>();
			mapStr = BeanUtils.bean2Map(axdGiftMemberCache);
			Map<byte[], byte[]> mapByte = new HashMap<byte[], byte[]>();
			mapStr.remove("lastAxdTime");
			Set<Entry<String, Object>>  setStr = mapStr.entrySet();
			for (Entry<String, Object> key : setStr) {
				mapByte.put(key.getKey().getBytes(), mapStr.get(key.getKey()).toString().getBytes());
			}
			redisService.hMSet(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, mapByte);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			long expiration = (cal.getTimeInMillis() - System.currentTimeMillis())/1000;
			redisService.setExpiration(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, expiration);
		}else{
			redisService.hset(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, AxdGiftMemberCache.Key_LastSignTime, nowStr);//更新时间
		}
		if (rewardPoints==null) {
			rewardPoints=0;
		}
		return new PointGiftDto(true, rewardPoints,"签到赠送积分成功"); 
	}
	
	/**
	 * 安心点赠送积分
	 * @param vo
	 * @param sMemberAccount
	 * @param communityModel
	 * @param now
	 * @return
	 */
	private PointGiftDto pointGiftByAxd(PointGiftVo vo,SMemberAccount sMemberAccount,CommunityModel communityModel,Date now) {
		String userName = vo.getUserName();//用户名
		String communityNumber = vo.getCommunityNumber();//小区编号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = sdf.format(now);
		AxdGiftConfig axdGiftConfig = vo.getAxdGiftConfig();//安心点积分赠送相关配置
		String license = vo.getLicense();//车牌号
		if(communityModel == null 
				|| axdGiftConfig == null 
				|| StringUtils.isBlank(license)){
			log.info(String.format(">>>>>:安心点赠送积分，参数错误，communityModel 或 axdGiftConfig 或 license 为空"));
			return new PointGiftDto(false, null,null);
		}
		Integer giftType = axdGiftConfig.getGiftType();//返还类型:0-积分,1-现金 ,2-积分现金混合,等
		Integer giftCountLimitOfDay = axdGiftConfig.getGiftCountLimitOfDay();//每天点击安心点有效赠送次数限制,为Integer. MAX_VALUE则不存在该限制
		Integer giftPointAmount = axdGiftConfig.getGiftPointAmount();//点击安心点返还的积分额度, 为0表示不赠送
		Integer giftPointLimitOfDay = axdGiftConfig.getGiftPointLimitOfDay();//每天点击安心点有效赠送积分总数限制, 为Integer. MAX_VALUE则不存在该限制
		Integer pointPool = axdGiftConfig.getPointPool();//赠送积分池, 为Integer. MAX_VALUE则不对小区做总积分数量限制
		
		
		//缓存 或 数据库存在小区相关配置
		if(giftType != null && (com.arf.axd.axdgift.entity.AxdGiftConfig.GiftType.Point.ordinal() == giftType
				|| com.arf.axd.axdgift.entity.AxdGiftConfig.GiftType.PointAndCash.ordinal() == giftType)){//积分或者积分现金混合
			if(giftPointAmount == null || giftPointAmount == 0){//点击安心点返还的积分额度, 为0表示不赠送
				log.info(String.format(">>>>>:安心点赠送积分，不赠送积分，giftPointAmount:%s",giftPointAmount));
				return new PointGiftDto(true, 0,"点击安心点不赠送积分");
			}
			//小区已赠送总额统计的缓存
			Map<String, String> communityAll = redisService.hgetAll(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber);
			AxdGiftStatistic axdGiftStatistic = null;
			if(!communityAll.isEmpty()){
				axdGiftStatistic = JSON.parseObject(JSON.toJSONString(communityAll), AxdGiftStatistic.class);
			}
			BigDecimal amountTotal = null;//已赠送总金额
			Integer pointTotal = null;//已赠送总积分
			if(axdGiftStatistic != null){
				amountTotal = axdGiftStatistic.getAmountTotal();
				pointTotal = axdGiftStatistic.getPointTotal();
			}
			//个人当天的缓存
			Map<String, String> memberAll = redisService.hgetAll(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName);
			AxdGiftMemberCache axdGiftMemberCache = null;
			if(!memberAll.isEmpty()){
				axdGiftMemberCache = JSON.parseObject(JSON.toJSONString(memberAll), AxdGiftMemberCache.class);
			}
			Integer giftCountOfToday = null;//当天的已赠送有效次数
			BigDecimal giftAmountOfToday = null;//当天的有效赠送现金总金额
			Integer giftPointOfToday = null;//当天的有效赠送积分总数
			String lastAxdTime = null;//当天最近一次安心点时间(格式:yyyy-MM-dd HH:mm:ss)
			if(axdGiftMemberCache != null){
				giftCountOfToday = axdGiftMemberCache.getGiftCountOfToday();
				giftAmountOfToday = axdGiftMemberCache.getGiftAmountOfToday();
				giftPointOfToday = axdGiftMemberCache.getGiftPointOfToday();
				lastAxdTime = axdGiftMemberCache.getLastAxdTime();
			}
			boolean isGift = false;
			//积分池不限制 或 已赠送积分为0 或 已赠送积分小于积分池
			if(pointPool != null && giftCountLimitOfDay != null && giftPointLimitOfDay != null){
				if(pointPool == Integer.MAX_VALUE
						|| pointPool > 0 && (axdGiftStatistic == null || pointTotal < pointPool)){
					if(giftCountLimitOfDay == Integer.MAX_VALUE  
							&& giftPointLimitOfDay == Integer.MAX_VALUE){//每天次数无限制 且 每天积分无限制
						isGift = true;
					}else if((giftCountLimitOfDay != Integer.MAX_VALUE && giftCountLimitOfDay > 0)  
							&& giftPointLimitOfDay == Integer.MAX_VALUE){//每天次数限制 且 每天积分无限制
						if(axdGiftMemberCache == null){//当天第一次
							isGift = true;
						}else if(giftCountOfToday < giftCountLimitOfDay){//次数比设置的少
							isGift = true;
						}
					}else if((giftPointLimitOfDay != Integer.MAX_VALUE && giftPointLimitOfDay != 0)
							&& giftCountLimitOfDay == Integer.MAX_VALUE){//每天次数无限制 且 每天积分限制
						if(axdGiftMemberCache == null){//当天第一次
							isGift = true;
						}else if(giftPointOfToday < giftPointLimitOfDay){//积分比设置的少
							isGift = true;
						}
					}else if((giftCountLimitOfDay != Integer.MAX_VALUE && giftCountLimitOfDay > 0)
							&& (giftPointLimitOfDay != Integer.MAX_VALUE && giftPointLimitOfDay > 0)){//每天次数限制 且 每天积分限制
						if(axdGiftMemberCache == null){//当天第一次
							isGift = true;
						}else if(giftCountOfToday < giftCountLimitOfDay 
								&& giftPointOfToday < giftPointLimitOfDay){//次数比设置的少 和 积分比设置的少
							isGift = true;
						}
					}
				}
			}
			if(!isGift){
				log.info(String.format(">>>>>:安心点赠送积分，赠送条件不满足"));
				return new PointGiftDto(true, 0,"赠送条件不满足");
			}
			//个人账户 update
			sMemberAccount.setPoint((sMemberAccount.getPoint()==null?0:sMemberAccount.getPoint()) + giftPointAmount);
			sMemberAccountServiceImpl.update(sMemberAccount);
			
			//积分账户流转记录生成 save
			PointTransferRecord pointTransferRecord = new PointTransferRecord();
			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
			pointTransferRecord.setPointSub(giftPointAmount);
			pointTransferRecord.setOperateTime(now);
			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
			pointTransferRecord.setComment("每日任务:安心点");
			pointTransferRecord.setSubType((int)SubType.ECC_AXD_Gift);
			pointTransferRecord.setCommunityNumber(communityNumber);
			Integer propertyNumber = communityModel.getProperty_number();//物业编号 
			Integer branchId = communityModel.getBranchId();//子公司编号 
			pointTransferRecord.setPropertyNumber(propertyNumber);
			pointTransferRecord.setBranchId(branchId);
			pointTransferRecordServiceImpl.save(pointTransferRecord);
			
			//赠送记录 save
			AxdGiftRecord axdGiftRecord = new AxdGiftRecord();
			axdGiftRecord.setUserName(userName);
			axdGiftRecord.setGiftAmout(new BigDecimal(0));
			axdGiftRecord.setGiftPoint(giftPointAmount);
			axdGiftRecord.setGiftType(giftType);
			axdGiftRecord.setCommunityNo(communityNumber);
			axdGiftRecord.setPropertyNumber(Integer.valueOf(communityModel.getProperty_number()).toString());
			axdGiftRecord.setBranchId(Integer.valueOf(communityModel.getBranchId()).toString());
			axdGiftRecord.setLicense(license);
			axdGiftRecord.setComment("每日任务:安心点");
			axdGiftRecordServiceImpl.save(axdGiftRecord);
			
			//小区已赠送总额统计的缓存 saveOrUpdate redis操作加减乘除
			if(axdGiftStatistic == null){
				axdGiftStatistic = new AxdGiftStatistic();
				axdGiftStatistic.setCommunityNo(communityNumber);
				axdGiftStatistic.setAmountTotal(new BigDecimal(0));
				axdGiftStatistic.setPointTotal(giftPointAmount);
				Map<String, Object> mapStr = new HashMap<String, Object>();
				mapStr = BeanUtils.bean2Map(axdGiftStatistic);
				Map<byte[], byte[]> mapByte = new HashMap<byte[], byte[]>();
				for (String key : mapStr.keySet()) {
					Object val = mapStr.get(key);
					if(val != null){
						mapByte.put(key.getBytes(), val.toString().getBytes());
					}
				}
				redisService.hMSet(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber, mapByte);
			}else{
				redisService.hIncrBy(AxdGiftStatistic.Key_Cached_AxdGiftStatistic_Prefix + ":" + communityNumber,
						AxdGiftStatistic.Key_PointTotal, giftPointAmount.longValue());
			}
			
			//个人当天的缓存 saveOrUpdate redis操作加减乘除
			if(axdGiftMemberCache == null){
				axdGiftMemberCache = new AxdGiftMemberCache();
				axdGiftMemberCache.setUserName(userName);
				axdGiftMemberCache.setGiftCountOfToday(1);
				axdGiftMemberCache.setGiftAmountOfToday(new BigDecimal(0));
				axdGiftMemberCache.setGiftPointOfToday(giftPointAmount);
				axdGiftMemberCache.setLastAxdTime(nowStr);
				Map<String, Object> mapStr = new HashMap<String, Object>();
				mapStr = BeanUtils.bean2Map(axdGiftMemberCache);
				Map<byte[], byte[]> mapByte = new HashMap<byte[], byte[]>();
				for (String key : mapStr.keySet()) {
					Object val = mapStr.get(key);
					if(val != null){
						mapByte.put(key.getBytes(), val.toString().getBytes());
					}
				}
				redisService.hMSet(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, mapByte);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				long expiration = (cal.getTimeInMillis() - System.currentTimeMillis())/1000;
				redisService.setExpiration(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, expiration);
			}else{
				redisService.hIncrBy(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, AxdGiftMemberCache.Key_GiftCountOfToday, 1);
				redisService.hIncrBy(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, AxdGiftMemberCache.Key_GiftPointOfToday, giftPointAmount.longValue());
				redisService.hset(AxdGiftMemberCache.Key_Cached_AxdGiftMemberCache_Prefix + ":" + userName, AxdGiftMemberCache.Key_LastAxdTime, nowStr);
			}
			return new PointGiftDto(true, giftPointAmount,"安心点赠送积分成功");
		}
		return new PointGiftDto(true, 0,"点击安心点不赠送积分");
	}
	
	private PointGiftDto pointGiftByReview(PointGiftVo vo,SMemberAccount sMemberAccount,CommunityModel communityModel,Date now){
		ServiceReview.Type reviewType = vo.getReviewType();//评价类型
		if(reviewType == null){
			return new PointGiftDto(false, null, "参数错误");
		}
		String communityNumber = vo.getCommunityNumber();//小区编号
		Integer propertyNumber = null;
		Integer branchId = null;
		if(communityModel != null){
			propertyNumber = communityModel.getProperty_number();//物业编号 
			branchId = communityModel.getBranchId();//子公司编号
		}
		String pointPercent = sysconfigService.getParentValue(PointTransferRecord.POINT_GIFT).get(PointTransferRecord.REVIEW_POINT_PERCENT);
		if(StringUtils.isBlank(pointPercent)){
			pointPercent = "0";
		}
		BigDecimal percent = BigDecimal.ZERO;
		try{
			percent = new BigDecimal(pointPercent);
		}catch(NumberFormatException e){
			log.error("评论赠送比例配置异常",e);
			return new PointGiftDto(false, null, "评论赠送比例配置异常");
		}
		Integer acquisitionPoint = new BigDecimal(1).multiply(percent).intValue();
		if(acquisitionPoint>0){
			sMemberAccount.setPoint((sMemberAccount.getPoint()==null?0:sMemberAccount.getPoint()) + acquisitionPoint);
			sMemberAccountServiceImpl.update(sMemberAccount);
			
			//积分账户流转记录生成 save
			PointTransferRecord pointTransferRecord = new PointTransferRecord();
			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
			pointTransferRecord.setPointSub(acquisitionPoint);
			pointTransferRecord.setOperateTime(new Date());
			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
			//TODO 后续添加
			if(reviewType == ServiceReview.Type.LaundryService){
				pointTransferRecord.setComment("洗衣帮帮评论赠送");
			}else if(reviewType == ServiceReview.Type.CarBrighter){
				pointTransferRecord.setComment("点滴洗评论赠送");
			}else if(reviewType == ServiceReview.Type.Insurance){
				pointTransferRecord.setComment("保险服务评论赠送");
			}else if(reviewType == ServiceReview.Type.Expressage){
				pointTransferRecord.setComment("快递服务评论赠送");
			}else if(reviewType == ServiceReview.Type.Violation){
				pointTransferRecord.setComment("违章评论赠送");
			}else{
				pointTransferRecord.setComment("评论赠送");
			}
			pointTransferRecord.setSubType((int)SubType.Review_Gift);
			pointTransferRecord.setCommunityNumber(communityNumber);
			pointTransferRecord.setPropertyNumber(propertyNumber);
			pointTransferRecord.setBranchId(branchId);
			pointTransferRecordServiceImpl.save(pointTransferRecord);
			return new PointGiftDto(true, acquisitionPoint, "评论赠送积分成功");
		}else{
			return new PointGiftDto(true, 0, "0积分，不赠送");
		}
	}
	
	private PointGiftDto pointGiftByPurchase(PointGiftVo vo,SMemberAccount sMemberAccount,CommunityModel communityModel,Date now){
		Byte subType = vo.getSubType();//消费类型
		BigDecimal amout = vo.getAmout();//消费金额
		if(subType == null || amout == null){
			return new PointGiftDto(false, null, "参数错误");
		}
		String communityNumber = vo.getCommunityNumber();//小区编号
		Integer propertyNumber = null;
		Integer branchId = null;
		if(communityModel != null){
			propertyNumber = communityModel.getProperty_number();//物业编号 
			branchId = communityModel.getBranchId();//子公司编号
		}
		
		
		//根据消费类型获得赠送积分比例
		String pointPercent = "0";
		//TODO 后续添加
		if(subType == SubType.ECC_MONTH_PAY || subType == SubType.Temp_Parking){//代收类型，取代收赠送积分比例
			pointPercent = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(SMemberAccount.COLLECTION_POINT_PERCENT);
		}
		//TODO 后续添加
//		else if(subType == SubType.){//消费类型，取消费赠送积分比例
//			pointPercent = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(SMemberAccount.CONSUME_POINT_PERCENT);
//		}
		if(StringUtils.isBlank(pointPercent)){
			pointPercent = "0";
		}
		BigDecimal percent = BigDecimal.ZERO;
		try{
			percent = new BigDecimal(pointPercent);
		}catch(NumberFormatException e){
			log.error("消费赠送比例配置异常",e);
			return new PointGiftDto(false, null, "消费赠送比例配置异常");
		}
		Integer acquisitionPoint = amout.multiply(percent).intValue();
		if(acquisitionPoint>0){
			sMemberAccount.setPoint((sMemberAccount.getPoint()==null?0:sMemberAccount.getPoint()) + acquisitionPoint);
			sMemberAccountServiceImpl.update(sMemberAccount);
			
			//积分账户流转记录生成 save
			PointTransferRecord pointTransferRecord = new PointTransferRecord();
			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
			pointTransferRecord.setPointSub(acquisitionPoint);
			pointTransferRecord.setOperateTime(new Date());
			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
			//TODO 后续添加
			if(subType == SubType.ECC_MONTH_PAY){
				pointTransferRecord.setComment("月租续费赠送");
				pointTransferRecord.setSubType((int)SubType.ECC_MONTH_PAY);
			}else if(subType == SubType.Temp_Parking){
				pointTransferRecord.setComment("临时车缴费赠送");
				pointTransferRecord.setSubType((int)SubType.Temp_Parking);
			}
			pointTransferRecord.setCommunityNumber(communityNumber);
			pointTransferRecord.setPropertyNumber(propertyNumber);
			pointTransferRecord.setBranchId(branchId);
			pointTransferRecordServiceImpl.save(pointTransferRecord);
			return new PointGiftDto(true, acquisitionPoint, "消费赠送积分成功");
		}else{
			return new PointGiftDto(true, 0, "0积分，不赠送");
		}
		
	}
	
	private PointTransferRecord incrementPointExperience(PointTransferRecord entity){
		try{
			final SMemberAccount account = sMemberAccountServiceImpl.finByAccountNumber(entity.getAccountNumber());
			if(entity != null && entity.getPointSub() != null
					&& entity.getPointSub() > 0 && entity.getPointType() != null && ((int)entity.getPointType()) == PointTransferRecord.PointType.gain.ordinal()){
				account.setPointExperience(account.getPointExperience() + (long)entity.getPointSub());
				sMemberAccountServiceImpl.update(account);
			}
			// 升级判断
			final Member member = memberService.findByUsername(account.getUserName());
			Integer memberLevel = member.getUserlevel();
			if(memberLevel == null){
				memberLevel = 0;
			}
			long pointExp = account.getPointExperience();
			final VipLevel upgradeLevel = vipLevelService.findFirstByLeExp(pointExp);
			if(upgradeLevel != null){
				if(memberLevel < upgradeLevel.getLevel()){//满足升级条件
					//如果是等级为0初始注册会员的,根据配置是否可以直接升级为高级会员
					String upgradedECC = sysconfigService.getValueByNameSpace("POINTS." + Config_Key_Level0_Upgraded_ECC);
					if(upgradedECC != null && "1".equals(upgradedECC.trim())){
						log.info("由于设置了没开通ECC的普通注册会员不可以直接升级高级会员,用户升级不成功,userName="+member.getUsername());
					}else{
						member.setUserlevel(upgradeLevel.getLevel());
						memberService.update(member);
						ThreadPoolFactory.getInstance().addTask(new Runnable() {
							@Override
							public void run() {
//								消息KEY:com.arf.core.oldws.PushMessage.ContentType.VIP_LEVEL_UPGRADE
//								业务数据:{
//									remainedPoint:1500,/** 积分余额 */,
//									vipLevel:3,/** 当前会员等级 */,
//									vipLevelName:’黄金会员’,/** 当前会员名称 */,
//									nextLevelPoint:1800,/** 下一等级需要积分 */,
//									nextLevelName:1800,/** 下一等级名称 */
//									privilegeList:[“特权1”,”特权2”], /** 会员特权描述数组 */
//									userName:”13242034701” /** 用户名 */
//								}
								PushMessage pushMessage = new PushMessage();
								pushMessage.setContentType(PushMessage.ContentType.VIP_LEVEL_UPGRADE.toString());
								
								pushMessage.setTitle("恭喜您,升级到" + upgradeLevel.getLevelName());
								pushMessage.put("remainedPoint", account.getPoint() + "");
								pushMessage.put("vipLevel", upgradeLevel.getLevel() + "");
								pushMessage.put("vipLevelName", upgradeLevel.getLevelName());
								
								VipLevel nextLevel = vipLevelService.findByLevel(upgradeLevel.getLevel() + 1);
								if(nextLevel != null){
									pushMessage.put("nextLevelPoint", nextLevel.getRequiredExperience() + "");
									pushMessage.put("nextLevelName", nextLevel.getLevelName());
									pushMessage.setMsgContent("恭喜您,升级到" + upgradeLevel.getLevelName() + ",还需" 
											+ (nextLevel.getRequiredExperience() - account.getPointExperience()) + "积分即可升级到" + nextLevel.getLevelName());
								}else{
									pushMessage.setMsgContent("恭喜您,升级到" + upgradeLevel.getLevelName());
								}
								pushMessage.put("privilegeList",JSON.toJSONString(new String[]{"购智能门锁折扣","购买停车卡折扣","大抽奖参与特权","点滴洗优惠券及赠送券"}));
								pushMessage.put("userName", member.getUsername());
								try {
									PushUntils.getUserPushUntils().pushCustomMsgByUserNameSingle(pushMessage, null, member.getUsername());
								} catch (APIConnectionException e) {
									e.printStackTrace();
								} catch (APIRequestException e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	@Override
	public PointTransferRecord save(PointTransferRecord entity) {
		return super.save(incrementPointExperience(entity));
	}
}
