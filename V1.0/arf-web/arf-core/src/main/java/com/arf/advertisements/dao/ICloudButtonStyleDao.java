package com.arf.advertisements.dao;

import java.util.List;

import com.arf.advertisements.entity.CloudButtonStyle;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonStatus;
import com.arf.advertisements.entity.CloudButtonStyle.ButtonType;
import com.arf.core.dao.BaseDao;

public interface ICloudButtonStyleDao extends BaseDao<CloudButtonStyle, Long>{

	List<CloudButtonStyle> findByButtonTypeButtonStatusCityCode(
			ButtonType buttonType, ButtonStatus buttonStatus, String cityCode);

}
