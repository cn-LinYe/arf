package com.arf.platform.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.PParKingrealTimeStatusDao;
import com.arf.platform.entity.PParKingrealTimeStatus;

/**
 * Dao - PParKingrealTimeStatus表
 * 
 * @author arf
 * @version 1.0
 */
@Repository("PParKingrealTimeStatusDaoImpl")
public class PParKingrealTimeStatusDaoImpl extends BaseDaoImpl<PParKingrealTimeStatus, Long> implements PParKingrealTimeStatusDao {

	@Override
	public List<PParKingrealTimeStatus> getPParKingrealTimeStatusByCommunityNo(String communityNo) {
		if (StringUtils.isEmpty(communityNo)) {
            return Collections.emptyList();
        }
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PParKingrealTimeStatus> criteriaQuery = criteriaBuilder.createQuery(PParKingrealTimeStatus.class);
		Root<PParKingrealTimeStatus> root = criteriaQuery.from(PParKingrealTimeStatus.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.equal(root.get("parkingId"), communityNo));
		return super.findList(criteriaQuery, null, null, null, null);
//		String hql = "from com.arf.platform.entity.PParKingrealTimeStatus where community_number ='" + communityNo + "'";
//		return entityManager.createQuery(hql,PParKingrealTimeStatus.class).getResultList();
	}
	
}
