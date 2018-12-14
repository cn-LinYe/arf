package com.arf.access.dao.impl;

import java.text.SimpleDateFormat;
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

import com.arf.access.dao.IAccessCardUserDao;
import com.arf.access.dto.AccessCardUserDto;
import com.arf.access.entity.AccessCardUser;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessCardUserDaoImpl")
public class AccessCardUserDaoImpl extends BaseDaoImpl<AccessCardUser, Long>
		implements IAccessCardUserDao {

	@Override
	public List<AccessCardUser> findByUserName(String userName) {
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where user_name = :userName order by create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUser> accessCardUsers = new ArrayList<AccessCardUser>(); 
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUser accessCardUser = new AccessCardUser();
				Object id =  map.get("id");
				Object createDate =  map.get("create_date");
				Object modifyDate =  map.get("modify_date");
				Object version =  map.get("version");
				Object cardNum =  map.get("card_num");
				Object status =  map.get("status");
				Object reportedLostDate =  map.get("reported_lost_date");
				Object accessNum =  map.get("access_num");
				Object communityNumber =  map.get("community_number");
				Object boundDate =  map.get("bound_date");
				Object endDate =  map.get("end_date");
				Object chipNum =  map.get("chip_num");
				Object roomBoundNumber =  map.get("room_bound_number");
				accessCardUser.setId(Long.valueOf(id + ""));
				accessCardUser.setCreateDate((Date)createDate);
				accessCardUser.setModifyDate((Date)modifyDate);
				accessCardUser.setVersion(Long.valueOf(version + ""));
				accessCardUser.setUserName(userName);
				accessCardUser.setCardNum(cardNum == null?null:cardNum.toString());
				accessCardUser.setStatus(status == null?null:Integer.valueOf(status.toString()));
				accessCardUser.setReportedLostDate(reportedLostDate==null?null:(Date)reportedLostDate);
				accessCardUser.setAccessNum(accessNum == null?null:accessNum.toString());
				accessCardUser.setCommunityNumber(communityNumber == null?null:communityNumber.toString());
				accessCardUser.setBoundDate(boundDate==null?null:(Date)boundDate);
				accessCardUser.setEndDate(endDate==null?null:(Date)endDate);
				accessCardUser.setChipNum(chipNum == null?null:chipNum.toString());
				accessCardUser.setRoomBoundNumber(roomBoundNumber == null?null:roomBoundNumber.toString());
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUser.setFreezeStatus(freezeStatus==null?null:AccessCardUser.FreezeStatus.values()[(int)freezeStatus]);
				accessCardUser.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUser.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUser.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUser.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUser.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUser.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUser.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				accessCardUsers.add(accessCardUser);
			}
		}
		return accessCardUsers;
	}

	@Override
	public List<AccessCardUser> findByUserNameCommunity(String userName,
			String communityNumber,String boundNumber) {
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where user_name = :userName and community_number = :communityNumber and room_bound_number = :boundNumber order by create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("boundNumber", boundNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUser> accessCardUsers = new ArrayList<AccessCardUser>(); 
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUser accessCardUser = new AccessCardUser();
				Object id =  map.get("id");
				Object createDate =  map.get("create_date");
				Object modifyDate =  map.get("modify_date");
				Object version =  map.get("version");
				Object cardNum =  map.get("card_num");
				Object status =  map.get("status");
				Object reportedLostDate =  map.get("reported_lost_date");
				Object accessNum =  map.get("access_num");
				Object boundDate =  map.get("bound_date");
				Object endDate =  map.get("end_date");
				Object chipNum =  map.get("chip_num");
				Object roomBoundNumber =  map.get("room_bound_number");
				accessCardUser.setId(Long.valueOf(id + ""));
				accessCardUser.setCreateDate((Date)createDate);
				accessCardUser.setModifyDate((Date)modifyDate);
				accessCardUser.setVersion(Long.valueOf(version + ""));
				accessCardUser.setUserName(userName);
				accessCardUser.setCardNum(cardNum == null?null:cardNum.toString());
				accessCardUser.setStatus(status == null?null:Integer.valueOf(status.toString()));
				accessCardUser.setReportedLostDate(reportedLostDate==null?null:(Date)reportedLostDate);
				accessCardUser.setAccessNum(accessNum == null?null:accessNum.toString());
				accessCardUser.setCommunityNumber(communityNumber == null?null:communityNumber.toString());
				accessCardUser.setBoundDate(boundDate==null?null:(Date)boundDate);
				accessCardUser.setEndDate(endDate==null?null:(Date)endDate);
				accessCardUser.setChipNum(chipNum == null?null:chipNum.toString());
				accessCardUser.setRoomBoundNumber(roomBoundNumber == null?null:roomBoundNumber.toString());
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUser.setFreezeStatus(freezeStatus==null?null:AccessCardUser.FreezeStatus.values()[(int)freezeStatus]);
				accessCardUser.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUser.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUser.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUser.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUser.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUser.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUser.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				accessCardUsers.add(accessCardUser);
			}
		}
		return accessCardUsers;
	}

	@Override
	public AccessCardUser findByChipNumUserName(String chipNum,String userName) {
		StringBuffer hql = new StringBuffer("from AccessCardUser where chipNum = :chipNum and userName = :userName order by createDate desc");
		TypedQuery<AccessCardUser> typedQuery = super.entityManager.createQuery(hql.toString(), AccessCardUser.class);
		typedQuery.setParameter("chipNum", chipNum);
		typedQuery.setParameter("userName", userName);
		try {
			return typedQuery.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AccessCardUserDto> findByUserNameJoinCommunityAccess(
			String userName,String boundNumber) {
		StringBuffer hql = new StringBuffer("select l.* from(");
		hql.append(" select");
		hql.append(" a.*,co.community_name,accma.access_name,accma.bluetooth_mac");
		hql.append(" from access_card_user a");
		hql.append(" left join community co on co.community_number = a.community_number");
		hql.append(" left join access_management accma on accma.access_num = a.access_num");
		hql.append(" where a.user_name = :user_name");
		if(StringUtils.isNotBlank(boundNumber)){
			hql.append(" and a.room_bound_number = :room_bound_number");
		}
		hql.append(" order by a.create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("user_name", userName);
		if(StringUtils.isNotBlank(boundNumber)){
			query.setParameter("room_bound_number", boundNumber);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUserDto> reusultReverse = new ArrayList<AccessCardUserDto>();
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUserDto accessCardUserDto = new AccessCardUserDto();
				Object id = map.get("id");
				Object version = map.get("version");
				accessCardUserDto.setId(Long.valueOf(id + ""));
				accessCardUserDto.setVersion(Long.valueOf(version + ""));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date create_date = (Date)map.get("create_date");
				if(create_date != null){
					accessCardUserDto.setCreateDate(sdf.format(create_date));
				}
				Date modify_date = (Date)map.get("modify_date");
				if(modify_date != null){
					accessCardUserDto.setModifyDate(sdf.format(modify_date));
				}
				accessCardUserDto.setAccessNum((String)map.get("access_num"));
				Date bound_date = (Date)map.get("bound_date");
				if(bound_date != null){
					accessCardUserDto.setBoundDate(sdf.format(bound_date));
				}
				accessCardUserDto.setCardNum((String)map.get("card_num"));
				accessCardUserDto.setChipNum((String)map.get("chip_num"));
				accessCardUserDto.setCardUseType((Integer)map.get("cardUseType"));
				accessCardUserDto.setRoomList((String)map.get("roomList"));
				accessCardUserDto.setCommunityNumber((String)map.get("community_number"));
				Date end_date = (Date)map.get("end_date");
				if(end_date != null){
					accessCardUserDto.setEndDate(sdf.format(end_date));
				}
				Date reported_lost_date = (Date)map.get("reported_lost_date");
				if(reported_lost_date != null){
					accessCardUserDto.setReportedLostDate(sdf.format(reported_lost_date));
				}
				accessCardUserDto.setStatus((Integer)map.get("status"));
				accessCardUserDto.setUserName((String)map.get("user_name"));
				accessCardUserDto.setCommunityName((String)map.get("community_name"));
				accessCardUserDto.setAccessName((String)map.get("access_name"));
				accessCardUserDto.setBluetoothMac((String)map.get("bluetooth_mac"));
				accessCardUserDto.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				accessCardUserDto.setAuthencateType(map.get("authencate_type")==null?1:(Integer)map.get("authencate_type"));
				Object roomNumber =  map.get("room_number");
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUserDto.setRoomNumber(roomNumber == null?null:roomNumber.toString());
				accessCardUserDto.setFreezeStatus(freezeStatus==null?null:Integer.valueOf(freezeStatus.toString()));
				accessCardUserDto.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUserDto.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUserDto.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUserDto.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUserDto.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUserDto.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				reusultReverse.add(accessCardUserDto);
			}
		}
		return reusultReverse;
	}

	@Override
	public List<AccessCardUser> findByUserNameRoomBoundNumber(String userName,
			String boundNumber) {
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where user_name = :userName and room_bound_number = :boundNumber order by create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("userName", userName);
		query.setParameter("boundNumber", boundNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUser> accessCardUsers = new ArrayList<AccessCardUser>(); 
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUser accessCardUser = new AccessCardUser();
				Object id =  map.get("id");
				Object createDate =  map.get("create_date");
				Object modifyDate =  map.get("modify_date");
				Object version =  map.get("version");
				Object cardNum =  map.get("card_num");
				Object status =  map.get("status");
				Object reportedLostDate =  map.get("reported_lost_date");
				Object accessNum =  map.get("access_num");
				Object communityNumber =  map.get("community_number");
				Object boundDate =  map.get("bound_date");
				Object endDate =  map.get("end_date");
				Object chipNum =  map.get("chip_num");
				Object roomBoundNumber =  map.get("room_bound_number");
				accessCardUser.setId(Long.valueOf(id + ""));
				accessCardUser.setCreateDate((Date)createDate);
				accessCardUser.setModifyDate((Date)modifyDate);
				accessCardUser.setVersion(Long.valueOf(version + ""));
				accessCardUser.setUserName(userName);
				accessCardUser.setCardNum(cardNum == null?null:cardNum.toString());
				accessCardUser.setStatus(status == null?null:Integer.valueOf(status.toString()));
				accessCardUser.setReportedLostDate(reportedLostDate==null?null:(Date)reportedLostDate);
				accessCardUser.setAccessNum(accessNum == null?null:accessNum.toString());
				accessCardUser.setCommunityNumber(communityNumber == null?null:communityNumber.toString());
				accessCardUser.setBoundDate(boundDate==null?null:(Date)boundDate);
				accessCardUser.setEndDate(endDate==null?null:(Date)endDate);
				accessCardUser.setChipNum(chipNum == null?null:chipNum.toString());
				accessCardUser.setRoomBoundNumber(roomBoundNumber == null?null:roomBoundNumber.toString());
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUser.setFreezeStatus(freezeStatus==null?null:AccessCardUser.FreezeStatus.values()[(int)freezeStatus]);
				accessCardUser.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUser.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUser.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUser.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUser.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUser.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUser.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				accessCardUsers.add(accessCardUser);
			}
		}
		return accessCardUsers;
	}

	@Override
	public AccessCardUser findbyChipNum(String chipNum) {
		StringBuffer hql = new StringBuffer("from AccessCardUser where chipNum = :chipNum order by createDate desc");
		TypedQuery<AccessCardUser> typedQuery = super.entityManager.createQuery(hql.toString(), AccessCardUser.class);
		typedQuery.setParameter("chipNum", chipNum);
		try {
			return typedQuery.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AccessCardUserDto> findbyCardNumUsername(String cardNum,String userName) {
		StringBuffer hql = new StringBuffer("select l.* from(");
		hql.append(" select");
		hql.append(" a.*,co.community_name,accma.access_name,accma.bluetooth_mac");
		hql.append(" from access_card_user a");
		hql.append(" left join community co on co.community_number = a.community_number");
		hql.append(" left join access_management accma on accma.access_num = a.access_num");
		hql.append(" where a.card_num = :cardNum");
		hql.append(" and a.user_name = :userName");
		hql.append(" order by a.create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("cardNum", cardNum);
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUserDto> reusultReverse = new ArrayList<AccessCardUserDto>();
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUserDto accessCardUserDto = new AccessCardUserDto();
				Object id = map.get("id");
				Object version = map.get("version");
				accessCardUserDto.setId(Long.valueOf(id + ""));
				accessCardUserDto.setVersion(Long.valueOf(version + ""));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date create_date = (Date)map.get("create_date");
				if(create_date != null){
					accessCardUserDto.setCreateDate(sdf.format(create_date));
				}
				Date modify_date = (Date)map.get("modify_date");
				if(modify_date != null){
					accessCardUserDto.setModifyDate(sdf.format(modify_date));
				}
				accessCardUserDto.setAccessNum((String)map.get("access_num"));
				Date bound_date = (Date)map.get("bound_date");
				if(bound_date != null){
					accessCardUserDto.setBoundDate(sdf.format(bound_date));
				}
				accessCardUserDto.setCardNum((String)map.get("card_num"));
				accessCardUserDto.setChipNum((String)map.get("chip_num"));
				accessCardUserDto.setCommunityNumber((String)map.get("community_number"));
				Date end_date = (Date)map.get("end_date");
				if(end_date != null){
					accessCardUserDto.setEndDate(sdf.format(end_date));
				}
				Date reported_lost_date = (Date)map.get("reported_lost_date");
				if(reported_lost_date != null){
					accessCardUserDto.setReportedLostDate(sdf.format(reported_lost_date));
				}
				accessCardUserDto.setStatus((Integer)map.get("status"));
				accessCardUserDto.setUserName((String)map.get("user_name"));
				accessCardUserDto.setCommunityName((String)map.get("community_name"));
				accessCardUserDto.setAccessName((String)map.get("access_name"));
				accessCardUserDto.setBluetoothMac((String)map.get("bluetooth_mac"));
				accessCardUserDto.setAuthencateType(map.get("authencate_type")==null?1:(Integer)map.get("authencate_type"));
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUserDto.setFreezeStatus(freezeStatus==null?null:Integer.valueOf(freezeStatus.toString()));
				accessCardUserDto.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUserDto.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUserDto.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUserDto.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUserDto.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUserDto.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUserDto.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				reusultReverse.add(accessCardUserDto);
			}
		}
		return reusultReverse;
	}

	@Override
	public List<AccessCardUserDto> findByRoomNumber(String roomNumber) {
		StringBuffer hql = new StringBuffer("select l.* from(");
		hql.append(" select");
		hql.append(" a.*,co.community_name,accma.access_name,accma.bluetooth_mac");
		hql.append(" from access_card_user a");
		hql.append(" left join community co on co.community_number = a.community_number");
		hql.append(" left join access_management accma on accma.access_num = a.access_num");
		hql.append(" where 1=1 ");
		if(StringUtils.isNotBlank(roomNumber)){
			hql.append(" and a.room_number = :roomNumber");
		}
		hql.append(" order by a.create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		if(StringUtils.isNotBlank(roomNumber)){
			query.setParameter("roomNumber", roomNumber);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUserDto> reusultReverse = new ArrayList<AccessCardUserDto>();
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUserDto accessCardUserDto = new AccessCardUserDto();
				Object id = map.get("id");
				Object version = map.get("version");
				accessCardUserDto.setId(Long.valueOf(id + ""));
				accessCardUserDto.setVersion(Long.valueOf(version + ""));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date create_date = (Date)map.get("create_date");
				if(create_date != null){
					accessCardUserDto.setCreateDate(sdf.format(create_date));
				}
				Date modify_date = (Date)map.get("modify_date");
				if(modify_date != null){
					accessCardUserDto.setModifyDate(sdf.format(modify_date));
				}
				accessCardUserDto.setAccessNum((String)map.get("access_num"));
				Date bound_date = (Date)map.get("bound_date");
				if(bound_date != null){
					accessCardUserDto.setBoundDate(sdf.format(bound_date));
				}
				accessCardUserDto.setCardNum((String)map.get("card_num"));
				accessCardUserDto.setChipNum((String)map.get("chip_num"));
				accessCardUserDto.setCommunityNumber((String)map.get("community_number"));
				Date end_date = (Date)map.get("end_date");
				if(end_date != null){
					accessCardUserDto.setEndDate(sdf.format(end_date));
				}
				Date reported_lost_date = (Date)map.get("reported_lost_date");
				if(reported_lost_date != null){
					accessCardUserDto.setReportedLostDate(sdf.format(reported_lost_date));
				}
				accessCardUserDto.setStatus((Integer)map.get("status"));
				accessCardUserDto.setUserName((String)map.get("user_name"));
				accessCardUserDto.setCommunityName((String)map.get("community_name"));
				accessCardUserDto.setAccessName((String)map.get("access_name"));
				accessCardUserDto.setBluetoothMac((String)map.get("bluetooth_mac"));
				accessCardUserDto.setRoomBoundNumber((String)map.get("room_bound_number"));
				accessCardUserDto.setRoomNumber((String)map.get("room_number"));
				accessCardUserDto.setAuthencateType(map.get("authencate_type")==null?1:(Integer)map.get("authencate_type"));
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				accessCardUserDto.setRoomNumber(roomNumber == null?null:roomNumber.toString());
				accessCardUserDto.setFreezeStatus(freezeStatus==null?null:Integer.valueOf(freezeStatus.toString()));
				accessCardUserDto.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUserDto.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUserDto.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUserDto.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUserDto.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUserDto.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUserDto.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				reusultReverse.add(accessCardUserDto);
			}
		}
		return reusultReverse;
	}

	@Override
	public List<AccessCardUserDto> findByRoomList(List<String> roomList) {
		StringBuffer hql = new StringBuffer("select l.* from(");
		hql.append(" select");
		hql.append(" a.*,co.community_name,accma.access_name,accma.bluetooth_mac");
		hql.append(" from access_card_user a");
		hql.append(" left join community co on co.community_number = a.community_number");
		hql.append(" left join access_management accma on accma.access_num = a.access_num");
		hql.append(" where 1=1 ");
		if(CollectionUtils.isNotEmpty(roomList)){
			hql.append(" and a.room_number in(:roomList)");
		}
		hql.append(" order by a.create_date desc");
		hql.append(" ) l");
		hql.append(" group by chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		if(CollectionUtils.isNotEmpty(roomList)){
			query.setParameter("roomList", roomList);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUserDto> reusultReverse = new ArrayList<AccessCardUserDto>();
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUserDto accessCardUserDto = new AccessCardUserDto();
				Object id = map.get("id");
				Object version = map.get("version");
				accessCardUserDto.setId(Long.valueOf(id + ""));
				accessCardUserDto.setVersion(Long.valueOf(version + ""));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date create_date = (Date)map.get("create_date");
				if(create_date != null){
					accessCardUserDto.setCreateDate(sdf.format(create_date));
				}
				Date modify_date = (Date)map.get("modify_date");
				if(modify_date != null){
					accessCardUserDto.setModifyDate(sdf.format(modify_date));
				}
				accessCardUserDto.setAccessNum((String)map.get("access_num"));
				Date bound_date = (Date)map.get("bound_date");
				if(bound_date != null){
					accessCardUserDto.setBoundDate(sdf.format(bound_date));
				}
				accessCardUserDto.setCardNum((String)map.get("card_num"));
				accessCardUserDto.setChipNum((String)map.get("chip_num"));
				accessCardUserDto.setCommunityNumber((String)map.get("community_number"));
				Date end_date = (Date)map.get("end_date");
				if(end_date != null){
					accessCardUserDto.setEndDate(sdf.format(end_date));
				}
				Date reported_lost_date = (Date)map.get("reported_lost_date");
				if(reported_lost_date != null){
					accessCardUserDto.setReportedLostDate(sdf.format(reported_lost_date));
				}
				accessCardUserDto.setStatus((Integer)map.get("status"));
				accessCardUserDto.setUserName((String)map.get("user_name"));
				accessCardUserDto.setCommunityName((String)map.get("community_name"));
				accessCardUserDto.setAccessName((String)map.get("access_name"));
				accessCardUserDto.setBluetoothMac((String)map.get("bluetooth_mac"));
				accessCardUserDto.setRoomBoundNumber((String)map.get("room_bound_number"));
				accessCardUserDto.setRoomNumber((String)map.get("room_number"));
				accessCardUserDto.setAuthencateType(map.get("authencate_type")==null?1:(Integer)map.get("authencate_type"));
				accessCardUserDto.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				accessCardUserDto.setFreezeStatus(map.get("freeze_status")==null?null:(Integer)map.get("freeze_status"));
				reusultReverse.add(accessCardUserDto);
			}
		}
		return reusultReverse;
	}

	@Override
	public List<Map<String,Object>> findByCommunity(String communityNumber, String cardVersion) {
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where community_number = :communityNumber and card_version = :cardVersion and is_send=0 order by create_date desc");
		hql.append(" ) l");
		hql.append(" group by l.chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("cardVersion", cardVersion);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		return reusult;
	}

	@Override
	public List<AccessCardUser> findByChipNumList(List<String> chipNumList) {
		if(CollectionUtils.isEmpty(chipNumList)){
			return null;
		}
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where chip_num in (:chipNumList) order by create_date desc");
		hql.append(" ) l");
		hql.append(" group by l.chip_num");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("chipNumList", chipNumList);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		List<AccessCardUser> accessCardUsers = new ArrayList<AccessCardUser>(); 
		if(CollectionUtils.isNotEmpty(reusult)){
			for (Map<String, Object> map : reusult) {
				AccessCardUser accessCardUser = new AccessCardUser();
				Object id =  map.get("id");
				Object createDate =  map.get("create_date");
				Object modifyDate =  map.get("modify_date");
				Object version =  map.get("version");
				Object cardNum =  map.get("card_num");
				Object status =  map.get("status");
				Object reportedLostDate =  map.get("reported_lost_date");
				Object accessNum =  map.get("access_num");
				Object boundDate =  map.get("bound_date");
				Object endDate =  map.get("end_date");
				Object chipNum =  map.get("chip_num");
				Object roomBoundNumber =  map.get("room_bound_number");
				Object cardType =  map.get("card_type");
				Object activateType =  map.get("activate_type");
				Object roomNumber =  map.get("room_number");
				Object lostType =  map.get("lost_type");
				Object cardSource =  map.get("card_source");
				Object authencateType =  map.get("authencate_type");
				Object freezeStatus =  map.get("freeze_status");
				Object updateDate =  map.get("update_date");
				Object effectiveDate =  map.get("effective_date");
				Object effectiveMac =  map.get("effective_mac");
				Object totalTimes =  map.get("total_times");
				Object userName =  map.get("user_name");
				Object isSend =  map.get("is_send");
				Object laveTimes =  map.get("lave_times");
				Object receivedAccess =  map.get("received_access");
				Object communityNumber =  map.get("community_number");
				accessCardUser.setId(Long.valueOf(id + ""));
				accessCardUser.setCreateDate((Date)createDate);
				accessCardUser.setModifyDate((Date)modifyDate);
				accessCardUser.setVersion(Long.valueOf(version + ""));
				accessCardUser.setUserName(userName == null?null:userName.toString());
				accessCardUser.setCardNum(cardNum == null?null:cardNum.toString());
				accessCardUser.setStatus(status == null?null:Integer.valueOf(status.toString()));
				accessCardUser.setReportedLostDate(reportedLostDate==null?null:(Date)reportedLostDate);
				accessCardUser.setAccessNum(accessNum == null?null:accessNum.toString());
				accessCardUser.setCommunityNumber(communityNumber == null?null:communityNumber.toString());
				accessCardUser.setBoundDate(boundDate==null?null:(Date)boundDate);
				accessCardUser.setEndDate(endDate==null?null:(Date)endDate);
				accessCardUser.setChipNum(chipNum == null?null:chipNum.toString());
				accessCardUser.setRoomBoundNumber(roomBoundNumber == null?null:roomBoundNumber.toString());
				accessCardUser.setCardType(cardType==null?null:AccessCardUser.CardType.values()[(int)cardType]);
				accessCardUser.setActivateType(activateType==null?null:AccessCardUser.ActivateType.values()[(int)activateType]);
				accessCardUser.setRoomNumber(roomNumber == null?null:roomNumber.toString());
				accessCardUser.setLostType(lostType==null?null:AccessCardUser.LostType.values()[(int)lostType]);
				accessCardUser.setCardSource(cardSource==null?null:AccessCardUser.CardSource.values()[(int)cardSource]);
				accessCardUser.setAuthencateType(authencateType==null?null:AccessCardUser.AuthencateType.values()[(int)authencateType]);
				accessCardUser.setFreezeStatus(freezeStatus==null?null:AccessCardUser.FreezeStatus.values()[(int)freezeStatus]);
				accessCardUser.setUpdateDate(updateDate==null?null:(Date)updateDate);
				accessCardUser.setEffectiveDate(effectiveDate==null?null:(Date)effectiveDate);
				accessCardUser.setEffectiveMac(effectiveMac==null?null:effectiveMac.toString());
				accessCardUser.setTotalTimes(totalTimes==null?null:Integer.valueOf(totalTimes.toString()));
				accessCardUser.setLaveTimes(laveTimes==null?null:Integer.valueOf(laveTimes.toString()));
				accessCardUser.setReceivedAccess(receivedAccess==null?null:receivedAccess.toString());
				accessCardUser.setCardVersion(map.get("card_version")==null?null:(String)map.get("card_version"));
				if(isSend!=null){
					if("0".equals(isSend.toString())){
						accessCardUser.setIsSend(false);
					}else{
						accessCardUser.setIsSend(true);
					}
				}
				accessCardUsers.add(accessCardUser);
			}
		}
		return accessCardUsers;
	}

	@Override
	public List<Map<String, Object>> findByCommunityAndId(List<String> communityNums,List<Long> ids) {
		StringBuffer hql = new StringBuffer("select l.* from( ");
		hql.append(" select * from access_card_user where 1=1 and community_number in (:communityNumbers) ");
		if(CollectionUtils.isNotEmpty(ids)){
			hql.append(" and id not in (:ids)");
		}
		hql.append(" and card_version = :cardVersion and is_send=0 order by create_date desc");
		hql.append(" ) l group by l.chip_num");
		hql.append(" order by l.update_date ");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("communityNumbers", communityNums);
		if(CollectionUtils.isNotEmpty(ids)){
			query.setParameter("ids", ids);
		}
		query.setParameter("cardVersion", "V2");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> reusult = query.getResultList();
		return reusult;
	}

	@Override
	public List<AccessCardUser> findByIdList(List<Long> idList) {
		if (CollectionUtils.isEmpty(idList)) {
			return null;
		}
		StringBuffer sb =new StringBuffer(" from AccessCardUser where 1=1 and id in (:ids)");
		TypedQuery<AccessCardUser> query =this.entityManager.createQuery(sb.toString(),AccessCardUser.class);
		query.setParameter("ids", idList);
		return query.getResultList();
	}

	@Override
	public void updateByChipNum(String chipNum, Long cardId) {
		StringBuffer hql = new StringBuffer("update access_card_user set is_send=:isSend where chip_num = :chipNum and id != :cardId");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("isSend", true);
		query.setParameter("chipNum", chipNum);
		query.setParameter("cardId", cardId);
		query.executeUpdate();
	}

}
