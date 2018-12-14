package com.arf.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.HQLResultConvert;
import com.arf.core.dao.TemporaryLicensePlateDao;
import com.arf.core.entity.TemporaryLicensePlate;

/**
 * Dao - 临时车牌表
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("temporaryLicensePlateDaoImpl")
public class TemporaryLicensePlateDaoImpl extends BaseDaoImpl<TemporaryLicensePlate, Long> implements TemporaryLicensePlateDao {

	@Override
	public TemporaryLicensePlate selectyByLicensePlate(String licensePlateNumber) {
		this.clear();//部分跟道闸交互的接口因多个系统update数据库导致数据不一致,这里需要clear缓存,请【谨慎】修改此处代码 by caolibao 2016-05-05 21:49
		String jpql = "select temporaryLicensePlate from TemporaryLicensePlate temporaryLicensePlate where temporaryLicensePlate.licensePlateNumber = :licensePlateNumber";
		List<TemporaryLicensePlate> temporaryLicensePlates =entityManager.createQuery(jpql, TemporaryLicensePlate.class).setParameter("licensePlateNumber", licensePlateNumber).getResultList();
		TemporaryLicensePlate temporaryLicensePlate = (temporaryLicensePlates==null || temporaryLicensePlates.size()<=0)?null:temporaryLicensePlates.get(0);
		return temporaryLicensePlate;
	}

	@Override
	public List<TemporaryLicensePlate> selectyByState() {
		try {
			String jpql = "select temporaryLicensePlate from TemporaryLicensePlate temporaryLicensePlate where temporaryLicensePlate.state = :3 and temporaryLicensePlate.payAmount>temporaryLicensePlate.calculationAmount";
			List<TemporaryLicensePlate> list=entityManager.createQuery(jpql, TemporaryLicensePlate.class).getResultList();
			return list.isEmpty()?null:list;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
     * 获取用户的所有入场车辆
     * @param userName
     * @return
     */
    public List<TemporaryLicensePlate> selectByUserName(String userName){
        String sql="select lp.license_plate_number from xx_member m "+
                "inner join license_plate lp on m.username=lp.user_name "+
                "inner join temporary_license_plate tl on tl.license_plate_number=lp.license_plate_number "+
                "where m.username='"+userName+"'";
        return findListBySQL(sql, null, new HQLResultConvert<TemporaryLicensePlate>() {
            @Override
            public List<TemporaryLicensePlate> convert(List list) {
                List<TemporaryLicensePlate> tlist=new ArrayList<TemporaryLicensePlate>();
                for(int i=0;i<list.size();i++){
                    String obj=(String)list.get(i);
                    TemporaryLicensePlate tp=new TemporaryLicensePlate();
                    tp.setLicensePlateNumber(obj==null?"":obj);
                    tlist.add(tp);
                }
                return tlist;
            }
        });
    }

	@Override
	public TemporaryLicensePlate selectByLicensePlateAndCommunityNumber(
			String licensePlate, String communityNumber) {
		String hql = "from com.arf.core.entity.TemporaryLicensePlate where licensePlateNumber = '" + licensePlate + "' and communityNumber = '" + communityNumber + "'";
		List<TemporaryLicensePlate> listTemporaryLicensePlate = entityManager.createQuery(hql,TemporaryLicensePlate.class).getResultList();
		if(listTemporaryLicensePlate == null || listTemporaryLicensePlate.size() == 0){
			return null;
		}
		return listTemporaryLicensePlate.get(0);
	}
}