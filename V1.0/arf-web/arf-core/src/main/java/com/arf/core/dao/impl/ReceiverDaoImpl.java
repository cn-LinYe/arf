/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.arf.core.HQLResultConvert;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.ReceiverDao;
import com.arf.core.entity.Member;
import com.arf.core.entity.Receiver;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 收货地址
 * 
 * @author arf
 * @version 4.0
 */
@Repository("receiverDaoImpl")
public class ReceiverDaoImpl extends BaseDaoImpl<Receiver, Long> implements ReceiverDao {

	public Receiver findDefault(Member member) {
		if (member == null) {
			return null;
		}
		try {
			String jpql = "select receiver from Receiver receiver where receiver.member = :member and receiver.isDefault = true";
			return entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).getSingleResult();
		} catch (NoResultException e) {
			try {
				String jpql = "select receiver from Receiver receiver where receiver.member = :member order by receiver.modifyDate desc";
				return entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).setMaxResults(1).getSingleResult();
			} catch (NoResultException e1) {
				return null;
			}
		}
	}

	public Page<Receiver> findPage(Member member, Pageable pageable) {
//		String hql="select id,consignee,areaName from Receiver";
//		String counthql="select count(1) from Receiver";
//		this.findPageByHQL(hql, counthql, pageable, null, new HQLResultConvert<Receiver>() {
//            
//            @Override
//            public List<Receiver> convert(List list) {
//                for(int i=0;i<list.size();i++){
//                    Object[] obj=(Object[])list.get(i);
//                    System.out.println("id:"+obj[0].toString());
//                    System.out.println("consignee:"+obj[1].toString());
//                }
//                return null;
//            }
//        });
	    
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Receiver> criteriaQuery = criteriaBuilder.createQuery(Receiver.class);
		Root<Receiver> root = criteriaQuery.from(Receiver.class);
		criteriaQuery.select(root);
		if (member != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public void setDefault(Receiver receiver) {
		Assert.notNull(receiver);
		Assert.notNull(receiver.getMember());

		receiver.setIsDefault(true);
		if (receiver.isNew()) {
			String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true";
			entityManager.createQuery(jpql).setParameter("member", receiver.getMember()).executeUpdate();
		} else {
			String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true and receiver != :receiver";
			entityManager.createQuery(jpql).setParameter("member", receiver.getMember()).setParameter("receiver", receiver).executeUpdate();
		}
	}

}