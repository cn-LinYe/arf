package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessAuthorizationDao;
import com.arf.access.entity.AccessAuthorization;
import com.arf.access.entity.AccessAuthorization.Status;
import com.arf.access.service.IAccessAuthorizationService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessAuthorizationServiceImpl")
@Lazy(false)
public class AccessAuthorizationServiceImpl extends BaseServiceImpl<AccessAuthorization, Long>
		implements IAccessAuthorizationService {

	@Resource(name = "accessAuthorizationDaoImpl")
	private IAccessAuthorizationDao accessAuthorizationDao;

	@Override
	protected BaseDao<AccessAuthorization, Long> getBaseDao() {
		return accessAuthorizationDao;
	}

	@Override
	public List<AccessAuthorization> findByCommunityNumberAndUserName(String communityNumber, String authorizeUserName,
			String userName) {
		return accessAuthorizationDao.findByCommunityNumberAndUserName(communityNumber, authorizeUserName, userName);
	}

	@Override
	public List<AccessAuthorization> findByCommunityNumberAndUserNameStatus(String communityNumber,
			String authorizeUserName, String userName, List<AccessAuthorization.Status> status) {
		return accessAuthorizationDao.findByCommunityNumberAndUserNameStatus(communityNumber, authorizeUserName,
				userName, status);
	}
	
	@Override
	public AccessAuthorization findByUserNameRoom(String userName,String communityNumber,String building,String unit,String floor,String room){
		return accessAuthorizationDao.findByUserNameRoom(userName, communityNumber, building, unit, floor, room);
	}

	@Override
	public List<AccessAuthorization> findByUserName(String userName) {
		return accessAuthorizationDao.findByUserName(userName);
	}

	@Override
	public void updateStatus(List<Long> ids, Status status) {
		accessAuthorizationDao.updateStatus(ids, status);
	}

	@Override
	public List<AccessAuthorization> findByDateStatus(Status status) {
		return accessAuthorizationDao.findByDateStatus(status);
	}

	@Override
	public void updateByArrayIds(List<Long> ids,Status status) {
		accessAuthorizationDao.updateByArrayIds(ids,status);
	}

	@Override
	public List<AccessAuthorization> findAllUserByHousrHolderRoomNumber(String userName, String roomNumber,Status status){
		return accessAuthorizationDao.findAllUserByHousrHolderRoomNumber(userName, roomNumber, status);
	}

	@Override
	public int updateByBoundNumbers(String[] boundNumbersArray, Status status) {
		return accessAuthorizationDao.updateByBoundNumbers(boundNumbersArray,status);
	}

	@Override
	public List<AccessAuthorization> findByRoomBoundNumber(String boundNumber,
			Status status) {
		return accessAuthorizationDao.findByRoomBoundNumber(boundNumber,status);
	}

	@Override
	public AccessAuthorization findByUserNameCommunityNumberRoomInfo(
			String userName, String communityNumber, String building,
			String unit, AccessAuthorization.Status status) {
		return accessAuthorizationDao.findByUserNameCommunityNumberRoomInfo(userName,communityNumber,building,unit,status);
	}

	@Override
	public List<AccessAuthorization> findByUserNameCommunityNumberRoomInfoUnnormal(
			String userName, String communityNumber, String building,
			String unit) {
		return accessAuthorizationDao.findByUserNameCommunityNumberRoomInfoUnnormal(userName,communityNumber,building,unit);
	}

	@Override
	public List<AccessAuthorization> findByRoomBoundList(List<String> boundList, Status status) {
		return accessAuthorizationDao.findByRoomBoundList(boundList, status);
	}
}
