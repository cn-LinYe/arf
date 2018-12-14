package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessNCard;
import com.arf.core.dao.BaseDao;

public interface IAccessNCardDao extends BaseDao<AccessNCard, Long>{
	/**
	 * 根据房屋编号及卡号查询
	 * @param roomNumber
	 * @param cardNumber
	 * @return
	 */
	public List<AccessNCard> findByRoomNumberAndCardNumber(String roomNumber, String cardNumber);
	
	/**
	 * 查询某个房间绑定的门禁卡数
	 * @param roomNumber
	 * @return
	 */
	public int findByRoomNumber(String roomNumber);
	
	/**
	 * 根据卡号查询
	 * @param cardNumber
	 * @return
	 */
	public AccessNCard findByCardNumber(String cardNumber);
	
	/**
	 * 根据房屋编号及卡号查询
	 * @param roomNumber
	 * @param cardNumber
	 * @return
	 */
	public List<AccessNCard> findByRoomList(List<String> roomList);
	
}
