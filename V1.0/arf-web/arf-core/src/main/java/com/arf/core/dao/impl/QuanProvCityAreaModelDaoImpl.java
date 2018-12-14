package com.arf.core.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.dao.QuanProvCityAreaModelDao;
import com.arf.core.dto.AreaDetailInfoDto;
import com.arf.core.entity.QuanProvCityAreaModel;

/**
 * Dao - 省市区表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("quanProvCityAreaModelDaoImpl")
public class QuanProvCityAreaModelDaoImpl extends BaseDaoImpl<QuanProvCityAreaModel, Long> implements QuanProvCityAreaModelDao {

	@Override
	public List<QuanProvCityAreaModel> sellectProvice() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuanProvCityAreaModel> criteriaQuery = criteriaBuilder.createQuery(QuanProvCityAreaModel.class);
		Root<QuanProvCityAreaModel> root = criteriaQuery.from(QuanProvCityAreaModel.class);
		criteriaQuery.select(root);	
		criteriaQuery.where(criteriaBuilder.equal(root.get("arealevel"), "1"));		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<QuanProvCityAreaModel> selectAllCity(String no) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuanProvCityAreaModel> criteriaQuery = criteriaBuilder.createQuery(QuanProvCityAreaModel.class);
		Root<QuanProvCityAreaModel> root = criteriaQuery.from(QuanProvCityAreaModel.class);
		criteriaQuery.select(root);	
		criteriaQuery.where(criteriaBuilder.equal(root.get("topno"), no));		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<QuanProvCityAreaModel> selectAllArea(String areacode) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuanProvCityAreaModel> criteriaQuery = criteriaBuilder.createQuery(QuanProvCityAreaModel.class);
		Root<QuanProvCityAreaModel> root = criteriaQuery.from(QuanProvCityAreaModel.class);
		criteriaQuery.select(root);	
		criteriaQuery.where(criteriaBuilder.equal(root.get("areacode"), areacode));		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<QuanProvCityAreaModel> selectAllQuarters(String no) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuanProvCityAreaModel> criteriaQuery = criteriaBuilder.createQuery(QuanProvCityAreaModel.class);
		Root<QuanProvCityAreaModel> root = criteriaQuery.from(QuanProvCityAreaModel.class);
		criteriaQuery.select(root);	
		criteriaQuery.where(criteriaBuilder.equal(root.get("no"), no));		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<QuanProvCityAreaModel> checkByAreaName(String areaName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuanProvCityAreaModel> criteriaQuery = criteriaBuilder.createQuery(QuanProvCityAreaModel.class);
		Root<QuanProvCityAreaModel> root = criteriaQuery.from(QuanProvCityAreaModel.class);
		criteriaQuery.select(root);	
		criteriaQuery.where(criteriaBuilder.equal(root.get("arealevel"), 2),criteriaBuilder.like(root.<String>get("areaname"), areaName));		
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public AreaDetailInfoDto getAreaDetailInfo(String no) {
		String sql = "select " + 
					" a.areaname as areaName,a.no as no,a.arealevel as areaLevel,concat(a.lng,',',a.lat) as geo,  " + 
					" c.areaname as upperAreaname,c.no as upperNo,c.arealevel as upperAreaLevel,c.geo as upperGeo,  " + 
					" e.areaname as upperAreaname2,e.no as upperNo2,e.arealevel as upperAreaLevel2,e.geo as upperGeo2  " + 
					" from quan_prov_city_area a  " + 
					" left join (select b.areaname,b.no,b.arealevel,b.topno,concat(b.lng,',',b.lat) as geo " +
					" 	from quan_prov_city_area b where b.no like concat(:provincePrefix,'%')) c on a.`topno` = c.no " + 
					" left join (select d.areaname,d.no,d.arealevel,d.topno,concat(d.lng,',',d.lat) as geo " +
					" 	from quan_prov_city_area d where d.no like concat(:provincePrefix,'%')) e on c.`topno`=e.no " +
					" where a.`no` = :no ";
		Query query = super.entityManager.createNativeQuery(sql).setParameter("no", no)
				.setParameter("provincePrefix", StringUtils.left(no, 2));
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		try {
			return JSON.toJavaObject(new JSONObject((Map<String, Object>) query.getSingleResult()), AreaDetailInfoDto.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<QuanProvCityAreaModel> checkByAreaName(List<Integer> nos) {
		if (CollectionUtils.isEmpty(nos)) {
			return new ArrayList<>();
		}
		StringBuffer hql = new StringBuffer(" from QuanProvCityAreaModel t where t.no in (:nos) ");
		Query query =entityManager.createQuery(hql.toString());
		query.setParameter("nos", nos);
		return query.getResultList();
	}
	
}
