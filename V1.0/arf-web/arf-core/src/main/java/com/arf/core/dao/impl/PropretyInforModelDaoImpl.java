package com.arf.core.dao.impl;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.arf.core.dao.PropretyInforModelDao;
import com.arf.core.entity.PropretyInforModel;
/**
 * Dao - 楼栋信息
 * 
 * @author arf  liaotao
 * @version 4.0
 */
@Repository("propretyInforModelDaoImpl")
public class PropretyInforModelDaoImpl extends BaseDaoImpl<PropretyInforModel, Long> implements PropretyInforModelDao {


	@Override
	public List<PropretyInforModel> selectCommunityHouse(String communityNumber) {			
		try {
			String jpql = "select PropretyInfor from PropretyInforModel PropretyInfor where PropretyInfor.communityNumber = :communityNumber group by buildingNumber order by -buildingNumber desc";
			return entityManager.createQuery(jpql, PropretyInforModel.class).setParameter("communityNumber", communityNumber).getResultList();
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public PropretyInforModel selectByHouseId(String houseId) {
		if (StringUtils.isEmpty(houseId)) {
			return null;
		}
		try {
			String jpql = "select propretyInforModel from PropretyInforModel propretyInforModel where propretyInforModel.houseId = :houseId";
			return entityManager.createQuery(jpql, PropretyInforModel.class).setParameter("houseId", houseId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public List<PropretyInforModel> selectByComBuild(String communityNumber, String buildingNumber) {
		try {
			String jpql = "select PropretyInfor from PropretyInforModel PropretyInfor where PropretyInfor.communityNumber = :communityNumber and  PropretyInfor.buildingNumber= :buildingNumber group by floorNumber order by -floorNumber desc";
			TypedQuery<PropretyInforModel> query =entityManager.createQuery(jpql, PropretyInforModel.class);
			query.setParameter("communityNumber", communityNumber);
			query.setParameter("buildingNumber", buildingNumber);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public List<PropretyInforModel> selectByComBuildFloor(String communityNumber, String buildingNumber,String floorNumber) {
		try {
			String jpql = "select propretyInfor from PropretyInforModel propretyInfor where propretyInfor.communityNumber = :communityNumber and  propretyInfor.buildingNumber= :buildingNumber and propretyInfor.floorNumber= :floorNumber order by -houseNmuber desc";
			TypedQuery<PropretyInforModel> query =entityManager.createQuery(jpql, PropretyInforModel.class);
			query.setParameter("communityNumber", communityNumber);
			query.setParameter("buildingNumber", buildingNumber);
			query.setParameter("floorNumber", floorNumber);
			return query.getResultList();			
		} catch (Exception e) {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PropretyInforModel> selectByUsernameAndCommunity(String user_name, String communityNumber) {
		String sql = "SELECT * FROM proprety_infor pi LEFT JOIN user_proprety_infor_record up ON pi.house_id=up.house_id WHERE up.user_name='"+user_name+"' AND up.community_number='"+communityNumber+"'";
		List<PropretyInforModel> PropretyInforModels = entityManager.createNativeQuery(sql, PropretyInforModel.class).getResultList();
		return PropretyInforModels;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PropretyInforModel> selectByUsername(String user_name) {
		String sql = "SELECT * FROM proprety_infor pi LEFT JOIN user_proprety_infor_record up ON pi.house_id=up.house_id WHERE up.user_name='"+user_name+"'";
		List<PropretyInforModel> PropretyInforModels = entityManager.createNativeQuery(sql, PropretyInforModel.class).getResultList();
		return PropretyInforModels;
	}

	
}
