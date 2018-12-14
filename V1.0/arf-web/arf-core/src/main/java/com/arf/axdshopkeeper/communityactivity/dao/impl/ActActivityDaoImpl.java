package com.arf.axdshopkeeper.communityactivity.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.axdshopkeeper.communityactivity.dao.IActActivityDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivity;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.google.common.collect.Lists;

@Repository("actActivityDaoImpl")
public class ActActivityDaoImpl extends BaseDaoImpl<ActActivity, Long>
		implements IActActivityDao {

	@Override
	public PageResult<ActActivity> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize,
			boolean findTimeout) {
		Date now = new Date();
		PageResult<ActActivity> pageResult = new PageResult<ActActivity>();
		//总记录数sql
		StringBuffer sbCount = new StringBuffer("select count(aa.id) as COUNT");
		sbCount.append(" from act_activity_community aac ");
		sbCount.append(" inner JOIN act_activity aa on (aa.id = aac.activity_id)");
		sbCount.append(" where 1 = 1");
		sbCount.append(" and aac.community_number = :communityNumber");
		sbCount.append(" and aa.status = :status");
		if(!findTimeout){
			sbCount.append(" and if(aa.start_time is not null and aa.start_time is not null,aa.start_time <= now() and now() <= aa.end_time,1 = 1)");
		}
		sbCount.append(" order by aa.create_date desc");
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		queryCount.setParameter("communityNumber", communityNumber);
		queryCount.setParameter("status", ActActivity.Status.PASSED.ordinal());
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageResult.setTotalNum(count);
		
		//分页查询sql
		StringBuffer sb = new StringBuffer("select aa.id,aa.create_date createDate,aa.modify_date modifyDate,aa.version version, ");
		sb.append(" aa.address,aac.community_number communityNumber,aa.content,aa.end_time endTime,aa.fee,");
		sb.append(" aa.fee_name feeName,aa.is_axd_partner isAxdPartner,aa.is_payment isPayment,aa.mobile,aa.name,");
		sb.append(" aa.number,aa.number_applicant numberApplicant,aa.photo,aa.start_time startTime,aa.status,");
		sb.append(" aa.user_name userName,aa.body_skip_url bodySkipUrl,aa.body_type bodyType,aa.face_skip_type faceSkipType,aa.face_url faceUrl,");
		sb.append(" aa.is_exceed isExceed,aa.is_face_skip isFaceSkip,aa.is_launch isLaunch,aa.is_name isName,aa.is_skip_button isSkipButton,");
		sb.append(" aa.other_name otherName,aa.other_photo otherPhoto,aa.other_skip_type otherSkipType,aa.other_url otherUrl,aa.shop_name shopName,");
		sb.append(" aa.skip_button_name skipButtonName,aa.type,aa.detail");
		sb.append(" from act_activity_community aac");
		sb.append(" LEFT JOIN act_activity aa on (aa.id = aac.activity_id)");
		sb.append(" where 1 = 1");
		sb.append(" and aac.community_number = :communityNumber");
		sb.append(" and aa.status = :status");
		if(!findTimeout){
			sb.append(" and if(aa.start_time is not null and aa.start_time is not null,aa.start_time <= now() and now() <= aa.end_time,1 = 1)");
		}
		sb.append(" order by aa.create_date desc");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("status", ActActivity.Status.PASSED.ordinal());
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);//最重要的语句
		List<ActActivity> actActivityList  =Lists.newArrayList();
		List<Map<String,Object>> rows = query.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			for (Map<String, Object> map : rows) {
				ActActivity actActivity = new ActActivity();
				actActivity = JSON.parseObject(JSON.toJSONString(map), ActActivity.class);
				actActivityList.add(actActivity);
			}
		}
		pageResult.setList(actActivityList);
		return pageResult;
	}

	@Override
	public List<ActActivity> findByKeyword(String keyword,
			String communityNumber) {
		StringBuffer sb = new StringBuffer("select aa.id,aa.create_date createDate,aa.modify_date modifyDate,aa.version version, ");
		sb.append(" aa.address,aac.community_number communityNumber,aa.content,aa.end_time endTime,aa.fee,");
		sb.append(" aa.fee_name feeName,aa.is_axd_partner isAxdPartner,aa.is_payment isPayment,aa.mobile,aa.name,");
		sb.append(" aa.number,aa.number_applicant numberApplicant,aa.photo,aa.start_time startTime,aa.status,");
		sb.append(" aa.user_name userName,aa.body_skip_url bodySkipUrl,aa.body_type bodyType,aa.face_skip_type faceSkipType,aa.face_url faceUrl,");
		sb.append(" aa.is_exceed isExceed,aa.is_face_skip isFaceSkip,aa.is_launch isLaunch,aa.is_name isName,aa.is_skip_button isSkipButton,");
		sb.append(" aa.other_name otherName,aa.other_photo otherPhoto,aa.other_skip_type otherSkipType,aa.other_url otherUrl,aa.shop_name shopName,");
		sb.append(" aa.skip_button_name skipButtonName,aa.type,aa.detail");
		sb.append(" from act_activity_community aac");
		sb.append(" LEFT JOIN act_activity aa on (aa.id = aac.activity_id)");
		sb.append(" where 1 = 1");
		sb.append(" and aac.community_number = :communityNumber");
		sb.append(" and aa.status = :status");
		sb.append(" and if(aa.start_time is not null and aa.start_time is not null,aa.start_time <= now() and now() <= aa.end_time,1 = 1)");
		sb.append(" and (aa.name like concat('%',:keyword,'%') or aa.content like concat('%',:keyword,'%'))");
		sb.append(" order by aa.create_date desc");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("status", ActActivity.Status.PASSED.ordinal());
		query.setParameter("keyword", keyword);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);//最重要的语句
		List<ActActivity> actActivityList  =Lists.newArrayList();
		List<Map<String,Object>> rows = query.getResultList();
		if(CollectionUtils.isNotEmpty(rows)){
			for (Map<String, Object> map : rows) {
				ActActivity actActivity = new ActActivity();
				actActivity = JSON.parseObject(JSON.toJSONString(map), ActActivity.class);
				actActivityList.add(actActivity);
			}
		}
		return actActivityList;
	}

}
