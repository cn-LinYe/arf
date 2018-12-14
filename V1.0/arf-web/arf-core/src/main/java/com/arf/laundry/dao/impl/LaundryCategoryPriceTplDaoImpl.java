package com.arf.laundry.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryCategoryPriceTplDao;
import com.arf.laundry.entity.LaundryCategoryPriceTpl;

@Repository("laundryCategoryPriceTplDaoImpl")
public class LaundryCategoryPriceTplDaoImpl extends BaseDaoImpl<LaundryCategoryPriceTpl, Long> implements ILaundryCategoryPriceTplDao {

	@Override
	public LaundryCategoryPriceTpl findByCategoryNum(String categoryNum){
		String hql = "from LaundryCategoryPriceTpl t where 1=1 and t.categoryNum = :categoryNum";
		TypedQuery<LaundryCategoryPriceTpl> typedQuery = entityManager.createQuery(hql,LaundryCategoryPriceTpl.class);
		typedQuery.setParameter("categoryNum", categoryNum);
		try{
			return typedQuery.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
}
