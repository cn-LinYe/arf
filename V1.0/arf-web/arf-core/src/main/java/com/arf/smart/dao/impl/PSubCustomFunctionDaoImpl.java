package com.arf.smart.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.smart.dao.IPSubCustomFunctionDao;
import com.arf.smart.entity.PSubCustomFunction;

@Repository("pSubCustomFunctionDao")
public class PSubCustomFunctionDaoImpl extends BaseDaoImpl<PSubCustomFunction, Long> implements IPSubCustomFunctionDao{

	@Override
	public List<PSubCustomFunction> findByParentMarkList(List<Integer> markList) {
		if(CollectionUtils.isEmpty(markList)){
			return null;
		}
		StringBuffer sql =new StringBuffer(" from PSubCustomFunction");
		sql.append(" where 1=1 and parentMark in (:markList)");
		sql.append(" order by iconOrder");
		TypedQuery<PSubCustomFunction> query 
			=this.entityManager.createQuery(sql.toString(),PSubCustomFunction.class);
		query.setParameter("markList", markList);
		
		List<PSubCustomFunction> list =query.getResultList();
		return list;
	}

}
