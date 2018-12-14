package com.arf.propertymgr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyRoomSubuserBindDao;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.Status;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.UserType;
import com.arf.propertymgr.service.IPropertyRoomSubuserBindService;

@Service("propertyRoomSubuserBindServiceImpl")
@Lazy(false)
public class PropertyRoomSubuserBindServiceImpl extends BaseServiceImpl<PropertyRoomSubuserBind, Long> implements IPropertyRoomSubuserBindService {

	@Resource(name = "propertyRoomSubuserBindDaoImpl")
	private IPropertyRoomSubuserBindDao propertyRoomSubuserBindDao;
	
	@Override
	protected BaseDao<PropertyRoomSubuserBind, Long> getBaseDao() {
		return propertyRoomSubuserBindDao;
	}
	
	@Override
	public PropertyRoomSubuserBind save(PropertyRoomSubuserBind entity) {
		entity.setNeedFirstAlert(true);
		return super.save(entity);
	}
	
	@Override
	public PropertyRoomSubuserBind findByBoundNumber(String boundNumber) {
		return propertyRoomSubuserBindDao.findByBoundNumber(boundNumber);
	}

	@Override
	public PropertyRoomSubuserBind findByUserNameRoomNumber(String userName,String roomNumber){
		return propertyRoomSubuserBindDao.findByUserNameRoomNumber(userName, roomNumber);
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findByHouseHolderRoomNumber(
			String houseHolder, String roomNumber) {
		return propertyRoomSubuserBindDao.findByHouseHolderRoomNumber(houseHolder,roomNumber);
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findNewUserByRoomNumber(String roomNumber){
		return propertyRoomSubuserBindDao.findNewUserByRoomNumber(roomNumber);
	}

	@Override
	public List<PropertyRoomSubuserBind> findByUserName(String userName) {
		return propertyRoomSubuserBindDao.findByUserName(userName);
	}

	@Override
	public List<PropertyRoomSubuserBind> findByBoundNumbers(
			String[] boundNumbersArray) {
		return propertyRoomSubuserBindDao.findByBoundNumbers(boundNumbersArray);
	}

	@Override
	public int updateByBoundNumbers(String[] boundNumbersArray,Status status,String unboundUser) {
		return propertyRoomSubuserBindDao.updateByBoundNumbers(boundNumbersArray,status,unboundUser);
	}

	@Override
	public List<PropertyRoomSubuserBind> findByUserNameUserType(
			String userName, UserType userType) {
		return propertyRoomSubuserBindDao.findByUserNameUserType(userName,userType);
	}

	@Override
	public PropertyRoomSubuserBind findManager(String roomNumber) {
		return propertyRoomSubuserBindDao.findManager(roomNumber);
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findManagerByRoomNumber(String roomNumber){
		return propertyRoomSubuserBindDao.findManagerByRoomNumber(roomNumber);
	}

	@Override
	public List<PropertyRoomSubuserBind> findByRoomNumber(String roomNumber) {
		return propertyRoomSubuserBindDao.findByRoomNumber(roomNumber);
	}

	@Override
	public List<PropertyRoomSubuserBind> findListByRoomNumber(String roomNumber) {
		return propertyRoomSubuserBindDao.findListByRoomNumber(roomNumber);
	}

	@Override
	public List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber, String userName,
			Status status) {
		return propertyRoomSubuserBindDao.findByCommunityUserNameStatus(communityNumber, userName, status);
	}

	@Override
	public List<PropertyRoomSubuserBind> findByRoomList(List<String> roomList) {
		return propertyRoomSubuserBindDao.findByRoomList(roomList);
	}
}
