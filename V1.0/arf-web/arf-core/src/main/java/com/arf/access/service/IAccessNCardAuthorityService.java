package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessNCardAuthority;
import com.arf.core.service.BaseService;

public interface IAccessNCardAuthorityService extends BaseService<AccessNCardAuthority, Long>{
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
