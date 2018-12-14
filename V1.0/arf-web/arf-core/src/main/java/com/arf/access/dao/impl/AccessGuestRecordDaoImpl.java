package com.arf.access.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessGuestRecordDao;
import com.arf.access.entity.AccessGuestRecord;
import com.arf.access.entity.AccessGuestRecord.Status;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessGuestRecordDaoImpl")
public class AccessGuestRecordDaoImpl extends
		BaseDaoImpl<AccessGuestRecord, Long> implements IAccessGuestRecordDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public PageResult<Map<String, Object>> findListByCondition(
			Integer pageSize, Integer pageNo, List<String> userName, Status status) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from access_guest_record a");
		countHql.append(" where 1=1 and a.user_name in (:userName) and a.status in (0,1,2,4)");
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.access_barcode accessBarcode,");
		queryHql.append(" a.access_user_id accessUserId,");
		queryHql.append(" a.allow_date allowDate,");
		queryHql.append(" a.apply_date applyDate,");
		queryHql.append(" a.avatar_url avatarUrl,");
		queryHql.append(" a.community_number communityNumber,");
		queryHql.append(" a.guest_identify_id guestIdentifyId,");
		queryHql.append(" a.guest_type guestType,");
		queryHql.append(" a.nickname,");
		queryHql.append(" a.reason,");
		queryHql.append(" a.status,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.building_name buildingName,");
		queryHql.append(" a.unit_name unitName,");
		queryHql.append(" b.user_type userType,");
		queryHql.append(" b.building,");
		queryHql.append(" b.unit,");
		queryHql.append(" b.floor,");
		queryHql.append(" b.room,");
		queryHql.append(" c.community_name communityName");
		/*queryHql.append(" d.photo userAvatar,");
		queryHql.append(" d.nickname userNickname");*/
		queryHql.append(" from access_guest_record a");
		queryHql.append(" left join access_user b on b.id = a.access_user_id");
		queryHql.append(" left join community c on c.community_number = a.community_number");
		/*queryHql.append(" left join xx_member d on d.username = a.user_name");*/
		queryHql.append(" where 1=1 and a.user_name in (:userName) and a.status in (0,1,2,4)");
		if(status!=null){
			queryHql.append(" and a.status =:status");
			countHql.append(" and a.status =:status");
		}
		queryHql.append(" order by a.create_date desc");
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		queryCount.setParameter("userName", userName);
		typedQuery.setParameter("userName", userName);
		if(status!=null){
			queryCount.setParameter("status", status.ordinal());
			typedQuery.setParameter("status", status.ordinal());
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
		pageResult.setTotalNum(count);
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<Map<String,Object>> findListByCondition(Integer pageSize, Integer pageNo, 
			String userName, String guestIdentifyId,Date startDate,Date endDate){
		StringBuffer countHql = new StringBuffer("select count(1) as COUNT from access_guest_record a ");
		countHql.append(" where 1=1 ");
		if(userName != null){
			countHql.append(" and a.user_name = :userName ");
		}
		if(startDate != null){
			countHql.append(" and a.apply_date >= :startDate ");
		}
		if(endDate != null){
			countHql.append(" and a.apply_date <= :endDate ");
		}
		countHql.append(" and a.guest_identify_id =:guestIdentifyId and a.status !=:deletedStatus and a.status !=:timeoutStatus ");
		
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.access_barcode accessBarcode,");
		queryHql.append(" a.access_user_id accessUserId,");
		queryHql.append(" a.allow_date allowDate,");
		queryHql.append(" a.apply_date applyDate,");
		queryHql.append(" a.avatar_url avatarUrl,");
		queryHql.append(" a.community_number communityNumber,");
		queryHql.append(" a.guest_identify_id guestIdentifyId,");
		queryHql.append(" a.guest_type guestType,");
		queryHql.append(" a.nickname,");
		queryHql.append(" a.reason,");
		queryHql.append(" a.status,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.access_temp_passwd accessTempPasswd,");
//		queryHql.append(" b.user_type userType,");
//		queryHql.append(" b.building,");
//		queryHql.append(" b.unit,");
//		queryHql.append(" b.building_name buildingName,");
//		queryHql.append(" b.unit_name unitName,");
		queryHql.append(" b.building buildingName,");
		queryHql.append(" b.unit unitName,");
		queryHql.append(" b.floor,");
		queryHql.append(" b.room,");
		queryHql.append(" c.community_name communityName");
		//queryHql.append(" d.photo userAvatar,");
		//queryHql.append(" d.nickname userNickname");
		queryHql.append(" from access_guest_record a");
		queryHql.append(" left join pty_property_room_info b on b.room_number = a.room_number");
		queryHql.append(" left join community c on c.community_number = a.community_number");
		//queryHql.append(" left join xx_member d on d.username = a.user_name");
		queryHql.append(" where 1=1 ");
		
		if(userName != null){
			queryHql.append(" and a.user_name = :userName ");
		}
		if(startDate != null){
			queryHql.append(" and a.apply_date >= :startDate ");
		}
		if(endDate != null){
			queryHql.append(" and a.apply_date <= :endDate ");
		}
		
		queryHql.append(" and a.guest_identify_id =:guestIdentifyId and a.status != :deletedStatus and a.status !=:timeoutStatus ");
		queryHql.append(" order by a.allow_date desc, a.create_date desc");
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		if(userName != null){
			queryCount.setParameter("userName", userName);
		}
		if(startDate != null){
			queryCount.setParameter("startDate", startDate);
		}
		if(endDate != null){
			queryCount.setParameter("endDate", endDate);
		}
		queryCount.setParameter("guestIdentifyId", guestIdentifyId);
		queryCount.setParameter("deletedStatus", AccessGuestRecord.Status.DELETED.ordinal());
		queryCount.setParameter("timeoutStatus", AccessGuestRecord.Status.TIMEOUT.ordinal());
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		if(userName != null){
			typedQuery.setParameter("userName", userName);
		}
		if(startDate != null){
			typedQuery.setParameter("startDate", startDate);
		}
		if(endDate != null){
			typedQuery.setParameter("endDate", endDate);
		}
		typedQuery.setParameter("guestIdentifyId", guestIdentifyId);
		typedQuery.setParameter("deletedStatus", AccessGuestRecord.Status.DELETED.ordinal());
		typedQuery.setParameter("timeoutStatus", AccessGuestRecord.Status.TIMEOUT.ordinal());
		Integer count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("AccessGuestRecordDaoImpl.findByCondition查询数据条数错误",e);
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		pageResult.setTotalNum(count);
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

	@Override
	public AccessGuestRecord findByIdUsername(Long id,Status status) {
		StringBuffer hql = new StringBuffer("from AccessGuestRecord where 1 = 1");
		hql.append(" and id = :id and status = :status");
		TypedQuery<AccessGuestRecord> query = this.entityManager.createQuery(hql.toString(), AccessGuestRecord.class);
		query.setParameter("id", id);
		query.setParameter("status", status);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int delete(String id, String userName) {
		StringBuffer hql = new StringBuffer("update access_guest_record set status = 5 where user_name = :userName and id = :id");
		Query query = this.entityManager.createNativeQuery(hql.toString());
		query.setParameter("userName", userName);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	public List<AccessGuestRecord> findByGuestIdentifyIdStatus(String guestIdentifyId,
			Status status) {
		StringBuffer hql = new StringBuffer("from AccessGuestRecord where 1 = 1");
		hql.append(" and guestIdentifyId = :guestIdentifyId and status = :status");
		TypedQuery<AccessGuestRecord> query = this.entityManager.createQuery(hql.toString(), AccessGuestRecord.class);
		query.setParameter("guestIdentifyId", guestIdentifyId);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public int updateReadStatus(List<Long> idList) {
		if(CollectionUtils.isEmpty(idList)){
			return 0;
		}
		String sql = "update access_guest_record set read_status = " + AccessGuestRecord.ReadStatus.READ.ordinal() + " where id in(:idList) ";
		Query query = this.entityManager.createNativeQuery(sql);
		query.setParameter("idList", idList);
		return query.executeUpdate();
	}

	@Override
	public void modifyOverdue(int applytimeouthour) {
		StringBuffer hql = new StringBuffer("update access_guest_record set status = :statuschange where status = :status");
		hql.append(" and (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(apply_date) > :applytimeouthour * 60 * 60)");
		Query query = this.entityManager.createNativeQuery(hql.toString());
		query.setParameter("status", AccessGuestRecord.Status.NEW.ordinal());
		query.setParameter("statuschange", AccessGuestRecord.Status.TIMEOUT.ordinal());
		query.setParameter("applytimeouthour", applytimeouthour);
		query.executeUpdate();
	}

	@Override
	public AccessGuestRecord findByIdStatus(Long id, Status status) {
		StringBuffer hql = new StringBuffer("from AccessGuestRecord where 1 = 1");
		hql.append(" and id = :id and status = :status");
		TypedQuery<AccessGuestRecord> query = this.entityManager.createQuery(hql.toString(), AccessGuestRecord.class);
		query.setParameter("id", id);
		query.setParameter("status", status);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageResult<Map<String, Object>> findByBoundNumber(String roomNumber, Status status, Integer pageSize,
			Integer pageNo) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from access_guest_record a");
		countHql.append(" where 1=1 and a.room_number =:roomNumber and a.status in (0,1,2,4)");
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.access_barcode accessBarcode,");
		queryHql.append(" a.access_user_id accessUserId,");
		queryHql.append(" a.allow_date allowDate,");
		queryHql.append(" a.apply_date applyDate,");
		queryHql.append(" a.avatar_url avatarUrl,");
		queryHql.append(" a.community_number communityNumber,");
		queryHql.append(" a.guest_identify_id guestIdentifyId,");
		queryHql.append(" a.guest_type guestType,");
		queryHql.append(" a.nickname,");
		queryHql.append(" a.reason,");
		queryHql.append(" a.status,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.room_bound_number roomBoundNumber,");
		queryHql.append(" a.room_number roomNumber,");
		queryHql.append(" b.user_type userType,");
		queryHql.append(" b.building,");
		queryHql.append(" b.unit,");
		queryHql.append(" b.floor,");
		queryHql.append(" b.room,");
		queryHql.append(" c.community_name communityName");
		queryHql.append(" from access_guest_record a");
		queryHql.append(" left join access_user b on b.id = a.access_user_id");
		queryHql.append(" left join community c on c.community_number = a.community_number");
		queryHql.append(" where 1=1 and a.room_number =:roomNumber and a.status in (0,1,2,4)");
		if(status!=null){
			queryHql.append(" and a.status =:status");
			countHql.append(" and a.status =:status");
		}
		queryHql.append(" order by a.create_date desc");
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		queryCount.setParameter("roomNumber", roomNumber);
		typedQuery.setParameter("roomNumber", roomNumber);
		if(status!=null){
			queryCount.setParameter("status", status.ordinal());
			typedQuery.setParameter("status", status.ordinal());
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
		pageResult.setTotalNum(count);
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

	@Override
	public int findCountByRoomAndStatus(String roomNumber, Status status) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from access_guest_record a");
		countHql.append(" where 1=1 and a.room_number =:roomNumber and a.status in (0,1,2,4)");
		if(status!=null){
			countHql.append(" and a.status =:status");
		}
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("roomNumber", roomNumber);
		if(status!=null){
			queryCount.setParameter("status", status.ordinal());
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
}
