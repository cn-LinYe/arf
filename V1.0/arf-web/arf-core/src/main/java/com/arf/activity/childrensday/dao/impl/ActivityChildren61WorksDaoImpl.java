package com.arf.activity.childrensday.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.activity.childrensday.dao.IActivityChildren61WorksDao;
import com.arf.activity.childrensday.entity.ActivityChildren61Works;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("activityChildren61WorksDaoImpl")
public class ActivityChildren61WorksDaoImpl extends
		BaseDaoImpl<ActivityChildren61Works, Long> implements
		IActivityChildren61WorksDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public ActivityChildren61Works findByWorksNum(Integer worksNum) {
		StringBuffer hql = new StringBuffer("from ActivityChildren61Works where worksNum = :worksNum");
		TypedQuery<ActivityChildren61Works> typedQuery = super.entityManager.createQuery(hql.toString(), ActivityChildren61Works.class);
		try {
			typedQuery.setParameter("worksNum", worksNum);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			logger.error("ActivityChildren61WorksDaoImpl 通过作品编号查询作品异常", e);
			return null;
		}
	}

	@Override
	public Page<ActivityChildren61Works> findByCondition(Integer pageNo,
			Integer pageSize, String keyword,Integer orderBy) {
		StringBuffer hql = new StringBuffer("from ActivityChildren61Works where 1 = 1");
		StringBuffer countSql = new StringBuffer("select count(id) from activity_children61_works where 1 = 1");
		if(StringUtils.isNotBlank(keyword)){
			hql.append(" and (worksNum like CONCAT('%',:keyword,'%') or authorName like CONCAT('%',:keyword,'%'))");
			countSql.append(" and (works_num like CONCAT('%',:keyword,'%') or author_name like CONCAT('%',:keyword,'%'))");
		}
		
		if(orderBy == 0){
			hql.append(" order by worksNum");
		}else{
			hql.append(" order by totalVotedCount desc,worksNum");
		}
		TypedQuery<ActivityChildren61Works> typedQuery = super.entityManager.createQuery(hql.toString(), ActivityChildren61Works.class);
		Query countQuery = super.entityManager.createNativeQuery(countSql.toString());
		if(StringUtils.isNotBlank(keyword)){
			typedQuery.setParameter("keyword", keyword);
			countQuery.setParameter("keyword", keyword);
		}
		if(pageNo != null
				&& pageSize != null){
			typedQuery.setFirstResult((pageNo - 1) * pageSize);
			typedQuery.setMaxResults(pageSize);
		}
		int totalCount = 0; 
		try {
			totalCount = ((BigInteger) countQuery.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pageable pageable = new Pageable(pageNo, pageSize);
		Page<ActivityChildren61Works> pageRes = new Page<ActivityChildren61Works>(typedQuery.getResultList(),totalCount,pageable);
		return pageRes;
	}

	@Override
	public List<ActivityChildren61Works> findAllByOrder() {
		StringBuffer hql = new StringBuffer("from ActivityChildren61Works");
		hql.append(" order by totalVotedCount desc,worksNum");
		TypedQuery<ActivityChildren61Works> typedQuery = super.entityManager.createQuery(hql.toString(), ActivityChildren61Works.class);
		return typedQuery.getResultList();
	}

	
	
}
