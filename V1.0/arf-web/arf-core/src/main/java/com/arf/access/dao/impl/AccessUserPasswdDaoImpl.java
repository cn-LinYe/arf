package com.arf.access.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessUserPasswdDao;
import com.arf.access.entity.AccessUserPasswd;
import com.arf.access.entity.AccessUserPasswd.UseStatus;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessUserPasswdDao")
public class AccessUserPasswdDaoImpl 
		extends BaseDaoImpl<AccessUserPasswd, Long> 
		implements IAccessUserPasswdDao {

	@Override
	public List<AccessUserPasswd> findByBlueMacAndPwd(String password, String bluetoothMac, String roomNumber,UseStatus useStatus) {
		StringBuffer hql = new StringBuffer("from AccessUserPasswd where 1 = 1");
		hql.append(" and bluetoothMac = :bluetoothMac");
		if(StringUtils.isNotBlank(password)){
			hql.append(" and passwd = :password");
		}
		if(StringUtils.isNotBlank(roomNumber)){
			hql.append(" and roomNumber = :roomNumber");
		}
		if(useStatus!=null){
			if(useStatus==AccessUserPasswd.UseStatus.Normal){
				hql.append(" and (useStatus = :useStatus or useStatus is null)");
			}else{
				hql.append(" and useStatus = :useStatus");
			}
		}
		TypedQuery<AccessUserPasswd> query = this.entityManager.createQuery(hql.toString(), AccessUserPasswd.class);
		query.setParameter("bluetoothMac", bluetoothMac);
		
		if(StringUtils.isNotBlank(password)){
			query.setParameter("password", password);
		}
		if(StringUtils.isNotBlank(roomNumber)){
			query.setParameter("roomNumber", roomNumber);
		}
		if(useStatus!=null){
			query.setParameter("useStatus", useStatus);
		}
		try {
			List<AccessUserPasswd> list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> findByRoomAndCommunity(String roomNumber, String communityNumber) {
		StringBuffer queryHql = new StringBuffer("select ");
		queryHql.append(" pri.community_number communityNumber,pri.room_number roomNumber, pri.building building,pri.unit unit,");
		queryHql.append(" pri.floor floor,pri.room room,pri.room_index roomIndex,am.access_num accessNum,am.bluetooth_mac bluetoothMac,am.access_name accessName,");
		queryHql.append(" aup.passwd passwd,aup.user_name setPassUserName,aup.create_date setPassDate,c.community_name communityName ");
		queryHql.append(" from pty_property_room_info pri ");
		
		queryHql.append(" left join access_management am on am.status=0 and pri.community_number=am.community_number ");
		queryHql.append("and pri.building=am.building_name and case when pri.unit = '无' then am.unit_name is null or am.unit_name='' else pri.unit=am.unit_name end");
		queryHql.append(" left join access_user_passwd aup on aup.room_number=pri.room_number and aup.bluetooth_mac= am.bluetooth_mac and (aup.use_status=0 or aup.use_status is null)");
		queryHql.append(" left join community c on c.community_number=pri.community_number");
		queryHql.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(roomNumber)){
			queryHql.append(" and pri.room_number =:roomNumber");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			queryHql.append(" and pri.community_number =:communityNumber");
		}
		queryHql.append(" GROUP BY pri.room_number,am.access_num");
		queryHql.append(" order by aup.create_date desc");
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		
		if(StringUtils.isNotBlank(roomNumber)){
			typedQuery.setParameter("roomNumber", roomNumber);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			typedQuery.setParameter("communityNumber", communityNumber);
		}
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		
		return rows;
	}

	@Override
	public AccessUserPasswd findByPwdAndMac(String password, String bluetoothMac) {
		StringBuffer hql = new StringBuffer("from AccessUserPasswd where ");
		hql.append(" bluetoothMac = :bluetoothMac and passwd = :password ");
		hql.append(" order by createDate desc ");
		TypedQuery<AccessUserPasswd> query = this.entityManager.createQuery(hql.toString(), AccessUserPasswd.class);
		query.setParameter("bluetoothMac", bluetoothMac);
		query.setParameter("password", password);
		try {
			List<AccessUserPasswd> list = query.getResultList();
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
