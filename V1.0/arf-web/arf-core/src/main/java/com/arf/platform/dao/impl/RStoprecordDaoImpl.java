package com.arf.platform.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.RStoprecordDao;
import com.arf.platform.entity.PParkingcar.StopType;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.entity.RStoprecord.FeePayStatus;
import com.arf.platform.search.StoprecordCondition;

/**
 * Dao - RStoprecord表
 * 
 * @author  arf
 * @version 1.0
 */
@Repository("RStoprecordDaoImpl")
public class RStoprecordDaoImpl extends BaseDaoImpl<RStoprecord, Long> implements RStoprecordDao {

	@Override
	public RStoprecord findByComLicArrLeav(String communityNo, String license, Date arriveTime,Date leaveTime) {
		StringBuffer hql = new StringBuffer("from com.arf.platform.entity.RStoprecord "
				+ " where parkingNumber = :communityNo and license = :license and arriveTime = :arriveTime ");
		if(leaveTime != null){
			hql.append(" and leaveTime = :leaveTime ");
		}
		
		hql.append(" order by id desc ");
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql.toString(),RStoprecord.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		query.setParameter("arriveTime", arriveTime);
		if(leaveTime != null)
			query.setParameter("leaveTime", leaveTime);
		List<RStoprecord> list = query.getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public RStoprecord findLatestNotPaidRecord(String communityNo, String license, Date arriveTime) {
		StringBuffer hql = new StringBuffer("from com.arf.platform.entity.RStoprecord "
				+ " where parkingNumber = :communityNo and license = :license and arriveTime = :arriveTimeStr ");
		hql.append(" and feePayStatus = :feePayStatus ");
		hql.append(" order by id desc ");
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql.toString(),RStoprecord.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		query.setParameter("arriveTimeStr", arriveTime);
		query.setParameter("feePayStatus", RStoprecord.FeePayStatus.Not_Paid.ordinal());
		
		List<RStoprecord> list = query.getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<RStoprecord> findOrderNotPaid(Date time) {
		
		StringBuffer hql = new StringBuffer("from com.arf.platform.entity.RStoprecord "
				+ " where 1=1");
		hql.append(" and( feePayStatus = :feePayStatus  or feePayStatus =:failedPaid )");
		hql.append(" and vouchersNum !=null or points!=null ");
		hql.append(" and isReturn=:isReturn");
		hql.append(" and createDate>=:time");
		hql.append(" order by id desc ");
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql.toString(),RStoprecord.class);
		query.setParameter("feePayStatus", RStoprecord.FeePayStatus.Not_Paid.ordinal());
		query.setParameter("failedPaid", RStoprecord.FeePayStatus.Failed_Paid.ordinal());
		query.setParameter("time", time);
		query.setParameter("isReturn", RStoprecord.IsReturn.NotReturn.ordinal());
		return query.getResultList();
	}

	@Override
	public RStoprecord findByOutTradeNo(String outTradeNo) {
		String hql = "from com.arf.platform.entity.RStoprecord  where outTradeNo = :outTradeNo";
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql,RStoprecord.class);
		query.setParameter("outTradeNo", outTradeNo);
		List<RStoprecord> list = query.getResultList();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<RStoprecord> findAllPaidRecord(String communityNo, String license, Date arriveTime) {
		StringBuffer hql = new StringBuffer("from com.arf.platform.entity.RStoprecord "
				+ " where parkingNumber = :communityNo and license = :license and arriveTime = :arriveTimeStr ");
		hql.append(" and feePayStatus = :feePayStatus ");
		hql.append(" order by createDate desc ");
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql.toString(),RStoprecord.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		query.setParameter("arriveTimeStr", arriveTime);
		query.setParameter("feePayStatus", RStoprecord.FeePayStatus.Paid.ordinal());
		
		List<RStoprecord> list = query.getResultList();
		return list;
	}

	@Override
	public PageResult<RStoprecord> myStopRecords(StoprecordCondition condition) {
		PageResult<RStoprecord> pageResult = new PageResult<RStoprecord>();
		int pageSize = condition.getPageSize();
		String userName = condition.getUserName();
		String parkingId = condition.getParkingId();
		String license=condition.getLicense();
		Date time=condition.getTime();
		Integer status=condition.getStatus();
		
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from r_stoprecord r where 1 = 1 ");
		StringBuffer sb = new StringBuffer("from com.arf.platform.entity.RStoprecord where 1 = 1");
		//设置查询条件
		if(StringUtils.isNotBlank(userName)){
			sbCount.append(" and r.user_name = :userName");
			sb.append(" and userName = :userName");
		}
		if(status!=null){
			sbCount.append(" and r.fee_pay_status = :status");
			sb.append(" and feePayStatus = :status");
		}
		if(StringUtils.isNotBlank(license)){
			sbCount.append(" and r.license = :license");
			sb.append(" and license = :license");
		}
		if(time!=null){
			sbCount.append(" and r.create_time >=:time");
			sbCount.append(" and r.create_time <=now()");
			sb.append(" and createTime >=:time");
			sb.append(" and createTime <=now()");
		}
		if(StringUtils.isNotBlank(parkingId)){
			sbCount.append(" and r.parking_number = :parkingId");
			sb.append(" and parkingNumber = :parkingId");
		}
		sb.append(" order by createDate desc");
		//创建查询
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		TypedQuery<RStoprecord> typedQuery = entityManager.createQuery(sb.toString(), RStoprecord.class);
		//设置参数
		if(StringUtils.isNotBlank(userName)){
			queryCount.setParameter("userName", userName);
			typedQuery.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(license)){
			queryCount.setParameter("license", license);
			typedQuery.setParameter("license", license);
		}
		if(time!=null){
			queryCount.setParameter("time", time);
			typedQuery.setParameter("time", time);
		}
		if(StringUtils.isNotBlank(parkingId)){
			queryCount.setParameter("parkingId", parkingId);
			typedQuery.setParameter("parkingId", parkingId);
		}
		if(status!=null){
			queryCount.setParameter("status", status);
			typedQuery.setParameter("status", status);
		}
		
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查询所有
		pageResult.setTotalNum(count);
		//分页查询
		if(condition.isUsePage()){
			typedQuery.setFirstResult(condition.getStartRowNum());
			typedQuery.setMaxResults(pageSize);
		}
		List<RStoprecord> list = typedQuery.getResultList();
		pageResult.setList(list);
		return pageResult;
	}

	@Override
	public PageResult<RStoprecord> myTemporaryStopRecords(String userName, int pageSize, int pageNo) {
		PageResult<RStoprecord> pageResult = new PageResult<RStoprecord>();
		StringBuffer sbCount = new StringBuffer("select count(id) as COUNT from r_stoprecord r where 1 = 1 ");
		StringBuffer sb = new StringBuffer("from com.arf.platform.entity.RStoprecord where 1 = 1");
		//设置查询条件
		if(StringUtils.isNotBlank(userName)){
			sbCount.append(" and r.user_name = :userName");
			sb.append(" and userName = :userName");
		}
		sbCount.append(" and r.fee_pay_status = :feePayStatus");
		sb.append(" and feePayStatus = :feePayStatus");
		
		sbCount.append(" and r.stop_type = :stopType");
		sb.append(" and stopType = :stopType");
		
		sbCount.append(" order by r.modify_date desc");
		sb.append(" order by modifyDate desc");
		//创建查询
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		TypedQuery<RStoprecord> typedQuery = entityManager.createQuery(sb.toString(), RStoprecord.class);
		//设置参数
		if(StringUtils.isNotBlank(userName)){
			queryCount.setParameter("userName", userName);
			typedQuery.setParameter("userName", userName);
		}
		
		queryCount.setParameter("feePayStatus", FeePayStatus.Paid.ordinal());
		typedQuery.setParameter("feePayStatus", FeePayStatus.Paid.ordinal());
		
		queryCount.setParameter("stopType", StopType.Temp_Parking.ordinal());
		typedQuery.setParameter("stopType", StopType.Temp_Parking.ordinal());
		//分页
		typedQuery.setMaxResults(pageSize);
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		List<RStoprecord> list = typedQuery.getResultList();
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pageResult = new PageResult<>(list,count);
		pageResult.setList(list);
		
		return pageResult;
	}

	@Override
	public List<String> findMonthsInDb() {
		String sql = "select DATE_FORMAT(create_date,'%Y-%m') as months from r_stoprecord group by DATE_FORMAT(create_date,'%Y-%m') order by months desc";
		Query query = this.entityManager.createNativeQuery(sql);
		List<String> list = query.getResultList();
		return list;
	}

	@Override
	public int migrateRStoprecords(String subTableName, String createStart, String createEnd, long first, int count){
		String sql = "INSERT INTO " + subTableName + " SELECT * FROM r_stoprecord "
					+ " where create_date between '" + createStart + "' and '" + createEnd + "' order by id ASC limit " + first + "," + count;
		Query query = this.entityManager.createNativeQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public int deleteBetweenCreateDate(String startDate,String endDate,int count) {
		String sql = "delete from r_stoprecord where create_date between '" + startDate + "' and '" + endDate + "' limit " + count;
		Query query = this.entityManager.createNativeQuery(sql);
		return query.executeUpdate();
	}
	@Override
	public RStoprecord findRecentPaidRecord(String communityNo, String license, Date arriveTime) {
		StringBuffer hql = new StringBuffer("from com.arf.platform.entity.RStoprecord "
				+ " where parkingNumber = :communityNo and license = :license and arriveTime >= :arriveTimeStr ");
		hql.append(" and feePayStatus = :feePayStatus ");
		hql.append(" order by leaveTime desc");
		TypedQuery<RStoprecord> query = entityManager.createQuery(hql.toString(),RStoprecord.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		query.setParameter("arriveTimeStr", arriveTime);
		query.setParameter("feePayStatus", RStoprecord.FeePayStatus.Paid.ordinal());
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<RStoprecord> list = query.getResultList();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int findAppearanceTodayByParkingId(String communityNo) {
		String sql="SELECT count(1) FROM r_stoprecord WHERE to_days(leave_time) = to_days(now()) and parking_number=:communityNo";
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("communityNo",communityNo);
		int count = 0; 
		try {
			count = ((BigInteger) query.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
