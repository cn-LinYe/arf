package com.arf.violation.service.impl;

import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.base.PageResult;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.violation.dao.ITrafficViolationRecordDao;
import com.arf.violation.entity.TrafficViolationRecord;
import com.arf.violation.service.ITrafficViolationRecordService;
@Service("trafficViolationRecordServiceImpl")
public class TrafficViolationRecordServiceImpl extends BaseServiceImpl<TrafficViolationRecord,Long> implements ITrafficViolationRecordService,ServletContextAware{

	private Logger log = LoggerFactory.getLogger(TrafficViolationRecordServiceImpl.class);
	
	@Resource(name="trafficViolationRecordDaoImpl")
	ITrafficViolationRecordDao trafficViolationRecordDao;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	private ServletContext servletContext;
	
	public static final String AREA_FILE = "/resources/commoncfg/shengshiqu.json";
	public static final String Arfweb_Verification_Condition = "Arfweb_Verification_Condition:";
	
	@Override
	protected BaseDao<TrafficViolationRecord, Long> getBaseDao() {
		return trafficViolationRecordDao;
	}
	@Override
	public TrafficViolationRecord findByUniqueCode(String uniqueCode) {
		return trafficViolationRecordDao.findByUniqueCode(uniqueCode);
	}
	@Override
	public List<TrafficViolationRecord> findByUniqueCode(String[] uniqueCode,TrafficViolationRecord.Status status) {
		return trafficViolationRecordDao.findByUniqueCode(uniqueCode,status);
	}
	@Override
	public PageResult<TrafficViolationRecord> findByLicense(String license,Integer pageNo,Integer pageSize) {
		return trafficViolationRecordDao.findByLicense(license,pageNo,pageSize);
	}
	@Override
	public TrafficViolationRecord findBySecondaryUniqueCode(String secondaryUniqueCode) {
		return trafficViolationRecordDao.findBySecondaryUniqueCode(secondaryUniqueCode);
	}
	@Override
	public List<TrafficViolationRecord> findBysecondaryUniqueCodes(List<String> list) {
		return trafficViolationRecordDao.findBysecondaryUniqueCodes(list);
	}
	@Override
	public void saveAllViolationRecord(JSONArray jsonArray,String license) {
		trafficViolationRecordDao.saveAllViolationRecord(jsonArray,license);
	}
	@Override
	public List<TrafficViolationRecord> findByUniqueCode(List<String> uniqueCode) {
		return trafficViolationRecordDao.findByUniqueCode(uniqueCode);
	}
	@Override
	public List<TrafficViolationRecord> findByLiscenseTime(String license, List<Date> list) {
		return trafficViolationRecordDao.findByLiscenseTime(license, list);
	}
	@Override
	public Integer[] getVerificationCondition(String licensePlateNumber) {
		Integer violationsQueryCarEngineLen = null;
		Integer violationsQueryCarCodeLen = null;
		//先读缓存
		String licensePlateNumberPrefix = licensePlateNumber.substring(0, 2);
		String verificationCondition = redisService.get(Arfweb_Verification_Condition + licensePlateNumberPrefix);
		if(StringUtils.isNotBlank(verificationCondition)){
			JSONObject jsonResult = JSON.parseObject(verificationCondition);
			violationsQueryCarEngineLen = jsonResult.getInteger("violationsQueryCarEngineLen");
			violationsQueryCarCodeLen = jsonResult.getInteger("violationsQueryCarCodeLen");
		}else{
			//缓存没有，再读文件，之后写到缓存（24H）
			try {
				//读文件
				org.springframework.core.io.Resource industriesResource = WebApplicationContextUtils
						.getWebApplicationContext(this.servletContext)
						.getResource(AREA_FILE);
				EncodedResource encResource = new EncodedResource(industriesResource, "UTF-8");
				Reader reader = encResource.getReader();
				String fromFile = FileCopyUtils.copyToString(reader);
				JSONObject areaJSONObject = JSONObject.parseObject(fromFile);
				JSONArray provinces = areaJSONObject.getJSONArray("provinces");
			Label_ParkRate: 
				for (Object province : provinces) {
					areaJSONObject = (JSONObject) province;
					String licensePrefix = areaJSONObject.getString("licensePrefix");
					if (StringUtils.isBlank(licensePrefix)) {
						// TODO
						break Label_ParkRate;
					}
					//京津沪渝（直辖市）
					if ("京".equals(licensePrefix) 
							|| "津".equals(licensePrefix) 
							|| "沪".equals(licensePrefix)
							|| "渝".equals(licensePrefix)) {
						if (licensePrefix.equals(licensePlateNumber.substring(0, 1))) {
							violationsQueryCarEngineLen = areaJSONObject.getInteger("violationsQueryCarEngineLen");
							violationsQueryCarCodeLen = areaJSONObject.getInteger("violationsQueryCarCodeLen");
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("licensePrefix", licensePlateNumberPrefix);
							map.put("violationsQueryCarEngineLen", violationsQueryCarEngineLen);
							map.put("violationsQueryCarCodeLen", violationsQueryCarCodeLen);
							redisService.set(Arfweb_Verification_Condition + licensePlateNumberPrefix, map, 24 * 60 * 60);
							break;
						}
					} else {
						JSONArray cities = areaJSONObject.getJSONArray("cities");
						boolean exit = false;
						for (Object city : cities) {
							areaJSONObject = (JSONObject) city;
							licensePrefix = areaJSONObject.getString("licensePrefix");
							if (StringUtils.isBlank(licensePrefix)) {
								// TODO
								continue Label_ParkRate;
							}
							if (licensePrefix.substring(0, 1).equals(licensePlateNumber.substring(0, 1))) {
								if (licensePrefix.equals(licensePlateNumber.substring(0, 2))) {
									violationsQueryCarEngineLen = areaJSONObject.getInteger("violationsQueryCarEngineLen");
									violationsQueryCarCodeLen = areaJSONObject.getInteger("violationsQueryCarCodeLen");
									Map<String,Object> map = new HashMap<String,Object>();
									map.put("licensePrefix", licensePlateNumberPrefix);
									map.put("violationsQueryCarEngineLen", violationsQueryCarEngineLen);
									map.put("violationsQueryCarCodeLen", violationsQueryCarCodeLen);
									redisService.set(Arfweb_Verification_Condition + licensePlateNumberPrefix, map, 24 * 60 * 60);
									exit = true;
									break;
								}
							} else {
								break;
							}
						}
						if (exit) {
							break;
						}
					}
				}
			} catch (Exception e) {
				log.error(">>>>>获取车牌验证条件 com.arf.violation.service.impl.TrafficViolationRecordServiceImpl.getVerificationCondition(String) ", e);
				violationsQueryCarEngineLen = 99;
				violationsQueryCarCodeLen = 99;
			}
		}
		Integer[] inter = new Integer[2];
		inter[0] =  violationsQueryCarCodeLen;
		inter[1] =  violationsQueryCarEngineLen;
		return inter;
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
