package com.arf.access.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessNHouseholdsDao;
import com.arf.access.entity.AccessNHouseholds;
import com.arf.access.entity.AccessUser;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessNHouseholdsDao")
public class AccessNHouseholdsDaoImpl extends BaseDaoImpl<AccessNHouseholds, Long> implements IAccessNHouseholdsDao{

	@Override
	public List<Map<String,Object>> findByCommunityAndUserName(String userName, String communityNo) {
		StringBuffer hql = new StringBuffer("select a.user_name userName,a.real_name realName,a.room_num roomNum,a.room room,");
		hql.append("a.community_building_id communityBuildingId,a.user_type userType,a.status status,a.community_name communityName, ");
		hql.append("a.community_number communityNumber,a.mid_community_number midCommunityNumber,a.create_date createDate,cb.building_name buildingName, ");
		hql.append("cd.building_name parentBuildName from access_n_households a ");
		hql.append("left join community_building cb on a.community_building_id=cb.id ");
		hql.append("left join community_building cd on cb.parent_id=cd.id ");
		hql.append("where 1=1  and a.user_name=:userName and a.status="+AccessUser.Status.normal.ordinal());
		if (StringUtils.isNotBlank(communityNo)) {
			hql.append(" and a.community_number=:communityNo");
		}
		Query query = this.entityManager.createNativeQuery(hql.toString());
		query.setParameter("userName", userName);
		if (StringUtils.isNotBlank(communityNo)) {
			query.setParameter("communityNo", communityNo);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

}
