package com.arf.advertisements.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.advertisements.dao.ICloudButtonStyleDao;
import com.arf.advertisements.entity.CloudButtonStyle;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonStatus;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonType;
import com.arf.advertisements.service.ICloudButtonStyleService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("cloudButtonStyleService")
public class CloudButtonStyleServiceImpl extends BaseServiceImpl<CloudButtonStyle, Long> implements ICloudButtonStyleService{

	@Resource(name="cloudButtonStyleDao")
	ICloudButtonStyleDao cloudButtonStyleDao;
	
	@Override
	protected BaseDao<CloudButtonStyle, Long> getBaseDao() {
		return cloudButtonStyleDao;
	}

	@Override
	public List<CloudButtonStyle> findByButtonTypeButtonStatusCityCode(
			ButtonType buttonType, ButtonStatus buttonStatus, String cityCode) {
		return cloudButtonStyleDao.findByButtonTypeButtonStatusCityCode(buttonType,buttonStatus,cityCode);
	}

}
