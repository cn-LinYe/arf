package com.arf.gas.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.chainsaw.Main;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.RefuelGasMemberDao;
import com.arf.gas.entity.RefuelGasMember;
import com.arf.gas.entity.RefuelGasMember.MemberStatus;

@Repository("refuelGasMemberDao")
public class RefuelGasMemberDaoImpl extends BaseDaoImpl<RefuelGasMember, Long> implements RefuelGasMemberDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public PageResult<Map<String, Object>> findByGasNum(String userName, String gasNum, String businessNum,String licenseNumber,
			Integer pageSize,Integer pageNo,Integer memberStatus) {
		StringBuffer countHql = new StringBuffer("select count(a.id) as COUNT from refuel_gas_member a");
		StringBuffer queryHql = new StringBuffer("select ");
		queryHql.append(" a.id id, a.user_name userName,a.real_name realName,a.member_level memberLevel,a.enjoy_discounts enjoyDiscounts,");
		queryHql.append(" a.payment_password paymentPassword,a.fingerprint_password fingerprintPassword,a.free_payment freePayment, ");
		queryHql.append(" a.IDcard_photo IDcardPhoto,a.driving_photo drivingPhoto,a.member_status memberStatus,");
		queryHql.append(" a.consumption_amount consumptionAmount,a.license_plate_number licensePlateNumber,a.free_payment_quota freePaymentQuota,");
		queryHql.append(" a.first_review_result firstReviewResult,a.Line_Data_result LineDataResult,a.second_review_result secondReviewResult,");
		queryHql.append(" a.business_num businessNum,a.gas_num gasNum,a.protocol_photo protocolPhoto,a.card_amount openCardAmount,a.id_card idCard,");
		queryHql.append(" a.recommend recommend,s.gas_name gasName,c.card_number cardNumber,");
		queryHql.append(" c.card_amount cardAmount,c.consumption_amount cardConsumptionAmount ,c.card_status cardStatus,c.create_date openCardDate,");
		queryHql.append(" c.gift_total_amount totalGiftAmount,b.business_name businessName,b.address businessAddress,b.business_pic businessPic,b.login_name businessPhone,");
		queryHql.append(" b.business_description businessDescription,b.lat lat,b.lng lng,c.create_date as cardCreateDate");
		queryHql.append(" from refuel_gas_member a ");
		
		queryHql.append(" left join refuel_gas_card c on (c.user_name=a.user_name and c.gas_num=a.gas_num)");
		queryHql.append(" left join  refuel_gas_station s on s.gas_num=a.gas_num");
		queryHql.append(" left join  p_business b on b.business_num=a.business_num");
		queryHql.append(" where 1=1 ");
		
		countHql.append(" left join refuel_gas_card c on c.user_name=a.user_name");
		countHql.append(" left join  refuel_gas_station s on s.gas_num=a.gas_num");
		countHql.append(" left join  p_business b on b.business_num=a.business_num");
		countHql.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(userName)){
			queryHql.append(" and a.user_name =:userName");
			countHql.append(" and a.user_name =:userName");
		}
		if(StringUtils.isNotBlank(gasNum)){
			queryHql.append(" and a.gas_num =:gasNum");
			countHql.append(" and a.gas_num =:gasNum");
		}
		if(StringUtils.isNotBlank(businessNum)){
			queryHql.append(" and a.business_num =:businessNum");
			countHql.append(" and a.business_num =:businessNum");
		}
		if(memberStatus!=null){
			queryHql.append(" and a.member_status =:memberStatus");
			countHql.append(" and a.member_status =:memberStatus");
		}
		
		if(StringUtils.isNotBlank(licenseNumber)){
			queryHql.append(" and a.license_plate_number like '%"+"\""+licenseNumber+"\""+"%'");
			countHql.append(" and a.license_plate_number like '%"+"\""+licenseNumber+"\""+"%'");
		}
		
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		
		if(StringUtils.isNotBlank(userName)){
			typedQuery.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(gasNum)){
			typedQuery.setParameter("gasNum", gasNum);
			queryCount.setParameter("gasNum", gasNum);
		}
		if(StringUtils.isNotBlank(businessNum)){
			typedQuery.setParameter("businessNum", businessNum);
			queryCount.setParameter("businessNum", businessNum);
		}
		if(memberStatus!=null){
			typedQuery.setParameter("memberStatus", memberStatus);
			queryCount.setParameter("memberStatus", memberStatus);
		}
		
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			logger.error("查询会员出错", e);
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
		pageResult.setTotalNum(count);
		//分页查询
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		if(pageNo!=null&&pageNo<=0){
			pageNo=1;
		}
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		
		return pageResult;
	}

	@Override
	public RefuelGasMember findByUserNameAndGasNum(String userName ,Integer gasNum,Integer businessNum,RefuelGasMember.MemberStatus memberStatus) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasMember where userName=:userName");
		queryHql.append(" and gasNum=:gasNum and businessNum=:businessNum" );
		if (memberStatus!=null) {
		queryHql.append(" and memberStatus=:memberStatus");
		}
		TypedQuery<RefuelGasMember> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasMember.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("gasNum", gasNum);
		typedQuery.setParameter("businessNum", businessNum);
		if(memberStatus!=null){
			typedQuery.setParameter("memberStatus", memberStatus);
		}
		
		List<RefuelGasMember> list =typedQuery.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			logger.error("查询会员在某个油站信息出错", e);
			return null;
		}
	}

	@Override
	public List<RefuelGasMember> findByUserName(String[] userNames,Integer gasNum) {
		if (userNames==null||userNames.length<=0) {
			return null;
		}
		StringBuffer sb =new StringBuffer("select m from RefuelGasMember m where m.userName in (:userNames) and m.gasNum=:gasNum");
		Query query =entityManager.createQuery(sb.toString());
		query.setParameter("userNames", Arrays.asList(userNames));
		query.setParameter("gasNum", gasNum);
		@SuppressWarnings("unchecked")
		List<RefuelGasMember>list =query.getResultList();
		return list;
	}

	@Override
	public List<RefuelGasMember> findByMemberStatus(List<MemberStatus> memberStatusList, Integer gasNum) {
		if (memberStatusList==null||memberStatusList.size()<=0) {
			return null;
		}
		StringBuffer sb =new StringBuffer("select m from RefuelGasMember m where m.memberStatus in (:memberStatusList) and m.gasNum=:gasNum");
		Query query =entityManager.createQuery(sb.toString());
		query.setParameter("memberStatusList", memberStatusList);
		query.setParameter("gasNum", gasNum);
		@SuppressWarnings("unchecked")
		List<RefuelGasMember>list =query.getResultList();
		return list;
	}

	@Override
	public int findLicenseUser(String license, String userName,Integer gasNum) {
		StringBuffer sb =new StringBuffer("SELECT ");
		sb.append("(SELECT COUNT(1) FROM refuel_gas_member_data_change r WHERE 1=1 AND r.change_status='1' AND r.gas_num=:gasNum and license_plate_number  LIKE '%"+license+"%')");
		sb.append("+(");
		sb.append("SELECT COUNT(1) FROM refuel_gas_member g WHERE 1=1 and g.gas_num=:gasNum and g.member_status ='1' AND license_plate_number  LIKE '%"+license+"%' ");
		sb.append(")+(");
		sb.append("SELECT COUNT(1) FROM refuel_gas_member r WHERE r.license_plate_number LIKE '%"+license+"%' AND r.user_name =:userName AND r.gas_num=:gasNum ");
		sb.append(")+(");
		sb.append("SELECT COUNT(1) FROM refuel_gas_member_data_change g WHERE g.license_plate_number LIKE '%"+license+"%' AND g.user_name=:userName AND g.gas_num=:gasNum) as count");
		Query query =entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.setParameter("gasNum", gasNum);
		int count=0;
		try {
			count =((BigInteger)query.getSingleResult()).intValue();	
		} catch (Exception e) {
		}
		return count;
	}

	@Override
	public RefuelGasMember findByIdCard(String idCard) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasMember where idCard=:idCard");
		TypedQuery<RefuelGasMember> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasMember.class);
		typedQuery.setParameter("idCard", idCard);
		
		List<RefuelGasMember> list =typedQuery.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			logger.error("查询会员信息出错", e);
			return null;
		}
	}

}














