package com.arf.review.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.review.dao.ServiceReviewDao;
import com.arf.review.entity.ServiceReview;
import com.arf.review.entity.ServiceReview.Type;

@Repository("serviceReviewDao")
public class ServiceReviewDaoImpl extends BaseDaoImpl<ServiceReview, Long> implements ServiceReviewDao{

	@Override
	public List<ServiceReview> findByOutTradeNo(String orderNo) {
		String hql = "from ServiceReview where orderNo = :orderNo";
		TypedQuery<ServiceReview> query = entityManager.createQuery(hql,ServiceReview.class);
		query.setParameter("orderNo",orderNo);
		return query.getResultList();
	}

	@Override
	public ServiceReview findByOutTradeNoAndForReview(String outTradeNo,ServiceReview forReview) {
		StringBuffer hql = new StringBuffer("from ServiceReview where orderNo = :orderNo");
		if(forReview != null){
			hql.append(" and forReview = :forReview");
		}else{
			hql.append(" and forReview is null");
		}
		TypedQuery<ServiceReview> query = entityManager.createQuery(hql.toString(),ServiceReview.class);
		query.setParameter("orderNo",outTradeNo);
		if(forReview != null){
			query.setParameter("forReview",forReview);
		}
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PageResult<Map<String,Object>> findReviewInfo(String orderNo, Integer score, Type type, Integer pageSize,
			Integer pageNo, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		StringBuffer sql =new StringBuffer("SELECT ");
		sql.append(" r.create_date createDate,r.anonymous anonymous,r.images images,");
		sql.append(" r.type type,r.order_no orderNo,r.belong_type belongType,");
		sql.append(" x.nickname nickname ,x.photo photo ,x.username username ,r.is_show isShow,");
		sql.append(" r.content content,r.score score,p.mobile businessPhone,p.business_pic businessPic ,");
		sql.append(" p.address businessAddress ,p.business_name businessName");
		sql.append(" FROM r_service_review r ");
		sql.append(" LEFT JOIN p_business p ON p.business_num=r.business_num 	");
		sql.append(" LEFT JOIN xx_member x ON x.username=r.username");
		sql.append(" WHERE 1=1 and r.for_review is null ");
		StringBuffer sqlCount =new StringBuffer("SELECT count(*) FROM r_service_review r ");
		sqlCount.append(" LEFT JOIN p_business p ON p.business_num=r.business_num 	");
		sqlCount.append(" LEFT JOIN xx_member x ON x.username=r.username");
		sqlCount.append(" WHERE 1=1 and r.for_review is null ");
		if (orderNo!=null) {
			sql.append(" AND r.order_no=:orderNo ");
			sqlCount.append(" AND r.order_no=:orderNo ");
		}
		if (isImages!=null) {
			switch (isImages) {
			case 0:
				sql.append(" AND r.images is not null");
				sqlCount.append(" AND r.images is not null");
				break;
			case 1:
				sql.append(" AND r.images is null ");
				sqlCount.append(" AND r.images is null");
				break;
				
			}
		}
		
		if (score!=null) {
			switch (score) {
			case 1:
				sql.append(" AND r.score<1");
				sqlCount.append(" AND r.score<1");
				break;
			case -1:
				sql.append(" AND r.score<1");
				sqlCount.append(" AND r.score<1");
				break;
			case 2:
				sql.append(" AND 1=<r.score<2 ");
				sqlCount.append(" AND 1=<r.score<score<2");
				break;
			case -2:
				sql.append(" AND r.score<2");
				sqlCount.append(" AND r.score<2");
				break;
			case 3:
				sql.append(" AND 2=<r.score<3");
				sqlCount.append(" 2=<AND r.score<3");
				break;
			case -3:
				sql.append(" AND r.score<3");
				sqlCount.append(" AND r.score<3");
				break;
			case 4:
				sql.append(" AND 3=<r.score<4");
				sqlCount.append(" AND 3=<r.score<4");
				break;
			case -4:
				sql.append(" AND r.score<score<4");
				sqlCount.append(" AND r.score<4");
				break;
			case 5:
				sql.append(" AND 4=<r.score<=5");
				sqlCount.append(" AND 4=<r.score<=5");
				break;
			case -5:
				sql.append(" AND r.score<5");
				sqlCount.append(" AND r.score<5");
				break;
			default:
				break;
			}
		}
		if (type!=null) {
			sql.append(" AND r.type=:type");
			sqlCount.append(" AND r.type=:type");
		}
		if (anonymous!=null) {
			sql.append(" AND r.anonymous=:anonymous");
			sqlCount.append(" AND r.anonymous=:anonymous");
		}
		if (businessNum!=null) {
			sql.append(" AND r.business_num=:businessNum");
			sqlCount.append(" AND r.business_num=:businessNum");
		}
		if (userName!=null) {
			sql.append(" AND r.username=:userName");
			sqlCount.append(" AND r.username=:userName");
		}
		if (isImages!=null) {
			switch (isImages) {
			case 0:
				sql.append(" AND r.images is NOT NULL");
				sqlCount.append(" AND r.images is NOT NULL");
				break;
			case 1:
				sql.append(" AND r.images is  NULL");
				sqlCount.append(" AND r.images is  NULL");
				break;
			default:
				break;
			}
		}
		sql.append("  GROUP BY r.create_date DESC");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryCount =entityManager.createNativeQuery(sqlCount.toString());
		if (orderNo!=null) {
			query.setParameter("orderNo", orderNo);
			queryCount.setParameter("orderNo", orderNo);
		}
		if (type!=null) {
			query.setParameter("type", type.ordinal());
			queryCount.setParameter("type", type.ordinal());
		}
		if (anonymous!=null) {
			query.setParameter("anonymous", anonymous);
			queryCount.setParameter("anonymous", anonymous);
		}
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
			queryCount.setParameter("businessNum", businessNum);
		}
		if (userName!=null) {
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list =query.getResultList();
		int count=0;
		try {
			count=((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String, Object>> page = new PageResult<Map<String, Object>>(list,count);
		return page;
	}

	@Override
	public List<ServiceReview> findByOutTradeNos(List<Object> orderNos) {
		String hql="from ServiceReview where orderNo in (:orderNos) and forReview is not null ";
		
		TypedQuery<ServiceReview> query =entityManager.createQuery(hql,ServiceReview.class);
		query.setParameter("orderNos", orderNos);
		return query.getResultList();
	}

	@Override
	public Map<String,Object> selectCountImages(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		
		StringBuffer sql=new StringBuffer(" SELECT SUM(p.imagesCount) havePicCount,SUM(p.noImagesCount)notPicCount FROM (");
		sql.append(" SELECT COUNT(*)as imagesCount,0 as noImagesCount FROM r_service_review  re where 1=1 ");
		if (businessNum!=null) {
			sql.append(" and re.business_num=:businessNum");	
		}
		if (type !=null) {
			sql.append(" AND re.type=:type");
		}
		sql.append(" and  re.images is NOT NULL");
		sql.append(" UNION ALL");
		sql.append(" SELECT 0 as imagesCount,COUNT(*)as noImagesCount FROM r_service_review r where 1=1 ");
		if (businessNum!=null) {
			sql.append(" and r.business_num=:businessNum");	
		}
		if (type !=null) {
			sql.append(" AND r.type=:type");
		}
		sql.append(" and  r.images is null ) p");
		Query query =entityManager.createNativeQuery(sql.toString());
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
		}
		if (type!=null) {
			query.setParameter("type", type.ordinal());
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map=query.getResultList();
		return CollectionUtils.isEmpty(map)?null:map.get(0);
	}

	@Override
	public Map<String, Object> findCountByScores(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		StringBuffer sql=new StringBuffer(" SELECT SUM(p.one)one ,SUM(p.two)two,SUM(p.three)three,SUM(p.four)four,SUM(p.five)five FROM ");
		sql.append(" (SELECT ");
		sql.append(" CASE WHEN score=1 THEN 1 else 0 END one,");
		sql.append(" CASE WHEN score=2 THEN 1 else 0 END two,");
		sql.append(" CASE WHEN score=3 THEN 1 else 0 END three,");
		sql.append(" CASE WHEN score=4 THEN 1 else 0 END four,");
		sql.append(" CASE WHEN score=5 THEN 1 else 0 END five,");
		sql.append(" score FROM r_service_review ");
		if (businessNum!=null) {
			sql.append(" where business_num=:businessNum");
		}
		if (type !=null) {
			sql.append(" AND type=:type");
		}
		if (isImages!=null) {
			switch (isImages) {
			case 0:
				sql.append(" AND images is NOT NULL");
				break;
			case 1:
				sql.append(" AND images is  NULL");
				break;
			default:
				break;
			}
		}
		sql.append(" )p");
		
		Query query=entityManager.createNativeQuery(sql.toString());
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
		}
		if (type!=null) {
			query.setParameter("type", type.ordinal());
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map=query.getResultList();
		return CollectionUtils.isEmpty(map)?null:map.get(0);
	}
}


















