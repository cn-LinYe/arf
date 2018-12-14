package com.arf.platform.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.platform.dao.PParkingcarDao;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.PParkingcar.StopType;

/**
 * Dao - PParkingcar表
 * 
 * @author arf
 * @version 1.0
 */
@Repository("PParkingcarDaoImpl")
public class PParkingcarDaoImpl extends BaseDaoImpl<PParkingcar, Long> implements PParkingcarDao {

	@Override
	public PParkingcar findByCommLic(String communityNo, String license) {
		String hql = "from com.arf.platform.entity.PParkingcar where communityNo = :communityNo and license = :license";
		TypedQuery<PParkingcar> query = entityManager.createQuery(hql,PParkingcar.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		List<PParkingcar> list = query.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public PParkingcar findByCommLicArr(String communityNo, String license, Date arriveTime) {
		if (StringUtils.isEmpty(license)) {
            return null;
        }
		String hql = "from com.arf.platform.entity.PParkingcar where communityNo = :communityNo and license = :license and arriveTime = :arriveTime" ;
		TypedQuery<PParkingcar> query = entityManager.createQuery(hql,PParkingcar.class);
		query.setParameter("communityNo", communityNo);
		query.setParameter("license", license);
		query.setParameter("arriveTime", arriveTime);
		List<PParkingcar> list = query.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public PParkingcar findByLicenseAndArriveTime(String license,Date arriveTime) {
		if (StringUtils.isEmpty(license)) {
            return null;
        }
		String hql = "from com.arf.platform.entity.PParkingcar where license = :license and arriveTime = :arriveTime" ;
		TypedQuery<PParkingcar> query = entityManager.createQuery(hql,PParkingcar.class);
		query.setParameter("license", license);
		query.setParameter("arriveTime", arriveTime);
		List<PParkingcar> list = query.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<PParkingcar> findByLic(String license) {
		if (StringUtils.isEmpty(license)) {
            return null;
        }
		String hql = "from com.arf.platform.entity.PParkingcar where  license = :license order by arriveTime desc" ;
		TypedQuery<PParkingcar> query = entityManager.createQuery(hql,PParkingcar.class);
		query.setParameter("license", license);
		List<PParkingcar> list = query.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			return list;
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> findByLicenseList(List<String> licenses) {
		if (CollectionUtils.isEmpty(licenses)) {
            return null;
        }
		StringBuffer sql =new StringBuffer(" select p.community_no parkingId,p.id parkindCarId,c.community_name parkingName,"
				+ "p.license,r.userName ,rp.book_time bookTime,p.arrive_time arriveTime ");
		sql.append(" FROM p_parkingcar p ");
		sql.append(" left join (");
		//取每个车最新一条入场时间
		sql.append(" SELECT MAX(t.arrive_time)arriveTime,t.license,t.user_name as userName "
				+ " FROM r_stoprecord t WHERE t.license in(:licenses) GROUP BY t.license ) r ON r.license=p.license");
		// 按照最新一条预约时间排序
		sql.append(" left join  ");
		sql.append(" r_parking_order_record rp ON rp.license=p.license and rp.arrive_time =p.arrive_time");
		sql.append(" left join community c on c.community_number =p.community_no ");
		
		sql.append(" left join park_rate prk on (p.license = prk.license_plate_number "
				+ " and p.community_no = prk.community_number and prk.create_time < now() and prk.end_time > now()) ");
		
		sql.append(" where 1=1   AND p.arrive_time =(SELECT MAX(arrive_time) FROM p_parkingcar as b where p.license = b.license and  b.license in(:licenses))");
		sql.append(" and (p.community_no <> prk.community_number or prk.community_number is null) ");
		sql.append(" and (p.stop_type ="+StopType.Temp_Parking.ordinal()+" or p.stop_type ="+StopType.Member_Account.ordinal()+") order by p.arrive_time desc");
		Query query =this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("licenses", licenses);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map =query.getResultList();
		return map;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PParkingcar> findTempParkingCar(String userName) {
//		List<PParkingcar> carsList = new ArrayList<PParkingcar>();
//		StringBuffer sql = new StringBuffer("select ppc.* from p_parkingcar ppc ");
//		sql.append("left join license_plate lp on ppc.license = lp.license_plate_number ");
//		sql.append("left join park_rate pr on ");
//		sql.append("( lp.license_plate_number = pr.license_plate_number and ppc.community_no = pr.community_number and pr.create_time < now() and pr.end_time > now() ) ");
//		sql.append("where 1=1 and (ppc.stop_type = :Temp_Parking or ppc.stop_type = :Member_Account) and lp.user_name = :userName and (ppc.community_no <> pr.community_number or pr.community_number is null) ");
//		carsList = this.entityManager.createNativeQuery(sql.toString(), PParkingcar.class)
//				.setParameter("userName", userName)
//				.setParameter("Temp_Parking", PParkingcar.StopType.Temp_Parking.ordinal())
//				.setParameter("Member_Account", PParkingcar.StopType.Member_Account.ordinal())
//				.getResultList();
//		Map<String,PParkingcar> tmp = new HashMap<String,PParkingcar>();
//		for(PParkingcar car : carsList){
//			String license = car.getLicense();
//			Date arriveTime = car.getArriveTime();
//			if(StringUtils.isBlank(license) || arriveTime == null){
//				continue;
//			}
//			if(!tmp.containsKey(license)){
//				tmp.put(license, car);
//			}else{
//				PParkingcar pre = tmp.get(license);
//				if(arriveTime.after(pre.getArriveTime())){
//					tmp.put(license, car);
//				}
//			}
//		}
		
		List<PParkingcar> carsList = new ArrayList<PParkingcar>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ( "+
					 "	SELECT * FROM ( "+
					"			SELECT ppc.* "+
					"			FROM p_parkingcar ppc "+
					"			LEFT JOIN license_plate lp ON ppc.license = lp.license_plate_number "+
					"			WHERE 1=1 AND (ppc.stop_type = :Temp_Parking OR ppc.stop_type = :Member_Account) AND lp.user_name = :userName "+
					"			ORDER BY ppc.arrive_time DESC "+
					"		) tmp "+
					"		GROUP BY tmp.license "+
					"	) utmp "+
					" LEFT JOIN park_rate pr ON ( "+
					" 	utmp.license = pr.license_plate_number AND utmp.community_no = pr.community_number AND pr.create_time < NOW() AND pr.end_time > NOW() "+
					" ) "+
					" WHERE 1=1 AND (utmp.community_no <> pr.community_number OR pr.community_number IS NULL) order by utmp.arrive_time desc"
					);
		
		carsList = this.entityManager.createNativeQuery(sql.toString(), PParkingcar.class)
				.setParameter("userName", userName)
				.setParameter("Temp_Parking", PParkingcar.StopType.Temp_Parking.ordinal())
				.setParameter("Member_Account", PParkingcar.StopType.Member_Account.ordinal())
				.getResultList();
		return carsList;
	}

	@Override
	public List<Long> findParkingcarIdWithoutCommunity(Date startCreate,Date endCreate) {
		StringBuffer sql = new StringBuffer("select a.id as ID from (select id,community_no from p_parkingcar where community_no is not null) a "
				+ " left join community b on a.community_no = b.community_number "
				+ " left join p_parkinginfo c on a.community_no = c.parking_no "
				+ " where b.community_number is NULL AND c.parking_no is NULL "
				);
		if(startCreate != null){
			sql.append(" and create_date >= :startCreate ");
		}
		if(endCreate != null){
			sql.append(" and create_date <= :endCreate ");
		}
		Query query =  this.entityManager.createNativeQuery(sql.toString());
		SQLQuery SQLQuery = query.unwrap(SQLQuery.class);
		SQLQuery.addScalar("ID",StandardBasicTypes.LONG);
		 if(startCreate != null){
			 SQLQuery.setParameter("startCreate", startCreate);
		 }
		 if(endCreate != null){
			 SQLQuery.setParameter("endCreate", endCreate);
		 }
		 return	SQLQuery.list();
	}

	@Override
	public int deleteByIds(Long... ids) {
		if(ids != null && ids.length > 0){
			String sql = " delete from p_parkingcar where id in(:ids) ";
			return this.entityManager.createNativeQuery(sql)
					.setParameter("ids", Arrays.asList(ids))
					.executeUpdate();
		}else{
			return 0;
		}
	}

	@Override
	public List<Long> findRepeatParkingcar(Date startArrive, Date endArrive) {
		StringBuffer sql = new StringBuffer(" select t.license,t.community_no,count(*) as c "
				+ " from p_parkingcar t group by t.license,t.community_no having c>1 order by c desc ");
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> result = query.getResultList();
		StringBuffer listSql = new StringBuffer("select id as ID from p_parkingcar where license = :license and community_no = :communityNo ");
		if(startArrive != null){
			listSql.append(" and arrive_time >= :startArrive ");
		}
		if(endArrive != null){
			listSql.append(" and arrive_time <= :endArrive ");
		}
		listSql.append(" order by arrive_time desc");
		query = this.entityManager.createNativeQuery(listSql.toString());
		SQLQuery SQLQuery = query.unwrap(SQLQuery.class);
		SQLQuery.addScalar("ID",StandardBasicTypes.LONG);
		if(startArrive != null){
			SQLQuery.setParameter("startArrive", startArrive);
		}
		if(endArrive != null){
			SQLQuery.setParameter("endArrive", endArrive);
		}
		
		List<Long> idList = new ArrayList<Long>();
		for(Map<String,Object> res : result){
			String license = (String) res.get("license");
			String communityNo = (String) res.get("community_no");
			if(StringUtils.isBlank(license) || StringUtils.isBlank(communityNo)){
				continue;
			}
			SQLQuery.setParameter("license", license);
			SQLQuery.setParameter("communityNo", communityNo);
			List<Long> ids =  SQLQuery.list();
			if(ids != null && ids.size() > 1){
				idList.addAll(ids.subList(1, ids.size()));
			}
		}
		return idList;
	}

	@Override
	public List<PParkingcar> findByUserName(String userName) {
		StringBuffer sb =new StringBuffer(" from com.arf.platform.entity.PParkingcar where  license in ( ");
		sb.append("select license_plate_number from LicensePlateModel l where user_name =:userName )");
		sb.append(" and (stopType ="+StopType.Member_Account.ordinal() +" or stopType =" +StopType.Temp_Parking.ordinal() +")");
		TypedQuery<PParkingcar> query = entityManager.createQuery(sb.toString(),PParkingcar.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

	@Override
	public int findAdmissionTodayByParkingId(String communityNo) {
		String sql="SELECT COUNT(1) FROM p_parkingcar WHERE to_days(create_date) = to_days(now()) and community_no=:communityNo";
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("communityNo",communityNo);
		int count = 0; 
		try {
			count = ((BigInteger) query.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean deleteById(Long id) {
		String sql = "delete from p_parkingcar where id = :id";
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("id",id);
		int count = 0; 
		try {
			count = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByCommunityNumberAndLicense(String communityNo,
			String license) {
		String sql = "delete from p_parkingcar where community_no = :community_no and license = :license";
		Query query = this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("community_no",communityNo);
		query.setParameter("license",license);
		int count = 0; 
		try {
			count = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findGasCardByCommunityNo(String communityNo) {
		StringBuffer sql =new StringBuffer(" select p.community_no parkingId,p.id parkindCarId,"
				+ "p.license,r.userName ,p.arrive_time arriveTime ");
		sql.append(" FROM p_parkingcar p ");
		sql.append("  RIGHT JOIN (");
		//取每个车最新一条入场时间
		sql.append(" select max(pc.license) license, max(pc.arrive_time),max(pc.id) id from p_parkingcar as pc WHERE pc.community_no=:communityNo GROUP BY pc.license)");
		sql.append("  as b ON p.license=b.license AND p.id=b.id ");
		
		sql.append(" left join (");
		//取每个车最新一条入场时间
		sql.append(" SELECT MAX(t.create_date)arriveTime,t.license_plate_number license,t.user_name as userName ");
		sql.append(" FROM refuel_gas_order t LEFT JOIN p_business_to_community bc ON bc.business_num=t.business_num");
		sql.append(" WHERE bc.community_num=:communityNo GROUP BY t.license_plate_number ) r ON r.license=p.license");
		
		sql.append(" where p.community_no=:communityNo");
		Query query =this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("communityNo", communityNo);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map =query.getResultList();
		return map;
	}

	@Override
	public List<Map<String, Object>> findGasCardByLicenseList(List<String> licenses) {
		StringBuffer sql =new StringBuffer(" select p.community_no parkingId,p.id parkindCarId,"
				+ "p.license,r.userName ,p.arrive_time arriveTime ");
		sql.append(" FROM p_parkingcar p ");
		sql.append("  RIGHT JOIN (");
		sql.append(" select max(pc.license) license, max(pc.arrive_time),max(pc.id) id from p_parkingcar as pc WHERE pc.license in(:licenses) GROUP BY pc.license)");
		sql.append("  as b ON p.license=b.license AND p.id=b.id ");
		sql.append(" left join (");
		//取每个车最新一条入场时间
		sql.append(" SELECT MAX(t.create_date)arriveTime,t.license_plate_number license,t.user_name as userName "
				+ " FROM refuel_gas_order t WHERE t.license_plate_number  in(:licenses) GROUP BY t.license_plate_number ) r ON r.license=p.license");
		
		sql.append(" where p.license in(:licenses)");
		Query query =this.entityManager.createNativeQuery(sql.toString());
		query.setParameter("licenses", licenses);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> map =query.getResultList();
		return map;
	}

}

