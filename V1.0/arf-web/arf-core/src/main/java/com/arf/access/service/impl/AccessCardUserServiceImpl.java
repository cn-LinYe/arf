package com.arf.access.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessCardUserDao;
import com.arf.access.dto.AccessCardUserDto;
import com.arf.access.entity.AccessCardUser;
import com.arf.access.service.IAccessCardUserService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessCardUserServiceImpl")
public class AccessCardUserServiceImpl extends
		BaseServiceImpl<AccessCardUser, Long> implements IAccessCardUserService {

	@Resource(name = "accessCardUserDaoImpl")
	private IAccessCardUserDao accessCardUserDaoImpl;
	
	@Override
	protected BaseDao<AccessCardUser, Long> getBaseDao() {
		return accessCardUserDaoImpl;
	}

	@Override
	public List<AccessCardUser> findByUserName(String userName) {
		return accessCardUserDaoImpl.findByUserName(userName);
	}

	@Override
	public List<AccessCardUser> findByUserNameCommunity(String userName,
			String communityNumber,String boundNumber) {
		return accessCardUserDaoImpl.findByUserNameCommunity(userName,communityNumber,boundNumber);
	}

	@Override
	public AccessCardUser findByChipNumUserName(String chipNum,String userName) {
		return accessCardUserDaoImpl.findByChipNumUserName(chipNum,userName);
	}

	@Override
	public List<AccessCardUserDto> findByUserNameJoinCommunityAccess(
			String userName,String boundNumber) {
		return accessCardUserDaoImpl.findByUserNameJoinCommunityAccess(userName,boundNumber);
	}

	@Override
	public List<AccessCardUser> findByUserNameRoomBoundNumber(String userName,
			String boundNumber) {
		return accessCardUserDaoImpl.findByUserNameRoomBoundNumber(userName,boundNumber);
	}

	@Override
	public AccessCardUser findbyChipNum(String chipNum) {
		return accessCardUserDaoImpl.findbyChipNum(chipNum);
	}

	@Override
	public List<AccessCardUserDto> findbyCardNumUsername(String cardNum,String userName) {
		return accessCardUserDaoImpl.findbyCardNumUsername(cardNum,userName);
	}

	@Override
	public List<AccessCardUserDto> findByRoomNumber(String roomNumber) {
		return accessCardUserDaoImpl.findByRoomNumber(roomNumber);
	}

	@Override
	public List<AccessCardUserDto> findByRoomList(List<String> roomList) {
		return accessCardUserDaoImpl.findByRoomList(roomList);
	}

	@Override
	public List<Map<String,Object>> findByCommunity(String communityNumber, String cardVersion) {
		return accessCardUserDaoImpl.findByCommunity(communityNumber, cardVersion);
	}

	@Override
	public List<AccessCardUser> findByChipNumList(List<String> chipNumList) {
		return accessCardUserDaoImpl.findByChipNumList(chipNumList);
	}

	@Override
	public List<Map<String, Object>> findByCommunityAndId(List<String> communityNums,List<Long> ids) {
		return accessCardUserDaoImpl.findByCommunityAndId(communityNums, ids);
	}

	@Override
	public List<AccessCardUser> findByIdList(List<Long> idList) {
		return accessCardUserDaoImpl.findByIdList(idList);
	}

	@Override
	public void updateByChipNum(String chipNum, Long cardId) {
		accessCardUserDaoImpl.updateByChipNum(chipNum, cardId);
	}

	
	
}
