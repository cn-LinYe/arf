package com.arf.access.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessUserDao;
import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessUser;
import com.arf.access.entity.AccessUser.Status;
import com.arf.access.service.IAccessManagementService;
import com.arf.access.service.IAccessUserService;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.MStringUntils;
import com.arf.propertymgr.entity.PropertyOfficeUser;
import com.arf.propertymgr.entity.PropertyRoomInfo;

@Service("accessUserServiceImpl")
@Lazy(false) //取消懒加载,初始化spring-ioc容器立即执行实例化,jsp中使用了该实例
public class AccessUserServiceImpl extends BaseServiceImpl<AccessUser, Long> implements IAccessUserService{

	private Logger log = LoggerFactory.getLogger(AccessUserServiceImpl.class);
	
	@Resource(name = "accessManagementServiceImpl")
	private IAccessManagementService accessManagementServiceImpl;
	
	private final String Perpetual_Date = "2099-12-31 23:59:59";//门禁永久时间
	
	@Resource(name="accessUserDaoImpl")
	private IAccessUserDao iAccessUserDao;

	@Override
	protected BaseDao<AccessUser, Long> getBaseDao() {
		return iAccessUserDao;
	}

	@Override
	public List<AccessUser> findByUserNameCommunity(String communityNumber, String userName) {
		return iAccessUserDao.findByUserNameCommunity(communityNumber, userName);
	}	
	
	@Override
	public List<Map<String,Object>> findByCondition(String communityNumber,String building,String unit,String keyword){
		return iAccessUserDao.findByCondition(communityNumber, building, unit, keyword);
	}

	@Override
	public AccessUser findByUserNameCommunityBuildingUnit(String communityNumber,
			String userName, String building, String unit,Status status) {
		return iAccessUserDao.findByUserNameCommunityBuildingUnit(communityNumber, userName, building, unit,status);
	}
	
	@Override
	public AccessUser findByUserNameCommunityBuildingNameUnitNameStatus(String communityNumber,
			String userName, String buildingName, String unitName,Status status) {
		return iAccessUserDao.findByUserNameCommunityBuildingNameUnitNameStatus(communityNumber, userName, buildingName, unitName,status);
	}
	
	@Override
	public AccessUser findByUserNameBoundNumberStatus(String userName, String roomBoundNumber,Status status){
		return iAccessUserDao.findByUserNameBoundNumberStatus(userName, roomBoundNumber,status);
	}

	@Override
	public AccessUser findByUserNameCommunityFloorRoom(String communityNumber,
			String userName, String floor, String room) {
		return iAccessUserDao.findByUserNameCommunityFloorRoom(communityNumber, userName,floor,room);
	}

	@Override
	public AccessUser findGateAccessByUserNameCommunity(String communityNumber,
			String userName) {
		return iAccessUserDao.findGateAccessByUserNameCommunity(communityNumber,userName);
	}

	@Override
	public void addAccessUser(CommunityModel communityModel,
			PropertyRoomInfo propertyRoomInfo, String userName,
			String nickName, String roomBoundNumber, Set<Long> accessGate,
			Set<Long> accessDoor) {
		try {
			String communityNumber = communityModel.getCommunity_number();
			String buildingName = propertyRoomInfo.getBuilding();
			String unitName = propertyRoomInfo.getUnit();
			if("无".equals(buildingName)){
				buildingName = "";
			}
			if("无".equals(unitName)){
				unitName = "";
			}
			
			Integer processBuilding = null;
			Integer processUnit = null;
			if(CollectionUtils.isNotEmpty(accessDoor)){
				for (Long accessId : accessDoor) {
					AccessManagement accessManagement = accessManagementServiceImpl.find(accessId);
					if(accessManagement != null){
						processBuilding = accessManagement.getBuilding();
						processUnit = accessManagement.getUnit();
						break;
					}
				}
			}
			
			/**
			 * 0 没有门禁
			 * 1 大门门禁
			 * 2 楼门门禁
			 */
//			int accessUserType = 0;
			//查找该绑定编号的门禁记录
			List<AccessUser> accessUserQuerys = findByUsernameBoundnumberStatus(userName, roomBoundNumber, AccessUser.Status.normal);
			if(CollectionUtils.isNotEmpty(accessUserQuerys)){
				for(AccessUser accessUserQuery : accessUserQuerys){
					accessUserQuery.setUserName(userName);
					accessUserQuery.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
					accessUserQuery.setCommunityName(communityModel.getCommunityName());
					accessUserQuery.setCommunityNumber(communityModel.getCommunity_number());
					accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
					accessUserQuery.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
					accessUserQuery.setBuildingName(("无".equals(buildingName)?"":buildingName));
					accessUserQuery.setUnitName("无".equals(unitName)?"":unitName);
					accessUserQuery.setBuilding(processBuilding==null?null:processBuilding.toString());
					accessUserQuery.setUnit(processUnit==null?null:processUnit.toString());
					accessUserQuery.setFloor(propertyRoomInfo.getFloor());
					accessUserQuery.setRoom(propertyRoomInfo.getRoom());
					Set<Long> accessGateDoor = new HashSet<Long>();
					accessGateDoor.addAll(accessGate);
					accessGateDoor.addAll(accessDoor);
					accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
					accessUserQuery.setAuthorizeStartDate(accessUserQuery.getAuthorizeStartDate());
					accessUserQuery.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
					accessUserQuery.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
					accessUserQuery.setRoomBoundNumber(roomBoundNumber);
					update(accessUserQuery);
				}
			}else{
				AccessUser accessUserQuery = new AccessUser();
				accessUserQuery.setUserName(userName);
				accessUserQuery.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
				accessUserQuery.setCommunityName(communityModel.getCommunityName());
				accessUserQuery.setCommunityNumber(communityModel.getCommunity_number());
				accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
				accessUserQuery.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
				//房间信息的building、unit，如果是“无”已处理成“”。
				accessUserQuery.setBuildingName(buildingName);
				accessUserQuery.setUnitName(unitName);
				accessUserQuery.setBuilding(processBuilding==null?null:processBuilding.toString());
				accessUserQuery.setUnit(processUnit==null?null:processUnit.toString());
				accessUserQuery.setFloor(propertyRoomInfo.getFloor());
				accessUserQuery.setRoom(propertyRoomInfo.getRoom());
				Set<Long> accessGateDoor = new HashSet<Long>();
				accessGateDoor.addAll(accessGate);
				accessGateDoor.addAll(accessDoor);
				accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
				Date now = new Date();
				accessUserQuery.setAuthorizeStartDate(now);
				accessUserQuery.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
				accessUserQuery.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
				accessUserQuery.setRoomBoundNumber(roomBoundNumber);
				save(accessUserQuery);
			}
			
			
//			if(CollectionUtils.isNotEmpty(accessDoor)){
//				for (Long accessId : accessDoor) {
//					AccessManagement accessManagement = accessManagementServiceImpl.find(accessId);
//					//楼门+大门(正常的)
//					accessUserQuery = findByUserNameCommunityBuildingUnit(communityNumber, userName,
//							accessManagement.getBuilding()==null?null:accessManagement.getBuilding().toString(),
//							accessManagement.getUnit()==null?null:accessManagement.getUnit().toString(),
//							AccessUser.Status.normal);
//					if(accessUserQuery != null){
//						accessUserType = 2;
//						processBuilding = accessManagement.getBuilding();
//						processUnit = accessManagement.getUnit();
//					}
//				}
//			}
//			
//			if(CollectionUtils.isNotEmpty(accessGate) 
//					|| CollectionUtils.isNotEmpty(accessDoor)){
//				if(accessUserQuery == null){
//					//楼门+大门(不正常的)
//					List<AccessUser> accessUsers = findByUserNameCommunityNumberRoomInfoUnnormal(communityNumber, userName,
//							processBuilding==null?null:processBuilding.toString(),
//							processUnit==null?null:processUnit.toString());
//					if(CollectionUtils.isNotEmpty(accessUsers)){
//						for (AccessUser accessUser : accessUsers) {
//							delete(accessUser);
//						}
//					}
//					//仅大门(不正常的)
//					List<AccessUser> accessusers = findByUserNameCommunityNumberRoomInfoUnnormal(communityNumber, userName,null,null);
//					if(CollectionUtils.isNotEmpty(accessusers)){
//						for (AccessUser accessUser : accessusers) {
//							delete(accessUser);
//						}
//					}
//					AccessUser accessUser = new AccessUser();
//					accessUser.setUserName(userName);
//					accessUser.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
//					accessUser.setCommunityName(communityModel.getCommunityName());
//					accessUser.setCommunityNumber(communityModel.getCommunity_number());
//					accessUser.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//					accessUser.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
//					if(CollectionUtils.isNotEmpty(accessDoor)){
//						//房间信息的building、unit，如果是“无”已处理成“”。
//						accessUser.setBuildingName(buildingName);
//						accessUser.setUnitName(unitName);
//						accessUser.setBuilding(processBuilding==null?null:processBuilding.toString());
//						accessUser.setUnit(processUnit==null?null:processUnit.toString());
//						accessUser.setFloor(propertyRoomInfo.getFloor());
//						accessUser.setRoom(propertyRoomInfo.getRoom());
//					}
//					Set<Long> accessGateDoor = new HashSet<Long>();
//					accessGateDoor.addAll(accessGate);
//					accessGateDoor.addAll(accessDoor);
//					accessUser.setAccessList(MStringUntils.join(accessGateDoor, ","));
//					Date now = new Date();
//					accessUser.setAuthorizeStartDate(now);
//					accessUser.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
//					accessUser.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
//					accessUser.setRoomBoundNumber(roomBoundNumber);
//					save(accessUser);
//				}else{
//					//查询出用户之前的门禁为大门门禁
//					if(accessUserType == 1){
//						//此时存在楼门
//						if(CollectionUtils.isNotEmpty(accessDoor)){
//							delete(accessUserQuery);
//							AccessUser accessUser = new AccessUser();
//							accessUser.setUserName(userName);
//							accessUser.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
//							accessUser.setCommunityName(communityModel.getCommunityName());
//							accessUser.setCommunityNumber(communityModel.getCommunity_number());
//							accessUser.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//							accessUser.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
//							//房间信息的building、unit，如果是“无”已处理成“”。
//							accessUser.setBuildingName(buildingName);
//							accessUser.setUnitName(unitName);
//							accessUser.setBuilding(processBuilding==null?null:processBuilding.toString());
//							accessUser.setUnit(processUnit==null?null:processUnit.toString());
//							accessUser.setFloor(propertyRoomInfo.getFloor());
//							accessUser.setRoom(propertyRoomInfo.getRoom());
//							Set<Long> accessGateDoor = new HashSet<Long>();
//							accessGateDoor.addAll(accessGate);
//							accessGateDoor.addAll(accessDoor);
//							accessUser.setAccessList(MStringUntils.join(accessGateDoor, ","));
//							Date now = new Date();
//							accessUser.setAuthorizeStartDate(now);
//							accessUser.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
//							accessUser.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
//							accessUser.setRoomBoundNumber(roomBoundNumber);
//							save(accessUser);
//						}
//						//此时只有大门
//						else{
//							Set<Long> accessGateDoor = new HashSet<Long>();
//							accessGateDoor.addAll(accessGate);
//							accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
//							if(!AccessUser.Status.normal.equals(accessUserQuery.getStatus())){
//								//用户重新绑定房间，房间绑定编号会改变
//								accessUserQuery.setRoomBoundNumber(roomBoundNumber);
//							}
//							accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//							update(accessUserQuery);
//						}
//					}
//					//查询出用户之前的门禁为（大门门禁+楼门门禁 / 楼门门禁）
//					else if(accessUserType == 2){
//						if(CollectionUtils.isNotEmpty(accessDoor)){
//							if(!AccessUser.Status.normal.equals(accessUserQuery.getStatus())){
//								accessUserQuery.setUserName(userName);
//								accessUserQuery.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
//								accessUserQuery.setCommunityName(communityModel.getCommunityName());
//								accessUserQuery.setCommunityNumber(communityModel.getCommunity_number());
//								accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//								accessUserQuery.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
//								accessUserQuery.setBuildingName(("无".equals(buildingName)?"":buildingName));
//								accessUserQuery.setUnitName("无".equals(unitName)?"":unitName);
//								accessUserQuery.setBuilding(processBuilding==null?null:processBuilding.toString());
//								accessUserQuery.setUnit(processUnit==null?null:processUnit.toString());
//								accessUserQuery.setFloor(propertyRoomInfo.getFloor());
//								accessUserQuery.setRoom(propertyRoomInfo.getRoom());
//								Set<Long> accessGateDoor = new HashSet<Long>();
//								accessGateDoor.addAll(accessGate);
//								accessGateDoor.addAll(accessDoor);
//								accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
//								accessUserQuery.setAuthorizeStartDate(accessUserQuery.getAuthorizeStartDate());
//								accessUserQuery.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
//								accessUserQuery.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
//								accessUserQuery.setRoomBoundNumber(roomBoundNumber);
//								update(accessUserQuery);
//							}else{
//								accessUserQuery.setUserName(userName);
//								accessUserQuery.setRealName(StringUtils.isBlank(nickName)?userName:nickName);
//								accessUserQuery.setCommunityName(communityModel.getCommunityName());
//								accessUserQuery.setCommunityNumber(communityModel.getCommunity_number());
//								accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//								accessUserQuery.setUserType(com.arf.access.entity.AccessUser.UserType.householder);
//								accessUserQuery.setBuildingName(("无".equals(buildingName)?"":buildingName));
//								accessUserQuery.setUnitName("无".equals(unitName)?"":unitName);
//								accessUserQuery.setBuilding(processBuilding==null?null:processBuilding.toString());
//								accessUserQuery.setUnit(processUnit==null?null:processUnit.toString());
//								accessUserQuery.setFloor(propertyRoomInfo.getFloor());
//								accessUserQuery.setRoom(propertyRoomInfo.getRoom());
//								Set<Long> accessGateDoor = new HashSet<Long>();
//								accessGateDoor.addAll(accessGate);
//								accessGateDoor.addAll(accessDoor);
//								accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
//								accessUserQuery.setAuthorizeStartDate(accessUserQuery.getAuthorizeStartDate());
//								accessUserQuery.setAuthorizeEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Perpetual_Date));
//								accessUserQuery.setAuthorizePermissions(com.arf.access.entity.AccessUser.AuthorizePermissions.allow);
//								accessUserQuery.setRoomBoundNumber(accessUserQuery.getRoomBoundNumber());
//								update(accessUserQuery);
//							}
//						}
//						else{
//							accessUserQuery.setBuildingName(null);
//							accessUserQuery.setUnitName(null);
//							accessUserQuery.setBuilding(null);
//							accessUserQuery.setUnit(null);
//							accessUserQuery.setFloor(null);
//							accessUserQuery.setRoom(null);
//							Set<Long> accessGateDoor = new HashSet<Long>();
//							accessGateDoor.addAll(accessGate);
//							accessUserQuery.setAccessList(MStringUntils.join(accessGateDoor, ","));
//							if(!AccessUser.Status.normal.equals(accessUserQuery.getStatus())){
//								//用户重新绑定房间，房间绑定编号会改变
//								accessUserQuery.setRoomBoundNumber(roomBoundNumber);
//							}
//							accessUserQuery.setStatus(com.arf.access.entity.AccessUser.Status.normal);
//							update(accessUserQuery);
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			log.error(">>>>>[查询我的房间接口getMyRooms]：初始化门禁 异常", e);
		}
	}

	@Override
	public List<AccessUser> findByUsernameBoundnumberStatus(String userName,
			String boundNumber, Status status) {
		return iAccessUserDao.findByUsernameBoundnumberStatus(userName, boundNumber,status);
	}

	@Override
	public List<AccessUser> findByUserNameCommunityNumberRoomInfoUnnormal(
			String communityNumber, String userName, String building,
			String unit) {
		return iAccessUserDao.findByUserNameCommunityNumberRoomInfoUnnormal(communityNumber, userName, building, unit);
	}

	@Override
	public List<AccessUser> findByBoundnumbersStatus(List<String> boundNumbers,
			Status status) {
		return iAccessUserDao.findByBoundnumbersStatus(boundNumbers,status);
	}

	@Override
	public List<AccessUser> findByUserNameStatus(String user_name, int status) {
		return iAccessUserDao.findByUserNameStatus(user_name,status);
	}
	@Override
	public List<AccessUser> findByUserNamesAndCommunityNumbers(List<String> numList, List<String> nameList) {		
		return iAccessUserDao.findByUserNamesAndCommunityNumbers(numList,nameList);
	}
}
