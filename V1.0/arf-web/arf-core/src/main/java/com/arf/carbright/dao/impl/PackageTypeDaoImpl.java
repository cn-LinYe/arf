package com.arf.carbright.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.PackageTypeDao;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.PackageType.IsEnabled;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("packageTypeDao")
public class PackageTypeDaoImpl extends BaseDaoImpl<PackageType, Long> implements PackageTypeDao {

	@Override
	public List<PackageType> findbyUrnenabled(String useRangeNum, Integer isEnabled) {

		String jpql = "select packageType from PackageType packageType where packageType.isEnabled = :isEnabled and packageType.useRangeNum = :useRangeNum";
		return entityManager.createQuery(jpql, PackageType.class)
				.setParameter("isEnabled",IsEnabled.enablem.ordinal())
				.setParameter("useRangeNum", useRangeNum)
				.getResultList();
	}

	@Override
	public PackageType findbyPackageTypeNum(String packageTypeNum, Integer isEnabled) {
		String jpql = "select packageType from PackageType packageType where packageType.isEnabled = :isEnabled and packageType.packageTypeNum = :packageTypeNum";
		List<PackageType> packageTypes= entityManager.createQuery(jpql, PackageType.class)
				.setParameter("isEnabled",isEnabled)
				.setParameter("packageTypeNum", packageTypeNum)
				.getResultList();
		return (packageTypes==null ||packageTypes.size()<=0)?null:packageTypes.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PackageType> findByBusinessId(Long businessId, IsEnabled enabled) {
		StringBuffer sqlSb = new StringBuffer("select t.* from p_package_type t right join p_use_range r on t.use_range_num = r.use_range_num where 1=1 ");
		
		if(businessId != null){
			sqlSb.append("and r.business_id = :businessId ");
		}
		if(enabled != null){
			sqlSb.append("and t.is_enabled = :isEnabled ");
		}
		sqlSb.append(" order by t.put_top_date desc");
		Query query = entityManager.createNativeQuery(sqlSb.toString(), PackageType.class);
		if(businessId != null){
			query.setParameter("businessId", businessId);
		}
		if(enabled != null){
			query.setParameter("isEnabled", enabled.ordinal());
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PackageType> findByBusinessIdAndType(Long businessId, IsEnabled enabled,String businessServiceType) {
		StringBuffer sqlSb = new StringBuffer("select t.* from p_package_type t right join p_use_range r on t.use_range_num = r.use_range_num where 1=1 "
				+ " and t.send_start_time<now() and t.send_end_time>now() ");
		
		if(businessId != null){
			sqlSb.append("and r.business_id = :businessId ");
		}
		if(enabled != null){
			sqlSb.append("and t.is_enabled = :isEnabled ");
		}
		if("1".equals(businessServiceType)){
			sqlSb.append(" and (t.package_type_num like '"+PBusiness.Prefix_Carbrighter_Appearance+"%' or t.package_type_num like '"+PBusiness.Prefix_Carbrighter_Appearance_Interior+"%')");
		}
		Query query = entityManager.createNativeQuery(sqlSb.toString(), PackageType.class);
		if(businessId != null){
			query.setParameter("businessId", businessId);
		}
		if(enabled != null){
			query.setParameter("isEnabled", enabled.ordinal());
		}
		return query.getResultList();
	}
	
	//获取套餐信息
	@Override
	public PackageType getPackageType(Integer packTypeId) {
		
		String sql = "from PackageType where id=:packTypeId";
		try{
			return entityManager.createQuery(sql,PackageType.class)
					.setParameter("packTypeId", (long)packTypeId)
					.getResultList().get(0); 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<PackageType> findByStatusAndEndTime(Integer isEnabled,Date endTime) {
		String hql = "from PackageType packageType where packageType.isEnabled = :isEnabled and packageType.sendEndTime < :endTime";
		TypedQuery<PackageType> query = entityManager.createQuery(hql, PackageType.class);
		query.setParameter("isEnabled", isEnabled);
		query.setParameter("endTime", endTime);
		return query.getResultList();
	}

	@Override
	public PackageType findByStartTimeNum(Long startTimeNum) {
		String hql = "from PackageType packageType where packageType.startTimeNum = :startTimeNum ";
		List<PackageType> packageTypes= entityManager.createQuery(hql, PackageType.class).setParameter("startTimeNum", startTimeNum).getResultList();
		return CollectionUtils.isEmpty(packageTypes)?null:packageTypes.get(0);
	}
}
