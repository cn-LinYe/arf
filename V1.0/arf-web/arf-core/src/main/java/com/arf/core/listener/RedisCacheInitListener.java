package com.arf.core.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.arf.carbright.dto.PackageTypeSettingsDto;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.service.PackageTypeService;
import com.arf.carbright.service.PbusinessService;
import com.arf.core.Order.Direction;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.cache.redis.RedisService;
import com.arf.eparking.dto.ParKingRealTimeStatusDto;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.service.ParkingInfoService;
import com.arf.platform.entity.PParKingrealTimeStatus;
import com.arf.platform.service.PParKingrealTimeStatusService;

@Component("redisCacheInitListener")
public class RedisCacheInitListener implements ApplicationListener<ContextRefreshedEvent>{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * @see {@link com.arf.controller.carbright.AdvertisingFactory.ADS_SETTINGS_NAME}
	 */
	private static final String ADS_SETTINGS_NAME="AdvertisementsJson:AdvertisementsJson";	
	
	@Resource(name = "packageTypeService")
	private PackageTypeService packageTypeService;
	
	@Resource(name = "PParKingrealTimeStatusServiceImpl")
	private PParKingrealTimeStatusService parKingRealTimeStatusService;
	
	@Resource(name = "parkingInfoServiceImpl")
	private ParkingInfoService parkingInfoService;
	
	@Resource(name = "pBusinessService")
	private PbusinessService businessService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
//					initParkingStatusCache();
					initParkingStatusCacheMap();
				}
			}).start();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					initPackageTypeCache();
				}
			}).start();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					initBusinessOpeningTimes();
				}
			}).start();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					initAdvertisementsCache();
				}
			}).start();
		}
	}
	
	/**
	 * 清楚广告缓存,让其从新加载广告资源缓存
	 */
	private void initAdvertisementsCache(){
		redisService.del(ADS_SETTINGS_NAME);
	}
	
	private void initBusinessOpeningTimes(){
		log.info("*********************************************"
				+ "△△△开始初始化BusinessOpeningTimes信息到缓存"
				+ "△△△*****************************************************************");
		Pageable pageable = new Pageable();
		pageable.setPageSize(100);
		pageable.setOrderProperty("id");
		pageable.setOrderDirection(Direction.desc);
		int pageNumber = 1;
		
		int initCounter = 0;
		long totalCount = 0;
		while (true) {
			pageable.setPageNumber(pageNumber);
			Page<PBusiness> page = businessService.findPage(pageable);
			if (page != null) {
				totalCount = page.getTotal();
				List<PBusiness> parkingStatuses = page.getContent();
				if (CollectionUtils.isNotEmpty(parkingStatuses)) {
					int count = parkingStatuses.size();
					for(int i=0;i<count;i++){
						PBusiness businesses = parkingStatuses.get(i);
						
						String openingTimes = redisService.get(PBusiness.Prefix_Cache_Key_Opening_Times 
								+ businesses.getBusinessNum(),String.class);
						if(StringUtils.isEmpty(openingTimes)){ //同步数据库的数据到redis
							redisService.set(PBusiness.Prefix_Cache_Key_Opening_Times 
									+ businesses.getBusinessNum(), PBusiness.Default_Opening_Times);
						}
						initCounter = initCounter + 1;
					}
					
					pageNumber = pageNumber + 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		log.info("*********************************************"
				+ "△△△完成初始化BusinessOpeningTimes信息到缓存(共初始化"+initCounter+"/"+totalCount+"个对象)△△△"
						+ "*****************************************************************");
	}

	/**
	 * 初始化停车场实时状态到缓存
	 */
	private void initParkingStatusCache(){
		log.info("*********************************************"
				+ "△△△开始初始化PParKingrealTimeStatus信息到缓存"
				+ "△△△*****************************************************************");
		Pageable pageable = new Pageable();
		pageable.setPageSize(100);
		pageable.setOrderProperty("version");
		pageable.setOrderDirection(Direction.desc);
		int pageNumber = 1;
		
		int initCounter = 0;
		long totalCount = 0;
		while (true) {
			pageable.setPageNumber(pageNumber);
			Page<PParKingrealTimeStatus> page = parKingRealTimeStatusService.findPage(pageable);
			if (page != null) {
				totalCount = page.getTotal();
				List<PParKingrealTimeStatus> parkingStatuses = page.getContent();
				if (CollectionUtils.isNotEmpty(parkingStatuses)) {
					int count = parkingStatuses.size();
					for(int i=0;i<count;i++){
						PParKingrealTimeStatus parkingStatus = parkingStatuses.get(i);
						
						ParKingRealTimeStatusDto cachedObj = redisService.get(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status 
								+ parkingStatus.getParkingId(),ParKingRealTimeStatusDto.class);
						if(cachedObj == null || cachedObj.getId() == null){ //同步数据库的数据到redis
							ParkingInfo parkingInfo = parkingInfoService.findByParkingNo(parkingStatus.getParkingId());
							
							ParKingRealTimeStatusDto realTimeDto = new ParKingRealTimeStatusDto(parkingInfo);
							
							try {
								BeanUtils.copyProperties(realTimeDto, parkingStatus);
								redisService.set(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status 
										+ parkingStatus.getParkingId(), realTimeDto);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}else{ //同步redis的数据到数据库
							try {
								BeanUtils.copyProperties(parkingStatus, cachedObj);
								parKingRealTimeStatusService.update(parkingStatus, "version");
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						initCounter = initCounter + 1;
					}
					
					pageNumber = pageNumber + 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		log.info("*********************************************"
				+ "△△△完成初始化PParKingrealTimeStatus信息到缓存(共初始化"+initCounter+"/"+totalCount+"个对象)△△△"
						+ "*****************************************************************");
	}
	
	/**
	 * 初始化停车场实时状态到缓存
	 */
	private void initParkingStatusCacheMap(){
		log.info("*********************************************"
				+ "△△△开始初始化PParKingrealTimeStatus信息到缓存"
				+ "△△△*****************************************************************");
		Pageable pageable = new Pageable();
		pageable.setPageSize(100);
		pageable.setOrderProperty("version");
		pageable.setOrderDirection(Direction.desc);
		int pageNumber = 1;
		
		int initCounter = 0;
		long totalCount = 0;
		while (true) {
			pageable.setPageNumber(pageNumber);
			//查询数据库，分页获取实时状态信息表
			Page<PParKingrealTimeStatus> page = parKingRealTimeStatusService.findPage(pageable);
			if (page != null) {
				totalCount = page.getTotal();
				List<PParKingrealTimeStatus> parkingStatuses = page.getContent();
				if (CollectionUtils.isNotEmpty(parkingStatuses)) {
					int count = parkingStatuses.size();
					for(int i=0;i<count;i++){
						PParKingrealTimeStatus parkingStatus = parkingStatuses.get(i);
						
						ParKingRealTimeStatusDto cachedObj = redisService.hGet(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,
								parkingStatus.getParkingId(),ParKingRealTimeStatusDto.class);
						
						if(cachedObj == null || cachedObj.getId() == null){ //同步数据库的数据到redis
							ParkingInfo parkingInfo = parkingInfoService.findByParkingNo(parkingStatus.getParkingId());
							//暂定为
							//能从p_parkinginfo表中查询到，即认定为停车场
							//不能从p_parkinginfo表中查询到，即认定为小区
							//parkingState状态:-1:已删除：0:新建（草稿）状态；1:运营中；
							if(parkingInfo == null
									|| (parkingInfo.getParkingState() != null && parkingInfo.getParkingState() == 1)){
								
								ParKingRealTimeStatusDto realTimeDto = new ParKingRealTimeStatusDto(parkingInfo);
								
								try {
									BeanUtils.copyProperties(realTimeDto, parkingStatus);
									redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,
											parkingStatus.getParkingId(), realTimeDto);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
						}else{ //同步redis的数据到数据库
							try {
								BeanUtils.copyProperties(parkingStatus, cachedObj);
								parKingRealTimeStatusService.update(parkingStatus, "version");
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						initCounter = initCounter + 1;
					}
					
					pageNumber = pageNumber + 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		log.info("*********************************************"
				+ "△△△完成初始化PParKingrealTimeStatus信息到缓存(共初始化"+initCounter+"/"+totalCount+"个对象)△△△"
						+ "*****************************************************************");
	}
	
	
	/**
	 * 初始化商户对套餐的设置,以及改套餐的每天的接单量到缓存
	 */
	private void initPackageTypeCache() {
		log.info("*********************************************" + "△△△开始初始化PackageType 设置信息到缓存"
				+ "△△△*****************************************************************");
		Pageable pageable = new Pageable();
		pageable.setPageSize(100);
		pageable.setOrderProperty("id");
		pageable.setOrderDirection(Direction.desc);
		int pageNumber = 1;

		int initCounter = 0;
		long totalCount = 0;
		while (true) {
			pageable.setPageNumber(pageNumber);
			Page<PackageType> page = packageTypeService.findPage(pageable);
			if (page != null) {
				totalCount = page.getTotal();
				List<PackageType> packageTypes = page.getContent();
				if (CollectionUtils.isNotEmpty(packageTypes)) {
					int count = packageTypes.size();
					for (int i = 0; i < count; i++) {
						PackageType packageType = packageTypes.get(i);

						Integer bookingCountOfDay = redisService
								.get(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day
										+ packageType.getPackageTypeNum(), Integer.class);
						if (bookingCountOfDay == null) { // 同步数据库的数据到redis
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DAY_OF_MONTH, +1);
							calendar.set(Calendar.HOUR_OF_DAY, 0);
							calendar.set(Calendar.MINUTE, 0);
							calendar.set(Calendar.SECOND, 0);
							Date createStart = calendar.getTime();
							long interval = createStart.getTime() / 1000L - new Date().getTime() / 1000L;
							redisService.set(PackageType.Prefix_Cache_Package_Type_Booking_Count_Of_Day
									+ packageType.getPackageTypeNum(), 0,interval);
						}
						
						PackageTypeSettingsDto settingObj = redisService.get(PackageType.Prefix_Cache_Package_Type_Setting + packageType.getPackageTypeNum(),PackageTypeSettingsDto.class);
						if(settingObj == null){
							settingObj = new PackageTypeSettingsDto();
							settingObj.setLimitOfDay(PackageTypeSettingsDto.Default_Limit_Of_Day);
							redisService.set(PackageType.Prefix_Cache_Package_Type_Setting + packageType.getPackageTypeNum(), settingObj);
						}
						
						initCounter = initCounter + 1;
					}
					pageNumber = pageNumber + 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		log.info("*********************************************" + "△△△完成初始化PackageType 设置信息到缓存(共初始化" + initCounter
				+ "/" + totalCount + "个对象)△△△" + "*****************************************************************");
	}
}
