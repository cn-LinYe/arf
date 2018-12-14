package com.arf.community.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.community.dao.RichMediaMessagePushDao;
import com.arf.community.entity.RichMediaMessagePush;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.core.util.MStringUntils;

@Repository("richMediaMessagePushDaoImpl")
public class RichMediaMessagePushDaoImpl extends BaseDaoImpl<RichMediaMessagePush, Long> implements RichMediaMessagePushDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByComProBraCity(List<String> communitys, List<Integer> propertys,List<Integer> branchIds, List<String> cityNos) {
		StringBuffer hql = new StringBuffer("");
		hql.append(" SELECT DISTINCT message_id from msg_rich_media_message_push");
		hql.append(" where 1 = 1");
		if(CollectionUtils.isNotEmpty(communitys)
				|| CollectionUtils.isNotEmpty(propertys)
				|| CollectionUtils.isNotEmpty(branchIds)
				|| CollectionUtils.isNotEmpty(cityNos)){
			hql.append(" and (");
			if(CollectionUtils.isNotEmpty(communitys)){
				hql.append(" (target_type = 0 and unique_identifier in ("+MStringUntils.join(communitys, ",")+"))");
			}
			if(CollectionUtils.isNotEmpty(propertys)){
				if(CollectionUtils.isNotEmpty(communitys)){
					hql.append(" or ");
				}
				hql.append(" (target_type = 1 and unique_identifier in ("+MStringUntils.join(propertys, ",")+"))");
			}
			if(CollectionUtils.isNotEmpty(branchIds)){
				if(CollectionUtils.isNotEmpty(communitys) || CollectionUtils.isNotEmpty(propertys)){
					hql.append(" or ");
				}
				hql.append(" (target_type = 2 and unique_identifier in ("+MStringUntils.join(branchIds, ",")+"))");
			}
			if(CollectionUtils.isNotEmpty(cityNos)){
				if(CollectionUtils.isNotEmpty(communitys) || CollectionUtils.isNotEmpty(propertys) || CollectionUtils.isNotEmpty(branchIds)){
					hql.append(" or ");
				}
				hql.append(" (target_type = 3 and unique_identifier in ("+MStringUntils.join(cityNos, ",")+"))");
			}
			hql.append(" )");
		}
		Query query = super.entityManager.createNativeQuery(hql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

}
