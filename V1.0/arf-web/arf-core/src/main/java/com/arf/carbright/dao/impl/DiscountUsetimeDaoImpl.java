package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IDiscountUsetimeDao;
import com.arf.carbright.entity.DiscountUsetime;
import com.arf.carbright.entity.DiscountUsetime.DiscountFrequency;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("discountUsetimeDaoImpl")
public class DiscountUsetimeDaoImpl extends BaseDaoImpl<DiscountUsetime, Long> implements IDiscountUsetimeDao{

	@Override
	public List<DiscountUsetime> findByDiscountFrequency(Long id, Integer frequency) {
		DiscountFrequency status=DiscountFrequency.get(frequency);
		if (status==null) {
			return null;
		}
		String hql="from DiscountUsetime where discountId =:discountId and discountFrequency=:frequency";
		TypedQuery<DiscountUsetime> query=this.entityManager.createQuery(hql,DiscountUsetime.class);
		query.setParameter("discountId", id);
		query.setParameter("frequency",status);
		return query.getResultList();
	}

}
