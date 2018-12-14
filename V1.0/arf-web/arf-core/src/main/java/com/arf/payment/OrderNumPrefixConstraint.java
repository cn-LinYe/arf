package com.arf.payment;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 定义的系统所有订单编号前缀约束常量
 * @author Caolibao
 * 2016年6月20日 下午3:04:59
 *
 */
public class OrderNumPrefixConstraint {
	
	/**
	 * @Warnning 【前缀长度必须为4位】 这些定义的前缀不可重复,且只与订单在线支付回调相关.不与其它任何业务耦合
	 */
	
	/**
	 * 众筹项目订单前缀
	 */
	public static final String PREFIX_CROW_FOUND = "ZCON";
	
	/**
	 * e停车预订车位订单前缀
	 */
	public static final String PREFIX_EPARKING_BOOKING_BERTH = "EPRB";
	
	/**
	 * 点滴洗洗车订单前缀
	 */
	public static final String PREFIX_CARBRIGHTER_CARWASHING = "CBWS";

	/**
	 * 点滴洗套餐购买订单前缀
	 */
	public static final String PREFIX_CARBRIGHTER_PKG_BUY = "CBPB";
	
	/**
	 * 临时车支付停车费前缀
	 */
	public static final String PREFIX_TEMP_PARKING_FEE_PAY = "TPFP";
	
	/**
	 * 月租车包月停车费前缀
	 */
	public static final String PREFIX_PARKING_MONTH_RENT_FEE_PAY = "PMFP";
	
	/**
	 * 开通ECC订单编号会员
	 */
	public static final String PREFIX_ECC_OPEN_VIP = "PEOV";
	
	/**
	 * 洗衣帮帮订单编号
	 */
	public static final String PREFIX_AXD_LAUNDRY_ORDER = "PALO";
	
	/**
	 * 车保订单编号前缀
	 */
	public static final String PREFIX_CAR_INSURANCE_ORDER = "PCIO";
	
	/**
	 * 代收快递订单编号前缀
	 */
	public static final String PREFIX_EXPRESS_DELIVERY_ORDER = "PEDO";
	/**
	 * 荣事达自助洗车订单
	 */
	public static final String PREFIX_CAR_WASHING_SELFSERVICE_ORDER = "CWSO";
	
	/**
	 * 违章处理订单前缀
	 */
	public static final String PREFIX_TRAFFIC_VIOLATION_ORDER = "TVLO";
	
	/**
	 * 违章处理订单-附加服务费前缀
	 */
	public static final String PREFIX_TRAFFIC_VIOLATION_ORDER_ADDITION_SERVICE = "TVLA";
	
	/**
	 * 金卡购买订单前缀
	 */
	public static final String PREFIX_BUY_GOLD_CARD_ORDER = "BGCO";
	
	/**
	 * 物业缴费订单前缀
	 */
	public static final String PREFIX_PROPERTY_MGR_FEE_ORDER = "PMFO";
	
	/**
	 * 门锁分期乐购买首款订单前缀
	 */
	public static final String PREFIX_SMART_LOCK_INSTALLMENT_FIRSTFUNDS = "SLIF";
	
	/**
	 * 门锁分期乐尾款订单前缀
	 */
	public static final String PREFIX_SMART_LOCK_INSTALLMENT_ORDER = "SLIO";
	
	/**
	 * 礼品券编号前缀
	 */
	public static final String PREFIX_GIFT_COUPONS = "GIFT";
	
	/**
	 * 临时车,支付停车费前缀
	 */
	public static final String PREFIX_WX_TEMP_PARKING_FEE_PAY = "WXTP";
	
	/**
	 * 扫码代金券前缀
	 */
	public static final String PREFIX_SCAN_CODE_ORDER = "PSCO";
	
	/**
	 * 门禁卡订单前缀
	 */
	public static final String PREFIX_ACCESS_CARD_USER_ORDER = "ACUO";
	
	/**
	 * 购买加油卡订单前缀
	 */
	public static final String PREFIX_REFUEL_CARD_ORDER = "PRCO";
	/**
	 * 加油支付订单前缀
	 */
	public static final String PREFIX_REFUEL_PAY_ORDER = "PRPO";
	/**
	 * 补油订单前缀
	 */
	public static final String PREFIX_REFUEL_SUPPLY_ORDER = "PRSO";
	
	/**
	 * 加油卡充值订单前缀
	 */
	public static final String PREFIX_REFUEL_RECHARGE_ORDER = "PRRO";
	
	/**
	 * 第三方商户接入订单编号前缀
	 */
	public static final String PREFIX_B2B_BUSINESS_ORDER = "BBBO";
	
	public static int DEFAULT_PREFIX_LEN = 4;
	private static OrderNumPrefixConstraint orderNumPrefixConstraint = new OrderNumPrefixConstraint();
	private OrderNumPrefixConstraint(){}
	
	public static OrderNumPrefixConstraint getInstance(){
		return orderNumPrefixConstraint;
	}
	
	/**返回订单号（可根据前缀区分订单号）
	 * @param prefix 前缀请查看OrderNumPrefixConstraint定义的常量 eg.{@link com.arf.core.OrderNumPrefixConstraint.PREFIX_CROW_FOUND}
	 * @param randomCount
	 * @return
	 */
	public  String genOrderNo(String prefix,int randomCount){
		randomCount=(randomCount<=0)?5:randomCount;//randomCount为0产生5位随机数
		return new String(prefix).concat(genOrderNo(randomCount));
	}
	
	/**
	 * 对订单编号做一个简单短时缓存
	 */
	private final LoadingCache<String, String> orderNoCache = CacheBuilder.newBuilder().
			expireAfterWrite(5L, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
		Map<String,String> map = new ConcurrentHashMap<>();
		@Override
		public String load(String key) {
			String v = map.get(key);
			return v == null ? "": v;
		}

	});
	/**
	 *  "yyyyMMddHHmmss" + randomCount位的随机唯一码生成策略
	 * @param randomCount
	 * @return
	 */
	private String genOrderNo(int randomCount){
		Date now = new Date();
		String timeStr = DateFormatUtils.format(now, "yyyyMMddHHmmss");
		String no = "";
		while(true){
			no =  timeStr + RandomStringUtils.randomNumeric(randomCount);
			String exist = "";
			try {
				exist = orderNoCache.get(no);
			} catch (ExecutionException e) {
				 e.printStackTrace();
			}
			if(StringUtils.isBlank(exist)){
				orderNoCache.put(no, no);
				break;
			}
		}
		return no;
	}
}
