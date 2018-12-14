package com.arf.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.arf.core.HQLResultConvert;
import com.arf.core.dao.SpareUserInforModelDao;
import com.arf.core.entity.SpareUserInforModel;
/**
 * 
 * @author arf zgr
 * @version 4.0
 *
 */
@Repository("spareUserInforModelDaoImpl")
public class SpareUserInforModelDaoImpl extends BaseDaoImpl<SpareUserInforModel,Long> implements SpareUserInforModelDao {

	@Override
	public SpareUserInforModel selectByLicenseCommunity(String community, String LicensePlate) {
		String jpql = "select spareUserInfor from SpareUserInforModel spareUserInfor where spareUserInfor.licensePlateNumber = :licensePlateNumber and spareUserInfor.communityNumber = :communityNumber";
		
		try {
		    List<SpareUserInforModel> list=entityManager.createQuery(jpql, SpareUserInforModel.class).setParameter("licensePlateNumber", LicensePlate).setParameter("communityNumber", community).getResultList();
			return (list==null||list.size()==0)?null:list.get(0);
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public SpareUserInforModel selectByLicensePlate(String LicensePlate) {
		String jpql = "select spareUserInfor from SpareUserInforModel spareUserInfor where spareUserInfor.licensePlateNumber = :licensePlateNumber";		
		try {
			return entityManager.createQuery(jpql, SpareUserInforModel.class).setParameter("licensePlateNumber", LicensePlate).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<SpareUserInforModel> selectByLicenseCommunitys(String LicensePlate,String community) {
		String jpql = "select spareUserInfor from SpareUserInforModel spareUserInfor where spareUserInfor.licensePlateNumber = :licensePlateNumber and spareUserInfor.communityNumber=:community";
		try {
			return entityManager.createQuery(jpql, SpareUserInforModel.class).setParameter("licensePlateNumber", LicensePlate).setParameter("community", community).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<SpareUserInforModel> selectByCommunity(String community) {
		String jpql = "select spareUserInfor from SpareUserInforModel spareUserInfor where spareUserInfor.communityNumber=:community";
		try {
			return entityManager.createQuery(jpql, SpareUserInforModel.class).setParameter("community", community).getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

//	@Override
//	public List<SpareUserInforModel> selectByCommunity(String community) {
//		String jpql = "select spareUserInfor from SpareUserInforModel spareUserInfor where spareUserInfor.communityNumber = :community";		
//		try {
//			return entityManager.createQuery(jpql, SpareUserInforModel.class).setParameter("community", community).getResultList();
//		} catch (NoResultException e) {
//			return null;
//		}
//	};

	public List<SpareUserInforModel> selectByCommunitySingle(String community){
	    String sql="select user_name,community_number from spare_user_infor_model where community_number='"+community+"' and LENGTH(user_name)=11 GROUP BY user_name,community_number";
	    return this.findListBySQL(sql, null, new HQLResultConvert<SpareUserInforModel>() {
            public List<SpareUserInforModel> convert(List list) {
                List<SpareUserInforModel> slist=new ArrayList<SpareUserInforModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    SpareUserInforModel sui=new SpareUserInforModel();
                    sui.setUserName(obj[0]==null?"":obj[0].toString());
                    sui.setCommunityNumber(obj[1]==null?"":obj[1].toString());
                    slist.add(sui);
                }
                return slist;
            }
        });
	}

	
}
