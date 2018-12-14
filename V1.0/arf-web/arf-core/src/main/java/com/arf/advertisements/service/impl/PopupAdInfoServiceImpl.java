package com.arf.advertisements.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.advertisements.dao.IPopupAdInfoDao;
import com.arf.advertisements.entity.PopupAdInfo;
import com.arf.advertisements.entity.PopupAdInfo.PopupStatus;
import com.arf.advertisements.entity.PopupAdInfo.PopupType;
import com.arf.advertisements.service.IPopupAdInfoService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("popupAdInfoService")
public class PopupAdInfoServiceImpl extends BaseServiceImpl<PopupAdInfo, Long> implements IPopupAdInfoService{
	
	@Resource(name="popupAdInfoDao")
	IPopupAdInfoDao popupAdInfoDao;

	@Override
	protected BaseDao<PopupAdInfo, Long> getBaseDao() {
		return popupAdInfoDao;
	}

	@Override
	public List<PopupAdInfo> findByPopupTypeAndStatus(PopupType popupType, PopupStatus popupStatus) {
		return popupAdInfoDao.findByPopupTypeAndStatus(popupType, popupStatus);
	}

}
