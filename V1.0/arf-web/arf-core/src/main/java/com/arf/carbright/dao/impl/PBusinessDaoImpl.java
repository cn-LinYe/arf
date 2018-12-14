package com.arf.carbright.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.carbright.condition.BusinessCondition;
import com.arf.carbright.dao.PBusinessDao;
import com.arf.carbright.dto.SearchBusinessDto;
import com.arf.carbright.entity.PBusiness;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("pBusinessDao")
public class PBusinessDaoImpl extends BaseDaoImpl<PBusiness, Long> implements PBusinessDao {

	
	@Override
	public PageResult<Map<String,Object>> findByLatAndLng(Double lat, Double lng,String distance ,Integer businessServiceType, Integer pageSize,Integer  pageNo) {
		if (lat==null||lng==null||StringUtils.isBlank(distance)) {
			return null;
		}
		
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append("  dis.id,dis.business_num as businessNum,dis.business_name as businessName,dis.address as businessAddress,dis.business_pic as businessPic ,dis.contact_phone as businessPhone, ");
		sql.append("  dis.mobile as businessContactPhone,dis.business_hours as businessHours ,dis.business_status as businessStatus,dis.business_usual_time as businessUsualTime,");
		sql.append("  dis.business_service_package_nums as businessServiceRange,dis.business_description as businessDescription ,dis.lat as lat,dis.lng as lng,");
		sql.append("  dis.distance,dis.business_comment as businessComment,dis.business_grade as businessGrade");
		sql.append("  FROM (SELECT");
		sql.append("  p.business_comment,p.business_grade,p.id,p.business_num,p.business_name,p.address,p.business_pic,p.contact_phone,p.mobile,p.business_hours,p.business_status,p.business_usual_time, ");
		sql.append("  p.business_service_package_nums,p.business_description,p.lat,p.lng,");
		sql.append("  acos( sin(("+lat+"*3.1415)/180) * sin((p.lat*3.1415)/180)+cos(("+lat+"*3.1415)/180) * cos((p.lat*3.1415)/180) * cos(("+lng+"*3.1415)/180 - (p.lng*3.1415)/180))*6370.996  AS distance  " );
		sql.append("  FROM p_business p where p.audit_status=1)dis");
		sql.append("  WHERE dis.distance <="+distance);
		
		StringBuffer sqlCount = new StringBuffer(" SELECT Count(*)  ");
		sqlCount.append("  FROM (SELECT");
		sqlCount.append("  acos( sin(("+lat+"*3.1415)/180) * sin((p.lat*3.1415)/180)+cos(("+lat+"*3.1415)/180) * cos((p.lat*3.1415)/180) * cos(("+lng+"*3.1415)/180 - (p.lng*3.1415)/180))*6370.996  AS distance  " );
		sqlCount.append("  FROM p_business p where p.audit_status=1)dis");
		sqlCount.append("  WHERE dis.distance <="+distance);
		
		if(1==businessServiceType){
			sql.append(" and (dis.business_Service_Package_Nums like '%"+PBusiness.Prefix_Carbrighter_Appearance+"%' or dis.business_Service_Package_Nums like '%"+PBusiness.Prefix_Carbrighter_Appearance_Interior+"%')");
		}
		if(3==businessServiceType){
			sql.append(" and (dis.business_Service_Package_Nums like '%"+PBusiness.Prefix_Refuel_Gas+"%')");
		}
		sql.append("  ORDER BY dis.distance ASC ");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> pbBusinesses =query.getResultList();
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>(pbBusinesses,count);
		return pageResult;
	}
	
	@Override
	public PBusiness findByUserPass(String loginName, String password) {

		try {
			String jpql = "select pBusiness from PBusiness pBusiness where pBusiness.loginName = :loginName and  pBusiness.password = :password";
			return entityManager.createQuery(jpql, PBusiness.class).setParameter("loginName", loginName)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public PBusiness findByUserName(String loginName) {
		try {
			String jpql = "select pBusiness from PBusiness pBusiness where pBusiness.loginName = :loginName";
			return entityManager.createQuery(jpql, PBusiness.class).setParameter("loginName", loginName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}}
			
	@Override
	public PBusiness findByBusinessId(int businessNum) {
		try {
			String jpql = "select pBusiness from PBusiness pBusiness where pBusiness.businessNum = :businessNum";
			return entityManager.createQuery(jpql, PBusiness.class).setParameter("businessNum", businessNum).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}}
	
	@Override
	public PBusiness findByBusinessNumAndType(Integer businessNum,String businessServiceType){
		StringBuffer sb = new StringBuffer("select pBusiness from PBusiness pBusiness where pBusiness.businessNum = :businessNum");
		if("1".equals(businessServiceType)){
			sb.append(" and (pBusiness.businessServicePackageNums like '%"+PBusiness.Prefix_Carbrighter_Appearance+"%' or pBusiness.businessServicePackageNums like '%"+PBusiness.Prefix_Carbrighter_Appearance_Interior+"%')");
		}
		else if("3".equals(businessServiceType)){
			sb.append(" and (pBusiness.businessServicePackageNums like '%"+PBusiness.Prefix_Laundry+"%')");
		}
		List<PBusiness> pblist = entityManager.createQuery(sb.toString(),PBusiness.class).setParameter("businessNum",businessNum).getResultList();
		if(CollectionUtils.isEmpty(pblist)){
			return null;
		}else{
			return pblist.get(0);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PBusiness> findByconidtion(String communityNumber,String businessServiceType){
		StringBuffer sb = new StringBuffer("select pBusiness.* from P_Business pBusiness where "
				+ "pBusiness.id in (select btc.business_Id from P_Business_To_Community btc where btc.community_Num = :communityNumber) ");
		if("1".equals(businessServiceType)){
			sb.append(" and (pBusiness.business_Service_Package_Nums like '%"+PBusiness.Prefix_Carbrighter_Appearance+"%' or pBusiness.business_Service_Package_Nums like '%"+PBusiness.Prefix_Carbrighter_Appearance_Interior+"%')");
		}else if("3".equals(businessServiceType)){
			sb.append(" and (pBusiness.business_Service_Package_Nums like '%"+PBusiness.Prefix_Laundry+"%')");
		}else if ("5".equals(businessServiceType)) {
			sb.append(" and (pBusiness.business_Service_Package_Nums like '%"+PBusiness.Prefix_Car_Insurance+"%')");
		}
		sb.append(" and pBusiness.audit_status = 1 and pBusiness.start_time < now() and pBusiness.end_time > now()");
		List<PBusiness> pblist = entityManager.createNativeQuery(sb.toString(),PBusiness.class).setParameter("communityNumber",communityNumber)
				.getResultList();
		return pblist;
	}
	
	@Override
	  public List<SearchBusinessDto> searchBusiness(BusinessCondition condition) {
	    
	    StringBuffer sb = new StringBuffer("select b.id,b.create_Date createDate,b.modify_Date modifyDate,b.business_Num businessNum "
	        + ",b.login_Name loginName,b.business_Type_Id businessTypeId,b.business_Name businessName,b.web_Address webAddress "
	        + ",b.contact_Person contactPerson,b.contact_Phone contactPhone "
	        + ",b.mobile,b.address,b.audit_Status auditStatus,b.create_Time createTime,b.last_Login_Time lastLoginTime "
	        + ",b.remark remark,b.start_Time startTime,b.end_Time endTime,b.business_Pic businessPic,b.branch_Id branchId "
	        + ",b.head_Office_Id headOfficeId,b.proprety_Number propretyNumber,b.business_Service_Package_Nums businessServicePackageNums "
	        + ",b.business_Hours businessHours,b.business_Description businessDescription,b.business_Status businessStatus "
	        + ",b.business_Usual_Time businessUsualTime,b.lat,b.lng,b.business_Grade businessGrade,b.business_Comment businessComment ");
	    if(condition.getLocation() != null){
	      double lat = condition.getLocation().getLatitudeRad();
	      double lng = condition.getLocation().getLongitude();
	      sb.append(" , acos( sin((");
	      sb.append(lat);
	      sb.append("*3.1415)/180) * sin((b.lat*3.1415)/180)+cos((");
	      sb.append(lat);
	      sb.append("*3.1415)/180) * cos((b.lat*3.1415)/180) * cos((");
	      sb.append(lng);
	      sb.append("*3.1415)/180 - (b.lng*3.1415)/180))*6370.996  AS distance  " );
	    }
	    
	    sb.append(" from p_business b where 1=1 ");
	    
	    
	    //这里【开始】添加其它查询条件
	    if(StringUtils.isNotBlank(condition.getServiceRange())){
	      sb.append(" and find_in_set(:serviceRange,b.business_service_package_nums) ");
	    }
	    if(StringUtils.isNotBlank(condition.getKeyword())){
	      sb.append(" and ( "
	          + " b.business_name like :keywordLike "
	          + " or b.address like :keywordLike "
	          + " ) ");
	    }
	    
	    if(condition.getAuditStatus() != null){
	    	sb.append(" and b.audit_Status =:auditStatus ");
	    }
	    
	    //这里【结束】添加其它查询条件
	    
	    
	    //位置搜索条件如存在 在放到最后并按照距离升序排序
	    if(condition.getLocation() != null && condition.getSearchRadius() > 0){
	      sb.append(" having distance <= ");
	      sb.append(condition.getSearchRadius() * 1000);//单位:米
	      sb.append(" order by  distance asc");
	    }
	    
	    Query query = this.entityManager.createNativeQuery(sb.toString());
	    if(StringUtils.isNotBlank(condition.getServiceRange())){
	      query.setParameter("serviceRange", condition.getServiceRange());
	    }
	    if(StringUtils.isNotBlank(condition.getKeyword())){
	      query.setParameter("keywordLike", "%" + condition.getKeyword() + "%");
	    }
	    if(condition.getAuditStatus() != null){
	      query.setParameter("auditStatus", condition.getAuditStatus());
	    }
	    
	    
	    if(condition.getPageSize() != null && condition.getPageNo() != null){
	      query.setMaxResults(condition.getPageSize());
	      query.setFirstResult((condition.getPageNo() - 1) * condition.getPageSize());
	    }
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SearchBusinessDto.class));
	    @SuppressWarnings("unchecked")
	    List<SearchBusinessDto> list = query.getResultList();
	    return list;
	  }

	@SuppressWarnings("unchecked")
	@Override
	public List<PBusiness> findByBusiness(List<Integer> business) {
		if (CollectionUtils.isEmpty(business)) {
			return new ArrayList<>();
		}
		StringBuffer hql = new StringBuffer(" from PBusiness t where t.businessNum in (:business) ");
		Query query =entityManager.createQuery(hql.toString());
		query.setParameter("business", business);
		return query.getResultList();
	}
}
