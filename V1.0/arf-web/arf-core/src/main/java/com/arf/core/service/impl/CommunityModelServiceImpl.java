/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.arf.base.PageResult;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CommunityModelDao;
import com.arf.core.dao.QuanProvCityAreaModelDao;
import com.arf.core.dto.CommunityCitynoDto;
import com.arf.core.dto.CommunityRedisDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.CommunityModel.AuthorizationMode;
import com.arf.core.service.CommunityModelService;
import com.arf.redis.CacheNameDefinition;


/**
 * Service - 小区表
 * 
 * @author arf
 * @version 4.0
 */
@Service("communityModelServiceImpl")
public class CommunityModelServiceImpl extends BaseServiceImpl<CommunityModel, Long> implements CommunityModelService {

	@Resource(name = "communityModelDaoImpl")
	private CommunityModelDao communityModelDao;	
	@Resource(name = "quanProvCityAreaModelDaoImpl")
	private QuanProvCityAreaModelDao quanProvCityAreaModelDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Override
	protected BaseDao<CommunityModel, Long> getBaseDao() {
		return communityModelDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<CommunityModel> checkByCommunity_number(String community_number) {		
		return communityModelDao.checkByCommunity_number(community_number);
	}

	@Override
	public List<CommunityModel> CheckByNo(String no) {
		return communityModelDao.CheckByNo(no);
	}

	@Override
	public List<CommunityModel> checkByPropretyNumber(long propretyId) {
		return communityModelDao.checkByPropretyNumber(propretyId);
	}

	@Override
	public List<CommunityModel> selectByLicenseCommunitys(String communityNumber) {
		return communityModelDao.checkByCommunity_number(communityNumber);
	}

	@Override
	public List<CommunityModel> selectBySendMessages() {
		return communityModelDao.selectBySendMessages();
	}
	
	@Override
	public CommunityModel findByNumber(String communityNumber){
		
		List<CommunityModel> comms = this.checkByCommunity_number(communityNumber);
		if(CollectionUtils.isEmpty(comms)){
			return null;
		}
		if(comms.size() == 1){
			return comms.get(0);
		}else{
			throw new NonUniqueResultException("通过小区编号[" + communityNumber + "]查询小区信息时出现"+comms.size()+"个小区信息.["+ JSON.toJSONString(comms) +"]");
		}
	}

	@Override
	public String getPrincipalMobile(String community_number) {
		// TODO Auto-generated method stub
		return communityModelDao.getPrincipalMobile(community_number);
	}

	@Override
	public List<CommunityModel> findByCommunityName(String communityName) {
		return communityModelDao.findByCommunityName(communityName);
	}

	@Override
	public List<Map<String, Object>> findNameByCommunityNuber(List<String> communitynumber) {		
		return communityModelDao.findNameByCommunityNuber(communitynumber);
	}

	@Override
	public List<CommunityModel> findByExecuteMethod(Integer executeMethod) {
		return communityModelDao.findByExecuteMethod(executeMethod);
	}

	@Override
	public CommunityModel findByNumberAndExecuteMethod(String communityNumber,Integer executeMethod) {
		return communityModelDao.findByNumberAndExecuteMethod(communityNumber,executeMethod);
	}

	@SuppressWarnings("null")
	@Override
	public CommunityRedisDto findRedisToDbByNumber(String communityNumber) {
		CommunityRedisDto model=redisService.hGet(String.format(CacheNameDefinition.Community_DB_Redis, communityNumber), communityNumber, CommunityRedisDto.class);
		if(model!=null){
			return model;
		}
		CommunityModel community=findByNumber(communityNumber);
		if(community != null){
		//将小区表里的部分字段取出存入缓存
		model = new CommunityRedisDto();
		model.setNo(community.getNo());
		model.setProvinceno(community.getProvinceno());
		model.setCityno(community.getCityno());
		model.setAreano(community.getAreano());
		model.setCommunity_number(community.getCommunity_number());
		model.setCommunityName(community.getCommunityName());
		model.setCommunityAddress(community.getCommunityAddress());
		model.setExecuteMethod(community.getExecuteMethod());
		model.setParkingType(community.getParkingType());
		model.setLat(community.getLat());
		model.setLng(community.getLng());
		model.setPropertyNumber(community.getProperty_number());
		model.setPropertyOfficePhone(community.getPropertyOfficePhone());
			redisService.hset(String.format(CacheNameDefinition.Community_DB_Redis, communityNumber), communityNumber, model);
			redisService.setExpiration(String.format(CacheNameDefinition.Community_DB_Redis, communityNumber), CacheNameDefinition.Default_Expiration);
		}
		return model;
	}

	@Override
	public List<CommunityModel> findByKeyWords(String keyword) {
		return communityModelDao.findByKeyWords(keyword);
	}

	@Override
	public PageResult<CommunityModel> findByLatAndLng(Double lat, Double lng,String distance, Integer pageSize, Integer pageNo) {
		return communityModelDao.findByLatAndLng(lat,lng,distance,pageSize,pageNo);
	}

	@Override
	public List<CommunityCitynoDto> findByCityNo(String cityNo) {
		return communityModelDao.findByCityNo(cityNo);
	}

	@Override
	public List<Map<String,Object>> findByAuthorizationMode(AuthorizationMode freeComeAndOut) {
		return communityModelDao.findByAuthorizationMode(freeComeAndOut);
	}

	@Override
	public List<Map<String, Object>> findByIsTemporaryParking(Double lat, Double lng, Integer distance) {
		return communityModelDao.findByIsTemporaryParking(lat, lng, distance);
	}

	@Override
	public List<CommunityModel> findByDisableFeeAgr(Integer disableTmpParkingFeeAgr, Integer disableMonthyParkingFeeAgr,
			Integer disablePropertyFeeAgr) {
		return communityModelDao.findByDisableFeeAgr(disableTmpParkingFeeAgr, disableMonthyParkingFeeAgr, disablePropertyFeeAgr);
	}

	@Override
	public List<CommunityModel> findByIsAxd(Integer isAxd) {
		return communityModelDao.findByIsAxd(isAxd);
	}

	@Override
	public List<CommunityModel> findByCommunityNumbers(List<String> communityNumbers) {
		return communityModelDao.findByCommunityNumbers(communityNumbers);
	}
	
}