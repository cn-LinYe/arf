package com.arf.advertisements.service;

import java.util.List;

import com.arf.advertisements.entity.CloudButtonStyle;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonStatus;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonType;
import com.arf.core.service.BaseService;

public interface ICloudButtonStyleService extends BaseService<CloudButtonStyle, Long>{

	List<CloudButtonStyle> findByButtonTypeButtonStatusCityCode(
			ButtonType buttonType, ButtonStatus buttonStatus, String cityCode);

}
