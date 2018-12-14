package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessNCardDao;
import com.arf.access.entity.AccessNCard;
import com.arf.access.service.IAccessNCardService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessNCardService")
public class AccessNCardServiceImpl extends BaseServiceImpl<AccessNCard, Long> implements IAccessNCardService{

	@Resource(name="accessNCardDao")
	IAccessNCardDao accessNCardDao;

	@Override
	protected BaseDao<AccessNCard, Long> getBaseDao() {
		return accessNCardDao;
	}

	@Override
	public List<AccessNCard> findByRoomNumberAndCardNumber(String roomNumber, String cardNumber) {
		return accessNCardDao.findByRoomNumberAndCardNumber(roomNumber, cardNumber);
	}

	@Override
	public int findByRoomNumber(String roomNumber) {
		return accessNCardDao.findByRoomNumber(roomNumber);
	}

	@Override
	public AccessNCard findByCardNumber(String cardNumber) {
		return accessNCardDao.findByCardNumber(cardNumber);
	}

	@Override
	public List<AccessNCard> findByRoomList(List<String> roomList) {
		return accessNCardDao.findByRoomList(roomList);
	}

}
