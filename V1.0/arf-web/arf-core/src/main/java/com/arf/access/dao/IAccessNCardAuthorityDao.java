package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessNCardAuthority;
import com.arf.core.dao.BaseDao;

public interface IAccessNCardAuthorityDao extends BaseDao<AccessNCardAuthority, Long>{
	
	/**
	 * 根据门禁卡id查询
	 * @param cardId
	 * @return
	 */
	public List<AccessNCardAuthority> findByCardId(Long cardId);
	
	/**
	 * 根据用户id删除门禁卡权限
	 * @param cardId
	 */
	public void deleteByCardId(Long cardId);

}
