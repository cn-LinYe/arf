package com.arf.core.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.HQLResultConvert;
import com.arf.core.dao.ParkRateModelDao;
import com.arf.core.entity.ParkRateModel;
import com.arf.platform.entity.PrivilegeCar;

/**
 * Dao - ParkRate表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("parkRateModelDaoImpl")
public class ParkRateModelDaoImpl extends BaseDaoImpl<ParkRateModel, Long> implements ParkRateModelDao {

	@Override
	public ParkRateModel selectByLicenseComunity(String communityNumber, String licensePlateNumber) {
		if (StringUtils.isEmpty(communityNumber)||StringUtils.isEmpty(licensePlateNumber)) {
			return null;
		}
		try {
			this.clear();//部分跟道闸交互的接口因多个系统update数据库导致数据不一致,这里需要clear缓存,请【谨慎】修改此处代码 by caolibao 2016-05-05 21:49
			String jpql = "select parkRateModel from ParkRateModel parkRateModel where parkRateModel.communityNumber = :communityNumber and parkRateModel.licensePlateNumber = :licensePlateNumber";
			List<ParkRateModel> parkRateModels =entityManager.createQuery(jpql, ParkRateModel.class).setParameter("licensePlateNumber", licensePlateNumber).setParameter("communityNumber", communityNumber).getResultList();
			ParkRateModel parkRateModel = (parkRateModels==null||parkRateModels.size()<=0)?null:parkRateModels.get(0);
			return parkRateModel;
		} catch (Exception e) {
		    e.printStackTrace();
			return null;
		}
	}
	
	@Override
    public ParkRateModel selectByLicenseComunity(String userName,String communityNumber, String licensePlateNumber) {
        if (StringUtils.isEmpty(communityNumber)||StringUtils.isEmpty(licensePlateNumber)) {
            return null;
        }
        try {
            String jpql = "select parkRateModel from ParkRateModel parkRateModel where parkRateModel.communityNumber = :communityNumber and parkRateModel.licensePlateNumber = :licensePlateNumber and parkRateModel.userName = :userName";
            List<ParkRateModel> parkRateModels =entityManager.createQuery(jpql, ParkRateModel.class).setParameter("licensePlateNumber", licensePlateNumber).setParameter("communityNumber", communityNumber).setParameter("userName", userName).getResultList();
            ParkRateModel parkRateModel = (parkRateModels==null||parkRateModels.size()<=0)?null:parkRateModels.get(0);
            return parkRateModel;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ParkRateModel> selectListByPlateNumber(String licensePlateNumber,String userName) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<ParkRateModel> criteriaQuery = criteriaBuilder.createQuery(ParkRateModel.class);
//        Root<ParkRateModel> root = criteriaQuery.from(ParkRateModel.class);
//        criteriaQuery.select(root);
//        Predicate restrictions = criteriaBuilder.conjunction();
//        restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("licensePlateNumber"), licensePlateNumber));
//        criteriaQuery.where(restrictions);
//        return super.findList(criteriaQuery, null, null,null,null);
        String sql="select DISTINCT cm.community_number community_number,cm.community_name community_name "+
                "from park_rate pr inner join community cm on pr.community_number=cm.community_number "+
                "where 1=1 ";
        if(!StringUtils.isBlank(userName)){
            sql+=" and pr.user_name='"+userName+"'";
        }
        
        if(!StringUtils.isBlank(licensePlateNumber)){
            sql+=" and pr.license_plate_number='"+licensePlateNumber+"' ";
        }else{
            sql+=" UNION "+
                    "select DISTINCT cm.community_number community_number,cm.community_name community_name "+ 
                    "from xx_member m inner join community cm on m.community_number=cm.community_number where m.username='"+userName+"'";
        }
        
        return this.findListBySQL(sql,null, new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> pl=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    ParkRateModel pr=new ParkRateModel();
                    pr.setCommunityNumber(obj[0]==null?"":obj[0].toString());
                    pr.setName(obj[1]==null?"":obj[1].toString());
                    pl.add(pr);
                }
                return pl;
            }
        });
    }
    /***
     * 根据用户和他关联的车牌获取小区
     * @param licensePlateNumber
     * @return
     */
    @Override
    public List<ParkRateModel> selectListByUser(String userName) {
        String sql="select cm.community_number community_number,cm.community_name community_name from xx_member m "+
                "inner join license_plate lp on m.username=lp.user_name "+
                "inner join park_rate pr on lp.license_plate_number=pr.license_plate_number "+
                "inner join community cm on pr.community_number=cm.community_number where m.username='"+userName+"'";        
        sql+=" UNION "+
                "select DISTINCT cm.community_number community_number,cm.community_name community_name "+ 
                "from xx_member m inner join community cm on m.community_number=cm.community_number where m.username='"+userName+"'";
        return this.findListBySQL(sql,null, new HQLResultConvert<ParkRateModel>() {
        	
        	
        	
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> pl=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    ParkRateModel pr=new ParkRateModel();
                    pr.setCommunityNumber(obj[0]==null?"":obj[0].toString());
                    pr.setName(obj[1]==null?"":obj[1].toString());
                    pl.add(pr);
                }
                return pl;
            }
        });
    }

	@Override
	public List<ParkRateModel> selectListByCommunityNumber(String communityNumber) {
        if (StringUtils.isEmpty(communityNumber)) {
            return null;
        }
        try {
            String jpql = "select parkRateModel from ParkRateModel parkRateModel where parkRateModel.communityNumber = :communityNumber ";
            return entityManager.createQuery(jpql, ParkRateModel.class).setParameter("communityNumber", communityNumber).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

	@Override
	public List<ParkRateModel> selectListByLicensePlate(String licensePlate) {
        if (StringUtils.isEmpty(licensePlate)) {
            return null;
        }
        try {
        	this.clear();//部分跟道闸交互的接口因多个系统update数据库导致数据不一致,这里需要clear缓存,请【谨慎】修改此处代码 by caolibao 2016-05-05 21:49
            String jpql = "select parkRateModel from ParkRateModel parkRateModel where parkRateModel.licensePlateNumber = :licensePlate ";
            return entityManager.createQuery(jpql, ParkRateModel.class).setParameter("licensePlate", licensePlate).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	/***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    @Override
	public List<ParkRateModel> selectListByPlateNumberAxd(String licensePlateNumber){
        if (StringUtils.isEmpty(licensePlateNumber)) {
            return null;
        }
        StringBuffer sb = new StringBuffer(" select ");
        sb.append(" pr.community_number, ");
        sb.append(" comm.community_name, ");
        sb.append(" case when comm.is_axd is null then 0 else comm.is_axd end is_axd, ");
        sb.append(" pr.user_locks, ");
        sb.append(" pr.end_time ");
        sb.append(" from park_rate pr ");
        sb.append(" inner join community comm on pr.community_number = comm.community_number where license_plate_number = '");
        sb.append(licensePlateNumber);
        sb.append("'");
        return findListBySQL(sb.toString(), null, new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> plist=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    ParkRateModel prm=new ParkRateModel();
                    prm.setCommunityNumber(obj[0]==null?"":obj[0].toString());
                    prm.setName(obj[1]==null?"":obj[1].toString());
                    prm.setCardNumber(obj[2]==null?"":obj[2].toString());
                    prm.setUserLocks(obj[3]==null?null:Integer.parseInt(obj[3].toString()));
                    prm.setEndTime(obj[4]==null?null:(Timestamp)obj[4]);
                    plist.add(prm);
                }
                return plist;
            }
        });
    }
    /***
     * 根据车牌获取小区信息和是否开启安心点
     * @param licensePlateNumber
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllListByPlateNumberAxd(String userName){
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        try{
            String jpql = "select parkRateModel from ParkRateModel parkRateModel inner join CommunityModel comm on parkRateModel.communityNumber=comm.community_number  where parkRateModel.userName = :userName";
            return entityManager.createQuery(jpql, ParkRateModel.class).setParameter("userName", userName).getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

	@Override
	public List<ParkRateModel> selectListByLicensePlateIn(String licensePlate, Integer inStatus) {
        if (StringUtils.isEmpty(licensePlate)) {
            return null;
        }
        try {
            String jpql = "select parkRateModel from ParkRateModel parkRateModel where parkRateModel.licensePlateNumber = :licensePlate and parkRateModel.inStatus = :inStatus";
            return entityManager.createQuery(jpql, ParkRateModel.class).setParameter("licensePlate", licensePlate).setParameter("inStatus", inStatus).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	/***
     * 获取小区所有开通了安心点的用户
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllUserNameAxd(){
        String sql="select DISTINCT pr.user_name from park_rate pr  "+
                    "inner join community comm on pr.community_number=comm.community_number "+
                    "where comm.is_axd=1 and (pr.user_name is not null and pr.user_name<>'' and pr.user_name<>'nullUser') and pr.user_locks=3";
        return this.findListBySQL(sql, null, new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> plist=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    Object obj=list.get(i);
                    ParkRateModel prm=new ParkRateModel();
                    prm.setUserName(obj.toString());
                    plist.add(prm);
                }
                return plist;
            }
        });
    }
    
    /***
     * 获取所有开通了安心点的小区
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllCommunityAxd(){
        String sql="select community_number from community where is_axd=1";
        return this.findListBySQL(sql, null, new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> plist=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    Object obj=list.get(i);
                    ParkRateModel prm=new ParkRateModel();
                    prm.setCommunityNumber(obj.toString());
                    plist.add(prm);
                }
                return plist;
            }
        });
    }
    
    /***
     * 获取所有需要锁定的车牌
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllNeedLicensePlate(Integer times){
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql="select a.community_number,a.license_plate_number,a.id,c.license_plate_id from park_rate a "+
                "inner join community b on a.community_number=b.community_number and b.is_axd<>1 and (a.user_locks=1 or a.user_locks is null) "+
                "inner join license_plate c on a.license_plate_number=c.license_plate_number "+
                "where (c.over_error_count=0 or c.over_error_count is null) and TIMESTAMPDIFF(MINUTE,c.last_active_time,'"+sim.format(new Date())+"')>"+times;
        return this.findListBySQL(sql,null,new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> plist=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    ParkRateModel pr=new ParkRateModel();
                    Object[] obj=(Object[])list.get(i);
                    pr.setCommunityNumber(obj[0]==null?"":obj[0].toString());
                    pr.setLicensePlateNumber(obj[1]==null?"":obj[1].toString());
                    pr.setId(obj[2]==null?-1:Long.parseLong(obj[2].toString()));
                    pr.setCardNumber(obj[3]==null?"":obj[3].toString());
                    plist.add(pr);
                }
                return plist;
            }
            
        });
    }
    
    /**
     * 更新所有自动锁闸的车的状态
     * @param ids
     */
    @Override
	public void updateParkRate(String ids){
        if(StringUtils.isBlank(ids)){
            return;
        }
        String sql="update park_rate set is_auto_lock=1 where id in ("+ids+")";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
    
    /**
     * 获取用户所有的被自动锁闸的车牌
     * @param user_name
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllLockLicensePlateByUser(String user_name){
        String sql="select a.community_number,a.license_plate_number,a.id,c.license_plate_id from park_rate a "+
                "inner join community b on a.community_number=b.community_number "+
                "inner join license_plate c on a.license_plate_number=c.license_plate_number "+
                "where c.user_name='"+user_name+"' and a.is_auto_lock=1 and (a.user_locks=3 or a.lock_status=3) and b.is_axd=0";
        return this.findListBySQL(sql, null, new HQLResultConvert<ParkRateModel>() {

            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> plist=new ArrayList<ParkRateModel>();
                for(int i=0;i<list.size();i++){
                    ParkRateModel pr=new ParkRateModel();
                    Object[] obj=(Object[])list.get(i);
                    pr.setCommunityNumber(obj[0]==null?"":obj[0].toString());
                    pr.setLicensePlateNumber(obj[1]==null?"":obj[1].toString());
                    pr.setId(obj[2]==null?-1:Long.parseLong(obj[2].toString()));
                    pr.setCardNumber(obj[3]==null?"":obj[3].toString());
                    plist.add(pr);
                }
                return plist;
            }
            
        });
    }
    
    /**
     * 获取用户所有在白名单表的车牌
     * @param user_name
     * @return
     */
    @Override
	public List<ParkRateModel> selectAllLicenseByParkRate(String user_name){
        String sql="select distinct a.license_plate_number,a.user_name from license_plate a "+
                "inner join park_rate b on a.license_plate_number=b.license_plate_number "+
                "where a.user_name='"+user_name+"'";
        
        return this.findListBySQL(sql, null, new HQLResultConvert<ParkRateModel>() {
            @Override
            public List<ParkRateModel> convert(List list) {
                List<ParkRateModel> lpmlist=new ArrayList<ParkRateModel>();
                ParkRateModel prm=null;
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    if(obj[0]==null) continue;
                    prm=new ParkRateModel();
                    prm.setLicensePlateNumber(obj[0].toString());
                    lpmlist.add(prm);
                }
                return lpmlist;
            }
            
        });
        
    }
    /**
     * 更新白名单用户名（根据车牌）
     * @param license
     * @param userNames
     */
	@Override
	public void updateUserName(String userNames,String ids) {
		 if(StringUtils.isBlank(userNames)||StringUtils.isBlank(ids)){
	            return;
	        }
	        String sql="UPDATE park_rate SET user_name="+userNames+" where id in ("+ids+")";
	        entityManager.createNativeQuery(sql).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findNearDeadlime(Calendar date,Integer distanceDay,String beforeOrAfter){
		if(date == null || distanceDay == null){
			return null;
		}
		StringBuffer sql = new StringBuffer("select pr.community_number AS communityNumber,c.community_name as communityName,pr.license_plate_number AS licensePlateNumber,"
				+ " pr.user_name,pr.create_date as createDate,pr.end_time as endTime,lp.user_name AS userName,mb.current_equipment as equipmentType"
				+ " FROM park_rate pr LEFT JOIN license_plate lp ON pr.license_plate_number = lp.license_plate_number "
				+ " LEFT JOIN xx_member mb ON lp.user_name = mb.username "
				+ " left join community c on pr.community_number = c.community_number "
				+ " where lp.user_name is not null and lp.user_name != 'nullUser'"
				+ " and c.community_number is not null and c.community_number <>'' and c.community_number <> '0' ");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate;
		if(beforeOrAfter != null && "AFTER".equals(beforeOrAfter)){
			date.add(Calendar.DATE, distanceDay);
			endDate = sf.format(date.getTime());
			sql.append(" and pr.end_time > now() and pr.end_time <= :endDate ");
		}else{
			date.set(Calendar.DATE, date.get(Calendar.DATE)-distanceDay);
			endDate = sf.format(date.getTime());
			sql.append(" and pr.end_time >= :endDate and pr.end_time <= now()");
		}
		Query query = entityManager.createNativeQuery(sql.toString()).setParameter("endDate",endDate);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		return rows;
	}

	@Override
	public PrivilegeCar findPrivilegeCarByLicenseCommunity(String license, String community) {
		if (StringUtils.isBlank(license) || StringUtils.isBlank(community)) {
            return null;
        }
        StringBuffer sb = new StringBuffer("select ");
        sb.append("spc.id,spc.create_date,spc.modify_date,");
        sb.append("spc.user_name,spc.license,spc.parking_id,spc.type,spc.remark,");
        sb.append("spc.poperty_number,spc.head_office_id,spc.branch_id ");
        sb.append("from s_privilege_car spc ");
        sb.append("where spc.license = :license and spc.parking_id = :community");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("license", license);
        query.setParameter("community", community);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> rows = query.getResultList();
        if(CollectionUtils.isNotEmpty(rows)){
        	Map<String,Object> map = rows.get(0);
        	long id = Long.valueOf(map.get("id").toString());
        	Date createDate = map.get("create_date") == null?null:(Timestamp)map.get("create_date");
        	Date modifyDate = map.get("modify_date") == null?null:(Timestamp)map.get("modify_date");
        	String userName = map.get("user_name") == null?null:map.get("user_name").toString();
        	String licensePlate = map.get("license") == null?null:map.get("license").toString();
        	String parkingId = map.get("parking_id") == null?null:map.get("parking_id").toString();
        	Integer type = map.get("type") == null?null:Integer.valueOf(map.get("type").toString());//特权类型：0特殊人群 1 公职特权',
        	String remark = map.get("remark") == null?null:map.get("remark").toString();
        	Integer popertyNumber = map.get("poperty_number") == null?null:Integer.valueOf(map.get("poperty_number").toString());
        	Integer headOfficeId = map.get("head_office_id") == null?null:Integer.valueOf(map.get("head_office_id").toString());
        	Integer branchId = map.get("branch_id") == null?null:Integer.valueOf(map.get("branch_id").toString());
        	return  new PrivilegeCar(id, createDate, modifyDate, userName, licensePlate, parkingId, type, remark, popertyNumber, headOfficeId, branchId);
        }else{
        	return null;
        }
	}

	@Override
	public PageResult<Map<String,Object>> findByCondition(String userName, Integer pageSize, Integer pageNo) {
		StringBuffer sql =new StringBuffer(" SELECT p.create_date AS createDate,p.user_name AS username ,c.community_name AS communityName ,");
		sql.append("  p.license_plate_number AS licensePlateNumber ,p.amount as amount,p.charge_type AS chargeType ,p.out_trade_no as outTradeNo ,");
		sql.append("  p.parking_type AS parkingType ,p.record_type AS recordType  ,p.start_time AS startTime  ,p.end_time AS endTime, p.subject AS subject,");
		sql.append("  p.trade_status AS tradeStatus  ,p.create_date AS orderTime  ,p.is_submit_success as isSubmitSuccess ,p.fee_pay_type AS feePayType , ");
		sql.append("  p.point,p.voucher_name as voucherName");
		sql.append("  FROM park_rate_record p ");
		sql.append("  LEFT JOIN community c ON c.community_number =p.community_number");
		sql.append(" WHERE 1=1");
		sql.append(" AND p.trade_status=9000");

		StringBuffer sqlCount =new StringBuffer(" select Count(*)");
		sqlCount.append(" FROM park_rate_record p ");
		sqlCount.append("  LEFT JOIN community c ON c.community_number =p.community_number");
		sqlCount.append("  WHERE 1=1");
		sqlCount.append("  AND p.trade_status=9000");
		
		
		
		if (userName!=null) {
			sql.append("   AND user_name=:userName");
			sqlCount.append("   AND user_name=:userName");
		}
		
		sql.append("  ORDER BY p.create_date DESC ");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		Query queryCount = entityManager.createNativeQuery(sqlCount.toString());
		if (userName!=null) {
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo-1)*pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> map=query.getResultList();
		int count=0;
		try {
			count=((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
		}
		PageResult<Map<String, Object>> result = new PageResult<Map<String, Object>>(map,count);
		return result;
	}

	@Override
	public List<Map<String,Object>> findUserParkRate(String userName) {
		StringBuffer sb =new StringBuffer(" SELECT 0 as licensePlateType ,p.license_plate_number licensePlateNumber , p.create_time createTime ,p.end_time endTime ,p.community_number communityNumber ,c.community_name communityName  FROM park_rate p ");
		sb.append(" LEFT JOIN community c ON c.community_number =p.community_number ");
		sb.append(" LEFT JOIN p_parkinginfo pi ON pi.parking_no =p.community_number");
		sb.append(" WHERE 1=1 AND (pi.field_type=0 or pi.field_type is NULL)  AND p.license_plate_number in( ");
		sb.append(" SELECT l.license_plate_number FROM license_plate l WHERE l.user_name =:userName and l.license_plate_number is not null");
		sb.append(" ) ");
		sb.append("  UNION ALL");
		sb.append(" SELECT ");
		sb.append(" 1 as licensePlateType,l.license_plate_number as licensePlateNumber ,null as createTime ,null AS endTime,null AS communityNumber,null as communityName");
		sb.append(" FROM license_plate l WHERE l.user_name =:userName  AND l.license_plate_number  NOT in(");
		sb.append(" SELECT p.license_plate_number FROM park_rate p where p.license_plate_number is not null");
		sb.append(" )");
		sb.append(" ORDER BY licensePlateType asc");
		Query query = entityManager.createNativeQuery(sb.toString()).setParameter("userName",userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> parkRateModels =query.getResultList();
		return parkRateModels;
	}
	@Override
	public List<ParkRateModel> findParkRateByUserLicense(String userName) {
		StringBuffer sb =new StringBuffer("select p FROM ParkRateModel p where p.licensePlateNumber in(");
		sb.append("select license_plate_number  FROM LicensePlateModel l WHERE l.user_name =:userName)");
		sb.append(" GROUP BY p.communityNumber,licensePlateNumber");
		TypedQuery<ParkRateModel> query = entityManager.createQuery(sb.toString(), ParkRateModel.class);
		query.setParameter("userName", userName);
		List<ParkRateModel> list =query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list;
	}
	
	@Override
	public List<ParkRateModel> findByLicenseNotOutofdate(String license,boolean monthlyExpired) {
		StringBuffer sb = new StringBuffer("from ParkRateModel where licensePlateNumber = :license");
		if(monthlyExpired){
			sb.append(" and endTime > now()");
		}
		TypedQuery<ParkRateModel> query = entityManager.createQuery(sb.toString(), ParkRateModel.class);
		query.setParameter("license", license);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findCommunitiesByLicensePlate(String licensePlateNumber,Boolean isNotOutOfDate){
		StringBuffer sb = new StringBuffer(" select p.community_number as communityNumber,p.end_time as monthlyEndTime ");
		sb.append(" ,c.community_name as communityName, case c.disable_monthy_parking_fee_agr when 0 then 'true' else 'false' end as parkingFeeOnline");
		sb.append(" from park_rate p ");
		sb.append(" left join community c on c.community_number =p.community_number ");
		sb.append(" where p.license_plate_number =:licensePlateNumber");
		if(true == isNotOutOfDate){
			sb.append(" and p.end_time > now()");
		}
		Query query = entityManager.createNativeQuery(sb.toString()).setParameter("licensePlateNumber", licensePlateNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}

	@Override
	public void updatLockStatus(Long id,Integer lockStatus, Integer userLocks) {
		String sql = " update park_rate set lock_status=:lockStatus, user_locks=:userLocks where id=:id";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("id", id);
		query.setParameter("lockStatus", lockStatus);
		query.setParameter("userLocks", userLocks);
		query.executeUpdate();
	}
}
