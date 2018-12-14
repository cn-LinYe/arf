/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.AreaDao;
import com.arf.core.entity.Area;

/**
 * Dao - 地区
 * 
 * @author arf
 * @version 4.0
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	@Override
	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Area> findParents(Area area, boolean recursive, Integer count) {
		if (area == null || area.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<Area> query;
		if (recursive) {
			String jpql = "select area from Area area where area.id in (:ids) order by area.grade asc";
			query = entityManager.createQuery(jpql, Area.class).setParameter("ids", Arrays.asList(area.getParentIds()));
		} else {
			String jpql = "select area from Area area where area = :area";
			query = entityManager.createQuery(jpql, Area.class).setParameter("area", area.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Area> findChildren(Area area, boolean recursive, Integer count) {
		TypedQuery<Area> query;
		if (recursive) {
			if (area != null) {
				String jpql = "select area from Area area where area.treePath like :treePath order by area.grade asc, area.order asc";
				query = entityManager.createQuery(jpql, Area.class).setParameter("treePath", "%" + Area.TREE_PATH_SEPARATOR + area.getId() + Area.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select area from Area area order by area.grade asc, area.order asc";
				query = entityManager.createQuery(jpql, Area.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<Area> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select area from Area area where area.parent = :parent order by area.order asc";
			query = entityManager.createQuery(jpql, Area.class).setParameter("parent", area);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序地区
	 * 
	 * @param areas
	 *            地区
	 */
	private void sort(List<Area> areas) {
		if (CollectionUtils.isEmpty(areas)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
		for (Area area : areas) {
			orderMap.put(area.getId(), area.getOrder());
		}
		Collections.sort(areas, new Comparator<Area>() {
			@Override
			public int compare(Area area1, Area area2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(area1.getParentIds(), area1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(area2.getParentIds(), area2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(area1.getGrade(), area2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}
	
	@Override
	public Area findByAreaNo(String areaNo){
	    
	    TypedQuery<Area> query;
	    
	    if(areaNo==null){//如果地区编号为空
	        return null;
	    }else{//如果地区编号不为空
	        String hql="from Area area where area.no=:no";
	        query=entityManager.createQuery(hql,Area.class).setParameter("no",areaNo);
	    }
	    try{
	    	return query.getSingleResult();
	    }catch (Exception e) {
	    	return null;
		}
	}

	@Override
	public Area findByProvinceCityName(String province, String city) {
		String sql =  " select a1.* from xx_area a1 left join ( " +
				 	 "		select name,no from xx_area where name = :province " +
					 "	) a2 on a1.topno = a2.no " +
					 " where a1.name = :city ";
		@SuppressWarnings("unchecked")
		List<Area> list = entityManager.createNativeQuery(sql, Area.class).setParameter("province", province).setParameter("city", city).getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
}