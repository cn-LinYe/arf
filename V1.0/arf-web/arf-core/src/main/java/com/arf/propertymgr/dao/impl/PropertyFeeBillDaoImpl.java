package com.arf.propertymgr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.condition.PropertyFeeBillCondition;
import com.arf.propertymgr.dao.IPropertyFeeBillDao;
import com.arf.propertymgr.entity.PropertyFeeBill;
import com.arf.propertymgr.entity.PropertyFeeBill.PayStatus;

@Repository("propertyFeeBillDaoImpl")
public class PropertyFeeBillDaoImpl extends BaseDaoImpl<PropertyFeeBill, Long> implements IPropertyFeeBillDao {

	@Override
	public List<PropertyFeeBill> findByCondition(PropertyFeeBillCondition condition) {
		boolean needPage = false;
		Integer pageSize = condition.getPageSize();
		Integer pageNo = condition.getPageNo();
		if(pageNo != null && pageNo > 0 && pageSize != null && pageSize > 0){
			needPage = true;
		}
		StringBuffer sqlBf = new StringBuffer("from PropertyFeeBill pb where 1=1 ");
		if(condition.getUserName() != null){
			sqlBf.append(" and ( pb.userName = :userName ");
			if(condition.getAgentUserName() != null){
				sqlBf.append(" or pb.agentUserName = :agentUserName  ) ");
			}else{
				sqlBf.append(" ) ");
			}
		}
		if(condition.getPayStatus() != null){
			sqlBf.append(" and pb.payStatus = :payStatus ");
		}
		sqlBf.append(" order by pb.billName desc");
		TypedQuery<PropertyFeeBill> query = this.entityManager.createQuery(sqlBf.toString(), PropertyFeeBill.class);
		if(condition.getUserName() != null){
			query.setParameter("userName", condition.getUserName());
			if(condition.getAgentUserName() != null){
				query.setParameter("agentUserName", condition.getAgentUserName());
			}
		}
		if(condition.getPayStatus() != null){
			query.setParameter("payStatus", condition.getPayStatus());
		}
		if(needPage){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		List<PropertyFeeBill> list = query.getResultList();
		return list;
	}

	@Override
	public List<PropertyFeeBill> findByUsernamePayStatus(String userName,
			PayStatus payStatus) {
		StringBuffer hql = new StringBuffer("from PropertyFeeBill pb where 1=1 ");
		hql.append(" and pb.userName = :userName ");
		hql.append(" and pb.payStatus = :payStatus ");
		hql.append(" and (pb.status = :statusOutOfDate ");
		hql.append(" or pb.status = :statusNormal) ");
		hql.append(" order by pb.billName desc");
		TypedQuery<PropertyFeeBill> query = this.entityManager.createQuery(hql.toString(), PropertyFeeBill.class);
		query.setParameter("userName", userName);
		query.setParameter("payStatus", payStatus);
		query.setParameter("statusOutOfDate", com.arf.propertymgr.entity.PropertyFeeBill.Status.OUT_OF_DATE);
		query.setParameter("statusNormal", com.arf.propertymgr.entity.PropertyFeeBill.Status.NORMAL);
		return query.getResultList();
	}

	@Override
	public List<PropertyFeeBill> findByConditionV2(
			PropertyFeeBillCondition condition) {
		boolean needPage = false;
		Integer pageSize = condition.getPageSize();
		Integer pageNo = condition.getPageNo();
		List<Map<String, String>> rooms = condition.getRooms();
		if(pageNo != null && pageNo > 0 && pageSize != null && pageSize > 0){
			needPage = true;
		}
		StringBuffer sqlBf = new StringBuffer("from PropertyFeeBill pb where 1=1 ");
		if(condition.getUserName() != null){
			sqlBf.append(" and ( pb.userName = :userName ");
			if(condition.getAgentUserName() != null){
				sqlBf.append(" or pb.agentUserName = :agentUserName ");
			}
			if(CollectionUtils.isNotEmpty(rooms)){
				for (int i = 0;i < rooms.size();i++) {
					sqlBf.append(" or(");
					sqlBf.append(" 	pb.communityNumber = :communityNumber" + i);
					sqlBf.append(" 	and pb.building = :building" + i);
					sqlBf.append(" 	and pb.unit = :unit" + i);
					sqlBf.append(" 	and pb.floor = :floor" + i);
					sqlBf.append(" 	and pb.room = :room" + i);
					sqlBf.append(" )");
				}
				sqlBf.append(" ) ");
			}else{
				sqlBf.append(" ) ");
			}
		}
		if(condition.getPayStatus() != null){
			sqlBf.append(" and pb.payStatus = :payStatus ");
		}
		sqlBf.append(" order by pb.billName desc");
		TypedQuery<PropertyFeeBill> query = this.entityManager.createQuery(sqlBf.toString(), PropertyFeeBill.class);
		if(condition.getUserName() != null){
			query.setParameter("userName", condition.getUserName());
			if(condition.getAgentUserName() != null){
				query.setParameter("agentUserName", condition.getAgentUserName());
			}
			if(CollectionUtils.isNotEmpty(rooms)){
				for (int i = 0;i < rooms.size();i++) {
					Map<String, String> singleRoom = rooms.get(i);
					String communityNumber = singleRoom.get("communityNumber");
					String building = singleRoom.get("building");
					String unit = singleRoom.get("unit");
					String floor = singleRoom.get("floor");
					String room = singleRoom.get("room");
					query.setParameter("communityNumber" + i, communityNumber);
					query.setParameter("building" + i, building);
					query.setParameter("unit" + i, unit);
					query.setParameter("floor" + i, floor);
					query.setParameter("room" + i, room);
				}
			}
		}
		if(condition.getPayStatus() != null){
			query.setParameter("payStatus", condition.getPayStatus());
		}
		if(needPage){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		List<PropertyFeeBill> list = query.getResultList();
		return list;
	}

	@Override
	public void addBillBatch(List<PropertyFeeBill> bills) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sql=new StringBuffer("INSERT INTO pty_property_fee_bill ") ;
        sql.append("(create_date,");
        sql.append("modify_date,");
        sql.append("version,");
        sql.append("acreage,");
        sql.append("agent_flag,");
        sql.append("bill_name,");
        sql.append("bill_no,");
        sql.append("billing_period,");
        sql.append("bound_number,");
        sql.append("building,");
        sql.append("charge_type,");
        sql.append("end_date,");
        sql.append("floor,");
        sql.append("other_fee,");
        sql.append("pay_status,");
        sql.append("price_perm2,");
        sql.append("remark,");
        sql.append("room,");
        sql.append("status,");
        sql.append("total_fee,");
        sql.append("sum_fee,");
        sql.append("unit,");
        sql.append("community_number,");
        sql.append("user_name");
        sql.append(")values");
		for (int i = 0; i < bills.size(); i++) {
			PropertyFeeBill bill = bills.get(i);
			sql.append("('");
			sql.append(sim.format(new Date())+"','");
			sql.append(sim.format(new Date())+"','");
			sql.append(1+"','");
			sql.append(bill.getAcreage()+"','");
			sql.append(bill.getAgentFlag().ordinal()+"','");
			sql.append(bill.getBillName()+"','");
			sql.append(bill.getBillNo()+"','");
			sql.append(bill.getBillingPeriod()+"','");
			sql.append(bill.getBoundNumber()+"','");
			sql.append(bill.getBuilding()+"','");
			sql.append(bill.getChargeType()+"','");
			sql.append(sim.format(bill.getEndDate())+"','");
			sql.append(bill.getFloor()+"','");
			sql.append(bill.getOtherFee()+"','");
			sql.append(bill.getPayStatus().ordinal()+"','");
			if (bill.getPricePerM2()==null) {
				sql.append(0+"','");
			}else{
				sql.append(bill.getPricePerM2()+"','");
			}
			sql.append(bill.getRemark()+"','");
			sql.append(bill.getRoom()+"','");
			sql.append(bill.getStatus().ordinal()+"','");
			sql.append(bill.getTotalFee()+"','");
			sql.append(bill.getSumFee()+"','");
			sql.append(bill.getUnit()+"','");
			sql.append(bill.getCommunityNumber()+"','");
			sql.append(bill.getUserName());
			sql.append("')");
			sql.append(",");
		}
		String sqls =sql.toString().substring(0, sql.toString().length()-1);
		 entityManager.createNativeQuery(sqls).executeUpdate();
		
	}

	@Override
	public List<PropertyFeeBill> findByPaidDate(Date startDate, Date endDate,List<String> communityList) {
		StringBuffer hql = new StringBuffer("from PropertyFeeBill pb where 1=1 ");
		hql.append(" and pb.payStatus = :payStatus ");
		hql.append(" and pb.paidDate >= :startDate ");
		hql.append(" and pb.paidDate <= :enDate ");
		hql.append(" and pb.communityNumber in (:communityList) ");
		TypedQuery<PropertyFeeBill> query = this.entityManager.createQuery(hql.toString(), PropertyFeeBill.class);
		query.setParameter("payStatus", PropertyFeeBill.PayStatus.PAID_SUCCESS);
		query.setParameter("startDate", startDate);
		query.setParameter("enDate", endDate);
		query.setParameter("communityList", communityList);
		return query.getResultList();
	}

	@Override
	public List<Map<String, String>> findAgenentBills(String userName) {
		StringBuffer sb =new StringBuffer(" SELECT pt.community_number as communityNumber,pt.building as building,pt.unit as unit ,");
		sb.append(" pt.floor ,pt.room,pt.agent_flag as agentFlag,c.community_name as communityName");
		sb.append(" FROM pty_property_fee_bill pt ");
		sb.append(" LEFT JOIN community c ON c.community_number =pt.community_number ");
		sb.append(" WHERE agent_user_name =:userName AND pt.pay_status ='0'");
		sb.append(" GROUP BY pt.building,pt.unit,pt.floor,pt.room");
		Query query =entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> rows = query.getResultList();
		return rows;
	}

}
