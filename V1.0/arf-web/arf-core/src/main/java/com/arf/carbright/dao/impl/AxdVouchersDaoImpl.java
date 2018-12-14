package com.arf.carbright.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IAxdVouchersDao;
import com.arf.carbright.entity.AxdVouchers;
import com.arf.carbright.entity.AxdVouchers.VouchersStatus;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.insurance.dto.VouchersDto;

@Repository("axdVouchersDaoImpl")
public class AxdVouchersDaoImpl extends BaseDaoImpl<AxdVouchers, Long> implements IAxdVouchersDao{
	
	public AxdVouchers findByVouchersNum(String vouchersNum){
		try{
			String hql = "from AxdVouchers a where a.vouchersNum = :vouchersNum";
			TypedQuery<AxdVouchers> query = entityManager.createQuery(hql, AxdVouchers.class);
			query.setParameter("vouchersNum", vouchersNum);
			return query.getSingleResult();
		} catch (NoResultException e){
			return null;
		}
	}

	@Override
	public List<VouchersDto> findAllVouchers(Integer pageNo,Integer pageSize,Integer voucherType,String userName) {
		StringBuffer sql =new StringBuffer(" SELECT ");
		sql.append("  id as Id,vouchers_type as vouchersType,vouchers_money as vouchersMoney ,vouchers_sales_money as vouchersSalesMoney,");
		sql.append("  used_status usedStatus,effective_start_date as effectiveStartDate,effective_start_date as effectiveEndDate ,vouchers_num as vouchersNum,");
		sql.append("  vouchers_name as vouchersName ,use_rules as useRules ,scope as scope ,axd_used axdUsed,wechat_used wechatUsed,");
		sql.append("  user_name as userName, business_num as businessNum");
		sql.append("  FROM r_axd_vouchers_record where 1=1");
		sql.append("  AND used_status="+UsedStatus.Available.ordinal());
		sql.append("  AND effective_start_date<=NOW() AND effective_end_date >=NOW()");
		if (voucherType!=null) {
			sql.append("  AND vouchers_type =:voucherType");	
		}
		if (userName!=null) {
			sql.append("  AND user_name =:userName");	
		}
		
	    Query query = this.entityManager.createNativeQuery(sql.toString());
	    if (voucherType!=null) {
	    	query.setParameter("voucherType", voucherType);
		}
	    if (userName!=null) {
	    	query.setParameter("userName", userName);
		}
	    
	    if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(VouchersDto.class));
	    @SuppressWarnings("unchecked")
		List<VouchersDto> list =query.getResultList();
		return list;
	}

	@Override
	public List<Map<String, Object>> findVouchers(Integer cityCode,Integer privinceCode,boolean isPark) {
		StringBuffer sql =new StringBuffer(" SELECT ");
		sql.append(" id,vouchers_money as vouchersMoney ,vouchers_sales_money vouchersSalesMoney ,effective_date effectiveDate ,vouchers_start_date vouchersStartDate ,");
		sql.append(" vouchers_end_date vouchersEndDate,vouchers_num vouchersNum  ,vouchers_name vouchersName ,vouchers_pic vouchersPic ,vouchers_number vouchersNumber ,");
		sql.append(" surplus_number surplusNumber ,use_amount useAmount ,receive_community receiveCommunity,receive_point receivePoint,use_city useCity,use_business_num  business");
		sql.append(" FROM p_axd_vouchers WHERE (query_city LIKE '%"+cityCode+"%'");
		sql.append(" OR query_city LIKE '%"+privinceCode+"%')");
		
		if (!isPark) {
			sql.append(" and query_type=2");
		}
		sql.append(" and vouchers_status="+VouchersStatus.enablem.ordinal());
		sql.append(" AND vouchers_end_date >NOW() and vouchers_money>0");
		
		 Query query = this.entityManager.createNativeQuery(sql.toString());
		 query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	     @SuppressWarnings("unchecked")
		 List<Map<String, Object>> list =query.getResultList();
		return list;
	}
}
