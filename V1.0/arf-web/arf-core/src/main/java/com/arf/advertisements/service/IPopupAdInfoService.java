package com.arf.advertisements.service;

import java.util.List;

import com.arf.advertisements.entity.PopupAdInfo;
import com.arf.advertisements.entity.PopupAdInfo.PopupStatus;
import com.arf.advertisements.entity.PopupAdInfo.PopupType;
import com.arf.core.service.BaseService;

public interface IPopupAdInfoService extends BaseService<PopupAdInfo, Long>{
	
	/**
	 * 根据弹窗位置及状态查询
	 * @param popupType
	 * @param popupStatus
	 * @return
	 */
	List<PopupAdInfo> findByPopupTypeAndStatus(PopupType popupType,PopupStatus popupStatus);

}
