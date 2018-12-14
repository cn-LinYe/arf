package com.arf.propertymgr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.propertymgr.dao.IPropertyRoomUserBindDao;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind.Status;
import com.arf.propertymgr.service.IPropertyRoomUserBindService;

@Service("propertyRoomUserBindServiceImpl")
public class PropertyRoomUserBindServiceImpl extends BaseServiceImpl<PropertyRoomUserBind, Long> implements IPropertyRoomUserBindService {

	@Resource(name = "propertyRoomUserBindDaoImpl")
	private IPropertyRoomUserBindDao propertyRoomUserBindDaoImpl;
	
	@Override
	protected BaseDao<PropertyRoomUserBind, Long> getBaseDao() {
		return propertyRoomUserBindDaoImpl;
	}

	@Override
	public List<PropertyRoomUserBind> findByUserName(String userName) {
		return propertyRoomUserBindDaoImpl.findByUserName(userName);
	}

	@Override
	public PropertyRoomUserBind findByUserNameBoundNumber(String userName,String boundNumber) {
		return propertyRoomUserBindDaoImpl.findByUserNameBoundNumber(userName,boundNumber);
	}
	
	@Override
	public PropertyRoomUserBind findByUserNameBoundNumberNotUnBound(String userName,String boundNumber) {
		return propertyRoomUserBindDaoImpl.findByUserNameBoundNumberNotUnBound(userName, boundNumber);
	}

	@Override
	public PropertyRoomUserBind findByRoomNumber(String roomNumber) {
		return propertyRoomUserBindDaoImpl.findByRoomNumber(roomNumber);
	}
	
	@Override
	public List<PropertyRoomUserBind> findListByRoomNumber(String roomNumber){
		return propertyRoomUserBindDaoImpl.findListByRoomNumber(roomNumber);
	}
	
	@Override
	public PropertyRoomUserBind findByBoundNumber(String boundNumber) {
		List<PropertyRoomUserBind> list = this.findList(null, (List<Order>)null, 
				new Filter("boundNumber",Operator.eq,boundNumber),
				new Filter("status",Operator.ne,-1)
				);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Override
	public PropertyRoomUserBind findByBoundNumberAll(String boundNumber) {
		List<PropertyRoomUserBind> list = this.findList(null, (List<Order>)null, 
				new Filter("boundNumber",Operator.eq,boundNumber)
				);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public PropertyRoomUserBind saveOrUpdate(PropertyRoomUserBind propertyRoomUserBind) {
		if(propertyRoomUserBind.getId() != null && propertyRoomUserBind.getId() > 0){
			return this.update(propertyRoomUserBind);
		}else{
			return this.save(propertyRoomUserBind);
		}
	}

	@Override
	public List<PropertyRoomUserBind> findByUserNameStatus(String userName,
			Status status) {
		return propertyRoomUserBindDaoImpl.findByUserNameStatus(userName,status);
	}

	@Override
	public List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgr(
			String userName) {
		return propertyRoomUserBindDaoImpl.findByUserNameNewMgruploadModifyedMgr(userName);
	}

	@Override
	public PropertyRoomUserBind findHouseHolderByRoomNumber(String roomNumber) {
		return propertyRoomUserBindDaoImpl.findHouseHolderByRoomNumber(roomNumber);
	}

	@Override
	public List<PropertyRoomUserBind> findByRoomNumbers(List<String> roomNumbers) {
		return propertyRoomUserBindDaoImpl.findByRoomNumbers(roomNumbers);
	}

	@Override
	public Integer findBindRoomCount(String userName,String communityNumber) {
		return propertyRoomUserBindDaoImpl.findBindRoomCount(userName, communityNumber);
	}
	@Override
	public List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgrRefuse(
			String userName) {
		return propertyRoomUserBindDaoImpl.findByUserNameNewMgruploadModifyedMgrRefuse(userName);
	}

	@Override
	public List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber, String userName, Status status) {
		return propertyRoomUserBindDaoImpl.findByCommunityUserNameStatus(communityNumber, userName, status);
	}


}
