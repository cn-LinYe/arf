package com.arf.axdshopkeeper.shopkeeperrecommend.dao.impl;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.emay.slf4j.Logger;
import cn.emay.slf4j.LoggerFactory;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofRecommendDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofRecommend;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Repository("cofRecommendDaoImpl")
public class CofRecommendDaoImpl extends BaseDaoImpl<CofRecommend, Long> implements ICofRecommendDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//店长推荐首页查询接口（无店长推荐则返回总部推荐）
	@Override
	public CofRecommend findLatest(String communityNumber){
		StringBuffer sb = new StringBuffer("from CofRecommend t where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and (t.communityNumber = :communityNumber or t.type=0)");
		}else{
			sb.append(" and t.type=0");
		}
		sb.append(" order by t.stickTime desc,t.createDate desc");
		TypedQuery<CofRecommend> query = entityManager.createQuery(sb.toString(), CofRecommend.class);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		List<CofRecommend> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 查询店长推荐列表,根据用户名判断用户是否有点赞收藏操作（分页）
	 * @param pageSize
	 * @param pageNo
	 * @param userName
	 * @param communityNumber
	 * @param shopkeeperUserName
	 * @return
	 */
	@Override
	public PageResult<Map<String, Object>> findExtraByCommunityNumberListPage(Integer pageSize,Integer pageNo,String userName,String communityNumber,String shopkeeperUserName){
		if(StringUtils.isBlank(userName)){
			return findExtraByCommunityNumberListPageWithNoUsername(pageSize,pageNo,communityNumber,shopkeeperUserName);
		}
		StringBuffer countSb = new StringBuffer("select count(1) as COUNT from cof_recommend t ");
		countSb.append(" left join cof_user_favorites lk on t.id = lk.recommend_id and lk.user_name = :userName and lk.`type` = 0 and lk.`status` = 0");
		countSb.append(" left join cof_user_favorites co on t.id = co.recommend_id and co.user_name = :userName and co.`type` = 1 and co.`status` = 0");
		countSb.append(" left join (select * from cof_comment inc where inc.user_name = :userName and inc.`status` = 0 group by user_name) c on t.id = c.recommend_id ");
		countSb.append(" where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)
				|| StringUtils.isNotBlank(shopkeeperUserName)){
			countSb.append(" and(");
			countSb.append(" 	( 1=1");
			if(StringUtils.isNotBlank(communityNumber)){
				countSb.append(" and t.community_number =:communityNumber");
			}
			if(StringUtils.isNotBlank(shopkeeperUserName)){
				countSb.append(" and t.user_name =:shopkeeperUserName");
			}
			countSb.append(" 	) or");
		}else{
			countSb.append(" and(");
		}
		countSb.append(" 		t.type=0)");
		Query countQuery = entityManager.createNativeQuery(countSb.toString());
		countQuery.setParameter("userName", userName);
		if(StringUtils.isNotBlank(communityNumber)){
			countQuery.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			countQuery.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		Integer count = 0;
		try{
			count = ((BigInteger)countQuery.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofRecommendDaoImpl.findMapListPage查询异常:"+e);
		}
		
		StringBuffer sb = new StringBuffer("select t.id id,t.create_date createDate,t.modify_date modifyDate,t.version version, t.user_name userName");
		sb.append(" ,t.community_number communityNumber, t.type type, t.name name, t.photo photo, t.introduce introduce, t.content_name contentName");
		sb.append(" ,t.content content, t.content_photo contentPhoto, t.status status, t.like_count likeCount, t.comment_count commentCount");
		sb.append(" ,t.favorites_count favoritesCount, t.recommend_photo recommendPhoto, t.recommend_title recommendTitle, t.recommend_price recommendPrice, t.recommend_url recommendUrl");
		sb.append(" ,case when lk.id is null then 0 else 1 end as isLike");
		sb.append(" ,case when co.id is null then 0 else 1 end as isFavorites");
		sb.append(" ,case when c.id is null then 0 else 1 end as isComment");
		sb.append(" ,case when t.file_type is null then 0 else t.file_type end as fileType");
		sb.append(" from cof_recommend t ");
		sb.append(" left join cof_user_favorites lk on t.id = lk.recommend_id and lk.user_name = :userName and lk.`type` = 0 and lk.`status` = 0");
		sb.append(" left join cof_user_favorites co on t.id = co.recommend_id and co.user_name = :userName and co.`type` = 1 and co.`status` = 0");
		sb.append(" left join (select * from cof_comment inc where inc.user_name = :userName and inc.`status` = 0 group by user_name) c on t.id = c.recommend_id ");
		sb.append(" where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)
				|| StringUtils.isNotBlank(shopkeeperUserName)){
			sb.append(" and (");
			sb.append(" 	( 1=1");
			if(StringUtils.isNotBlank(communityNumber)){
				sb.append(" and t.community_number =:communityNumber");
			}
			if(StringUtils.isNotBlank(shopkeeperUserName)){
				sb.append(" and t.user_name =:shopkeeperUserName");
			}
			sb.append(" 	) or");
		}else{
			sb.append(" and(");
		}
		sb.append(" 		t.type=0)");
		sb.append(" order by t.stick_time desc,t.create_date desc");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			query.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		if(pageSize==null || pageNo==null){
			//没传则返回全部数据
			pageSize = count;
			pageNo = 1;
		}
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map = query.getResultList();
		
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		pageResult.setList(map);
		pageResult.setTotalNum(count);
		return pageResult;
	}
	
	
	
	private PageResult<Map<String, Object>> findExtraByCommunityNumberListPageWithNoUsername(
			Integer pageSize, Integer pageNo, String communityNumber,String shopkeeperUserName) {
		StringBuffer countSb = new StringBuffer("select count(1) as COUNT from cof_recommend t ");
		countSb.append(" where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)
				|| StringUtils.isNotBlank(shopkeeperUserName)){
			countSb.append(" and(");
			countSb.append(" 	( 1=1");
			if(StringUtils.isNotBlank(communityNumber)){
				countSb.append(" and t.community_number =:communityNumber");
			}
			if(StringUtils.isNotBlank(shopkeeperUserName)){
				countSb.append(" and t.user_name =:shopkeeperUserName");
			}
			countSb.append(" 	) or");
		}else{
			countSb.append(" and(");
		}
		countSb.append(" 		t.type=0)");
		Query countQuery = entityManager.createNativeQuery(countSb.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			countQuery.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			countQuery.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		Integer count = 0;
		try{
			count = ((BigInteger)countQuery.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofRecommendDaoImpl.findMapListPage查询异常:"+e);
		}
		
		StringBuffer sb = new StringBuffer("select t.id id,t.create_date createDate,t.modify_date modifyDate,t.version version, t.user_name userName");
		sb.append(" ,t.community_number communityNumber, t.type type, t.name name, t.photo photo, t.introduce introduce, t.content_name contentName");
		sb.append(" ,t.content content, t.content_photo contentPhoto, t.status status, t.like_count likeCount, t.comment_count commentCount");
		sb.append(" ,t.favorites_count favoritesCount, t.recommend_photo recommendPhoto, t.recommend_title recommendTitle, t.recommend_price recommendPrice, t.recommend_url recommendUrl");
		sb.append(" ,0 as isLike");
		sb.append(" ,0 as isFavorites");
		sb.append(" ,0 as isComment");
		sb.append(" ,case when t.file_type is null then 0 else t.file_type end as fileType");
		sb.append(" from cof_recommend t ");
		sb.append(" where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)
				|| StringUtils.isNotBlank(shopkeeperUserName)){
			sb.append(" and (");
			sb.append(" 	( 1=1");
			if(StringUtils.isNotBlank(communityNumber)){
				sb.append(" and t.community_number =:communityNumber");
			}
			if(StringUtils.isNotBlank(shopkeeperUserName)){
				sb.append(" and t.user_name =:shopkeeperUserName");
			}
			sb.append(" 	) or");
		}else{
			sb.append(" and(");
		}
		sb.append(" 		t.type=0)");
		sb.append(" order by t.stick_time desc,t.create_date desc");
		Query query = entityManager.createNativeQuery(sb.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			query.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		if(pageSize==null || pageNo==null){
			//没传则返回全部数据
			pageSize = count;
			pageNo = 1;
		}
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map = query.getResultList();
		
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		pageResult.setList(map);
		pageResult.setTotalNum(count);
		return pageResult;
	}

	//店长推荐列表查询（含总部推荐）
	//根据小区编码，查询此小区的推荐列表，列表中包含店长推荐和总部推荐，根据时间顺序倒叙（分页）
	@Override
	public PageResult<CofRecommend> findByCommunityNumberListPage(Integer pageSize,Integer pageNo,String communityNumber,String shopkeeperUserName){
		StringBuffer countSb = new StringBuffer("select count(1) as COUNT from cof_recommend t where t.status=3");
		countSb.append(" and (( 1=1 ");
		if(StringUtils.isNotBlank(communityNumber)){
			countSb.append(" and t.community_number = :communityNumber");
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			countSb.append(" and t.user_name = :shopkeeperUserName");
		}
		countSb.append(" ) or t.type=0)");
		Query countQuery = entityManager.createNativeQuery(countSb.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			countQuery.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			countQuery.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		Integer count = 0;
		try{
			count = ((BigInteger)countQuery.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofRecommendDaoImpl.findByCommunityNumberListPage查询异常: "+e);
		}
		
		StringBuffer sb = new StringBuffer("from CofRecommend t where t.status=3");
		sb.append(" and (( 1=1 ");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and t.communityNumber = :communityNumber");
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			sb.append(" and t.userName = :shopkeeperUserName");
		}
		sb.append(" ) or t.type=0)");
		sb.append(" order by t.createDate desc");
		TypedQuery<CofRecommend> query = entityManager.createQuery(sb.toString(), CofRecommend.class);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		if(StringUtils.isNotBlank(shopkeeperUserName)){
			query.setParameter("shopkeeperUserName", shopkeeperUserName);
		}
		if(pageSize==null || pageNo==null){
			//没传则返回全部数据
			pageSize = count;
			pageNo = 1;
		}
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		List<CofRecommend> list = query.getResultList();
		
		PageResult<CofRecommend> pageResult = new PageResult<CofRecommend>();
		pageResult.setList(list);
		pageResult.setTotalNum(count);
		return pageResult;
	}
	
	//店长推荐详情
	//根据推荐id,查询当前店长推荐详情
	@Override
	public CofRecommend findById(Long id){
		if(id==null){
			return null;
		}
		StringBuffer sb = new StringBuffer("from CofRecommend t where status=3");
		sb.append(" and t.id=:id");
		TypedQuery<CofRecommend> query = entityManager.createQuery(sb.toString(), CofRecommend.class);
		query.setParameter("id", id);
		List<CofRecommend> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public Map<String,Object> findMapByIdUserName(Long id,String userName){
		if(id==null){
			return null;
		}
		if(StringUtils.isBlank(userName)){
			return findMapById(id);
		}
		StringBuffer sb = new StringBuffer("select t.id id,t.create_date createDate,t.modify_date modifyDate,t.version version, t.user_name userName");
		sb.append(" ,t.community_number communityNumber, t.type type, t.name name, t.photo photo, t.introduce introduce, t.content_name contentName");
		sb.append(" ,t.content content, t.content_photo contentPhoto, t.status status, t.like_count likeCount, t.comment_count commentCount");
		sb.append(" ,t.favorites_count favoritesCount, t.recommend_photo recommendPhoto, t.recommend_title recommendTitle, t.recommend_price recommendPrice, t.recommend_url recommendUrl");
		sb.append(" ,case when lk.id is null then 0 else 1 end as isLike");
		sb.append(" ,case when co.id is null then 0 else 1 end as isFavorites");
		sb.append(" ,case when c.id is null then 0 else 1 end as isComment");
		sb.append(" ,case when t.file_type is null then 0 else t.file_type end as fileType");
		sb.append(" from cof_recommend t ");
		sb.append(" left join cof_user_favorites lk on t.id = lk.recommend_id and lk.user_name = :userName and lk.`type` = 0 and lk.`status` = 0");
		sb.append(" left join cof_user_favorites co on t.id = co.recommend_id and co.user_name = :userName and co.`type` = 1 and co.`status` = 0");
		sb.append(" left join (select * from cof_comment inc where inc.user_name = :userName and inc.recommend_id=:id and inc.`status` = 0 group by user_name) c on t.id = c.recommend_id ");
		sb.append(" where t.status=3 and t.id =:id");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	private Map<String, Object> findMapById(Long id) {
		StringBuffer sb = new StringBuffer("select t.id id,t.create_date createDate,t.modify_date modifyDate,t.version version, t.user_name userName");
		sb.append(" ,t.community_number communityNumber, t.type type, t.name name, t.photo photo, t.introduce introduce, t.content_name contentName");
		sb.append(" ,t.content content, t.content_photo contentPhoto, t.status status, t.like_count likeCount, t.comment_count commentCount");
		sb.append(" ,t.favorites_count favoritesCount, t.recommend_photo recommendPhoto, t.recommend_title recommendTitle, t.recommend_price recommendPrice, t.recommend_url recommendUrl");
		sb.append(" ,0 as isLike");
		sb.append(" ,0 as isFavorites");
		sb.append(" ,0 as isComment");
		sb.append(" ,case when t.file_type is null then 0 else t.file_type end as fileType");
		sb.append(" from cof_recommend t ");
		sb.append(" where t.status=3 and t.id =:id");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<CofRecommend> findByKeyword(String keyword, String communityNumber,int limit) {
		StringBuffer sql = new StringBuffer("select a.* from cof_recommend a left join community b on a.community_number=b.community_number where 1=1 and a.status = :statusNormal ");
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and (a.community_number is null or a.community_number = :communityNumber) ");
		}else{
			sql.append(" and (a.community_number is null) ");
		}
		sql.append(" and ( ");
		sql.append(" a.content_name like concat('%',:keyword,'%') ");
		sql.append(" or a.content like concat('%',:keyword,'%') ");
		sql.append(" or a.introduce like concat('%',:keyword,'%') ");
		sql.append(" or a.recommend_title like concat('%',:keyword,'%') ");
		sql.append(" or b.community_name like concat('%',:keyword,'%') ");
		sql.append(" ) ");
		sql.append(" order by a.create_date desc ");
		sql.append(" limit :limit ");
		Query query = this.entityManager.createNativeQuery(sql.toString(), CofRecommend.class);
		query.setParameter("statusNormal", CofRecommend.Status.PASS.ordinal());
		query.setParameter("keyword", keyword);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.setParameter("limit", limit);
		return query.getResultList();
	}

	@Override
	public Map<String, Object> findNextRecommendByIdUserNameCommunityNumber(
			Long recommendId, String userName, String communityNumber) {
		StringBuffer sb = new StringBuffer("select t.id");
		sb.append(" from cof_recommend t ");
		sb.append(" where t.status=3");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and (");
			sb.append(" ( 1=1");
			sb.append(" 	and t.community_number =:communityNumber");
			sb.append(" ) or");
		}else{
			sb.append(" and(");
		}
		sb.append(" 		t.type=0)");
		sb.append(" order by t.stick_time desc,t.create_date desc");
		Query query = entityManager.createNativeQuery(sb.toString());
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,BigInteger>> mapId = query.getResultList();
		if(CollectionUtils.isEmpty(mapId)){
			return null;
		}else{
			List<BigInteger> listId = Lists.transform(mapId, new Function<Map<String,BigInteger>,BigInteger>(){
				@Override
				public BigInteger apply(Map<String, BigInteger> input) {
					return input.get("id");
				}
			});
			List<BigInteger> listIdCopy = Lists.newLinkedList();
			listIdCopy.addAll(listId);
			System.out.println(listIdCopy);
			BigInteger nextId = null;
			//传入推荐id对应index，不存在取第一条；最后一条取第一条
			int index = listIdCopy.indexOf(new BigInteger(recommendId+""));
			if(index == listIdCopy.size() - 1){
				nextId = listIdCopy.get(0);
			}else{
				nextId = listIdCopy.get(index + 1);
			}
			if(nextId != null){
				return this.findMapByIdUserName(nextId.longValue(), userName);
			}else{
				return null;
			}
		}
	}
}
