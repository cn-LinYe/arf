package com.arf.axdshopkeeper.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.dao.IShopkeeperCommunityDao;
import com.arf.axdshopkeeper.dto.ShopkeeperCommunityDto;
import com.arf.axdshopkeeper.entity.ShopkeeperCommunity;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("shopkeeperCommunityDaoImpl")
public class ShopkeeperCommunityDaoImpl extends
		BaseDaoImpl<ShopkeeperCommunity, Long> implements
		IShopkeeperCommunityDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public PageResult<ShopkeeperCommunity> findByCondition(Integer pageSize,
			Integer pageNo, String cityNo, ShopkeeperCommunityDto.OrderBy orderBy) {
		//查询总数
		StringBuffer sbCount = new StringBuffer("select count(1) as COUNT from bi_shopkeeper_community a ");
		if(StringUtils.isNotBlank(cityNo)){
			sbCount.append(" left join community c on c.community_number = a.community_number ");
		}
		sbCount.append(" where 1 = 1 and (a.status = :status1 or a.status = :status2) ");
		if(StringUtils.isNotBlank(cityNo)){
			//直辖市特殊处理
			if("110000".equals(cityNo)
					|| "120000".equals(cityNo)
					|| "310000".equals(cityNo)
					|| "500000".equals(cityNo)){
				sbCount.append(" and c.provinceno = :cityNo ");
			}else{
				sbCount.append(" and c.cityno = :cityNo ");
			}
		}
		Query queryCount = this.entityManager.createNativeQuery(sbCount.toString());
		queryCount.setParameter("status1", ShopkeeperCommunity.Status.PUBLISH_ING.ordinal());
		queryCount.setParameter("status2", ShopkeeperCommunity.Status.NEGOTIATE_ING.ordinal());
		if(StringUtils.isNotBlank(cityNo)){
			queryCount.setParameter("cityNo", cityNo);
		}		
		Integer count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("ShopkeeperCommunityDaoImpl.findByCondition查询数据条数异常",e);
		}
		
		//分页查询
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" a.id id ");
		sb.append(" ,a.create_date ");
		sb.append(" ,a.modify_date ");
		sb.append(" ,a.version ");
		sb.append(" ,a.browse_count ");
		sb.append(" ,a.community_desc ");
		sb.append(" ,a.community_number ");
		sb.append(" ,a.enter_amount ");
		sb.append(" ,a.image_banner ");
		sb.append(" ,a.image_introduce ");
		sb.append(" ,a.inviting_tender_mobile ");
		sb.append(" ,a.inviting_tender_name ");
		sb.append(" ,a.offline_date ");
		sb.append(" ,a.online_date ");
		sb.append(" ,a.proprietor_count ");
		sb.append(" ,a.status ");
		sb.append(" from bi_shopkeeper_community a ");
		if(StringUtils.isNotBlank(cityNo)){
			sb.append(" left join community c on c.community_number = a.community_number ");
		}
		sb.append(" where 1 = 1 and (a.status = :status1 or a.status = :status2) ");
		if(StringUtils.isNotBlank(cityNo)){
			//直辖市特殊处理
			if("110000".equals(cityNo)
					|| "120000".equals(cityNo)
					|| "310000".equals(cityNo)
					|| "500000".equals(cityNo)){
				sb.append(" and c.provinceno = :cityNo ");
			}else{
				sb.append(" and c.cityno = :cityNo ");
			}
		}
		if(ShopkeeperCommunityDto.OrderBy.SCALE.equals(orderBy)){
			sb.append(" order by proprietor_count desc");
		}else if(ShopkeeperCommunityDto.OrderBy.NEWEST.equals(orderBy)){
			sb.append(" order by online_date desc");
		}else{
			sb.append(" order by proprietor_count desc");//默认按规模
		}
		
		/**
		 * Query javax.persistence.EntityManager.createNativeQuery(String sqlString, Class resultClass)
		 * resultClass必须为hibernate映射的实体
		 */
		Query typedQuery = this.entityManager.createNativeQuery(sb.toString(),ShopkeeperCommunity.class);
		typedQuery.setParameter("status1", ShopkeeperCommunity.Status.PUBLISH_ING.ordinal());
		typedQuery.setParameter("status2", ShopkeeperCommunity.Status.NEGOTIATE_ING.ordinal());
		if(StringUtils.isNotBlank(cityNo)){
			typedQuery.setParameter("cityNo", cityNo);
		}
		//如果没有传分页，则返回全部
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		@SuppressWarnings("unchecked")
		List<ShopkeeperCommunity> shopkeeperCommunitys = typedQuery.getResultList();
		
		PageResult<ShopkeeperCommunity> pageResult = new PageResult<ShopkeeperCommunity>();
		pageResult.setList(shopkeeperCommunitys);
		pageResult.setTotalNum(count);
		return pageResult;
	}

}
