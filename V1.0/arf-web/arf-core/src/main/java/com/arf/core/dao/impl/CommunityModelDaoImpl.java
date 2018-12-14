package com.arf.core.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.base.PageResult;
import com.arf.core.dao.CommunityModelDao;
import com.arf.core.dto.CommunityCitynoDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.CommunityModel.AuthorizationMode;
import com.arf.core.filter.NonprintUnicodeFilter;
import com.google.common.collect.Lists;

/**
 * Dao - 小区表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("communityModelDaoImpl")
public class CommunityModelDaoImpl extends BaseDaoImpl<CommunityModel, Long> implements CommunityModelDao {
	private int DEFAULT_SEARCH_DISTANCE = 3;//默认搜索半径三千米
	
	@Override
	public List<CommunityModel> CheckByNo(String no) {
		String hql = "from CommunityModel where no = :no and version > :version";
		List<CommunityModel> list = entityManager.createQuery(hql, CommunityModel.class)
				.setParameter("no", Integer.valueOf(no))
				.setParameter("version", -10000L)
				.getResultList();
		return list;
	}

	@Override
	public List<CommunityModel> checkByCommunity_number(String community_number) {
		if (community_number==null) {
			return null;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CommunityModel> criteriaQuery = criteriaBuilder.createQuery(CommunityModel.class);
		Root<CommunityModel> root = criteriaQuery.from(CommunityModel.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("community_number"), community_number));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<CommunityModel> checkByPropretyNumber(long propretyId) {
		String hql = "from com.arf.core.entity.CommunityModel where property_number ='" + propretyId + "'";
		return entityManager.createQuery(hql, CommunityModel.class).getResultList();
	}

	@Override
	public List<CommunityModel> selectBySendMessages() {
		String hql = "from com.arf.core.entity.CommunityModel where isSendMessage ='" + 1 + "' and connectFlag='" + 1
				+ "'";
		return entityManager.createQuery(hql, CommunityModel.class).getResultList();
	}

	// 根据小区号，得到小区负责人号码
	@Override
	public String getPrincipalMobile(String community_number) {

		String hql = "from com.arf.core.entity.CommunityModel where community_number =:community_number";

		List<CommunityModel> list = entityManager.createQuery(hql, CommunityModel.class)
				.setParameter("community_number", community_number).getResultList();
		if (list.size() > 0)
			return list.get(0).getPhone();
		else
			return "";
	}

	@Override
	public List<CommunityModel> findByCommunityName(String communityName) {
		if (StringUtils.isBlank(communityName)) {
			return Lists.newArrayList();
		}
		try {                                                           
			String hql = "from com.arf.core.entity.CommunityModel where communityName =:communityName";
			return this.entityManager.createQuery(hql, CommunityModel.class).setParameter("communityName", communityName)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}

	}

	@Override
	public List<Map<String, Object>> findNameByCommunityNuber(List<String> communitynumber) {
		if(communitynumber!=null){
			String hql = "select community_number as communityNumber,community_name as communityName from community where community_number in (:communitynumber)";
			Query query=entityManager.createNativeQuery(hql);		
			query.setParameter("communitynumber", communitynumber);			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> objList =query.getResultList();
			if(!CollectionUtils.isEmpty(objList)){			
				return objList;
			}		
		}
		return Lists.newArrayList();
	}

	@Override
	public List<CommunityModel> findByExecuteMethod(Integer executeMethod) {
		try {                                                           
			String hql = "from com.arf.core.entity.CommunityModel where executeMethod =:executeMethod";
			return this.entityManager.createQuery(hql, CommunityModel.class).setParameter("executeMethod", executeMethod).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Lists.newArrayList();
		}
	}

	@Override
	public CommunityModel findByNumberAndExecuteMethod(String communityNumber,Integer executeMethod) {
		try {                                                           
			String hql = "from com.arf.core.entity.CommunityModel where community_number =:communityNumber and executeMethod =:executeMethod";
			return this.entityManager.createQuery(hql, CommunityModel.class)
					.setParameter("communityNumber", communityNumber)
					.setParameter("executeMethod", executeMethod).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<CommunityModel> findByKeyWords(String keyword) {
		keyword = NonprintUnicodeFilter.getInstance().filterEmoji(keyword);
		StringBuffer hql = new StringBuffer("from com.arf.core.entity.CommunityModel");
		hql.append(" where");
		hql.append(" communityName like CONCAT('%',:communityName,'%')");
		hql.append(" or communityAddress like CONCAT('%',:communityAddress,'%')");
		hql.append(" order by communityName");
		TypedQuery<CommunityModel> typedQuery = this.entityManager.createQuery(hql.toString(), CommunityModel.class);
		typedQuery.setParameter("communityName", keyword);
		typedQuery.setParameter("communityAddress", keyword);
		return typedQuery.getResultList();
	}

	/**
	 * 只返回了community_name、community_number两个字段
	 */
	@Override
	public PageResult<CommunityModel> findByLatAndLng(Double lat, Double lng,String distance, Integer pageSize, Integer pageNo) {
		if (lat==null||lng==null||StringUtils.isBlank(distance)) {
			return null;
		}
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append("  dis.community_name,");
		sql.append("  dis.community_number");
		sql.append("  FROM (SELECT");
		sql.append("  p.community_name,");
		sql.append("  p.community_number,");
		sql.append("  p.lat,");
		sql.append("  p.lng,");
		sql.append("  acos( sin(("+lat+"*3.1415)/180) * sin((p.lat*3.1415)/180)+cos(("+lat+"*3.1415)/180) * cos((p.lat*3.1415)/180) * cos(("+lng+"*3.1415)/180 - (p.lng*3.1415)/180))*6370.996  AS distance  " );
		sql.append("  FROM community p )dis");
		sql.append("  WHERE dis.distance <="+distance);
		
		StringBuffer sqlCount = new StringBuffer(" SELECT Count(*)  ");
		sqlCount.append("  FROM (SELECT");
		sqlCount.append("  acos( sin(("+lat+"*3.1415)/180) * sin((p.lat*3.1415)/180)+cos(("+lat+"*3.1415)/180) * cos((p.lat*3.1415)/180) * cos(("+lng+"*3.1415)/180 - (p.lng*3.1415)/180))*6370.996  AS distance  " );
		sqlCount.append("  FROM community p )dis");
		sqlCount.append("  WHERE dis.distance <="+distance);
		
		sql.append("  ORDER BY dis.distance ASC ");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		if(pageSize != null && pageNo != null){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo - 1) * pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> communityModelMap =query.getResultList();
		int count = 0; 
		try {
			count = ((BigInteger) queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CommunityModel> communityModels = new ArrayList<CommunityModel>();
		for (Map<String, Object> map : communityModelMap) {
			CommunityModel communityModel = new CommunityModel();
			communityModel.setCommunity_number(map.get("community_number")==null?null:map.get("community_number").toString());
			communityModel.setCommunityName(map.get("community_name")==null?null:map.get("community_name").toString());
			communityModels.add(communityModel);
		}
		PageResult<CommunityModel> pageResult = new PageResult<CommunityModel>(communityModels,count);
		return pageResult;
	}

	@Override
	public List<CommunityCitynoDto> findByCityNo(String cityNo) {
		StringBuffer sb = new StringBuffer("SELECT");
		sb.append(" co.community_name communityName,");
		sb.append(" co.community_number communityNumber,");
		sb.append(" e.areaname as province,");
		sb.append(" c.areaname as city,");
		sb.append(" a.areaname as district,");
		sb.append(" co.community_address address,");
		sb.append(" co.property_office_phone propertyOfficePhone,");
		sb.append(" co.is_axd as isAxd");
		sb.append(" from community co");
		sb.append(" LEFT JOIN quan_prov_city_area a ON a.no = co.no");
		sb.append(" LEFT JOIN (select b.areaname,b.no,b.arealevel,b.topno,concat(b.lng,',',b.lat) as geo from quan_prov_city_area b) c on a.`topno` = c.no ");
		sb.append(" LEFT JOIN (select d.areaname,d.no,d.arealevel,d.topno,concat(d.lng,',',d.lat) as geo from quan_prov_city_area d) e on c.`topno`=e.no");
		sb.append(" where (co.provinceno = :provinceno or co.cityno = :cityno)");
		sb.append(" ORDER BY co.community_name;");
		Query query = super.entityManager.createNativeQuery(sb.toString());
		query.setParameter("provinceno", cityNo);
		query.setParameter("cityno", cityNo);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<CommunityCitynoDto> communityCitynoDtos = new ArrayList<CommunityCitynoDto>();
		List<Map<String,Object>> rows = query.getResultList();
		for (Map<String, Object> map : rows) {
			CommunityCitynoDto communityCitynoDto = JSON.toJavaObject(new JSONObject(map), CommunityCitynoDto.class);
			communityCitynoDtos.add(communityCitynoDto);
		}
		return communityCitynoDtos;
	}

	@Override
	public List<Map<String,Object>> findByAuthorizationMode(AuthorizationMode freeComeAndOut) {
		String sql="SELECT community_number  communityNumber,axd_alive_time axdAliveTime FROM community WHERE axd_alive_time is NOT null AND change_date is not null AND authorization_mode=:freeComeAndOut";
		Query query = super.entityManager.createNativeQuery(sql);
		query.setParameter("freeComeAndOut", freeComeAndOut.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map =query.getResultList();
		return map;
	}

	@Override
	public List<Map<String, Object>> findByIsTemporaryParking(Double lat, Double lng, Integer distance) {
		if (lat==null||lng==null) {
			return Lists.newArrayList();
		}
		StringBuffer sql = new StringBuffer(" SELECT  ");
		sql.append("  dis.community_name, dis.community_number, dis.community_address, dis.phone, dis.is_temporary_parking,");
		sql.append("  dis.parking_type, dis.distance, dis.lat, dis.lng");
		sql.append("  FROM (SELECT");
		sql.append("  p.community_name, p.community_number, p.community_address, p.phone, p.is_temporary_parking,");
		sql.append("  p.parking_type, p.lat, p.lng,");
		sql.append("  acos( sin(("+lat+"*3.1415)/180) * sin((p.lat*3.1415)/180)+cos(("+lat+"*3.1415)/180) * cos((p.lat*3.1415)/180) * cos(("+lng+"*3.1415)/180 - (p.lng*3.1415)/180))*6370.996  AS distance  " );
		sql.append("  FROM community p )dis");
		sql.append("  WHERE 1=1 and dis.distance <= :distance");
		sql.append("  and dis.is_temporary_parking = :IsTemporaryParking");
		sql.append("  and ( dis.parking_type = :parkingType or dis.parking_type is null)");
		sql.append("  ORDER BY dis.distance ASC ");
		
		Query query =entityManager.createNativeQuery(sql.toString());
		if(distance==null){
			 query.setParameter("distance", DEFAULT_SEARCH_DISTANCE);//默认搜索半径三千米
		}else{
			 query.setParameter("distance", distance);
		}
	    query.setParameter("IsTemporaryParking", CommunityModel.IsTemporaryParking.Open.ordinal());
	    query.setParameter("parkingType", CommunityModel.ParkingType.Community.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> communityModelMap =query.getResultList();
		
		return communityModelMap;
	}

	@Override
	public List<CommunityModel> findByDisableFeeAgr(Integer disableTmpParkingFeeAgr, Integer disableMonthyParkingFeeAgr,
			Integer disablePropertyFeeAgr) {
		StringBuffer hql = new StringBuffer("from CommunityModel where 1=1");
		if(disableTmpParkingFeeAgr!=null){
			hql.append(" and disableTmpParkingFeeAgr =:disableTmpParkingFeeAgr");
		}
		if(disableMonthyParkingFeeAgr!=null){
			hql.append(" and disableMonthyParkingFeeAgr =:disableMonthyParkingFeeAgr");
		}
		if(disablePropertyFeeAgr!=null){
			hql.append(" and disablePropertyFeeAgr =:disablePropertyFeeAgr");
		}
		TypedQuery<CommunityModel> query = entityManager.createQuery(hql.toString(), CommunityModel.class);
		if(disableTmpParkingFeeAgr!=null){
			query.setParameter("disableTmpParkingFeeAgr", (byte)disableTmpParkingFeeAgr.intValue());
		}
		if(disableMonthyParkingFeeAgr!=null){
			query.setParameter("disableMonthyParkingFeeAgr", (byte)disableMonthyParkingFeeAgr.intValue());
		}
		if(disablePropertyFeeAgr!=null){
			query.setParameter("disablePropertyFeeAgr", (byte)disablePropertyFeeAgr.intValue());
		}
		List<CommunityModel> list = new ArrayList<CommunityModel>();
		list = query.getResultList();
		return list;
	}

	@Override
	public List<CommunityModel> findByIsAxd(Integer isAxd) {
		String hql = "from com.arf.core.entity.CommunityModel where isAxd ='" + isAxd + "'";
		return entityManager.createQuery(hql, CommunityModel.class).getResultList();
	}

	@Override
	public List<CommunityModel> findByCommunityNumbers(List<String> communityNumbers) {
		if(communityNumbers!=null && communityNumbers.size() > 0){
			String hql = "from com.arf.core.entity.CommunityModel where community_number in (:communitynumber)";
			TypedQuery<CommunityModel> query=entityManager.createQuery(hql, CommunityModel.class);	
			query.setParameter("communitynumber", communityNumbers);
			List<CommunityModel> objList =query.getResultList();
			if(!CollectionUtils.isEmpty(objList)){			
				return objList;
			}		
		}
		return Lists.newArrayList();
	}

}
