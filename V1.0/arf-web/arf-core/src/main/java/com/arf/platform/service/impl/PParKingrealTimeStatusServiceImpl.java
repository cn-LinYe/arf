/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.platform.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.MemberService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.ParkingInfoDao;
import com.arf.eparking.dto.ParKingRealTimeStatusDto;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.vo.ParkingInfoSearchVo;
import com.arf.platform.dao.PParKingrealTimeStatusDao;
import com.arf.platform.entity.PParKingrealTimeStatus;
import com.arf.platform.service.PParKingrealTimeStatusService;


/**
 * Service - PParKingrealTimeStatus表
 * 
 * @author arf
 * @version 1.0
 */
@Service("PParKingrealTimeStatusServiceImpl")
public class PParKingrealTimeStatusServiceImpl extends BaseServiceImpl<PParKingrealTimeStatus, Long> implements PParKingrealTimeStatusService{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private int DEFAULT_SEARCH_DISTANCE = 3;//默认搜索半径三千米
	
	
	@Resource(name = "PParKingrealTimeStatusDaoImpl")
	private PParKingrealTimeStatusDao PParKingrealTimeStatusDao;
	
	
	@Resource(name = "parkingInfoDaoImpl")
	private ParkingInfoDao parkingInfoDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "PParKingrealTimeStatusDaoImpl")
	private PParKingrealTimeStatusDao parKingRealTimeStatusDao;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Override
	protected BaseDao<PParKingrealTimeStatus, Long> getBaseDao() {
		return PParKingrealTimeStatusDao;
	}

	@Override
	public List<PParKingrealTimeStatus> getPParKingrealTimeStatusByCommunityNo(String communityNo) {
		return PParKingrealTimeStatusDao.getPParKingrealTimeStatusByCommunityNo(communityNo);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<ParKingRealTimeStatusDto>[] searchParkings(ParkingInfoSearchVo searchVo) {
		//停车场列表数组，有关键字时，关键字列表放在第一个对象里
		Collection[] collectionArray = new Collection[2];
		
		long startTime = System.currentTimeMillis();
		Integer pageSize = searchVo.getPageSize();
		String keyWords = searchVo.getKeyWords();
		final Double longitude = searchVo.getLongitude();
		final Double latiude = searchVo.getLatiude();
		Integer searchDistance = searchVo.getSearchDistance();
		Integer subType = searchVo.getSubType();
		if(pageSize == null || pageSize <= 0){
			pageSize = 60;//默认返回三十条数据
		}
		
		String[] keyWord;
		if(StringUtils.isNotBlank(keyWords)){
			keyWord = keyWords.split("\\s+");
		}else{
			keyWord = new String[]{};
		}
		
		Collection<ParKingRealTimeStatusDto> searchResultList1 = null;
		Collection<ParKingRealTimeStatusDto> searchResultList2 = null;
		if(keyWord != null && keyWord.length > 0){//如果是通过关键字搜索的,采用List集合, 通过自动加入的顺序排序
			searchResultList1 = new ArrayList<ParKingRealTimeStatusDto>();
		}else if(longitude != null && latiude != null){ //只有上传经纬度才通过距离排序
			searchResultList2 = new TreeSet<ParKingRealTimeStatusDto>(new Comparator<ParKingRealTimeStatusDto>() {

				@Override
				public int compare(ParKingRealTimeStatusDto o1, ParKingRealTimeStatusDto o2) {
					if(longitude != null && latiude != null){
						ParkingInfo parkingInfo1 = o1.getParkingInfo();
						ParkingInfo parkingInfo2 = o2.getParkingInfo();
						if(parkingInfo1 != null && parkingInfo1.getLatiude() != null && parkingInfo2 != null && parkingInfo2.getLatiude() != null
								&& parkingInfo1 != null && parkingInfo1.getLongitude() != null && parkingInfo2 != null && parkingInfo2.getLongitude() != null){
							Double distance1 = distance(longitude, latiude, parkingInfo1.getLongitude(), parkingInfo1.getLatiude());
							Double distance2 = distance(longitude, latiude, parkingInfo2.getLongitude(), parkingInfo2.getLatiude());
							return distance1.compareTo(distance2);
						}
					}
					return 0;
				}
			});
		}else{//既没有关键字、也没有经纬度
			searchResultList2 = new ArrayList<ParKingRealTimeStatusDto>();
		}
		Map<String,String> parkingsCached = redisService.hgetAll(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map);
		if(parkingsCached == null){
			return collectionArray;
		}
		
	LABEL_FIRST : 
		//第一次轮询 parkingsCached
		for(String prkingId : parkingsCached.keySet()){
			int size1=0;
			int size2=0;
			if(searchResultList1!=null){
				size1=searchResultList1.size();
			}
			if(searchResultList2!=null){
				size2=searchResultList2.size();
			}
			if((size1+size2) >= pageSize){
				return collectionArray;
			}
			
			ParKingRealTimeStatusDto parkingStatusDto = null;
			try{
				parkingStatusDto = JSON.parseObject(parkingsCached.get(prkingId), ParKingRealTimeStatusDto.class);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(parkingStatusDto != null){
				ParkingInfo parkingInfo = parkingStatusDto.getParkingInfo();
				
				//停车场存在
				//1、关键字存在，则通过关键字搜素
				//2、经纬度存在，则通过经纬度、搜索距离（默认DEFAULT_SEARCH_DISTANCE）搜索
				if(parkingInfo != null){
					//优先通过keyWord查询(匹配：商圈、停车场编号、停车场名称、公司全称、停车场地址)
					if(keyWord.length > 0){
						
						String businessArea = parkingInfo.getBusinessArea();
						List<String> area = new ArrayList<String>();
						if(StringUtils.isNotBlank(businessArea)){
							area = Arrays.asList(businessArea.split(","));
						}
						
						for(String keyw : keyWord){

							if(area.contains(keyw)){
								if(available(parkingInfo, subType)){
									searchResultList1.add(parkingStatusDto);
								}
								continue LABEL_FIRST;
							}
							
							SimplePropertyPreFilter propertyPrefilter = new SimplePropertyPreFilter(ParkingInfo.class);
							propertyPrefilter.getIncludes().add("parkingNo");
							propertyPrefilter.getIncludes().add("parkingName");
							propertyPrefilter.getIncludes().add("companyFullName");
							propertyPrefilter.getIncludes().add("address");
							if(JSON.toJSONString(parkingStatusDto,propertyPrefilter).indexOf(keyw) >= 0){
								if(available(parkingInfo, subType)){
									searchResultList1.add(parkingStatusDto);
								}
								continue LABEL_FIRST;
							}
						}
					}
					
					//经纬度距离查询
					if(longitude != null && latiude != null){
						if(searchResultList2== null){
							searchResultList2 = new TreeSet<ParKingRealTimeStatusDto>(
									new Comparator<ParKingRealTimeStatusDto>() {

										@Override
										public int compare(ParKingRealTimeStatusDto o1, ParKingRealTimeStatusDto o2) {
											if(longitude != null && latiude != null){
												ParkingInfo parkingInfo1 = o1.getParkingInfo();
												ParkingInfo parkingInfo2 = o2.getParkingInfo();
												if(parkingInfo1 != null && parkingInfo1.getLatiude() != null && parkingInfo2 != null && parkingInfo2.getLatiude() != null
														&& parkingInfo1 != null && parkingInfo1.getLongitude() != null && parkingInfo2 != null && parkingInfo2.getLongitude() != null){
													Double distance1 = distance(longitude, latiude, parkingInfo1.getLongitude(), parkingInfo1.getLatiude());
													Double distance2 = distance(longitude, latiude, parkingInfo2.getLongitude(), parkingInfo2.getLatiude());
													return distance1.compareTo(distance2);
												}
											}
											return 0;
										}
									});
						}
						if(searchDistance == null ||searchDistance <= 0){
							searchDistance = DEFAULT_SEARCH_DISTANCE;//默认搜索半径三千米
						}
						
						if(parkingInfo != null){
							Double lng = parkingInfo.getLongitude();
							Double lat = parkingInfo.getLatiude();
							if(lng != null && lng > 0
									&& lat != null && lat > 0){
								int distance = (int) distance(longitude,latiude,lng,lat);
								if(distance <= searchDistance*1000){
									if(available(parkingInfo, subType)){
										searchResultList2.add(parkingStatusDto);
									}
									continue LABEL_FIRST;
								}
							}
						}
					}
				}
			}
		}
		
		//如果第一次轮询查询结果为空，则第二次轮询
		if(CollectionUtils.isEmpty(searchResultList1)&&CollectionUtils.isEmpty(searchResultList2)){
			if(searchResultList2==null){
				searchResultList2 = new ArrayList<ParKingRealTimeStatusDto>();
			}
			int size1=0;
			if(searchResultList2!=null){
				size1=searchResultList2.size();
			}
			if(size1 >= pageSize){
				return collectionArray;
			}
			//第二次轮询 parkingsCached
			for(String prkingId : parkingsCached.keySet()){
				ParKingRealTimeStatusDto parkingStatusDto = null;
				try{
					parkingStatusDto = JSON.parseObject(parkingsCached.get(prkingId), ParKingRealTimeStatusDto.class);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(parkingStatusDto != null && parkingStatusDto.getParkingInfo() != null){
					
					ParkingInfo parkingInfo = parkingStatusDto.getParkingInfo();
					//停车场所属子公司 == 用户绑定小区所属子公司 并且 searchResultList2
					if(parkingInfo != null && parkingInfo.getBranchId() != null && parkingInfo.getBranchId().equals(searchVo.getBranchId())
							&& searchResultList2 instanceof List){
								if(available(parkingInfo, subType)){
									((List<ParKingRealTimeStatusDto>)searchResultList2).add(0, parkingStatusDto);
								}
					}else{
						if(available(parkingInfo, subType)){
							searchResultList2.add(parkingStatusDto);
						}
					}
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		log.info("查询停车场共耗时:" + (endTime - startTime) + "毫秒" + ",共扫描" + parkingsCached.size() + "个停车场");
		collectionArray[0]=searchResultList1;
		collectionArray[1]=searchResultList2;
		return collectionArray;
	}
	
	/** 
	 * 计算地球上任意两点(经纬度)距离 
	 * @param long1 
	 *            第一点经度 
	 * @param lat1 
	 *            第一点纬度 
	 * @param long2 
	 *            第二点经度 
	 * @param lat2 
	 *            第二点纬度 
	 * @return 返回距离 单位：米 
	 */  
	public double distance(double long1, double lat1, double long2,  
	        double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * R  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}
	
	/**
	 * 根据缓存中的停车场数据判断停车场类型是否符合subType
	 * @param parkingInfo
	 * @param subType
	 * @return
	 */
	public boolean available(ParkingInfo parkingInfo,Integer subType){
		if(parkingInfo == null){
			return false;
		}
		String dbSubType = parkingInfo.getSubType();
		//数据库中的subType为空，则此停车场不满足条件
		if(StringUtils.isBlank(dbSubType)){
			return false;
		}else{
			if(subType == null || subType == 0){
				return true;
			}else if(subType == 1 && "07".equals(dbSubType)){
				return true;
			}
		}
		return false;
	}

}