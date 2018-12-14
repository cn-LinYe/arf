package com.arf.gift.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.service.ParkRateRecordModelService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gift.dao.IGiftVoucherInfoRecordDao;
import com.arf.gift.entity.CommunityPromotions;
import com.arf.gift.entity.GiftVoucherInfoRecord;
import com.arf.gift.entity.GiftVoucherInfoRecord.GetChannel;
import com.arf.gift.entity.GiftVoucherInfoRecord.Status;
import com.arf.gift.entity.RebateRatio;
import com.arf.gift.service.CommunityPromotionsService;
import com.arf.gift.service.IGiftVoucherInfoRecordService;
import com.arf.gift.service.RebateRatioService;
import com.arf.payment.OrderNumPrefixConstraint;

@Service("giftVoucherInfoRecordServiceImpl")
public class GiftVoucherInfoRecordServiceImpl extends BaseServiceImpl<GiftVoucherInfoRecord, Long> implements IGiftVoucherInfoRecordService{

	Logger log = LoggerFactory.getLogger(GiftVoucherInfoRecordServiceImpl.class);
	@Resource(name="giftVoucherInfoRecordDaoImpl")
	IGiftVoucherInfoRecordDao giftVoucherInfoRecordDao;
	
	@Resource(name = "parkRateRecordModelServiceImpl")
	private ParkRateRecordModelService parkRateRecordModelService;
	@Resource(name = "communityPromotionsServiceImpl")
	private CommunityPromotionsService communityPromotionsService;
	@Resource(name = "rebateRatioServiceImpl")
	private RebateRatioService rebateRatioService;
	
	@Override
	protected BaseDao<GiftVoucherInfoRecord, Long> getBaseDao() {
		return giftVoucherInfoRecordDao;
	}
	@Override
	public PageResult<GiftVoucherInfoRecord> findGiftRecordByCondition(Integer pageNo, Integer pageSize, GiftVoucherInfoRecord.Status status[],
			String userName, Long id,GiftVoucherInfoRecord.ExpressLogisticsStatus expressLogisticsStatus) {
		return giftVoucherInfoRecordDao.findGiftRecordByCondition(pageNo, pageSize, status, userName, id,expressLogisticsStatus);
	}
	
	@Override
	public GiftVoucherInfoRecord genRecordAboutParkrate(Integer effectiveDate,Status status,GetChannel getChannel,
			String userName,String communityNumber,BigDecimal amount,String outTradeNo) {
		GiftVoucherInfoRecord gift = new GiftVoucherInfoRecord();
		String voucherNumber = OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_GIFT_COUPONS, 5);
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE)+effectiveDate);
		gift.setVoucherName("月租缴费礼品券");
		gift.setVoucherNumber(voucherNumber);
		gift.setEndDate(endDate.getTime());
		gift.setStatus(status);
		gift.setAmount(amount);
		gift.setUserName(userName);
		gift.setGetChannel(getChannel);
		gift.setCommunityNumber(communityNumber);
		gift.setOutTradeNo(outTradeNo);
		return gift;
	}
	@Override
	public int getGiftRecordCount(String userName,Status status) {	
		return giftVoucherInfoRecordDao.getGiftRecordCount(userName,status);
	}
	@Override
	public GiftVoucherInfoRecord findByVoucherNumber(String voucherNumber) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("voucherNumber", Operator.eq, voucherNumber));
		List<GiftVoucherInfoRecord> list = this.findList(null, filters, null);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	//月租缴费赠送礼品卷
	//1、查询缴费的小区是否开通缴费赠送礼品卷（先查询小区活动表，再查返利优惠比例表）
	//2、如是：获得缴费时长类型与赠送等级匹配，并生成一条赠送礼品卷记录
	@Override
	public GiftVoucherInfoRecord genRecordByPayParkRate(String outTradeNo,BigDecimal totalFeeYuan,ParkRateRecordModel parkRateRecordModel){
		if(outTradeNo==null || totalFeeYuan==null || totalFeeYuan.compareTo(BigDecimal.ZERO)<0){
			return null;
		}
//		ParkRateRecordModel parkRateRecordModel = parkRateRecordModelService.selectByouttradeno(outTradeNo);
		if(parkRateRecordModel == null){
			log.info("月租车缴费后生成礼品卷时获取到的parkRateRecordModel对象为null(outTradeNo="+outTradeNo+")");
			return null;
		}
		String communityNumber = parkRateRecordModel.getCommunityNumber();
		BigDecimal giftAmount = BigDecimal.ZERO;
		CommunityPromotions communityPromotions = communityPromotionsService.findByCommunityNumberMonthlyStatus(
				communityNumber, CommunityPromotions.MonthlyPaymentStatus.PARTICIPATE);
		RebateRatio rebateRatio = null;
		if(communityPromotions != null){
			rebateRatio = rebateRatioService.findByCommunityNumber(communityNumber,RebateRatio.RebateType.ECC_MONTH_PAY);
		}
		if(rebateRatio!=null){
			Integer rebate = 0;
			long diffDays = (parkRateRecordModel.getEndTime()-parkRateRecordModel.getStartTime())/(24*60*60);
			if(diffDays<32){
				//1个月
				rebate = rebateRatio.getOneMonthRebate();
			}else if(diffDays<93){
				//3个月
				rebate = rebateRatio.getThreeMonthRebate();
			}else if(diffDays<190){
				//6个年
				rebate = rebateRatio.getSixMonthRebate();
			}else if(diffDays<366){
				//1年
				rebate = rebateRatio.getOneYearRebate();
			}else{
				//2年
				rebate = rebateRatio.getTwoYearRebate();
			}
			Double rebateDouble = rebate==null?0:rebate/100D;
			giftAmount = totalFeeYuan.multiply(new BigDecimal(rebateDouble.toString()));
			if(giftAmount.compareTo(new BigDecimal("0.01"))>=0){
				GiftVoucherInfoRecord gift = new GiftVoucherInfoRecord();
				String voucherNumber = OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_GIFT_COUPONS, 5);
				Calendar endDate = Calendar.getInstance();
				endDate.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE)+rebateRatio.getEffectiveDate());
				BigDecimal amount = giftAmount;
				String amountStr = amount.toString();
				if(amountStr.contains(".")){
					amountStr = StringUtils.left(amountStr, amountStr.indexOf(".") + 3);
				}
				if(amountStr.contains(".00")){
					amountStr = amountStr.split("\\.")[0];
				}else if(amountStr.contains(".")){
					amountStr = StringUtils.left(amountStr, amountStr.indexOf(".") + 3);;
				}
				gift.setVoucherName(amountStr + "元" + "月租缴费礼品券");
				gift.setVoucherNumber(voucherNumber);
				gift.setEndDate(endDate.getTime());
				gift.setStatus(GiftVoucherInfoRecord.Status.UNUSED);
				gift.setAmount(giftAmount);
				gift.setUserName(parkRateRecordModel.getUserName());
				gift.setGetChannel(GiftVoucherInfoRecord.GetChannel.ECC_MONTH_PAY);
				gift.setCommunityNumber(communityNumber);
				gift.setOutTradeNo(outTradeNo);
				return this.save(gift);
			}
		}
		return null;
	}
	@Override
	public int updateOverdueRecordStatus() {
		return giftVoucherInfoRecordDao.updateOverdueRecordStatus();
	}
	
	@Override
	public GiftVoucherInfoRecord save(GiftVoucherInfoRecord entity) {
		if(entity != null && entity.getEndDate() != null){
			Date endDate = entity.getEndDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			entity.setEndDate(calendar.getTime());
		}
		return super.save(entity);
	}
}
