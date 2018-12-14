package com.arf.access.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.arf.access.dao.IAccessManagementDao;
import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessManagement.AccessType;
import com.arf.access.entity.AccessManagement.AuthencateAccessType;
import com.arf.access.entity.AccessManagement.Status;
import com.arf.access.service.IAccessManagementService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessManagementServiceImpl")
public class AccessManagementServiceImpl extends BaseServiceImpl<AccessManagement, Long> implements IAccessManagementService{
	
	private Logger log = LoggerFactory.getLogger(AccessManagementServiceImpl.class);

	private static String BUILDINGNAME_SURFIX = "栋|幢|座$";
	private static String UNITNAME_SURFIX = "单元|门$";
	
	@Resource(name="accessManagementDaoImpl")
	private IAccessManagementDao accessManagementDao;
	
	@Override
	protected BaseDao<AccessManagement, Long> getBaseDao() {
		return accessManagementDao;
	}

	@Override
	public List<AccessManagement> findByIds(Long[] array) {
		return accessManagementDao.findByIds(array);
	}

	@Override
	public List<AccessManagement> findByIdsAndStatus(Long[] Ids, String bluetoothMac, Status status) {
		return accessManagementDao.findByIdsAndStatus(Ids, bluetoothMac, status);
	}

	@Override
	public List<AccessManagement> findByCommunityNumberAndStatus(String communityNumber,String keyword, String bluetoothMac,Status status) {
		return accessManagementDao.findByCommunityNumberAndStatus(communityNumber, keyword, bluetoothMac, status);
	}

	@Override
	public List<AccessManagement> findByCommunityNumberMacIsnull(String communityNumber) {
		return accessManagementDao.findByCommunityNumberMacIsnull(communityNumber);
	}
	
	@Override
	public AccessManagement findByAccessNum(String accessNum) {
		return accessManagementDao.findByAccessNum(accessNum);
	}

	@Override
	public List<AccessManagement> findByCommunityStatusAccessType(
			String communityNumber, Status status, AccessType accessType) {
		return accessManagementDao.findByCommunityStatusAccessType(communityNumber,status,accessType);
	}

	@Override
	public AccessManagement findByCommunityStatusAccessTypeRoom(
			String communityNumber, Status status, AccessType accessType,
			String buildingName, String unitName) {
		return accessManagementDao.findByCommunityStatusAccessTypeRoom(
				communityNumber,status,accessType,buildingName,unitName);
	}

	@Override
	public AccessManagement findByCommunityBuildingInfo(String communityNumber,
			String buildingName, String unitName) {
		return accessManagementDao.findByCommunityBuildingInfo(communityNumber,buildingName,unitName);
	}

	@Override
	public List<AccessManagement> findAccessManagementByBuildingAndUnit(
			String communityNumber, Integer building, Integer unit) {
		return accessManagementDao.findAccessManagementByBuildingAndUnit(communityNumber,building,unit);
	}

	@Override
	public List<AccessManagement> findByCommunity(String communityNumber) {
		return accessManagementDao.findByCommunity(communityNumber);
	}
	
	@Override
	public AccessManagement findByBluetoothMac(String bluetoothMac){
		return accessManagementDao.findByBluetoothMac( bluetoothMac);
	}

	@Override
	public Map<String, Object> processBuildingUnitInfo(String communityNumber,
			String buildingName, String unitName) {
		Map<String,Object> infoMap = new HashMap<String,Object>();
		if(StringUtils.isBlank(communityNumber)){
			log.info("processBuildingUnitInfo处理门禁楼栋单元标号结果：infoMap = "+ JSON.toJSONString(infoMap));
			return infoMap;
		}
		//根据BUILDING_NAME,UNIT_NAME处理building,unit标号
		if(StringUtils.isBlank(buildingName)){
			log.info("processBuildingUnitInfo处理门禁楼栋单元标号结果：infoMap = "+ JSON.toJSONString(infoMap));
			return infoMap;
		}
		Integer building = null;
		Integer unit = null;
		buildingName = buildingName.trim().replaceAll("\\s+", "");
		
		if(buildingName.matches(".*" + BUILDINGNAME_SURFIX)){ //带有楼栋尾称的
			String building$  = buildingName.replaceAll(BUILDINGNAME_SURFIX,"");
			if(building$.matches("^[0-9]+$")){ //纯数字的
				building = Integer.parseInt(building$);
			}else if(building$.matches("^[0-9a-zA-Z]+$")){ //纯数字+字母的
				
			}else{ //其它的
				
			}
		}else{ //不带楼栋尾称的
			if(buildingName.matches("^[0-9]+$")){ //纯数字的
				building = Integer.parseInt(buildingName);
				buildingName = buildingName + "栋";
			}else if(buildingName.matches("^[0-9a-zA-Z]+$")){ //纯数字+字母的
				buildingName = buildingName + "栋";
			}else{ //其它的
				
			}
		}
		
		if(StringUtils.isNotBlank(unitName)){
			unitName = unitName.trim().replaceAll("\\s+", "");
			if(unitName.matches(".*" + UNITNAME_SURFIX)){ //带有单元尾称的
				String unit$  = unitName.replaceAll(UNITNAME_SURFIX, "");
				if(unit$.matches("^[0-9]+$")){ //纯数字的
					unit = Integer.parseInt(unit$);
				}else if(unit$.matches("^[0-9a-zA-Z]+$")){ //纯数字+字母的
					
				}else{ //其它的
					
				}
			}else{ //不带单元尾称的
				if(unitName.matches("^[0-9]+$")){ //纯数字的
					unit = Integer.parseInt(unitName);
					unitName = unitName + "单元";
				}else if(unitName.matches("^[0-9a-zA-Z]+$")){ //纯数字+字母的
					unitName = unitName + "单元";
				}else{ //其它的
					
				}
			}
		}
		if(building == null || (StringUtils.isNotBlank(unitName) && unit == null)){
			AccessManagement buildingInfo = 
					findByCommunityBuildingInfo(communityNumber, buildingName, unitName);
			if(buildingInfo != null){
				building = buildingInfo.getBuilding();
				unit = buildingInfo.getUnit();
			}else{
				infoMap.put("BUILDING_NAME", buildingName);
				infoMap.put("UNIT_NAME", unitName);
				infoMap.put("RET", 101); //101 : 根据楼栋单元名称没有查询数据库中存在的信息
				log.info("processBuildingUnitInfo处理门禁楼栋单元标号结果：infoMap = "+ JSON.toJSONString(infoMap));
				return infoMap;
			}
		}else{
			List<AccessManagement> buildingInfos = 
					findAccessManagementByBuildingAndUnit(communityNumber, building, unit);
			boolean none = true;
			if(CollectionUtils.isNotEmpty(buildingInfos)){
				for(AccessManagement accessManagement : buildingInfos){
					if(accessManagement.getAccessType() == 1){
						none = false;
						break;
					}
				}
			}
			if(none){
				infoMap.put("RET", 102); //102 : 根据楼栋单元标号没有查询数据库中存在的信息
			}
		}
		infoMap.put("BUILDING_NAME", buildingName);
		infoMap.put("UNIT_NAME", unitName);
		infoMap.put("BUILDING", building);
		infoMap.put("UNIT", unit);
		log.info("processBuildingUnitInfo处理门禁楼栋单元标号结果：infoMap = "+ JSON.toJSONString(infoMap));
		return infoMap;
	}

	@Override
	public List<AccessManagement> findByCommunityBuildingnameUnitname(
			String communityNumber, String building, String unit) {
		return accessManagementDao.findByCommunityBuildingnameUnitname(communityNumber,building,unit);
	}

	@Override
	public AccessManagement findByBluetoothMacAndStatus(String bluetoothMac, Status status) {
		return accessManagementDao.findByBluetoothMacAndStatus(bluetoothMac, status);
	}

	@Override
	public List<AccessManagement> findByStatus(Status status,AuthencateAccessType authencateAccessType) {
		return accessManagementDao.findByStatus(status,authencateAccessType);
	}

	@Override
	public List<AccessManagement> findByCommunitys(List<String> communityNumbers) {		
		return accessManagementDao.findByCommunitys(communityNumbers);
	}
}
