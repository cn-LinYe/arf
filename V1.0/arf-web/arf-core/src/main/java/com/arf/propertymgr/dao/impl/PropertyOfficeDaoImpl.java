package com.arf.propertymgr.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyOfficeDao;
import com.arf.propertymgr.dao.IPropertyRoomInfoDao;
import com.arf.propertymgr.dto.PropertyRoomDto;
import com.arf.propertymgr.dto.PropertyRoomInfoDto;
import com.arf.propertymgr.entity.PropertyOffice;
import com.arf.propertymgr.entity.PropertyRoomInfo;
import com.arf.propertymgr.entity.PropertyRoomInfo.RoomType;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig.Compare;

@Repository("propertyOfficeDaoImpl")
public class PropertyOfficeDaoImpl extends BaseDaoImpl<PropertyOffice, Long> implements IPropertyOfficeDao {

	

	
}
