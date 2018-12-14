package com.arf.anxinshop.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.anxinshop.dao.IEsHotArticleDao;
import com.arf.anxinshop.entity.EsHotArticle;
import com.arf.anxinshop.entity.EsHotArticle.Guest;
import com.arf.anxinshop.entity.EsHotArticle.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("esHotArticleDaoImpl")
public class EsHotArticleDaoImpl extends BaseDaoImpl<EsHotArticle, Long>
		implements IEsHotArticleDao {

	@Override
	public List<EsHotArticle> findByGuestStatus(Guest guest, Status status) {
		StringBuffer hql = new  StringBuffer("from EsHotArticle where 1 = 1");
		hql.append(" and guest = :guest ");
		hql.append(" and status = :status and pubDate is not null");
		hql.append(" order by top desc,pubDate desc");
		TypedQuery<EsHotArticle> typedQuery = entityManager.createQuery(hql.toString(), EsHotArticle.class);
		typedQuery.setParameter("guest", guest);
		typedQuery.setParameter("status", status);
		return typedQuery.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByStatusUserGroupIds(Status status,
			String groupids) {
		StringBuffer hql = new  StringBuffer("select * from es_hot_article where 1 = 1 ");
		hql.append(" and status = :status and pub_date is not null ");
//		hql.append(" and(");
//		
//		hql.append(" ( guest = :guesty )");
//		
//		boolean groupidsIsNotEmpty = false;
//		if(StringUtils.isNotBlank(groupids)){
//			String[] groupidsArr = groupids.split(",");
//			if(groupidsArr.length > 0){
//				groupidsIsNotEmpty = true;
//				hql.append(" or ( guest = :guestn");
//				hql.append("		and (");
//				for (int i = 0;i<groupidsArr.length;i++) {
//					hql.append("  		find_in_set( "+groupidsArr[i]+",group_ids) ");
//					if(i != groupidsArr.length - 1){
//						hql.append(" 	or ");
//					}
//				}
//				hql.append(" 			)");
//				hql.append(" 	)");
//			}
//		}
//		hql.append(" 	) ");
		hql.append(" order by top desc,pub_date desc");
		Query query = this.entityManager.createNativeQuery(hql.toString());
		query.setParameter("status", status.ordinal());
//		query.setParameter("guesty", Guest.YES.ordinal());
//		if(groupidsIsNotEmpty){
//			query.setParameter("guestn", Guest.NO.ordinal());
//		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<EsHotArticle> findByKeyword(String keyword,String communityNumber, int limit){
		String bizChannelNumber = null;
		String branchId = null;
		String propertyNumbr = null;
		if(StringUtils.isNotBlank(communityNumber)){
			//找到这个小区的物业公司, 联营公司, 渠道
			String csql = "select CONCAT(a.property_number) as propertyNumbr,"
					+ " CONCAT(a.branch_id) as branchId,"
					+ " CONCAT(b.biz_channel_number) as bizChannelNumber from community a left join bi_biz_channel_ref_branch b on a.branch_id = b.branch_id"
					+ " where a.community_number = :communityNumber";
			Query query = this.entityManager.createNativeQuery(csql);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setParameter("communityNumber", communityNumber);
			List<Map<String,Object>> list = query.getResultList();
			if(CollectionUtils.isNotEmpty(list)){
				Map<String,Object> community = list.get(0);
				bizChannelNumber = (String) community.get("bizChannelNumber");
				branchId = (String) community.get("branchId");
				propertyNumbr = (String) community.get("propertyNumbr");
			}
		}
		StringBuffer sql = new StringBuffer("select a.* from es_hot_article a where 1=1"
				+ " and a.status = :statusPass and (a.title like concat('%',:keyword,'%') or a.content like concat('%',:keyword,'%'))");
		sql.append(" and ( ");
		sql.append(" a.scope_type = :ScopeType_NO_LIMIT "); //不限范围的
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" or (a.scope_type = :ScopeType_COMMUNITY and find_in_set(:communityNumber, a.scope_numbers) > 0 )"); //限制小区范围的
		}
		if(StringUtils.isNotBlank(propertyNumbr)){
			sql.append(" or (a.scope_type = :ScopeType_PROPERTY and find_in_set(:propertyNumbr, a.scope_numbers) > 0 )"); //限制物业范围的
		}
		if(StringUtils.isNotBlank(branchId)){
			sql.append(" or (a.scope_type = :ScopeType_BRANCH and find_in_set(:branchId, a.scope_numbers) > 0 )"); //限制联营公司范围的
		}
		if(StringUtils.isNotBlank(bizChannelNumber)){
			sql.append(" or (a.scope_type = :ScopeType_CHANNEL and find_in_set(:bizChannelNumber, a.scope_numbers) > 0 )"); //限制渠道范围的
		}
		sql.append(" ) ");
		sql.append(" limit :limit ");
		
		Query query = this.entityManager.createNativeQuery(sql.toString(), EsHotArticle.class);
		query.setParameter("statusPass", EsHotArticle.Status.PASS.ordinal());
		query.setParameter("keyword", keyword);
		query.setParameter("ScopeType_NO_LIMIT", EsHotArticle.ScopeType.NO_LIMIT.ordinal());
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("ScopeType_COMMUNITY", EsHotArticle.ScopeType.COMMUNITY.ordinal());
			query.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(propertyNumbr)){
			query.setParameter("ScopeType_PROPERTY", EsHotArticle.ScopeType.PROPERTY.ordinal());
			query.setParameter("propertyNumbr", propertyNumbr);
		}
		if(StringUtils.isNotBlank(branchId)){
			query.setParameter("ScopeType_BRANCH", EsHotArticle.ScopeType.BRANCH.ordinal());
			query.setParameter("branchId", branchId);
		}
		if(StringUtils.isNotBlank(bizChannelNumber)){
			query.setParameter("ScopeType_CHANNEL", EsHotArticle.ScopeType.CHANNEL.ordinal());
			query.setParameter("bizChannelNumber", bizChannelNumber);
		}
		query.setParameter("limit", limit);
		return query.getResultList();
	}

}
