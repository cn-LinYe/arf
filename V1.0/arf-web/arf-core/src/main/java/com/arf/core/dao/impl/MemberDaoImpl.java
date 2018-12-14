/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.MemberDao;
import com.arf.core.dto.MemberCommunityDto;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Member;
import com.arf.core.entity.Member.RankingType;
import com.arf.core.entity.Member.Type;
import com.arf.core.entity.MemberAttribute;
import com.arf.core.util.MD5Util;

/**
 * Dao - 会员
 * 
 * @author arf
 * @version 4.0
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {

	public boolean usernameExists(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		String jpql = "select count(*) from Member members where members.username = :username";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public boolean emailExists(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		String jpql = "select count(*) from Member members where members.email = :email";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Member find(String loginPluginId, String openId) {
		if (StringUtils.isEmpty(loginPluginId) || StringUtils.isEmpty(openId)) {
			return null;
		}
		try {
			String jpql = "select members from Member members where members.loginPluginId = :loginPluginId and members.openId = :openId";
			return entityManager.createQuery(jpql, Member.class).setParameter("loginPluginId", loginPluginId).setParameter("openId", openId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Member findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		try {
			String jpql = "select members from Member members where members.username = :username";
			return entityManager.createQuery(jpql, Member.class).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Member> findListByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return Collections.emptyList();
		}
		String jpql = "select members from Member members where members.email = :email";
		return entityManager.createQuery(jpql, Member.class).setParameter("email", email).getResultList();
	}
	
	public Page<Member> findPage(Member.RankingType rankingType,Type memtype, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();		
		if (memtype != null ) {
			restrictions =	criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), memtype));
		}		
		if (rankingType != null) {
			switch (rankingType) {
			case point:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("point")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case balance:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case amount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("amount")), criteriaBuilder.desc(root.get("createDate")));
				break;
			default :
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
				break;
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	
	public Long registerMemberCount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public void clearAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null || memberAttribute.getPropertyIndex() == null) {
			return;
		}

		String propertyName;
		switch (memberAttribute.getType()) {
		case text:
		case select:
		case checkbox:
			propertyName = Member.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			break;
		default:
			propertyName = String.valueOf(memberAttribute.getType());
			break;
		}
		String jpql = "update Member members set members." + propertyName + " = null";
		entityManager.createQuery(jpql).executeUpdate();
	}

	public List<Member> findListByDocumentCode(String code,String username) {
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(username)) {
            return Collections.emptyList();
        }
        String jpql = "select members from Member members where members.documentCode = :code and members.username = :username";
        return entityManager.createQuery(jpql, Member.class).setParameter("code", code).setParameter("username",username).getResultList();
    }

	@Override
	public Page<Member> findPage(RankingType rankingType, Type memtype, Member.State state, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);	
		Predicate restrictions = criteriaBuilder.conjunction();		
		if (memtype != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), memtype));
		}
		if(state != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"), state));
		}		
		criteriaQuery.where(restrictions);
		if (rankingType != null) {
			switch (rankingType) {
			case point:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("point")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case balance:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case amount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("amount")), criteriaBuilder.desc(root.get("createDate")));
				break;
			default :
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
				break;
			}
		}
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public void updateEnabled(Boolean bl,Long id) {
		String jpql = "update Member members set members.isEnabled = ? where id = ?";
		entityManager.createQuery(jpql).setParameter(1, bl).setParameter(2,id).executeUpdate();
		
	}

	@Override
	public Member findByUsername(String name, Admin admin) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);		 
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("username"), name));
		Subquery<Admin> subquery = criteriaQuery.subquery(Admin.class);
		Root<Admin> subqueryRoot = subquery.from(Admin.class);
		subquery.select(subqueryRoot);
		subquery.where(criteriaBuilder.equal(subqueryRoot.get("id"), admin.getId()),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("admin")));		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));	
		criteriaQuery.where(restrictions);
		List<Member> list=entityManager.createQuery(criteriaQuery).getResultList();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Member> findListByadmin(Admin admin, Member.Type type) {
		String jpql = "select members from Member members where admin = :admin and isEnabled = true and type = :type";
		return entityManager.createQuery(jpql, Member.class).setParameter("admin", admin).setParameter("type", type).getResultList();
	
	}

	public void createTestUser(){
	    String userpre="15000000";
	    String username="";
	    String userend="";
	    String password="";
	    for(int i=0;i<650;i++){
	        if(i<10) userend="00"+i;
	        else if(i<100) userend="0"+i;
	        else userend=i+"";
	        username=userpre+userend;
	        password=MD5Util.getMD5(username.subSequence(4, 9) +"e10adc3949ba59abbe56e057f20f883e");
	        String sql="insert into xx_member(create_date,modify_date,version,amount,balance,email,is_enabled,is_locked,lock_key,mobile,name,password,phone,point,register_ip,username,member_rank,type,document_code,activation,authorization_code,authorization_time,current_equipment,is_send_message,is_send_push,lock_count,vip,vouchers,user_level,login_failure_count,register_way,key_id,open_id) "+
	                   " values('2015-12-05 00:00:00','2015-12-05 00:00:00',0,0,0,'xx@qq.com',1,0,'lockkey','15000000001','测试员"+i+"','"+password+"',"+username+",0,'127.0.0.1',"+username+",1,0,'aaaaaaaaaaaa',1,'sssssssssssa','2016-01-01 00:00:00',0,0,0,0,1,0,0,0,0,'aaa','82301fd02c3cfde99f15b78b16e825e1')";
	        entityManager.createNativeQuery(sql).executeUpdate();
	    }
	}

	@Override
	public BigInteger findListByCommunity(String communityNumber) {
		String sql ="SELECT COUNT(1)  FROM xx_member WHERE community_number =:communityNumber";
		Query query =entityManager.createNativeQuery(sql).setParameter("communityNumber", communityNumber);
        BigInteger total=(BigInteger)query.getSingleResult();
        return total;
	}

	@Override
	public BigInteger findListByCommunityVip(String communityNumber, int vip) {
		String sql ="SELECT COUNT(*)  from xx_member WHERE community_number =:communityNumber and vip=:vip";
		Query query =entityManager.createNativeQuery(sql).setParameter("communityNumber", communityNumber).setParameter("vip", vip);
        BigInteger total=(BigInteger)query.getSingleResult();
        return total;
	}

	@Override
	public List<Member> findByIsPushSign(Integer isPushSign,String communityNumber) {
		String sql ="from Member WHERE isPushSign =:isPushSign and communityNumber=:communityNumber and signTime is not null";
		TypedQuery<Member> query =entityManager.createQuery(sql,Member.class)
				.setParameter("isPushSign", isPushSign).setParameter("communityNumber", communityNumber);
        return query.getResultList();
	}

	@Override
	public List<Member> findByIsPushSign(List<Object> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		String sql="from Member where isSendPush=0 and username in(:list)";
		TypedQuery<Member> query =entityManager.createQuery(sql,Member.class);
		query.setParameter("list", list);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberCommunityDto> findCommunityByUsername(String userName,int queryType) {
		StringBuffer hql = new StringBuffer("");
		if(queryType == 0){
			hql.append(" SELECT DISTINCT");
			hql.append(" a.community_number communityNumber,");
			hql.append(" b.community_name communityName,");
			hql.append(" b.property_number propertyNumber,");
			hql.append(" b.branch_id branchId");
			hql.append(" from park_rate a LEFT JOIN community b on b.community_number = a.community_number");
			hql.append(" where a.license_plate_number in (SELECT license_plate_number from license_plate where user_name = :userName1)");
			hql.append(" UNION");
			hql.append(" SELECT");
			hql.append(" if(c.community_number = '0' or c.community_number is null,null,c.community_number) communityNumber,");
			hql.append(" d.community_name communityName,");
			hql.append(" d.property_number propertyNumber,");
			hql.append(" d.branch_id branchId");
			hql.append(" from xx_member c LEFT JOIN community d on d.community_number = c.community_number");
			hql.append(" where c.username = :userName2");
		}else if(queryType == 1){
			hql.append(" SELECT DISTINCT");
			hql.append(" a.community_number communityNumber,");
			hql.append(" b.community_name communityName,");
			hql.append(" b.property_number propertyNumber,");
			hql.append(" b.branch_id branchId");
			hql.append(" from park_rate a LEFT JOIN community b on b.community_number = a.community_number");
			hql.append(" where a.license_plate_number in (SELECT license_plate_number from license_plate where user_name = :userName1)");
		}else if(queryType == 2){
			hql.append(" SELECT");
			hql.append(" if(c.community_number = '0' or c.community_number is null,null,c.community_number) communityNumber,");
			hql.append(" d.community_name communityName,");
			hql.append(" d.property_number propertyNumber,");
			hql.append(" d.branch_id branchId");
			hql.append(" from xx_member c LEFT JOIN community d on d.community_number = c.community_number");
			hql.append(" where c.username = :userName2");
		}
		Query query = super.entityManager.createNativeQuery(hql.toString());
		if(queryType == 0){
			query.setParameter("userName1", userName);
			query.setParameter("userName2", userName);
		}else if(queryType == 1){
			query.setParameter("userName1", userName);
		}else if(queryType == 2){
			query.setParameter("userName2", userName);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> maps = query.getResultList();
		List<MemberCommunityDto> lists = null;
		if(CollectionUtils.isNotEmpty(maps)){
			lists = new ArrayList<MemberCommunityDto>();
		}
		for (Map<String, Object> map : maps) {
			MemberCommunityDto dto = new MemberCommunityDto();
			dto.setCommunityNumber(map.get("communityNumber") == null ? null:map.get("communityNumber").toString());
			dto.setCommunityName(map.get("communityName") == null ? null:map.get("communityName").toString());
			dto.setPropertyNumber(map.get("propertyNumber") == null ? null:Integer.valueOf(map.get("propertyNumber").toString()));
			dto.setBranchId(map.get("branchId") == null ? null:Integer.valueOf(map.get("branchId").toString()));
			lists.add(dto);
		}
		return lists;
	}

	@Override
	public List<Member> findByUserNames(List<String> userlist) {
		if (CollectionUtils.isEmpty(userlist)) {
			return null;
		}
		String sql="from Member where username in(:userlist)";
		TypedQuery<Member> query =entityManager.createQuery(sql,Member.class);
		query.setParameter("userlist", userlist);
		return query.getResultList();
	}
}






