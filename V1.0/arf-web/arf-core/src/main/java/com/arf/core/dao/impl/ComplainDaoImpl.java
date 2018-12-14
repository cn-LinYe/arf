/**
 * @(#)ComplainDaoImpl.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2015-11-28       arf      Created
 **********************************************
 */

package com.arf.core.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.HQLResultConvert;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.ComplainDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Complain;
import com.arf.core.entity.Member;

/**
 * Dao - 投诉
 * 
 * @author arf
 * @version 4.0
 */
@Repository("complainDaoImpl")
public class ComplainDaoImpl extends BaseDaoImpl<Complain,Long> implements ComplainDao{

    @Override
    public List<Complain> findByOrderSn(String sn) {
        if (StringUtils.isEmpty(sn)) {
            return Collections.emptyList();
        }
        String jpql = "select complains from Complain as complains inner join complains.order as order where order.sn=:sn";
        return entityManager.createQuery(jpql,Complain.class).setParameter("sn",sn).getResultList();
        
    }
	@Override
	public Page<Complain> findPage(Complain complain, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Complain> criteriaQuery = criteriaBuilder.createQuery(Complain.class);
		Root<Complain> root = criteriaQuery.from(Complain.class);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, pageable);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<Complain> findPage(Admin admin, Pageable pageable) {
		String sql="SELECT * from xx_order_complains com where EXISTS (SELECT ord.id from xx_order ord where  EXISTS (SELECT memb.id from xx_member memb where  memb.admin=:admin and ord.member_biz=memb.id) and com.orders = ord.id )";
		if(pageable.getSearchProperty()!=null && pageable.getSearchValue()!=null){
			if(pageable.getSearchProperty().equals("content")){
				sql="SELECT * from xx_order_complains com where EXISTS (SELECT ord.id from xx_order ord where  EXISTS (SELECT g.id from xx_member g where  g.admin=:admin and ord.member_biz=g.id) and com.orders = ord.id ) AND com.content LIKE :content";
			}else{
				sql="SELECT * from xx_order_complains com where EXISTS (SELECT ord.id from xx_order ord where  EXISTS (SELECT g.id from xx_member g where  g.admin=:admin and ord.member_biz=g.id AND ord.sn like :sn) and com.orders = ord.id )";
			}
			}		
		Query query=entityManager.createNativeQuery(sql,Complain.class);
		Query query1 = entityManager.createNativeQuery(sql,Complain.class);
		query.setParameter("admin", admin.getId());	
		query1.setParameter("admin", admin.getId());
		if(pageable.getSearchProperty()!=null && pageable.getSearchValue()!=null){
			if(pageable.getSearchProperty().equals("content")){
				query.setParameter("content", "%"+pageable.getSearchValue()+"%");		
				query1.setParameter("content", "%"+pageable.getSearchValue()+"%");
			}else{
				query.setParameter("sn", "%"+pageable.getSearchValue()+"%");		
				query1.setParameter("sn", "%"+pageable.getSearchValue()+"%");
				}
			}
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		List<Complain> list= query1.getResultList();
		long total = list.size();
		return new Page<Complain>(query.getResultList(),total,pageable);
	}

	public Page<Complain> findPage(Member member,Pageable pageable){
	    String hql="select c from Complain c where c.isdel<>true and c.member.id="+member.getId();
	    String countHql="select count(1) from Complain c where c.isdel<>true and c.member.id="+member.getId();
	    
	    return findPageByHQL(hql, countHql, pageable,null, new HQLResultConvert<Complain>() {
            @Override
            public List<Complain> convert(List list) {
                return list;
            }
        });
	}
}
